package com.carbon.emissions;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Access class for interacting with the carbon_emissions_db
 */
public class CarbonEmissionsDataAccess {
    
    /**
     * Calls the update_business_emission_stats stored procedure
     * 
     * @param businessId the business ID to update stats for
     * @return the message returned by the procedure
     * @throws SQLException if a database error occurs
     */
    public String updateBusinessEmissionStats(int businessId) throws SQLException {
        String result = null;
        
        try (Connection conn = CarbonEmissionsDBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL update_business_emission_stats(?, ?)}")) {
            
            stmt.setInt(1, businessId);
            stmt.setBoolean(2, true); // return message
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    result = rs.getString("message");
                }
            }
        }
        
        return result;
    }
    
    /**
     * Gets simulation recommendations for a specific simulation
     * 
     * @param simulationId the simulation ID
     * @return the recommendation text and detailed breakdown
     * @throws SQLException if a database error occurs
     */
    public Map<String, Object> getSimulationRecommendations(int simulationId) throws SQLException {
        Map<String, Object> results = new HashMap<>();
        String recommendation = null;
        List<Map<String, Object>> details = new ArrayList<>();
        
        try (Connection conn = CarbonEmissionsDBConnection.getConnection();
             CallableStatement stmt = conn.prepareCall("{CALL generate_simulation_recommendations(?)}")) {
            
            stmt.setInt(1, simulationId);
            
            boolean hasResults = stmt.execute();
            
            // First result set contains the recommendation
            if (hasResults) {
                try (ResultSet rs = stmt.getResultSet()) {
                    if (rs.next()) {
                        recommendation = rs.getString("simulation_recommendation");
                    }
                }
            }
            
            // Next result set contains the detailed breakdown
            if (stmt.getMoreResults()) {
                try (ResultSet rs = stmt.getResultSet()) {
                    while (rs.next()) {
                        Map<String, Object> detail = new HashMap<>();
                        detail.put("currentSource", rs.getString("current_source"));
                        detail.put("proposedSource", rs.getString("proposed_source"));
                        detail.put("energyAmount", rs.getDouble("energy_amount"));
                        detail.put("emissionsReduction", rs.getDouble("emissions_reduction"));
                        detail.put("reductionPerUnit", rs.getDouble("reduction_per_unit"));
                        detail.put("implementationCost", rs.getDouble("implementation_cost"));
                        detail.put("annualSavings", rs.getDouble("annual_savings"));
                        detail.put("roiPercentage", rs.getDouble("roi_percentage"));
                        details.add(detail);
                    }
                }
            }
        }
        
        results.put("recommendation", recommendation);
        results.put("details", details);
        
        return results;
    }
    
    /**
     * Calculates emissions using the database function
     * 
     * @param sourceId the energy source ID
     * @param energyAmount the amount of energy
     * @return the calculated emissions
     * @throws SQLException if a database error occurs
     */
    public double calculateEmissions(int sourceId, double energyAmount) throws SQLException {
        double emissions = 0;
        
        try (Connection conn = CarbonEmissionsDBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
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
    
    /**
     * Example usage of the data access methods
     */
    public static void main(String[] args) {
        CarbonEmissionsDataAccess dao = new CarbonEmissionsDataAccess();
        
        try {
            // Example: Update business stats
            String updateResult = dao.updateBusinessEmissionStats(1);
            System.out.println("Update result: " + updateResult);
            
            // Example: Get simulation recommendations
            Map<String, Object> recommendations = dao.getSimulationRecommendations(1);
            System.out.println("Recommendation: " + recommendations.get("recommendation"));
            
            // Example: Calculate emissions
            double emissions = dao.calculateEmissions(1, 1000000.0);
            System.out.println("Calculated emissions: " + emissions);
            
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 