/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package com.italianDudes.gvedk;

public final class GVEDK {

    private GVEDK() {
        throw new UnsupportedOperationException("Can't instantiate this class!");
    }

    //Project Constants
    public static final class Defs {

        private Defs() {
            throw new UnsupportedOperationException("Can't instantiate this class!");
        }

        //PATHS
        public static final String EXTENSIONS_DIR = "extensions/";
        public static final String LOG_DIR = "logs/";
        public static final String LOG_LATEST_FILE = LOG_DIR + "latest.log";

        //General Data Communication
        public static final int DEFAULT_CONNECTION_TIMEOUT = 60000; //Expressed in milliseconds

    }
}