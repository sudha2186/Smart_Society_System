package ui;

import dao.ComplaintDAO;
import model.Complaint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminPage {
    private JFrame frame;
    private JTable complaintsTable;
    private JButton viewButton;
    private JButton updateButton;

    public AdminPage() {
        frame = new JFrame("Admin View Complaints");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new BorderLayout());

        // Get the list of all complaints from the database
        ComplaintDAO complaintDAO = new ComplaintDAO();
        List<Complaint> complaints = complaintDAO.getAllComplaints();

        // Convert the complaints list to a 2D array for JTable
        String[][] data = new String[complaints.size()][4];
        for (int i = 0; i < complaints.size(); i++) {
            Complaint c = complaints.get(i);
            data[i][0] = String.valueOf(c.getComplaintID());
            data[i][1] = c.getFlatNo();
            data[i][2] = c.getDescription();
            data[i][3] = c.getStatus();
        }

        // Column names for the JTable
        String[] columnNames = {"Complaint ID", "Flat No", "Description", "Status"};
        complaintsTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(complaintsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);

        viewButton = new JButton("View Details");
        updateButton = new JButton("Update Status");

        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);

        // View complaint details on button click
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = complaintsTable.getSelectedRow();
                if (selectedRow != -1) {
                    int complaintID = Integer.parseInt((String) complaintsTable.getValueAt(selectedRow, 0));
                    // Proceed to show complaint details (not implemented here)
                    JOptionPane.showMessageDialog(frame, "View complaint ID: " + complaintID);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a complaint.");
                }
            }
        });

        // Update complaint status on button click
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = complaintsTable.getSelectedRow();
                if (selectedRow != -1) {
                    int complaintID = Integer.parseInt((String) complaintsTable.getValueAt(selectedRow, 0));
                    new UpdateStatusPage(complaintID);  // Open UpdateStatusPage for selected complaint
                    frame.setVisible(false);  // Hide AdminPage
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a complaint.");
                }
            }
        });

        frame.setVisible(true);
    }
}
