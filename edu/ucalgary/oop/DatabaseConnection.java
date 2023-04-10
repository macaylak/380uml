// package edu.ucalgary.oop;
// import java.sql.*;
// import java.util.*;

// public class DatabaseConnection {
//     private static Connection dbConnect;
    
//     public DatabaseConnection() {
//         createConnection();
//         Animal.extractAnimalTable();
//         Task.extractTaskTable();
//         Treatment.extractTreatmentTable();
//         Schedule.createTaskMap();//have mainhashmap
//     }
    

//     public static void createConnection() {
//         try {
//             dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/ewr", "root", "sqlpassword");
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }

//     public void close() {
//         try {
//             dbConnect.close();
//         } catch (SQLException e) {
//             e.printStackTrace();
//         }
//     }

// }

