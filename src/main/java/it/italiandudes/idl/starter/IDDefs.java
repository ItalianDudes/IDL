package it.italiandudes.idl.starter;

import it.italiandudes.idl.IDL;
import it.italiandudes.idl.common.TargetPlatform;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.net.URISyntaxException;

@SuppressWarnings("unused")
public final class IDDefs {

    // Jar App Position
    public static final String JAR_POSITION;
    static {
        try {
            JAR_POSITION = new File(IDL.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    // Current Platform
    @Nullable public static final TargetPlatform CURRENT_PLATFORM = TargetPlatform.getCurrentPlatform();
}
