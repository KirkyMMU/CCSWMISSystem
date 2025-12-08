import mis.models.*;
import mis.services.DataManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for AttendanceReport.
 * 
 * <p>These tests verify that the AttendanceReport class correctly generates
 * attendance reports including per-student details, course-level averages,
 * and overall averages. They also ensure the report handles empty datasets
 * gracefully.</p>
 * 
 * <p><b>Design Notes:</b>
 * <ul>
 *   <li>Tests use a fresh {@link DataManager} instance for isolation ensuring
 *       no state leaks between test cases.</li>
 *   <li>Attendance percentages are explicitly set to fixed values so that
 *       assertions can check predictable output rather than randomised data.</li>
 *   <li>Course enrolments are configured in {@link #setUp()} to validate
 *       course-level average calculations.</li>
 *   <li>Assertions focus on verifying that expected substrings appear in the
 *       generated report rather than exact formatting to allow flexibility
 *       in report presentation.</li>
 *   <li>An empty manager scenario is included to confirm graceful handling
 *       when no students exist.</li>
 * </ul>
 * </p>
 */
public class AttendanceReportTests
{
    private DataManager manager;

    /**
     * Sets up a fresh DataManager with sample courses and students
     * before each test. Attendance percentages are explicitly set
     * to ensure predictable report output.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create a new {@link DataManager}.</li>
     *   <li>Add two courses (CS101, MATH201).</li>
     *   <li>Create three students with fixed attendance values.</li>
     *   <li>Assign students to courses and enrol them.</li>
     *   <li>Add students and courses to the manager.</li>
     * </ol>
     * </p>
     */
    @BeforeEach
    void setUp()
    {
        manager = new DataManager();

        Course cs101 = new Course("CS101", "Introduction to Programming");
        Course math201 = new Course("MATH201", "Further Mathematics");
        manager.addCourse(cs101);
        manager.addCourse(math201);

        Student alice = new Student(101,"Alice Smith", "alice@example.com");
        alice.setAttendancePercentage(98.5);
        alice.setCourse(cs101);
        cs101.enrolStudent(alice.getId());
        manager.addStudent(alice);

        Student bob = new Student(102, "Bob Jones", "bob@example.com");
        bob.setAttendancePercentage(87.4);
        bob.setCourse(cs101);
        cs101.enrolStudent(bob.getId());
        manager.addStudent(bob);

        Student carol = new Student(103, "Carol Lee", "carol@example.com");
        carol.setAttendancePercentage(100);
        carol.setCourse(math201);
        math201.enrolStudent(carol.getId());
        manager.addStudent(carol);
    }

    /**
     * Verifies that the generated report includes each student's
     * name and attendance percentage.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create an {@link AttendanceReport} with the test manager.</li>
     *   <li>Generate the report string.</li>
     *   <li>Assert that names and attendance values appear in the output.</li>
     * </ol>
     * </p>
     */
    @Test
    void testReportIncludesStudentAttendance()
    {
        AttendanceReport report = new AttendanceReport(manager);
        String output = report.generateReport();

        assertTrue(output.contains("Alice Smith"));
        assertTrue(output.contains("98.5%"));
        assertTrue(output.contains("Bob Jones"));
        assertTrue(output.contains("87.4%"));
        assertTrue(output.contains("Carol Lee"));
        assertTrue(output.contains("100.0%"));
    }

    /**
     * Verifies that the generated report includes course-level
     * average attendance values.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create an {@link AttendanceReport} with the test manager.</li>
     *   <li>Generate the report string.</li>
     *   <li>Assert that course average lines appear in the output.</li>
     * </ol>
     * </p>
     */
    @Test
    void testReportIncludesCourseAverages()
    {
        AttendanceReport report = new AttendanceReport(manager);
        String output = report.generateReport();

        assertTrue(output.contains("Course CS101 Average Attendance"));
        assertTrue(output.contains("Course MATH201 Average Attendance"));
    }

    /**
     * Verifies that the generated report includes the overall
     * average attendance across all students.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create an {@link AttendanceReport} with the test manager.</li>
     *   <li>Generate the report string.</li>
     *   <li>Assert that the overall average line appears in the output.</li>
     * </ol>
     * </p>
     */
    @Test
    void testReportIncludesOverallAverage()
    {
        AttendanceReport report = new AttendanceReport(manager);
        String output = report.generateReport();

        assertTrue(output.contains("Overall Average Attendance"));
    }

    /**
     * Verifies that the report handles the case where no students
     * exist in the DataManager and produces a clear message.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create an empty {@link DataManager}.</li>
     *   <li>Create an {@link AttendanceReport} with the empty manager.</li>
     *   <li>Generate the report string.</li>
     *   <li>Assert that the output contains "No students found".</li>
     * </ol>
     * </p>
     */
    @Test
    void testReportHandlesNoStudents()
    {
        DataManager emptyManager = new DataManager();
        AttendanceReport report = new AttendanceReport(emptyManager);
        String output = report.generateReport();

        assertTrue(output.contains("No students found"));
    }
}