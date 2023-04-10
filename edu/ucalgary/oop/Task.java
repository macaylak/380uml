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
    
    public static void feedingTimeCoyote(Schedule db) {
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
            db.insertFeedingCoyotesToHashmap(19, totalFeedingTimeInHour19, numCoyotesInHour19);
        }
        if (numCoyotesInHour20 > 0) {
            int totalFeedingTimeInHour20 = numCoyotesInHour20 * feedingTimePerCoyote + feedingPrepTime;
            db.insertFeedingCoyotesToHashmap(20, totalFeedingTimeInHour20, numCoyotesInHour20);
        }
        if (numCoyotesInHour21 > 0) {
            int totalFeedingTimeInHour21 = numCoyotesInHour21 * feedingTimePerCoyote + feedingPrepTime;
            db.insertFeedingCoyotesToHashmap(21, totalFeedingTimeInHour21, numCoyotesInHour21);
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

    // public void updateScheduleWithFeedingTimes(Schedule db) {
    //     feedingTimeCoyote(db);
    //     // feedingTimeRaccoon(db);
    //     // feedingTimeBeaver(db);
    //     // feedingTimePorcupine(db);
    //     // feedingTimeFox(db);
    // }
    

    public static void main(String[] args) {
        Schedule db = new Schedule();
        Task t = new Task();
    
        // Call feedingTimeFox() method and pass db as a parameter
        Task.feedingTimeCoyote(db);
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
    