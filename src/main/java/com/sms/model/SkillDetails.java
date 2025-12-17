package com.sms.model;

public class SkillDetails {
    private int id;
    private int studentId;
    private String programmingLevel; // Beginner, Intermediate, Advanced
    private int communicationRating; // 1-10
    private int leadershipRating; // 1-10
    private String technicalSkills; // Comma separated
    private double overallSkillScore;

    public SkillDetails() {
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

    public String getProgrammingLevel() {
        return programmingLevel;
    }

    public void setProgrammingLevel(String programmingLevel) {
        this.programmingLevel = programmingLevel;
    }

    public int getCommunicationRating() {
        return communicationRating;
    }

    public void setCommunicationRating(int communicationRating) {
        this.communicationRating = communicationRating;
    }

    public int getLeadershipRating() {
        return leadershipRating;
    }

    public void setLeadershipRating(int leadershipRating) {
        this.leadershipRating = leadershipRating;
    }

    public String getTechnicalSkills() {
        return technicalSkills;
    }

    public void setTechnicalSkills(String technicalSkills) {
        this.technicalSkills = technicalSkills;
    }

    public double getOverallSkillScore() {
        return overallSkillScore;
    }

    public void setOverallSkillScore(double overallSkillScore) {
        this.overallSkillScore = overallSkillScore;
    }
}
