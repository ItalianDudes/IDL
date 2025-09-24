/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italiandudes.idl.throwable.exceptions.IO.socket;

import java.io.IOException;

@SuppressWarnings("unused")
public class BytesMismatchException extends IOException {
    public BytesMismatchException(){
        super("Mismatch between bytes red and bytes expected.");
    }
}
