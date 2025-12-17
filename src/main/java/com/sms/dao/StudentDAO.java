package com.sms.dao;

import com.sms.model.Student;
import java.util.List;

public interface StudentDAO {
    boolean addStudent(Student student);

    boolean updateStudent(Student student);

    boolean deleteStudent(int studentId);

    Student getStudentById(int studentId);

    Student getStudentByIdCard(String idCardNo);

    List<Student> getAllStudents();

    List<Student> getStudentsWithPendingFees();

    List<Student> getSportsStudents();
}
