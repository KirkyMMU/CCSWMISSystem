package mis.ui;

import mis.models.*;
import mis.services.DataManager;
import mis.util.Inputs;

/**
 * Main entry point for the MIS System.
 * Provides a console-based menu for managing students, staff and courses.
 * Uses Inputs helper for validated user input
 */
public class MISSystem
{
    public static void main(String[] args)
    {
        // Create the central data manager to store students, staff and courses
        DataManager manager = new DataManager();

        // Flag to control the main menu loop
        boolean running = true;

        System.out.println("Welcome to the MIS System.\n");

        // Main Menu loop
        while(running)
        {
            System.out.println("~~~~~ Main Menu ~~~~~");
            System.out.println("1. Students");
            System.out.println("2. Staff");
            System.out.println("3. Courses");
            System.out.println("4. Reports");
            System.out.println("5. Save/Load");
            System.out.println("6. Exit");
        

            // Read user choice using validated input
            int choice = Inputs.readInt("Choose an option (1-6):");

            // Route to appropriate sub-menu or action
            switch(choice)
            {
                case 1 -> studentMenu(manager);
                case 2 -> System.out.println("Staff menu not implemented yet.");
                case 3 -> System.out.println("Courses menu not implemented yet.");
                case 4 -> System.out.println("Reports not implemented yet.");
                case 5 -> System.out.println("Save/Load not implemented yet.");
                case 6 ->
                {
                    // Confirm exit before terminating loop
                    boolean confirmExit = Inputs.confirm("Are you sure you want to exit?");
                    if(confirmExit)
                    {
                        running = false;
                    }
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
        System.out.println("Goodbye!");
    }

    /**
     * Displays the student sub-menu and handles student-related operations.
     * @param manager Reference to the shared DataManager instance.
     */
    private static void studentMenu(DataManager manager)
    {
        System.out.println("\n----- Student Menu -----");
        System.out.println("1. Add Student");
        System.out.println("2. List Students");
        System.out.println("3. Remove Student");
        System.out.println("4. Back");

        int choice = Inputs.readInt("Choose an option (1-4):");

        switch(choice)
        {
            case 1 -> 
            {
                // Add Student
                int id = Inputs.readInt("Enter Student's ID:");
                String name = Inputs.readString("Enter Student's Name:");
                String email = Inputs.readString("Enter Student's Email:");
                String courseId = Inputs.readString("Enter Student's Course ID:");
                String courseTitle = Inputs.readString("Enter Student's Course Title:");
                Course course = new Course(courseId, courseTitle);

                // Create and add student to manager
                Student s = new Student(id, name, email, course);
                manager.addStudent(s);
                System.out.println("Student added successfully.");
            }
            case 2 ->
            {
                // List Students
                manager.listStudents();
            }
            case 3 ->
            {
                // Remove Student
                int removeId = Inputs.readInt("Enter Student ID to be removed:");
                boolean removed = manager.removeStudentById(removeId);
                if(removed)
                {
                    System.out.println("Student removed successfully.");
                }
                else
                {
                    System.out.println("Student not found.");
                }
            }
            case 4 ->
            {
                // Return to Main Menu
                System.out.println("Returning to main menu...");
            }
            default ->
            {
                System.out.println("Invalid option.");
            }
        }
    }
}
