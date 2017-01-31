package com.jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by surik on 1/29/17
 */

public class SQLiteConnection {

    public static Connection Connector() {
        try {
            Class.forName("org.sqlite.JDBC");
//            String pathToDB = SQLiteConnection.class.getResource("/UniverDB.sqlite").toString();
            return DriverManager.getConnection("jdbc:sqlite::resource:UniverDB.sqlite");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
            //TODO handle exception
        }
    }

}
