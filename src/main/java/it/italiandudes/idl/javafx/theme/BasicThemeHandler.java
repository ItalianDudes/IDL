package it.italiandudes.idl.javafx.theme;

import javafx.scene.Scene;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public final class BasicThemeHandler {

    // Attributes
    private final String lightThemeExternalForm;
    private final String darkThemeExternalForm;
    private String currentTheme = null;

    // Constructor
    private BasicThemeHandler(@NotNull final String lightThemeExternalForm, @NotNull final String darkThemeExternalForm) {
        this.lightThemeExternalForm = lightThemeExternalForm;
        this.darkThemeExternalForm = darkThemeExternalForm;
    }

    // Instance
    private static BasicThemeHandler INSTANCE;
    public static BasicThemeHandler initInstance(@NotNull final String lightThemeExternalForm, @NotNull final String darkThemeExternalForm) {
        if (INSTANCE != null) return INSTANCE;
        INSTANCE = new BasicThemeHandler(lightThemeExternalForm, darkThemeExternalForm);
        return INSTANCE;

    }
    public static BasicThemeHandler getInstance() {
        if (INSTANCE == null) throw new RuntimeException("Must call initInstance() first!");
        return INSTANCE;
    }

    // Methods
    public void setCurrentTheme(@NotNull final BasicTheme theme) {
        if (theme == BasicTheme.DARK) currentTheme = darkThemeExternalForm;
        else if (theme == BasicTheme.LIGHT) currentTheme = lightThemeExternalForm;
    }

    // Config Theme
    public void loadTheme(@NotNull final Scene scene, @NotNull final BasicTheme theme) {
        if (currentTheme == null) setCurrentTheme(theme);
        scene.getStylesheets().clear();
        scene.getStylesheets().add(currentTheme);
    }

    // Light Theme
    public void loadLightTheme(@NotNull final Scene scene) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(lightThemeExternalForm);
    }

    // Dark Theme
    public void loadDarkTheme(@NotNull final Scene scene) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(darkThemeExternalForm);
    }
}
