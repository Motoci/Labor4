package com.Repository;

import com.Entities.Teacher;
import com.Entities.Course;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherRepository extends InMemoryRepository<Teacher> implements FileRepository<Teacher> {

    String file;
    public TeacherRepository(String _file) {
        super();
        this.file = _file;
    }

    @Override
    public String toString() {
        return "TeacherRepository (" + this.repo + ")";
    }


    @Override
    public List<Teacher> readFromFile() throws IOException {

        Reader reader = new BufferedReader(new FileReader(file));
        ObjectMapper objMapper = new ObjectMapper();
        JsonNode parser = objMapper.readTree(reader);

        for (JsonNode pm : parser) {
            int ID = pm.path("id").asInt();
            String firstName = pm.path("firstName").asText();
            String lastName = pm.path("lastName").asText();

            List<Long> courses = new ArrayList<>();
            for (JsonNode n : pm.path("courses")) {
                courses.add(n.asLong());
            }

            repo.add(new Teacher(firstName, lastName, ID, courses));
        }
        return repo;
    }


    @Override
    public void writeToFile() throws IOException {

        ObjectMapper objMapper = new ObjectMapper();
        ObjectWriter writer = objMapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(file), repo);
    }

    /**
     * method searches for the Teacher with the given ID in a jsonFile
     * @param _id ID of the entity we are searching for
     * @return the found entity or null
     */
    @Override
    public Teacher findOne(Long _id) throws IOException {

        Reader reader = new BufferedReader(new FileReader(file));
        ObjectMapper objMapper = new ObjectMapper();
        JsonNode parser = objMapper.readTree(reader);

        for (JsonNode pm : parser) {
            long ID = pm.path("id").asLong();

            if (ID == _id) {
                String firstName = pm.path("firstName").asText();
                String lastName = pm.path("lastName").asText();

                List<Long> courses = new ArrayList<>();
                for (JsonNode n : pm.path("courses")) {
                    courses.add(n.asLong());
                }

                return new Teacher(firstName, lastName, ID, courses);
            }
        }
        return null;
    }

    /**
     * delete the Teacher entity with the given ID
     * @param _id of entity to be deleted
     * @throws IndexOutOfBoundsException if List empty
     */
    public void delete(Long _id) throws IOException {

        if (repo.isEmpty())
            throw new IndexOutOfBoundsException("The List is empty");

        for (Teacher teacher : repo) {
            if (teacher.getID() == _id) {
                repo.remove(teacher);
                writeToFile();
                break;
            }
        }
    }

    /**
     * save a new Teacher to the List and to the file
     * @param _entity to be saved
     * @throws IllegalArgumentException if Teacher entity is found in List
     */
    public void save(Teacher _entity) throws IOException {

        this.repo.add(_entity);
        writeToFile();
    }

    /**
     * @param _teacher from whom f to delete the course
     * @param _course to be deleted
     * @return true if deletion is successful, false otherwise
     * @throws IOException for writing to file
     */
    public boolean deleteCourse(Teacher _teacher, Course _course) throws IOException {

        if (!_teacher.getCourses().contains(_course.getID()))
            return false;

        _teacher.deleteCourseFromList(_course);
        writeToFile();
        return true;
    }

    /**
     * search for the teacher with the given ID
     * @return true if Teacher is found, false otherwise
     */
    public boolean containsID(Long _id) {

        for (Teacher teacher : repo) {
            if (teacher.getID() == _id)
                return true;
        }
        return false;
    }

    /**
     * calls function to search the List of the given Teacher for given CourseID
     * @return true if course is found, false otherwise
     */
    public boolean teachesCourse(Teacher _teacher, Long _id) {
        return _teacher.hasCourseInList(_id);
    }

    /**
     * iterate through the list of teachers to delete the given course
     * @param _course to be deleted
     * @throws IOException for writing tn file
     */
    public void deleteCourseFromAllTeachers (Course _course) throws IOException {

        for (Teacher teacher : repo) {
            teacher.deleteCourseFromList(_course);
        }
        writeToFile();
    }

    /**
     * add course to teacher's list
     * @throws IOException for writing to file
     */
    public void addCourse(long _idTeacher, Course _course) throws IOException {

        for (Teacher teacher : repo)
            if (teacher.getID() == _idTeacher) {
                teacher.addCourseToList(_course.getID());
                writeToFile();
                break;
            }
    }









}