package it.italiandudes.idl.starter;

import it.italiandudes.idl.common.TargetPlatform;
import it.italiandudes.idl.handler.JarHandler;
import it.italiandudes.idl.logger.InfoFlags;
import it.italiandudes.idl.logger.Logger;
import org.apache.commons.lang3.SystemUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.jar.Attributes;

@SuppressWarnings("unused")
public final class IDMain {

    // Launcher Class Loader
    @Nullable private static ClassLoader launcherClassLoader = null;
    @Nullable public static ClassLoader getLauncherClassLoader() {
        return launcherClassLoader;
    }
    public static void setLauncherClassLoader(@NotNull final ClassLoader launcherClassLoader) {
        if (IDMain.launcherClassLoader == null) IDMain.launcherClassLoader = launcherClassLoader;
    }
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isStartedFromLauncher() {
        return launcherClassLoader != null;
    }

    // Methods
    public static void defaultJ21Main() {
        defaultJ21Main(null);
    }
    public static void defaultJ21Main(@Nullable final String LOGGER_CONTEXT) {

        // Setting Charset to UTF-8
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true, StandardCharsets.UTF_8));

        // Initializing the logger
        try {
            Logger.init();
            Logger.log("Logger initialized!", LOGGER_CONTEXT);
        } catch (IOException e) {
            Logger.log("An error has occurred during Logger initialization, exit...", LOGGER_CONTEXT);
            return;
        }

        // Configure the shutdown hooks
        Logger.log("Configuring Shutdown Hooks...", LOGGER_CONTEXT);
        Runtime.getRuntime().addShutdownHook(new Thread(Logger::close));
        Logger.log("Shutdown Hooks configured!", LOGGER_CONTEXT);

        // Check Java Version
        Logger.log("Verifying for Java 21...", LOGGER_CONTEXT);
        if (!SystemUtils.IS_JAVA_21) {
            Logger.log("The current java version is wrong, to run this jar you need Java 21!", new InfoFlags(true, true, true, true), LOGGER_CONTEXT);
            if (!isStartedFromLauncher()) {
                Logger.close();
                System.exit(-1);
            }
            return;
        }
        Logger.log("Java 21 Verified!", LOGGER_CONTEXT);

        // Check OS
        Logger.log("Verifying OS...", LOGGER_CONTEXT);
        Logger.log("OS Name: " + SystemUtils.OS_NAME, LOGGER_CONTEXT);
        Logger.log("OS Arch: " + SystemUtils.OS_ARCH, LOGGER_CONTEXT);
        Logger.log("Current OS Platform: " + (IDDefs.CURRENT_PLATFORM != null ? IDDefs.CURRENT_PLATFORM.getName() : "NOT RECOGNIZED"), LOGGER_CONTEXT);
        if (IDDefs.CURRENT_PLATFORM == null) {
            Logger.log("WARNING: Current OS Platform not recognized! An attempt to start the app will be done anyway.", new InfoFlags(true, false, false, true), LOGGER_CONTEXT);
        }
        try {
            Attributes manifestAttributes = JarHandler.ManifestReader.readJarManifest(IDDefs.JAR_POSITION);
            @Nullable String manifestTargetPlatform = JarHandler.ManifestReader.getValue(manifestAttributes, "Target-Platform");
            if (manifestTargetPlatform == null) {
                Logger.log("Target-Platform not specified in jar manifest, this jar shouldn't be used for release.", new InfoFlags(false, false, false, true), LOGGER_CONTEXT);
            } else {
                @Nullable TargetPlatform targetPlatform = TargetPlatform.fromManifestTargetPlatform(manifestTargetPlatform);
                if (targetPlatform == null) {
                    Logger.log("Target-Platform provided \"" + manifestTargetPlatform + "\" not recognized, this jar shouldn't be used for release.", new InfoFlags(false, false, false, true), LOGGER_CONTEXT);
                } else {
                    Logger.log("Jar Target-Platform: " + targetPlatform.getName(), LOGGER_CONTEXT);
                    if (IDDefs.CURRENT_PLATFORM != null && !targetPlatform.isCurrentOS()) {
                        Logger.log("Target-Platform \"" + targetPlatform.getName() + "\" incompatible with the current OS Platform!", new InfoFlags(true, true, true, true), LOGGER_CONTEXT);
                        if (!isStartedFromLauncher()) {
                            Logger.close();
                            System.exit(-1);
                        }
                        return;
                    }
                }
            }
        } catch (IOException e) {
            Logger.log("An error has occurred while attempting to read jar manifest!", new InfoFlags(true, true, true, true), LOGGER_CONTEXT);
            if (!isStartedFromLauncher()) {
                Logger.close();
                System.exit(-1);
            }
            return;
        }
        Logger.log("Startup Checks Completed!", LOGGER_CONTEXT);
    }
}
