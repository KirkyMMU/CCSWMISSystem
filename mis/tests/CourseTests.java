import mis.models.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CourseTests
{
    @Test
    void testEnrolStudentAddsId()
    {
        Course c = new Course("CS101", "Computer Science");
        c.enrolStudent(1);
        assertTrue(c.getEnrolledIdsCSV().contains("1"));
    }

    @Test
    void testEnrolDuplicateStudentDoesNotAddTwice()
    {
        Course c = new Course("CS101", "Computer Science");
        c.enrolStudent(1);
        c.enrolStudent(1);
        assertEquals("1", c.getEnrolledIdsCSV()); // only once
    }
}