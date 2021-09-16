package org.lemandog.jdbc;
import org.lemandog.Frames.MenuSettings;
import org.lemandog.Utility;

import java.sql.*;

public class Control {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/starfighter";
    static final String USER = "pilot";
    static final String PASS = "";
    static Connection conn = null;
    static Statement stmt = null;
    public static void main() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS SETTINGS " +
                    "(ID INTEGER NOT NULL AUTO_INCREMENT, " +
                    " VOLUMEM INTEGER, " +
                    " VOLUMES INTEGER, " +
                    " COMIC BOOLEAN, " +
                    " DEBUG BOOLEAN, " +
                    " PRIMARY KEY ( ID ))";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE IF NOT EXISTS SAVE " +
                    "(ID INTEGER NOT NULL AUTO_INCREMENT, " +
                    " LEVEL INTEGER, "+
                    " PRIMARY KEY ( ID ))";
            stmt.executeUpdate(sql);
        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    public static void saveSettings(int volM, int volS, boolean comic, boolean debug) {
        String sql = "UPDATE SETTINGS SET" +
                " VOLUMEM = "+volM+", VOLUMES = "+volS+", COMIC = "+comic+", DEBUG="+debug+" WHERE ID =1";
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String loadSettings(int i){
        try {
            String sql = "SELECT VOLUMEM, VOLUMES, COMIC, DEBUG FROM SETTINGS WHERE ID = 1";
            Utility.debugOutput("(LOAD)SQL: " + sql);
            ResultSet res = stmt.executeQuery(sql);
            res.first();
            switch (i){
                case 1:{return res.getNString("VOLUMEM");}
                case 2:{return res.getNString("VOLUMES");}
                case 3:{return res.getNString("COMIC");}
                case 4:{return res.getNString("DEBUG");}
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resetSave();
            loadSettings(i);
        }
        return "";
    }
    public static String currentSave(){
        try {
            String sql = "SELECT LEVEL FROM SAVE WHERE ID = 1";
            Utility.debugOutput("(LOAD)SQL: " + sql);
            ResultSet res = stmt.executeQuery(sql);
            res.first();
            return res.getNString("LEVEL");
        } catch (SQLException throwables) {
            if (MenuSettings.showDebug){throwables.printStackTrace();}
        }
        resetSave();
        return "6";
    }
    public static void resetSave(){
        try {
            String sql = "UPDATE SAVE SET LEVEL=6 WHERE ID=1";
            Utility.debugOutput("(RESET)SQL: " + sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            if (MenuSettings.showDebug){e.printStackTrace();}
        }
    }
    public static void progressLevel(int i){
        if (i<=0){return;}
        try {
            String sql = "UPDATE SAVE SET LEVEL="+i+" WHERE ID=1";
            Utility.debugOutput("(UPDATE)SQL: " + sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            if (MenuSettings.showDebug){e.printStackTrace();}
        }
    }
}
