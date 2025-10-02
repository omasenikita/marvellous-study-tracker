# 📚 Marvellous Study Tracker App

A console-based Java application designed to help students systematically **log**, **track**, **summarize**, and **export** their study activities.

---

## 🛠️ Technology Used

- **Language:** Java  
- **Packages & APIs:**
  - `java.util.*` – Data structures (ArrayList, TreeMap), user input via Scanner
  - `java.time.LocalDate` – Auto-captures current date for study logs
  - `java.io.*` – File handling and CSV export

---

## 📌 Project Overview

The Marvellous Study Tracker App allows users to:
- Maintain daily study records
- View summaries grouped by **date** or **subject**
- Export all logs into a **CSV file** for offline reference

This project demonstrates practical usage of:
- Java Collections
- File I/O
- Object-Oriented Design  
in a real-world, utility-driven application.

---

## ✨ Key Features

- **Insert Study Log**  
  Record study sessions with auto-generated date, subject, duration, and description.

- **Display Logs**  
  View all study logs currently stored in memory.

- **Summary by Date**  
  Calculate and display total study hours grouped by date.

- **Summary by Subject**  
  Calculate and display total study hours grouped by subject.

- **Export to CSV**  
  Export all study logs into `MarvellousStudy.csv` for offline tracking.

- **User-Friendly Console Menu**  
  Menu-driven interface with switch-case navigation for ease of use.

---

## 🧭 Project Flow

1. Launch the application → Main Menu displayed  
2. Choice 1: Insert new → User provides subject, duration, description → Date auto-captured  
3. Choice 2: Display all study logs stored in memory  
4. Choice 3: Display summary grouped by subject (total hours per day)  
5. Choice 4: Display summary grouped by date (total hours per subject)  
6. Choice 5: Export all study logs to `MarvellousStudy.csv`  
7. Choice 6: Exit application

---

## 🧱 Class Structure

### `StudyLog`
Represents a single study session  
**Attributes:**
- `LocalDate date`
- `String subject`
- `double duration`
- `String description`  
**Methods:**
- Constructors
- Getters
- `toString()`

---

### `StudyTracker`
Manages all logs in memory  
**Attributes:**
- `ArrayList<StudyLog> database`  
**Methods:**
- `InsertLog()`
- `DisplayLog()`
- `SummaryByDate()`
- `SummaryBySubject()`
- `ExportCSV()`

---

### `StudyTrackerApp` (Main Class)
- Contains `main()` method  
- Handles menu-driven interface and user input  
- Calls appropriate methods from `StudyTracker`

---

**Sample Input:**
- Subject: Java Programming  
- Duration (hours): 2.5  
- Description: Practiced ArrayList and TreeMap  
- Date: 2025-09-13
