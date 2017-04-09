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
        student.setMiddleName(resultSet.getString("middle_name"));
        student.setCourse(resultSet.getInt("course"));
        student.setGroup(resultSet.getString("group"));
        student.setLastRating(resultSet.getDouble("last_rating"));
        student.setRating(resultSet.getDouble("rating"));
        student.setPassedFirstExam(resultSet.getBoolean("passed_first_exam"));
        student.setPassedSecondExam(resultSet.getBoolean("passed_second_exam"));
        student.setPassedLastExam(resultSet.getBoolean("passed_last_exam"));

        return student;
    }
}
