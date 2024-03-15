/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italiandudes.idl.common;

import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Arrays;

@SuppressWarnings("unused")
public class Credential implements Serializable {

    //Attributes
    @NotNull private final String username;
    @Nullable private final String password;

    //Builders
    public Credential(@NotNull String username, @Nullable String password, boolean encryptPassword){
        this.username = username;
        if (password != null) {
            if (encryptPassword) this.password = DigestUtils.sha512Hex(password);
            else this.password = password;
        } else this.password = null;
    }
    public Credential(@NotNull String username, char @Nullable[] password, boolean encryptPassword){
        this.username = username;
        if (password != null) {
            if (encryptPassword) this.password = DigestUtils.sha512Hex(passwordCharArrayToString(password));
            else this.password = passwordCharArrayToString(password);
        } else this.password = null;
    }
    public Credential(@NotNull String username, @Nullable String password){
        this(username,password,true);
    }
    public Credential(@NotNull String username, char @Nullable[] password){
        this(username,password,true);
    }

    //Methods
    @NotNull
    public String getUsername(){
        return username;
    }
    @Nullable
    public String getPassword(){
        return password;
    }
    @Override
    public String toString(){
        return "Username: "+username + (password!=null?"\nPassword: "+password:"");
    }
    @Nullable
    private String passwordCharArrayToString(char @Nullable[] passwordCharArray){
        if (passwordCharArray == null) return null;
        else return Arrays.toString(passwordCharArray);
    }
}