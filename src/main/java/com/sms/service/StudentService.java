package com.sms.service;

import com.sms.dao.StudentDAO;
import com.sms.dao.StudentDAOImpl;
import com.sms.model.Student;

import java.util.List;
import java.util.ArrayList;

public class StudentService {
    private final StudentDAO studentDAO;

    public StudentService() {
        this.studentDAO = new StudentDAOImpl();
    }

    public boolean addStudent(Student student) {
        if (!processStudentData(student))
            return false;
        return studentDAO.addStudent(student);
    }

    public boolean updateStudent(Student student) {
        if (!processStudentData(student))
            return false;
        return studentDAO.updateStudent(student);
    }

    public boolean deleteStudent(int id) {
        return studentDAO.deleteStudent(id);
    }

    public Student getStudentById(int id) {
        Student s = studentDAO.getStudentById(id);
        if (s != null)
            calculateDerivedAttributes(s);
        return s;
    }

    public Student getStudentByIdCard(String idCard) {
        Student s = studentDAO.getStudentByIdCard(idCard);
        if (s != null)
            calculateDerivedAttributes(s);
        return s;
    }

    public List<Student> getAllStudents() {
        List<Student> list = studentDAO.getAllStudents();
        list.forEach(this::calculateDerivedAttributes);
        return list;
    }

    public List<Student> filterPendingFees() {
        List<Student> list = studentDAO.getStudentsWithPendingFees();
        list.forEach(this::calculateDerivedAttributes);
        return list;
    }

    public List<Student> filterSportsStudents() {
        List<Student> list = studentDAO.getSportsStudents();
        list.forEach(this::calculateDerivedAttributes);
        return list;
    }

    public List<Student> filterByGrade(String grade) {
        List<Student> all = getAllStudents(); // Fetch all first as grade is calculated
        List<Student> filtered = new ArrayList<>();
        for (Student s : all) {
            if (s.getCalculatedGrade() != null && s.getCalculatedGrade().equalsIgnoreCase(grade)) {
                filtered.add(s);
            }
        }
        return filtered;
    }

    /**
     * Central method to handle grading, tagging and risk assessment
     */
    private boolean processStudentData(Student student) {
        // Calculate pending fee just in case
        if (student.getFeeDetails() != null) {
            double total = student.getFeeDetails().getTotalFee();
            double paid = student.getFeeDetails().getPaidAmount();
            student.getFeeDetails().setPendingFee(total - paid);
            if (student.getFeeDetails().getPendingFee() > 0) {
                // Check if actually overdue? For now if pending > 0, status PENDING or OVERDUE
                if (!"OVERDUE".equals(student.getFeeDetails().getFeeStatus().name())) {
                    student.getFeeDetails().setFeeStatus(com.sms.model.FeeDetails.FeeStatus.PENDING);
                }
            } else {
                student.getFeeDetails().setFeeStatus(com.sms.model.FeeDetails.FeeStatus.PAID);
            }
        }

        // Calculate Overall Skill Score
        if (student.getSkillDetails() != null) {
            // Simple logic: Communication + Leadership (out of 20) -> scaled
            // Programming Level: Beginner=5, Inter=7, Advanced=10
            double skillScore = 0;
            switch (student.getSkillDetails().getProgrammingLevel().toUpperCase()) {
                case "ADVANCED":
                    skillScore += 10;
                    break;
                case "INTERMEDIATE":
                    skillScore += 7;
                    break;
                default:
                    skillScore += 5;
                    break;
            }
            skillScore += student.getSkillDetails().getCommunicationRating(); // Max 10
            skillScore += student.getSkillDetails().getLeadershipRating(); // Max 10
            // Total max 30. Normalized to 10 scale? Or keep as raw score.
            // Let's normalize to 100 for storage
            student.getSkillDetails().setOverallSkillScore((skillScore / 30.0) * 100.0);
        }

        return true;
    }

    private void calculateDerivedAttributes(Student s) {
        // 1. Calculate Grade
        // Weight: CGPA(40%), Sports(20%), Skills(30%), Attendance(10%)
        double score = 0;

        // CGPA (Scale 10) -> * 10 -> 100 * 0.4
        if (s.getAcademicDetails() != null) {
            score += (s.getAcademicDetails().getCurrentCgpa() * 10) * 0.4;
            // Attendance (Scale 100) * 0.1
            score += s.getAcademicDetails().getAttendancePercentage() * 0.1;
        }

        // Skills (Scale 100) * 0.3
        if (s.getSkillDetails() != null) {
            score += s.getSkillDetails().getOverallSkillScore() * 0.3;
        }

        // Sports (Participation = 100 * 0.2)
        if (s.getSportsDetails() != null && s.getSportsDetails().isSportsParticipant()) {
            score += 100 * 0.2;
        }

        String grade;
        if (score >= 90)
            grade = "A+";
        else if (score >= 80)
            grade = "A";
        else if (score >= 70)
            grade = "B";
        else if (score >= 60)
            grade = "C";
        else
            grade = "D";
        s.setCalculatedGrade(grade);

        // 2. Smart Tagging
        List<String> tags = new ArrayList<>();
        if (s.getAcademicDetails().getCurrentCgpa() >= 9.0)
            tags.add("ACADEMIC_TOPPER");
        if (s.getSportsDetails().isSportsParticipant() &&
                ("STATE".equalsIgnoreCase(s.getSportsDetails().getLevel())
                        || "NATIONAL".equalsIgnoreCase(s.getSportsDetails().getLevel()))) {
            tags.add("SPORTS_CHAMP");
        }
        if (score >= 85 && s.getSportsDetails().isSportsParticipant())
            tags.add("ALL_ROUNDER");
        if (s.getSkillDetails().getOverallSkillScore() >= 90)
            tags.add("SKILL_FOCUSED");

        // 3. Risk Indicator
        // Backlogs > 2, Attendance < 60, Fee Pending > Threshold (e.g., 50000 -> Just
        // check if Pending)
        boolean atRisk = false;
        if (s.getAcademicDetails().getBacklogs() > 2)
            atRisk = true;
        if (s.getAcademicDetails().getAttendancePercentage() < 60.0)
            atRisk = true;
        if (s.getFeeDetails().getPendingFee() > 10000 && "OVERDUE".equals(s.getFeeDetails().getFeeStatus().name())) {
            // Let's say if overdue and significant amount
            // Just "Fee Pending > threshold" per req.
        }

        // Combined risk logic for tagging
        if (atRisk) {
            tags.add("AT_RISK_STUDENT");
            s.setRiskFactor(true);
        } else {
            s.setRiskFactor(false);
        }

        s.setSmartTags(String.join(", ", tags));
    }
}
