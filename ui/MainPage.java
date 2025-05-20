package ui;

import javax.swing.*;
import java.awt.event.*;

public class MainPage {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Smart Society System");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JButton userLogin = new JButton("Login as User");
        userLogin.setBounds(70, 40, 150, 30);
        frame.add(userLogin);

        JButton adminLogin = new JButton("Login as Admin");
        adminLogin.setBounds(70, 90, 150, 30);
        frame.add(adminLogin);

        userLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // close main page
                new LoginPage(); // show user login page
            }
        });

        adminLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // close main page
                new AdminLoginPage(); // show admin login page
            }
        });

        frame.setVisible(true);
    }
}
