package dao;
import db.DBConnection;
import java.sql.*;

public class MaintenanceDAO {

    public double getDueAmount(String flat_no) {
        String query = "SELECT due_amount FROM maintenance WHERE flat_no = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, flat_no);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("dueAmount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updatePaymentStatus(String flat_no, boolean status) {
        String query = "UPDATE maintenance SET isPaid = ? WHERE flat_no = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, status);
            stmt.setString(2, flat_no);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
