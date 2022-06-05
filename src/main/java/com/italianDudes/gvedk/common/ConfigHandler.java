package com.italianDudes.gvedk.common;

import com.italianDudes.gvedk.common.exceptions.fileIO.ConfigFormatException;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public final class ConfigHandler {

    //Constructors
    private ConfigHandler(){
        throw new UnsupportedOperationException("Can't instantiate this class!");
    }

    //Methods
    public static ArrayList<Property> readConfigs(String configFilePath) throws ConfigFormatException {
        return readConfigs(new File(configFilePath));
    }
    public static ArrayList<Property> readConfigs(File configFile) throws ConfigFormatException {

        ArrayList<Property> configs = new ArrayList<>();

        Scanner inFile;
        try {
            inFile = new Scanner(configFile);
        }catch (FileNotFoundException e){
            Logger.log(e);
            return null;
        }

        String buffer;
        String[] parsedBuffer;
        while (inFile.hasNext()){
            buffer = inFile.nextLine();
            if(buffer.charAt(0)=='#'){
                configs.add(new Property("#",StringHandler.getStringAfterChar(buffer,'#')));
            }else{
                parsedBuffer = buffer.split("=");
                if(parsedBuffer.length>=2){
                    configs.add(new Property(StringHandler.removeCharsInString(parsedBuffer[0],' '),StringHandler.removeCharsBeforeString(parsedBuffer[1],' ')));
                }else{
                    inFile.close();
                     throw new ConfigFormatException("Invalid format in "+configFile.getAbsolutePath());
                }
            }
        }
        inFile.close();

        return configs;

    }
    public static boolean writeConfigs(ArrayList<Property> configs, String configFilePath){
        return writeConfigs(configs,new File(configFilePath));
    }
    public static boolean writeConfigs(ArrayList<Property> configs, File configFile){
        BufferedWriter outFile;
        try {
            outFile = new BufferedWriter(new FileWriter(configFile));
        }catch (IOException e){
            Logger.log(e);
            return false;
        }
        for (Property config : configs) {
            try {

                outFile.append(config.toString()).append("\n");
                outFile.flush();
            } catch (IOException e) {
                Logger.log(e);
                try {
                    outFile.close();
                } catch (IOException ex) {
                    Logger.log(ex);
                }
                return false;
            }
        }
        try{
            outFile.close();
        }catch (IOException e){
            Logger.log(e);
            return false;
        }
        return true;
    }

}
