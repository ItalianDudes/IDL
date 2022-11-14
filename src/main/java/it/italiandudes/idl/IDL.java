/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italiandudes.idl;

public final class IDL {

    //Constructors
    private IDL() {
        throw new UnsupportedOperationException("Can't instantiate this class!");
    }

    //Project Constants
    public static final class Defs {

        private Defs() {
            throw new UnsupportedOperationException("Can't instantiate this class!");
        }

        //PATHS
        public static final String LOG_DIR = "logs/";
        public static final String LOG_LATEST_FILE = LOG_DIR + "latest.log";

        //General Data Communication
        public static final int DEFAULT_CONNECTION_TIMEOUT = 10000; //Expressed in milliseconds

    }
}