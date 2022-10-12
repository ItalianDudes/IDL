package com.italianDudes.idl.common;

import java.io.File;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

@SuppressWarnings("unused")
public final class ManifestReader {

    //Constructors
    private ManifestReader(){
        throw new UnsupportedOperationException("Can't instantiate this class!");
    }

    //Methods
    public static Attributes readJarManifest(String jarPath) throws IOException {
        return readJarManifest(new JarFile(jarPath));
    }
    public static Attributes readJarManifest(File jar) throws IOException {
        return readJarManifest(new JarFile(jar));
    }
    public static Attributes readJarManifest(JarFile jar) throws IOException {
        return readJarManifest(jar.getManifest());
    }
    public static String getValue(Attributes attributes, Attributes.Name valueName){
        return attributes.getValue(valueName);
    }
    public static String getValue(Attributes attributes, String valueName){
        return getValue(attributes,new Attributes.Name(valueName));
    }
    public static boolean containsValue(Attributes attributes, Attributes.Name valueName){
        return containsValue(attributes, valueName.toString());
    }
    public static boolean containsValue(Attributes attributes, String valueName){
        return attributes.containsValue(valueName);
    }
    public static boolean containsKey(Attributes attributes, Attributes.Name keyName){
        return attributes.containsKey(keyName);
    }
    public static boolean containsKey(Attributes attributes, String keyName){
        return containsKey(attributes, new Attributes.Name(keyName));
    }
    public static Attributes readJarManifest(Manifest manifest){
        return manifest.getMainAttributes();
    }

}
