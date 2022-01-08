package com.Repository;

import com.Entities.Student;
import com.Entities.Course;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class StudentRepository extends InMemoryRepository<Student> implements FileRepository<Student> {

    String file;
    public StudentRepository(String _file) {
        super();
        this.file = _file;
    }

    @Override
    public String toString() {
        return "StudentRepo (" + this.repo + ")";
    }

    @Override
    public List<Student> readFromFile() throws IOException {

        Reader reader = new BufferedReader(new FileReader(file));
        ObjectMapper objMapper = new ObjectMapper();
        JsonNode parser = objMapper.readTree(reader);

        for (JsonNode pm : parser) {
            long ID = pm.path("id").asLong();
            String firstName = pm.path("firstName").asText();
            String lastName = pm.path("lastName").asText();
            int totalCredits = pm.path("totalCredits").asInt();

            List<Long> enrolledCourses = new ArrayList<>();
            for (JsonNode n : pm.path("enrolledCourses")) {
                enrolledCourses.add(n.asLong());
            }

            repo.add(new Student(firstName, lastName, ID, totalCredits, enrolledCourses));
        }
        return repo;
    }

    @Override
    public void writeToFile() throws IOException {

        ObjectMapper objMapper = new ObjectMapper();
        ObjectWriter writer = objMapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(file), repo);
    }

    @Override
    public Student findOne(Long _id) throws IOException {

        Reader reader = new BufferedReader(new FileReader(file));
        ObjectMapper objMapper = new ObjectMapper();
        JsonNode parser = objMapper.readTree(reader);

        for (JsonNode pm : parser) {
            long fromFileID = pm.path("studentID").asLong();
            if (Objects.equals(_id, fromFileID)) {
                String firstName = pm.path("firstName").asText();
                String lastName = pm.path("lastName").asText();
                int totalCredits = pm.path("totalCredits").asInt();

                List<Long> enrolledCourses = new ArrayList<>();
                for (JsonNode n : pm.path("enrolledCourses")) {
                    enrolledCourses.add(n.asLong());
                }

                return new Student(firstName, lastName, _id, totalCredits, enrolledCourses);
            }
        }
        return null;
    }

    @Override
    public void save(Student _entity) throws IOException {

        this.repo.add(_entity);
        writeToFile();
    }

    @Override
    public void delete(Long _id) throws IOException {
        if (repo.isEmpty())
            throw new IndexOutOfBoundsException("List is empty");

        for (Student student : repo)
            if (student.getID() == _id) {
                repo.remove(student);
                writeToFile();
            }
    }

    /**
     * sorts List of Students by number of courses enrolled
     */
    public void sortList() {
        repo.sort(Student::compareTo);
    }

    /**
     * @return list of objects that meet criteria
     */
    public List<Student> filterList() {
        return repo.stream()
                .filter(student -> student.getTotalCredits() > 24).toList();
    }

    /**
     * iterate through every Student's coursesList
     * @param _id of searched Course
     * @return the list of students enrolled in specific course
     */
    public List<Student> studentEnrolledInSpecificCourse(Long _id) {

        List<Student> enrolledStudents = new ArrayList<>();
        for (Student student : repo) {

            List<Long> studentCourses = student.getEnrolledCourses();
            for (Long course : studentCourses) {

                if (Objects.equals(course, _id)) {
                    enrolledStudents.add(student);
                    break;
                }
            }
        }
        return enrolledStudents;
    }

    /**
     * delete course from all enrolled Students Lists
     */
    public void leaveCourse(Course _course) throws IOException {

        for (Student student : repo) {
            if (student.getEnrolledCourses().contains(_course.getID())) {
                student.exitCourse(_course);
                writeToFile();
            }
        }
    }

    /**
     * method updates the new credit total for all students enrolled in it
     * @param _idCourse is the ID of the course whose credit number is changed
     * @param _credits number of new credits
     * @param _oldCredits is the number of credits the course has before the change
     */
    public void modifyCourseCredit(Long _idCourse, int _credits, int _oldCredits) {

        for (Student student : repo) {
            if (student.getEnrolledCourses().contains(_idCourse)) {
                student.setTotalCredits(student.getTotalCredits() - _oldCredits + _credits);
            }
        }
    }

    /**
     * check if student is eligible to enroll to course
     * @param _student to check for credits
     * @param _course to check for credits
     * @return true if the number of his remaining credits is bigger than the course's credit number
     */
    public boolean addCourseValidation(Student _student, Course _course) {

        if (!repo.contains(_student))
            return false;

        return _student.getRemainingCredits() >= _course.getCredits();
    }

    /**
     * add student to course if validator function returns true
     */
    public void addToCourse(Student _student, Course _course) throws IOException {

        if (addCourseValidation(_student, _course)) {
            _student.enroll(_course);
            writeToFile();
        }
    }

    /**
     * @return true if List contains the student with the given ID
     */
    public boolean containsID(Long _id) {

        for (Student student : repo) {
            if (student.getID() == _id)
                return true;
        }
        return false;
    }

    public boolean containsCourse(Student _student, Long _idCourse) {
        return _student.getEnrolledCourses().contains(_idCourse);
    }

}