import mis.models.*;
import mis.services.DataManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link GradesReport}.
 * 
 * <p>This test suite verifies that the GradesReport class correctly
 * generates grade summaries for students including:</p>
 * <ul>
 *   <li>Per-student average grade calculation</li>
 *   <li>Handling of students with no grades</li>
 *   <li>Handling of students without assigned courses</li>
 *   <li>Graceful behaviour when no students exist</li>
 * </ul>
 *
 * <p><b>Design notes:</b></p>
 * <ul>
 *   <li>Each test uses a fresh {@link DataManager} instance.</li>
 *   <li>Grades are explicitly assigned to ensure predictable averages.</li>
 *   <li>Assertions focus on meaningful substrings rather than exact formatting.</li>
 *   <li>Tests are organised using {@link Nested} classes to group related scenarios.</li>
 * </ul>
 */
class GradesReportTests
{
    private DataManager manager;

    /**
     * Sets up a DataManager populated with sample students and courses 
     * before each test.
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
     * Tests related to per-student grade output
     * and average grade calculation.
     */
    @Nested
    class StudentGradeTests
    {
        @Test
        void reportIncludesStudentNamesIdsAndAverages()
        {
            GradesReport report = new GradesReport(manager);
            String output = report.generateReport();

            assertAll
            (
                () -> assertTrue(output.contains("Alice Smith")),
                () -> assertTrue(output.contains("ID: 101")),
                () -> assertTrue(output.contains("Average Grade: 7.5")), // (7+8)/2
                () -> assertTrue(output.contains("Bob Jones")),
                () -> assertTrue(output.contains("ID: 102")),
                () -> assertTrue(output.contains("Average Grade: 6.5")) // (6+7)/2
            );
        }

        @Test
        void reportHandlesSingleGrade()
        {
            Student eve = new Student(105, "Eve", "eve@example.com");
            eve.addGrade(9);
            manager.addStudent(eve);

            GradesReport report = new GradesReport(manager);
            String output = report.generateReport();

            assertAll
            (
                () -> assertTrue(output.contains("Eve")),
                () -> assertTrue(output.contains("Average Grade: 9.0"))
            );
        }
    }

    /**
     * Tests related to students who have no grades recorded.
     */
    @Nested
    class NoGradesTests
    {
        @Test
        void reportShowsNAForStudentsWithNoGrades()
        {
            GradesReport report = new GradesReport(manager);
            String output = report.generateReport();

            assertAll
            (
                () -> assertTrue(output.contains("Carol Lee")),
                () -> assertTrue(output.contains("N/A"))
            );
        }

        @Test
        void mixedStudentStates()
        {
            Student grace = new Student(107, "Grace", "grace@example.com");
            // no grades, no course
            manager.addStudent(grace);

            GradesReport report = new GradesReport(manager);
            String output = report.generateReport();

            assertAll
            (
                () -> assertTrue(output.contains("Grace")),
                () -> assertTrue(output.contains("No course")),
                () -> assertTrue(output.contains("N/A"))
            );
        }
    }

    /**
     * Tests related to students who are not enrolled
     * on any course.
     */
    @Nested
    class NoCourseTests
    {
        @Test
        void reportShowsNoCourseWhenStudentNotEnrolled()
        {
            Student dave = new Student(104, "Dave White", "dave@example.com");
            dave.addGrade(9);
            manager.addStudent(dave);

            GradesReport report = new GradesReport(manager);
            String output = report.generateReport();

            assertAll
            (
                () -> assertTrue(output.contains("Dave White")),
                () -> assertTrue(output.contains("No course"))
            );
        }

        @Test
        void reportHandlesCourseNotInManager()
        {
            Course orphanCourse = new Course("CS999", "Orphan Course");

            Student frank = new Student(106, "Frank", "frank@example.com");
            frank.addGrade(8);
            frank.setCourse(orphanCourse);
            manager.addStudent(frank);

            GradesReport report = new GradesReport(manager);
            String output = report.generateReport();

            assertAll
            (
                () -> assertTrue(output.contains("Frank")),
                () -> assertTrue(output.contains("CS999"))
            );
        }
    }

    /**
     * Tests covering edge cases where no student
     * data exists in the system.
     */
    @Nested
    class EmptyDataTests
    {
        @Test
        void reportHandlesNoStudentsGracefully()
        {
            DataManager emptyManager = new DataManager();
            GradesReport report = new GradesReport(emptyManager);
            String output = report.generateReport();

            assertTrue(output.contains("No students found."));
        }
    }

    /**
     * Tests related to the structural elements
     * of the grades report output.
     */
    @Nested
    class ReportStructureTests
    {
        @Test
        void reportContainsHeader()
        {
            GradesReport report = new GradesReport(manager);
            String output = report.generateReport();

            assertTrue(output.contains("--- Grades Report ---"));
        }
    }
}