// package com.srms;

// public class Main {
    
// }
package com.srms;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.srms.dao.ResultDAO;
import com.srms.dao.StudentDAO;
import com.srms.dao.SubjectDAO;
import com.srms.model.Result;
import com.srms.model.Student;
import com.srms.model.Subject;
import com.srms.util.DatabaseConnection;

public class Main {
    
    private static Scanner scanner = new Scanner(System.in);
    private static StudentDAO studentDAO = new StudentDAO();
    private static SubjectDAO subjectDAO = new SubjectDAO();
    private static ResultDAO resultDAO = new ResultDAO();
    
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║  STUDENT RESULT MANAGEMENT SYSTEM (SRMS)     ║");
        System.out.println("╚═══════════════════════════════════════════════╝");
        
        // Test database connection
        if (DatabaseConnection.getConnection() == null) {
            System.out.println("❌ Cannot connect to database. Exiting...");
            return;
        }
        
        boolean running = true;
        
        while (running) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    studentManagementMenu();
                    break;
                case 2:
                    subjectManagementMenu();
                    break;
                case 3:
                    resultManagementMenu();
                    break;
                case 4:
                    reportMenu();
                    break;
                case 5:
                    running = false;
                    System.out.println("\n👋 Thank you for using SRMS!");
                    break;
                default:
                    System.out.println("❌ Invalid choice! Try again.");
            }
        }
        
        DatabaseConnection.closeConnection();
        scanner.close();
    }
    // The database password is not set in this Main class.
    // It is typically configured in the DatabaseConnection class, often in a properties file or as a constant.
    // Check com.srms.util.DatabaseConnection for the password or connection details.
    // ==================== MENUS ====================
    // No additional menu code needed here; all menus are implemented below.
    private static void displayMainMenu() {
        System.out.println("\n" + repeat("=", 50));
        System.out.println("                   MAIN MENU");
        System.out.println(repeat("=", 50));
        System.out.println("1. Student Management");
        System.out.println("2. Subject Management");
        System.out.println("3. Result Management");
        System.out.println("4. Reports & Search");
        System.out.println("5. Exit");
        System.out.println(repeat("=", 50));
    }
    
    // ==================== STUDENT MANAGEMENT ====================
    
    private static void studentManagementMenu() {
        System.out.println("\n--- STUDENT MANAGEMENT ---");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Back to Main Menu");
        
        int choice = getIntInput("Enter choice: ");
        
        switch (choice) {
            case 1: addStudent(); break;
            case 2: viewAllStudents(); break;
            case 3: searchStudent(); break;
            case 4: updateStudent(); break;
            case 5: deleteStudent(); break;
            case 6: return;
            default: System.out.println("Invalid choice!");
        }
    }
    
    private static void addStudent() {
        System.out.println("\n--- ADD NEW STUDENT ---");
        
        System.out.print("Roll Number: ");
        String rollNumber = scanner.nextLine();
        
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        
        System.out.print("Date of Birth (YYYY-MM-DD): ");
        String dobStr = scanner.nextLine();
        LocalDate dob = LocalDate.parse(dobStr);
        
        System.out.print("Gender (Male/Female/Other): ");
        String gender = scanner.nextLine();
        
        System.out.print("Address: ");
        String address = scanner.nextLine();
        
        Student student = new Student(rollNumber, firstName, lastName, email, 
                                     phone, dob, gender, address);
        
        if (studentDAO.addStudent(student)) {
            System.out.println("✅ Student added successfully!");
        } else {
            System.out.println("❌ Failed to add student!");
        }
    }
    
    private static void viewAllStudents() {
        System.out.println("\n--- ALL STUDENTS ---");
        List<Student> students = studentDAO.getAllStudents();
        
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        
        System.out.println(repeat("-", 120));
        System.out.printf("%-12s %-15s %-15s %-30s %-15s %-12s%n",
                "Roll No", "First Name", "Last Name", "Email", "Phone", "Gender");
        System.out.println(repeat("-", 120));
        
        for (Student s : students) {
            System.out.printf("%-12s %-15s %-15s %-30s %-15s %-12s%n",
                    s.getRollNumber(), s.getFirstName(), s.getLastName(),
                    s.getEmail(), s.getPhone(), s.getGender());
        }
        System.out.println(repeat("-", 120));
        System.out.println("Total Students: " + students.size());
    }
    
    private static void searchStudent() {
        System.out.print("\nEnter Roll Number or Name to search: ");
        String search = scanner.nextLine();
        
        // Try searching by roll number first
        Student student = studentDAO.getStudentByRollNumber(search);
        
        if (student != null) {
            displayStudentDetails(student);
        } else {
            // Search by name
            List<Student> students = studentDAO.searchStudentsByName(search);
            if (students.isEmpty()) {
                System.out.println("❌ No students found!");
            } else {
                System.out.println("\n✅ Found " + students.size() + " student(s):");
                for (Student s : students) {
                    displayStudentDetails(s);
                }
            }
        }
    }
    
    private static void displayStudentDetails(Student s) {
        System.out.println("\n" + repeat("=", 60));
        System.out.println("Roll Number  : " + s.getRollNumber());
        System.out.println("Name         : " + s.getFirstName() + " " + s.getLastName());
        System.out.println("Email        : " + s.getEmail());
        System.out.println("Phone        : " + s.getPhone());
        System.out.println("DOB          : " + s.getDateOfBirth());
        System.out.println("Gender       : " + s.getGender());
        System.out.println("Address      : " + s.getAddress());
        System.out.println(repeat("=", 60));
    }
    
    private static void updateStudent() {
        System.out.print("\nEnter Roll Number to update: ");
        String rollNumber = scanner.nextLine();
        
        Student student = studentDAO.getStudentByRollNumber(rollNumber);
        if (student == null) {
            System.out.println("❌ Student not found!");
            return;
        }
        
        System.out.println("Current details:");
        displayStudentDetails(student);
        
        System.out.println("\nEnter new details (press Enter to keep current):");
        
        System.out.print("First Name [" + student.getFirstName() + "]: ");
        String firstName = scanner.nextLine();
        if (!firstName.isEmpty()) student.setFirstName(firstName);
        
        System.out.print("Last Name [" + student.getLastName() + "]: ");
        String lastName = scanner.nextLine();
        if (!lastName.isEmpty()) student.setLastName(lastName);
        
        System.out.print("Email [" + student.getEmail() + "]: ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) student.setEmail(email);
        
        System.out.print("Phone [" + student.getPhone() + "]: ");
        String phone = scanner.nextLine();
        if (!phone.isEmpty()) student.setPhone(phone);
        
        if (studentDAO.updateStudent(student)) {
            System.out.println("✅ Student updated successfully!");
        } else {
            System.out.println("❌ Failed to update student!");
        }
    }
    
    private static void deleteStudent() {
        System.out.print("\nEnter Roll Number to delete: ");
        String rollNumber = scanner.nextLine();
        
        Student student = studentDAO.getStudentByRollNumber(rollNumber);
        if (student == null) {
            System.out.println("❌ Student not found!");
            return;
        }
        
        displayStudentDetails(student);
        System.out.print("\nAre you sure you want to delete? (yes/no): ");
        String confirm = scanner.nextLine();
        
        if (confirm.equalsIgnoreCase("yes")) {
            if (studentDAO.deleteStudent(rollNumber)) {
                System.out.println("✅ Student deleted successfully!");
            } else {
                System.out.println("❌ Failed to delete student!");
            }
        } else {
            System.out.println("Delete cancelled.");
        }
    }
    
    // ==================== SUBJECT MANAGEMENT ====================
    
    private static void subjectManagementMenu() {
        System.out.println("\n--- SUBJECT MANAGEMENT ---");
        System.out.println("1. Add Subject");
        System.out.println("2. View All Subjects");
        System.out.println("3. Update Subject");
        System.out.println("4. Delete Subject");
        System.out.println("5. Back to Main Menu");
        
        int choice = getIntInput("Enter choice: ");
        
        switch (choice) {
            case 1: addSubject(); break;
            case 2: viewAllSubjects(); break;
            case 3: updateSubject(); break;
            case 4: deleteSubject(); break;
            case 5: return;
            default: System.out.println("Invalid choice!");
        }
    }
    
    private static void addSubject() {
        System.out.println("\n--- ADD NEW SUBJECT ---");
        
        System.out.print("Subject Code: ");
        String code = scanner.nextLine();
        
        System.out.print("Subject Name: ");
        String name = scanner.nextLine();
        
        int totalMarks = getIntInput("Total Marks: ");
        int passingMarks = getIntInput("Passing Marks: ");
        
        Subject subject = new Subject(code, name, totalMarks, passingMarks);
        
        if (subjectDAO.addSubject(subject)) {
            System.out.println("✅ Subject added successfully!");
        } else {
            System.out.println("❌ Failed to add subject!");
        }
    }
    
    private static void viewAllSubjects() {
        System.out.println("\n--- ALL SUBJECTS ---");
        List<Subject> subjects = subjectDAO.getAllSubjects();
        
        if (subjects.isEmpty()) {
            System.out.println("No subjects found.");
            return;
        }
        
        System.out.println(repeat("-", 80));
        System.out.printf("%-12s %-35s %-15s %-15s%n",
                "Code", "Subject Name", "Total Marks", "Passing Marks");
        System.out.println(repeat("-", 80));
        
        for (Subject sub : subjects) {
            System.out.printf("%-12s %-35s %-15d %-15d%n",
                    sub.getSubjectCode(), sub.getSubjectName(),
                    sub.getTotalMarks(), sub.getPassingMarks());
        }
        System.out.println(repeat("-", 80));
    }
    
    private static void updateSubject() {
        System.out.print("\nEnter Subject Code to update: ");
        String code = scanner.nextLine();
        
        Subject subject = subjectDAO.getSubjectByCode(code);
        if (subject == null) {
            System.out.println("❌ Subject not found!");
            return;
        }
        
        System.out.println("Current: " + subject);
        System.out.print("New Subject Name: ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) subject.setSubjectName(name);
        
        if (subjectDAO.updateSubject(subject)) {
            System.out.println("✅ Subject updated!");
        } else {
            System.out.println("❌ Update failed!");
        }
    }
    
    private static void deleteSubject() {
        System.out.print("\nEnter Subject Code to delete: ");
        String code = scanner.nextLine();
        
        if (subjectDAO.deleteSubject(code)) {
            System.out.println("✅ Subject deleted!");
        } else {
            System.out.println("❌ Delete failed!");
        }
    }
    
    // ==================== RESULT MANAGEMENT ====================
    
    private static void resultManagementMenu() {
        System.out.println("\n--- RESULT MANAGEMENT ---");
        System.out.println("1. Add Result");
        System.out.println("2. View All Results");
        System.out.println("3. Update Result");
        System.out.println("4. Delete Result");
        System.out.println("5. Back to Main Menu");
        
        int choice = getIntInput("Enter choice: ");
        
        switch (choice) {
            case 1: addResult(); break;
            case 2: viewAllResults(); break;
            case 3: updateResult(); break;
            case 4: deleteResult(); break;
            case 5: return;
            default: System.out.println("Invalid choice!");
        }
    }
    
    private static void addResult() {
        System.out.println("\n--- ADD RESULT ---");
        
        System.out.print("Student Roll Number: ");
        String rollNumber = scanner.nextLine();
        Student student = studentDAO.getStudentByRollNumber(rollNumber);
        
        if (student == null) {
            System.out.println("❌ Student not found!");
            return;
        }
        
        System.out.print("Subject Code: ");
        String subjectCode = scanner.nextLine();
        Subject subject = subjectDAO.getSubjectByCode(subjectCode);
        
        if (subject == null) {
            System.out.println("❌ Subject not found!");
            return;
        }
        
        double marks = getDoubleInput("Marks Obtained: ");
        
        System.out.print("Exam Date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        LocalDate examDate = LocalDate.parse(dateStr);
        
        System.out.print("Semester: ");
        String semester = scanner.nextLine();
        
        System.out.print("Academic Year: ");
        String academicYear = scanner.nextLine();
        
        String grade = resultDAO.calculateGrade(marks);
        
        Result result = new Result(student.getStudentId(), subject.getSubjectId(),
                marks, examDate, semester, academicYear, grade);
        
        if (resultDAO.addResult(result)) {
            System.out.println("✅ Result added successfully! Grade: " + grade);
        } else {
            System.out.println("❌ Failed to add result!");
        }
    }
    
    private static void viewAllResults() {
        System.out.println("\n--- ALL RESULTS ---");
        List<Result> results = resultDAO.getAllResults();
        
        if (results.isEmpty()) {
            System.out.println("No results found.");
            return;
        }
        
        System.out.println(repeat("-", 100));
        System.out.printf("%-25s %-30s %-10s %-10s %-15s%n",
                "Student", "Subject", "Marks", "Grade", "Semester");
        System.out.println(repeat("-", 100));
        
        for (Result r : results) {
            System.out.printf("%-25s %-30s %-10.2f %-10s %-15s%n",
                    r.getStudentName(), r.getSubjectName(),
                    r.getMarksObtained(), r.getGrade(), r.getSemester());
        }
        System.out.println(repeat("-", 100));
    }
    
    private static void updateResult() {
        int resultId = getIntInput("\nEnter Result ID to update: ");
        double newMarks = getDoubleInput("New Marks: ");
        String newGrade = resultDAO.calculateGrade(newMarks);
        
        if (resultDAO.updateResult(resultId, newMarks, newGrade)) {
            System.out.println("✅ Result updated! New Grade: " + newGrade);
        } else {
            System.out.println("❌ Update failed!");
        }
    }
    
    private static void deleteResult() {
        int resultId = getIntInput("\nEnter Result ID to delete: ");
        
        if (resultDAO.deleteResult(resultId)) {
            System.out.println("✅ Result deleted!");
        } else {
            System.out.println("❌ Delete failed!");
        }
    }
    
    // ==================== REPORTS ====================
    
    private static void reportMenu() {
        System.out.println("\n--- REPORTS & SEARCH ---");
        System.out.println("1. Student Report Card");
        System.out.println("2. Semester Results");
        System.out.println("3. Back to Main Menu");
        
        int choice = getIntInput("Enter choice: ");
        
        switch (choice) {
            case 1: studentReportCard(); break;
            case 2: semesterResults(); break;
            case 3: return;
            default: System.out.println("Invalid choice!");
        }
    }
    
    private static void studentReportCard() {
        System.out.print("\nEnter Student Roll Number: ");
        String rollNumber = scanner.nextLine();
        
        Student student = studentDAO.getStudentByRollNumber(rollNumber);
        if (student == null) {
            System.out.println("❌ Student not found!");
            return;
        }
        
        List<Result> results = resultDAO.getResultsByRollNumber(rollNumber);
        
        if (results.isEmpty()) {
            System.out.println("❌ No results found for this student!");
            return;
        }
        
        // Display Report Card
        System.out.println("\n" + repeat("=", 80));
        System.out.println("                        REPORT CARD");
        System.out.println(repeat("=", 80));
        System.out.println("Student Name : " + student.getFirstName() + " " + student.getLastName());
        System.out.println("Roll Number  : " + student.getRollNumber());
        System.out.println("Email        : " + student.getEmail());
        System.out.println(repeat("=", 80));
        
        System.out.printf("%-30s %-12s %-10s %-15s%n", 
                "Subject", "Marks", "Grade", "Semester");
        System.out.println(repeat("-", 80));
        
        double totalMarks = 0;
        int subjectCount = 0;
        int passed = 0;
        int failed = 0;
        
        for (Result r : results) {
            System.out.printf("%-30s %-12.2f %-10s %-15s%n",
                    r.getSubjectName(), r.getMarksObtained(), 
                    r.getGrade(), r.getSemester());
            
            totalMarks += r.getMarksObtained();
            subjectCount++;
            
            if (r.getMarksObtained() >= 40) {
                passed++;
            } else {
                failed++;
            }
        }
        
        System.out.println(repeat("=", 80));
        
        double average = totalMarks / subjectCount;
        double percentage = (totalMarks / (subjectCount * 100)) * 100;
        
        System.out.printf("Total Marks    : %.2f / %d%n", totalMarks, subjectCount * 100);
        System.out.printf("Average Marks  : %.2f%n", average);
        System.out.printf("Percentage     : %.2f%%%n", percentage);
        System.out.printf("Subjects Passed: %d%n", passed);
        System.out.printf("Subjects Failed: %d%n", failed);
        System.out.printf("Overall Status : %s%n", failed == 0 ? "✅ PASSED" : "❌ FAILED");
        System.out.println(repeat("=", 80));
    }
    
    private static void semesterResults() {
        System.out.print("\nEnter Semester (e.g., Semester 1): ");
        String semester = scanner.nextLine();
        
        System.out.print("Enter Academic Year (e.g., 2024-25): ");
        String academicYear = scanner.nextLine();
        
        List<Result> results = resultDAO.getResultsBySemester(semester, academicYear);
        
        if (results.isEmpty()) {
            System.out.println("❌ No results found!");
            return;
        }
        
        System.out.println("\n--- SEMESTER RESULTS: " + semester + " (" + academicYear + ") ---");
        System.out.println(repeat("-", 100));
        System.out.printf("%-25s %-30s %-12s %-10s%n",
                "Student", "Subject", "Marks", "Grade");
        System.out.println(repeat("-", 100));
        
        for (Result r : results) {
            System.out.printf("%-25s %-30s %-12.2f %-10s%n",
                    r.getStudentName(), r.getSubjectName(),
                    r.getMarksObtained(), r.getGrade());
        }
        System.out.println(repeat("-", 100));
        System.out.println("Total Results: " + results.size());
    }
    
    // ==================== HELPER METHODS ====================
    
    private static String repeat(String s, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number!");
            }
        }
    }
    
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number!");
            }
        }
    }
}