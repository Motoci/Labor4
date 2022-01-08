package com.Repository;

import com.Entities.Course;
import com.Entities.Student;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.util.*;

public class CourseRepository extends InMemoryRepository<Course> implements FileRepository<Course> {

    String file;
    public CourseRepository(String _file) {
        super();
        this.file = _file;
    }

    public String toString() {
        return "CourseRepository (" + this.repo + ")";
    }

    /**
     * read the attributes separately then compose all Teacher entities
     * @return repo list
     * @throws IOException for writing to file
     */
    public List<Course> readFromFile() throws IOException {

        Reader reader = new BufferedReader(new FileReader(file));
        ObjectMapper objMapper = new ObjectMapper();
        JsonNode parser = objMapper.readTree(reader);

        for (JsonNode pm : parser) {
            long ID = pm.path("id").asLong();
            String title = pm.path("title").asText();
            int teacher = pm.path("teacher").asInt();
            int maxStudents = pm.path("maxStudents").asInt();
            int credits = pm.path("credits").asInt();
            List<Long> studentsList = new ArrayList<>();
            for (JsonNode n : pm.path("studentsEnrolled"))
                studentsList.add(n.asLong());

            repo.add(new Course(ID, title, teacher, maxStudents, credits, studentsList));
        }
        return repo;
    }

    /**
     * write Courses to file
     */
    @Override
    public void writeToFile() throws IOException {

        ObjectMapper objMapper = new ObjectMapper();
        ObjectWriter writer = objMapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(file), repo);
    }

    /**
     * return the searched Course object or null if ID doesn't match
     * @param _IDCourse the id to search for
     * @throws IOException for writing to file
     */
    @Override
    public Course findOne(Long _IDCourse) throws IOException {

        Reader reader = new BufferedReader(new FileReader(file));
        ObjectMapper objMapper = new ObjectMapper();
        JsonNode parser = objMapper.readTree(reader);

        for (JsonNode pm : parser) {
            long ID = pm.path("id").asLong();
            if (Objects.equals(ID, _IDCourse)) {
                String title = pm.path("title").asText();
                int teacher = pm.path("teacher").asInt();
                int maxStudents = pm.path("maxStudents").asInt();
                int credits = pm.path("credits").asInt();

                List<Long> studentsList = new ArrayList<>();
                for (JsonNode n : pm.path("studentsEnrolled"))
                    studentsList.add(n.asLong());

                return new Course(ID, title, teacher, maxStudents, credits, studentsList);
            }
        }
        return null;
    }

    /**
     * @param _entity to be stored in the appropriate container
     * @throws IOException for writing in file
     */
    @Override
    public void save(Course _entity) throws IOException {

        repo.add(_entity);
        writeToFile();
    }

    /**
     * removes the entity with the given ID from the list of courses
     * @param _IDCourse id of entity to be deleted
     * @throws IndexOutOfBoundsException if List is empty
     */
    @Override
    public void delete (Long _IDCourse) throws IOException {

        if (repo.isEmpty())
            throw new IndexOutOfBoundsException("List is empty");

        for (Course course : repo)
            if (course.getID() == _IDCourse) {
                repo.remove(course);
                writeToFile();
                break;
            }
    }

    /**
     * filters the list by credit number
     * @return the list of courses with credit number bigger than 5
     */
    public List<Course> filterList() {
        return repo.stream().filter(course->course.getCredits() > 5).toList();
    }

    /**
     * sorts the List using the compareTo function in the Course class
     */
    public void sortList() {
        repo.sort(Course::compareTo);
    }

    /**
     * searches all courses to find the ones not yet filled
     * @return list of courses available to enroll to
     */
    public List<Course> getCoursesNotYetFull() {

        List<Course> coursesNotYetFull = new ArrayList<>();
        for (Course course : repo) {
            if (course.isFree())
                coursesNotYetFull.add(course);
        }
        return coursesNotYetFull;
    }

    /**
     * method uses map container to store each free course with its number of available places
     * @return the Hashmap
     */
    public Map<Long, Integer> availablePlacesPerCourse() {

        Map<Long, Integer> mapAvailableCourses = new HashMap<>();
        for (Course course : repo) {
            if (course.isFree())
                mapAvailableCourses.put(course.getID(), course.getNrAvailablePlaces());

        }
        return mapAvailableCourses;
    }

    /**
     * modify the number of credits for given course
     * @param _IDCourse id for course
     * @param _credits new number of credits
     * @throws IOException for writing in the file
     */
    public int modifyCreditNumber(Long _IDCourse, int _credits) throws IOException {

        int oldCreditNr = 0;
        for (Course course : repo) {
            if (course.getID() == _IDCourse) {
                oldCreditNr = course.getCredits();
                course.setCredits(_credits);
                writeToFile();
                break;
            }
        }
        return oldCreditNr;
    }

    /**
     * checks if a course has free places
     * @param _IDCourse id of the course to check
     * @return true if there are free places available, false otherwise
     */
    public boolean validateFreePlaces(Long _IDCourse) {

        for (Course course : repo) {
            if (course.getID() == _IDCourse)
                if (course.isFree())
                    return true;
        }
        return false;
    }

    /**
     * checks if a given student attends a given course
     * @param _IDCourse to identify course
     * @param _IDStudent to identify student
     * @return true if student attends course, false otherwise
     */
    public boolean studentAttendsCourse(Long _IDCourse, Long _IDStudent) {

        for (Course course : repo)
            if (course.getID() == _IDCourse)
                if (course.getStudentsEnrolled().contains(_IDStudent))
                    return true;

        return false;
    }

    /**
     * use earlier defined functions to check if the given course has available places
     * and if the given student does not attend the course already, than add student to course
     * @throws IOException for writing in file
     */
    public void addStudentToCourse(Student _student, Long _IDCourse) throws IOException {
        if (validateFreePlaces(_IDCourse) && !studentAttendsCourse(_IDCourse, _student.getID())) {
            for (Course course : repo)
                if (course.getID() == _IDCourse) {
                    course.enrollStudent(_student);
                    writeToFile();
                    break;
                }
        }
    }

    /**
     * @param _id to check
     * @return true if there exists a course with the given id, false otherwise
     */
    public boolean containsID(Long _id) {
        for (Course course : repo) {
            if (course.getID() == _id)
                return true;
        }
        return false;
    }

}