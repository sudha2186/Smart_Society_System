package dao;
import db.DBConnection;
import model.*;
import java.sql.*;

public class UserDAO {

    public User validateUser(String username, String password) {
    User user = null;
    try (Connection conn = DBConnection.getConnection()) {
        String query = "SELECT * FROM users WHERE username=? AND password=?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            String role = rs.getString("role"); // Add a 'role' column in DB
            if ("Admin".equalsIgnoreCase(role)) {
                user = new Admin();
            } else {
                user = new FlatOwner();
                ((FlatOwner) user).setFlatNo(rs.getString("flat_no")); // Assume flat_no is in DB
            }
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return user;
}


	public boolean registerUser(String username, String password) {
    try (Connection conn = DBConnection.getConnection()) {
        // Check if username already exists
        String checkQuery = "SELECT * FROM users WHERE username = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
        checkStmt.setString(1, username);
        ResultSet rs = checkStmt.executeQuery();
        
        if (rs.next()) {
            return false; // Username already exists
        }

        // Insert new user
        String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
        PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
        insertStmt.setString(1, username);
        insertStmt.setString(2, password); 
        insertStmt.executeUpdate();

        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}

}
