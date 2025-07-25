package ui;

import dao.UserDAO;
import classes.Profile;
import classes.Session;

import java.awt.EventQueue;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ProfileSplashScreen {
    private JFrame splashFrame;
    private JComboBox<Profile> profileComboBox;
    private UserDAO userDAO = new UserDAO();
    private List<Profile> profiles;

    public ProfileSplashScreen() {
        initialize();
    }

    private void initialize() {
        // Set up the main splash screen window
        splashFrame = new JFrame("Choose Profile");
        splashFrame.setSize(600, 450);
        splashFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        splashFrame.setLocationRelativeTo(null);
        splashFrame.setResizable(false);

        // Create main panel with GroupLayout
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        // Enable auto-create gaps between components
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Title Label
        JLabel label = new JLabel("Select Your Profile:");
        label.setFont(new Font("Arial", Font.PLAIN, 16));

        //making empty ComboBox then populating with Profiles from database
        profileComboBox = new JComboBox<>();
        profiles = userDAO.loadProfilesFromDatabase();

        for (Profile profile : profiles) {
            profileComboBox.addItem(profile);
        }
        profileComboBox.setSelectedIndex(-1);

        JButton continueButton = new JButton("Continue");
        continueButton.setFocusable(false);

        JButton backButton = new JButton("Back to Welcome");
        backButton.setFocusable(false);

        // Set up horizontal group
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGap(155) // Add gap on the left to push components right
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(label)
                    .addComponent(profileComboBox, GroupLayout.PREFERRED_SIZE, 275,
                            GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(continueButton, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                        .addGap(20) // Space between buttons
                        .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE))));

        // Set up vertical group
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGap(135) // Top padding
                .addComponent(label)
                .addGap(20)
                .addComponent(profileComboBox, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addGap(20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(continueButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                    .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
                .addGap(130) // Bottom padding
        );

        // Action listener for continue button
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Profile selectedProfile = (Profile) profileComboBox.getSelectedItem();
                if (selectedProfile != null) {
                    Session.currentUser = selectedProfile;
                    LoginPage loginPage = new LoginPage();
                    splashFrame.setVisible(false);
                    loginPage.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(splashFrame, "Please select a profile", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                WelcomePage welcomePage = new WelcomePage();
                splashFrame.setVisible(false);
                welcomePage.setVisible(true);
            }
        });

        splashFrame.add(panel);
        splashFrame.setVisible(true);
    }

    //This method used to get local profile, session.currentUser is gobal profile.
    public Profile getSelectedProfile() {
        if (profileComboBox != null) {
            return (Profile) profileComboBox.getSelectedItem();
        }
        return null;
    }

    public void setVisible(boolean b) {
        splashFrame.setVisible(b);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ProfileSplashScreen window = new ProfileSplashScreen();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
