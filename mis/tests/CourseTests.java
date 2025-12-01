import mis.models.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Course} class.
 * 
 * <p>These tests verify the behaviour of student enrolment
 * within a course, ensuring that IDs are added correctly and
 * duplicates are prevented.</p>
 * 
 * <p>Design notes:
 * <ul>
 *   <li>Uses JUnit 5 annotations (@Test) and assertions from
 *       {@link org.junit.jupiter.api.Assertions}.</li>
 *   <li>Each test method is independent and creates its own
 *       Course instance.</li>
 *   <li>Tests focus on enrolment logic and CSV ouput formatting.</li>
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
}