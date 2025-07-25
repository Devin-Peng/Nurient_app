package ui;

import classes.Session;

import javax.swing.*;
import java.awt.*;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUIScreen {

    private JFrame mainScreenFrame;
    private JMenuBar menuBar;
    private JTabbedPane tabbedPanel;

    public MainGUIScreen() {
        mainScreenFrame = new JFrame();
        menuBar = new JMenuBar();
        tabbedPanel = new JTabbedPane(JTabbedPane.TOP);
        initialize();
    }

    private void initialize() {
        mainScreenFrame.setTitle("User Interface");
        mainScreenFrame.setSize(800, 600);
        mainScreenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainScreenFrame.setLocationRelativeTo(null);

        // Menu Bar
        JMenu logoutMenu = new JMenu("Logout");
        JMenu editProfileMenu = new JMenu("Edit Profile");
        menuBar.add(logoutMenu);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(editProfileMenu);
        mainScreenFrame.setJMenuBar(menuBar);

        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.add(new JLabel("Welcome to Home Tab!", SwingConstants.CENTER), BorderLayout.CENTER);
        tabbedPanel.addTab("Home Tab", homePanel);

        // Meal Analysiser tab with another panel
        JPanel mealAnalysisPanel = new JPanel();
        GroupLayout mealLayout = new GroupLayout(mealAnalysisPanel);
        mealAnalysisPanel.setLayout(mealLayout);
        mealLayout.setAutoCreateGaps(true);
        mealLayout.setAutoCreateContainerGaps(true);
        tabbedPanel.addTab("Meal Analysis", mealAnalysisPanel);

        // Jtable column names & rows spaces
        String[] columnNames = { "Meal Type", "Date", "Ingredient", "Quantity" };
        JTable mealTable = new JTable(new DefaultTableModel(columnNames, 7));
        mealTable.setRowHeight(25);
        mealTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane tableScrollPane = new JScrollPane(mealTable);

        mealTable.getColumnModel().getColumn(0).setPreferredWidth(75); // Meal Type
        mealTable.getColumnModel().getColumn(1).setPreferredWidth(75); // Date
        mealTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Ingredients
        mealTable.getColumnModel().getColumn(3).setPreferredWidth(50); // Quantity

        // add to meal logging so that it atuomatically refreshes when meal entered used the gobal attribute
        // JButton refreshButton = new JButton("Refresh");
        // mealPanel.add(refreshButton);

        // The Meal Logger, Compare, Swap buttons
        JButton mealLoggerButton = new JButton("Meal logger");
        mealLoggerButton.setPreferredSize(new Dimension(160, 40));
        mealLoggerButton.setFocusable(false);

        JButton compareButton = new JButton("Compare Meal");
        compareButton.setPreferredSize(new Dimension(160, 40));
        compareButton.setFocusable(false);

        JButton swapButton = new JButton("Swap Meal");
        swapButton.setPreferredSize(new Dimension(160, 40));
        swapButton.setFocusable(false);

        mealLayout.setHorizontalGroup(
                mealLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(tableScrollPane)
                        .addGroup(mealLayout.createSequentialGroup()
                                .addComponent(mealLoggerButton)
                                .addComponent(compareButton)
                                .addComponent(swapButton)));

        mealLayout.setVerticalGroup(
                mealLayout.createSequentialGroup()
                        .addComponent(tableScrollPane, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                        .addGroup(mealLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(mealLoggerButton)
                                .addGap(100)
                                .addComponent(compareButton)
                                .addComponent(swapButton)));

        JPanel visualizationPanel = new JPanel();
        GroupLayout visualizationLayout = new GroupLayout(visualizationPanel);
        visualizationPanel.setLayout(visualizationLayout);
        visualizationLayout.setAutoCreateGaps(true);
        visualizationLayout.setAutoCreateContainerGaps(true);
        tabbedPanel.addTab("Visualization", visualizationPanel);
        mainScreenFrame.add(tabbedPanel, BorderLayout.CENTER);



        // Using abstract class to reduce lines, see MenuAdapter.java in classes
        logoutMenu.addMenuListener(new MenuAdapter() {
            @Override
            public void menuSelected(MenuEvent e) {
                mainScreenFrame.dispose();
                ProfileSplashScreen splashScreen = new ProfileSplashScreen();
                splashScreen.setVisible(true); // opens and shows login screen
            }
        });

        editProfileMenu.addMenuListener(new MenuAdapter() {
            public void menuSelected(MenuEvent e) {
                EditingProfile editingProfile = new EditingProfile();
                mainScreenFrame.setVisible(false);
                editingProfile.setVisible(true);
            }
        });

        mealLoggerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainScreenFrame.setVisible(false);
                if (Session.currentUser != null) {
                    int userId = Session.currentUser.getId();
                    MealLoggingForm mealForm = new MealLoggingForm(userId);
                    mainScreenFrame.setVisible(false);
                    mealForm.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "No user logged in!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        compareButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //empty for now
            }
        });

        swapButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //empty for now
            }
        });

    }

    public void setVisible(boolean b) {
        mainScreenFrame.setVisible(b);
    }

    private static class MenuAdapter implements MenuListener {
        @Override
        public void menuSelected(MenuEvent e) {
        }

        @Override
        public void menuDeselected(MenuEvent e) {
        }

        @Override
        public void menuCanceled(MenuEvent e) {
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainGUIScreen window = new MainGUIScreen();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
