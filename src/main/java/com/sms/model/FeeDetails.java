package com.sms.model;

public class FeeDetails {
    private int id;
    private int studentId;
    private double totalFee;
    private double paidAmount;
    private double pendingFee;
    private String lastPaymentDate;
    private FeeStatus feeStatus;

    public enum FeeStatus {
        PAID, PENDING, OVERDUE
    }

    public FeeDetails() {
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

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getPendingFee() {
        return pendingFee;
    }

    public void setPendingFee(double pendingFee) {
        this.pendingFee = pendingFee;
    }

    public String getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(String lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public FeeStatus getFeeStatus() {
        return feeStatus;
    }

    public void setFeeStatus(FeeStatus feeStatus) {
        this.feeStatus = feeStatus;
    }
}
