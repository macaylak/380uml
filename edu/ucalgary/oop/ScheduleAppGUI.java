package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleAppGUI extends JFrame {
    private JTextArea scheduleArea;
    private JButton calculateButton;

    public ScheduleAppGUI() {
        super("EWR Schedule Builder");

        // Set up GUI components
        JTextArea dateLabel = new JTextArea("Today's Date: " + LocalDate.now().toString());
        JTextArea welcomeLabel = new JTextArea("Welcome to the EWR Schedule Builder!");
        JTextArea instructionLabel = new JTextArea ("***Please ensure that all medical tasks for the day have been entered into the EWR database before clicking the Calculate Schedule button.");


        dateLabel.setEditable(false);
        welcomeLabel.setEditable(false);
        instructionLabel.setEditable(false);

        // wrap text in instruction label to fit in window
        instructionLabel.setLineWrap(true);
        instructionLabel.setWrapStyleWord(true);

        // set font for labels
        dateLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // set font color for labels
        instructionLabel.setForeground(Color.decode("#D71881"));


        calculateButton = new JButton("Calculate Schedule");


        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(dateLabel);
        panel.add(welcomeLabel);
        panel.add(instructionLabel);
        panel.add(calculateButton);

        // Add panel to frame
        add(panel);

        // Set up frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);



        // Set up event listener for button click
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new Extracting_database object to extract data from the database
                Schedule db = new Schedule();
                Animal animalInfo = new Animal();
                db.createConnection();
                Task Task = new Task();

                // Call feedingTimeFox() method and pass db as a parameter
                Task.feedingTimeCoyote(db, animalInfo);
                Task.feedingTimePorcupine(db, animalInfo);
                Task.feedingTimeBeaver(db, animalInfo);
                Task.feedingTimeRaccoon(db, animalInfo);
                Task.feedingTimeFox(db, animalInfo);
                Schedule.addCleaningTimesToHashmap(db,animalInfo);

                // if an hour contains more than 120 minutes of tasks, display the error message
                // "It was impossible to complete the schedule due to too many events scheduled at (FullStartHour). Please shift some of the following activities: (TasksExceedingTimeLimit)"

                // make a list of the start hours that exceed 120 minutes
                List<Integer> startHoursExceedingTimeLimit = new ArrayList<>();
                for (Integer startHour : db.getMainHashmapWithTasksandStartHours().keySet()) {
                    if (Schedule.checkDuration(db.getMainHashmapWithTasksandStartHours(), startHour)) {
                        startHoursExceedingTimeLimit.add(startHour);
                    }
                }
                

                // Ask user for confirmation before showing schedule
                // The program should not exit without generating a schedule and getting confirmation for all backup volunteers
                // a list conatinig the start hours that need backup volunteers in the format of "startHour:00, startHour:00, ..."
                List<String> backupVolunteerHours = new ArrayList<>();
                for (Integer startHour : db.getMainHashmapWithTasksandStartHours().keySet()) {
                    if (Schedule.checkDuration(db.getMainHashmapWithTasksandStartHours(), startHour)) {
                        backupVolunteerHours.add(startHour + ":00");
                    }
                }
                
                // if no backup volunteers are needed, show the schedule
                if (backupVolunteerHours == null || backupVolunteerHours.isEmpty()) {
                    // Calculate schedule based on tasks
                    StringBuilder schedule = new StringBuilder();
                    schedule.append("Schedule for " + LocalDate.now().toString() + "\n");
                    for (Integer startHour : db.getMainHashmapWithTasksandStartHours().keySet()) {
                        String startHourString = startHour + ":00";
                        if (Schedule.checkDuration(db.getMainHashmapWithTasksandStartHours(), startHour)) {
                            startHourString += " [+ backup volunteer]";
                        }
                        schedule.append("\n" + startHourString + "\n");
                        for (Map<String, String> task : db.getMainHashmapWithTasksandStartHours().get(startHour)) {
                            if (task.get("AnimalNickname") == null) {
                                schedule.append("* " + task.get("Description") + "\n");
                            } else {
                                schedule.append("* " + task.get("Description") + " (" + task.get("AnimalNickname") + ")" + "\n");
                            }
                        }
                    }
                    scheduleArea = new JTextArea(schedule.toString());
                    scheduleArea.setEditable(false);
                    scheduleArea.setLineWrap(true);
                    scheduleArea.setWrapStyleWord(true);
                    scheduleArea.setFont(new Font("Arial", Font.BOLD, 14));
                    scheduleArea.setForeground(Color.decode("#D71881"));
                    panel.add(scheduleArea);
                    pack();

                } else {
                    // if backup volunteers are needed, ask for confirmation
                    String message = "Please confirm backup volunteers are available for the following times:\n"
                            + backupVolunteerHours.toString()
                                .replace(" ", "")
                                .replace("[", "")
                                .replace("]", "")
                                .replace(",", "\n");
                    int choice = JOptionPane.showConfirmDialog(null, message, "Confirm Schedule Viewing", JOptionPane.YES_NO_OPTION);
                    if (choice == JOptionPane.YES_OPTION) {
                        // Calculate schedule based on tasks
                        StringBuilder schedule = new StringBuilder();
                        schedule.append("Schedule for " + LocalDate.now().toString() + "\n");
                        for (Integer startHour : db.getMainHashmapWithTasksandStartHours().keySet()) {
                            String startHourString = startHour + ":00";
                            if (Schedule.checkDuration(db.getMainHashmapWithTasksandStartHours(), startHour)) {
                                startHourString += " [+ backup volunteer]";
                            }
                            schedule.append("\n" + startHourString + "\n");
                            for (Map<String, String> task : db.getMainHashmapWithTasksandStartHours().get(startHour)) {
                                if (task.get("AnimalNickname") == null) {
                                    schedule.append("* " + task.get("Description") + "\n");
                                } else {
                                    schedule.append("* " + task.get("Description") + " (" + task.get("AnimalNickname") + ")" + "\n");
                                }
                            }
                        }

                    // Display schedule in a new window
                    JFrame scheduleFrame = new JFrame("Schedule");
                    scheduleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    scheduleFrame.setSize(500, 500);
                    scheduleFrame.setLocationRelativeTo(null);
                    scheduleFrame.setVisible(true);

                    JTextArea scheduleArea = new JTextArea(schedule.toString());
                    scheduleArea.setEditable(false);
                    scheduleFrame.add(scheduleArea);
                    JScrollPane scrollPane = new JScrollPane(scheduleArea);
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                    scheduleFrame.add(scrollPane);
                    // exit program after closing schedule window and thank user for using the program
                    scheduleFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            JOptionPane.showMessageDialog(null, "Thank you for using the EWR Schedule builder!");
                            System.exit(0);
                        }
                    });
                    } else
                    if (choice == JOptionPane.NO_OPTION) {


                        // A dictionary that holds the hour that requires a backup volunteer as a key and the tasks in that hour as a value
                        Map<Integer, List<String>> tasksExceedingTimeLimit = new HashMap<>();
                        for (Integer startHour : db.getMainHashmapWithTasksandStartHours().keySet()) {
                            if (Schedule.checkDuration(db.getMainHashmapWithTasksandStartHours(), startHour)) {
                                List<String> tasks = new ArrayList<>();
                                for (Map<String, String> task : db.getMainHashmapWithTasksandStartHours().get(startHour)) {
                                    if (task.get("AnimalNickname") == null) {
                                        tasks.add(task.get("Description"));
                                    } else {
                                        tasks.add(task.get("Description") + " (" + task.get("AnimalNickname") + ")");
                                    }
                                }
                                tasksExceedingTimeLimit.put(startHour, tasks);
                            }
                        }

                        // convert to string
                        String tasksExceedingTimeLimitString = "";
                        for (Integer startHour : tasksExceedingTimeLimit.keySet()) {
                            tasksExceedingTimeLimitString += "\n" + startHour + ":00\n";
                            for (String task : tasksExceedingTimeLimit.get(startHour)) {
                                tasksExceedingTimeLimitString += "* " + task + "\n";
                            }
                        }



                        JOptionPane.showMessageDialog(null, "It was impossible to complete the schedule due to too many events scheduled at " + backupVolunteerHours.toString()
                        .replace(" ", "")
                        .replace("[", "")
                        .replace("]", "")
                        .replace(",", " & ") + 
                        ".Please shift some of the following activities and try again:\n" +
                        tasksExceedingTimeLimitString);
                    }
                }
            }
        });

        // Add panel to frame and set frame properties
        add(panel);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        // allow window size to be changed
        setResizable(true);
    }



    public static void main(String[] args) {
        new ScheduleAppGUI();
    }

    
}
