import mis.models.*;
import mis.services.*;
import mis.util.DataIO;
import org.junit.jupiter.api.*;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for persistence functionality using {@link DataIO}.
 * 
 * <p>These tests verify that data managed by {@link DataManager} can be
 * saved to a file and subsequently reloaded correctly, ensuring that
 * student information and grades are preserved across sessions.</p>
 * 
 * <p>Design notes:
 * <ul>
 *   <li>Uses a temporary test file ("test_data.txt") for persistence checks.</li>
 *   <li>Validates both structural persistence (student exists after reload) and
 *       data integrity (grades are preserved).</li>
 *   <li>Includes cleanup logic to remove the test file after all tests run
 *       preventing pollution of the working directory.</li>
 * </ul>
 * </p>
 */
public class PersistenceTests
{
    // Temporary file used for testing persistence
    private static final String TEST_FILE = "test_data.txt";

    /**
     * Verifies that saving and loading students preserves their data correctly.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create a new {@link DataManager} and add a student with a grade.</li>
     *   <li>Save the DataManager state to a file using {@link DataIO#saveToFile}.</li>
     *   <li>Create a fresh DataManager and load the state from the file.</li>
     *   <li>Assert that the student exists in the reloaded manager.</li>
     *   <li>Assert that the student's average grade is preserved.</li>
     * </ol>
     * </p>
     */
    @Test
    void testSaveAndLoadStudents()
    {
        // Create initial manager and add a student with a grade
        DataManager manager = new DataManager();
        Student s = new Student(1, "Alice", "alice@example.com");
        s.addGrade(7);
        manager.addStudent(s);

        // Save state to file
        DataIO.saveToFile(manager, TEST_FILE);

        // Load state into a new manager
        DataManager manager2 = new DataManager();
        DataIO.loadFromFile(manager2, TEST_FILE);

        // Verify student exists and grade data is preserved
        assertNotNull(manager2.findStudentById(1));
        assertEquals(7.0, manager2.findStudentById(1).calculateAverage());
    }

    /**
     * Cleanup method executed after all tests.
     * 
     * <p>Deletes the temporary test file to ensure no leftover files remain
     * after the test suite completes.</p>
     */
    @AfterAll
    static void cleanup()
    {
        new File(TEST_FILE).delete();
    }
}