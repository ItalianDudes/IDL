package com.italianDudes.gvedk.common.exceptions.IO.file;

import java.io.FileNotFoundException;

@SuppressWarnings("unused")
public class ImageNotFoundException extends FileNotFoundException {
    public ImageNotFoundException(Exception e){
        super(e.getMessage());
    }
}
