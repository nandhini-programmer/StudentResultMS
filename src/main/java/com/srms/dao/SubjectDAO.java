// // package com.srms.dao;

// // public class SubjectDAO {
    
// // }
// package com.srms.dao;

// import com.srms.model.Subject;
// import com.srms.util.DatabaseConnection;

// import java.sql.*;
// import java.util.ArrayList;
// import java.util.List;

// public class SubjectDAO {
    
//     // Add new subject
//     public boolean addSubject(Subject subject) {
//         String sql = "INSERT INTO subjects (subject_code, subject_name, total_marks, passing_marks) " +
//                      "VALUES (?, ?, ?, ?)";
        
//         try (Connection conn = DatabaseConnection.getConnection();
//              PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
//             pstmt.setString(1, subject.getSubjectCode());
//             pstmt.setString(2, subject.getSubjectName());
//             pstmt.setInt(3, subject.getTotalMarks());
//             pstmt.setInt(4, subject.getPassingMarks());
            
//             int rows = pstmt.executeUpdate();
//             return rows > 0;
            
//         } catch (SQLException e) {
//             System.err.println("Error adding subject: " + e.getMessage());
//             return false;
//         }
//     }
    
//     // Get all subjects
//     public List<Subject> getAllSubjects() {
//         List<Subject> subjects = new ArrayList<>();
//         String sql = "SELECT * FROM subjects ORDER BY subject_code";
        
//         try (Connection conn = DatabaseConnection.getConnection();
//              Statement stmt = conn.createStatement();
//              ResultSet rs = stmt.executeQuery(sql)) {
            
//             while (rs.next()) {
//                 Subject subject = extractSubjectFromResultSet(rs);
//                 subjects.add(subject);
//             }
            
//         } catch (SQLException e) {
//             System.err.println("Error fetching subjects: " + e.getMessage());
//         }
        
//         return subjects;
//     }
    
//     // Get subject by code
//     public Subject getSubjectByCode(String subjectCode) {
//         String sql
package com.srms.dao;

import com.srms.model.Subject;
import com.srms.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectDAO {
    
    // Add new subject
    public boolean addSubject(Subject subject) {
        String sql = "INSERT INTO subjects (subject_code, subject_name, total_marks, passing_marks) " +
                     "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, subject.getSubjectCode());
            pstmt.setString(2, subject.getSubjectName());
            pstmt.setInt(3, subject.getTotalMarks());
            pstmt.setInt(4, subject.getPassingMarks());
            
            int rows = pstmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error adding subject: " + e.getMessage());
            return false;
        }
    }
    
    // Get all subjects
    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        String sql = "SELECT * FROM subjects ORDER BY subject_code";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Subject subject = extractSubjectFromResultSet(rs);
                subjects.add(subject);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching subjects: " + e.getMessage());
        }
        
        return subjects;
    }
    
    // Get subject by code
    public Subject getSubjectByCode(String subjectCode) {
        String sql = "SELECT * FROM subjects WHERE subject_code = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, subjectCode);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractSubjectFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching subject: " + e.getMessage());
        }
        
        return null;
    }
    
    // Get subject by ID
    public Subject getSubjectById(int subjectId) {
        String sql = "SELECT * FROM subjects WHERE subject_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, subjectId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractSubjectFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Error fetching subject: " + e.getMessage());
        }
        
        return null;
    }
    
    // Update subject
    public boolean updateSubject(Subject subject) {
        String sql = "UPDATE subjects SET subject_name=?, total_marks=?, passing_marks=? " +
                     "WHERE subject_code=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, subject.getSubjectName());
            pstmt.setInt(2, subject.getTotalMarks());
            pstmt.setInt(3, subject.getPassingMarks());
            pstmt.setString(4, subject.getSubjectCode());
            
            int rows = pstmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error updating subject: " + e.getMessage());
            return false;
        }
    }
    
    // Delete subject
    public boolean deleteSubject(String subjectCode) {
        String sql = "DELETE FROM subjects WHERE subject_code = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, subjectCode);
            int rows = pstmt.executeUpdate();
            return rows > 0;
            
        } catch (SQLException e) {
            System.err.println("Error deleting subject: " + e.getMessage());
            return false;
        }
    }
    
    // Helper method to extract Subject from ResultSet
    private Subject extractSubjectFromResultSet(ResultSet rs) throws SQLException {
        Subject subject = new Subject();
        subject.setSubjectId(rs.getInt("subject_id"));
        subject.setSubjectCode(rs.getString("subject_code"));
        subject.setSubjectName(rs.getString("subject_name"));
        subject.setTotalMarks(rs.getInt("total_marks"));
        subject.setPassingMarks(rs.getInt("passing_marks"));
        return subject;
    }
}