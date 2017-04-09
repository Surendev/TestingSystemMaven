package com.dto;

/**
 * Created by surik on 2/4/17
 */
public class Student {

    private String firstName;
    private String middleName;
    private String lastName;

    private String group;
    private int course;

    private Double lastRating;
    private double rating;

    private boolean passedFirstExam;
    private boolean passedSecondExam;
    private boolean passedLastExam;

    public Student() {
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

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setLastRating(Double lastRating) {
        this.lastRating = lastRating;
    }

    public Double getLastRating() {
        return lastRating;
    }

    public void setPassedLastExam(boolean passedLastExam) {
        this.passedLastExam = passedLastExam;
    }

    public boolean isPassedLastExam() {
        return passedLastExam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (course != student.course) return false;
        if (Double.compare(student.rating, rating) != 0) return false;
        if (passedFirstExam != student.passedFirstExam) return false;
        if (passedSecondExam != student.passedSecondExam) return false;
        if (passedLastExam != student.passedLastExam) return false;
        if (!firstName.equals(student.firstName)) return false;
        if (!middleName.equals(student.middleName)) return false;
        if (!lastName.equals(student.lastName)) return false;
        if (!group.equals(student.group)) return false;
        return lastRating.equals(student.lastRating);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = firstName.hashCode();
        result = 31 * result + middleName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + group.hashCode();
        result = 31 * result + course;
        result = 31 * result + lastRating.hashCode();
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (passedFirstExam ? 1 : 0);
        result = 31 * result + (passedSecondExam ? 1 : 0);
        result = 31 * result + (passedLastExam ? 1 : 0);
        return result;
    }
}
