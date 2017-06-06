package com.jdbc.services;

import com.dto.Student;
import com.jdbc.dao.StudentsDAO;
import com.jdbc.mappers.StudentRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by surik on 2/4/17
 */
public class StudentsService implements StudentsDAO{

    private JdbcTemplate jdbc;

    public void setDataSource(DataSource dataSource) {
        jdbc = new JdbcTemplate(dataSource);
    }


    @Override
    public Student getStudentById(String id) {
        String query = "SELECT * FROM students WHERE id=?";
        return jdbc.queryForObject(query, new Object[]{id},new StudentRowMapper());
    }

    @Override
    public List<String> getGroups() {
        final String query = "SELECT DISTINCT \"group\" FROM students";
        return jdbc.query(query,(resultSet, i) -> resultSet.getString(1));
    }

    @Override
    public int addOrUpdateStudent(String id,String firstName, String lastName, Integer course, String group, boolean update) {
        StringBuilder query;
        if(!update) {
            query = new StringBuilder("INSERT INTO students(first_name,last_name,")
                    .append("course,'group', rating) VALUES(?,?,?,?,?)");
            return jdbc.update(query.toString(), firstName, lastName, course, group, 0);
        }else{
            query = new StringBuilder("UPDATE students SET ");
            query   .append(firstName.equals("") ? "" : "first_name='" + firstName + "'")
                    .append(lastName.equals("") ? "" :  (query.charAt(query.length()-1)!=' '? "," : "") + "last_name='" + lastName + "'")
                    .append(course==null ? "" : (query.charAt(query.length()-1)!=' '? "," : "" )+ "course=" + course )
                    .append(group.equals("") ? "" : (query.charAt(query.length()-1)!=' '? "," : "") + "'group'='" + group + "'")
                    .append(" WHERE id=" + id);
            return jdbc.update(query.toString());
        }


    }

    @Override
    public List<Student> getAllStudents() {
        String query = "SELECT * FROM students";
        List<Student> studentsList = jdbc.query(query, new StudentRowMapper());
        return studentsList.size() == 0 ? null : studentsList;
    }
}
