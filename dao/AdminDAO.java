package dao;

import db.DBConnection;
import java.sql.*;

public class AdminDAO {
    public boolean validateAdmin(String username, String password) {
        String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true if admin exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
