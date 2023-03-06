/*
 *  Copyright (C) 2022 ItalianDudes
 *  Software distributed under the GPLv3 license
 */
package it.italiandudes.idl.common;

import java.io.File;
import java.sql.*;

@SuppressWarnings("unused")
public final class SQLiteHandler {

    //Constructors
    private SQLiteHandler(){
        throw new UnsupportedOperationException("Can't instantiate this class!");
    }

    //Methods
    public static Connection openConnection(File fileDB){
        return openConnection(fileDB.getAbsolutePath(), false);
    }
    public static Connection openConnection(File fileDB, boolean readOnly){
        return openConnection(fileDB.getAbsolutePath(), readOnly);
    }
    public static Connection openConnection(String dbPath){
        return openConnection(dbPath, false);
    }
    public static Connection openConnection(String dbPath, boolean readOnly){

        if(dbPath==null)
            return null;
        File fileChecker = new File(dbPath);
        try{
            Connection dbConnection = DriverManager.getConnection("jdbc:sqlite:"+dbPath);
            dbConnection.setReadOnly(readOnly);
            return dbConnection;
        }catch (SQLException e){
            if(Logger.isInitialized()){
                Logger.log(e);
            }else{
                e.printStackTrace();
            }
            return null;
        }
    }
    public static ResultSet readDataFromDB(Connection dbConnection, String query){

        try {

            if(dbConnection == null || dbConnection.isClosed())
                return null;

            return dbConnection.createStatement().executeQuery(query);

        }catch (SQLException e){
            if(Logger.isInitialized()){
                Logger.log(e);
            }else{
                e.printStackTrace();
            }
            return null;
        }
    }
    public static PreparedStatement prepareDataWriteIntoDB(Connection dbConnection, String query){

        try {
            if(dbConnection == null || dbConnection.isClosed() || dbConnection.isReadOnly())
                return null;

            return dbConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        }catch (SQLException e){
            if(Logger.isInitialized()) {
                Logger.log(e);
            }else{
                e.printStackTrace();
            }
            return null;
        }
    }
    public static void closeConnection(Connection dbConnection){

        try {
            if(dbConnection != null && !dbConnection.isClosed())
                dbConnection.close();
        }catch (SQLException e){
            if(Logger.isInitialized()){
                Logger.log(e);
            }else{
                e.printStackTrace();
            }
        }
    }

}
