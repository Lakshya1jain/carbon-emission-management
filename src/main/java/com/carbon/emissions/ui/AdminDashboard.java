package com.carbon.emissions.ui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Dashboard for administrators to manage users and system settings
 */
public class AdminDashboard extends JFrame {
    
    private String username;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    
    public AdminDashboard(String username) {
        this.username = username;
        
        // Set up the frame
        setTitle("Admin Dashboard - Carbon Emissions Management");
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
        tabbedPane.addTab("User Management", createUserManagementPanel());
        tabbedPane.addTab("System Settings", createSystemSettingsPanel());
        tabbedPane.addTab("Database Management", createDatabasePanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(70, 130, 180)); // Steel blue
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel welcomeLabel = new JLabel("Admin Panel - Logged in as " + username);
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
        JPanel panel = new JPanel(new GridLayout(2, 2, 15, 15));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // System status card
        panel.add(createSummaryCard(
                "System Status", 
                "Online", 
                "All services running normally",
                new Color(46, 139, 87)));
        
        // User statistics card
        panel.add(createSummaryCard(
                "User Statistics", 
                "145 Active Users", 
                "12 new users this month",
                new Color(70, 130, 180)));
        
        // Database statistics card
        panel.add(createSummaryCard(
                "Database Status", 
                "Healthy", 
                "Last backup: Today 02:00 AM",
                new Color(218, 165, 32)));
        
        // System resources card
        panel.add(createSummaryCard(
                "System Resources", 
                "24% CPU, 56% Memory", 
                "Storage: 42% used (234GB free)",
                new Color(178, 34, 34)));
        
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
    
    private JPanel createUserManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create user management controls
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlsPanel.setBackground(new Color(240, 248, 255));
        
        JButton addUserButton = new JButton("Add User");
        styleButton(addUserButton);
        
        JButton editUserButton = new JButton("Edit User");
        styleButton(editUserButton);
        
        JButton deleteUserButton = new JButton("Delete User");
        styleButton(deleteUserButton);
        deleteUserButton.setBackground(new Color(178, 34, 34)); // Red for delete
        
        JTextField searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JButton searchButton = new JButton("Search");
        styleButton(searchButton);
        
        controlsPanel.add(addUserButton);
        controlsPanel.add(editUserButton);
        controlsPanel.add(deleteUserButton);
        controlsPanel.add(Box.createHorizontalStrut(30));
        controlsPanel.add(searchField);
        controlsPanel.add(searchButton);
        
        // Create user table
        String[] columnNames = {"ID", "Username", "Email", "Role", "Status", "Last Login"};
        Object[][] data = {
                {"1", "johndoe", "john@example.com", "User", "Active", "2023-06-01 10:15 AM"},
                {"2", "janedoe", "jane@example.com", "Analyst", "Active", "2023-06-02 09:30 AM"},
                {"3", "admin", "admin@example.com", "Admin", "Active", "2023-06-02 11:45 AM"},
                {"4", "marksmith", "mark@example.com", "User", "Inactive", "2023-05-15 03:20 PM"},
                {"5", "sarahlee", "sarah@example.com", "Analyst", "Active", "2023-06-01 02:10 PM"}
        };
        
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        
        JTable userTable = new JTable(tableModel);
        userTable.setFont(new Font("Arial", Font.PLAIN, 14));
        userTable.setRowHeight(25);
        userTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane tableScrollPane = new JScrollPane(userTable);
        tableScrollPane.setBorder(BorderFactory.createEtchedBorder());
        
        panel.add(controlsPanel, BorderLayout.NORTH);
        panel.add(tableScrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createSystemSettingsPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create settings sections
        JTabbedPane settingsTabs = new JTabbedPane();
        settingsTabs.setFont(new Font("Arial", Font.BOLD, 14));
        
        // General settings
        JPanel generalPanel = createSettingsSection(
                new String[]{"Application Name", "Maintenance Mode", "Session Timeout (minutes)", "Max Upload Size (MB)"}, 
                new String[]{"Carbon Emissions Management System", "Off", "30", "10"});
        settingsTabs.addTab("General", generalPanel);
        
        // Security settings
        JPanel securityPanel = createSettingsSection(
                new String[]{"Password Minimum Length", "Password Requires Special Char", "Account Lockout Attempts", "Two-Factor Authentication"}, 
                new String[]{"8", "Yes", "5", "Optional"});
        settingsTabs.addTab("Security", securityPanel);
        
        // Email settings
        JPanel emailPanel = createSettingsSection(
                new String[]{"SMTP Server", "SMTP Port", "Sender Email", "Email Encryption"}, 
                new String[]{"smtp.example.com", "587", "noreply@example.com", "TLS"});
        settingsTabs.addTab("Email", emailPanel);
        
        // Backup settings
        JPanel backupPanel = createSettingsSection(
                new String[]{"Automatic Backups", "Backup Frequency", "Backup Retention (days)", "Backup Location"}, 
                new String[]{"Enabled", "Daily", "30", "/var/backups/carbon_emissions"});
        settingsTabs.addTab("Backup", backupPanel);
        
        panel.add(settingsTabs, BorderLayout.CENTER);
        
        // Save button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(240, 248, 255));
        
        JButton saveButton = new JButton("Save All Settings");
        styleButton(saveButton);
        
        JButton resetButton = new JButton("Reset to Defaults");
        resetButton.setFont(new Font("Arial", Font.BOLD, 14));
        resetButton.setBackground(new Color(220, 220, 220));
        resetButton.setForeground(Color.BLACK);
        resetButton.setFocusPainted(false);
        resetButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        resetButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        buttonPanel.add(resetButton);
        buttonPanel.add(saveButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createSettingsSection(String[] labels, String[] values) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 15, 15));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i] + ":");
            label.setFont(new Font("Arial", Font.BOLD, 14));
            
            JTextField field = new JTextField(values[i]);
            field.setFont(new Font("Arial", Font.PLAIN, 14));
            
            panel.add(label);
            panel.add(field);
        }
        
        return panel;
    }
    
    private JPanel createDatabasePanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Database connection panel
        JPanel connectionPanel = new JPanel(new GridLayout(0, 2, 15, 15));
        connectionPanel.setBackground(new Color(240, 248, 255));
        connectionPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), 
                "Database Connection", 
                TitledBorder.LEFT, 
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)));
        
        String[] connLabels = {"Database Type", "Host", "Port", "Database Name", "Username", "Password"};
        String[] connValues = {"MySQL", "localhost", "3306", "carbon_emissions_db", "root", "********"};
        
        for (int i = 0; i < connLabels.length; i++) {
            JLabel label = new JLabel(connLabels[i] + ":");
            label.setFont(new Font("Arial", Font.BOLD, 14));
            
            JTextField field = new JTextField(connValues[i]);
            field.setFont(new Font("Arial", Font.PLAIN, 14));
            
            connectionPanel.add(label);
            connectionPanel.add(field);
        }
        
        // Database management buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(240, 248, 255));
        
        JButton testConnButton = new JButton("Test Connection");
        styleButton(testConnButton);
        
        JButton backupButton = new JButton("Backup Database");
        styleButton(backupButton);
        
        JButton restoreButton = new JButton("Restore Database");
        styleButton(restoreButton);
        
        JButton optimizeButton = new JButton("Optimize Database");
        styleButton(optimizeButton);
        
        buttonPanel.add(testConnButton);
        buttonPanel.add(backupButton);
        buttonPanel.add(restoreButton);
        buttonPanel.add(optimizeButton);
        
        // Database status panel
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusPanel.setBackground(Color.WHITE);
        statusPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), 
                "Database Status", 
                TitledBorder.LEFT, 
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)));
        
        JTextArea statusArea = new JTextArea();
        statusArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        statusArea.setEditable(false);
        statusArea.setText("Connected to: MySQL Server 8.0.33\n" +
                "Database: carbon_emissions_db\n" +
                "Status: Connected\n" +
                "Tables: 12\n" +
                "Size: 24.5 MB\n" +
                "Last Backup: 2023-06-02 02:00 AM");
        
        JScrollPane statusScrollPane = new JScrollPane(statusArea);
        statusPanel.add(statusScrollPane, BorderLayout.CENTER);
        
        // Add components to main panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(240, 248, 255));
        topPanel.add(connectionPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(statusPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180)); // Steel blue
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