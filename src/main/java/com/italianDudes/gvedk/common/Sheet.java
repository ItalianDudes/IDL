/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package com.italianDudes.gvedk.common;

import java.io.Serializable;

@SuppressWarnings("unused")
public abstract class Sheet implements Serializable {

    //Attributes
    private final Credential userCredential;

    //Constructors
    public Sheet(Credential userCredential){
        this.userCredential = userCredential;
    }

    //Methods
    public Credential getUserCredential(){
        return userCredential;
    }

    //Abstract Methods
    public abstract Sheet readSheet(String path);
    public abstract void writeSheet(String path);
    public abstract void sendSheet(Peer destinationPeer);
}