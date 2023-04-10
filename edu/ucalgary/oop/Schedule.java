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
    
    static HashMap<Integer, List<Map<String, String>>> mainHashmapWithTasksAndStartHours;

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
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/ewr", "root", "ensf380root");
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
    public void insertFeedingCoyotesToHashmap(int startHour, int feedingDuration, int numAnimal, List<String> coyoteNicknames) {
        if (coyoteNicknames == null) {
            throw new NullPointerException("coyoteNicknames cannot be null");
        }
    
        if (startHour < 0) {
            throw new IllegalArgumentException("startHour cannot be negative");
        }
    
        if (feedingDuration <= 0) {
            throw new IllegalArgumentException("feedingDuration must be greater than zero");
        }
    
        try {
            String coyoteNames = String.join(", ", coyoteNicknames);
            String description = "Feeding - Coyote (" + numAnimal + ": " + coyoteNames + ")";
        
            // Create a new task map for the feeding task
            Map<String, String> task = new HashMap<>();
            task.put("Description", description);
            task.put("Duration", Integer.toString(feedingDuration));
        
            // Get the task list for the specified start hour from the main hashmap
            List<Map<String, String>> taskList = mainHashmapWithTasksAndStartHours.getOrDefault(startHour, new ArrayList<>());
        
            // Add the new feeding task to the task list
            taskList.add(task);
        
            // Update the main hashmap with the modified task list
            mainHashmapWithTasksAndStartHours.put(startHour, taskList);
        } catch (Exception e) {
            throw new RuntimeException("Error while inserting feeding coyotes task into hashmap", e);
        }
    }
    
    
    public void insertFeedingFoxToHashmap(int startHour, int feedingDuration, int numAnimal, List<String> foxNicknames) throws IllegalArgumentException {
        if(startHour < 0 || startHour > 23) {
            throw new IllegalArgumentException("Invalid start hour: " + startHour);
        }
        if(feedingDuration <= 0) {
            throw new IllegalArgumentException("Feeding duration must be positive: " + feedingDuration);
        }
        if(numAnimal <= 0) {
            throw new IllegalArgumentException("Number of animals must be positive: " + numAnimal);
        }
        if(foxNicknames == null || foxNicknames.isEmpty()) {
            throw new IllegalArgumentException("List of fox nicknames cannot be null or empty");
        }
        
        String foxNames = String.join(", ", foxNicknames);
        String description = "Feeding - Fox (" + numAnimal + ": " + foxNames + ")";
        
        // Create a new task map for the feeding task
        Map<String, String> task = new HashMap<>();
        task.put("Description", description);
        task.put("Duration", Integer.toString(feedingDuration));
        
        // Get the task list for the specified start hour from the main hashmap
        List<Map<String, String>> taskList = mainHashmapWithTasksAndStartHours.getOrDefault(startHour, new ArrayList<>());
        
        // Add the new feeding task to the task list
        taskList.add(task);
        
        // Update the main hashmap with the modified task list
        mainHashmapWithTasksAndStartHours.put(startHour, taskList);
    }
    public void insertFeedingBeaversToHashmap(int startHour, int feedingDuration, int numAnimal, List<String> beaverNicknames) throws IllegalArgumentException {
        if(startHour < 0 || startHour > 23) {
            throw new IllegalArgumentException("Start hour must be between 0 and 23 inclusive.");
        }
        if(feedingDuration < 1 || feedingDuration > 24) {
            throw new IllegalArgumentException("Feeding duration must be between 1 and 24 hours inclusive.");
        }
        if(numAnimal < 1) {
            throw new IllegalArgumentException("Number of animals must be greater than 0.");
        }
        if(beaverNicknames == null || beaverNicknames.isEmpty()) {
            throw new IllegalArgumentException("Beaver nicknames cannot be null or empty.");
        }
        
        String beaverNames = String.join(", ", beaverNicknames);
        String description = "Feeding - Beaver (" + numAnimal + ": " + beaverNames + ")";
        
        // Create a new task map for the feeding task
        Map<String, String> task = new HashMap<>();
        task.put("Description", description);
        task.put("Duration", Integer.toString(feedingDuration));
        
        // Get the task list for the specified start hour from the main hashmap
        List<Map<String, String>> taskList = mainHashmapWithTasksAndStartHours.getOrDefault(startHour, new ArrayList<>());
        
        // Add the new feeding task to the task list
        taskList.add(task);
        
        // Update the main hashmap with the modified task list
        mainHashmapWithTasksAndStartHours.put(startHour, taskList);
    }
    public void insertFeedingPorcupinesToHashmap(int startHour, int feedingDuration, int numAnimal, List<String> porcupineNicknames) throws IllegalArgumentException {
        if (startHour < 0 || startHour > 23) {
            throw new IllegalArgumentException("Start hour must be between 0 and 23 (inclusive).");
        }
        if (feedingDuration <= 0) {
            throw new IllegalArgumentException("Feeding duration must be greater than 0.");
        }
        if (numAnimal <= 0) {
            throw new IllegalArgumentException("Number of animals must be greater than 0.");
        }
        if (porcupineNicknames.isEmpty()) {
            throw new IllegalArgumentException("List of porcupine nicknames cannot be empty.");
        }
    
        String porcupineNames = String.join(", ", porcupineNicknames);
        String description = "Feeding - Porcupine (" + numAnimal + ": " + porcupineNames + ")";
        
        // Create a new task map for the feeding task
        Map<String, String> task = new HashMap<>();
        task.put("Description", description);
        task.put("Duration", Integer.toString(feedingDuration));
        
        // Get the task list for the specified start hour from the main hashmap
        List<Map<String, String>> taskList = mainHashmapWithTasksAndStartHours.getOrDefault(startHour, new ArrayList<>());
        
        // Add the new feeding task to the task list
        taskList.add(task);
        
        // Update the main hashmap with the modified task list
        mainHashmapWithTasksAndStartHours.put(startHour, taskList);
    }
    public void insertFeedingRaccoonsToHashmap(int startHour, int feedingDuration, int numAnimal, List<String> raccoonNames) throws IllegalArgumentException {
        if (startHour < 0 || startHour > 23) {
            throw new IllegalArgumentException("Invalid start hour. Must be between 0 and 23.");
        }
        if (feedingDuration <= 0) {
            throw new IllegalArgumentException("Invalid feeding duration. Must be greater than 0.");
        }
        if (numAnimal <= 0) {
            throw new IllegalArgumentException("Invalid number of animals. Must be greater than 0.");
        }
        if (raccoonNames == null || raccoonNames.isEmpty()) {
            throw new IllegalArgumentException("Invalid raccoon names. List cannot be null or empty.");
        }
        
        String names = String.join(", ", raccoonNames);
        String description = "Feeding - Raccoon (" + numAnimal + ": " + names + ")";
        
        // Create a new task map for the feeding task
        Map<String, String> task = new HashMap<>();
        task.put("Description", description);
        task.put("Duration", Integer.toString(feedingDuration));
        
        // Get the task list for the specified start hour from the main hashmap
        List<Map<String, String>> taskList = mainHashmapWithTasksAndStartHours.getOrDefault(startHour, new ArrayList<>());
        
        // Add the new feeding task to the task list
        taskList.add(task);
        
        // Update the main hashmap with the modified task list
        mainHashmapWithTasksAndStartHours.put(startHour, taskList);
    }
    

    public static void addCleaningTimesToHashmap(Schedule db, Animal animalInfo) throws IllegalArgumentException, NullPointerException {
        HashMap<Integer, Integer> minutesLeftmap = getMinutesLeftMap(Schedule.getMainHashmapWithTasksandStartHours());
        Set<AnimalType> insertedAnimals = new HashSet<>();
        List<String> coyoteNicknames = animalInfo.getCoyoteNicknames();
        List<String> porcupineNicknames = animalInfo.getPorcupineNicknames();


        int cleaningTimeCoyote = AnimalType.COYOTE.getCleaningTime();
        AnimalType animalTypeCoyote = AnimalType.COYOTE;
        int startHour = 0;
        AnimalType animalTypePorcupine = AnimalType.PORCUPINE;
        int cleaningTimePorcupine = AnimalType.PORCUPINE.getCleaningTime();
        for (String nickname : coyoteNicknames) {
            while (startHour < 24) {
                int minutesLeft = minutesLeftmap.getOrDefault(startHour, 60);
                if (minutesLeft >= cleaningTimeCoyote) {
                    insertCleaningToHashMap(startHour, cleaningTimeCoyote, animalTypeCoyote, nickname);
                    minutesLeftmap.put(startHour, minutesLeft - cleaningTimeCoyote);
                    insertedAnimals.add(animalTypeCoyote);
                    break;
                } else {
                    startHour++;
                }
            }
            if (insertedAnimals.size() == AnimalType.values().length) {
                break;
            }
        }
        for (String nickname : porcupineNicknames) {
            while (startHour < 24) {
                int minutesLeft = minutesLeftmap.getOrDefault(startHour, 60);
                if (minutesLeft >= cleaningTimePorcupine) {
                    insertCleaningToHashMap(startHour, cleaningTimePorcupine, animalTypePorcupine, nickname);
                    minutesLeftmap.put(startHour, minutesLeft - cleaningTimePorcupine);
                    insertedAnimals.add(animalTypePorcupine);
                    break;
                } else {
                    startHour++;
                }
            }
            if (insertedAnimals.size() == AnimalType.values().length) {
                break;
            }
        }

        //fox cleaning 
        List<String> foxNicknames = animalInfo.getFoxNicknames();
        int cleaningTimeFox = AnimalType.FOX.getCleaningTime();

        AnimalType animalTypeFox = AnimalType.FOX;
        for (String nickname : foxNicknames) {
            while (startHour < 24) {
                int minutesLeft = minutesLeftmap.getOrDefault(startHour, 60);
                if (minutesLeft >= cleaningTimeFox) {
                    insertCleaningToHashMap(startHour, cleaningTimeFox, animalTypeFox, nickname);
                    minutesLeftmap.put(startHour, minutesLeft - cleaningTimeFox);
                    insertedAnimals.add(animalTypeFox);
                    break;
                } else {
                    startHour++;
                }
            }
            if (insertedAnimals.size() == AnimalType.values().length) {
                break;
            }
        }

        //raccoon cleaning
        List<String> raccoonNicknames = animalInfo.getRaccoonNicknames();
        int cleaningTimeRaccoon = AnimalType.RACCOON.getCleaningTime();

        AnimalType animalTypeRaccoon = AnimalType.RACCOON;
        for (String nickname : raccoonNicknames) {
            while (startHour < 24) {
                int minutesLeft = minutesLeftmap.getOrDefault(startHour, 60);
                if (minutesLeft >= cleaningTimeRaccoon) {
                    insertCleaningToHashMap(startHour, cleaningTimeRaccoon, animalTypeRaccoon, nickname);
                    minutesLeftmap.put(startHour, minutesLeft - cleaningTimeRaccoon);
                    insertedAnimals.add(animalTypeRaccoon);
                    break;
                } else {
                    startHour++;
                }
            }
            if (insertedAnimals.size() == AnimalType.values().length) {
                break;
            }
        }
        List<String> Beaversicknames = animalInfo.getBeaverNicknames();
        int cleaningTimeBeavers= AnimalType.BEAVER.getCleaningTime();

        AnimalType animalTypeBeaver = AnimalType.BEAVER;
        for (String nickname : Beaversicknames) {
            while (startHour < 24) {
                int minutesLeft = minutesLeftmap.getOrDefault(startHour, 60);
                if (minutesLeft >= cleaningTimeBeavers) {
                    insertCleaningToHashMap(startHour, cleaningTimeBeavers, animalTypeBeaver, nickname);
                    minutesLeftmap.put(startHour, minutesLeft - cleaningTimeBeavers);
                    insertedAnimals.add(animalTypeBeaver);
                    break;
                } else {
                    startHour++;
                }
            }
            if (insertedAnimals.size() == AnimalType.values().length) {
                break;
            }
        }
      
    }
    
    public static HashMap<Integer, List<Map<String, String>>> getMainHashmapWithTasksandStartHours(){
        return mainHashmapWithTasksAndStartHours;
    }

    public static void insertCleaningToHashMap(int startHour, int cleaningTime, AnimalType animalType, String nickname) throws IllegalArgumentException {
        if (startHour < 0 || startHour > 23) {
            throw new IllegalArgumentException("Start hour must be between 0 and 23");
        }
        if (cleaningTime < 0) {
            throw new IllegalArgumentException("Cleaning time must be a positive integer");
        }
        if (animalType == null) {
            throw new IllegalArgumentException("Animal type cannot be null");
        }
        if (nickname == null || nickname.isEmpty()) {
            throw new IllegalArgumentException("Nickname cannot be null or empty");
        }
        
        String description = "Cage Cleaning for " + animalType.name().toLowerCase() + " " + nickname;
        Map<String, String> task = new HashMap<>();
        task.put("Description", description);
        task.put("Duration", Integer.toString(cleaningTime));
    
        List<Map<String, String>> taskList = mainHashmapWithTasksAndStartHours.getOrDefault(startHour, new ArrayList<>());
        taskList.add(task);
    
        mainHashmapWithTasksAndStartHours.put(startHour, taskList);
    }
    
    public static boolean checkDuration(HashMap<Integer, List<Map<String, String>>> taskMap, int startHour) throws IllegalArgumentException {
        int totalDuration = 0;
    
        List<Map<String, String>> taskList = taskMap.getOrDefault(startHour, new ArrayList<>());
    
        for (Map<String, String> task : taskList) {
            String durationStr = task.get("Duration");
            if (durationStr == null) {
                throw new IllegalArgumentException("Duration missing for task at start hour " + startHour);
            }
            int duration;
            try {
                duration = Integer.parseInt(durationStr);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid duration for task at start hour " + startHour + ": " + durationStr, e);
            }
            totalDuration += duration;
        }
    
        if (totalDuration > 60) {
            return true;
        } else {
            return false;
        }
    }
    
    public static HashMap<Integer, Integer> getMinutesLeftMap(HashMap<Integer, List<Map<String, String>>> taskMap) throws NullPointerException, NumberFormatException {
        HashMap<Integer, Integer> minutesLeftMap = new HashMap<>();
        for (int i = 0; i < 24; i++) {
            if (taskMap.containsKey(i)) {
                int totalDuration = 0;
                List<Map<String, String>> taskList = taskMap.get(i);
                for (Map<String, String> task : taskList) {
                    int duration = Integer.parseInt(task.get("Duration"));
                    totalDuration += duration;
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
        return minutesLeftMap;
    }
    

    
}
