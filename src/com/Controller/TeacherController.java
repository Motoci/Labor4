package com.Controller;

import com.Entities.Teacher;
import com.Entities.Course;
import com.Repository.TeacherRepository;
import com.Repository.CourseRepository;
import com.Repository.StudentRepository;

import java.io.IOException;
import java.util.List;

public class TeacherController implements Controller<Teacher> {

    private TeacherRepository teacherRepo;
    private CourseRepository courseRepo;
    private StudentRepository studentRepo;

    public TeacherController(TeacherRepository _teacherRepo, CourseRepository _courseRepo, StudentRepository _studentRepo) {
        this.teacherRepo = _teacherRepo;
        this.courseRepo = _courseRepo;
        this.studentRepo = _studentRepo;
    }

    public void setTeacherRepo(TeacherRepository _teacherRepo) {
        this.teacherRepo = _teacherRepo;
    }

    public void setCourseRepo(CourseRepository _courseRepo) {
        this.courseRepo = _courseRepo;
    }

    public void setStudentRepo(StudentRepository _studentRepo) {
        this.studentRepo = _studentRepo;
    }

    public TeacherRepository getTeacherRepo() {
        return this.teacherRepo;
    }

    public CourseRepository getCourseRepo() {
        return this.courseRepo;
    }

    public StudentRepository getStudentRepo() {
        return this.studentRepo;
    }

    /**
     * call save method from TeacherRepository
     */
    @Override
    public void save(Teacher _entity) throws IOException {
        teacherRepo.save(_entity);
    }

    /**
     * call delete method from TeacherRepository
     */
    @Override
    public void delete(Long _id) throws IOException {
        teacherRepo.delete(_id);
    }

    /**
     * call findOne method from TeacherRepository
     */
    @Override
    public Teacher findOne(Long _id) throws IOException {
        return teacherRepo.findOne(_id);
    }

    /**
     * call findAll method from TeacherRepository
     */
    @Override
    public List<Teacher> findAll() {
        return teacherRepo.findAll();
    }

    /**
     * call readFromFile method from TeacherRepository
     */
    @Override
    public List<Teacher> readFromFile() throws IOException {
        return teacherRepo.readFromFile();
    }

    /**
     * call writeToFile method from TeacherRepository
     */
    @Override
    public void writeToFile() throws IOException {
        teacherRepo.writeToFile();
    }

    /**
     * deletes course from all 3 repositories
     * @throws IOException for writing into file
     */
    public void deleteCourse(Teacher _teacher, Course _course) throws IOException {
        if (teacherRepo.deleteCourse(_teacher, _course)) {
            studentRepo.leaveCourse(_course);
            courseRepo.delete(_course.getID());
        }
    }

    /**
     * call containsID method from TeacherRepository
     */
    public boolean containsID(Long _id) {
        return teacherRepo.containsID(_id);
    }

    /**
     * call containsCourse method from TeacherRepository
     */
    public boolean containsCourse(Teacher _teacher, Long _id) {
        return teacherRepo.teachesCourse(_teacher, _id);
    }

    /**
     * call deleteCourseFromAllTeachers method from TeacherRepository
     */
    public void deleteCourseFromAllTeachers(Course _course) throws IOException {
        teacherRepo.deleteCourseFromAllTeachers(_course);
    }

}
