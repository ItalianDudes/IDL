package com.italianDudes.idl.common.exceptions.IO.socket;

import java.io.IOException;

@SuppressWarnings("unused")
public class CorruptedImageException extends IOException {
    public CorruptedImageException(Throwable cause){
        super("Conversion from byte[] to BufferedImage failed.",cause);
    }
}
