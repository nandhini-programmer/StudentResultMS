// package com.srms.model;

// public class Result {
    
// }
package com.srms.model;

import java.time.LocalDate;

public class Result {
    private int resultId;
    private int studentId;
    private int subjectId;
    private double marksObtained;
    private LocalDate examDate;
    private String semester;
    private String academicYear;
    private String grade;
    private String remarks;
    
    // For display purposes
    private String studentName;
    private String subjectName;
    
    // Constructors
    public Result() {}
    
    public Result(int studentId, int subjectId, double marksObtained, 
                  LocalDate examDate, String semester, String academicYear, String grade) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.marksObtained = marksObtained;
        this.examDate = examDate;
        this.semester = semester;
        this.academicYear = academicYear;
        this.grade = grade;
    }
    
    // Getters and Setters
    public int getResultId() { return resultId; }
    public void setResultId(int resultId) { this.resultId = resultId; }
    
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    
    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }
    
    public double getMarksObtained() { return marksObtained; }
    public void setMarksObtained(double marksObtained) { this.marksObtained = marksObtained; }
    
    public LocalDate getExamDate() { return examDate; }
    public void setExamDate(LocalDate examDate) { this.examDate = examDate; }
    
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }
    
    public String getAcademicYear() { return academicYear; }
    public void setAcademicYear(String academicYear) { this.academicYear = academicYear; }
    
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    
    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    
    @Override
    public String toString() {
        return String.format("Result[ID=%d, Student=%s, Subject=%s, Marks=%.2f, Grade=%s]",
                resultId, studentName, subjectName, marksObtained, grade);
    }
}