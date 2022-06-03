package com.italianDudes.gvedk.common;

public class State {

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
}
