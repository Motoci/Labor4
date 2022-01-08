package com.Entities;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

public class Student extends Person implements Comparable<Student> {

    private long ID;
    private int totalCredits;
    private List<Long> enrolledCourses;

    public Student(String _firstName, String _lastName, long _ID) {
        super(_firstName, _lastName);
        this.ID = _ID;
        this.totalCredits = 0;
        this.enrolledCourses = new ArrayList<>();
    }

    public Student(String _firstName, String _lastName, long _ID, int _totalCredits, List<Long> _enrolledCourses) {
        super(_firstName, _lastName);
        this.ID = _ID;
        this.totalCredits = _totalCredits;
        this.enrolledCourses = _enrolledCourses;
    }

    public void setEnrolledCourses(List<Long> _enrolledCourses) {
        this.enrolledCourses = _enrolledCourses;
    }

    public void setID(long _ID) {
        this.ID = _ID;
    }

    public void setTotalCredits(int _totalCredits) {
        this.totalCredits = _totalCredits;
    }

    public long getID() {
        return ID;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public List<Long> getEnrolledCourses() {
        return enrolledCourses;
    }

    @Override
    public String toString() {
        return "Student " + this.getFirstName() + " " + this.getLastName() + " ";
    }

    @Override
    public boolean equals(Object _o) {
        if (this == _o) return true;
        if (_o == null) return false;
        if (_o instanceof Student) {
            Student test = (Student) _o;
            if (this.getID() == test.getID()
                    && this.getTotalCredits() == test.getTotalCredits()
                    && Objects.equals(this.getEnrolledCourses(), test.getEnrolledCourses()))
                return true;
        }
        return false;
    }

    /**
     * deletes course from student's List and subtracts credits for removed course
     * @param _course to remove
     */
    public void exitCourse(Course _course) {
        this.enrolledCourses.remove(_course.getID());
        this.totalCredits -= _course.getCredits();
    }

    /**
     *  determine number or remaining credits to pass the year
     * @return subtraction between 30 and current number or credits
     */
    public int getRemainingCredits() {
        return 30 - this.getTotalCredits();
    }

    /**
     * we add the enrolled course to the students list of courses and increment his credit number
     * @param _course is the course for which the student just enrolled
     */
    public void enroll(Course _course) {
        this.enrolledCourses.add(_course.getID());
        this.totalCredits += _course.getCredits();
    }

    /**
     * method returns number of courses for current student
     * @return number as integer
     */
    public int getNumberOfCourses() {
        return enrolledCourses.size();
    }

    /**
     * uses number of courses as a comparison between students
     */
    @Override
    public int compareTo(Student _o) {
        return Integer.compare(this.getNumberOfCourses(), _o.getNumberOfCourses());
    }

}