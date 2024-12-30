/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italiandudes.idl.common;

import it.italiandudes.idl.IDL;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"UnusedReturnValue","unused"})
public final class Logger {

    // Constants
    private static final SimpleDateFormat DEFAULT_TIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.sss");

    //Attributes
    private static final File latestLogFilePointer = new File(IDL.Defs.LOG_LATEST_FILE);
    private static BufferedWriter logger = null;
    private static ExecutorService queue = null;
    private final static LocalDateTime startTime = LocalDateTime.now();
    private static SimpleDateFormat TIME_FORMAT;
    @Nullable private static String context = null;

    // [DATE][CONTEXT] Message

    //Classes
    private static final class LogWriter implements Runnable {

        //Attributes
        @NotNull private final String message;
        @Nullable private final String context;
        @NotNull private final InfoFlags flags;

        //Constructors
        public LogWriter(@NotNull String message, @Nullable InfoFlags flags, @Nullable String context){
            this.message = message;
            if(flags==null) this.flags = new InfoFlags();
            else this.flags = flags;
            this.context = context;
        }

        //Methods
        @Override
        public void run() {
            if (flags.isErrStream()) {
                System.err.println(flags + "[" + TIME_FORMAT.format(System.currentTimeMillis()) + "]" + (context!=null?"[" + context + "] ":" ") + message);
                System.err.flush();
            } else {
                System.out.println(flags + "[" + TIME_FORMAT.format(System.currentTimeMillis()) + "]" + (context!=null?"[" + context + "] ":" ") + message);
                System.out.flush();
            }
            try {
                writeMessageIntoLogFile(message, flags, context);
            } catch (IOException e) {
                System.err.println("[EXCEPTION]" + "[" + TIME_FORMAT.format(System.currentTimeMillis()) + "]" + (context!=null?"[" + context + "] ":" ") + "Can't write log message into log file!");
            }
        }
    }

    //Constructors
    private Logger(){
        throw new UnsupportedOperationException("Can't instantiate this class!");
    }

    //Methods
    public synchronized static void setContext(@Nullable String context) {
        Logger.context = context;
    }
    public static @Nullable String getContext() {
        return context;
    }
    public static boolean isInitialized(){
        return logger != null;
    }
    public static boolean init(boolean logUncaughtExceptions, @NotNull final SimpleDateFormat timeFormatter) throws IOException {
        TIME_FORMAT = timeFormatter;
        try {
            backupOldLog();
        }catch (IOException e){
            System.err.println("[ERROR][" + TIME_FORMAT.format(System.currentTimeMillis()) + "]" + (context!=null?"[" + context + "] ":" ") + "Error during old log file copy");
        }
        File logDirectory = new File(IDL.Defs.LOG_DIR);
        if(!logDirectory.exists() || !logDirectory.isDirectory()) {
            if (!logDirectory.mkdir()) {
                System.err.println("[ERROR][FATAL]["+TIME_FORMAT.format(System.currentTimeMillis())+"]" + (context!=null?"[" + context + "] ":" ") + "Can't create log directory!");
                return false;
            }
        }
        queue = Executors.newSingleThreadExecutor();
        boolean logCreated = createLogFile();
        if(logUncaughtExceptions)
            Thread.setDefaultUncaughtExceptionHandler((t, e) -> Logger.log(e));
        return logCreated;
    }
    public static boolean init(@NotNull final SimpleDateFormat timeFormatter) throws IOException {
        return init(true, timeFormatter);
    }
    public static boolean init() throws IOException {
        return init(true, DEFAULT_TIME_FORMAT);
    }
    public static void log(String message){
        log(message,new InfoFlags());
    }
    public static void log(Throwable throwable, InfoFlags flags){
        log(StringHandler.getStackTrace(throwable),flags);
    }
    public static void log(Throwable throwable){ //Not Fatal by Default
        log(StringHandler.getStackTrace(throwable), new InfoFlags(throwable,false));
    }
    public static void logWithCaller(String message){
        log("["+Thread.currentThread().getStackTrace()[2]+"] "+message);
    }
    public static void log(String message, InfoFlags flags){
        if(Logger.isInitialized() && !queue.isShutdown()) {
            queue.submit(new LogWriter(message, flags, context));
        }else{
            if(flags.isErrStream()){
                System.err.println("[" + TIME_FORMAT.format(System.currentTimeMillis()) + "][ERR]" + (context!=null?"[" + context + "] ":" ") + message);
            }else{
                System.out.println("[" + TIME_FORMAT.format(System.currentTimeMillis()) + "]" + (context!=null?"[" + context + "] ":" ") + message);
            }
        }
    }
    private static void backupOldLog() throws IOException{
        File latestLogFile = new File(IDL.Defs.LOG_LATEST_FILE);
        if(latestLogFile.exists() && latestLogFile.isFile()){

            String date = null;

            Scanner inFile = new Scanner(latestLogFile);

            try {
                if (inFile.hasNext()) {
                    date = inFile.nextLine();
                }
            }catch (NoSuchElementException ignored){}

            inFile.close();

            if(date!=null) {
                File newLogDestination = new File(IDL.Defs.LOG_DIR + date + ".log");
                FileUtils.copyFile(latestLogFilePointer, newLogDestination);
            }
        }
    }
    public static void close() {
        if(Logger.isInitialized())
            new Thread(() -> {
                boolean result = false;
                queue.shutdown();
                try {
                    result = queue.awaitTermination(60, TimeUnit.SECONDS);
                    new CountDownLatch(1).countDown();
                }catch (InterruptedException e){
                    Logger.log(e);

                    System.err.println("[ERROR][FATAL]["+TIME_FORMAT.format(System.currentTimeMillis())+"]" + (context!=null?"[" + context + "] ":" ") +  "An error has occurred during queue termination");
                }
                if(!result) {
                    System.err.println("[ERROR][FATAL]["+TIME_FORMAT.format(System.currentTimeMillis())+"]" + (context!=null?"[" + context + "] ":" ") + "Time elapsed before queue termination");
                }
                try{
                    logger.close();
                }catch (IOException e){
                    System.err.println("[ERROR][FATAL]["+TIME_FORMAT.format(System.currentTimeMillis())+"]" + (context!=null?"[" + context + "] ":" ") + "Can't close log file!");
                }
            }).start();
    }
    private static boolean createLogFile() throws IOException {
        String date = startTime.format(DateTimeFormatter.BASIC_ISO_DATE)+"_"+startTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
        date = date.replaceAll(":","-");
        date = date.split("\\.")[0];
        try {
            logger = new BufferedWriter(new FileWriter(latestLogFilePointer));
            logger.append(date).append("\n");
            logger.flush();
        }catch (IOException e){
            System.err.println("[ERROR][FATAL]["+TIME_FORMAT.format(System.currentTimeMillis())+"]" + (context!=null?"[" + context + "] ":" ") + "Can't initialize logger!");
            throw e;
        }
        return true;
    }
    private synchronized static void writeMessageIntoLogFile(String message, InfoFlags flags, String context) throws IOException {
        try {
            logger.append(flags.toString()).append("[").append(TIME_FORMAT.format(System.currentTimeMillis())).append("]").append(context!=null?"[" + context + "] ":" ").append(message).append("\n");
        }catch (IOException e){
            System.err.println("[ERROR][FATAL]["+TIME_FORMAT.format(System.currentTimeMillis())+"]" + (context!=null?"[" + context + "] ":" ") + "Can't write message into log file!");
            throw e;
        }
        try {
            logger.flush();
        }catch (IOException e){
            System.err.println("[ERROR][FATAL]["+TIME_FORMAT.format(System.currentTimeMillis())+"]" + (context!=null?"[" + context + "] ":" ") + "Can't flush log file!");
            throw e;
        }
    }
}
