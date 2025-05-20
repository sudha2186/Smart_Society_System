package ui;

import dao.AdminDAO;
import javax.swing.*;
import java.awt.event.*;

public class AdminLoginPage {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public AdminLoginPage() {
        frame = new JFrame("Admin Login");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(30, 30, 80, 25);
        frame.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(120, 30, 160, 25);
        frame.add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(30, 70, 80, 25);
        frame.add(passLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(120, 70, 160, 25);
        frame.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(120, 110, 100, 25);
        frame.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                AdminDAO adminDAO = new AdminDAO();
                boolean isValid = adminDAO.validateAdmin(username, password);

                if (isValid) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    frame.dispose(); // Close login page
                    new AdminDashboardPage(); // Open dashboard
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid credentials");
                }
            }
        });

        frame.setVisible(true);
    }
}
