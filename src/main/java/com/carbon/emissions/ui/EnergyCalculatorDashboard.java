package com.carbon.emissions.ui;

import com.carbon.emissions.CarbonEmissionsDBConnection;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Dashboard for companies to input energy usage and view renewable energy benefits
 */
public class EnergyCalculatorDashboard extends JFrame {
    
    private String username;
    private JPanel mainPanel;
    private JPanel resultsPanel;
    
    // Input components
    private JComboBox<String> energySourceCombo;
    private JTextField energyUsageField;
    private JComboBox<String> countryCombo;
    private JComboBox<String> renewableSourceCombo;
    
    // Results components
    private JLabel currentEmissionsLabel;
    private JLabel newEmissionsLabel;
    private JLabel emissionsReductionLabel;
    private JLabel costSavingsLabel;
    private JLabel incentivesLabel;
    private JLabel paybackPeriodLabel;
    
    // Energy source data
    private Map<String, Integer> energySources;
    private Map<String, Integer> renewableSources;
    private Map<String, Integer> countries;
    
    public EnergyCalculatorDashboard(String username) {
        this.username = username;
        
        // Set up the frame
        setTitle("Company Dashboard - Carbon Emissions Management");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Initialize data
        loadDataMaps();
        
        // Create main panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        
        // Create header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Create main content panel
        JPanel contentPanel = new JPanel(new BorderLayout(20, 20));
        contentPanel.setBackground(new Color(240, 248, 255));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add input panel
        JPanel inputPanel = createInputPanel();
        contentPanel.add(inputPanel, BorderLayout.NORTH);
        
        // Add results panel
        resultsPanel = createResultsPanel();
        resultsPanel.setVisible(false); // Initially hidden
        contentPanel.add(resultsPanel, BorderLayout.CENTER);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    private void loadDataMaps() {
        // In a real implementation, these would be loaded from the database
        energySources = new HashMap<>();
        energySources.put("Coal", 1);
        energySources.put("Natural Gas", 2);
        energySources.put("Petroleum", 3);
        energySources.put("Grid Electricity", 4);
        
        renewableSources = new HashMap<>();
        renewableSources.put("Solar", 5);
        renewableSources.put("Wind", 6);
        renewableSources.put("Hydro", 7);
        renewableSources.put("Geothermal", 8);
        
        countries = new HashMap<>();
        countries.put("United States", 1);
        countries.put("United Kingdom", 2);
        countries.put("Germany", 3);
        countries.put("Canada", 4);
        countries.put("Australia", 5);
        countries.put("India", 6);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(46, 139, 87)); // Sea green
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel welcomeLabel = new JLabel("Welcome, " + username);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setForeground(Color.WHITE);
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 12));
        logoutButton.setBackground(new Color(220, 220, 220));
        logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
            }
        });
        
        panel.add(welcomeLabel, BorderLayout.WEST);
        panel.add(logoutButton, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(new Color(240, 248, 255));
        
        // Title
        JLabel titleLabel = new JLabel("Calculate Renewable Energy Benefits");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(new Color(46, 139, 87));
        
        // Description
        JLabel descLabel = new JLabel("Enter your energy usage to see potential emissions reductions, cost savings, and incentives");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBackground(new Color(240, 248, 255));
        headerPanel.add(titleLabel);
        headerPanel.add(descLabel);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 15));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(46, 139, 87), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        
        // Current energy source
        JLabel sourceLabel = new JLabel("Current Energy Source:");
        sourceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        energySourceCombo = new JComboBox<>(energySources.keySet().toArray(new String[0]));
        energySourceCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Energy usage
        JLabel usageLabel = new JLabel("Annual Energy Usage (kWh):");
        usageLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        energyUsageField = new JTextField("1000000");
        energyUsageField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Country for incentives
        JLabel countryLabel = new JLabel("Country:");
        countryLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        countryCombo = new JComboBox<>(countries.keySet().toArray(new String[0]));
        countryCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Renewable energy source to transition to
        JLabel renewableLabel = new JLabel("Transition to Renewable Source:");
        renewableLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        renewableSourceCombo = new JComboBox<>(renewableSources.keySet().toArray(new String[0]));
        renewableSourceCombo.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Calculate button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton calculateButton = new JButton("Calculate Benefits");
        styleButton(calculateButton);
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateBenefits();
            }
        });
        
        buttonPanel.add(calculateButton);
        
        // Add components to form
        formPanel.add(sourceLabel);
        formPanel.add(energySourceCombo);
        formPanel.add(usageLabel);
        formPanel.add(energyUsageField);
        formPanel.add(countryLabel);
        formPanel.add(countryCombo);
        formPanel.add(renewableLabel);
        formPanel.add(renewableSourceCombo);
        formPanel.add(new JLabel()); // Empty space
        formPanel.add(buttonPanel);
        
        // Add all to panel
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 15));
        panel.setBackground(new Color(240, 248, 255));
        
        // Results title
        JLabel resultsTitle = new JLabel("Your Renewable Energy Transition Benefits");
        resultsTitle.setFont(new Font("Arial", Font.BOLD, 16));
        resultsTitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Results grid
        JPanel resultsGrid = new JPanel(new GridLayout(3, 2, 15, 15));
        resultsGrid.setBackground(new Color(240, 248, 255));
        
        // Emissions results
        resultsGrid.add(createResultCard(
                "Emissions Impact",
                new String[]{"Current Emissions:", "New Emissions:", "Total Reduction:"},
                new JLabel[]{
                        currentEmissionsLabel = new JLabel("--"),
                        newEmissionsLabel = new JLabel("--"),
                        emissionsReductionLabel = new JLabel("--")
                },
                new Color(70, 130, 180) // Steel blue
        ));
        
        // Cost savings results
        resultsGrid.add(createResultCard(
                "Financial Benefits",
                new String[]{"Annual Cost Savings:", "Available Incentives:", "Payback Period:"},
                new JLabel[]{
                        costSavingsLabel = new JLabel("--"),
                        incentivesLabel = new JLabel("--"),
                        paybackPeriodLabel = new JLabel("--")
                },
                new Color(46, 139, 87) // Sea green
        ));
        
        // New simulation button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 248, 255));
        
        JButton newSimulationButton = new JButton("New Simulation");
        styleButton(newSimulationButton);
        newSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearResults();
            }
        });
        
        JButton saveResultsButton = new JButton("Save Results");
        styleButton(saveResultsButton);
        saveResultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSimulation();
            }
        });
        
        buttonPanel.add(newSimulationButton);
        buttonPanel.add(saveResultsButton);
        
        // Add components to panel
        panel.add(resultsTitle, BorderLayout.NORTH);
        panel.add(resultsGrid, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createResultCard(String title, String[] labels, JLabel[] valueLabels, Color color) {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(color);
        
        JPanel contentPanel = new JPanel(new GridLayout(labels.length, 2, 5, 10));
        contentPanel.setBackground(Color.WHITE);
        
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Arial", Font.PLAIN, 14));
            
            valueLabels[i].setFont(new Font("Arial", Font.BOLD, 14));
            
            contentPanel.add(label);
            contentPanel.add(valueLabels[i]);
        }
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void calculateBenefits() {
        try {
            // Get input values
            String currentSourceName = (String) energySourceCombo.getSelectedItem();
            String renewableSourceName = (String) renewableSourceCombo.getSelectedItem();
            String countryName = (String) countryCombo.getSelectedItem();
            
            double energyAmount = Double.parseDouble(energyUsageField.getText());
            
            int currentSourceId = energySources.get(currentSourceName);
            int renewableSourceId = renewableSources.get(renewableSourceName);
            int countryId = countries.get(countryName);
            
            // Call database functions to calculate values
            try (Connection conn = CarbonEmissionsDBConnection.getConnection()) {
                // Calculate current emissions
                double currentEmissions = calculateEmissions(conn, currentSourceId, energyAmount);
                
                // Calculate new emissions with renewable source
                double newEmissions = calculateEmissions(conn, renewableSourceId, energyAmount);
                
                // Calculate emissions reduction
                double emissionsReduction = currentEmissions - newEmissions;
                
                // Simulate cost savings (in a real implementation, this would be more complex)
                double implementationCost = estimateImplementationCost(renewableSourceId, energyAmount);
                double annualSavings = estimateAnnualSavings(currentSourceId, renewableSourceId, energyAmount);
                
                // Calculate available incentives
                double incentives = calculateIncentives(conn, countryId, renewableSourceId, implementationCost);
                
                // Calculate payback period (in months)
                double adjustedCost = implementationCost - incentives;
                int paybackPeriod = (int) Math.ceil((adjustedCost / annualSavings) * 12);
                
                // Update results labels
                DecimalFormat df = new DecimalFormat("#,##0.00");
                
                currentEmissionsLabel.setText(df.format(currentEmissions) + " tons CO2e");
                newEmissionsLabel.setText(df.format(newEmissions) + " tons CO2e");
                emissionsReductionLabel.setText(df.format(emissionsReduction) + " tons CO2e");
                
                costSavingsLabel.setText("$" + df.format(annualSavings) + " per year");
                incentivesLabel.setText("$" + df.format(incentives));
                paybackPeriodLabel.setText(paybackPeriod + " months");
                
                // Show results panel
                resultsPanel.setVisible(true);
                
                // Repaint to ensure results are displayed correctly
                revalidate();
                repaint();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                    "Please enter a valid number for energy usage.",
                    "Input Error", 
                    JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                    "Database error: " + e.getMessage(),
                    "Database Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private double calculateEmissions(Connection conn, int sourceId, double energyAmount) throws SQLException {
        double emissions = 0;
        
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT calculate_emissions(?, ?) AS emissions")) {
            stmt.setInt(1, sourceId);
            stmt.setDouble(2, energyAmount);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    emissions = rs.getDouble("emissions");
                }
            }
        }
        
        return emissions;
    }
    
    private double calculateIncentives(Connection conn, int countryId, int sourceId, double implementationCost) throws SQLException {
        double incentives = 0;
        
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT calculate_available_incentives(?, ?, ?) AS incentives")) {
            stmt.setInt(1, countryId);
            stmt.setInt(2, sourceId);
            stmt.setDouble(3, implementationCost);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    incentives = rs.getDouble("incentives");
                }
            }
        }
        
        return incentives;
    }
    
    private double estimateImplementationCost(int renewableSourceId, double energyAmount) {
        // Simplified implementation cost estimation
        // In a real application, this would be more complex and based on actual data
        double costPerKwh = 0;
        
        switch (renewableSourceId) {
            case 5: // Solar
                costPerKwh = 1.5;
                break;
            case 6: // Wind
                costPerKwh = 1.2;
                break;
            case 7: // Hydro
                costPerKwh = 2.0;
                break;
            case 8: // Geothermal
                costPerKwh = 2.5;
                break;
            default:
                costPerKwh = 1.5;
        }
        
        return energyAmount * costPerKwh;
    }
    
    private double estimateAnnualSavings(int currentSourceId, int renewableSourceId, double energyAmount) {
        // Simplified annual savings calculation
        // In a real application, this would be more complex and based on actual data
        double currentCostPerKwh = 0;
        double renewableCostPerKwh = 0;
        
        switch (currentSourceId) {
            case 1: // Coal
                currentCostPerKwh = 0.12;
                break;
            case 2: // Natural Gas
                currentCostPerKwh = 0.10;
                break;
            case 3: // Petroleum
                currentCostPerKwh = 0.15;
                break;
            case 4: // Grid Electricity
                currentCostPerKwh = 0.14;
                break;
            default:
                currentCostPerKwh = 0.12;
        }
        
        switch (renewableSourceId) {
            case 5: // Solar
                renewableCostPerKwh = 0.04;
                break;
            case 6: // Wind
                renewableCostPerKwh = 0.03;
                break;
            case 7: // Hydro
                renewableCostPerKwh = 0.05;
                break;
            case 8: // Geothermal
                renewableCostPerKwh = 0.06;
                break;
            default:
                renewableCostPerKwh = 0.04;
        }
        
        return energyAmount * (currentCostPerKwh - renewableCostPerKwh);
    }
    
    private void saveSimulation() {
        JOptionPane.showMessageDialog(this,
                "Simulation saved successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void clearResults() {
        // Hide results panel
        resultsPanel.setVisible(false);
        
        // Reset input fields
        energySourceCombo.setSelectedIndex(0);
        energyUsageField.setText("1000000");
        countryCombo.setSelectedIndex(0);
        renewableSourceCombo.setSelectedIndex(0);
        
        // Repaint to ensure results are hidden correctly
        revalidate();
        repaint();
    }
    
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(46, 139, 87)); // Sea green
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    private void logout() {
        int response = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        
        if (response == JOptionPane.YES_OPTION) {
            this.dispose();
            new MainLoginFrame().setVisible(true);
        }
    }
} 