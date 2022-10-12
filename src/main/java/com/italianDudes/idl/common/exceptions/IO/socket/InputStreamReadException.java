/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package com.italianDudes.idl.common.exceptions.IO.socket;

import java.io.IOException;

@SuppressWarnings("unused")
public class InputStreamReadException extends IOException {
    public InputStreamReadException(Throwable cause){
        super("Reading on InputStream failed.",cause);
    }
}
