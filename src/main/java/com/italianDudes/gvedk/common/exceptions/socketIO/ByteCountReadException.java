package com.italianDudes.gvedk.common.exceptions.socketIO;

import java.io.IOException;

@SuppressWarnings("unused")
public class ByteCountReadException extends IOException {
    public ByteCountReadException(Throwable cause){
        super("Reading numBytes on InputStream failed.",cause);
    }
}
