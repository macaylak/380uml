package edu.ucalgary.oop;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class Task {
    private Connection dbConnect;
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
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/ewr", "root", "sqlpassword");
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

    public void extractTaskTable() {
        try {
            Statement myStmt = dbConnect.createStatement();
            ResultSet resultsTasks = myStmt.executeQuery("SELECT * FROM tasks");
            
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
   public void feedingTimeCoyote(Schedule db) {
        HashMap<Integer, Integer> minutesLeftmap = Schedule.getMinutesLeftMap(Schedule.getMainHashmapWithTasksandStartHours());

        int numCoyotes = Animal.getNumCoyotes();
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
            Schedule.insertFeedingCoyotesToHashmap(19, totalFeedingTimeInHour19, numCoyotesInHour19);
        }
        if (numCoyotesInHour20 > 0) {
            int totalFeedingTimeInHour20 = numCoyotesInHour20 * feedingTimePerCoyote + feedingPrepTime;
            Schedule.insertFeedingCoyotesToHashmap(20, totalFeedingTimeInHour20, numCoyotesInHour20);
        }
        if (numCoyotesInHour21 > 0) {
            int totalFeedingTimeInHour21 = numCoyotesInHour21 * feedingTimePerCoyote + feedingPrepTime;
            Schedule.insertFeedingCoyotesToHashmap(21, totalFeedingTimeInHour21, numCoyotesInHour21);
        }
    }

    public void feedingTimeFox(Schedule db) {
        HashMap<Integer, Integer> minutesLeftmap = Schedule.getMinutesLeftMap(Schedule.getMainHashmapWithTasksandStartHours());
        
        int numFoxes = Animal.getNumFoxes();
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
            Schedule.insertFeedingFoxToHashmap(0, totalFeedingTimeInHour0, numFoxesInHour0);
        }
        if (numFoxesInHour1 > 0) {
            int totalFeedingTimeInHour1 = numFoxesInHour1 * feedingTimePerFox + feedingPrepTime;
            Schedule.insertFeedingFoxToHashmap(1, totalFeedingTimeInHour1, numFoxesInHour1);
        }
        if (numFoxesInHour2 > 0) {
            int totalFeedingTimeInHour2 = numFoxesInHour2 * feedingTimePerFox + feedingPrepTime;
            Schedule.insertFeedingFoxToHashmap(2, totalFeedingTimeInHour2, numFoxesInHour2);
        }
    }

    public void feedingTimeBeaver(Schedule db){
        HashMap<Integer, Integer> minutesLeftmap = Schedule.getMinutesLeftMap(Schedule.getMainHashmapWithTasksandStartHours());

        int numBeavers = Animal.getNumBeavers();
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
            Schedule.insertFeedingBeaversToHashmap(8, totalFeedingTimeInHour8, numBeaversInHour8);
        }
        if (numBeaversInHour9 > 0) {
            int totalFeedingTimeInHour9 = numBeaversInHour9 * feedingTimePerBeaver + feedingPrepTime;
            Schedule.insertFeedingBeaversToHashmap(9, totalFeedingTimeInHour9, numBeaversInHour9);
        }
        if (numBeaversInHour10 > 0) {
            int totalFeedingTimeInHour10 = numBeaversInHour10 * feedingTimePerBeaver + feedingPrepTime;
            Schedule.insertFeedingBeaversToHashmap(10, totalFeedingTimeInHour10, numBeaversInHour10);
        }
    
    }

    public void feedingTimeRaccoon(Schedule db) {
        HashMap<Integer, Integer> minutesLeftmap = Schedule.getMinutesLeftMap(Schedule.getMainHashmapWithTasksandStartHours());
        
        int numRaccoons = Animal.getNumRaccoons();
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
            Schedule.insertFeedingRaccoonsToHashmap(0, totalFeedingTimeInHour0, numRaccoonsInHour0);
        }
        if (numRaccoonsInHour1 > 0) {
            int totalFeedingTimeInHour1 = numRaccoonsInHour1 * feedingTimePerRaccoon + feedingPrepTime;
            Schedule.insertFeedingRaccoonsToHashmap(1, totalFeedingTimeInHour1, numRaccoonsInHour1);
        }
        if (numRaccoonsInHour2 > 0) {
            int totalFeedingTimeInHour2 = numRaccoonsInHour2 * feedingTimePerRaccoon + feedingPrepTime;
            Schedule.insertFeedingRaccoonsToHashmap(2, totalFeedingTimeInHour2, numRaccoonsInHour2);
        }
    }    

    public void feedingTimePorcupine(Schedule db){
        HashMap<Integer, Integer> minutesLeftmap = db.getMinutesLeftMap(db.getMainHashmapWithTasksandStartHours());
        int numPorcupines = Animal.getNumPorcupines();
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
            Schedule.insertFeedingPorcupinesToHashmap(19, totalFeedingTimeInHour19, numPorcupineInHour19);
        }
        if (numPorcupineInHour20 > 0) {
            int totalFeedingTimeInHour20 = numPorcupineInHour20 * feedingTimePerPorcupine + feedingPrepTime;
            Schedule.insertFeedingPorcupinesToHashmap(20, totalFeedingTimeInHour20, numPorcupineInHour20);
        }
        if (numPorcupineInHour21 > 0) {
            int totalFeedingTimeInHour21 = numPorcupineInHour21 * feedingTimePerPorcupine + feedingPrepTime;
            Schedule.insertFeedingPorcupinesToHashmap(21, totalFeedingTimeInHour21, numPorcupineInHour21);
        }
    }

    public static boolean checkDuration(Map<Integer, List<Map<String, String>>> mainHashMapWithTasksandStartHours2, int startHour) {
        int totalDuration = 0;
    
        List<Map<String, String>> taskList = mainHashMapWithTasksandStartHours2.getOrDefault(startHour, new ArrayList<>());
    
        for (Map<String, String> task : taskList) {
            int duration = Integer.parseInt(task.get("Duration"));
            totalDuration += duration;
        }
    
        if (totalDuration > 60) {
            return true;
        } else {
            return false;
        }
    }    

    public void updateScheduleWithFeedingTimes(Schedule db) {
        feedingTimeCoyote(db);
        feedingTimeRaccoon(db);
        feedingTimeBeaver(db);
        feedingTimePorcupine(db);
        feedingTimeFox(db);
    }
    

    public static void main(String[] args) {
        Schedule db = new Schedule();
        Task Task = new Task();
    
        // Call feedingTimeFox() method and pass db as a parameter
        Task.updateScheduleWithFeedingTimes(db);
        Schedule.addCleaningTimesToHashmap(db);
    
        for (Integer startHour : Schedule.getMainHashmapWithTasksandStartHours().keySet()) {
            System.out.println();
            String startHourString = startHour + ":00";
            if (Schedule.checkDuration(Schedule.getMainHashmapWithTasksandStartHours(), startHour)) {
                startHourString += " [+ backup volunteer]";
            }
            System.out.println(startHourString);
            for (Map<String, String> task : Schedule.getMainHashmapWithTasksandStartHours().get(startHour)) {
                if (task.get("AnimalNickname") == null) {
                    System.out.println("* " + task.get("Description"));
                    continue;
                }else{
                    System.out.println("* " + task.get("Description") + " (" + task.get("AnimalNickname") + ")");
                }
            }
        }
    }   
}
    