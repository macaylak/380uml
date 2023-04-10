package edu.ucalgary.oop;

import java.sql.*;
import java.util.*;

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
    private List<String> coyoteNicknames = new ArrayList<String>();
    private List<String> raccoonNicknames = new ArrayList<String>();
    private List<String> beaverNicknames = new ArrayList<String>();
    private List<String> foxNicknames = new ArrayList<String>();
    private List<String> porcupineNicknames = new ArrayList<String>();

    public Animal() {
        createConnection();
        extractAnimalTable();
    }

    public void createConnection() {
        try {
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/ewr", "ensf380", "ensf380");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void extractAnimalTable() {
        try {
            Statement myStmt = dbConnect.createStatement();
            resultsAnimals = myStmt.executeQuery("SELECT * FROM animals");
    
            while (resultsAnimals.next()) {
                String species = resultsAnimals.getString("AnimalSpecies");
                String nickname = resultsAnimals.getString("AnimalNickname");
                
                switch (species) {
                    case "coyote":
                        coyoteNicknames.add(nickname);
                        numCoyotes++;
                        break;
                    case "raccoon":
                        raccoonNicknames.add(nickname);
                        numRaccoons++;
                        break;
                    case "beaver":
                        beaverNicknames.add(nickname);
                        numBeavers++;
                        break;
                    case "fox":
                        foxNicknames.add(nickname);
                        numFoxes++;
                        break;
                    case "porcupine":
                        porcupineNicknames.add(nickname);
                        numPorcupines++;
                        break;
                    default:
                        break;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public int getNumCoyotes() { return numCoyotes;}
    public int getNumBeavers() { return numBeavers;}
    public int getNumFoxes() { return numFoxes;}
    public int getNumRaccoons() { return numRaccoons; }
    public int getNumPorcupines() { return numPorcupines;}

    public List<String> getCoyoteNicknames() { return coyoteNicknames;}   
    public List<String> getFoxNicknames() { return foxNicknames; }   
    public List<String> getPorcupineNicknames() { return porcupineNicknames; }  
    public List<String> getRaccoonNicknames() { return raccoonNicknames; }      
    public List<String> getBeaverNicknames() { return beaverNicknames; }

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
}

