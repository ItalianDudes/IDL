/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italiandudes.idl.common.exceptions.IO.directory;

import java.io.IOException;

@SuppressWarnings("unused")
public class DirectoryNotFoundException extends IOException {

    public DirectoryNotFoundException(String message){
        super(message);
    }

}
