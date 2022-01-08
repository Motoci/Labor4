package com.Entities;

import com.Repository.TeacherRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.io.IOException;

public class Course implements Comparable<Course> {

    private long ID;
    private String title;
    private int credits;
    private long teacher;
    private int maxStudents;
    private List<Long> studentsEnrolled;

    public Course(long _ID, String _title, long _teacher, int _maxStudents, int _credits) throws IOException {
        this.ID = _ID;
        this.title = _title;
        this.teacher = _teacher;
        this.maxStudents = _maxStudents;
        this.credits = _credits;
        this.studentsEnrolled = new ArrayList<>();

        //TeacherRepository repo = new TeacherRepository("teacher.json");
        //repo.addCourse(_teacher, this);
    }

    public Course(long _ID, String _title, long _teacher, int _maxStudents, int _credits, List<Long> _studentsEnrolled) throws IOException {
        this.ID = _ID;
        this.title = _title;
        this.teacher = _teacher;
        this.maxStudents = _maxStudents;
        this.credits = _credits;
        this.studentsEnrolled = _studentsEnrolled;

    }

    public Course(long _ID, int _credits) {
        this.ID = _ID;
        this.credits = _credits;
    }


    public void setID(long _ID) {
        this.ID = _ID;
    }
    public void setTitle(String _title) {
        this.title = _title;
    }
    public void setCredits(int _credits) {
        this.credits = _credits;
    }
    public void setTeacher(long _teacher) {
        this.teacher = _teacher;
    }
    public void setMaxStudents(int _maxStudents) {
        this.maxStudents = _maxStudents;
    }
    public void setStudentsEnrolled(List<Long> _studentsEnrolled) {
        this.studentsEnrolled = _studentsEnrolled;
    }

    public long getID() {
        return ID;
    }
    public String getTitle() {
        return title;
    }
    public int getCredits() {
        return credits;
    }
    public long getTeacher() {
        return teacher;
    }
    public int getMaxStudents() {
        return maxStudents;
    }
    public List<Long> getStudentsEnrolled() {
        return studentsEnrolled;
    }

    @Override
    public String toString() {
        return "Course " + this.getTitle() + " ";
    }

    @Override
    public boolean equals(Object _o) {
        if (this == _o) return true;
        if (_o == null) return false;
        if (_o instanceof Course) {
            Course test = (Course) _o;
            if (this.getID() == test.getID()
                    && Objects.equals(this.getTitle(), test.getTitle())
                    && Objects.equals(this.getTeacher(), test.getTeacher())
                    && this.getCredits() == test.getCredits()
                    && this.getMaxStudents() == test.getMaxStudents()
                    && Objects.equals(this.getStudentsEnrolled(), test.getStudentsEnrolled()))
                return true;
        }
        return false;
    }

    /**
     * check for course availability
     * @return true if there are any empty places
     */
    public boolean isFree() {
        return this.getStudentsEnrolled().size() < this.getMaxStudents();
    }

    /**
     * @return number of available places
     */
    public int getNrAvailablePlaces() {
        return this.getMaxStudents() - getStudentsEnrolled().size();
    }

    /**
     * add student to the courses list of enrolled students
     * @param _student to be added
     */
    public void enrollStudent(Student _student) {
        this.studentsEnrolled.add(_student.getID());
    }


    /**
     * compare 2 courses by their number of credits
     * @param _o object to compare to
     * @return 1 if Course1 has a bigger number of credits, -1 otherwise, 0 if the two are equal
     */
    @Override
    public int compareTo(Course _o) {
        return Integer.compare(this.getCredits(), _o.getCredits());

        /*
        if (this.getCredits() > _o.getCredits())
            return 1;
        else if (this.getCredits() < _o.getCredits())
            return -1;
        else
            return 0;
         */
    }

}
