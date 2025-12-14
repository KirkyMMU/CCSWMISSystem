import mis.models.*;
import mis.services.DataManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Course} class.
 *
 * <p>This test suite verifies the core behaviour of course enrolment
 * and management within the MIS system including:</p>
 * <ul>
 *   <li>Student enrolment and de-enrolment</li>
 *   <li>Prevention of duplicate enrolments</li>
 *   <li>CSV output formatting for enrolled student IDs</li>
 *   <li>Summary string representation</li>
 *   <li>Graceful handling of invalid or missing data</li>
 * </ul>
 *
 * <p><b>Design notes:</b></p>
 * <ul>
 *   <li>Each test creates its own {@link Course} instance to ensure isolation.</li>
 *   <li>Assertions focus on observable behaviour rather than internal state.</li>
 *   <li>Tests are organised using {@link Nested} classes to group related scenarios.</li>
 * </ul>
 */
class CourseTests
{
    /**
     * Tests related to enrolling and removing students
     * from a course.
     */
    @Nested
    class EnrolmentTests
    {
        @Test
        void enrolStudentAddsId()
        {
            Course course = new Course("CS101", "Computer Science");
            course.enrolStudent(1);

            assertTrue(course.getEnrolledIdsCSV().contains("1"));
        }

        @Test
        void enrolDuplicateStudentDoesNotAddTwice()
        {
            Course course = new Course("CS101", "Computer Science");
            course.enrolStudent(1);
            course.enrolStudent(1);

            assertEquals("1", course.getEnrolledIdsCSV()); // only once
        }

        @Test
        void removeStudentRemovesId()
        {
            Course course = new Course("CS101", "Computer Science");
            course.enrolStudent(1);
            course.removeStudent(1);

            assertEquals("", course.getEnrolledIdsCSV());
        }

        @Test
        void removeNonExistentStudentDoesNothing()
        {
            Course course = new Course("CS101", "Computer Science");
            course.enrolStudent(1);
            course.removeStudent(99); // not enrolled

            assertEquals("1", course.getEnrolledIdsCSV());
        }
    }

    /**
     * Tests related to CSV output formatting
     * for enrolled student IDs.
     */
    @Nested
    class CsvOutputTests
    {
        @Test
        void multipleStudentsAreFormattedCorrectly()
        {
            Course course = new Course("CS101", "Computer Science");
            course.enrolStudent(1);
            course.enrolStudent(2);

            assertEquals("1,2", course.getEnrolledIdsCSV());
        }

        @Test
        void emptyCourseReturnsEmptyCsv()
        {
            Course course = new Course("CS101", "Computer Science");

            assertEquals("", course.getEnrolledIdsCSV());
        }
    }

    /**
     * Tests related to the string representation
     * and metadata of a course.
     */
    @Nested
    class CourseMetadataTests
    {
        @Test
        void courseSummaryStringIncludesKeyInformation()
        {
            Course course = new Course("CS101", "Computer Science");
            course.enrolStudent(1);
            course.enrolStudent(2);

            String summary = course.toString();

            assertAll
            (
                () -> assertTrue(summary.contains("CS101")),
                () -> assertTrue(summary.contains("Computer Science")),
                () -> assertTrue(summary.contains("2 enrolled"))
            );
        }

        @Test
        void getCodeAndTitleReturnCorrectValues()
        {
            Course course = new Course("CS101", "Computer Science");

            assertAll
            (
                () -> assertEquals("CS101", course.getCode()),
                () -> assertEquals("Computer Science", course.getTitle())
            );
        }
    }

    /**
     * Tests covering defensive behaviour when
     * enrolled student IDs cannot be resolved.
     */
    @Nested
    class DefensiveBehaviourTests
    {
        @Test
        void testListEnrolledHandlesMissingStudent()
        {
            Course course = new Course("CS101", "Computer Science");
            course.enrolStudent(1); // ID not in DataManager

            DataManager manager = new DataManager();
            
            // Should not throw an exception
            course.listEnrolled(manager);
        }
    }
}