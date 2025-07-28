package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import classes.Account;
import classes.Profile;
import service.ProfileService;

public class ProfileForm {

    private JFrame profileForm;

    public ProfileForm() {
        profileForm = new JFrame();
        initialize();
    }

    private void initialize() {
        profileForm.setTitle("Create User Profile");
        profileForm.setSize(400, 500);
        profileForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        profileForm.setLocationRelativeTo(null);
        profileForm.setLayout(new GridLayout(8, 2));

        JComboBox<String> genderBox = new JComboBox<>(new String[] { "Male", "Female", "Other" });
        JTextField nameField = new JTextField();
        JTextField dobField = new JTextField("YYYY-MM-DD");
        JTextField heightField = new JTextField();
        JTextField weightField = new JTextField();
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        JButton saveButton = new JButton("Save");
        JButton backButton = new JButton("Back to welcome");

        profileForm.add(new JLabel("Name"));
        profileForm.add(nameField);
        profileForm.add(new JLabel("Gender"));
        profileForm.add(genderBox);
        profileForm.add(new JLabel("Date of Birth"));
        profileForm.add(dobField);
        profileForm.add(new JLabel("Height (cm)"));
        profileForm.add(heightField);
        profileForm.add(new JLabel("Weight (kg)"));
        profileForm.add(weightField);
        profileForm.add(new JLabel("Username"));
        profileForm.add(usernameField);
        profileForm.add(new JLabel("Password"));
        profileForm.add(passwordField);

        profileForm.add(backButton);
        profileForm.add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Profile user = new Profile(
                            nameField.getText(),
                            (String) genderBox.getSelectedItem(),
                            LocalDate.parse(dobField.getText()),
                            Double.parseDouble(heightField.getText()),
                            Double.parseDouble(weightField.getText()));

                    Account accountDetails = new Account(
                            usernameField.getText(),
                            passwordField.getText());

                    new ProfileService().createProfileWithAuth(user, accountDetails);

                    JOptionPane.showMessageDialog(profileForm, "Profile saved!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(profileForm, "Error: Field(s) are empty or incorrect", "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WelcomePage welcomePage = new WelcomePage();
                profileForm.setVisible(false);
                welcomePage.setVisible(true);
            }
        });
    }

    public void setVisible(boolean b) {
        profileForm.setVisible(b);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ProfileForm window = new ProfileForm();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
