package com.ConsoleView;

import com.Entities.Student;
import com.Entities.Course;
import com.Entities.Teacher;
import com.Controller.StudentController;
import com.Controller.CourseController;
import com.Controller.TeacherController;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class ConsoleView {

    private StudentController studentController;
    private CourseController courseController;
    private TeacherController teacherController;

    public ConsoleView(StudentController _studentController, CourseController _courseController, TeacherController _teacherController) {
        this.studentController = _studentController;
        this.courseController = _courseController;
        this.teacherController = _teacherController;
    }

    public StudentController getStudentController() {
        return studentController;
    }

    public void setStudentController(StudentController _studentController) {
        this.studentController = _studentController;
    }

    public CourseController getCourseController() {
        return courseController;
    }

    public void setCourseController(CourseController _courseController) {
        this.courseController = _courseController;
    }

    public TeacherController getTeacherController() {
        return teacherController;
    }

    public void setTeacherController(TeacherController _teacherController) {
        this.teacherController = _teacherController;
    }

    /**
     * main Menu
     */
    public void showMenu() {
        System.out.println("""
                1 - Add Student
                2 - Add Teacher
                3 - Add Course
                4 - Delete Course
                5 - Sort Course
                6 - Sort Students
                7 - Available number of places for each course
                8 - Filter Students
                9 - Filter Course
               10 - Register Student to Course
               11 - Change Course Credits
               12 - Show Courses not yet full
               13 - Show Students enrolled to specific Course
               14 - Show Students
               15 - Show Teachers
               16 - Show Courses
                0 - Exit
                """);
    }

    /**
     * User Interface
     */
    public void start() throws IOException {


        while (true) {
            showMenu();
            Scanner input = new Scanner(System.in);
            int number;
            do {
                System.out.print("Choose an option: ");
                number = input.nextInt();
            } while (number < 0 || number > 18);

            long studentID, courseID, teacherID;
            int credits;
            switch (number) {
                case 0 -> {
                    System.out.println("You chose EXIT");
                    System.exit(0);
                }
                case 1 -> {
                    Student student = createStudent();
                    studentController.save(student);
                }
                case 2 -> {
                    Teacher teacher = createTeacher();
                    teacherController.save(teacher);
                }
                case 3 -> {
                    Course course = createCourse();
                    courseController.save(course);
                }
                case 4 -> {
                    do {
                        System.out.println("Please select the teacherID for the course: ");
                        teacherID = input.nextLong();
                    } while (!teacherController.containsID(teacherID));

                    do {
                        System.out.println("Please select a courseID: ");
                        courseID = input.nextLong();
                    } while (!courseController.containsID(courseID));

                    Teacher teacher = teacherController.findOne(teacherID);
                    if (teacherController.containsCourse(teacher, courseID)) {
                        Course course = courseController.findOne(courseID);
                        teacherController.deleteCourseFromAllTeachers(course);
                        teacherController.deleteCourse(teacher, course);
                    } else {
                        System.out.println("Teacher is not responsible for given course");
                    }
                }
                case 5 -> courseController.sort();
                case 6 -> studentController.sort();
                case 7 -> {
                    System.out.print("Courses with their number of free places: ");
                    Map<Long, Integer> hash_map = courseController.availablePlacesPerCourse();
                    hash_map.forEach((key,value) -> System.out.println(key + ", " + value));
                }
                case 8 -> {
                    System.out.println("Filtered students: ");
                    System.out.println(studentController.filter());
                }
                case 9 -> {
                    System.out.println("Filtered courses: ");
                    System.out.println(courseController.filter());
                }
                case 10 -> {
                    do {
                        System.out.println("StudentID: ");
                        studentID = input.nextLong();
                    } while (!studentController.containsID(studentID));

                    do {
                        System.out.println("CourseID: ");
                        courseID = input.nextLong();
                    } while (!courseController.containsID(courseID));

                    Student student = studentController.findOne(studentID);
                    Course course = courseController.findOne(courseID);
                    courseController.register(student, course);
                }
                case 11 -> {
                    do {
                        System.out.println("courseID: ");
                        courseID = input.nextLong();
                    } while (!courseController.containsID(courseID));

                    do {
                        System.out.println("Number of credits: ");
                        credits = input.nextInt();
                    } while (credits < 0);

                    courseController.modifyCreditNumber(courseID, credits);
                }
                case 12 -> System.out.println(courseController.getCoursesNotYetFull());
                case 13 -> {
                    do {
                        System.out.println("CourseID: ");
                        courseID = input.nextLong();
                    } while (!courseController.containsID(courseID));

                    studentController.getStudentsEnrolledInSpecificCourse(courseID);
                }
                case 14 -> System.out.println(studentController.findAll());
                case 15 -> System.out.println(teacherController.findAll());
                case 16 -> System.out.println(courseController.findAll());
            }
        }
    }

    /**
     * take the data as input and create a student entity
     * @return the created entity
     */
    public Student createStudent() {

        Scanner scan = new Scanner(System.in);
        System.out.print("FirstName: ");
        String firstName = scan.nextLine();
        System.out.print("LastName: ");
        String lastName = scan.nextLine();

        long id;
        do {
            System.out.print("ID: ");
            id = scan.nextLong();
        } while (studentController.containsID(id));

        return new Student(firstName, lastName, id);
    }

    /**
     * take the data as input and create a teacher entity
     * @return the created entity
     */
    public Teacher createTeacher() {

        Scanner scan = new Scanner(System.in);
        System.out.print("FirstName: ");
        String firstName = scan.nextLine();
        System.out.print("LastName: ");
        String lastName = scan.nextLine();

        long id;
        do {
            System.out.print("ID: ");
            id = scan.nextLong();
        } while (teacherController.containsID(id));

        return new Teacher(firstName, lastName, id);
    }

    /**
     * take the data as input and create a Course entity
     * @return the created entity
     */
    public Course createCourse() throws IOException {

        Scanner scan = new Scanner(System.in);
        System.out.print("Title: ");
        String title = scan.nextLine();

        long id;
        do {
            System.out.print("ID: ");
            id = scan.nextInt();
        } while (courseController.containsID(id));

        int maxStudents;
        do {
            System.out.print("Max number of students: ");
            maxStudents = scan.nextInt();
        } while (maxStudents < 1);

        int credits;
        do {
            System.out.print("Number of credits: ");
            credits = scan.nextInt();
        } while (credits < 0);

        long idTeacher;
        do {
            System.out.print("ID of Teacher: ");
            idTeacher = scan.nextInt();
        } while (!teacherController.containsID(idTeacher));

        Teacher teacher = teacherController.findOne(idTeacher);
        teacher.addCourseToList(id);
        teacherController.writeToFile();
        return new Course(id, title, idTeacher, maxStudents, credits);
    }
}
