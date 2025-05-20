package ui;

import model.Complaint;
import dao.*;
import db.DBConnection;
import java.sql.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComplaintPage {
    private JFrame frame;
    private JTextField complaintDescription;
    private JButton submitButton;
    private JLabel duesLabel;
    private String flatNo;  // ✅ Add this variable

    // ✅ Constructor accepting flatNo
    public ComplaintPage(String flatNo) {
        this.flatNo = flatNo;  // ✅ Store it for later use

        frame = new JFrame("Complaint Submission");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        complaintDescription = new JTextField(20);
        panel.add(new JLabel("Complaint Description:"));
        panel.add(complaintDescription);

        duesLabel = new JLabel("Maintenance Due: ");
        panel.add(duesLabel);

        submitButton = new JButton("Submit Complaint");
        panel.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String description = complaintDescription.getText().trim();

        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a complaint description.");
            return;
        }

        Complaint complaint = new Complaint();
        complaint.setFlatNo(flatNo);
        complaint.setDescription(description);
        complaint.setStatus("Pending");

        ComplaintDAO complaintDAO = new ComplaintDAO();
        boolean result = complaintDAO.addComplaint(complaint);

        if (result) {
            JOptionPane.showMessageDialog(frame, "Complaint submitted successfully!");
            complaintDescription.setText("");
        } else {
            JOptionPane.showMessageDialog(frame, "Failed to submit complaint.");
        }

        // Fetch and display maintenance due
        MaintenanceDAO maintenanceDAO = new MaintenanceDAO();
        double dueAmount = maintenanceDAO.getDueAmount(flatNo);
        if (dueAmount > 0) {
    duesLabel.setText("Maintenance Due: ₹" + dueAmount);
} else {
    duesLabel.setText("No Maintenance Dues!");
}
    }
});


        frame.setVisible(true);
    }

    // Keep this method here if you want to update complaint status externally
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
}
