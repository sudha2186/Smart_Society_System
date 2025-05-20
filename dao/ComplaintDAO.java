package dao;

import model.Complaint;
import db.DBConnection;
import java.sql.*;
import java.util.*;

public class ComplaintDAO {

    public boolean addComplaint(Complaint complaint) {
        try (Connection con = DBConnection.getConnection()) {
            String query = "INSERT INTO complaints(flat_no, description, status) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, complaint.getFlatNo());
            ps.setString(2, complaint.getDescription());
            ps.setString(3, complaint.getStatus());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

   public Complaint getComplaintById(int complaintID) {
        String query = "SELECT * FROM complaints WHERE complaint_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, complaintID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Complaint complaint = new Complaint();
                complaint.setComplaintID(rs.getInt("complaint_id"));
                complaint.setFlatNo(rs.getString("flat_no"));
                complaint.setDescription(rs.getString("description"));
                complaint.setStatus(rs.getString("status"));
                return complaint;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateComplaintStatus(Complaint complaint) {
        String query = "UPDATE complaints SET status = ? WHERE complaint_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, complaint.getStatus());
            stmt.setInt(2, complaint.getComplaintID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Complaint> getAllComplaints() {
        List<Complaint> complaints = new ArrayList<>();
        String query = "SELECT c.complaint_id, c.flat_no, IFNULL(u.username, 'Unknown') AS username, c.description, c.status " +
               "FROM complaints c " +
               //"LEFT JOIN users u ON c.flat_no = u.flat_no";
		"INNER JOIN users u ON c.flat_no = u.flat_no";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Complaint complaint = new Complaint();
                complaint.setComplaintID(rs.getInt("complaint_id"));
                complaint.setFlatNo(rs.getString("flat_no"));
                complaint.setUsername(rs.getString("username")); // NEW
                complaint.setDescription(rs.getString("description"));
                complaint.setStatus(rs.getString("status"));
                complaints.add(complaint);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return complaints;
    }
}
