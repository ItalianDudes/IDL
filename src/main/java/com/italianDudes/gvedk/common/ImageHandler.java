/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package com.italianDudes.gvedk.common;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

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
    @Deprecated
    public static void sendImage(OutputStream out, String imagePath, final int DIM_BUFFER) throws IOException {

        FileInputStream in = new FileInputStream(imagePath);

        byte[] buffer = new byte[DIM_BUFFER];
        int numLetti = in.read(buffer);
        while( numLetti > 0 ){
            out.write(buffer,0,numLetti);
            numLetti = in.read(buffer);
        }

        in.close();
    }
    @Deprecated
    public static void sendImage(OutputStream out, String imagePath) throws IOException {

        FileInputStream in = new FileInputStream(imagePath);

        ByteArrayOutputStream outByte = new ByteArrayOutputStream();

        ImageIO.write(readImage(imagePath),getImageExtension(imagePath),outByte);

        DataOutputStream outStream = new DataOutputStream(out);

        outStream.writeInt(outByte.size());
        out.write(outByte.toByteArray(),0,outByte.size());

        in.close();
    }
    @Deprecated
    public static void sendImage(OutputStream out, File image) throws IOException {

        ByteArrayOutputStream outByte = new ByteArrayOutputStream();

        ImageIO.write(ImageIO.read(image),getImageExtension(image.getName()),outByte);

        DataOutputStream outStream = new DataOutputStream(out);

        outStream.writeInt(outByte.size());
        out.write(outByte.toByteArray(),0,outByte.size());
    }
    @Deprecated
    public static BufferedImage receiveImage(InputStream in) throws IOException {

        DataInputStream inStream = new DataInputStream(in);

        int size = inStream.readInt();

        byte[] bytes = new byte[size];

        int bytesRead = in.read(bytes,0,size);

        if ( bytesRead == size ) {
            return ImageIO.read(new ByteArrayInputStream(bytes));
        } else {
            return null;
        }
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
