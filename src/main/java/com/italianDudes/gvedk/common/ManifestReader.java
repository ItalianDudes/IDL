package com.italianDudes.gvedk.common;

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
    public static Attributes readJarManifest(Manifest manifest){
        return manifest.getMainAttributes();
    }

}
