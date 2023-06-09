package edu.ucalgary.oop;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class Task {
    private  Connection dbConnect;
    private int feedingTimeCoyote;
    private int feedingTimeRaccoon;
    private int feedingTimeBeaver;
    private int feedingTimePorcupine;
    private int feedingTimeFox;
    private Task resultsTask;


    public Task() {
        createConnection();
        extractTaskTable();
    }

    public void createConnection() {
        try {
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/ewr", "ensf380", "ensf380");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            resultsTask.close();
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void extractTaskTable() {
        try {
            Statement myStmt = dbConnect.createStatement();
            ResultSet resultsTasks = myStmt.executeQuery("SELECT * FROM tasks");
            
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static HashMap<Integer, List<Map<String, String>>> getMainHashmapWithTasksandStartHours(){
        return Schedule.mainHashmapWithTasksAndStartHours;
    }
    
    public static void feedingTimeCoyote(Schedule db, Animal animalInfo) throws IllegalArgumentException, InsufficientFeedingTimeException {
        if (db == null) {
            throw new IllegalArgumentException("Database object is empty.");
        }
        if (animalInfo == null) {
            throw new IllegalArgumentException("Animal object is empty.");
        }
        HashMap<Integer, Integer> minutesLeftmap = Schedule.getMinutesLeftMap(Schedule.getMainHashmapWithTasksandStartHours());
        int numCoyotes = animalInfo.getNumCoyotes();
        List<String> coyoteNicknames = animalInfo.getCoyoteNicknames();
        int feedingPrepTime = AnimalType.COYOTE.getFeedingPrepTime();
        int feedingTimePerCoyote = AnimalType.COYOTE.getFeedingTime();
    
        int minutesLeft19 = minutesLeftmap.getOrDefault(19, 0);
        int minutesLeft20 = minutesLeftmap.getOrDefault(20, 0);
        int minutesLeft21 = minutesLeftmap.getOrDefault(21, 0);
    
        int numCoyotesInHour19 = Math.min(numCoyotes, (minutesLeft19 - feedingPrepTime) / feedingTimePerCoyote);
        numCoyotes -= numCoyotesInHour19;
    
        int numCoyotesInHour20 = Math.min(numCoyotes, (minutesLeft20 - feedingPrepTime) / feedingTimePerCoyote);
        numCoyotes -= numCoyotesInHour20;
    
        int numCoyotesInHour21 = Math.min(numCoyotes, (minutesLeft21 - feedingPrepTime) / feedingTimePerCoyote);
    
        if (numCoyotesInHour19 > 0) {
            int totalFeedingTimeInHour19 = numCoyotesInHour19 * feedingTimePerCoyote + feedingPrepTime;
            db.insertFeedingCoyotesToHashmap(19, totalFeedingTimeInHour19, numCoyotesInHour19, coyoteNicknames.subList(0, numCoyotesInHour19));
        }
        if (numCoyotesInHour20 > 0) {
            int totalFeedingTimeInHour20 = numCoyotesInHour20 * feedingTimePerCoyote + feedingPrepTime;
            db.insertFeedingCoyotesToHashmap(20, totalFeedingTimeInHour20, numCoyotesInHour20, coyoteNicknames.subList(numCoyotesInHour19, numCoyotesInHour19 + numCoyotesInHour20));
        }
        if (numCoyotesInHour21 > 0) {
            int totalFeedingTimeInHour21 = numCoyotesInHour21 * feedingTimePerCoyote + feedingPrepTime;
            db.insertFeedingCoyotesToHashmap(21, totalFeedingTimeInHour21, numCoyotesInHour21, coyoteNicknames.subList(numCoyotesInHour19 + numCoyotesInHour20, numCoyotesInHour19 + numCoyotesInHour20 + numCoyotesInHour21));
        }
        if (numCoyotes > 0 && minutesLeft19 < feedingPrepTime + numCoyotes * feedingTimePerCoyote && minutesLeft20 < feedingPrepTime + numCoyotes * feedingTimePerCoyote && minutesLeft21 < feedingPrepTime + numCoyotes * feedingTimePerCoyote) {
            throw new InsufficientFeedingTimeException("There is not enough time to feed all of the coyotes in the designated times.");
        }      
    }
    

    public void feedingTimeFox(Schedule db, Animal animalInfo) throws IllegalArgumentException, InsufficientFeedingTimeException {
        if (db == null) {
            throw new IllegalArgumentException("Database object is empty.");
        }
        if (animalInfo == null) {
            throw new IllegalArgumentException("Animal object is empty.");
        }
        HashMap<Integer, Integer> minutesLeftmap = Schedule.getMinutesLeftMap(Schedule.getMainHashmapWithTasksandStartHours());
        int numFoxes = animalInfo.getNumFoxes();
        List<String> foxNicknames = animalInfo.getFoxNicknames();
        int feedingPrepTime = AnimalType.FOX.getFeedingPrepTime();
        int feedingTimePerFox = AnimalType.FOX.getFeedingTime();
    
        int minutesLeft0 = minutesLeftmap.getOrDefault(0, 0);
        int minutesLeft1 = minutesLeftmap.getOrDefault(1, 0);
        int minutesLeft2 = minutesLeftmap.getOrDefault(2, 0);
    
        int numFoxesInHour0 = Math.min(numFoxes, (minutesLeft0 - feedingPrepTime) / feedingTimePerFox);
        numFoxes -= numFoxesInHour0;
    
        int numFoxesInHour1 = Math.min(numFoxes, (minutesLeft1 - feedingPrepTime) / feedingTimePerFox);
        numFoxes -= numFoxesInHour1;
    
        int numFoxesInHour2 = Math.min(numFoxes, (minutesLeft2 - feedingPrepTime) / feedingTimePerFox);
    
        if (numFoxesInHour0 > 0) {
            int totalFeedingTimeInHour0 = numFoxesInHour0 * feedingTimePerFox + feedingPrepTime;
            db.insertFeedingFoxToHashmap(0, totalFeedingTimeInHour0, numFoxesInHour0, foxNicknames.subList(0, numFoxesInHour0));
        }
        if (numFoxesInHour1 > 0) {
            int totalFeedingTimeInHour1 = numFoxesInHour1 * feedingTimePerFox + feedingPrepTime;
            db.insertFeedingFoxToHashmap(1, totalFeedingTimeInHour1, numFoxesInHour1, foxNicknames.subList(numFoxesInHour0, numFoxesInHour0 + numFoxesInHour1));
        }
        if (numFoxesInHour2 > 0) {
            int totalFeedingTimeInHour2 = numFoxesInHour2 * feedingTimePerFox + feedingPrepTime;
            db.insertFeedingFoxToHashmap(2, totalFeedingTimeInHour2, numFoxesInHour2, foxNicknames.subList(numFoxesInHour0 + numFoxesInHour1, numFoxesInHour0 + numFoxesInHour1 + numFoxesInHour2));
        }
        if (numFoxes > 0 && minutesLeft0 < feedingPrepTime + numFoxes * feedingTimePerFox && minutesLeft1 < feedingPrepTime + numFoxes * feedingTimePerFox && minutesLeft2 < feedingPrepTime + numFoxes * feedingTimePerFox) {
            throw new InsufficientFeedingTimeException("There is not enough time to feed all of the foxes in the designated times.");
        }
    }
    
    public void feedingTimePorcupine(Schedule db, Animal animalInfo) throws IllegalArgumentException, InsufficientFeedingTimeException {
        if (db == null) {
            throw new IllegalArgumentException("Database object is empty.");
        }
        if (animalInfo == null) {
            throw new IllegalArgumentException("Animal object is empty.");
        }
        HashMap<Integer, Integer> minutesLeftmap = Schedule.getMinutesLeftMap(Schedule.getMainHashmapWithTasksandStartHours());
        int numPorcupines = animalInfo.getNumPorcupines();
        List<String> porcupineNicknames = animalInfo.getPorcupineNicknames();
        int feedingPrepTime = AnimalType.PORCUPINE.getFeedingPrepTime();
        int feedingTimePerPorcupine = AnimalType.PORCUPINE.getFeedingTime();
    
        int minutesLeft19 = minutesLeftmap.getOrDefault(19, 0);
        int minutesLeft20 = minutesLeftmap.getOrDefault(20, 0);
        int minutesLeft21 = minutesLeftmap.getOrDefault(21, 0);
    
        int numPorcupineInHour19 = Math.min(numPorcupines, (minutesLeft19 - feedingPrepTime) / feedingTimePerPorcupine);
        numPorcupines -= numPorcupineInHour19;
    
        int numPorcupineInHour20 = Math.min(numPorcupines, (minutesLeft20 - feedingPrepTime) / feedingTimePerPorcupine);
        numPorcupines -= numPorcupineInHour20;
    
        int numPorcupineInHour21 = Math.min(numPorcupines, (minutesLeft21 - feedingPrepTime) / feedingTimePerPorcupine);
    
        if (numPorcupineInHour19 > 0) {
            int totalFeedingTimeInHour19 = numPorcupineInHour19 * feedingTimePerPorcupine + feedingPrepTime;
            db.insertFeedingPorcupinesToHashmap(19, totalFeedingTimeInHour19, numPorcupineInHour19, porcupineNicknames.subList(0, numPorcupineInHour19));
        }
        if (numPorcupineInHour20 > 0) {
            int totalFeedingTimeInHour20 = numPorcupineInHour20 * feedingTimePerPorcupine + feedingPrepTime;
            db.insertFeedingPorcupinesToHashmap(20, totalFeedingTimeInHour20, numPorcupineInHour20, porcupineNicknames.subList(numPorcupineInHour19, numPorcupineInHour19 + numPorcupineInHour20));
        }
        if (numPorcupineInHour21 > 0) {
            int totalFeedingTimeInHour21 = numPorcupineInHour21 * feedingTimePerPorcupine + feedingPrepTime;
            db.insertFeedingPorcupinesToHashmap(21, totalFeedingTimeInHour21, numPorcupineInHour21, porcupineNicknames.subList(numPorcupineInHour19 + numPorcupineInHour20, numPorcupineInHour19 + numPorcupineInHour20 + numPorcupineInHour21));
        }
        if (numPorcupines > 0 && minutesLeft19 < feedingPrepTime + numPorcupines * feedingTimePerPorcupine && minutesLeft20 < feedingPrepTime + numPorcupines * feedingTimePerPorcupine && minutesLeft21 < feedingPrepTime + numPorcupines * feedingTimePerPorcupine) {
            throw new InsufficientFeedingTimeException("There is not enough time to feed all of the porcupines in the designated times.");
        }        
    }

    public void feedingTimeBeaver(Schedule db, Animal animalInfo) throws IllegalArgumentException, InsufficientFeedingTimeException {
        if (db == null) {
            throw new IllegalArgumentException("Database object is empty.");
        }
        if (animalInfo == null) {
            throw new IllegalArgumentException("Animal object is empty.");
        }
        HashMap<Integer, Integer> minutesLeftmap = Schedule.getMinutesLeftMap(Schedule.getMainHashmapWithTasksandStartHours());
        int numBeavers = animalInfo.getNumBeavers();
        List<String> beaverNicknames = animalInfo.getBeaverNicknames();
        int feedingPrepTime = AnimalType.BEAVER.getFeedingPrepTime();
        int feedingTimePerBeaver = AnimalType.BEAVER.getFeedingTime();
    
        int minutesLeft8 = minutesLeftmap.getOrDefault(8, 0);
        int minutesLeft9 = minutesLeftmap.getOrDefault(9, 0);
        int minutesLeft10 = minutesLeftmap.getOrDefault(10, 0);
    
        int numBeaversInHour8 = Math.min(numBeavers, (minutesLeft8 - feedingPrepTime) / feedingTimePerBeaver);
        numBeavers -= numBeaversInHour8;
    
        int numBeaversInHour9 = Math.min(numBeavers, (minutesLeft9 - feedingPrepTime) / feedingTimePerBeaver);
        numBeavers -= numBeaversInHour9;
    
        int numBeaversInHour10 = Math.min(numBeavers, (minutesLeft10 - feedingPrepTime) / feedingTimePerBeaver);
    
        if (numBeaversInHour8 > 0) {
            int totalFeedingTimeInHour8 = numBeaversInHour8 * feedingTimePerBeaver + feedingPrepTime;
            db.insertFeedingBeaversToHashmap(8, totalFeedingTimeInHour8, numBeaversInHour8, beaverNicknames.subList(0, numBeaversInHour8));
        }
        if (numBeaversInHour9 > 0) {
            int totalFeedingTimeInHour9 = numBeaversInHour9 * feedingTimePerBeaver + feedingPrepTime;
            db.insertFeedingBeaversToHashmap(9, totalFeedingTimeInHour9, numBeaversInHour9, beaverNicknames.subList(numBeaversInHour8, numBeaversInHour8 + numBeaversInHour9));
        }
        if (numBeaversInHour10 > 0) {
            int totalFeedingTimeInHour10 = numBeaversInHour10 * feedingTimePerBeaver + feedingPrepTime;
            db.insertFeedingBeaversToHashmap(10, totalFeedingTimeInHour10, numBeaversInHour10, beaverNicknames.subList(numBeaversInHour8 + numBeaversInHour9, numBeaversInHour8 + numBeaversInHour9 + numBeaversInHour10));
        }
        if (numBeavers > 0 && minutesLeft8 < feedingPrepTime + numBeavers * feedingTimePerBeaver && minutesLeft9 < feedingPrepTime + numBeavers * feedingTimePerBeaver && minutesLeft10 < feedingPrepTime + numBeavers * feedingTimePerBeaver) {
            throw new InsufficientFeedingTimeException("There is not enough time to feed all of the beavers in the designated times.");
        }
    }
    
    
    public void feedingTimeRaccoon(Schedule db, Animal animalInfo) throws IllegalArgumentException, InsufficientFeedingTimeException {
        if (db == null) {
            throw new IllegalArgumentException("Database object is empty.");
        }
        if (animalInfo == null) {
            throw new IllegalArgumentException("Animal object is empty.");
        }
        HashMap<Integer, Integer> minutesLeftmap = Schedule.getMinutesLeftMap(Schedule.getMainHashmapWithTasksandStartHours());
        int numRaccoons = animalInfo.getNumRaccoons();
        List<String> raccoonNicknames = animalInfo.getRaccoonNicknames();
        int feedingPrepTime = AnimalType.RACCOON.getFeedingPrepTime();
        int feedingTimePerRaccoon = AnimalType.RACCOON.getFeedingTime();
            
        int minutesLeft0 = minutesLeftmap.getOrDefault(0, 0);
        int minutesLeft1 = minutesLeftmap.getOrDefault(1, 0);
        int minutesLeft2 = minutesLeftmap.getOrDefault(2, 0);
            
        int numRaccoonsInHour0 = Math.min(numRaccoons, (minutesLeft0 - feedingPrepTime) / feedingTimePerRaccoon);
        numRaccoons -= numRaccoonsInHour0;
            
        int numRaccoonsInHour1 = Math.min(numRaccoons, (minutesLeft1 - feedingPrepTime) / feedingTimePerRaccoon);
        numRaccoons -= numRaccoonsInHour1;
            
        int numRaccoonsInHour2 = Math.min(numRaccoons, (minutesLeft2 - feedingPrepTime) / feedingTimePerRaccoon);
            
        if (numRaccoonsInHour0 > 0) {
            int totalFeedingTimeInHour0 = numRaccoonsInHour0 * feedingTimePerRaccoon + feedingPrepTime;
            db.insertFeedingRaccoonsToHashmap(0, totalFeedingTimeInHour0, numRaccoonsInHour0, raccoonNicknames.subList(0, numRaccoonsInHour0));
         }
        if (numRaccoonsInHour1 > 0) {
            int totalFeedingTimeInHour1 = numRaccoonsInHour1 * feedingTimePerRaccoon + feedingPrepTime;
            db.insertFeedingRaccoonsToHashmap(1, totalFeedingTimeInHour1, numRaccoonsInHour1, raccoonNicknames.subList(numRaccoonsInHour0, numRaccoonsInHour0 + numRaccoonsInHour1));
        }
        if (numRaccoonsInHour2 > 0) {
            int totalFeedingTimeInHour2 = numRaccoonsInHour2 * feedingTimePerRaccoon + feedingPrepTime;
            db.insertFeedingRaccoonsToHashmap(2, totalFeedingTimeInHour2, numRaccoonsInHour2, raccoonNicknames.subList(numRaccoonsInHour0 + numRaccoonsInHour1, numRaccoonsInHour0 + numRaccoonsInHour1 + numRaccoonsInHour2));
         }
         if (numRaccoons > 0 && minutesLeft0 < feedingPrepTime + numRaccoons * feedingTimePerRaccoon && minutesLeft1 < feedingPrepTime + numRaccoons * feedingTimePerRaccoon && minutesLeft2 < feedingPrepTime + numRaccoons * feedingTimePerRaccoon) {
            throw new InsufficientFeedingTimeException("There is not enough time to feed all of the raccoons in the designated times.");
        }        
    }

    public static boolean checkDuration(Map<Integer, List<Map<String, String>>> mainHashMapWithTasksandStartHours2, int startHour) throws IllegalArgumentException {
        if (startHour < 0 || startHour > 23) {
            throw new IllegalArgumentException("Start hour must be between 0 and 23 inclusive.");
        }
        
        List<Map<String, String>> taskList = mainHashMapWithTasksandStartHours2.getOrDefault(startHour, new ArrayList<>());
        int totalDuration = 0;
        
        for (Map<String, String> task : taskList) {
            int duration;
            try {
                duration = Integer.parseInt(task.get("Duration"));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid duration for task: " + task.get("Name"));
            }
            if (duration < 0) {
                throw new IllegalArgumentException("Duration must be non-negative for task: " + task.get("Name"));
            }
            totalDuration += duration;
        }
        
        if (totalDuration > 60) {
            return true;
        } else {
            return false;
        }
    }      
}
    