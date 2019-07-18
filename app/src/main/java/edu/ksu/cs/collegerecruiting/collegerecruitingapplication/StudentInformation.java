package edu.ksu.cs.collegerecruiting.collegerecruitingapplication;

public class StudentInformation {

    private int id;
    private String lastName;
    private String firstName;
    private String email;
    private String phone;
    private String major;
    private String degree;
    private String graduationDate;
    private String positionInterestedIn;
    private String comment;
    private int universityId;
    private int professionalTypesId;

    public StudentInformation() {
    }


    public StudentInformation(String lastName, String firstName, String email,
                              String phone, String major, String degree, String graduationDate,
                              String positionInterestedIn, String comment, int universityId,
                              int professionalTypesId) {

        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phone = phone;
        this.major = major;
        this.degree = degree;
        this.graduationDate = graduationDate;
        this.positionInterestedIn = positionInterestedIn;
        this.comment = comment;
        this.universityId = universityId;
        this.professionalTypesId = professionalTypesId;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(String graduationDate) {
        this.graduationDate = graduationDate;
    }

    public String getPositionInterestedIn() {
        return positionInterestedIn;
    }

    public void setPositionInterestedIn(String positionInterestedIn) {
        this.positionInterestedIn = positionInterestedIn;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUniversityId() {
        return universityId;
    }

    public void setUniversityId(int universityId) {
        this.universityId = universityId;
    }

    public int getProfessionalTypesId() {
        return professionalTypesId;
    }

    public void setProfessionalTypesId(int professionalTypesId) {
        this.professionalTypesId = professionalTypesId;
    }
}
