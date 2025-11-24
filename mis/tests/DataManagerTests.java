import mis.models.*;
import mis.services.DataManager;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link DataManager} service class.
 * 
 * <p>These tests verify core student management functionality, including:
 * <ul>
 *   <li>Adding students with unique IDs.</li>
 *   <li>Preventing duplicate student IDs.</li>
 *   <li>Removing students and ensuring they are de-enrolled from courses.</li>
 *   <li>Searching for students by ID, including handling of non-existent
 *       IDs.</li>
 * </ul>
 * </p>
 * 
 * <p>Design notes:
 * <ul>
 *   <li>Uses JUnit 5 annotations (@Test, @BeforeEach).</li>
 *   <li>Each test is independent and uses a fresh {@link DataManager}
 *       instance.</li>
 *   <li>Assertions validate both direct outcomes (return values) and side
 *       effects (e.g. course enrolment updates).</li>
 * </ul>
 * </p>
 */
public class DataManagerTests
{
    // DataManager instance created before each test
    private DataManager manager;

    /**
     * Setup method created before each test.
     * 
     * <p>Ensures a clean DataManager instance so tests remain independent
     * and do not interfere with one another.</p>
     */
    @BeforeEach
    void setup()
    {
        manager = new DataManager();
    }

    /**
     * Verifies that adding a student with a unique ID succeeds.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create a new Student.</li>
     *   <li>Add the student to the DataManager.</li>
     *   <li>Assert that the addition returns true.</li>
     *   <li>Assert that the student can be found by ID.</li>
     * </ol>
     * </p>
     */
    @Test
    void testAddStudentSuccessfully()
    {
        Student s = new Student(1, "Alice", "alice@example.com");
        assertTrue(manager.addStudent(s));
        assertEquals(s, manager.findStudentById(1));
    }

    /**
     * Verifies that adding a student with a duplicate ID fails.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create two Students with the same ID.</li>
     *   <li>Add the first student successfully.</li>
     *   <li>Attempt to add the second student.</li>
     *   <li>Assert that the addition returns false.</li>
     * </ol>
     * </p>
     */
    @Test
    void testAddDuplicateStudentIdFails()
    {
        Student s1 = new Student(1, "Alice", "alice@example.com");
        Student s2 = new Student(1, "Bob", "bob@example.com");
        manager.addStudent(s1);
        assertFalse(manager.addStudent(s2));
    }

    /**
     * Verifies that removing a student also de-enrols them from their
     * course.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create a Course and a Student enrolled in it.</li>
     *   <li>Add both to the DataManager.</li>
     *   <li>Remove the student by ID.</li>
     *   <li>Assert that the removal returns true.</li>
     *   <li>Assert that the student's ID no longer appears in the course
     *       enrolment list.</li>
     * </ol>
     * </p>
     */
    @Test
    void testRemoveStudentAlsoDeEnrolsFromCourse()
    {
        Course c = new Course("CS101", "Computer Science");
        Student s = new Student(1, "Alice", "alice@example.com", c);
        manager.addCourse(c);
        manager.addStudent(s);

        assertTrue(manager.removeStudentById(1));
        assertFalse(c.getEnrolledIdsCSV().contains("1"));
    }

    /**
     * Verifies that searching for a non-existent student ID returns null.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Search for a student ID that does not exist in the DataManager.</li>
     *   <li>Assert that the result is null.</li>
     * </ol>
     * </p>
     */
    @Test
    void testFindNonExistentStudentReturnsNull()
    {
        assertNull(manager.findStudentById(999));
    }
}