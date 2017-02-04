package com.jdbc.dao;

import com.dto.Student;

import java.util.List;

/**
 * Created by surik on 2/4/17
 */
public interface StudentsDAO {

    void insert(String firstName,String lastName);

    List<Student> getAllStudents();
}
