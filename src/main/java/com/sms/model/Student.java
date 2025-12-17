package com.sms.model;

public class Student {
    private int studentId;
    private String fullName;
    private String dob;
    private String gender;
    private String phoneNumber;
    private String email;
    private String address;
    private String collegeIdCardNo;

    // Composition details
    private AcademicDetails academicDetails;
    private FeeDetails feeDetails;
    private SportsDetails sportsDetails;
    private SkillDetails skillDetails;

    private String calculatedGrade; // A+, A, etc.
    private String smartTags; // Comma separated tags: ACADEMIC_TOPPER, etc.
    private boolean riskFactor; // true if AT_RISK

    public Student() {
    }

    // Getters and Setters
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCollegeIdCardNo() {
        return collegeIdCardNo;
    }

    public void setCollegeIdCardNo(String collegeIdCardNo) {
        this.collegeIdCardNo = collegeIdCardNo;
    }

    public AcademicDetails getAcademicDetails() {
        return academicDetails;
    }

    public void setAcademicDetails(AcademicDetails academicDetails) {
        this.academicDetails = academicDetails;
    }

    public FeeDetails getFeeDetails() {
        return feeDetails;
    }

    public void setFeeDetails(FeeDetails feeDetails) {
        this.feeDetails = feeDetails;
    }

    public SportsDetails getSportsDetails() {
        return sportsDetails;
    }

    public void setSportsDetails(SportsDetails sportsDetails) {
        this.sportsDetails = sportsDetails;
    }

    public SkillDetails getSkillDetails() {
        return skillDetails;
    }

    public void setSkillDetails(SkillDetails skillDetails) {
        this.skillDetails = skillDetails;
    }

    public String getCalculatedGrade() {
        return calculatedGrade;
    }

    public void setCalculatedGrade(String calculatedGrade) {
        this.calculatedGrade = calculatedGrade;
    }

    public String getSmartTags() {
        return smartTags;
    }

    public void setSmartTags(String smartTags) {
        this.smartTags = smartTags;
    }

    public boolean isRiskFactor() {
        return riskFactor;
    }

    public void setRiskFactor(boolean riskFactor) {
        this.riskFactor = riskFactor;
    }
}
