package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPage {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public RegisterPage() {
        frame = new JFrame("Register");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        frame.add(panel);

        panel.add(new JLabel("New Username:"));
        usernameField = new JTextField(20);
        panel.add(usernameField);

        panel.add(new JLabel("New Password:"));
        passwordField = new JPasswordField(20);
        panel.add(passwordField);

        JButton registerBtn = new JButton("Register");
        panel.add(registerBtn);

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                UserDAO userDAO = new UserDAO();

                if (userDAO.registerUser(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Registration Successful!");
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Username already exists!");
                }
            }
        });

        frame.setVisible(true);
    }
}
