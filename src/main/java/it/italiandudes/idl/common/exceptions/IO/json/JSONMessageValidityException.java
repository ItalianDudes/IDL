/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italiandudes.idl.common.exceptions.IO.json;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@SuppressWarnings("unused")
public class JSONMessageValidityException extends IOException {
    public JSONMessageValidityException(@NotNull final String message){
        super(message);
    }
    public JSONMessageValidityException(@NotNull final String message, @NotNull final Throwable cause) {
        super(message, cause);
    }
}
