package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import classes.Session;
import classes.Meal;
import service.MealLoggingService;

public class MealLoggingForm {

    private JFrame mealLoggingForm;
    private Meal meal;

    public MealLoggingForm(int userId) {
        mealLoggingForm = new JFrame();
        initialize();
    }

    private void initialize() {
        mealLoggingForm.setTitle("Meal Logging");
        mealLoggingForm.setSize(600, 450);
        mealLoggingForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mealLoggingForm.setLocationRelativeTo(null);
        mealLoggingForm.setLayout(new GridLayout(7, 2));

        JComboBox<String> mealTypeBox = new JComboBox<>(new String[]{"breakfast", "lunch", "dinner", "snack"});
        JTextField dateField = new JTextField("YYYY-MM-DD");
        JTextField  ingredientField = new JTextField("Please add ingredient one by one");
        JTextField quantityField = new JTextField();
        JButton addButton = new JButton("Add ingredient");
        JButton submitButton = new JButton("Log this meal");
        JButton backButton = new JButton("Back to Home Screen");

        mealLoggingForm.add(new JLabel("Date of meal"));
        mealLoggingForm.add(dateField);
        mealLoggingForm.add(new JLabel("Type of meal"));
        mealLoggingForm.add(mealTypeBox);
        mealLoggingForm.add(new JLabel("Ingredient"));
        mealLoggingForm.add(ingredientField);
        mealLoggingForm.add(new JLabel("Quantity"));
        mealLoggingForm.add(quantityField);

        mealLoggingForm.add(new JLabel(""));
        mealLoggingForm.add(addButton);
        mealLoggingForm.add(new JLabel(""));
        mealLoggingForm.add(submitButton);
        mealLoggingForm.add(new JLabel(""));
        mealLoggingForm.add(backButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (meal == null) {
                        meal = new Meal(
                            Session.currentUser.getId(),
                            mealTypeBox.getSelectedItem().toString(),
                            LocalDate.parse(dateField.getText()),
                            ingredientField.getText(),
                            quantityField.getText()
                        );
                    }

                    // Clear ingredient fields for next ingredient
                    ingredientField.setText("");
                    quantityField.setText("");

                    JOptionPane.showMessageDialog(mealLoggingForm, "Ingredient added!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(mealLoggingForm, "Error: " + ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (meal == null) {
                        JOptionPane.showMessageDialog(mealLoggingForm, "Please add at least one ingredient first!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    new MealLoggingService().logMeal(meal);
                    JOptionPane.showMessageDialog(mealLoggingForm, "Meal saved!");

                    // Reset form after successful save
                    meal = null;
                    dateField.setText("YYYY-MM-DD");
                    mealTypeBox.setSelectedIndex(0);
                    ingredientField.setText("Please add ingredient one by one");
                    quantityField.setText("");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(mealLoggingForm, "Error: " + ex.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainGUIScreen main= new MainGUIScreen();
                mealLoggingForm.setVisible(false);
                main.setVisible(true);
            }
        });
    }

    public void setVisible(boolean b) {
        mealLoggingForm.setVisible(b);
    }
}

