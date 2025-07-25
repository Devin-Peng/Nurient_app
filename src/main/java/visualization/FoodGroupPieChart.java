package visualization;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import dbmanager.DatabaseManager;

public class FoodGroupPieChart {

    private JFrame chartFrame;
    private DefaultPieDataset dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;

    public FoodGroupPieChart() {
        chartFrame = new JFrame();
        dataset = new DefaultPieDataset();
        initialize();
    }

    private void initialize() {
        chartFrame.setTitle("Food Group Pie Chart");
        chartFrame.setSize(800, 600);
        chartFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chartFrame.setLocationRelativeTo(null);

        try {
            dataset = createDataset();
            chart = ChartFactory.createPieChart(
                "Food Items by Group", 
                dataset, 
                true,  // legend
                true,  // tooltips
                false  // URLs
            );

            chartPanel = new ChartPanel(chart);
            chartFrame.setContentPane(chartPanel);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(chartFrame, 
                "Error creating chart: " + e.getMessage(), 
                "Chart Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private DefaultPieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        
        String sql = "SELECT fg.food_group_name, COUNT(fi.food_id) AS count " +
                    "FROM food_items fi " +
                    "JOIN food_groups fg ON fi.food_group_id = fg.food_group_id " +
                    "GROUP BY fg.food_group_name";
        
        try (Connection conn = DatabaseManager.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                String groupName = rs.getString("food_group_name");
                int count = rs.getInt("count");
                dataset.setValue(groupName, count);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load chart data", e);
        }
        
        return dataset;
    }

    public void setVisible(boolean b) {
        chartFrame.setVisible(b);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FoodGroupPieChart window = new FoodGroupPieChart();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
