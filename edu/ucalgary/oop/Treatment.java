package edu.ucalgary.oop;
import java.sql.*;
import java.util.*;

public class Treatment {
    private Connection dbConnect;
    private int TreatmentID;
    private ResultSet resultsTreatments;

    public Treatment() {
        createConnection();
        extractTreatmentTable();
    }

    public void createConnection() {
        try {
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/ewr", "user1", "ensf");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            resultsTreatments.close();
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void extractTreatmentTable() {        
        try {                    
            Statement myStmt = dbConnect.createStatement();
            resultsTreatments = myStmt.executeQuery("SELECT * FROM treatments");
            
        }
        
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
}
    
