package edu.ucalgary.oop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Schedule {

    private  Connection dbConnect;

    private static HashMap<Integer, Integer> minutesLeftMap;
    static HashMap<Integer, List<Map<String, String>>> mainHashmapWithTasksAndStartHours;
    private List<Integer> EmptyStartHours;

    public Schedule() {
        Task task = new Task();
        task.extractTaskTable();
        createConnection();
        createTaskMap();//have mainhashmap
    }

    public void close() {
        try {
            dbConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createConnection() {
        try {
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/ewr", "root", "sqlpassword");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTaskMap() {
        HashMap<Integer, List<Map<String, String>>> taskMap = new HashMap<>();
    
        try {
            Statement myStmt = dbConnect.createStatement();
            ResultSet results = myStmt.executeQuery("SELECT t.StartHour, ts.Description, ts.Duration, ts.MaxWindow, a.AnimalNickname " +
                    "FROM treatments t " +
                    "JOIN tasks ts ON t.TaskID = ts.TaskID " +
                    "JOIN animals a ON t.AnimalID = a.AnimalID");
    
            while (results.next()) {
                int startHour = results.getInt("StartHour");
                String description = results.getString("Description");
                int duration = results.getInt("Duration");
                int maxWindow = results.getInt("MaxWindow");
                String animalNickname = results.getString("AnimalNickname");
    
                Map<String, String> task = new HashMap<>();
                task.put("Description", description);
                task.put("AnimalNickname", animalNickname);
                task.put("Duration", Integer.toString(duration));
                task.put("MaxWindow", Integer.toString(maxWindow));
    
                List<Map<String, String>> taskList = taskMap.getOrDefault(startHour, new ArrayList<>());
                taskList.add(task);
                taskMap.put(startHour, taskList);
                
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Schedule.mainHashmapWithTasksAndStartHours = taskMap;
    }

    public  void insertFeedingCoyotesToHashmap(int StartHour, int feedingDuration, int NumAnimal) {
        int startHour = StartHour;
        int duration = feedingDuration;
        int numAnimal = NumAnimal;
        String description = "Feeding " + numAnimal + " coyotes";

        // Create a new task map for the feeding task
        Map<String, String> task = new HashMap<>();
        task.put("Description", description);
        task.put("Duration", Integer.toString(duration));

        // Get the task list for the specified start hour from the main hashmap
        List<Map<String, String>> taskList = mainHashmapWithTasksAndStartHours.getOrDefault(startHour, new ArrayList<>());

        // Add the new feeding task to the task list
        taskList.add(task);

        // Update the main hashmap with the modified task list
        mainHashmapWithTasksAndStartHours.put(startHour, taskList);

    }

    //only one task of feeding is given to insert into hashmap at a time
    public static void insertFeedingFoxToHashmap(int StartHour, int feedingDuration, int NumAnimal) {

        int startHour = StartHour;
        int duration = feedingDuration;
        int numAnimal = NumAnimal;
        String description = "Feeding " + numAnimal + " foxes";

        // Create a new task map for the feeding task
        Map<String, String> task = new HashMap<>();
        task.put("Description", description);
        task.put("Duration", Integer.toString(duration));

        // Get the task list for the specified start hour from the main hashmap
        List<Map<String, String>> taskList = mainHashmapWithTasksAndStartHours.getOrDefault(startHour, new ArrayList<>());

        // Add the new feeding task to the task list
        taskList.add(task);

        // Update the main hashmap with the modified task list
        mainHashmapWithTasksAndStartHours.put(startHour, taskList);
    }

    public static void insertFeedingBeaversToHashmap(int StartHour, int feedingDuration, int NumAnimal) {
        int startHour = StartHour;
        int duration = feedingDuration;
        int numAnimal = NumAnimal;
        String description = "Feeding " + numAnimal + " beavers";

        // Create a new task map for the feeding task
        Map<String, String> task = new HashMap<>();
        task.put("Description", description);
        task.put("Duration", Integer.toString(duration));

        // Get the task list for the specified start hour from the main hashmap
        List<Map<String, String>> taskList = mainHashmapWithTasksAndStartHours.getOrDefault(startHour, new ArrayList<>());

        // Add the new feeding task to the task list
        taskList.add(task);

        // Update the main hashmap with the modified task list
        mainHashmapWithTasksAndStartHours.put(startHour, taskList);
    }

    public static void insertFeedingRaccoonsToHashmap(int StartHour, int feedingDuration, int NumAnimal) {
        int startHour = StartHour;
        int duration = feedingDuration;
        int numAnimal = NumAnimal;
        String description = "Feeding " + numAnimal + " raccoons";

        // Create a new task map for the feeding task
        Map<String, String> task = new HashMap<>();
        task.put("Description", description);
        task.put("Duration", Integer.toString(duration));

        // Get the task list for the specified start hour from the main hashmap
        List<Map<String, String>> taskList = mainHashmapWithTasksAndStartHours.getOrDefault(startHour, new ArrayList<>());

        // Add the new feeding task to the task list
        taskList.add(task);

        // Update the main hashmap with the modified task list
        mainHashmapWithTasksAndStartHours.put(startHour, taskList);
    }

    public  void insertFeedingPorcupinesToHashmap(int StartHour, int feedingDuration, int NumAnimal) {
        int startHour = StartHour;
        int duration = feedingDuration;
        int numAnimal = NumAnimal;
        String description = "Feeding " + numAnimal + " porcupines";

        // Create a new task map for the feeding task
        Map<String, String> task = new HashMap<>();
        task.put("Description", description);
        task.put("Duration", Integer.toString(duration));

        // Get the task list for the specified start hour from the main hashmap
        List<Map<String, String>> taskList = mainHashmapWithTasksAndStartHours.getOrDefault(startHour, new ArrayList<>());

        // Add the new feeding task to the task list
        taskList.add(task);

        // Update the main hashmap with the modified task list
        mainHashmapWithTasksAndStartHours.put(startHour, taskList);

    }

    public static HashMap<Integer, Integer> getMinutesLeftMap(HashMap<Integer, List<Map<String, String>>> taskMap) {
        HashMap<Integer, Integer> minutesLeftMap = new HashMap<>();
        for (int i = 0; i < 24; i++) {
            if (taskMap.containsKey(i)) {
                int totalDuration = 0;
                List<Map<String, String>> taskList = taskMap.get(i);
                for (Map<String, String> task : taskList) {
                    totalDuration += Integer.parseInt(task.get("Duration"));
                }
                if (totalDuration < 60) {
                    int minutesLeft = 60 - totalDuration;
                    minutesLeftMap.put(i, minutesLeft);
                } else {
                    minutesLeftMap.put(i, 0);
                }
            } else {
                minutesLeftMap.put(i, 60);
            }
        }
        Schedule.minutesLeftMap = minutesLeftMap;
        return minutesLeftMap;
    }

    public static void addCleaningTimesToHashmap(Schedule db) {
        HashMap<Integer, Integer> minutesLeftmap = Schedule.getMinutesLeftMap(Schedule.getMainHashmapWithTasksandStartHours());
        Set<AnimalType> insertedAnimals = new HashSet<>();
    
        for (int startHour = 0; startHour < 24; startHour++) {
            int minutesLeft = minutesLeftmap.getOrDefault(startHour, 0);
    
            for (AnimalType animal : AnimalType.values()) {
                if (insertedAnimals.contains(animal)) {
                    continue; // skip animals that have already been inserted
                }
    
                int cleaningTime = animal.getCleaningTime();
                if (minutesLeft > 0 && minutesLeft >= cleaningTime) {
                    db.insertCleaningToHashMap(startHour, cleaningTime, animal);
                    insertedAnimals.add(animal);
                    break;
                }
            }
        }
    }
    
    public static HashMap<Integer, List<Map<String, String>>> getMainHashmapWithTasksandStartHours(){
        return mainHashmapWithTasksAndStartHours;
    }

    public void insertCleaningToHashMap(int startHour, int cleaningTime, AnimalType animalType) {
        String description = "Cleaning " + animalType.name().toLowerCase();
        Map<String, String> task = new HashMap<>();
        task.put("Description", description);
        task.put("Duration", Integer.toString(cleaningTime));
    
        List<Map<String, String>> taskList = mainHashmapWithTasksAndStartHours.getOrDefault(startHour, new ArrayList<>());
        taskList.add(task);
    
        mainHashmapWithTasksAndStartHours.put(startHour, taskList);
    }

    public static boolean checkDuration(HashMap<Integer, List<Map<String, String>>> taskMap, int startHour) {
        int totalDuration = 0;
    
        List<Map<String, String>> taskList = taskMap.getOrDefault(startHour, new ArrayList<>());
    
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
    

    // test insertFeedingCoyotesToHashmap
    // public static void main(String[] args) {
    //     Schedule db = new Schedule();
    //     // test insertFeedingCoyotesToHashmap
    //     Task task = new Task();
    //     task.feedingTimeCoyote(db);

    //     // print out the main hashmap
    //     System.out.println("mainHashmapWithTasksAndStartHours: " + mainHashmapWithTasksAndStartHours);

        

    // }


}
