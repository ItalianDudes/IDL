package it.italiandudes.idl.common;

import org.apache.commons.lang3.SystemUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
public enum TargetPlatform {
    LINUX("linux", "Linux", SystemUtils.IS_OS_LINUX && (SystemUtils.OS_ARCH.contains("x86") || SystemUtils.OS_ARCH.contains("amd64"))),
    LINUX_AARCH64("linux-aarch64", "Linux (ARM)", SystemUtils.IS_OS_LINUX),
    WINDOWS("windows", "Windows", SystemUtils.IS_OS_WINDOWS),
    MAC("mac", "Mac OSX", SystemUtils.IS_OS_MAC && (SystemUtils.OS_ARCH.contains("x86") || SystemUtils.OS_ARCH.contains("amd64"))),
    MAC_AARCH64("mac-aarch64", "Mac OSX (ARM)", SystemUtils.IS_OS_MAC);

    // Attributes
    private final String manifestTargetPlatform;
    private final String name;
    private final boolean isCurrentOS;

    // Constructor
    TargetPlatform(@NotNull final String manifestTargetPlatform, @NotNull final String name, final boolean isCurrentOS) {
        this.manifestTargetPlatform = manifestTargetPlatform;
        this.name = name;
        this.isCurrentOS = isCurrentOS;
    }

    // Static
    @Nullable
    public static TargetPlatform fromManifestTargetPlatform(@NotNull final String manifestTargetPlatform) {
        for (TargetPlatform targetPlatform : TargetPlatform.values()) {
            if (targetPlatform.manifestTargetPlatform.equalsIgnoreCase(manifestTargetPlatform)) {
                return targetPlatform;
            }
        }
        return null;
    }
    @Nullable
    public static TargetPlatform getCurrentPlatform() {
        for (TargetPlatform targetPlatform : TargetPlatform.values()) {
            if (targetPlatform.isCurrentOS()) return targetPlatform;
        }
        return null;
    }

    // Methods
    @NotNull
    public String getManifestTargetPlatform() {
        return manifestTargetPlatform;
    }
    @NotNull
    public String getName() {
        return name;
    }
    public boolean isCurrentOS() {
        return isCurrentOS;
    }
    @Override @NotNull
    public String toString() {
        return getName();
    }
}
