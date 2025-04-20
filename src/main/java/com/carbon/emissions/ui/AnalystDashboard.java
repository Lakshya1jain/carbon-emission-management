package com.carbon.emissions.ui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * Dashboard for analysts to analyze and visualize carbon emissions data
 */
public class AnalystDashboard extends JFrame {
    
    private String username;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    
    public AnalystDashboard(String username) {
        this.username = username;
        
        // Set up the frame
        setTitle("Analyst Dashboard - Carbon Emissions Management");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Create main panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        
        // Create header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Create tabbed pane for different sections
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Add tabs
        tabbedPane.addTab("Dashboard", createDashboardPanel());
        tabbedPane.addTab("Data Analysis", createDataAnalysisPanel());
        tabbedPane.addTab("Visualizations", createVisualizationsPanel());
        tabbedPane.addTab("Reports", createReportsPanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(32, 178, 170)); // Light sea green
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel welcomeLabel = new JLabel("Analyst Portal - Logged in as " + username);
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
    
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Summary cards panel
        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        cardsPanel.setBackground(new Color(240, 248, 255));
        
        cardsPanel.add(createSummaryCard(
                "Total Businesses", 
                "257", 
                "12% increase from last quarter",
                new Color(32, 178, 170)));
        
        cardsPanel.add(createSummaryCard(
                "Total Simulations", 
                "1,245", 
                "356 this month alone",
                new Color(32, 178, 170)));
        
        cardsPanel.add(createSummaryCard(
                "Average Reduction", 
                "28.5%", 
                "Potential CO2 reduction across all businesses",
                new Color(32, 178, 170)));
        
        // Recent activity panel
        JPanel activityPanel = new JPanel(new BorderLayout());
        activityPanel.setBackground(Color.WHITE);
        activityPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), 
                "Recent Analysis Activity", 
                TitledBorder.LEFT, 
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)));
        
        String[] activityData = {
                "2023-06-02 11:23 - Generated 'Manufacturing Sector CO2 Analysis' report",
                "2023-06-02 10:05 - Created new simulation for Business ID: 145",
                "2023-06-01 15:30 - Updated baseline data for Healthcare sector",
                "2023-06-01 13:12 - Ran comparative analysis for Tech sector vs. Manufacturing",
                "2023-05-31 16:45 - Exported Q2 projection data for executive review",
                "2023-05-31 11:20 - Created visualization set for renewable energy transition",
                "2023-05-30 14:55 - Updated predictive models with new efficiency factors"
        };
        
        JList<String> activityList = new JList<>(activityData);
        activityList.setFont(new Font("Arial", Font.PLAIN, 14));
        activityList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane activityScroll = new JScrollPane(activityList);
        activityPanel.add(activityScroll, BorderLayout.CENTER);
        
        // Add all components to main panel
        panel.add(cardsPanel, BorderLayout.NORTH);
        panel.add(activityPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createSummaryCard(String title, String value, String description, Color color) {
        JPanel panel = new JPanel(new BorderLayout(0, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(color, 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(color);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setForeground(Color.GRAY);
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        panel.add(descLabel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createDataAnalysisPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(new Color(240, 248, 255));
        
        JLabel businessLabel = new JLabel("Business Sector:");
        businessLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        String[] sectors = {"All Sectors", "Manufacturing", "Technology", "Healthcare", "Retail", "Energy", "Transportation"};
        JComboBox<String> sectorCombo = new JComboBox<>(sectors);
        
        JLabel dateLabel = new JLabel("Date Range:");
        dateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        String[] dateRanges = {"Last Quarter", "Last Year", "Last 2 Years", "Custom"};
        JComboBox<String> dateCombo = new JComboBox<>(dateRanges);
        
        JLabel analysisLabel = new JLabel("Analysis Type:");
        analysisLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        String[] analysisTypes = {"Carbon Footprint", "Energy Efficiency", "Cost Impact", "Regulatory Compliance"};
        JComboBox<String> analysisCombo = new JComboBox<>(analysisTypes);
        
        JButton runButton = new JButton("Run Analysis");
        styleButton(runButton);
        
        filterPanel.add(businessLabel);
        filterPanel.add(sectorCombo);
        filterPanel.add(Box.createHorizontalStrut(15));
        filterPanel.add(dateLabel);
        filterPanel.add(dateCombo);
        filterPanel.add(Box.createHorizontalStrut(15));
        filterPanel.add(analysisLabel);
        filterPanel.add(analysisCombo);
        filterPanel.add(Box.createHorizontalStrut(15));
        filterPanel.add(runButton);
        
        // Results panel with tabs
        JTabbedPane resultsTabs = new JTabbedPane();
        resultsTabs.setFont(new Font("Arial", Font.BOLD, 14));
        
        // Results table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        
        String[] columnNames = {"Business ID", "Business Name", "Sector", "Current Emissions (tons)", "Potential Reduction (%)", "Cost Savings ($)", "ROI (%)"};
        Object[][] data = {
                {101, "TechSolutions Inc.", "Technology", 1250.5, 22.5, 78500, 15.3},
                {102, "GreenManufacturing Co.", "Manufacturing", 3450.2, 35.0, 125000, 22.7},
                {103, "HealthPlus Services", "Healthcare", 2100.8, 18.2, 45000, 12.1},
                {104, "QuickRetail Stores", "Retail", 1875.3, 24.8, 68000, 18.5},
                {105, "PowerGen Solutions", "Energy", 5200.6, 42.3, 234000, 28.9}
        };
        
        JTable resultsTable = new JTable(data, columnNames);
        resultsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        resultsTable.setRowHeight(25);
        resultsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        
        JScrollPane tableScroll = new JScrollPane(resultsTable);
        tablePanel.add(tableScroll, BorderLayout.CENTER);
        
        // Summary panel
        JPanel summaryPanel = new JPanel(new BorderLayout());
        summaryPanel.setBackground(Color.WHITE);
        
        JTextArea summaryArea = new JTextArea();
        summaryArea.setFont(new Font("Arial", Font.PLAIN, 14));
        summaryArea.setEditable(false);
        summaryArea.setText("ANALYSIS SUMMARY - CARBON FOOTPRINT BY SECTOR\n\n" +
                "Date Range: Last Quarter (Apr 2023 - Jun 2023)\n" +
                "Total Businesses Analyzed: 257\n\n" +
                "Key Findings:\n" +
                "1. Manufacturing sector shows highest absolute emissions (avg 3,240 tons CO2e)\n" +
                "2. Technology sector demonstrates best reduction potential (avg 28.5%)\n" +
                "3. Energy sector has highest ROI on transition investments (avg 27.2%)\n" +
                "4. Healthcare sector shows slowest adoption rate of green technologies\n\n" +
                "Recommendations:\n" +
                "- Focus incentive programs on manufacturing sector for highest absolute impact\n" +
                "- Develop specialized transitional strategies for healthcare businesses\n" +
                "- Expand educational resources on high-ROI energy transition options");
        
        JScrollPane summaryScroll = new JScrollPane(summaryArea);
        summaryPanel.add(summaryScroll, BorderLayout.CENTER);
        
        resultsTabs.addTab("Results Table", tablePanel);
        resultsTabs.addTab("Analysis Summary", summaryPanel);
        
        // Add components to main panel
        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(resultsTabs, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(240, 248, 255));
        
        JButton exportButton = new JButton("Export Results");
        styleButton(exportButton);
        
        JButton saveButton = new JButton("Save Analysis");
        styleButton(saveButton);
        
        buttonPanel.add(saveButton);
        buttonPanel.add(exportButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createVisualizationsPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Chart options panel
        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        optionsPanel.setBackground(new Color(240, 248, 255));
        
        JLabel chartLabel = new JLabel("Chart Type:");
        chartLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        String[] chartTypes = {"Bar Chart", "Line Chart", "Pie Chart", "Scatter Plot", "Heat Map"};
        JComboBox<String> chartCombo = new JComboBox<>(chartTypes);
        
        JLabel dataLabel = new JLabel("Data Series:");
        dataLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        String[] dataSeries = {"Emissions by Sector", "Reduction Potential", "Cost Savings", "Emissions Over Time"};
        JComboBox<String> dataCombo = new JComboBox<>(dataSeries);
        
        JButton generateButton = new JButton("Generate Chart");
        styleButton(generateButton);
        
        optionsPanel.add(chartLabel);
        optionsPanel.add(chartCombo);
        optionsPanel.add(Box.createHorizontalStrut(15));
        optionsPanel.add(dataLabel);
        optionsPanel.add(dataCombo);
        optionsPanel.add(Box.createHorizontalStrut(15));
        optionsPanel.add(generateButton);
        
        // Chart display panel with sample pie chart
        JPanel chartPanel = new JPanel(new BorderLayout());
        chartPanel.setBackground(Color.WHITE);
        chartPanel.setBorder(BorderFactory.createEtchedBorder());
        
        JPanel pieChartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw pie chart
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                int radius = Math.min(getWidth(), getHeight()) / 3;
                
                // Sample data for sectors
                String[] sectors = {"Manufacturing", "Energy", "Technology", "Retail", "Healthcare"};
                double[] values = {35.0, 28.5, 15.2, 12.8, 8.5};
                Color[] colors = {
                        new Color(46, 139, 87),    // Sea green
                        new Color(70, 130, 180),   // Steel blue
                        new Color(218, 165, 32),   // Goldenrod
                        new Color(178, 34, 34),    // Firebrick
                        new Color(72, 61, 139)     // Dark slate blue
                };
                
                double total = 0;
                for (double value : values) {
                    total += value;
                }
                
                double startAngle = 0;
                DecimalFormat df = new DecimalFormat("#.#");
                
                // Draw pie slices
                for (int i = 0; i < values.length; i++) {
                    double arcAngle = 360.0 * (values[i] / total);
                    
                    g2d.setColor(colors[i]);
                    Arc2D.Double arc = new Arc2D.Double(
                            centerX - radius, centerY - radius,
                            2 * radius, 2 * radius,
                            startAngle, arcAngle, Arc2D.PIE);
                    g2d.fill(arc);
                    
                    // Draw labels
                    double middleAngle = Math.toRadians(startAngle + arcAngle / 2);
                    double labelRadius = radius * 1.3;
                    int labelX = (int) (centerX + labelRadius * Math.cos(middleAngle));
                    int labelY = (int) (centerY - labelRadius * Math.sin(middleAngle));
                    
                    g2d.setColor(Color.BLACK);
                    g2d.drawString(sectors[i] + " (" + df.format(values[i]) + "%)", labelX - 50, labelY);
                    
                    startAngle += arcAngle;
                }
                
                // Draw title
                g2d.setFont(new Font("Arial", Font.BOLD, 16));
                g2d.drawString("Carbon Emissions by Sector", centerX - 100, 30);
            }
        };
        pieChartPanel.setBackground(Color.WHITE);
        
        chartPanel.add(pieChartPanel, BorderLayout.CENTER);
        
        // Legend and export panel
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBackground(new Color(240, 248, 255));
        
        JPanel legendPanel = new JPanel(new GridLayout(0, 3, 10, 5));
        legendPanel.setBackground(new Color(240, 248, 255));
        legendPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Legend",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)));
        
        String[] sectors = {"Manufacturing", "Energy", "Technology", "Retail", "Healthcare"};
        Color[] colors = {
                new Color(46, 139, 87),    // Sea green
                new Color(70, 130, 180),   // Steel blue
                new Color(218, 165, 32),   // Goldenrod
                new Color(178, 34, 34),    // Firebrick
                new Color(72, 61, 139)     // Dark slate blue
        };
        
        for (int i = 0; i < sectors.length; i++) {
            JPanel colorPanel = new JPanel();
            colorPanel.setBackground(colors[i]);
            colorPanel.setPreferredSize(new Dimension(20, 20));
            
            JLabel sectorLabel = new JLabel(" " + sectors[i]);
            sectorLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBackground(new Color(240, 248, 255));
            itemPanel.add(colorPanel, BorderLayout.WEST);
            itemPanel.add(sectorLabel, BorderLayout.CENTER);
            
            legendPanel.add(itemPanel);
        }
        
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBackground(new Color(240, 248, 255));
        
        JButton exportImageButton = new JButton("Export as Image");
        styleButton(exportImageButton);
        
        JButton exportDataButton = new JButton("Export Data");
        styleButton(exportDataButton);
        
        buttonsPanel.add(exportDataButton);
        buttonsPanel.add(exportImageButton);
        
        controlPanel.add(legendPanel, BorderLayout.CENTER);
        controlPanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        // Add all components to main panel
        panel.add(optionsPanel, BorderLayout.NORTH);
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Report templates panel
        JPanel templatesPanel = new JPanel(new BorderLayout());
        templatesPanel.setBackground(Color.WHITE);
        templatesPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Report Templates",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)));
        
        String[] templates = {
                "Executive Summary - Carbon Reduction Opportunities",
                "Sector Analysis - Comparing Emissions Across Industries",
                "Business Efficiency Report - Detailed Recommendations",
                "ROI Analysis - Financial Benefits of Carbon Reduction",
                "Compliance Report - Regulatory Requirements Analysis",
                "Trend Analysis - Emissions Over Time",
                "Custom Report - Build Your Own"
        };
        
        JList<String> templateList = new JList<>(templates);
        templateList.setFont(new Font("Arial", Font.PLAIN, 14));
        templateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        templateList.setSelectedIndex(0);
        
        JScrollPane templateScroll = new JScrollPane(templateList);
        templatesPanel.add(templateScroll, BorderLayout.CENTER);
        
        // Report options panel
        JPanel optionsPanel = new JPanel(new GridLayout(0, 2, 15, 10));
        optionsPanel.setBackground(new Color(240, 248, 255));
        optionsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Report Options",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)));
        
        JLabel titleLabel = new JLabel("Report Title:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JTextField titleField = new JTextField("Executive Summary - Carbon Reduction Opportunities");
        
        JLabel sectorLabel = new JLabel("Business Sector:");
        sectorLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        String[] sectors = {"All Sectors", "Manufacturing", "Technology", "Healthcare", "Retail", "Energy"};
        JComboBox<String> sectorCombo = new JComboBox<>(sectors);
        
        JLabel periodLabel = new JLabel("Reporting Period:");
        periodLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        String[] periods = {"Current Quarter", "Last Quarter", "Current Year", "Last Year", "Custom"};
        JComboBox<String> periodCombo = new JComboBox<>(periods);
        
        JLabel formatLabel = new JLabel("Output Format:");
        formatLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        String[] formats = {"PDF", "Excel", "Word", "HTML", "CSV"};
        JComboBox<String> formatCombo = new JComboBox<>(formats);
        
        JLabel includeLabel = new JLabel("Include Charts:");
        includeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JCheckBox includeCheck = new JCheckBox();
        includeCheck.setSelected(true);
        includeCheck.setBackground(new Color(240, 248, 255));
        
        JLabel detailLabel = new JLabel("Detail Level:");
        detailLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        String[] details = {"Summary", "Detailed", "Comprehensive"};
        JComboBox<String> detailCombo = new JComboBox<>(details);
        
        optionsPanel.add(titleLabel);
        optionsPanel.add(titleField);
        optionsPanel.add(sectorLabel);
        optionsPanel.add(sectorCombo);
        optionsPanel.add(periodLabel);
        optionsPanel.add(periodCombo);
        optionsPanel.add(formatLabel);
        optionsPanel.add(formatCombo);
        optionsPanel.add(includeLabel);
        optionsPanel.add(includeCheck);
        optionsPanel.add(detailLabel);
        optionsPanel.add(detailCombo);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(240, 248, 255));
        
        JButton previewButton = new JButton("Preview Report");
        styleButton(previewButton);
        
        JButton generateButton = new JButton("Generate Report");
        styleButton(generateButton);
        
        buttonPanel.add(previewButton);
        buttonPanel.add(generateButton);
        
        // Add components to main panel
        JPanel topPanel = new JPanel(new GridLayout(2, 1, 0, 15));
        topPanel.setBackground(new Color(240, 248, 255));
        topPanel.add(templatesPanel);
        topPanel.add(optionsPanel);
        
        panel.add(topPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(32, 178, 170)); // Light sea green
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