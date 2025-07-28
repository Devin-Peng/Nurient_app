package ui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import classes.Meal;
import classes.Session;
import service.MealLoggingService;

public class MealCalculation {
    // private double protein;
    // private double fat;
    // private double carbs;
    // private double kCal;
    // private double water;
    // private double KJ;
    // private double fibre;
    // private double other_nurients;
    private JFrame mealCalculationFrame;
    private Meal meal; 

     public MealCalculation() {
        mealCalculationFrame = new JFrame();
        initialize();
    }

    private void initialize() {
        mealCalculationFrame.setTitle("meal calculation");
        mealCalculationFrame.setSize(1200, 800);
        mealCalculationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mealCalculationFrame.setLocationRelativeTo(null);
        mealCalculationFrame.setLayout(null);

        JComboBox<String> mealTypeBox = new JComboBox<>(new String[]{"breakfast", "lunch", "dinner", "snack"});
        JTextField dateField = new JTextField("YYYY-MM-DD");
        JTextField  ingredientField = new JTextField("Please add ingredient one by one");
        JTextField quantityField = new JTextField();
        JButton addButton = new JButton("Add ingredient");
        JButton submitButton = new JButton("Log this meal");
        JButton backButton = new JButton("Back to Home Screen");

         mealCalculationFrame.add(new JLabel("Date of meal"));
         mealCalculationFrame.add(dateField);
         mealCalculationFrame.add(new JLabel("Type of meal"));
         mealCalculationFrame.add(mealTypeBox);
         mealCalculationFrame.add(new JLabel("Ingredient"));
         mealCalculationFrame.add(ingredientField);
         mealCalculationFrame.add(new JLabel("Quantity"));
         mealCalculationFrame.add(quantityField);

         mealCalculationFrame.add(new JLabel(""));
         mealCalculationFrame.add(addButton);
         mealCalculationFrame.add(new JLabel(""));
         mealCalculationFrame.add(submitButton);
         mealCalculationFrame.add(new JLabel(""));
         mealCalculationFrame.add(backButton);

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

                    JOptionPane.showMessageDialog( mealCalculationFrame, "Meal ADDED!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog( mealCalculationFrame, "Error: " + ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (meal == null) {
                        JOptionPane.showMessageDialog( mealCalculationFrame, "Please add at least one ingredient first!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    new MealLoggingService().logMeal_v2(meal);
                    JOptionPane.showMessageDialog( mealCalculationFrame, "Meal saved!");

                    // Reset form after successful save
                    meal = null;
                    dateField.setText("YYYY-MM-DD");
                    mealTypeBox.setSelectedIndex(0);
                    ingredientField.setText("Please add ingredient one by one");
                    quantityField.setText("");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog( mealCalculationFrame, "Error: " + ex.getMessage(), "Save Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });







    }

    public void setVisible(boolean b) {
        mealCalculationFrame.setVisible(b);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MealCalculation window = new MealCalculation();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
