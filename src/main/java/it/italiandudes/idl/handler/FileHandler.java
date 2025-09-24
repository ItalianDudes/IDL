/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italiandudes.idl.handler;

import it.italiandudes.idl.throwable.exceptions.IO.directory.DirectoryNotFoundException;

import java.io.File;

@SuppressWarnings("unused")
public final class FileHandler {

    public static String getFileExtension(String path){
        return getFileExtension(new File(path));
    }
    public static String getFileExtension(File file){
        if(file == null) return null;
        String path = file.getPath();
        String[] splitPath = path.split("\\.");
        if(splitPath.length>0){
            return splitPath[splitPath.length-1];
        }else {
            return null;
        }
    }
    public static File[] findFilesWithName(File directory, String name) throws DirectoryNotFoundException {
        if(!DirectoryHandler.directoryExist(directory))
            throw new DirectoryNotFoundException("Directory "+directory.getAbsolutePath()+" doesn't exist");
        return directory.listFiles(pathname -> pathname.getName().contains(name));

    }
    public static File[] findDirectoriesContainingName(File directory, String name) throws DirectoryNotFoundException {
        if(!DirectoryHandler.directoryExist(directory))
            throw new DirectoryNotFoundException("Directory "+directory.getAbsolutePath()+" doesn't exist");
        return directory.listFiles(pathname -> pathname.getName().contains(name) && pathname.isDirectory());
    }
}