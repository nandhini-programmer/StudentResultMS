// package com.srms.model;

// public class Subject {
    
// }
package com.srms.model;

public class Subject {
    private int subjectId;
    private String subjectCode;
    private String subjectName;
    private int totalMarks;
    private int passingMarks;
    
    // Constructors
    public Subject() {}
    
    public Subject(String subjectCode, String subjectName, int totalMarks, int passingMarks) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.totalMarks = totalMarks;
        this.passingMarks = passingMarks;
    }
    
    // Getters and Setters
    public int getSubjectId() { return subjectId; }
    public void setSubjectId(int subjectId) { this.subjectId = subjectId; }
    
    public String getSubjectCode() { return subjectCode; }
    public void setSubjectCode(String subjectCode) { this.subjectCode = subjectCode; }
    
    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }
    
    public int getTotalMarks() { return totalMarks; }
    public void setTotalMarks(int totalMarks) { this.totalMarks = totalMarks; }
    
    public int getPassingMarks() { return passingMarks; }
    public void setPassingMarks(int passingMarks) { this.passingMarks = passingMarks; }
    
    @Override
    public String toString() {
        return String.format("Subject[ID=%d, Code=%s, Name=%s, Total=%d, Passing=%d]",
                subjectId, subjectCode, subjectName, totalMarks, passingMarks);
    }
}