import mis.models.*;
import mis.services.DataManager;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class DataManagerTests
{
    private DataManager manager;

    @BeforeEach
    void setup()
    {
        manager = new DataManager();
    }

    @Test
    void testAddStudentSuccessfully()
    {
        Student s = new Student(1, "Alice", "alice@example.com");
        assertTrue(manager.addStudent(s));
        assertEquals(s, manager.findStudentById(1));
    }

    @Test
    void testAddDuplicateStudentIdFails()
    {
        Student s1 = new Student(1, "Alice", "alice@example.com");
        Student s2 = new Student(1, "Bob", "bob@example.com");
        manager.addStudent(s1);
        assertFalse(manager.addStudent(s2));
    }

    @Test
    void testRemoveStudentAlsoDeEnrolsFromCourse()
    {
        Course c = new Course("CS101", "Computer Science");
        Student s = new Student(1, "ALice", "alice@example.com", c);
        manager.addCourse(c);
        manager.addStudent(s);

        assertTrue(manager.removeStudentById(1));
        assertFalse(c.getEnrolledIdsCSV().contains("1"));
    }

    @Test
    void testFindNonExistentStudentReturnsNull()
    {
        assertNull(manager.findStudentById(999));
    }
}