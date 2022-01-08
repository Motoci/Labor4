package com;

import com.Repository.*;
import com.Controller.*;
import com.ConsoleView.ConsoleView;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        StudentRepository studentRepo = new StudentRepository("student.json");
        studentRepo.readFromFile();
        TeacherRepository teacherRepo = new TeacherRepository("teacher.json");
        teacherRepo.readFromFile();
        CourseRepository courseRepo = new CourseRepository("course.json");
        courseRepo.readFromFile();
        StudentController studentController = new StudentController(studentRepo);
        TeacherController teacherController = new TeacherController(teacherRepo, courseRepo, studentRepo);
        CourseController courseController = new CourseController(courseRepo, studentRepo, teacherRepo);
        ConsoleView userInterface = new ConsoleView(studentController, courseController, teacherController);
        userInterface.start();

    }

}

