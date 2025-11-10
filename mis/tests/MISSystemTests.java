package mis.tests;

import java.time.LocalDate;
import mis.models.*;
import mis.services.DataManager;

public class MISSystemTests
{
    public static void main(String[] args)
    {
        testAddingStudent();
        testRemovingStudent();
        testAddingStaff();
        testAssigningTask();
        testRemovingStaff();
    }

    private static void testAddingStudent()
    {
        System.out.println("\n--- Test: Adding Students ---");
        DataManager manager = new DataManager();

        // Valid Course
        Course computing = new Course("CS101", "Computer Science");
        manager.addCourse(computing);

        // Valid Student
        Student s1 = new Student(1, "Alice", "alice@example.com", computing);
        s1.addGrade(7);
        manager.addStudent(s1);

        // Edge Case: Duplicate ID
        Student duplicate = new Student(1, "Alicia", "alicia@example.com", computing);
        boolean duplicateAdded = manager.addStudent(duplicate);
        System.out.println("Add duplicate ID (should be false): " + duplicateAdded);

        // Edge Case: Null Student
        boolean nullAdded = manager.addStudent(null);
        System.out.println("Add null ID (should be false): " + nullAdded);

        // Edge Case: Invalid Grade (negative)
        boolean negativeGrade = s1.addGrade(-3);
        System.out.println("Add grade -3 (should be false): " + negativeGrade);

        // Edge Case Invalid Grade (above range)
        boolean highGrade = s1.addGrade(15);
        System.out.println("Add grade 15 (should be false): " + highGrade);
    }

    private static void testRemovingStudent()
    {
        System.out.println("\n--- Test: Removing Students ---");
        DataManager manager = new DataManager();

        // Valid Course
        Course computing = new Course("CS101", "Computer Science");
        manager.addCourse(computing);

        // Valid Student
        Student s1 = new Student(1, "Andrew", "andrew@example.com", computing);
        manager.addStudent(s1);

        // Test: ID to remove does not exist
        boolean fakeRemoved = manager.removeStudentById(2);
        System.out.println("Fake ID removed (should be false): " + fakeRemoved);

        // Test: ID to remove does exist
        boolean realRemoved = manager.removeStudentById(1);
        System.out.println("Real ID removed (should be true): " + realRemoved);

    }

    private static void testAddingStaff()
    {
        System.out.println("\n--- Test: Adding Staff ---");
        DataManager manager = new DataManager();

        // Valid Staff Member
        Staff st1 = new Staff(1, "Arthur", "arthur@example.com", "Senior Lecturer", "Computer Science");
        manager.addStaff(st1);

        // Edge Case: Duplicate ID
        Staff duplicate = new Staff(1, "Archie", "archie@example.com", "Apprentice Lecturer", "Computer Science");
        boolean duplicateAdded = manager.addStaff(duplicate);
        System.out.println("Add duplicate ID (should be false): " + duplicateAdded);

        // Edge Case: Null Staff Member
        boolean nullAdded = manager.addStaff(null);
        System.out.println("Add null ID (should be false): " + nullAdded);
    }

    private static void testAssigningTask()
    {
        System.out.println("\n--- Test: Assigning Tasks ---");
        DataManager manager = new DataManager();

        // Valid Staff Member
        Staff st1 = new Staff(101, "Fred", "fred@example.com", "Lecturer", "IT");
        manager.addStaff(st1);

        // Edge Case: Invalid Deadline (past date)
        System.out.println("1st Test: Invalid deadline entered (past date) (staff should not have any task assigned)");
        LocalDate pastDate = LocalDate.now().minusDays(1);
        st1.assignTask("Review syllabus", pastDate);

        System.out.println(st1);

        // Edge Case: Invalid Deadline (today's date)
        System.out.println("2nd Test: Invalid deadline entered (today's date) (staff should not have any task assigned)");
        LocalDate today = LocalDate.now();
        st1.assignTask("Cover lesson", today);

        System.out.println(st1);

        // Edge Case: Invalid Deadline (future date)
        System.out.println("3rd Test: Invalid deadline entered (future date) (staff should not have any task assigned)");
        LocalDate futureDate = LocalDate.now().plusMonths(6);
        st1.assignTask("Prepare conference", futureDate);

        System.out.println(st1);

        // Test: Valid Deadline
        System.out.println("4th Test: Valid deadline entered (staff should have 1 task assigned)");
        LocalDate validDate = LocalDate.now().plusDays(7);
        st1.assignTask("Prepare lesson plan", validDate);

        System.out.println(st1);

    }

    private static void testRemovingStaff()
    {
        System.out.println("\n--- Test: Removing Staff ---");
        DataManager manager = new DataManager();

        // Valid Staff Member
        Staff st1 = new Staff(101, "Henrietta", "henrietta@example.com", "Teaching Assistant", "Geography");
        manager.addStaff(st1);

        // Test: ID to remove does not exist
        boolean fakeRemoved = manager.removeStaffById(2);
        System.out.println("Fake ID removed (should be false): " + fakeRemoved);

        // Test: ID to remove does exist
        boolean realRemoved = manager.removeStaffById(101);
        System.out.println("Real ID removed (should be true): " + realRemoved);
    }
}