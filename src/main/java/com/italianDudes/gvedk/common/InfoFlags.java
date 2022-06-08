package com.italianDudes.gvedk.common;

import java.io.Serializable;

public class InfoFlags implements Serializable {
    //Attributes
    private final boolean isException;
    private final boolean isError;
    private final boolean isFatal;
    private final boolean errStream;

    //Constructors
    public InfoFlags(Exception exception){
        this(true,false);
    }
    public InfoFlags(Exception exception, boolean isFatal){
        this(true,isFatal);
    }
    public InfoFlags(Error error){
        this(true);
    }
    public InfoFlags(Throwable throwable, boolean isFatal){
        if(throwable instanceof Error){
            this.isError = true;
            this.isFatal = true;
            this.errStream = true;
            this.isException = false;
        }else if(throwable instanceof Exception){
            this.isError = false;
            this.errStream = true;
            this.isException = true;
            this.isFatal = isFatal;
        }else{
            this.errStream = false;
            this.isError = false;
            this.isException = false;
            this.isFatal = false;
        }
    }
    public InfoFlags(){
        this(false,false,false,false);
    }
    public InfoFlags(boolean isException, boolean isFatal){
        this(isException,false,isFatal,isException||isFatal);
    }
    public InfoFlags(boolean isError){
        this(false,isError,isError,isError);
    }
    public InfoFlags(boolean isException, boolean isError, boolean isFatal, boolean errStream){
        this.isFatal = isFatal;
        this.isException = isException;
        this.isError = isError;
        this.errStream = errStream;
    }

    //Methods
    public boolean isException() {
        return isException;
    }
    public boolean isError() {
        return isError;
    }
    public boolean isFatal() {
        return isFatal;
    }
    public boolean isErrStream(){
        return errStream;
    }
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof InfoFlags))
            return false;
        InfoFlags flags = (InfoFlags) obj;
        return flags.isError == this.isError && flags.isException == this.isException && flags.isFatal == this.isFatal && flags.errStream == this.errStream;
    }
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if(isError) {
            stringBuilder.append("[ERROR][FATAL]");
        }else{
            if(isException){
                stringBuilder.append("[EXCEPTION]");
                if(isFatal)
                    stringBuilder.append("[FATAL]");
            }
        }
        return stringBuilder.toString();
    }
}
