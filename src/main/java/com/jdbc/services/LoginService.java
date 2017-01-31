package com.jdbc.services;

import com.jdbc.connection.SQLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by surik on 1/29/17
 */
public class LoginService {

    private Connection sqliteConnection;

    public LoginService() {
        sqliteConnection = SQLiteConnection.Connector();
        if(sqliteConnection == null){
            System.out.println("DB is not connected");
            System.exit(1);
        }
    }

    public boolean isConnected(){
        try {
            return !sqliteConnection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public boolean  login(String login, String password) throws SQLException {
        String query = "SELECT * FROM students WHERE login=? and password=?";
        try (PreparedStatement preparedStatement = sqliteConnection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery()) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }


    }
}
