package com.Entities;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;


public class Teacher extends Person {
    private long ID;
    private List<Long> courses;

    public Teacher(String _firstName, String _lastName, long _ID) {
        super(_firstName, _lastName);
        this.ID = _ID;
        this.courses = new ArrayList<>();
    }

    public Teacher(String _firstName, String _lastName, long _ID, List<Long> _courses) {
        super(_firstName, _lastName);
        this.ID = _ID;
        this.courses = _courses;
    }

    public void setID(long ID) {
        this.ID = ID;
    }
    public void setCourses(List<Long> courses) {
        this.courses = courses;
    }

    public long getID() {
        return ID;
    }
    public List<Long> getCourses() {
        return courses;
    }

    @Override
    public String toString() {
        return " Teacher " + this.getFirstName() + " " + this.getLastName() + " ";
    }

    @Override
    public boolean equals(Object _o) {
        if (this == _o) return true;
        if (_o == null) return false;
        if (_o instanceof Teacher) {
            Teacher test = (Teacher) _o;
            if (this.getID() == test.getID()
                    && Objects.equals(this.getCourses(), test.getCourses() ))
                return true;
        }
        return false;
    }

    /**
     * add course to Teacher's List
     * @param _idCourse to be added
     */
    public void addCourseToList(Long _idCourse) {
        this.courses.add(_idCourse);
    }

    /**
     * delete course from Teacher's List
     * @param _course to be deleted
     */
    public void deleteCourseFromList(Course _course) {
        this.courses.remove(_course.getID());
    }

    /**
     * searches a course by ID in teacher's list of courses
     * @param _id to be searched
     * @return true if found
     *         false otherwise
     */
    public boolean hasCourseInList(Long _id) {
        return this.courses.contains(_id);
    }

}