package it.italiandudes.idl.common;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@SuppressWarnings("unused")
public class JarHandler {

    // Constructors
    private JarHandler(){
        throw new UnsupportedOperationException("Can't instantiate this class!");
    }

    // Methods
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean copyFolderFromJar(@NotNull String jarPath, String folderName, File destFolder, boolean replaceIfDestExist) throws IOException {
        if (!destFolder.exists())
            destFolder.mkdirs();

        byte[] buffer = new byte[1024];

        File fullPath;
        try {
            if (!jarPath.startsWith("file"))
                jarPath = "file://" + jarPath;

            fullPath = new File(new URI(jarPath));
        } catch (URISyntaxException e) {
            Logger.log(e);
            return false;
        }

        ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(fullPath.toPath()));

        ZipEntry entry;
        while ((entry = zipInputStream.getNextEntry()) != null) {
            if (!entry.getName().startsWith(folderName + '/'))
                continue;

            String fileName = entry.getName();

            if (fileName.charAt(fileName.length() - 1) == '/') {
                File file = new File(destFolder + File.separator + fileName);
                if (file.isFile()) {
                    file.delete();
                }
                file.mkdirs();
                continue;
            }

            File file = new File(destFolder + File.separator + fileName);
            if (replaceIfDestExist && file.exists()) continue;

            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();

            if (!file.exists()) file.createNewFile();
            FileOutputStream outStream = new FileOutputStream(file);

            int len;
            while ((len = zipInputStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, len);
            }
            outStream.close();
        }

        zipInputStream.closeEntry();
        zipInputStream.close();
        return true;
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
