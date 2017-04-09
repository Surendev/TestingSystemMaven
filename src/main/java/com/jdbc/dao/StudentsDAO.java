package com.jdbc.dao;

import com.dto.Student;

import java.util.List;

/**
 * Created by surik on 2/4/17
 */
public interface StudentsDAO {

    int addOrUpdateStudent(String id,String firstName, String lastName, Integer course, String group, boolean update);

    List<Student> getAllStudents();

    Student getStudentById(String id);

}
