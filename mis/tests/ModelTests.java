package mis.tests;

import mis.models.*;
import java.time.LocalDate;

/**
 * Simple test class for phase 2 of the MIS System to demonstrate the functionality of Student and Staff models.
 * Acts as a manual unit test for verifying constructors, methods and inheritance.
 */
public class ModelTests
{
    public static void main(String[] args)
    {
        // Create a Course object with sample data
        Course computing = new Course("CS101", "Computing");

        // Create a Student object with the Course
        Student s = new Student(1, "Alice", "alice@example.com", computing);
        
        // Add GCSE-style grades (1-9)
        s.addGrade(7);
        s.addGrade(8);
        s.addGrade(9);

        // Display student details
        System.out.println(s);

        // Display average grade
        System.out.println("Average: " + s.calculateAverage());

        // Create a Staff object with sample data
        Staff st = new Staff(2, "Bob", "bob@example.com", "Lecturer", "IT");

        // Assign a task to the staff member
        st.assignTask("Prepare lesson plan", LocalDate.of(2025, 11, 10));

        // Display staff details
        System.out.println(st);
    }
}