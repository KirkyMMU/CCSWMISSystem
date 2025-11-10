package mis.tests;

import mis.models.*;
import mis.services.DataManager;

/**
 * Test class for Phase 3 of the MIS System.
 * Demonstrates creation, management and interaction of students, staff and courses.
 */
public class DataManagerTests
{
    public static void main(String[] args)
    {
        // Create the central Data Manager
        DataManager manager = new DataManager();

        // Create and add sample courses
        Course computing = new Course("CS101", "Computing");
        Course business = new Course("BUS123", "Business");
        manager.addCourse(computing);
        manager.addCourse(business);

        // Add sample students
        Student s1 = new Student(1, "Alice", "alice@example.com", computing);
        s1.addGrade(7);
        s1.addGrade(8);
        manager.addStudent(s1);

        Student s2 = new Student(2, "Charlie", "charlie@example.com", business);
        s2.addGrade(6);
        manager.addStudent(s2);

        // Add sample staff
        Staff st1 = new Staff(101, "Bob", "bob@example.com", "Lecturer", "IT");
        manager.addStaff(st1);

        // Display all students
        System.out.println("~~~ Students ~~~");
        manager.listStudents();

        // Display all staff
        System.out.println("\n~~~ Staff ~~~");
        manager.listStaff();

        // Display all courses
        System.out.println("\n~~~ Courses ~~~");
        for(Course c : manager.getCourses())
        {
            System.out.println(c);
        }

        // Display enrolments for each course
        System.out.println("\n~~~ Enrolments ~~~");
        for(Course c : manager.getCourses())
        {
            c.listEnrolled(manager);
            System.out.println();
        }

        // Search for a student by ID
        System.out.println("Searching for student with ID 1...");
        Student found = manager.findStudentById(1);
        if(found != null)
        {
            System.out.println("Found: " + found);
        }
        else
        {
            System.out.println("Student not found.");
        }

        // Remove a student and confirm removal
        System.out.println("\nRemoving student with ID 2...");
        boolean removed = manager.removeStudentById(2);
        System.out.println("Removed: " + removed);

        System.out.println("\n~~~ Students After Removal ~~~");
        manager.listStudents();
    }
}
