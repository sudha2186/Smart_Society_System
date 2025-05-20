package ui;
import dao.ComplaintDAO;
import model.Complaint;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateStatusPage {
    private JFrame frame;
    private JTextField complaintIDField;
    private JTextArea complaintDescription;
    private JComboBox<String> statusComboBox;
    private JButton updateButton;

    public UpdateStatusPage(int complaintID) {
        frame = new JFrame("Update Complaint Status");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Fetch complaint details from the database
        ComplaintDAO complaintDAO = new ComplaintDAO();
        Complaint complaint = complaintDAO.getComplaintById(complaintID);  // Implement this in DAO

        // Set complaint details
        complaintIDField = new JTextField(String.valueOf(complaint.getComplaintID()));
        complaintIDField.setEditable(false);
        complaintDescription = new JTextArea(5, 20);
        complaintDescription.setText(complaint.getDescription());
        complaintDescription.setEditable(false);

        String[] statuses = {"Pending", "In Progress", "Resolved"};
        statusComboBox = new JComboBox<>(statuses);
        statusComboBox.setSelectedItem(complaint.getStatus());

        updateButton = new JButton("Update Status");

        panel.add(new JLabel("Complaint ID:"));
        panel.add(complaintIDField);
        panel.add(new JLabel("Description:"));
        panel.add(new JScrollPane(complaintDescription));
        panel.add(new JLabel("Status:"));
        panel.add(statusComboBox);
        panel.add(updateButton);

        // Update status action
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newStatus = (String) statusComboBox.getSelectedItem();
                complaint.setStatus(newStatus);

                // Update in database
                complaintDAO.updateComplaintStatus(complaint);  // Implement in DAO
                JOptionPane.showMessageDialog(frame, "Complaint status updated!");

                frame.setVisible(false);  // Close update page
                new AdminPage();  // Open AdminPage again to reflect changes
            }
        });

        frame.setVisible(true);
    }
}
