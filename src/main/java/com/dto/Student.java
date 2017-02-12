package com.dto;

/**
 * Created by surik on 2/4/17
 */
public class Student {

    private String firstName;
    private String lastName;
    private String group;
    private int course;
    private double rating;
    private boolean passedFirstExam;
    private boolean passedSecondExam;

    private String login;
    private String password;

    public Student() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public boolean isPassedFirstExam() {
        return passedFirstExam;
    }

    public void setPassedFirstExam(boolean passedFirstExam) {
        this.passedFirstExam = passedFirstExam;
    }

    public boolean isPassedSecondExam() {
        return passedSecondExam;
    }

    public void setPassedSecondExam(boolean passedSecondExam) {
        this.passedSecondExam = passedSecondExam;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", group='" + group + '\'' +
                ", course=" + course +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (getCourse() != student.getCourse()) return false;
        if (!getFirstName().equals(student.getFirstName())) return false;
        if (!getLastName().equals(student.getLastName())) return false;
        if (!getGroup().equals(student.getGroup())) return false;
        if (!getLogin().equals(student.getLogin())) return false;
        return getPassword().equals(student.getPassword());
    }

}
