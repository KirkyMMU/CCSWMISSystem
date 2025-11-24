import mis.models.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Student} class.
 * 
 * <p>These tests verify the behaviour of grade management, ensuring
 * that:
 * <ul>
 *   <li>Valid GCSE-style grades (1-9) are accepted.</li>
 *   <li>Invalid grades below the range are rejected.</li>
 *   <li>Invalid grades above the range are rejected.</li>
 * </ul>
 * </p>
 * 
 * <p>Design notes:
 * <ul>
 *   <li>Uses JUnit 5 annotations (@Test) and assertions from 
 *       {@link org.junit.jupiter.api.Assertions}.</li>
 *   <li>Each test creates a fresh {@link Student} instance to ensure
 *       independence.</li>
 *   <li>Focusses specifically on {@link Student#addGrade(int)} validation
 *       logic.</li>
 * </ul>
 * <p>
 */
public class StudentTests
{
    /**
     * Verifies that adding a valid grade succeeds.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create a {@link Student} instance.</li>
     *   <li>Add a grade within the valid range (7).</li>
     *   <li>Assert that the method returns true, indicating success.</li>
     * </ol>
     * </p>
     */
    @Test
    void testAddValidGrade()
    {
        Student s = new Student(1, "Alice", "alice@example.com");
        assertTrue(s.addGrade(7));
    }

    /**
     * Verifies that adding a grade below the valid range fails.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create a {@link Student} instance.</li>
     *   <li>Attempt to add a grade below the valid range (-1).</li>
     *   <li>Assert that the method returns false, indicating rejection.</li>
     * </ol>
     * </p>
     */
    @Test
    void testAddInvalidGradeBelowRange()
    {
        Student s = new Student(1, "Alice", "alice@example.com");
        assertFalse(s.addGrade(-1));
    }

    /**
     * Verifies that adding a grade above the valid range fails.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create a {@link Student} instance.</li>
     *   <li>Attempt to add a grade above the valid range (15).</li>
     *   <li>Assert that the method returns false, indicating rejection.</li>
     * </ol>
     * </p>
     */
    @Test
    void testAddInvalidGradeAboveRange()
    {
        Student s = new Student(1, "Alice", "alice@example.com");
        assertFalse(s.addGrade(15));
    }


    /**
     * Verifies that calculating the average grade works correctly
     * when multiple valid grades are present.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create a {@link Student} instance.</li>
     *   <li>Add two valid grades (7 and 9).</li>
     *   <li>Call {@link Student#calculateAverage()}.</li>
     *   <li>Assert that the result is 8.0, since (7 + 9) / 2 = 8.0.</li>
     * </ol>
     * </p>
     */
    @Test
    void testCalculateAverageWithGrades()
    {
        Student s = new Student(1, "Alice", "alice@example.com");
        s.addGrade(7);
        s.addGrade(9);
        assertEquals(8.0, s.calculateAverage());
    }

    /**
     * Verifies that calculating the average with no grades returns 0.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create a new {@link Student} instance with no grades.</li>
     *   <li>Call {@link Student#calculateAverage()}.</li>
     *   <li>Assert that the result is 0.0, indicating no grades
     *       recorded.</li>
     * </ol>
     * </p>
     */
    @Test
    void testCalculateAverageNoGradesReturnsZero()
    {
        Student s = new Student(1, "Alice", "alice@example.com");
        assertEquals(0.0, s.calculateAverage());
    }

    /**
     * Verifies that reassigning a student to a new course updates
     * enrolment correctly.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create two {@link Course} instances (CS101 and BUS123).</li>
     *   <li>Create a {@link Student} instance initially enrolled in
     *       CS101.</li>
     *   <li>Reassign the student to BUS123 using {@link Student#setCourse(Course)}.</li>
     *   <li>Assert that the student's course is updated to BUS123.</li>
     *   <li>Assert that the student is removed from CS101's enrolment list.</li>
     *   <li>Assert that the student is added to BUS123's enrolment list.</li>
     * </ol>
     * </p>
     */
    @Test
    void testSetCourseReassignsCorrectly()
    {
        Course c1 = new Course("CS101", "Computer Science");
        Course c2 = new Course("BUS123", "Business");
        Student s = new Student(1, "Alice", "alice@example.com", c1);

        s.setCourse(c2);
        assertEquals("BUS123", s.getCourse().getCode());
        assertFalse(c1.getEnrolledIdsCSV().contains("1"));
        assertTrue(c2.getEnrolledIdsCSV().contains("1"));
    }
}