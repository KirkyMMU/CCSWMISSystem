import mis.models.*;
import mis.services.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link GradesReport}.
 * 
 * <p>These tests verify that the GradesReport class correctly generates
 * grade summaries for students including handling of average calculation,
 * missing grades and course information.</p>
 *
 * <p><b>Design Notes:</b>
 * <ul>
 *   <li>Uses a fresh {@link DataManager} in each test to ensure isolation.</li>
 *   <li>Students are created with fixed grade values to produce predictable averages.</li>
 *   <li>Tests verify both normal and edge cases (no grades, no course).</li>
 *   <li>Assertions check substrings in the report output rather than exact formatting
 *       allowing flexibility in presentation.</li>
 * </ul>
 * </p>
 */
public class GradesReportTests
{
    private DataManager manager;

    /**
     * Sets up a DataManager with sample students and courses before each test.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create a new {@link DataManager}.</li>
     *   <li>Add a course (CS101).</li>
     *   <li>Create students with fixed grades and assign them to the course.</li>
     *   <li>Add students and course to the manager.</li>
     * </ol>
     * </p>
     */
    @BeforeEach
    void setUp()
    {
        manager = new DataManager();

        Course cs101 = new Course("CS101", "Introduction to Programming");
        manager.addCourse(cs101);

        Student alice = new Student(101, "Alice Smith", "alice@example.com");
        alice.addGrade(7);
        alice.addGrade(8);
        alice.setCourse(cs101);
        manager.addStudent(alice);

        Student bob = new Student(102, "Bob Jones", "bob@example.com");
        bob.addGrade(6);
        bob.addGrade(7);
        bob.setCourse(cs101);
        manager.addStudent(bob);

        Student carol = new Student(103, "Carol Lee", "carol@example.com");
        // Carol has no grades
        manager.addStudent(carol);
    }

    /**
     * Verifies that the report includes each student's name, ID and average grade.
     *
     * <p>Steps:
     * <ol>
     *   <li>Create a {@link GradesReport} with the test manager.</li>
     *   <li>Generate the report string.</li>
     *   <li>Assert that student names, IDs and average grades appear in the output.</li>
     * </ol>
     * </p>
     */
    @Test
    void testReportIncludesStudentGrades()
    {
        GradesReport report = new GradesReport(manager);
        String output = report.generateReport();

        assertTrue(output.contains("Alice Smith"));
        assertTrue(output.contains("ID: 101"));
        assertTrue(output.contains("Average Grade: 7.5")); // (7+8)/2
        assertTrue(output.contains("Bob Jones"));
        assertTrue(output.contains("ID: 102"));
        assertTrue(output.contains("Average Grade: 6.5")); // (6+7)/2
    }

    /**
     * Verifies that the report shows "N/A" when a student has no grades.
     *
     * <p>Steps:
     * <ol>
     *   <li>Create a {@link GradesReport} with the test manager.</li>
     *   <li>Generate the report string.</li>
     *   <li>Assert that Carol's entry contains "N/A".</li>
     * </ol>
     * </p>
     */
    @Test
    void testReportHandlesNoGrades()
    {
        GradesReport report = new GradesReport(manager);
        String output = report.generateReport();

        assertTrue(output.contains("Carol Lee"));
        assertTrue(output.contains("N/A"));
    }

    /**
     * Verifies that the report shows "No course" when a student is not enrolled.
     *
     * <p>Steps:
     * <ol>
     *   <li>Create a new student without assigning a course.</li>
     *   <li>Add the student to the manager.</li>
     *   <li>Generate the report string.</li>
     *   <li>Assert that the student's entry contains "No course".</li>
     * </ol>
     * </p>
     */  
    @Test
    void testReportHandlesNoCourse()
    {
        Student dave = new Student(104, "Dave White", "dave@example.com");
        dave.addGrade(9);
        manager.addStudent(dave);

        GradesReport report = new GradesReport(manager);
        String output = report.generateReport();

        assertTrue(output.contains("Dave White"));
        assertTrue(output.contains("No course"));
    }
}