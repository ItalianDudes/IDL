/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italianDudes.idl.common.exceptions.IO.socket;

import java.io.IOException;

@SuppressWarnings("unused")
public class SpecializedStreamInstancingException extends IOException {
    public SpecializedStreamInstancingException(Throwable cause){
        super("Instancing specialized stream failed.",cause);
    }
}
