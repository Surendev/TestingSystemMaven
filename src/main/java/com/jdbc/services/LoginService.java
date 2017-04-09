package com.jdbc.services;


import com.dto.Student;
import com.jdbc.dao.LoginDAO;
import com.jdbc.mappers.StudentRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by surik on 1/29/17
 * This class provides communication with database
 * for handling users right login
 */

public class LoginService implements LoginDAO {

    private static final String  SAP = "R_PetRosYan";

    private MessageDigest digest;
    private JdbcTemplate jdbc;

    public void setDataSource(DataSource dataSource) throws NoSuchAlgorithmException {
        jdbc = new JdbcTemplate(dataSource);
        digest = MessageDigest.getInstance("SHA-256");
    }

    public boolean login(String login, String password) {
        String query = "SELECT * FROM students WHERE login=?";
        Student student = null;
        try {
            student = jdbc.queryForObject(query, new Object[]{login}, new StudentRowMapper());
        } catch (EmptyResultDataAccessException e) {
            System.err.println("Not found");
            System.out.println(e.getMessage());
        }
        return student != null /*&& student.getPassword().equals(password)*/;
    }
}
