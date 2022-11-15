/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italiandudes.idl.common;

import it.italiandudes.idl.IDL;
import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"UnusedReturnValue","unused"})
public final class Logger {

    //Attributes
    private static final File latestLogFilePointer = new File(IDL.Defs.LOG_LATEST_FILE);
    private static BufferedWriter logger = null;
    private static ExecutorService queue = null;
    private final static LocalDateTime startTime = LocalDateTime.now();

    //Classes
    private static class LogWriter implements Runnable {

        //Attributes
        private final String message;
        private final InfoFlags flags;

        //Constructors
        public LogWriter(String message, InfoFlags flags){
            this.message = message;
            if(flags==null)
                this.flags = new InfoFlags();
            else
                this.flags = flags;
        }

        //Methods
        @Override
        public void run() {
            if(flags.isErrStream()){
                System.err.println(flags+"[" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "] " + message);
                System.err.flush();
            }else {
                System.out.println(flags+"[" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "] " + message);
                System.out.flush();
            }
            try {
                writeMessageIntoLogFile(message, flags);
            } catch (IOException e) {
                System.err.println("[EXCEPTION]"+"[" + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "] Can't write log message into log file!");
            }
        }
    }

    //Constructors
    private Logger(){
        throw new UnsupportedOperationException("Can't instantiate this class!");
    }

    //Methods
    public static boolean isInitialized(){
        return logger != null;
    }
    public static boolean init() throws IOException {
        try {
            backupOldLog();
        }catch (IOException e){
            System.err.println("Error during old log file copy");
        }
        File logDirectory = new File(IDL.Defs.LOG_DIR);
        if(!logDirectory.exists() || !logDirectory.isDirectory()) {
            if (!logDirectory.mkdir()) {
                System.err.println("[ERROR][FATAL]["+LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+"] Can't create log directory!");
                return false;
            }
        }
        queue = Executors.newSingleThreadExecutor();
        return createLogFile();
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
        if(!queue.isShutdown())
            queue.submit(new LogWriter(message, flags));
    }
    private static void backupOldLog() throws IOException{
        File latestLogFile = new File(IDL.Defs.LOG_LATEST_FILE);
        if(latestLogFile.exists() && latestLogFile.isFile()){

            String date = null;

            Scanner inFile = new Scanner(latestLogFile);

            if(inFile.hasNext()){
                date = inFile.nextLine();
            }

            inFile.close();

            File newLogDestination = new File(IDL.Defs.LOG_DIR+date+".log");
            FileUtils.copyFile(latestLogFilePointer,newLogDestination);

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
                    e.printStackTrace();
                    System.err.println("[ERROR][FATAL]["+LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+"] An error has occurred during queue termination");
                }
                if(!result) {
                    System.err.println("[ERROR][FATAL]["+LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+"] Time elapsed before queue termination");
                }
                try{
                    logger.close();
                }catch (IOException e){
                    System.err.println("[ERROR][FATAL]["+LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+"] Can't close log file!");
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
        }catch (IOException e){
            System.err.println("[ERROR][FATAL]["+LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+"] Can't initialize logger!");
            throw e;
        }
        return true;
    }
    private synchronized static void writeMessageIntoLogFile(String message, InfoFlags flags) throws IOException {
        try {
            logger.append(flags.toString()).append("[").append(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append("] ").append(message).append("\n");
        }catch (IOException e){
            System.err.println("[ERROR][FATAL]["+LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+"] Can't write message into log file!");
            throw e;
        }
        try {
            logger.flush();
        }catch (IOException e){
            System.err.println("[ERROR][FATAL]["+LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+"] Can't flush log file!");
            throw e;
        }
    }
}
