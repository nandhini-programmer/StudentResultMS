// package com.srms.dao;

// public class ResultDAO {
    
// }
package com.srms.dao;

import com.srms.model.Result;
import com.srms.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResultDAO {
    
    // Add new result
    public boolean addResult(Result result) {
        String sql = "INSERT INTO results (student_id, subject_id, marks_obtained, exam_date, " +
                     "semester, academic_year, grade, remarks) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, result.getStudentId());
            pstmt.setInt(2, result.getSubjectId());
            pstmt.setDouble(3, result.getMarksObtained());
            pstmt.setDate(4, Date.valueOf(result.getExamDate()));
            pstmt.setString(5, result.getSemester());
            pstmt.setString(6, result.getAcademicYear());
            pstmt.setString(7, result.getGrade());
            pstmt.setString(8, result.getRemarks());
            
            int rows = pstmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding result: " + e.getMessage());
            return false;
        }
    }
    
    // Get results by student roll number
    public List<Result> getResultsByRollNumber(String rollNumber) {
        List<Result> results = new ArrayList<>();
        String sql = "SELECT r.*, s.roll_number, CONCAT(s.first_name, ' ', s.last_name) as student_name, " +
                     "sub.subject_code, sub.subject_name, sub.total_marks, sub.passing_marks " +
                     "FROM results r " +
                     "JOIN students s ON r.student_id = s.student_id " +
                     "JOIN subjects sub ON r.subject_id = sub.subject_id " +
                     "WHERE s.roll_number = ? " +
                     "ORDER BY r.exam_date";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, rollNumber);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Result result = extractResultFromResultSet(rs);
                results.add(result);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching results: " + e.getMessage());
        }
        
        return results;
    }
    
    // Get all results
    public List<Result> getAllResults() {
        List<Result> results = new ArrayList<>();
        String sql = "SELECT r.*, CONCAT(s.first_name, ' ', s.last_name) as student_name, " +
                     "s.roll_number, sub.subject_name, sub.subject_code " +
                     "FROM results r " +
                     "JOIN students s ON r.student_id = s.student_id " +
                     "JOIN subjects sub ON r.subject_id = sub.subject_id " +
                     "ORDER BY r.exam_date DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Result result = extractResultFromResultSet(rs);
                results.add(result);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching results: " + e.getMessage());
        }
        
        return results;
    }
    
    // Get results by semester
    public List<Result> getResultsBySemester(String semester, String academicYear) {
        List<Result> results = new ArrayList<>();
        String sql = "SELECT r.*, CONCAT(s.first_name, ' ', s.last_name) as student_name, " +
                     "s.roll_number, sub.subject_name " +
                     "FROM results r " +
                     "JOIN students s ON r.student_id = s.student_id " +
                     "JOIN subjects sub ON r.subject_id = sub.subject_id " +
                     "WHERE r.semester = ? AND r.academic_year = ? " +
                     "ORDER BY s.roll_number";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, semester);
            pstmt.setString(2, academicYear);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Result result = extractResultFromResultSet(rs);
                results.add(result);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching results: " + e.getMessage());
        }
        
        return results;
    }
    
    // Update result
    public boolean updateResult(int resultId, double marks, String grade) {
        String sql = "UPDATE results SET marks_obtained=?, grade=? WHERE result_id=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, marks);
            pstmt.setString(2, grade);
            pstmt.setInt(3, resultId);
            
            int rows = pstmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating result: " + e.getMessage());
            return false;
        }
    }
    
    // Delete result
    public boolean deleteResult(int resultId) {
        String sql = "DELETE FROM results WHERE result_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, resultId);
            int rows = pstmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting result: " + e.getMessage());
            return false;
        }
    }
    
    // Calculate grade based on marks
    public String calculateGrade(double marks) {
        if (marks >= 90) return "A+";
        else if (marks >= 80) return "A";
        else if (marks >= 70) return "B";
        else if (marks >= 60) return "C";
        else if (marks >= 40) return "D";
        else return "F";
    }
    
    // Helper method to extract Result from ResultSet
    private Result extractResultFromResultSet(ResultSet rs) throws SQLException {
        Result result = new Result();
        result.setResultId(rs.getInt("result_id"));
        result.setStudentId(rs.getInt("student_id"));
        result.setSubjectId(rs.getInt("subject_id"));
        result.setMarksObtained(rs.getDouble("marks_obtained"));
        
        Date examDate = rs.getDate("exam_date");
        if (examDate != null) {
            result.setExamDate(examDate.toLocalDate());
        }
        
        result.setSemester(rs.getString("semester"));
        result.setAcademicYear(rs.getString("academic_year"));
        result.setGrade(rs.getString("grade"));
        result.setRemarks(rs.getString("remarks"));
        
        // Additional info if available
        try {
            result.setStudentName(rs.getString("student_name"));
            result.setSubjectName(rs.getString("subject_name"));
        } catch (SQLException ignored) {}
        
        return result;
    }
}