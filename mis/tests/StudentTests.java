import mis.models.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class StudentTests
{
    @Test
    void testAddValidGrade()
    {
        Student s = new Student(1, "Alice", "alice@example.com");
        assertTrue(s.addGrade(7));
    }

    @Test
    void testAddInvalidGradeBelowRange()
    {
        Student s = new Student(1, "Alice", "alice@example.com");
        assertFalse(s.addGrade(-1));
    }

    @Test
    void testAddInvalidGradeAboveRange()
    {
        Student s = new Student(1, "Alice", "alice@example.com");
        assertFalse(s.addGrade(15));
    }

    @Test
    void testCalculateAverageWithGrades()
    {
        Student s = new Student(1, "Alice", "alice@example.com");
        s.addGrade(7);
        s.addGrade(9);
        assertEquals(8.0, s.calculateAverage());
    }

    @Test
    void testCalculateAverageNoGradesReturnsZero()
    {
        Student s = new Student(1, "Alice", "alice@example.com");
        assertEquals(0.0, s.calculateAverage());
    }

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