/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italiandudes.idl.logger;

import java.io.Serializable;

@SuppressWarnings({"unused", "ClassCanBeRecord"})
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
            this.isFatal = isFatal;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InfoFlags infoFlags)) return false;

        if (isException() != infoFlags.isException()) return false;
        if (isError() != infoFlags.isError()) return false;
        if (isFatal() != infoFlags.isFatal()) return false;
        return isErrStream() == infoFlags.isErrStream();
    }
    @Override
    public int hashCode() {
        int result = (isException() ? 1 : 0);
        result = 31 * result + (isError() ? 1 : 0);
        result = 31 * result + (isFatal() ? 1 : 0);
        result = 31 * result + (isErrStream() ? 1 : 0);
        return result;
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