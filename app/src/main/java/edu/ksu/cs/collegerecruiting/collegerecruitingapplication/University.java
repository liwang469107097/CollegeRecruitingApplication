package edu.ksu.cs.collegerecruiting.collegerecruitingapplication;

import android.graphics.Bitmap;

import java.sql.Blob;

public class University {

    private int id;
    private String name;
    private Bitmap photo;

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
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

    public University() {
    }

    public University(String name, Bitmap photo) {
        this.name = name;
        this.photo = photo;
    }
}
