package com.Controller;

import com.Entities.Student;
import com.Repository.StudentRepository;

import java.io.IOException;
import java.util.List;

public class StudentController implements Controller<Student> {

    private StudentRepository studentsRepo;

    public StudentController(StudentRepository _studentsRepo) {
        this.studentsRepo = _studentsRepo;
    }

    public StudentRepository getStudentsRepo() {
        return studentsRepo;
    }

    public void setStudentsRepo(StudentRepository _studentsRepo) {
        this.studentsRepo = _studentsRepo;
    }

    /**
     * call save method from StudentRepository
     */
    @Override
    public void save(Student _entity) throws IOException {
        studentsRepo.save(_entity);
    }

    /**
     * call delete method from StudentRepository
     */
    @Override
    public void delete(Long _id) throws IOException {
        studentsRepo.delete(_id);
    }

    /**
     * call findOne method from StudentRepository
     */
    @Override
    public Student findOne(Long _id) throws IOException {
        return studentsRepo.findOne(_id);
    }

    /**
     * call findAll method from StudentRepository
     */
    @Override
    public List<Student> findAll() {
        return studentsRepo.findAll();
    }

    /**
     * call readFromFile method from StudentRepository
     */
    @Override
    public List<Student> readFromFile() throws IOException {
        return studentsRepo.readFromFile();
    }

    /**
     * call writeToFile method from StudentRepository
     */
    @Override
    public void writeToFile() throws IOException {
        studentsRepo.writeToFile();
    }

    /**
     * call filter method from StudentRepository
     */
    public List<Student> filter() {
        return studentsRepo.filterList();
    }

    /**
     * call sort method from StudentRepository
     */
    public void sort() {
        studentsRepo.sortList();
    }

    /**
     * call studentsEnrolledInSpecificCourse method from StudentRepository
     */
    public void getStudentsEnrolledInSpecificCourse(Long _id) {
        System.out.println(studentsRepo.studentEnrolledInSpecificCourse(_id));
    }

    /**
     * call containsID method from StudentRepository
     */
    public boolean containsID(Long _id) {
        return studentsRepo.containsID(_id);
    }

    /**
     * call containsCourse method from StudentRepository
     */
    public boolean containsCourse(Student _entity, Long _IDCourse) {
        return studentsRepo.containsCourse(_entity, _IDCourse);
    }

}
