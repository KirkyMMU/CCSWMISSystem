import mis.models.*;
import mis.services.DataManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link AttendanceReport}.
 *
 * <p>This test suite verifies that the AttendanceReport class correctly
 * generates attendance summaries at multiple levels including:</p>
 * <ul>
 *   <li>Per-student attendance output</li>
 *   <li>Course-level average attendance</li>
 *   <li>Overall average attendance</li>
 *   <li>Graceful handling of empty or partial datasets</li>
 * </ul>
 *
 * <p><b>Design notes:</b></p>
 * <ul>
 *   <li>Each test uses a fresh {@link DataManager} instance to ensure isolation.</li>
 *   <li>Attendance values are explicitly set to avoid reliance on random data.</li>
 *   <li>Assertions focus on meaningful substrings rather than exact formatting.</li>
 *   <li>Tests are organised using {@link Nested} classes to group related scenarios.</li>
 * </ul>
 */
class AttendanceReportTests
{
    private DataManager manager;

    /**
     * Sets up a fresh {@link DataManager} populated with sample courses
     * and students before each test.
     *
     * <p>This shared setup ensures consistent test data for all nested
     * test scenarios.</p>
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
     * Tests related to per-student attendance information
     * displayed in the report output.
     */
    @Nested
    class StudentAttendanceTests
    {
        @Test
        void reportIncludesStudentAttendance()
        {
            AttendanceReport report = new AttendanceReport(manager);
            String output = report.generateReport();

            assertAll
            (
                () -> assertTrue(output.contains("Alice Smith")),
                () -> assertTrue(output.contains("98.5%")),
                () -> assertTrue(output.contains("Bob Jones")),
                () -> assertTrue(output.contains("87.4%")),
                () -> assertTrue(output.contains("Carol Lee")),
                () -> assertTrue(output.contains("100.0%"))
            );
        }

        @Test
        void attendanceIsFormattedToOneDecimalPlace()
        {
            AttendanceReport report = new AttendanceReport(manager);
            String output = report.generateReport();

            assertAll
            (
                () -> assertTrue(output.contains("98.5%")),
                () -> assertTrue(output.contains("87.4%")),
                () -> assertTrue(output.contains("100.0%"))
            );
        }
    }

    /**
     * Tests related to course-level average attendance
     * calculations and output.
     */
    @Nested
    class CourseAverageTests
    {
        @Test
        void reportIncludesCourseAverages()
        {
            AttendanceReport report = new AttendanceReport(manager);
            String output = report.generateReport();

            assertAll
            (
                () -> assertTrue(output.contains("Course CS101 Average Attendance")),
                () -> assertTrue(output.contains("Course MATH201 Average Attendance"))
            );
        }

        @Test
        void courseWithNoEnrolledStudentsIsNotIncluded()
        {
            Course emptyCourse = new Course("PHY301", "Physics");
            manager.addCourse(emptyCourse);

            AttendanceReport report = new AttendanceReport(manager);
            String output = report.generateReport();

            assertFalse(output.contains("Course PHY301 Average Attendance"));
        }

        @Test
        void courseAverageSkipsMissingStudent()
        {
            Course cs101 = manager.getCourses().get(0);
            cs101.enrolStudent(999); // ID not in DataManager

            AttendanceReport report = new AttendanceReport(manager);
            String output = report.generateReport();

            assertTrue(output.contains("Course CS101 Average Attendance"));
        }
    }

    /**
     * Tests related to the overall average attendance
     * calculated across all students.
     */
    @Nested
    class OverallAverageTests
    {
        @Test
        void reportIncludesOverallAverage()
        {
            AttendanceReport report = new AttendanceReport(manager);
            String output = report.generateReport();

            assertTrue(output.contains("Overall Average Attendance"));
        }
    }

    /**
     * Tests covering edge cases where data is missing
     * or partially populated.
     */
    @Nested
    class EmptyDataTests
    {
        @Test
        void reportHandlesNoStudents()
        {
            DataManager emptyManager = new DataManager();
            AttendanceReport report = new AttendanceReport(emptyManager);
            String output = report.generateReport();

            assertTrue(output.contains("No students found"));
        }

        @Test
        void courseAveragesSectionNotShownWhenNoCourses()
        {
            DataManager noCoursesManager = new DataManager();

            Student student = new Student(1, "Test Student", "test@example.com");
            student.setAttendancePercentage(90);
            noCoursesManager.addStudent(student);

            AttendanceReport report = new AttendanceReport(noCoursesManager);
            String output = report.generateReport();

            assertFalse(output.contains("--- Course Averages ---"));
        }
    }

    /**
     * Tests related to the structural elements
     * of the report output.
     */
    @Nested
    class ReportStructureTests
    {
        @Test
        void reportContainsHeader()
        {
            AttendanceReport report = new AttendanceReport(manager);
            String output = report.generateReport();

            assertTrue(output.contains("--- Attendance Report ---"));
        }
    }
}