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

        // Add sample students
        Student s1 = new Student(1, "Alice", "alice@example.com", "Computing");
        s1.addGrade(7);
        s1.addGrade(8);
        manager.addStudent(s1);

        Student s2 = new Student(2, "Charlie", "charlie@example.com", "Business");
        s2.addGrade(6);
        manager.addStudent(s2);

        // Add sample staff
        Staff st1 = new Staff(101, "Bob", "bob@example.com", "Lecturer", "IT");
        manager.addStaff(st1);

        // Create and add courses
        Course c1 = new Course("CS101", "Intro to Computing");
        Course c2 = new Course("BUS201", "Business Fundamentals");
        manager.addCourse(c1);
        manager.addCourse(c2);

        // Enrol students onto courses
        c1.enrolStudent(1); // Enrol Alice
        c2.enrolStudent(2); // Enrol Charlie

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
