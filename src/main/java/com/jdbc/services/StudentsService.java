package com.jdbc.services;

import com.dto.Student;
import com.jdbc.dao.StudentsDAO;
import com.jdbc.mappers.StudentRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
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
    public int addNewStudent(String firstName, String lastName, int course, String group) {
        String sql = "INSERT INTO students(first_name,last_name," +
                "course,'group', rating, login, password) VALUES(?,?,?,?,?,?,?)";

        return jdbc.update(sql,firstName,lastName,course,group,0,"artyom","asdasdasd");
    }

    @Override
    public List<Student> getAllStudents() {
        String query = "SELECT * FROM students";
        List<Student> studentsList = jdbc.query(query, new StudentRowMapper());
        return studentsList.size() == 0 ? null : studentsList;
    }
}
