package ui;
import dao.UserDAO;
import model.*;
import db.DBConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPage() {
        frame = new JFrame("Login");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new GridLayout(3, 2));

        JLabel userLabel = new JLabel("Username:");
        panel.add(userLabel);
        usernameField = new JTextField(20);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);
        passwordField = new JPasswordField(20);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        panel.add(loginButton);
        JButton registerButton = new JButton("Register");
        panel.add(registerButton);

	registerButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        new RegisterPage(); // Opens the RegisterPage UI
    }
});


        loginButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        UserDAO userDAO = new UserDAO();
        User user = userDAO.validateUser(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(frame, "Login Successful");

            frame.setVisible(false); // Hide login frame

            if (user instanceof Admin) {
                new AdminPage();
            } else if (user instanceof FlatOwner) {
                String flatNo = ((FlatOwner) user).getFlatNo();
                new ComplaintPage(flatNo); // Pass flat number to ComplaintPage constructor
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid Credentials");
        }
    }
});

        frame.setVisible(true);
    }

}
