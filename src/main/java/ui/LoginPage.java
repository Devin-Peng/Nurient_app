package ui;

import dao.UserDAO;

import java.awt.EventQueue;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPage {
    private JFrame loginPage;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPage() {
        loginPage = new JFrame();
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        initialize();
    }

    private void initialize() {
        loginPage.setTitle("Login Screen");
        loginPage.setSize(600, 450);
        loginPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginPage.setLocationRelativeTo(null);
        loginPage.setLayout(null);

        JLabel title = new JLabel("Login", JLabel.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 36));
        title.setBounds(168, 50, 300, 50);

        JLabel message = new JLabel("Hello, welcome back!", JLabel.CENTER);
        message.setFont(new Font("SansSerif", Font.PLAIN, 18));
        message.setBounds(168, 100, 300, 30);

        JLabel labelUsername = new JLabel("Username");
        labelUsername.setBounds(134, 150, 100, 30);
        usernameField.setBounds(200, 150, 212, 30);

        JLabel labelPassowrd = new JLabel("Password");
        labelPassowrd.setBounds(134, 200, 100, 30);
        passwordField.setBounds(200, 200, 212, 30);

        JButton loginButton = new JButton("Login");
        loginButton.setFocusable(false);
        loginButton.setBounds(200, 250, 100, 30);

        JButton resetButton = new JButton("Reset");
        resetButton.setFocusable(false);
        resetButton.setBounds(310, 250, 100, 30);

        JButton welcomePageButton = new JButton("Back to Welcome");
        welcomePageButton.setFocusable(false);
        welcomePageButton.setBounds(200, 300, 210, 30);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String usernameText = usernameField.getText();
                String passwordText = new String(passwordField.getPassword());

                if (usernameText.isEmpty() || passwordText.isEmpty()) {
                    JOptionPane.showMessageDialog(loginPage,
                        "Username and/or password can't be blank.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                UserDAO user = new UserDAO();
                boolean loginSuccessful = user.validateLogin(usernameText, passwordText);

                if (loginSuccessful) {
                    MainGUIScreen mainScreen = new MainGUIScreen();
                    loginPage.setVisible(false);
                    mainScreen.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(loginPage,
                        "Invalid username or password. Please try again.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
                    passwordField.setText("");
                }

            }
        });

        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                usernameField.setText("");
                passwordField.setText("");

            }
        });

        welcomePageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WelcomePage welcomePage = new WelcomePage();
                loginPage.setVisible(false);
                welcomePage.setVisible(true);

            }
        });

        loginPage.add(labelUsername);
        loginPage.add(labelPassowrd);
        loginPage.add(title);
        loginPage.add(message);
        loginPage.add(usernameField);
        loginPage.add(passwordField);
        loginPage.add(loginButton);
        loginPage.add(resetButton);
        loginPage.add(welcomePageButton);

    }

    public void setVisible(boolean b) {
        loginPage.setVisible(b);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginPage window = new LoginPage();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
