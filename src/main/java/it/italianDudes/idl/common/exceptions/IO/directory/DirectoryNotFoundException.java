package it.italianDudes.idl.common.exceptions.IO.directory;

import java.io.IOException;

@SuppressWarnings("unused")
public class DirectoryNotFoundException extends IOException {

    public DirectoryNotFoundException(String message){
        super(message);
    }

}
