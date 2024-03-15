/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italiandudes.idl.common;

import it.italiandudes.idl.common.exceptions.IO.socket.CorruptedImageException;
import it.italiandudes.idl.common.exceptions.IO.socket.InputStreamReadException;
import it.italiandudes.idl.common.exceptions.IO.socket.OutputStreamWriteException;
import it.italiandudes.idl.common.exceptions.IO.socket.SpecializedStreamInstancingException;

import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.io.*;
import java.util.Base64;

@SuppressWarnings("unused")
public final class RawSerializer {

    //Constructors
    private RawSerializer(){
        throw new UnsupportedOperationException("Can't instantiate this class!");
    }

    //Public Definitions (Invokers): Sender
    public static void sendInt(OutputStream outputStream, int number) throws OutputStreamWriteException {
        writeInt(outputStream,number,false);
    }
    public static void sendInt(OutputStream outputStream, int number, boolean advancedLog) throws OutputStreamWriteException {
        writeInt(outputStream,number,advancedLog);
    }
    public static void sendLong(OutputStream outputStream, long longNumber) throws OutputStreamWriteException {
        writeLong(outputStream,longNumber,false);
    }
    public static void sendLong(OutputStream outputStream, long longNumber, boolean advancedLog) throws OutputStreamWriteException {
        writeLong(outputStream,longNumber,advancedLog);
    }
    public static void sendFloat(OutputStream outputStream, float floatNumber) throws OutputStreamWriteException {
        writeFloat(outputStream,floatNumber,false);
    }
    public static void sendFloat(OutputStream outputStream, float floatNumber, boolean advancedLog) throws OutputStreamWriteException {
        writeFloat(outputStream,floatNumber,advancedLog);
    }
    public static void sendDouble(OutputStream outputStream, double doubleNumber) throws OutputStreamWriteException {
        writeDouble(outputStream,doubleNumber,false);
    }
    public static void sendDouble(OutputStream outputStream, double doubleNumber, boolean advancedLog) throws OutputStreamWriteException {
        writeDouble(outputStream,doubleNumber,advancedLog);
    }
    public static void sendBoolean(OutputStream outputStream, boolean state) throws OutputStreamWriteException {
        writeBoolean(outputStream,state,false);
    }
    public static void sendBoolean(OutputStream outputStream, boolean state, boolean advancedLog) throws OutputStreamWriteException {
        writeBoolean(outputStream,state,advancedLog);
    }
    public static void sendString(OutputStream outputStream, String str) throws OutputStreamWriteException {
        writeString(outputStream,str,false);
    }
    public static void sendString(OutputStream outputStream, String str, boolean advancedLog) throws OutputStreamWriteException {
        writeString(outputStream, str, advancedLog);
    }
    public static void sendObject(OutputStream outputStream, Object obj) throws OutputStreamWriteException, NotSerializableException, SpecializedStreamInstancingException {
        writeObject(outputStream,obj,false);
    }
    public static void sendObject(OutputStream outputStream, Object obj, boolean advancedLog) throws OutputStreamWriteException, NotSerializableException, SpecializedStreamInstancingException {
        writeObject(outputStream,obj,advancedLog);
    }
    public static void sendFormattedImage(OutputStream outputStream, File img) throws IOException {
        writeFormattedImage(outputStream,new FormattedImage(ImageIO.read(img), FileHandler.getFileExtension(img)),false);
    }
    public static void sendFormattedImage(OutputStream outputStream, File img, boolean advancedLog) throws IOException {
        writeFormattedImage(outputStream, new FormattedImage(ImageIO.read(img), FileHandler.getFileExtension(img)),advancedLog);
    }
    public static void sendFormattedImage(OutputStream outputStream, FormattedImage formattedImage) throws OutputStreamWriteException {
        writeFormattedImage(outputStream,formattedImage,false);
    }
    public static void sendFormattedImage(OutputStream outputStream, FormattedImage formattedImage, boolean advancedLog) throws OutputStreamWriteException {
        writeFormattedImage(outputStream,formattedImage,advancedLog);
    }
    public static void sendBytes(OutputStream outputStream, byte[] bytes) throws OutputStreamWriteException {
        writeBytes(outputStream, bytes, 0, bytes.length, false);
    }
    public static void sendBytes(OutputStream outputStream, byte[] bytes, int offset) throws OutputStreamWriteException {
        writeBytes(outputStream, bytes, offset, bytes.length, false);
    }
    public static void sendBytes(OutputStream outputStream, byte[] bytes, int offset, int length) throws OutputStreamWriteException {
        writeBytes(outputStream, bytes, offset, length, false);
    }
    public static void sendBytes(OutputStream outputStream, byte[] bytes, boolean advancedLog) throws OutputStreamWriteException {
        writeBytes(outputStream, bytes, 0, bytes.length, advancedLog);
    }
    public static void sendBytes(OutputStream outputStream, byte[] bytes, int offset, boolean advancedLog) throws OutputStreamWriteException {
        writeBytes(outputStream, bytes, offset, bytes.length, advancedLog);
    }
    public static void sendBytes(OutputStream outputStream, byte[] bytes, int offset, int length, boolean advancedLog) throws OutputStreamWriteException {
        writeBytes(outputStream, bytes, offset, length, advancedLog);
    }

    //Public Definitions (Invokers): Receiver
    public static int receiveInt(InputStream inputStream) throws  InputStreamReadException {
        return readInt(inputStream,false);
    }
    public static int receiveInt(InputStream inputStream, boolean advancedLog) throws  InputStreamReadException {
        return readInt(inputStream,advancedLog);
    }
    public static long receiveLong(InputStream inputStream) throws  InputStreamReadException {
        return readLong(inputStream,false);
    }
    public static long receiveLong(InputStream inputStream, boolean advancedLog) throws  InputStreamReadException {
        return readLong(inputStream,advancedLog);
    }
    public static float receiveFloat(InputStream inputStream) throws  InputStreamReadException {
        return readFloat(inputStream,false);
    }
    public static float receiveFloat(InputStream inputStream, boolean advancedLog) throws  InputStreamReadException {
        return readFloat(inputStream,advancedLog);
    }
    public static double receiveDouble(InputStream inputStream) throws  InputStreamReadException {
        return readDouble(inputStream,false);
    }
    public static double receiveDouble(InputStream inputStream, boolean advancedLog) throws  InputStreamReadException {
        return readDouble(inputStream,advancedLog);
    }
    public static boolean receiveBoolean(InputStream inputStream) throws  InputStreamReadException {
        return readBoolean(inputStream,false);
    }
    public static boolean receiveBoolean(InputStream inputStream, boolean advancedLog) throws  InputStreamReadException {
        return readBoolean(inputStream,advancedLog);
    }
    public static String receiveString(InputStream inputStream) throws  InputStreamReadException {
        return readString(inputStream,false);
    }
    public static String receiveString(InputStream inputStream, boolean advancedLog) throws  InputStreamReadException {
        return readString(inputStream,advancedLog);
    }
    public static Object receiveObject(InputStream inputStream) throws InputStreamReadException, ClassNotFoundException, SpecializedStreamInstancingException {
        return readObject(inputStream,false);
    }
    public static Object receiveObject(InputStream inputStream, boolean advancedLog) throws InputStreamReadException, ClassNotFoundException, SpecializedStreamInstancingException {
        return readObject(inputStream,advancedLog);
    }
    public static FormattedImage receiveFormattedImage(InputStream inputStream) throws  CorruptedImageException, InputStreamReadException {
        return readFormattedImage(inputStream,false);
    }
    public static FormattedImage receiveFormattedImage(InputStream inputStream, boolean advancedLog) throws  CorruptedImageException, InputStreamReadException {
        return readFormattedImage(inputStream,advancedLog);
    }
    public static byte[] receiveBytes(InputStream inputStream) throws InputStreamReadException {
        return readBytes(inputStream, false);
    }
    public static byte[] receiveBytes(InputStream inputStream, boolean advancedLog) throws InputStreamReadException {
        return readBytes(inputStream, advancedLog);
    }

    //Private Definitions: Output
    private static void writeInt(OutputStream outputStream, int integerNumber, boolean advancedLog) throws OutputStreamWriteException {
        DataOutputStream outStream = new DataOutputStream(outputStream);
        try {
            outStream.writeInt(integerNumber);
            outStream.flush();
        } catch (IOException e) {
            if(advancedLog) Logger.log(e);
            throw new OutputStreamWriteException(e);
        }
    }
    private static void writeLong(OutputStream outputStream, long longNumber, boolean advancedLog) throws OutputStreamWriteException {
        DataOutputStream outStream = new DataOutputStream(outputStream);
        try {
            outStream.writeLong(longNumber);
            outStream.flush();
        } catch (IOException e) {
            if(advancedLog) Logger.log(e);
            throw new OutputStreamWriteException(e);
        }
    }
    private static void writeFloat(OutputStream outputStream, float floatNumber, boolean advancedLog) throws OutputStreamWriteException {
        DataOutputStream outStream = new DataOutputStream(outputStream);
        try {
            outStream.writeFloat(floatNumber);
            outStream.flush();
        } catch (IOException e) {
            if(advancedLog) Logger.log(e);
            throw new OutputStreamWriteException(e);
        }
    }
    private static void writeDouble(OutputStream outputStream, double doubleNumber, boolean advancedLog) throws OutputStreamWriteException {
        DataOutputStream outStream = new DataOutputStream(outputStream);
        try {
            outStream.writeDouble(doubleNumber);
            outStream.flush();
        } catch (IOException e) {
            if(advancedLog) Logger.log(e);
            throw new OutputStreamWriteException(e);
        }
    }
    private static void writeBoolean(OutputStream outputStream, boolean state, boolean advancedLog) throws OutputStreamWriteException {
        DataOutputStream outStream = new DataOutputStream(outputStream);
        try {
            outStream.writeBoolean(state);
            outStream.flush();
        } catch (IOException e) {
            if(advancedLog) Logger.log(e);
            throw new OutputStreamWriteException(e);
        }
    }
    private static void writeString(OutputStream outputStream, String str, boolean advancedLog) throws OutputStreamWriteException {
        DataOutputStream outStream = new DataOutputStream(outputStream);
        byte[] byteStr = str.getBytes();
        try {
            outStream.writeInt(byteStr.length);
            outStream.flush();
            outStream.write(byteStr);
            outStream.flush();
        } catch (IOException e) {
            if(advancedLog) Logger.log(e);
            throw new OutputStreamWriteException(e);
        }
    }
    private static void writeObject(OutputStream outputStream, Object obj, boolean advancedLog) throws OutputStreamWriteException, NotSerializableException, SpecializedStreamInstancingException {
        if (!(obj instanceof Serializable)) throw new NotSerializableException(obj.getClass().getCanonicalName());
        ObjectOutputStream outStream;
        try {
            outStream = new ObjectOutputStream(outputStream);
        } catch (IOException e) {
            if(advancedLog) Logger.log(e);
            throw new SpecializedStreamInstancingException(e);
        }
        try {
            outStream.writeObject(obj);
            outStream.flush();
        } catch (IOException e) {
            if(advancedLog) Logger.log(e);
            throw new OutputStreamWriteException(e);
        }
    }
    private static void writeFormattedImage(OutputStream outputStream, FormattedImage formattedImage, boolean advancedLog) throws OutputStreamWriteException {
        RawSerializer.sendString(outputStream,formattedImage.getFormatName(),advancedLog);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        MemoryCacheImageOutputStream imageStream = new MemoryCacheImageOutputStream(byteStream);
        try {
            ImageIO.write(formattedImage.getImage(),formattedImage.getFormatName(),imageStream);
        } catch (IOException exception) {
            if(advancedLog) Logger.log(exception);
            throw new OutputStreamWriteException(exception);
        }
        RawSerializer.sendString(outputStream,Base64.getEncoder().encodeToString(byteStream.toByteArray()));
    }
    private static void writeBytes(OutputStream outputStream, byte[] bytes, int offset, int length, boolean advancedLog) throws OutputStreamWriteException {
        byte[] finalByteArray = new byte[length];
        System.arraycopy(bytes, offset, finalByteArray, 0, length);
        String base64byteString = Base64.getEncoder().encodeToString(finalByteArray);
        RawSerializer.sendString(outputStream, base64byteString, advancedLog);
    }

    //Private Definitions: Input
    private static int readInt(InputStream inputStream, boolean advancedLog) throws InputStreamReadException {
        DataInputStream inStream = new DataInputStream(inputStream);
        try {
            return inStream.readInt();
        } catch (IOException e) {
            if(advancedLog) Logger.log(e);
            throw new InputStreamReadException(e);
        }
    }
    private static long readLong(InputStream inputStream, boolean advancedLog) throws InputStreamReadException {
        DataInputStream inStream = new DataInputStream(inputStream);
        try {
            return inStream.readLong();
        } catch (IOException e) {
            if(advancedLog) Logger.log(e);
            throw new InputStreamReadException(e);
        }
    }
    private static float readFloat(InputStream inputStream, boolean advancedLog) throws InputStreamReadException {
        DataInputStream inStream = new DataInputStream(inputStream);
        try {
            return inStream.readFloat();
        } catch (IOException e) {
            if(advancedLog) Logger.log(e);
            throw new InputStreamReadException(e);
        }
    }
    private static double readDouble(InputStream inputStream, boolean advancedLog) throws  InputStreamReadException {
        DataInputStream inStream = new DataInputStream(inputStream);
        try {
            return inStream.readDouble();
        } catch (IOException e) {
            if(advancedLog) Logger.log(e);
            throw new InputStreamReadException(e);
        }
    }
    private static boolean readBoolean(InputStream inputStream, boolean advancedLog) throws InputStreamReadException {
        DataInputStream inStream = new DataInputStream(inputStream);
        try {
            return inStream.readBoolean();
        } catch (IOException e) {
            if(advancedLog) Logger.log(e);
            throw new InputStreamReadException(e);
        }
    }
    private static String readString(InputStream inputStream, boolean advancedLog) throws InputStreamReadException {
        DataInputStream inStream = new DataInputStream(inputStream);
        int length;
        byte[] byteStr;
        try {
            length = inStream.readInt();
            if (length > 0) {
                byteStr = new byte[length];
                inStream.readFully(byteStr, 0, byteStr.length);
                return new String(byteStr);
            }else {
                return null;
            }
        } catch (IOException e) {
            if(advancedLog) Logger.log(e);
            throw new InputStreamReadException(e);
        }
    }
    private static Object readObject(InputStream inputStream, boolean advancedLog) throws InputStreamReadException, ClassNotFoundException, SpecializedStreamInstancingException {
        ObjectInputStream inStream;
        try {
            inStream = new ObjectInputStream(inputStream);
        }catch (IOException e){
            if(advancedLog) Logger.log(e);
            throw new SpecializedStreamInstancingException(e);
        }
        try{
            return inStream.readObject();
        }catch (IOException e){
            if(advancedLog) Logger.log(e);
            throw new InputStreamReadException(e);
        } catch (ClassNotFoundException e) {
            if(advancedLog) Logger.log(e);
            throw e;
        }
    }
    private static FormattedImage readFormattedImage(InputStream inputStream, boolean advancedLog) throws InputStreamReadException, CorruptedImageException {
        String formatName = RawSerializer.receiveString(inputStream);
        String base64image = RawSerializer.receiveString(inputStream);
        byte[] imageByte = Base64.getDecoder().decode(base64image);
        ByteArrayInputStream inStream = new ByteArrayInputStream(imageByte);
        try {
            return new FormattedImage(ImageIO.read(inStream),formatName);
        }catch (IOException e) {
            throw new CorruptedImageException(e);
        }
    }
    private static byte[] readBytes(InputStream inputStream, boolean advancedLog) throws InputStreamReadException {
        String base64byteString = RawSerializer.receiveString(inputStream, advancedLog);
        return Base64.getDecoder().decode(base64byteString);
    }
}