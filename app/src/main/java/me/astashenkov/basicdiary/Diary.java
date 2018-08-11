package me.astashenkov.basicdiary;

import java.io.Serializable;
import java.util.Date;

public class Diary implements Serializable {
    public static final String DIARY = "Diary";
    public static final String ID = "_id";
    public static final String TITLE = "Title";
    public static final String DESCRIPTION = "Description";
    public static final String CREATED = "Created";
    public static final String MODIFIED = "Modified";
    public static final String CREATE_DIARY = "create table " + DIARY +
            " (" + ID + " integer primary key autoincrement, " + TITLE +
            " text null, " + DESCRIPTION + " text null, " + CREATED +
            " datetime default CURRENT_TIMESTAMP, " + MODIFIED +
            " datetime default CURRENT_TIMESTAMP); ";

    private int id;
    private String dateCreated;
    private String title;
    private String description;
    private String dateModified;

    public Diary() {};

    public Diary(int id, String title, String description, String dateCreated, String dateModified) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.title = title;
        this.description = description;
        this.dateModified = dateModified;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }
}

