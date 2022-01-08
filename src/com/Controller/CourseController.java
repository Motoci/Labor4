package com.Controller;

import com.Entities.Course;
import com.Entities.Student;
import com.Repository.CourseRepository;
import com.Repository.StudentRepository;
import com.Repository.TeacherRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CourseController implements Controller<Course> {

    private CourseRepository courseRepo;
    private StudentRepository studentRepo;
    private TeacherRepository teacherRepo;

    public CourseController(CourseRepository _courseRepo, StudentRepository _studentRepo, TeacherRepository _teacherRepo) {
        this.courseRepo = _courseRepo;
        this.studentRepo = _studentRepo;
        this.teacherRepo = _teacherRepo;
    }

    public void setCourseRepo(CourseRepository _courseRepo) {
        this.courseRepo = _courseRepo;
    }

    public void setStudentRepo(StudentRepository _studentRepo) {
        this.studentRepo = _studentRepo;
    }

    public void setTeacherRepo(TeacherRepository _teacherRepo) {
        this.teacherRepo = _teacherRepo;
    }

    public CourseRepository getCourseRepo() {
        return courseRepo;
    }

    public StudentRepository getStudentRepo() {
        return studentRepo;
    }

    public TeacherRepository getTeacherRepo() {
        return teacherRepo;
    }

    /**
     * call save method from CourseRepository
     */
    @Override
    public void save(Course _entity) throws IOException {
        courseRepo.save(_entity);
    }

    /**
     * call delete method from CourseRepository
     */
    @Override
    public void delete(Long _id) throws IOException {
        courseRepo.delete(_id);
    }

    /**
     * call findOne method from CourseRepository
     */
    @Override
    public Course findOne(Long _id) throws IOException {
        return courseRepo.findOne(_id);
    }

    /**
     * call findAll method from CourseRepository
     */
    @Override
    public List<Course> findAll() {
        return courseRepo.findAll();
    }

    /**
     * call readFromFile method from CourseRepository
     */
    @Override
    public List<Course> readFromFile() throws IOException {
        return courseRepo.readFromFile();
    }

    /**
     * call writeToFile method from CourseRepository
     */
    @Override
    public void writeToFile() throws IOException {
        courseRepo.writeToFile();
    }

    /**
     * call filter method from CourseRepository
     */
    public List<Course> filter() {
        return courseRepo.filterList();
    }

    /**
     * call sort method from CourseRepository
     */
    public void sort() {
        courseRepo.sortList();
    }

    /**
     * call CoursesNotYetFull method from CourseRepository
     */
    public List<Course> getCoursesNotYetFull() {
        return courseRepo.getCoursesNotYetFull();
    }

    /**
     * call availablePlacesPerCourse method from CourseRepository
     */
    public Map<Long, Integer> availablePlacesPerCourse() {
        return courseRepo.availablePlacesPerCourse();
    }

    /**
     * call containsID method from CourseRepository
     */
    public boolean containsID(Long _id) {
        return courseRepo.containsID(_id);
    }

    /**
     * change the number of Credits from a Course
     * @param _idCourse to search the course by ID
     * @param _credits new number of credits
     * @throws IOException for writing to file
     */
    public void modifyCreditNumber(Long _idCourse, int _credits) throws IOException {

        int oldCreditNumber = courseRepo.modifyCreditNumber(_idCourse, _credits);
        studentRepo.modifyCourseCredit(_idCourse, _credits, oldCreditNumber);
    }

    /**
     * enroll a student to a course
     * @param _student to enroll
     * @param _course to enroll to
     * @throws IOException for writing to file
     */
    public void register(Student _student, Course _course) throws IOException {

        if (courseRepo.validateFreePlaces(_course.getID()) && studentRepo.addCourseValidation(_student, _course))
            if (!studentRepo.containsCourse(_student, _course.getID())) {
                courseRepo.addStudentToCourse(_student, _course.getID());
                studentRepo.addToCourse(_student, _course);
            }
    }


}
