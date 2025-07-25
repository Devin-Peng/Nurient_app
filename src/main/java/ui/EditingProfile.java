package ui;

import classes.Profile;
import classes.Session;
import dao.UserDAO;

import java.awt.EventQueue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditingProfile {
    private JFrame editingProfile;
    private UserDAO user = new UserDAO();

    public EditingProfile() {
        editingProfile = new JFrame();
        initialize();
    }

    private void initialize() {
        editingProfile.setTitle("Editing Profile");
        editingProfile.setSize(600, 450);
        editingProfile.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editingProfile.setLocationRelativeTo(null);

        //Placing a Panel on top the Frame
        JPanel editingPanel = new JPanel(); 
        GroupLayout layout = new GroupLayout(editingPanel);
        editingPanel.setLayout(layout);
        editingProfile.add(editingPanel);

        //global profile 
        Profile temp = Session.currentUser;

        JLabel nameLabel = new JLabel("Name: ");
        JLabel dobLabel = new JLabel("Date of Birth: ");
        JLabel heightLabel = new JLabel("Height(cm): ");
        JLabel weightLabel = new JLabel("Weight(Kg): ");

        //dob is using LocalDate, height & weight is double in database;
        
        JTextField nameField = new JTextField();
        JTextField dobField = new JTextField();
        JTextField heightField = new JTextField();
        JTextField weightField = new JTextField();

        if (temp != null) {
            nameField.setText(temp.getName());
            LocalDate dob = temp.getDateOfBirth();
            dobField.setText(dob != null ? dob.toString() : "");
            heightField.setText(String.valueOf(temp.getHeightCm()));
            weightField.setText(String.valueOf(temp.getWeightKg()));
        }

        JButton changeButton = new JButton("Change");
        JButton backButton = new JButton("Back to Home Screen");

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(nameLabel)
                        .addComponent(dobLabel)
                        .addComponent(heightLabel)
                        .addComponent(weightLabel))
                    .addGroup(layout.createParallelGroup()
                        .addComponent(nameField)
                        .addComponent(dobField)
                        .addComponent(heightField)
                        .addComponent(weightField)))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(changeButton)
                    .addComponent(backButton))
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel).addComponent(nameField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(dobLabel).addComponent(dobField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(heightLabel).addComponent(heightField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(weightLabel).addComponent(weightField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(changeButton)
                    .addComponent(backButton))
        );

        changeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                // Get the updated values from the text fields
                String name = nameField.getText().trim();
                String dobText = dobField.getText().trim();
                String heightText = heightField.getText().trim();
                String weightText = weightField.getText().trim();

                // Validate input
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(editingProfile, "Name cannot be empty!");
                    return;
                }

                // Parse date of birth
                LocalDate dob = null;
                if (!dobText.isEmpty()) {
                    try {
                        dob = LocalDate.parse(dobText);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(editingProfile, "Invalid date format! Use YYYY-MM-DD");
                        return;
                    }
                }

                // Parse height and weight
                double height = 0;
                double weight = 0;
                
                if (!heightText.isEmpty()) {
                    try {
                        height = Double.parseDouble(heightText);
                        if (height < 0) {
                            JOptionPane.showMessageDialog(editingProfile, "Height cannot be negative!");
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(editingProfile, "Invalid height format!");
                        return;
                    }
                }

                if (!weightText.isEmpty()) {
                    try {
                        weight = Double.parseDouble(weightText);
                        if (weight < 0) {
                            JOptionPane.showMessageDialog(editingProfile, "Weight cannot be negative!");
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(editingProfile, "Invalid weight format!");
                        return;
                    }
                }

                // Update the database
                if (temp != null && user.updateProfile(temp.getId(), name, dob, height, weight)) {
                    // Update the local profile object
                    temp.setName(name);
                    temp.setDateOfBirth(dob);
                    temp.setHeightCm(height);
                    temp.setWeightKg(weight);
                    
                    JOptionPane.showMessageDialog(editingProfile, "Profile updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(editingProfile, "Failed to update profile. Please try again.");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(editingProfile, "Error updating profile: " + ex.getMessage());
                ex.printStackTrace();
            }
            }
        });


        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainGUIScreen main = new MainGUIScreen();
                editingProfile.dispose();
                main.setVisible(true);
            }
        });

    }

    public void setVisible(boolean b) {
        editingProfile.setVisible(b);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EditingProfile window = new EditingProfile();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
