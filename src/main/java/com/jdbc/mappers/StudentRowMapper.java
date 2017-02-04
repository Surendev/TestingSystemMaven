package com.jdbc.mappers;

import com.dto.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by surik on 2/4/17
 */
public class StudentRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet resultSet, int i) throws SQLException {
        Student student = new Student();
        student.setFirstName(resultSet.getString("first_name"));
        student.setLastName(resultSet.getString("last_name"));
        student.setLogin(resultSet.getString("login"));
        student.setPassword(resultSet.getString("password"));
        student.setCourse(resultSet.getInt("course"));
        student.setGroup(resultSet.getString("group"));
        return student;
    }
}
