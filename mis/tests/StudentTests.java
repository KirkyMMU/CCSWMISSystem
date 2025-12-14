import mis.models.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Student} class.
 * 
 * <p>This test suite verifies the core behaviour of students within the
 * MIS system including:</p>
 * <ul>
 *   <li>Grade validation and average calculation</li>
 *   <li>Course enrolment and reassignment</li>
 *   <li>Attendance generation and management</li>
 *   <li>CSV output for persistence</li>
 *   <li>String representation of student details</li>
 * </ul>
 *
 * <p><b>Design notes:</b></p>
 * <ul>
 *   <li>Each test creates a fresh {@link Student} instance to ensure isolation.</li>
 *   <li>Assertions focus on observable behaviour rather than internal state.</li>
 *   <li>Tests are organised using {@link Nested} classes to group related scenarios.</li>
 * </ul>
 */
class StudentTests
{
    /**
     * Tests related to adding grades and validating
     * grade input.
     */
    @Nested
    class GradeValidationTests
    {
        @Test
        void addValidGradeSucceeds()
        {
            Student student = new Student(1, "Alice", "alice@example.com");

            assertTrue(student.addGrade(7));
        }

        @Test
        void addInvalidGradeBelowRangeFails()
        {
            Student student = new Student(1, "Alice", "alice@example.com");

            assertFalse(student.addGrade(-1));
        }

        @Test
        void addInvalidGradeAboveRangeFails()
        {
            Student student = new Student(1, "Alice", "alice@example.com");

            assertFalse(student.addGrade(15));
        }
    }

    /**
     * Tests related to calculating average grades
     * for students.
     */
    @Nested
    class GradeAverageTests
    {
        @Test
        void calculateAverageWithGradesReturnsCorrectValue()
        {
            Student student = new Student(1, "Alice", "alice@example.com");
            student.addGrade(7);
            student.addGrade(9);

            assertEquals(8.0, student.calculateAverage());
        }

        @Test
        void calculateAverageWithNoGradesReturnsZero()
        {
            Student student = new Student(1, "Alice", "alice@example.com");

            assertEquals(0.0, student.calculateAverage());
        }
    }

    /**
     * Tests related to course enrolment,
     * reassignment and de-enrolment.
     */
    @Nested
    class CourseAssignmentTests
    {
        @Test
        void constructorWithCourseEnrolsStudent()
        {
            Course course = new Course("CS101", "Computer Science");
            Student student = new Student(1, "Alice", "alice@example.com", course);

            assertAll
            (
                () -> assertEquals(course, student.getCourse()),
                () -> assertTrue(course.getEnrolledIdsCSV().contains("1"))
            );
        }

        @Test
        void setCourseReassignsStudentCorrectly()
        {
            Course course1 = new Course("CS101", "Computer Science");
            Course course2 = new Course("BUS123", "Business");
            Student student = new Student(1, "Alice", "alice@example.com", course1);

            student.setCourse(course2);

            assertAll
            (
                () -> assertEquals("BUS123", student.getCourse().getCode()),
                () -> assertFalse(course1.getEnrolledIdsCSV().contains("1")),
                () -> assertTrue(course2.getEnrolledIdsCSV().contains("1"))
            );
        }

        @Test
        void setCourseToNullRemovesEnrolment()
        {
            Course course = new Course("CS101", "Computer Science");
            Student student = new Student(1, "Alice", "alice@example.com", course);

            student.setCourse(null);

            assertAll
            (
                () -> assertNull(student.getCourse()),
                () -> assertFalse(course.getEnrolledIdsCSV().contains("1"))
            );
        }
    }

    /**
     * Tests related to attendance generation
     * and manual attendance updates.
     */
    @Nested
    class AttendanceTests
    {
        @Test
        void setAttendancePercentageUpdatesValue()
        {
            Student student = new Student(1, "Alice", "alice@example.com");
            student.setAttendancePercentage(92.5);

            assertEquals(92.5, student.getAttendancePercentage());
        }

        @Test
        void generatedAttendanceIsWithinExpectedRange()
        {
            Student student = new Student(1, "Alice", "alice@example.com");
            double attendance = student.getAttendancePercentage();

            assertAll
            (
                () -> assertTrue(attendance >= 85.0),
                () -> assertTrue(attendance <= 100.0)
            );
        }
    }

    /**
     * Tests related to CSV output of grades
     * for persistence and reporting.
     */
    @Nested
    class GradeCsvTests
    {
        @Test
        void getGradesCSVWithGradesReturnsCommaSeparatedValues()
        {
            Student student = new Student(1, "Alice", "alice@example.com");
            student.addGrade(7);
            student.addGrade(9);

            assertEquals("7,9", student.getGradesCSV());
        }

        @Test
        void getGradesCSVWithNoGradesReturnsEmptyString()
        {
            Student student = new Student(1, "Alice", "alice@example.com");

            assertEquals("", student.getGradesCSV());
        }
    }

    /**
     * Tests related to the string representation
     * of student details.
     */
    @Nested
    class StudentToStringTests
    {
        @Test
        void toStringIncludesCourseAndAttendance()
        {
            Course course = new Course("CS101", "Computer Science");
            Student student = new Student(1, "Alice", "alice@example.com", course);
            student.setAttendancePercentage(95.0);

            String output = student.toString();

            assertAll
            (
                () -> assertTrue(output.contains("CS101")),
                () -> assertTrue(output.contains("95.0%"))
            );
        }
    }
}