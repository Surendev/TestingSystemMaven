package com.jdbc.dao;

import com.dto.Student;

import java.util.List;

/**
 * Created by surik on 2/4/17
 */
public interface StudentsDAO {

    int addNewStudent(String firstName, String lastName, int course, String group);

    List<Student> getAllStudents();

}
