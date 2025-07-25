package ui;

import java.awt.EventQueue;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class WelcomePage {

    private JFrame welcomePage;

    public WelcomePage() {
        welcomePage = new JFrame();
        initialize();
    }

    private void initialize() {
        welcomePage.setTitle("Nutrients_App Welcome Page");
        welcomePage.setSize(600, 450);
        welcomePage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        welcomePage.setLocationRelativeTo(null);
        welcomePage.setLayout(null);

        JLabel title = new JLabel("Welcome to Nutrients_App!", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.PLAIN, 18));
        title.setBounds(148, 135, 300, 30);

        JButton loginButton = new JButton("Login");
        loginButton.setFocusable(false);
        loginButton.setBounds(190, 250, 100, 30);

        JButton registerButton = new JButton("Register");
        registerButton.setFocusable(false);
        registerButton.setBounds(300, 250, 100, 30);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProfileSplashScreen profileScreen = new ProfileSplashScreen();
                welcomePage.setVisible(false);
                profileScreen.setVisible(true);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProfileForm profileCreation = new ProfileForm();
                welcomePage.setVisible(false);
                profileCreation.setVisible(true);
            }
        });

        welcomePage.add(title);
        welcomePage.add(loginButton);
        welcomePage.add(registerButton);

    }

    public void setVisible(boolean b) {
        welcomePage.setVisible(b);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    WelcomePage window = new WelcomePage();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
