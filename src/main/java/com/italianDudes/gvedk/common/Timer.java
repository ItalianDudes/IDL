package com.italianDudes.gvedk.common;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Timer implements Serializable {

    //Attributes
    private final Thread timerThread;
    private final RunningTimer timer;

    //Classes
    private static class RunningTimer implements Runnable {

        //Attributes
        private int seconds;
        private boolean isStopped;
        private boolean timeFinished;

        //Constructors
        public RunningTimer(int seconds){
            this.seconds = Math.max(seconds, 0);
            isStopped = false;
            timeFinished = this.seconds==0;
        }

        //Methods
        public int getSecondsLeft(){
            return seconds;
        }
        public boolean isStopped(){
            return isStopped;
        }
        public boolean isTimeFinished(){
            return timeFinished;
        }
        public void resume(){
            isStopped = false;
        }
        public void pause(){
            isStopped = true;
        }
        public void stop(){
            seconds = 0;
            timeFinished = true;
        }
        @SuppressWarnings({"BusyWait", "ResultOfMethodCallIgnored"})
        @Override
        public void run() {

            while (!timeFinished){

                if(isStopped){
                    try {
                        Thread.sleep(Long.MAX_VALUE);
                    } catch (InterruptedException interruptedException) {
                        Thread.interrupted();
                    }
                }

                if(seconds>0)
                    seconds--;
                if(seconds<=0){
                    timeFinished = true;
                    isStopped = true;
                }
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException ignored){
                    Thread.interrupted();
                }

            }

        }
    }

    //Constructors
    public Timer(int seconds){
            timer = new RunningTimer(seconds);
            timerThread = new Thread(timer);
    }

    //Methods
    public int getSecondsLeft(){
        return timer.getSecondsLeft();
    }
    public boolean isStopped(){
        return timer.isStopped();
    }
    public boolean isTimeFinished(){
        return timer.isTimeFinished();
    }
    public void resume(){
        timer.resume();
        timerThread.interrupt();
    }
    public void pause(){
        timer.pause();
    }
    public void start(){
        if(timerThread.getState().equals(Thread.State.NEW))
            timerThread.start();
    }
    public void stop(){
        timer.stop();
        timerThread.interrupt();
    }

}
