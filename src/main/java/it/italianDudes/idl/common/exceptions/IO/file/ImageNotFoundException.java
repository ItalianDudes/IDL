/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italiandudes.idl.common.exceptions.IO.file;

import java.io.FileNotFoundException;

@SuppressWarnings("unused")
public class ImageNotFoundException extends FileNotFoundException {
    public ImageNotFoundException(Exception e){
        super(e.getMessage());
    }
}
