/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italiandudes.idl.common;

/**
 *
 * @deprecated Use Apache Commons Lang Instead
 * */
@SuppressWarnings("unused") @Deprecated
public final class OSUtils {
    public static String getOsName() {
        return System.getProperty("os.name");
    }
    public static String getOsArch(){
        return System.getProperty("os.arch");
    }
    public static boolean isWindows(){
        return getOsName().contains("Windows");
    }
    public static boolean isLinux(){
        return getOsName().contains("Linux");
    }
    public static boolean isMacOSX(){
        return getOsName().contains("Mac OS X");
    }
    public static boolean isSunOS(){
        return getOsName().contains("SunOS");
    }
    public static boolean isFreeBSD(){
        return getOsName().contains("FreeBSD");
    }
}