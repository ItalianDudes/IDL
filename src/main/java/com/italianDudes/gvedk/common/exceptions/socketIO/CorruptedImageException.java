package com.italianDudes.gvedk.common.exceptions.socketIO;

import java.io.IOException;

@SuppressWarnings("unused")
public class CorruptedImageException extends IOException {
    public CorruptedImageException(Throwable cause){
        super("Conversion from byte[] to BufferedImage failed.",cause);
    }
}
