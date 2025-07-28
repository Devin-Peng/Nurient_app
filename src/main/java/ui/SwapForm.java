package ui;

import service.FoodSwapService;
import dao.MealDAO;
import dao.FoodSwapDAO;
import factory.*;
import observer.*;
import decorator.*;

import javax.swing.*;

import classes.Session;
import classes.SwapGoal;
import classes.SwapLoggedEvent;
import classes.SwapSuggestion;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class SwapForm extends JFrame {
    
    private JComboBox<String> dateBox;
    private JComboBox<String> mealBox;
    private JComboBox<String> nutrientBox;
    private JComboBox<String> directionBox;
    private JComboBox<String> percentageBox;
    private JButton swapButton;
    private JButton logButton;
    private JTextArea resultArea;
    private JTextArea caloArea;
    private JCheckBox showCaloriesBox;

    private JTextArea cholesArea;
    private JCheckBox showCholesBox;
    
    private FoodSwapService service = new FoodSwapService();
    private MealDAO mealDAO = new MealDAO();
    private FoodSwapDAO foodSwapDAO = new FoodSwapDAO();
    
    private final SwapGoalFactory swapGoalFactory = new DefaultSwapGoalFactory();
   
    // observer: to register listeners
    private final List<SwapEventListener> listeners = new ArrayList<>();
    
    
    private SwapSuggestion latestSuggestion;
    private String latestNutrient;

    public SwapForm() {
        setTitle("Precise Food Swap");
        setSize(600, 450);
        setLayout(new GridLayout(15, 5));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        

        // Initialize components
       
        dateBox = new JComboBox<>();
        mealBox = new JComboBox<>();
        nutrientBox = new JComboBox<>(new String[]{"carbohydrate", "protein", "fibre"});
        directionBox = new JComboBox<>(new String[]{"INCREASE", "DECREASE"});
        percentageBox = new JComboBox<>(new String[]{"10", "25"});
        swapButton = new JButton("Swap");
        logButton = new JButton("Log Swap");
        // for decorator
        showCaloriesBox = new JCheckBox("Show Calorie Impact");
        showCholesBox = new JCheckBox("Show Cholesterol Impact");
        JButton backButton = new JButton("Back to Home Screen");
        resultArea = new JTextArea(10, 40);
        
        // for decorator CalorieImpact display
        caloArea = new JTextArea(10, 40);
        caloArea.setLineWrap(true);
        caloArea.setWrapStyleWord(true);
        cholesArea = new JTextArea(10, 40);
        cholesArea.setLineWrap(true);
        cholesArea.setWrapStyleWord(true);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        // Add components to layout
        
        add(new JLabel("Select Date:")); add(dateBox);
        add(new JLabel("Select Meal Type:")); add(mealBox);
        add(new JLabel("Nutrient to Adjust:")); add(nutrientBox);
        add(new JLabel("Direction:")); add(directionBox);
        add(new JLabel("Percentage:")); add(percentageBox);
        add(swapButton); add(logButton);
        add(new JLabel("Suggestion:")); add(new JScrollPane(resultArea));
        // for decorator pattern
        add(showCaloriesBox); add(new JLabel("")); // align grid
      
        add(new JLabel("Calorie Impact:")); add(new JScrollPane(caloArea));
        
        add(showCholesBox); add(new JLabel("")); // align grid
        
        add(new JLabel("Cholesterol Impact:")); add(new JScrollPane(cholesArea));
        
        add(backButton); add(new JLabel());  // aligns layout

       

        // Add event listeners
       
        dateBox.addActionListener(this::onDateSelected);
        swapButton.addActionListener(e -> handleSwap());
        logButton.addActionListener(e -> handleLogSwap());
        
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainGUIScreen main = new MainGUIScreen();
                dispose();
                main.setVisible(true);
            }
        });
        setLocationRelativeTo(null);
        populateDates();

        setVisible(true);
    }
    // observer DESIGN PATTERN
    public void addSwapEventListener(SwapEventListener listener) {
        listeners.add(listener);
    }
    
    // private void fireSwapLoggedEvent(SwapSuggestion suggestion, String nutrientName) {
    //     SwapLoggedEvent event = new SwapLoggedEvent(suggestion, nutrientName);
    //     for (SwapEventListener listener : listeners) {
    //         listener.onSwapLogged(event);
    //     }
    // }
private void fireSwapLoggedEvent(SwapSuggestion suggestion, String nutrientName) {
    System.out.println("[SwapForm] firing event to " + listeners.size() + " listeners");
    SwapLoggedEvent event = new SwapLoggedEvent(suggestion, nutrientName);
    for (SwapEventListener l : listeners) {
        System.out.println("[SwapForm] notifying: " + l.getClass().getSimpleName());
        l.onSwapLogged(event);
    }
}
   

    

    private void onDateSelected(ActionEvent e) {
        int userId = Session.getCurrentUser().getId();
        String date = (String) dateBox.getSelectedItem();
        if (date != null) {
            mealBox.removeAllItems();
            List<String> types = mealDAO.getMealTypesForUserDate(userId, date);
            for (String m : types) mealBox.addItem(m);
        }
    }

    private void handleSwap() {
        try {
        	int userId = Session.getCurrentUser().getId();
            String date = (String) dateBox.getSelectedItem();
            String mealType = (String) mealBox.getSelectedItem();

            String nutrient = (String) nutrientBox.getSelectedItem();
            String direction = (String) directionBox.getSelectedItem();
            int percentage = Integer.parseInt((String) percentageBox.getSelectedItem());
            
            // below is before using factory pattern
            //SwapGoal goal = new SwapGoal(SwapGoal.NutrientType.valueOf(nutrient.toUpperCase()), SwapGoal.GoalDirection.valueOf(direction), percentage);
            
            // using factory DESIGN PATTERN                string    string     in
            SwapGoal goal = swapGoalFactory.createSwapGoal(nutrient, direction, percentage);

            SwapSuggestion suggestion = service.getSwapSuggestion(userId, date, mealType, goal);
            if (suggestion != null) {
                String measure = foodSwapDAO.getMeasureDescription(suggestion.getOriginalFood(), userId, date, mealType);
                double after = foodSwapDAO.getRecommendedNutrientAmount(suggestion.getRecommendedFood(), measure, nutrient);

                latestSuggestion = suggestion;
                latestNutrient = nutrient;

                resultArea.setText("Original: " + suggestion.getOriginalFood() +
                        "\nSuggested: " + suggestion.getRecommendedFood() +
                        "\n" + nutrient + " before: " + suggestion.getOriginalValue() +
                        "\n" + nutrient + " after: " + after);
                if (showCaloriesBox.isSelected()) {
                	CalorieImpactDecorator decorated = new CalorieImpactDecorator(suggestion, userId, date, mealType);
                    caloArea.append("\n\n" + decorated.getDecoratedInfo());
                }
                if (showCholesBox.isSelected()) {
                	CholesterolImpactDecorator decorated2 = new CholesterolImpactDecorator(suggestion, userId, date, mealType);
                    cholesArea.append("\n\n" + decorated2.getDecoratedInfo());
                }
            } else {
                resultArea.setText("⚠ No suitable swap found for your goal.");
            }
        } catch (Exception ex) {
            resultArea.setText("Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void handleLogSwap() {
        try {
            if (latestSuggestion == null || latestNutrient == null) {
                JOptionPane.showMessageDialog(this, "Please perform a swap first.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int userId = Session.getCurrentUser().getId();
            String date = (String) dateBox.getSelectedItem();
            String mealType = (String) mealBox.getSelectedItem();
            int mealId = mealDAO.getMealId(userId, date, mealType);
            if (mealId == -1) {
                JOptionPane.showMessageDialog(this, "Original meal not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String measure = foodSwapDAO.getMeasureDescription(latestSuggestion.getOriginalFood(), userId, date, mealType);
            double after = foodSwapDAO.getRecommendedNutrientAmount(latestSuggestion.getRecommendedFood(), measure, latestNutrient);

            boolean success = foodSwapDAO.logSwapMeal(
                userId, mealId, date, mealType,
                latestSuggestion.getOriginalFood(), latestSuggestion.getOriginalValue(),
                latestSuggestion.getRecommendedFood(), after, latestNutrient.toUpperCase()
            );

            if (success) {
            	
            	fireSwapLoggedEvent(latestSuggestion, latestNutrient); //  Notify all observers
                JOptionPane.showMessageDialog(this, "Swap logged successfully.");

            } else {
                JOptionPane.showMessageDialog(this, "Failed to log swap.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            resultArea.setText("Log Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void populateDates() {
        int userId = Session.getCurrentUser().getId();
        dateBox.removeAllItems();
        List<String> dates = mealDAO.getDatesForUser(userId);
        for (String d : dates) {
            dateBox.addItem(d);
        }
    }
    
}
