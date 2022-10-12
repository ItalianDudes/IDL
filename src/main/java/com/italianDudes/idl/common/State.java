package com.italianDudes.idl.common;

import java.io.Serializable;

public class State implements Serializable {

    //Attributes
    private final int code;
    private final String message;

    //Constructors
    public State(int code, String message){
        this.code = code;
        this.message = message;
    }
    public State(int code){
        this(code,null);
    }

    //Methods
    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;

        State state = (State) o;

        if (getCode() != state.getCode()) return false;
        return getMessage().equals(state.getMessage());
    }
    @Override
    public int hashCode() {
        int result = getCode();
        result = 31 * result + getMessage().hashCode();
        return result;
    }
    @Override
    public String toString(){
        return code+" - "+message;
    }
}
