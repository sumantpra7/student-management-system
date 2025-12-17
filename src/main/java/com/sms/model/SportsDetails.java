package com.sms.model;

public class SportsDetails {
    private int id;
    private int studentId;
    private String sportsName;
    private String level; // College, State, National
    private String extraCurriculars;
    private boolean isSportsParticipant;

    public SportsDetails() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getSportsName() {
        return sportsName;
    }

    public void setSportsName(String sportsName) {
        this.sportsName = sportsName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getExtraCurriculars() {
        return extraCurriculars;
    }

    public void setExtraCurriculars(String extraCurriculars) {
        this.extraCurriculars = extraCurriculars;
    }

    public boolean isSportsParticipant() {
        return isSportsParticipant;
    }

    public void setSportsParticipant(boolean sportsParticipant) {
        isSportsParticipant = sportsParticipant;
    }
}
