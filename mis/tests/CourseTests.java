import mis.models.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Course} class.
 * 
 * <p>These tests verify the behaviour of student enrolment
 * within a course ensuring that IDs are added correctly and
 * duplicates are prevented. Also ensuring that removal and output
 * formatting behave as expected.</p>
 * 
 * <p><b>Design notes:</b>
 * <ul>
 *   <li>Uses JUnit 5 annotations (@Test) and assertions from
 *       {@link org.junit.jupiter.api.Assertions}.</li>
 *   <li>Each test method is independent and creates its own
 *       Course instance.</li>
 *   <li>Tests focus on enrolment logic, removal, CSV ouput formatting
 *       and summary string formatting.</li>
 * </ul>
 * </p>
 */
public class CourseTests
{
    /**
     * Verifies that enrolling a student adds their ID to the course.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create a {@link Course} instance.</li>
     *   <li>Enrol a student with ID "1".</li>
     *   <li>Assert that the CSV string of enrolled IDs contains "1".</li>
     * </ol>
     * </p>
     */
    @Test
    void testEnrolStudentAddsId()
    {
        Course course = new Course("CS101", "Computer Science");
        course.enrolStudent(1);
        assertTrue(course.getEnrolledIdsCSV().contains("1"));
    }

    /**
     * Verifies that enrolling the same student twice does not create
     * duplicates.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create a {@link Course} instance.</li>
     *   <li>Enrol a student with ID "1" twice.</li>
     *   <li>Assert that the CSV string of enrolled IDs contains "1" only once.</li>
     * </ol>
     * </p>
     */
    @Test
    void testEnrolDuplicateStudentDoesNotAddTwice()
    {
        Course course = new Course("CS101", "Computer Science");
        course.enrolStudent(1);
        course.enrolStudent(1);
        assertEquals("1", course.getEnrolledIdsCSV()); // only once
    }

    /**
     * Verifies that removing a student deletes their ID from the course.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create a {@link Course} instance.</li>
     *   <li>Enrol a student with ID "1".</li>
     *   <li>Remove the student with ID "1".</li>
     *   <li>Assert that the CSV string of enrolled IDs is empty.</li>
     * </ol>
     * </p>
     */
    @Test
    void testRemoveStudentRemovesId()
    {
        Course course = new Course("CS101", "Computer Science");
        course.enrolStudent(1);
        course.removeStudent(1);
        assertEquals("", course.getEnrolledIdsCSV());
    }

    /**
     * Verifies that multiple enrolled students are represented correctly
     * in the CSV output.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create a {@link Course} instance.</li>
     *   <li>Enrol students with IDs "1" and "2".</li>
     *   <li>Assert that the CSV string equals "1,2".</li>
     * </ol>
     * </p>
     */
    @Test
    void testMultipleStudentsInCsvOutput()
    {
        Course course = new Course("CS101", "Computer Science");
        course.enrolStudent(1);
        course.enrolStudent(2);
        assertEquals("1,2", course.getEnrolledIdsCSV());
    }

    /**
     * Verifies that an empty course returns an empty CSV string.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create a {@link Course} instance.</li>
     *   <li>Do not enrol any students.</li>
     *   <li>Assert that the CSV string is empty.</li>
     * </ol>
     * </p>
     */
    @Test
    void testEmptyCourseReturnsEmptyCsv()
    {
        Course course = new Course("CS101", "Computer Science");
        assertEquals("", course.getEnrolledIdsCSV());
    }

    /**
     * Verifies that the course summary string includes the code,
     * title and number of enrolled students.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create a {@link Course} instance with code "CS101" and title "Computer Science".</li>
     *   <li>Enrol two students with IDs "1" and "2".</li>
     *   <li>Call {@link Course#toString()}.</li>
     *   <li>Assert that the string contains "CS101", "Computer Science" and "2 enrolled".</li>
     * </ol>
     * </p>
     */
    @Test
    void testCourseSummaryString()
    {
        Course course = new Course("CS101", "Computer Science");
        course.enrolStudent(1);
        course.enrolStudent(2);
        String summary = course.toString();
        assertTrue(summary.contains("CS101"));
        assertTrue(summary.contains("Computer Science"));
        assertTrue(summary.contains("2 enrolled"));
    }
}