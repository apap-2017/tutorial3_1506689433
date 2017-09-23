package com.example.tutorial3.service;

import com.example.tutorial3.model.StudentModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AufaHR on 9/23/2017.
 */
public class InMemoryStudentService implements StudentService {

    private static List<StudentModel> studentList = new ArrayList<StudentModel>();

    @Override
    public StudentModel selectStudent(String npm) {
        for (StudentModel student : studentList) {
            if (student.getNpm().equals(npm)) {
                return student;
            }
        }

        return null;
    }

    @Override
    public List<StudentModel> selectAllStudents() {
        return studentList;
    }

    @Override
    public void addStudent(StudentModel student) {
        studentList.add(student);
    }

    @Override
    public void deleteStudent(String npm) throws Exception {
        for (StudentModel student : studentList) {
            if (student.getNpm().equals(npm)) {
                studentList.remove(student);
                return;
            }
        }

        throw new Exception("Student not found");
    }
}
