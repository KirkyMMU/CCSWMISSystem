import mis.models.*;
import mis.services.*;
import mis.util.DataIO;
import org.junit.jupiter.api.*;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public class PersistenceTests
{
    private static final String TEST_FILE = "test_data.txt";

    @Test
    void testSaveAndLoadStudents()
    {
        DataManager manager = new DataManager();
        Student s = new Student(1, "Alice", "alice@example.com");
        s.addGrade(7);
        manager.addStudent(s);

        DataIO.saveToFile(manager, TEST_FILE);

        DataManager manager2 = new DataManager();
        DataIO.loadFromFile(manager2, TEST_FILE);

        assertNotNull(manager2.findStudentById(1));
        assertEquals(7.0, manager2.findStudentById(1).calculateAverage());
    }

    @AfterAll
    static void cleanup()
    {
        new File(TEST_FILE).delete();
    }
}