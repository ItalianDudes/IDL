/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italiandudes.idl.throwable.exceptions.IO;

import java.io.IOException;

@SuppressWarnings("unused")
public class InsufficientPrivilegesException extends IOException {

    public InsufficientPrivilegesException(String message){
        super(message);
    }

}
