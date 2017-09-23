package com.example.tutorial3.service;

import com.example.tutorial3.model.StudentModel;

import java.util.List;

/**
 * Created by AufaHR on 9/23/2017.
 */
public interface StudentService {
    StudentModel selectStudent(String npm);

    List<StudentModel> selectAllStudents();

    void addStudent(StudentModel student);

    void deleteStudent(String npm) throws Exception;
}
