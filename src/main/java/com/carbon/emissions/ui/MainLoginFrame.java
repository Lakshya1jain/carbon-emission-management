package com.carbon.emissions.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main login frame for the Carbon Emissions Management System
 */
public class MainLoginFrame extends JFrame {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    
    public MainLoginFrame() {
        // Set up the frame
        setTitle("Carbon Emissions Management System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        
        // Create components with modern look
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 248, 255)); // Light blue background
        
        // Header panel with logo
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(240, 248, 255)); // Same color as main panel
        
        JLabel titleLabel = new JLabel("Carbon Emissions Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 100, 0)); // Dark green text
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Try to load the icon, but provide a text fallback if it's not found
        JLabel iconLabel = new JLabel();
        try {
            // First try with class loader
            ImageIcon icon = new ImageIcon(getClass().getResource("/tree_icon.png"));
            if (icon.getIconWidth() <= 0) {
                // If failed, try absolute path in resources directory
                icon = new ImageIcon(getClass().getResource("/com/carbon/emissions/ui/resources/tree_icon.png"));
            }
            
            // If icon still not loaded, use emoji fallback
            if (icon.getIconWidth() <= 0) {
                iconLabel.setText("🌳");
                iconLabel.setFont(new Font("Arial", Font.PLAIN, 48));
                iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
            } else {
                iconLabel.setIcon(icon);
                iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
            }
        } catch (Exception e) {
            // Fallback to emoji
            iconLabel.setText("🌳");
            iconLabel.setFont(new Font("Arial", Font.PLAIN, 48));
            iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        
        headerPanel.add(iconLabel, BorderLayout.CENTER);
        headerPanel.add(titleLabel, BorderLayout.SOUTH);
        
        // Login form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 2, 10, 15));
        formPanel.setBackground(new Color(240, 248, 255));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        String[] roles = {"User", "Admin", "Analyst"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(roleLabel);
        formPanel.add(roleComboBox);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(new Color(240, 248, 255));
        
        JButton loginButton = new JButton("Login");
        styleButton(loginButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        
        JButton cancelButton = new JButton("Cancel");
        styleButton(cancelButton);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        
        // Add all panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        add(mainPanel);
    }
    
    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(46, 139, 87)); // Sea green
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();
        
        // Basic validation - in real app, check against database
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password", 
                    "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // For demo, we'll just check that fields aren't empty
        if (!username.isEmpty() && !password.isEmpty()) {
            // Open appropriate dashboard based on role
            JFrame dashboard = null;
            
            switch (role) {
                case "User":
                    dashboard = new EnergyCalculatorDashboard(username);
                    break;
                case "Admin":
                    dashboard = new AdminDashboard(username);
                    break;
                case "Analyst":
                    dashboard = new AnalystDashboard(username);
                    break;
            }
            
            if (dashboard != null) {
                dashboard.setVisible(true);
                this.dispose(); // Close the login window
            }
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid username, password, or role. Please try again.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        // Use system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create and display the login form
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainLoginFrame().setVisible(true);
            }
        });
    }
} 