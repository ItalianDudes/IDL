/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italiandudes.idl.common;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("unused")
public final class ImageHandler {

    //Constructors
    private ImageHandler(){
        throw new UnsupportedOperationException("Can't instantiate this class!");
    }

    //Methods
    public static String getImageExtension(String imagePath){
        return FileHandler.getFileExtension(imagePath);
    }
    public static void writeImage(String destinationPath, BufferedImage bufferedImage) throws IOException{
        writeImage(new File(destinationPath), bufferedImage);
    }
    public static void writeImage(File destinationFile, BufferedImage bufferedImage) throws IOException{
        ImageIO.write(bufferedImage,FileHandler.getFileExtension(destinationFile),destinationFile);
    }
    public static BufferedImage readImage(String imagePath) throws IOException{
        return readImage(new File(imagePath));
    }
    public static BufferedImage readImage(File imageFile) throws IOException{
        return ImageIO.read(imageFile);
    }
}