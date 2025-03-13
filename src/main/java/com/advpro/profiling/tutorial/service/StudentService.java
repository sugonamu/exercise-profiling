package com.advpro.profiling.tutorial.service;

import com.advpro.profiling.tutorial.model.Student;
import com.advpro.profiling.tutorial.model.StudentCourse;
import com.advpro.profiling.tutorial.repository.StudentCourseRepository;
import com.advpro.profiling.tutorial.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author muhammad.khadafi
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public List<StudentCourse> getAllStudentsWithCourses() {
        List<Student> students = studentRepository.findAll();

        // Fetch all student courses in one query instead of per student
        List<StudentCourse> allStudentCourses = studentCourseRepository.findAll();

        Map<Long, Student> studentMap = students.stream()
                .collect(Collectors.toMap(Student::getId, Function.identity()));

        List<StudentCourse> studentCourses = new ArrayList<>();
        for (StudentCourse studentCourse : allStudentCourses) {
            Student student = studentMap.get(studentCourse.getStudent().getId());
            if (student != null) {
                studentCourse.setStudent(student);
                studentCourses.add(studentCourse);
            }
        }
        return studentCourses;
    }


    public Optional<Student> findStudentWithHighestGpa() {
        return studentRepository.findStudentWithHighestGpa();
    }


    public String joinStudentNames() {
        List<Student> students = studentRepository.findAll();
        if (students.isEmpty()) {
            return ""; // handle empty case
        }

        StringBuilder result = new StringBuilder();
        for (Student student : students) {
            result.append(student.getName()).append(", ");
        }

        // Remove the last ", "
        result.setLength(result.length() - 2);
        return result.toString();
    }
}