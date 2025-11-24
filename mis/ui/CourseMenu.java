package mis.ui;

import mis.models.*;
import mis.services.DataManager;
import mis.util.Inputs;

/**
 * Handles course-related operations via a console sub-menu.
 * 
 * <p>Provides options to list, add, enrol students, search and remove courses.
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
public class CourseMenu 
{
    // Shared DataManager instance used to manage courses and students.
    private final DataManager manager;

    /**
     * Constructs a CourseMenu with the given DataManager.
     * 
     * @param manager the shared {@link DataManager} instance
     */
    public CourseMenu(DataManager manager)
    {
        this.manager = manager;
    }

    // Displays the course menu and routes user choices to appropriate actions.
    public void show()
    {
        System.out.println("\n ----- Course Menu -----");
        System.out.println("1. List Courses");
        System.out.println("2. Add New Course");
        System.out.println("3. Enrol Student Onto Course");
        System.out.println("4. List Students In A Course");
        System.out.println("5. Search Course By Code");
        System.out.println("6. Remove Course");
        System.out.println("7. Back");

        int choice = Inputs.readInt("Choose an option (1-7):");

        switch(choice)
        {
            case 1 -> listCourses();
            case 2 -> addCourse();
            case 3 -> enrolStudent();
            case 4 -> listStudentsInCourse();
            case 5 -> searchCourse();
            case 6 -> removeCourse();
            case 7 -> System.out.println("\nReturning to Main Menu...\n");
            default -> System.out.println("\nInvalid option.\n");
        }
    }

    // Lists all courses currently stored in the DataManager
    private void listCourses()
    {
        for(Course c : manager.getCourses())
        {
            System.out.println("\n" + c);
        }
    }

    /**
     * Adds a new course to the DataManager.
     * 
     * <p>Prompts the user for a course code and title then attempts to add
     * the course. Displays a success or failure message depending on whether
     * the course code already exists.</p>
     */
    private void addCourse()
    {
        String code = Inputs.readString("Enter the new Course Code:");
        String title = Inputs.readString("Enter the new Course Title:");

        Course course = new Course(code, title);
        boolean added = manager.addCourse(course);

        if(added)
        {
            System.out.println("\nCourse added successfully.\n");
        }
        else
        {
            System.out.println("\nCourse Code already exists. Course not added.\n");
        }
    }

    /**
     * Enrols a student onto a course.
     * 
     * <p>Prompts the user for a course code and student ID. If both exist,
     * the student is reassigned to the specified course. Displays appropriate
     * messages if either the course or student cannot be found.</p>
     */
    private void enrolStudent()
    {
        String code = Inputs.readString("Enter the Course Code:");
        Course course = manager.findCourseByCode(code);

        if(course != null)
        {
            int studentID = Inputs.readInt("Enter Student ID to enrol:");
            Student student = manager.findStudentById(studentID);

            if(student != null)
            {
                student.setCourse(course);
                System.out.println("\nStudent enrolled successfully.\n");
            }
            else
            {
                System.out.println("\nStudent ID not found.\n");
            }
        }
        else
        {
            System.out.println("\nCourse Code not found.");
        }
    }

    /**
     * Lists all students enrolled in a specific course.
     * 
     * <p>Prompts the user for a course code then displays enrolled students
     * if the course exists. Displays an error message if the course cannot be found.</p>
     */
    private void listStudentsInCourse()
    {
        String code = Inputs.readString("Enter the Course Code:");
        Course course = manager.findCourseByCode(code);

        if(course != null)
        {
            course.listEnrolled(manager);
        }
        else
        {
            System.out.println("\nCourse not found.\n");
        }
    }

    /**
     * Searches for a course by its code and displays its details.
     * 
     * <p>Prompts the user for a course code then prints the course details
     * if found. Displays an error message if the course does not exist.</p>
     */
    private void searchCourse()
    {
        String code = Inputs.readString("Enter the Course Code:");
        Course course = manager.findCourseByCode(code);

        if(course != null)
        {
            System.out.println(course);
        }
        else
        {
            System.out.println("\nCourse not found.\n");
        }
    }

    /**
     * Removes a course by its code.
     * 
     * <p>Prompts the user for a course code then attempts to remove the course
     * from the DataManager. Displays a success or failure message depending on
     * whether the course was found.</p>
     */
    private void removeCourse()
    {
        String code = Inputs.readString("Enter Course Code to remove:");
        boolean removed = manager.removeCourseByCode(code);

        if(removed)
        {
            System.out.println("\nCourse removed successfully.\n");
        }
        else
        {
            System.out.println("\nCourse not found.\n");
        }
    }
}