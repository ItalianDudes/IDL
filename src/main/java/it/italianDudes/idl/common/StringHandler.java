/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italiandudes.idl.common;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public final class StringHandler {

    //Constructors
    private StringHandler(){
        throw new UnsupportedOperationException("Can't instantiate this class!");
    }

    //Methods
    public static void sendString(OutputStream out, String str) throws IOException {
        RawSerializer.sendString(out,str);
    }

    public static String receiveString(InputStream in) throws IOException{
        return RawSerializer.receiveString(in);
    }

    public static String getStringBeforeChar(String str, char car){
        if(str==null)
            return null;
        char[] charSequence = str.toCharArray();
        for(int i=0;i<charSequence.length;i++){
            if(charSequence[i]==car)
                return str.substring(0,i);
        }
        return null;
    }

    public static String getStringAfterChar(String str, char car){
        if(str==null)
            return null;
        char[] charSequence = str.toCharArray();
        for(int i=0;i<charSequence.length;i++){
            if(charSequence[i]==car)
                try {
                    return str.substring(i+1,charSequence.length);
                }catch (IndexOutOfBoundsException exception){
                    return null;
                }
        }
        return null;
    }

    public static String getStackTrace(final Throwable throwable) {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter out = new PrintWriter(stringWriter, true);
        throwable.printStackTrace(out);
        return stringWriter.getBuffer().toString();
    }

    public static String removeCharsInString(String str, char car){
        StringBuilder stringBuilder = new StringBuilder();
        char[] strCharArray = str.toCharArray();
        for (char c : strCharArray) {
            if (c != car)
                stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static String removeCharsBeforeString(String str, char car){
        StringBuilder stringBuilder = new StringBuilder();
        char[] strCharArray = str.toCharArray();
        boolean endCar = false;
        for (char c : strCharArray) {
            if (endCar) {
                stringBuilder.append(c);
            } else if (c != car) {
                endCar = true;
                stringBuilder.append(c);
            }
        }
        return stringBuilder.toString();
    }

    public static int getOccurrencesFromString(String str, char car) {

        int occorrenze = 0;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == car)
                occorrenze++;
        }

        return occorrenze;
    }

    public static String[] parseString(String str){
        List<String> commandPipeline = new ArrayList<>();

        String[] comandoAsset = str.split(" ");

        int index=0;

        String buffer;

        while(index<comandoAsset.length){
            List<String> composition = new ArrayList<>();
            if(comandoAsset[index].contains("\"") && getOccurrencesFromString(comandoAsset[index],'\"')%2!=0){
                buffer = comandoAsset[index];
                buffer = buffer.replaceAll("\"","");
                composition.add(buffer);
                try {
                    do {
                        index++;
                        buffer = comandoAsset[index];
                        buffer = buffer.replaceAll("\"", "");
                        composition.add(buffer);
                    } while (!comandoAsset[index].contains("\""));
                }catch (ArrayIndexOutOfBoundsException outOfBoundsException){
                    if(Logger.isInitialized()) {
                        Logger.log("Error during splitting string: missing \"", new InfoFlags(true, false));
                    }else{
                        System.err.println("Error during splitting string: missing \"");
                    }
                    return null;
                }
                StringBuilder stringBuilder = new StringBuilder();
                for(String string : composition){
                    stringBuilder.append(string).append(" ");
                }
                String newStr = stringBuilder.toString();
                newStr = newStr.substring(0,newStr.length()-1);
                commandPipeline.add(newStr);
            }else if (comandoAsset[index].contains("\"") && getOccurrencesFromString(comandoAsset[index],'\"')%2==0){
                buffer = comandoAsset[index];
                buffer = buffer.replaceAll("\"","");
                commandPipeline.add(buffer);
            }else{
                commandPipeline.add(comandoAsset[index]);
            }
            index++;
        }
        return commandPipeline.toArray(new String[0]);
    }

}