/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italiandudes.idl.common.exceptions;

@SuppressWarnings("unused")
public class NullPeerException extends NullPointerException {
    public NullPeerException(){
        super("The peer is null.");
    }

}
