/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package com.italianDudes.gvedk.common.exceptions.IO.socket;

import java.io.IOException;

@SuppressWarnings("unused")
public class OutputStreamWriteException extends IOException {
    public OutputStreamWriteException(Throwable cause){
        super("Writing on OutputStream failed.",cause);
    }
}
