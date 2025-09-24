/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italiandudes.idl.throwable.error.os;

@SuppressWarnings("unused")
public class UnsupportedOSError extends Error {

    public UnsupportedOSError(String message){
        super(message);
    }
}
