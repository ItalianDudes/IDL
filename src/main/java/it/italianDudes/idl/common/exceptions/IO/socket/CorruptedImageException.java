/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italiandudes.idl.common.exceptions.IO.socket;

import java.io.IOException;

@SuppressWarnings("unused")
public class CorruptedImageException extends IOException {
    public CorruptedImageException(Throwable cause){
        super("Conversion from byte[] to BufferedImage failed.",cause);
    }
}
