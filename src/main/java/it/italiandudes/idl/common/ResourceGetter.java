package it.italiandudes.idl.common;

import it.italiandudes.idl.IDL;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

@SuppressWarnings("unused")
public final class ResourceGetter {
    public static URL getResource(@NotNull final String resourceConst) {
        return Objects.requireNonNull(IDL.class.getResource(resourceConst));
    }
    public static InputStream getResourceAsStream(@NotNull final String resourceConst) {
        return Objects.requireNonNull(IDL.class.getResourceAsStream(resourceConst));
    }
}
