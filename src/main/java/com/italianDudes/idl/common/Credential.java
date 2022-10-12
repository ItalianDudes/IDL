/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package com.italianDudes.idl.common;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;
import java.util.Arrays;

@SuppressWarnings("unused")
public class Credential implements Serializable {

    //Attributes
    private final String username;
    private final String password;

    //Builders
    public Credential(){
        this.username = null;
        this.password = null;
    }
    public Credential(String username, String password, boolean encryptPassword){
        this.username = username;
        if(encryptPassword)
            this.password = DigestUtils.sha512Hex(password);
        else
            this.password = password;
    }
    public Credential(String username, char[] password, boolean encryptPassword){
        this.username = username;
        if(encryptPassword)
            this.password = DigestUtils.sha512Hex(passwordCharArrayToString(password));
        else
            this.password = passwordCharArrayToString(password);
    }
    public Credential(String username, String password){
        this(username,password,true);
    }
    public Credential(String username, char[] password){
        this(username,password,true);
    }

    //Methods
    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Credential)) return false;

        Credential that = (Credential) o;

        if (!getUsername().equals(that.getUsername())) return false;
        return getPassword().equals(that.getPassword());
    }
    @Override
    public int hashCode() {
        int result = getUsername().hashCode();
        result = 31 * result + getPassword().hashCode();
        return result;
    }
    @Override
    public String toString(){
        return "Username: "+username+"\nPassword: "+password;
    }

    private String passwordCharArrayToString(char[] passwordCharArray){
        return Arrays.toString(passwordCharArray);
    }
}