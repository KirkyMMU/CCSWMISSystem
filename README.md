# CCSWMISSystem
A simplified Management Information System (MIS) built in Java designed for use by an education provider such as *Cheshire College - South & West*. The system demonstrates core MIS functionality including student, staff and course management alongside reporting and persistence features.

---

## Features

- **Student Management**
    - Add, list, remove students
    - Record GCSE style grades (1-9)
    - Assign students to courses
    - Automatically generate and persist attendance percentages

- **Staff Management**
    - Add, list, remove staff members
    - Assign tasks with realistic deadlines

- **Course Management**
    - Add, list, remove courses
    - Enrol students onto courses
    - Search courses by code
    - List students enrolled in a course

- **Reports**
    - **Grades Report**: Summarises student grades and averages
    - **Attendance Report**: Displays pers-student attendance, course-level averages and overall attendance figures

- **Persistence**
    - Save and load system state to/from a text file ("mis_data.txt")
    - Students, staff, courses, grades and attendance percentages are all persisted

- **Console UI**
    - Menu-driven interface ('MainMenu') with sub-menus for Students, Staff, Courses, Reports and Save/Load
    - Validated user input via 'Inputs' utility class

---

## Project Structure

mis/
├── models/     # Core domain classes (Student, Staff, Course, Reports)
├── services/     # DataManager and ReportManager for business logic
├── ui/     # Console-based menus (MainMenu, StudentMenu, StaffMenu, CourseMenu, ReportsMenu, SaveLoadMenu)
├── util/     # Utility classes (Inputs, DataIO)
└── tests/     # Unit test (future expansion)

---

## Setup
- Java project structured into packages: models, services, ui, util, tests.
- Version control managed using Git GUI.
- Repository connected to GitHub for remote tracking.
- Regular commits document feature development and testing progress.

---

## How to Run

Compile and run from the project root:

'''bash
javac mis/ui/MISSystem.java 
java mis.ui.MISSystem

---

## Example Workflow

1. Launch the system (MISSystem).
2. Navigate via the Main Menu:
    - Add students and staff
    - Create courses and enrol students
    - Assign tasks to staff
    - Record grades
3. Generate reports:
    - View Grades Report for student performance
    - View Attendance Report for per-student and per-course attendance averages
4. Save system state to 'mis_data.txt'.
5. Reload data in future sessions.