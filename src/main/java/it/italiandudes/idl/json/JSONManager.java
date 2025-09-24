package it.italiandudes.idl.json;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@SuppressWarnings("unused")
public final class JSONManager {
    @NotNull
    public static JSONObject readJSON(@NotNull final File file) throws JSONException, IOException {
        return readJSON(new Scanner(file, StandardCharsets.UTF_8));
    }
    @NotNull
    public static JSONObject readJSON(@NotNull final InputStream inputStream) throws JSONException {
        return readJSON(new Scanner(inputStream, StandardCharsets.UTF_8));
    }
    @NotNull
    private static JSONObject readJSON(@NotNull final Scanner fileReader) {
        StringBuilder buffer = new StringBuilder();
        while (fileReader.hasNext()) {
            buffer.append(fileReader.nextLine()).append('\n');
        }
        fileReader.close();
        return new JSONObject(buffer.toString());
    }
    public static void writeJSON(@NotNull final JSONObject json, @NotNull final File destination) throws IOException {
        FileWriter writer = new FileWriter(destination);
        writer.append(json.toString(2));
        writer.close();
    }
}
