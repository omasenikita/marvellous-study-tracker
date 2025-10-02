/*
 * Author    : Nikita omase
 * File Name : StudyTrackerApp.java
 * Date      : August 17, 2025
 * Description:
 * This program implements a GUI-based Study Tracker application using Java AWT
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class StudyTrackerApp extends Frame
{
    // ------------ Inner Class: StudyLog -------------
    static class StudyLog 
    {
        // Fields for study log
        private LocalDate date;
        private String subject;
        private double duration;
        private String description;

        public StudyLog(LocalDate date, String subject, double duration, String description) 
        {

            this.date = date;
            this.subject = subject;
            this.duration = duration;
            this.description = description;
        }
        // Getters for the fields
        public LocalDate getDate() { return date; }
        public String getSubject() { return subject; }
        public double getDuration() { return duration; }
        public String getDescription() { return description; }

        // Override toString for easy display
        public String toString()
        {
            return date + " | " + subject + " | " + duration + " hours | " + description;
        }
    }

    // ------------ Inner Class: StudyTracker -------------

    static class StudyTracker 
    {
        // List to hold study logs
        private ArrayList<StudyLog> logs = new ArrayList<>();

        // Methods to manage study logs
        public void addLog(LocalDate date, String subject, double duration, String description)
         {
            logs.add(new StudyLog(date, subject, duration, description));
        }
         
        // Method to get all logs as a formatted string
        public String getLogsAsString()
         {
            if (logs.isEmpty()) return "No logs available.";
            StringBuilder sb = new StringBuilder();
            for (StudyLog log : logs) {
                sb.append(log).append("\n");
            }
            return sb.toString();
        }
        // Method to export logs to a CSV file
        public void exportCSV()
         {
            if (logs.isEmpty()) return;
            try (FileWriter fw = new FileWriter("MarvellousStudy.csv"))
             {
                fw.write("Date,Subject,Duration,Description\n");
                for (StudyLog log : logs) 
                {
                    fw.write(log.getDate() + "," +
                            log.getSubject().replace(",", " ") + "," +
                            log.getDuration() + "," +
                            log.getDescription().replace(",", " ") + "\n");
                }
            } catch (IOException e) 
            {
                System.err.println("Error writing to CSV file: " + e.getMessage());  
            }
        }
        // Methods to summarize logs by date and subject
        public String getSummaryByDate()
         {
            if (logs.isEmpty()) return "No logs available.";

            // Using TreeMap to sort dates
            TreeMap<LocalDate, Double> map = new TreeMap<>();
            for (StudyLog log : logs)
             {
                map.put(log.getDate(), map.getOrDefault(log.getDate(), 0.0) + log.getDuration());
            }

            StringBuilder sb = new StringBuilder();

            // Iterate through the map to create a summary
            for (Map.Entry<LocalDate, Double> entry : map.entrySet())
             {
                sb.append("Date: ").append(entry.getKey())
                  .append(" | Total Study: ").append(entry.getValue()).append(" hrs\n");
            }
            return sb.toString();
        }
         // Method to summarize logs by subject
        public String getSummaryBySubject()
         {
            if (logs.isEmpty()) return "No logs available.";

            // Using TreeMap to sort subjects alphabetically
            TreeMap<String, Double> map = new TreeMap<>();
            for (StudyLog log : logs)
            {
                map.put(log.getSubject(), map.getOrDefault(log.getSubject(), 0.0) + log.getDuration());
            }

            StringBuilder sb = new StringBuilder();

            // Iterate through the map to create a summary
            for (Map.Entry<String, Double> entry : map.entrySet())
            {
                sb.append("Subject: ").append(entry.getKey())
                  .append(" | Total Study: ").append(entry.getValue()).append(" hrs\n");
            }
            return sb.toString();
        }
    }

    // GUI Fields
    private StudyTracker tracker = new StudyTracker();
    private TextField subjectField, durationField;
    private TextArea descriptionArea, displayArea;

    public StudyTrackerApp() 
    {

        // Frame setup
        setTitle("Marvellous Study Tracker");
        setSize(800, 700);
        setLayout(new BorderLayout(10, 10));
        setTitle("Marvellous Study Tracker");
        setSize(800, 700);
        setLayout(new BorderLayout(10, 10));

        // Top input panel
        Panel inputPanel = new Panel(new GridLayout(4, 2, 5, 5));
        inputPanel.add(new Label("Subject:"));
        subjectField = new TextField();
        inputPanel.add(subjectField);

        // Duration input
        inputPanel.add(new Label("Duration (in hours):"));
        durationField = new TextField();
        inputPanel.add(durationField);

        // Description input
        inputPanel.add(new Label("Description:"));
        descriptionArea = new TextArea(3, 20);
        inputPanel.add(descriptionArea);

        add(inputPanel, BorderLayout.NORTH);

        // Center display area
        displayArea = new TextArea("", 25, 80, TextArea.SCROLLBARS_VERTICAL_ONLY);
        displayArea.setEditable(false);
        add(displayArea, BorderLayout.CENTER);

        // Bottom button panel
        Panel buttonPanel = new Panel(new GridLayout(2, 3, 10, 10));
        Button insertBtn = new Button("Insert Log");
        Button viewBtn = new Button("View All Logs");
        Button exportBtn = new Button("Export to CSV");
        Button dateSummaryBtn = new Button("Summary by Date");
        Button subjectSummaryBtn = new Button("Summary by Subject");
        Button exitBtn = new Button("Exit");

        // Add buttons to the panel
        buttonPanel.add(insertBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(exportBtn);
        buttonPanel.add(dateSummaryBtn);
        buttonPanel.add(subjectSummaryBtn);
        buttonPanel.add(exitBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        insertBtn.addActionListener(e -> insertLog());
        viewBtn.addActionListener(e -> displayLogs());
        exportBtn.addActionListener(e -> exportLogs());
        dateSummaryBtn.addActionListener(e -> summaryByDate());
        subjectSummaryBtn.addActionListener(e -> summaryBySubject());
        exitBtn.addActionListener(e -> System.exit(0));

        // Window Close Listener
        addWindowListener(new WindowAdapter() 
        {
            public void windowClosing(WindowEvent we) 
            {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    // Action Handlers
    private void insertLog() 
    {
        // Validate input and add log
        if (subjectField.getText().isEmpty() || durationField.getText().isEmpty() || descriptionArea.getText().isEmpty()) 
        {
            displayArea.setText("Please fill all fields.\n");
            return;
        }
        try {
            String subject = subjectField.getText().trim();
            double duration = Double.parseDouble(durationField.getText().trim());
            String description = descriptionArea.getText().trim();

            if (subject.isEmpty() || description.isEmpty()) 
            {
                displayArea.setText("⚠ Please fill all fields.\n");
                return;
            }

            // Add the log to the tracker
            tracker.addLog(LocalDate.now(), subject, duration, description);
            displayArea.setText("✅ Study log added successfully.");
            subjectField.setText("");
            durationField.setText("");
            descriptionArea.setText("");
        } catch (NumberFormatException ex)
        {
            displayArea.setText(" Invalid duration. Please enter a valid number.");
        }
    }

    // Method to display all logs
    private void displayLogs()
    {
        displayArea.setText(tracker.getLogsAsString());
    }

    // Method to export logs to CSV
    private void exportLogs()
   {
        tracker.exportCSV();

        displayArea.setText("📁 Study logs exported to MarvellousStudy.csv");
    }
    

    // Methods to summarize logs by date and subject
    private void summaryByDate()
    {
        displayArea.setText(tracker.getSummaryByDate());
    }
     
    // Method to summarize logs by subject
    private void summaryBySubject() 
    {
        displayArea.setText(tracker.getSummaryBySubject());
    }


   // Main method to run the application
    public static void main(String[] args) 
    {
        new StudyTrackerApp();
    }
}

