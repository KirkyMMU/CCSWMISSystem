package mis.ui;

import mis.models.*;
import mis.services.*;
import mis.util.Inputs;

/**
 * Handles student-related operations via a console sub-menu.
 * 
 * <p>Provides options to add, list, remove and update student records.
 * Encapsulates input handling and delegates persistence to {@link DataManager}.</p>
 * 
 * <p>Design notes:
 * <ul>
 *   <li>Uses {@link Inputs} for validated user input.</li>
 *   <li>Delegates storage and retrieval to {@link DataManager}.</li>
 *   <li>Each menu option is encapsulated in its own private helper method.</li>
 * </ul>
 * </p>
 */
public class StudentMenu 
{
    // Shared DataManager instance used to manager students and courses
    private final DataManager manager;

    /**
     * Constructs a StudentMenu with the given DataManager.
     * 
     * @param manager the shared {@link DataManager} instance
     */
    public StudentMenu(DataManager manager)
    {
        this.manager = manager;
    }

    // Displays the student menu and routes user choices to appropriate actions
    public void show()
    {
        System.out.println("\n----- Student Menu -----");
        System.out.println("1. Add Student");
        System.out.println("2. List Students");
        System.out.println("3. Remove Student");
        System.out.println("4. Add Grade to Student");
        System.out.println("5. Back");

        int choice = Inputs.readInt("Choose an option (1-5):");

        switch(choice)
        {
            case 1 -> addStudent();
            case 2 -> manager.listStudents();
            case 3 -> removeStudent();
            case 4 -> addGrade();
            case 5 -> System.out.println("\nReturning to Main Menu...\n");
            default -> System.out.println("\nInvalid option.");
        }
    }

    /**
     * Adds a new student to the DataManager.
     * 
     * <p>Prompts the user for student details (ID, name, email). Alternatively,
     * the student can be enrolled onto a course immediately. If the course
     * code already exists, the student is enrolled onto the existing course
     * otherwise a new course is created.</p>
     * 
     * <p>Displays a success or failure message depending on whether the student
     * ID already exists.</p>
     */
    private void addStudent()
    {
        int id = Inputs.readInt("Enter Student ID:");
        String name = Inputs.readString("Enter Student Name:");
        String email = Inputs.readString("Enter Student Email:");
        boolean assignCourse = Inputs.confirm("\nDo you want to enrol student onto a course now?");

        Student student;
        if(assignCourse)
        {
            String courseCode = Inputs.readString("Enter Course Code:");
            String courseTitle = Inputs.readString("Enter Course Title:");

            // Check if course code already exists in DataManager
            Course existingCourse = manager.findCourseByCode(courseCode);

            if(existingCourse != null)
            {
                // Re-use existing course, ignore new title
                student = new Student(id, name, email, existingCourse);
                System.out.println("\nCourse code already exists. Student enrolled onto existing course: " + existingCourse.getTitle());
            }
            else
            {
                // Create new course if not found
                Course newCourse = new Course(courseCode, courseTitle);
                manager.addCourse(newCourse);
                student = new Student(id, name, email, newCourse);
                System.out.println("\nNew course created and student enrolled.\n");
            }
        }
        else
        {
            student = new Student(id, name, email);
        }
        boolean added = manager.addStudent(student);
        if(added)
        {
            System.out.println("\nStudent added successfully.\n");
        }
        else
        {
            System.out.println("\nStudent ID already exists. Student not added.\n");
        }
    }

    /**
     * Removes a student by their ID.
     * 
     * <p>Prompts the user for a student ID then attempts to remove the student
     * from the DataManager. Displays a success or failure message depending on
     * whether the student was found.</p>
     */
    private void removeStudent()
    {
        int id = Inputs.readInt("Enter Student ID to remove:");

        boolean removed = manager.removeStudentById(id);
        if(removed)
        {
            System.out.println("\nStudent removed successfully.\n");
        }
        else
        {
            System.out.println("\nStudent not found.\n");
        }
    }

    /**
     * Adds a grade to a student.
     * 
     * <p>Prompts the user for a student ID and grade. If the student exists,
     * the grade is added (must be between 1 and 9). Displays a success or
     * failure message depending on whether the grade was valid.</p>
     */
    private void addGrade()
    {
        int id = Inputs.readInt("Enter Student ID:");
        Student student = manager.findStudentById(id);
        if(student != null)
        {
            int grade = Inputs.readInt("Enter grade (1-9):");

            boolean added = student.addGrade(grade);
            if(added)
            {
                System.out.println("\nGrade added successfully.\n");
            }
            else
            {
               System.out.println("\nInvalid grade.\n");
            }
        }
        else
        {
            System.out.println("\nStudent not found.\n");
        }
    }
}