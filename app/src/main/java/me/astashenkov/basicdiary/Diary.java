package me.astashenkov.basicdiary;

import java.io.Serializable;
import java.util.Date;

public class Diary implements Serializable {

    private int diaryId;
    private Date dateCreated;
    private String title;
    private String description;
    private Date dateModified;

    public Diary(int diaryId, Date dateCreated, String title, String description, Date dateModified) {
        this.diaryId = diaryId;
        this.dateCreated = dateCreated;
        this.title = title;
        this.description = description;
        this.dateModified = dateModified;
    }

    public int getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(int diaryId) {
        this.diaryId = diaryId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
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

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }
}

