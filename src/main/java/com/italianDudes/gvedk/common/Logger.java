package com.italianDudes.gvedk.common;

import com.italianDudes.gvedk.GVEDK;
import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SuppressWarnings({"UnusedReturnValue","unused"})
public final class Logger {

    //Attributes
    private static final File latestLogFilePointer = new File(GVEDK.Defs.LOG_LATEST_FILE);
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
    public static boolean init() throws IOException {
        File logDirectory = new File(GVEDK.Defs.LOG_DIR);
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
        queue.submit(new LogWriter(message, flags));
    }
    private static boolean save() throws IOException{
        String date = startTime.format(DateTimeFormatter.BASIC_ISO_DATE)+"_"+startTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
        date = date.replaceAll(":","-");
        date = date.split("\\.")[0];
        File newLogDestination = new File(GVEDK.Defs.LOG_DIR+date+".log");
        FileUtils.copyFile(latestLogFilePointer,newLogDestination);
        return true;
    }
    public static boolean close() throws IOException {

        boolean result;
        queue.shutdown();
        try {
            result = queue.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        }catch (InterruptedException e){
            System.err.println("[ERROR][FATAL]["+LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+"] An error has occurred during queue termination");
            result = false;
        }
        if(!result) {
            System.err.println("[ERROR][FATAL]["+LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+"] An error has occurred during queue termination");
        }
        try{
            logger.close();
        }catch (IOException e){
            System.err.println("[ERROR][FATAL]["+LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+"] Can't close log file!");
            throw e;
        }
        return save();
    }
    private static boolean createLogFile() throws IOException {
        try {
            logger = new BufferedWriter(new FileWriter(latestLogFilePointer));
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
