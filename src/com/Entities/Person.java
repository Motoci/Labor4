package com.Entities;

public class Person {
    private String firstName;
    private String lastName;

    public Person(String _firstName, String _lastName) {
        this.firstName = _firstName;
        this.lastName = _lastName;
    }

    public void setFirstName(String _firstName) {
        this.firstName = _firstName;
    }

    public void setLastName(String _lastName) {
        this.lastName = _lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

}
