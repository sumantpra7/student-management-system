package com.sms.main;

import com.sms.model.*;
import com.sms.service.AuthService;
import com.sms.service.StudentService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final AuthService authService = new AuthService();
    private static final StudentService studentService = new StudentService();

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("   STUDENT MANAGEMENT SYSTEM (SMS) v1.0   ");
        System.out.println("==========================================");

        while (true) {
            if (!AuthService.isLoggedIn()) {
                login();
            } else {
                showMenu();
            }
        }
    }

    private static void login() {
        System.out.println("\n--- LOGIN ---");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (authService.login(email, password)) {
            System.out.println("Login Successful! Welcome " + AuthService.getCurrentUser().getEmail());
        } else {
            System.out.println("Invalid Credentials. Please try again.");
            // Optional: Exit after n tries
        }
    }

    private static void showMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Add Student");
        System.out.println("2. Update Student Details");
        System.out.println("3. View Student by ID");
        System.out.println("4. View All Students");
        System.out.println("5. Fee Payment Update");
        System.out.println("6. Generate Student Summary"); 
        System.out.println("7. Filter Students");
        System.out.println("8. Delete Student");
        System.out.println("9. Logout");
        System.out.println("0. Exit");

        System.out.print("Select Option: ");
        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid Input.");
            return;
        }

        User.Role role = AuthService.getCurrentUser().getRole();

        switch (choice) {
            case 1:
                if (role == User.Role.ADMIN)
                    addStudent();
                else
                    System.out.println("Access Denied: Admin only.");
                break;
            case 2:
                if (role == User.Role.ADMIN)
                    System.out.println("Feature: Update Student (Implement logic similar to Add)"); 
                else
                    System.out.println("Access Denied: Admin only.");
                break;
            case 3:
                viewStudentById();
                break;
            case 4:
                viewAllStudents();
                break;
            case 5:
                if (role == User.Role.ADMIN)
                    updateFee();
                else
                    System.out.println("Access Denied: Admin only.");
                break;
            case 6:
                generateSummary();
                break;
            case 7:
                filterMenu();
                break;
            case 8:
                if (role == User.Role.ADMIN)
                    deleteStudent();
                else
                    System.out.println("Access Denied: Admin only.");
                break;
            case 9:
                authService.logout();
                System.out.println("Logged out.");
                break;
            case 0:
                System.out.println("Exiting System. Goodbye!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid Choice.");
        }
    }

    private static void addStudent() {
        System.out.println("\n--- ADD NEW STUDENT ---");
        Student s = new Student();

       
        System.out.print("Full Name: ");
        s.setFullName(scanner.nextLine());
        System.out.print("DOB (YYYY-MM-DD): ");
        s.setDob(scanner.nextLine());
        System.out.print("Gender: ");
        s.setGender(scanner.nextLine());
        System.out.print("Phone: ");
        s.setPhoneNumber(scanner.nextLine());
        System.out.print("Email: ");
        s.setEmail(scanner.nextLine());
        System.out.print("Address: ");
        s.setAddress(scanner.nextLine());
        System.out.print("College ID Card No: ");
        s.setCollegeIdCardNo(scanner.nextLine());

        
        AcademicDetails ad = new AcademicDetails();
        System.out.print("Course: ");
        ad.setCourse(scanner.nextLine());
        System.out.print("Semester: ");
        ad.setSemester(Integer.parseInt(scanner.nextLine()));
        System.out.print("Current CGPA: ");
        ad.setCurrentCgpa(Double.parseDouble(scanner.nextLine()));
        System.out.print("Backlogs: ");
        ad.setBacklogs(Integer.parseInt(scanner.nextLine()));
        System.out.print("Attendance %: ");
        ad.setAttendancePercentage(Double.parseDouble(scanner.nextLine()));
        System.out.print("Academic Year: ");
        ad.setAcademicYear(scanner.nextLine());
        s.setAcademicDetails(ad);

        FeeDetails fd = new FeeDetails();
        System.out.print("Total Fee: ");
        fd.setTotalFee(Double.parseDouble(scanner.nextLine()));
        System.out.print("Paid Amount: ");
        fd.setPaidAmount(Double.parseDouble(scanner.nextLine()));
        fd.setLastPaymentDate("N/A"); // Default
        fd.setFeeStatus(FeeDetails.FeeStatus.PENDING); // Default, logic calculates actual
        s.setFeeDetails(fd);

      
        SportsDetails sd = new SportsDetails();
        System.out.print("Sports Participant? (yes/no): ");
        String isSports = scanner.nextLine();
        sd.setSportsParticipant(isSports.equalsIgnoreCase("yes"));
        if (sd.isSportsParticipant()) {
            System.out.print("Sports Name: ");
            sd.setSportsName(scanner.nextLine());
            System.out.print("Level (College/State/National): ");
            sd.setLevel(scanner.nextLine());
            System.out.print("Extra Curriculars: ");
            sd.setExtraCurriculars(scanner.nextLine());
        } else {
            sd.setSportsName("None");
            sd.setLevel("None");
            sd.setExtraCurriculars("None");
        }
        s.setSportsDetails(sd);

       
        SkillDetails sk = new SkillDetails();
        System.out.print("Programming Level (Beginner/Intermediate/Advanced): ");
        sk.setProgrammingLevel(scanner.nextLine());
        System.out.print("Communication Rating (1-10): ");
        sk.setCommunicationRating(Integer.parseInt(scanner.nextLine()));
        System.out.print("Leadership Rating (1-10): ");
        sk.setLeadershipRating(Integer.parseInt(scanner.nextLine()));
        System.out.print("Technical Skills (Java, SQL, etc.): ");
        sk.setTechnicalSkills(scanner.nextLine());
        s.setSkillDetails(sk);

        if (studentService.addStudent(s)) {
            System.out.println("Student Added Successfully!");
        } else {
            System.out.println("Failed to Add Student.");
        }
    }

    private static void viewStudentById() {
        System.out.print("Enter Student ID: ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            Student s = studentService.getStudentById(id);
            if (s != null)
                displayStudent(s);
            else
                System.out.println("Student Not Found.");
        } catch (Exception e) {
            System.out.println("Invalid ID.");
        }
    }

    private static void viewAllStudents() {
        List<Student> list = studentService.getAllStudents();
        if (list.isEmpty())
            System.out.println("No Students Found.");
        else
            list.forEach(Main::displayStudent);
    }

    private static void updateFee() {
        System.out.print("Enter Student ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        Student s = studentService.getStudentById(id);
        if (s != null) {
            System.out.println("Current Paid: " + s.getFeeDetails().getPaidAmount() + ", Total: "
                    + s.getFeeDetails().getTotalFee());
            System.out.print("Enter New Payment Amount (Add to existing): ");
            double amount = Double.parseDouble(scanner.nextLine());
            s.getFeeDetails().setPaidAmount(s.getFeeDetails().getPaidAmount() + amount);
            s.getFeeDetails().setLastPaymentDate("2024-12-17"); // Use actual date helper in real app

            
            if (studentService.updateStudent(s))
                System.out.println("Fee Updated.");
            else
                System.out.println("Update Failed.");
        } else {
            System.out.println("Student Not Found.");
        }
    }

    private static void generateSummary() {
        System.out.print("Enter Student ID Card No: "); // Search by ID Card as per requirements
        String card = scanner.nextLine();
        Student s = studentService.getStudentByIdCard(card);
        if (s != null) {
            System.out.println("\n------- STUDENT SUMMARY REPORT -------");
            System.out.println("Name: " + s.getFullName());
            System.out.println("ID Card No: " + s.getCollegeIdCardNo());
            System.out.println("CGPA: " + s.getAcademicDetails().getCurrentCgpa());
            System.out.println("Backlogs: " + s.getAcademicDetails().getBacklogs());
            System.out.println(
                    "Sports: " + s.getSportsDetails().getSportsName() + " (" + s.getSportsDetails().getLevel() + ")");
            System.out.println("Skills: " + s.getSkillDetails().getTechnicalSkills());
            System.out.println("Overall Grade: " + s.getCalculatedGrade());
            System.out.println("Status/Tags: " + s.getSmartTags());
            if (s.isRiskFactor())
                System.out.println("RISK ALERT: STUDENT IS ACADEMIC RISK!");
            System.out.println("--------------------------------------");
        } else {
            System.out.println("Student Not Found.");
        }
    }

    private static void filterMenu() {
        System.out.println("1. Filter by Pending Fees");
        System.out.println("2. Filter by Grade");
        System.out.println("3. Filter Sports Students");
        System.out.print("Choice: ");
        int c = Integer.parseInt(scanner.nextLine());
        List<Student> list;
        switch (c) {
            case 1:
                list = studentService.filterPendingFees();
                break;
            case 2:
                System.out.print("Enter Grade (A+, A, B, C): ");
                String g = scanner.nextLine();
                list = studentService.filterByGrade(g);
                break;
            case 3:
                list = studentService.filterSportsStudents();
                break;
            default:
                list = List.of();
        }
        if (list.isEmpty())
            System.out.println("No matching students.");
        else
            list.forEach(Main::displayStudent);
    }

    private static void deleteStudent() {
        System.out.print("Enter Student ID to Delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (studentService.deleteStudent(id))
            System.out.println("Deleted.");
        else
            System.out.println("Failed to Delete.");
    }

    private static void displayStudent(Student s) {
        System.out
                .println("\nID: " + s.getStudentId() + " | " + s.getFullName() + " | Grade: " + s.getCalculatedGrade());
        System.out.println("Details: " + s.getAcademicDetails().getCourse() + " - "
                + s.getAcademicDetails().getSemester() + " Sem");
    }
}
