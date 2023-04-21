package it.italiandudes.idl.common;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

@SuppressWarnings({"unused", "Duplicates"})
public class JarHandler {

    // Constructors
    private JarHandler(){
        throw new UnsupportedOperationException("Can't instantiate this class!");
    }

    // Methods
    public static void copyFileFromJar(@NotNull File jarFilePointer, @NotNull String filePathInJar, @NotNull File destPath) throws IOException {
        if (!jarFilePointer.exists()) {
            throw new FileNotFoundException("The path \"" + jarFilePointer.getAbsolutePath() + "\" doesn't exist");
        }

        // Flipping backslashes
        filePathInJar = filePathInJar.replace("\\", "/");

        // Remove useless slashes
        if (filePathInJar.startsWith("/")) {
            filePathInJar = filePathInJar.substring(1);
        }
        if (filePathInJar.endsWith("/")) {
            filePathInJar = filePathInJar.substring(0, filePathInJar.length()-1);
        }

        JarFile jarFile;
        try {
            jarFile = new JarFile(jarFilePointer);
        } catch (IOException e) {
            throw new IOException("Can't open jar file at path \""+jarFilePointer.getAbsolutePath()+"\"");
        }

        JarEntry entry = jarFile.getJarEntry(filePathInJar);
        if (entry != null) {
            Path destination = destPath.toPath();
            InputStream inStream = null;
            try {
                inStream = jarFile.getInputStream(entry);
                Files.copy(inStream, destination, StandardCopyOption.REPLACE_EXISTING);
                inStream.close();
            }catch (IOException e) {
                try {
                    if (inStream != null) inStream.close();
                }catch (Exception ignored){}
                throw new IOException("An error has occurred on file copying");
            }
        }
    }
    public static void copyDirectoryFromJar(@NotNull File jarFilePointer, @NotNull String directoryPathInJar, @NotNull File destPath, boolean extractDirectory) throws IOException {
        if (!jarFilePointer.exists()) {
            throw new FileNotFoundException("The path \""+jarFilePointer.getAbsolutePath()+"\" doesn't exist");
        }

        // Flipping backslashes
        directoryPathInJar = directoryPathInJar.replace("\\", "/");

        // Remove useless slashes
        if (directoryPathInJar.startsWith("/")) {
            directoryPathInJar = directoryPathInJar.substring(1);
        }
        if (directoryPathInJar.endsWith("/")) {
            directoryPathInJar = directoryPathInJar.substring(0, directoryPathInJar.length()-1);
        }

        // If true, allows to copy the entire folder, not only the content
        if (!extractDirectory) {
            if (directoryPathInJar.contains("/")) {
                String[] splitJarFolderPath = directoryPathInJar.split("/");
                destPath = new File(destPath.getAbsolutePath()+'/'+splitJarFolderPath[splitJarFolderPath.length - 1]);
            } else {
                destPath = new File(destPath.getAbsolutePath()+'/' + directoryPathInJar);
            }
        }

        JarFile jarFile;
        try {
            jarFile = new JarFile(jarFilePointer);
        } catch (IOException e) {
            throw new IOException("Can't open jar file at path \""+jarFilePointer.getAbsolutePath()+"\"");
        }

        Enumeration<JarEntry> entries = jarFile.entries();

        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().startsWith(directoryPathInJar + "/")) {
                Path destination = Paths.get(destPath.getAbsolutePath(), entry.getName().substring(directoryPathInJar.length() + 1));
                if (entry.isDirectory()) {
                    try {
                        Files.createDirectories(destination);
                    }catch (IOException e) {
                        try {
                            jarFile.close();
                        }catch (Exception ignored){}
                        throw new IOException("An error has occurred while creating directory tree destination \""+destination+"\"");
                    }
                } else {
                    InputStream inStream = null;
                    try {
                        inStream = jarFile.getInputStream(entry);
                        Files.copy(inStream, destination, StandardCopyOption.REPLACE_EXISTING);
                        inStream.close();
                    } catch (IOException e) {
                        try {
                            if (inStream != null) inStream.close();
                        }catch (Exception ignored){}
                        try {
                            jarFile.close();
                        }catch (Exception ignored){}
                        throw new IOException("An error has occurred on file copying");
                    }
                }
            }
        }
        jarFile.close();
    }


    // Manifest Reader
    public static final class ManifestReader {

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
}
