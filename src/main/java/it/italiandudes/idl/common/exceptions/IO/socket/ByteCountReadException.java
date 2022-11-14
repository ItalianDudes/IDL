/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italiandudes.idl.common.exceptions.IO.socket;

import java.io.IOException;

@SuppressWarnings("unused")
public class ByteCountReadException extends IOException {
    public ByteCountReadException(Throwable cause){
        super("Reading numBytes on InputStream failed.",cause);
    }
}
