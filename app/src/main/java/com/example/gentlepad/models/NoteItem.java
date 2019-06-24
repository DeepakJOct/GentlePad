package com.example.gentlepad.models;

public class NoteItem {

    private String notesTitle, notesDesc, date;

    public NoteItem(String notesTitle, String notesDesc, String date) {
        this.notesTitle = notesTitle;
        this.notesDesc = notesDesc;
        this.date = date;
    }

    public String getNotesTitle() {
        return notesTitle;
    }

    public String getNotesDesc() {
        return notesDesc;
    }

    public String getDate() {
        return date;
    }

    public void setNotesTitle(String notesTitle) {
        this.notesTitle = notesTitle;
    }

    public void setNotesDesc(String notesDesc) {
        this.notesDesc = notesDesc;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
