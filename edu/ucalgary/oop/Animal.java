package edu.ucalgary.oop;

import java.sql.*;

public class Animal {
    private static Connection dbConnect;
    private static ResultSet resultsAnimals;
    private String AnimalID;
    private String AnimalNickname;
    private String AnimalSpecies;
    private static int numCoyotes;
    private static int numRaccoons;
    private static int numBeavers;
    private static int numFoxes;
    private static int numPorcupines;

    public Animal() {
        createConnection();
        extractAnimalTable();
    }

    public void createConnection() {
        try {
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/ewr", "root", "sqlpassword");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void extractAnimalTable() {
        try {
            Statement myStmt = dbConnect.createStatement();
            resultsAnimals = myStmt.executeQuery("SELECT * FROM animals");
            
            ResultSet rsCoyotes = myStmt.executeQuery("SELECT COUNT(*) FROM ANIMALS WHERE AnimalSpecies = 'coyote'");
            if (rsCoyotes.next()) {
                numCoyotes = rsCoyotes.getInt(1);
            }
            
            ResultSet rsRaccoons = myStmt.executeQuery("SELECT COUNT(*) FROM ANIMALS WHERE AnimalSpecies = 'raccoon'");
            if (rsRaccoons.next()) {
                numRaccoons = rsRaccoons.getInt(1);
            }
            
            ResultSet rsBeavers = myStmt.executeQuery("SELECT COUNT(*) FROM ANIMALS WHERE AnimalSpecies = 'beaver'");
            if (rsBeavers.next()) {
                numBeavers = rsBeavers.getInt(1);
            }
            
            ResultSet rsFoxes = myStmt.executeQuery("SELECT COUNT(*) FROM ANIMALS WHERE AnimalSpecies = 'fox'");
            if (rsFoxes.next()) {
                numFoxes = rsFoxes.getInt(1);
            }
            
            ResultSet rsPorcupines = myStmt.executeQuery("SELECT COUNT(*) FROM ANIMALS WHERE AnimalSpecies = 'porcupine'");
            if (rsPorcupines.next()) {
                numPorcupines = rsPorcupines.getInt(1);
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static int getNumCoyotes() {
        return numCoyotes;
    }


    public static int getNumBeavers() {
        return numBeavers;
    }

    public static int getNumFoxes() {
        return numFoxes;
    }
    public static int getNumRaccoons() {
        return numRaccoons;
    }

    public static int getNumPorcupines() {
        return numPorcupines;
    }

    public void close() {
        try {
            resultsAnimals.close();
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // getters and setters
    public String getAnimalID() {
        return AnimalID;
    }

    public void setAnimalID(String animalID) {
        AnimalID = animalID;
    }

    public String getAnimalNickname() {
        return AnimalNickname;
    }

    public void setAnimalNickname(String animalNickname) {
        AnimalNickname = animalNickname;
    }

    public String getAnimalSpecies() {
        return AnimalSpecies;
    }

    public void setAnimalSpecies(String animalSpecies) {
        AnimalSpecies = animalSpecies;
    }

    public static void main(String[] args) {
        Animal animal = new Animal();
        System.out.println(Animal.getNumCoyotes());
        System.out.println(Animal.getNumBeavers());
        System.out.println(Animal.getNumFoxes());
        System.out.println(Animal.getNumRaccoons());
        System.out.println(Animal.getNumPorcupines());
        animal.close();
    }
}

