package edu.ksu.cs.collegerecruiting.collegerecruitingapplication;

public class ProfessionalType {

    private int id;
    private String name;

    public ProfessionalType() {
    }

    public ProfessionalType(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
