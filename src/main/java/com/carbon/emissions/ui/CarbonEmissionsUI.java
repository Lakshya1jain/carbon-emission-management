package com.carbon.emissions.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Main class to launch the Carbon Emissions Management System UI
 */
public class CarbonEmissionsUI {
    
    public static void main(String[] args) {
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Custom button styling
            UIManager.put("Button.background", new Color(46, 139, 87)); // Sea green
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("Button.font", new Font("Arial", Font.BOLD, 14));
            
            // Custom panel styling
            UIManager.put("Panel.background", new Color(240, 248, 255)); // Light blue
            
            // Tab styling
            UIManager.put("TabbedPane.selected", new Color(220, 240, 220));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Launch the login frame
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainLoginFrame loginFrame = new MainLoginFrame();
                loginFrame.setVisible(true);
            }
        });
    }
} 