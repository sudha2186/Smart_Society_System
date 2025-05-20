package ui;

import dao.ComplaintDAO;
import model.Complaint;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminDashboardPage {
    private JFrame frame;
    private JTable complaintTable;
    private JButton markResolvedButton;

    public AdminDashboardPage() {
        frame = new JFrame("Admin Dashboard");
        frame.setSize(700, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ComplaintDAO complaintDAO = new ComplaintDAO();
        List<Complaint> complaints = complaintDAO.getAllComplaints();

        String[] columnNames = {"Complaint ID", "Flat No", "Owner", "Description", "Status"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Complaint c : complaints) {
            Object[] row = {
                c.getComplaintID(),
                c.getFlatNo(),
                c.getUsername(),
                c.getDescription(),
                c.getStatus()
            };
            model.addRow(row);
        }

        complaintTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(complaintTable);

        // Add button for Mark as Resolved
        markResolvedButton = new JButton("Mark as Resolved");
        markResolvedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected complaint ID
                int selectedRow = complaintTable.getSelectedRow();
                if (selectedRow != -1) {
                    int complaintId = (int) complaintTable.getValueAt(selectedRow, 0);  // Complaint ID from table
                    Complaint complaint = complaintDAO.getComplaintById(complaintId);
                    if (complaint != null) {
                        // Mark the complaint as resolved
                        complaint.setStatus("Resolved");
                        complaintDAO.updateComplaintStatus(complaint);
                        JOptionPane.showMessageDialog(frame, "Complaint marked as Resolved!");

                        // Refresh the table to show updated status
                        model.setValueAt("Resolved", selectedRow, 4);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a complaint to resolve.");
                }
            }
        });

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(markResolvedButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
