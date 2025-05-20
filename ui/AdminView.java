// Importing necessary Java Swing and SQL libraries
package ui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminView extends JFrame {

    // Declaring table and its model to show complaints
    JTable complaintTable;
    DefaultTableModel model;

    // Constructor to initialize the Admin View GUI
    public AdminView() {
        setTitle("Admin View - All Complaints");  // Title for the window
        setSize(800, 400);                         // Set window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Close operation
        setLocationRelativeTo(null);              // Center the frame on screen

        // Define table column names
        String[] columnNames = {"ID", "Name", "Flat No", "Complaint", "Status"};
        model = new DefaultTableModel(columnNames, 0); // Create table model with no rows initially
        complaintTable = new JTable(model);            // Create table using model

        JScrollPane scrollPane = new JScrollPane(complaintTable); // Add scroll for table

        add(scrollPane, BorderLayout.CENTER); // Add table to center of frame

        // Load complaint data from the database
        loadComplaints();

        setVisible(true); // Make frame visible
    }

    // Method to fetch all complaints from the database and populate the table
    private void loadComplaints() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Load JDBC driver and connect to MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/society_db", "root", "");

            // Prepare SQL statement to select all records
            String sql = "SELECT * FROM complaints";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            // Loop through the result set and add rows to the table model
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String flat = rs.getString("flat");
                String complaint = rs.getString("complaint");
                String status = rs.getString("status");

                // Add row to table
                model.addRow(new Object[]{id, name, flat, complaint, status});
            }

        } catch (Exception e) {
            e.printStackTrace(); // Print any errors for debugging
        } finally {
            // Close connections
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }

    // Main method to run this class independently (for testing)
    public static void main(String[] args) {
        new AdminView();
    }
}
