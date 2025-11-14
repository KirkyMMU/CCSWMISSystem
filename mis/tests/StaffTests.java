import mis.models.*;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class StaffTests
{
    @Test
    void testAssignValidTask()
    {
        Staff st = new Staff(101, "Bob", "bob@example.com", "Lecturer", "IT");
        LocalDate deadline = LocalDate.now().plusDays(7);
        st.assignTask("Prepare lesson plan", deadline);
        assertTrue(st.toString().contains("Prepare lesson plan"));
    }

    @Test
    void testAssignTaskPastDeadlineFails()
    {
        Staff st = new Staff(101, "Bob", "bob@example.com", "Lecturer", "IT");
        LocalDate pastDate = LocalDate.now().minusDays(1);
        st.assignTask("Review syllabus", pastDate);
        assertTrue(st.toString().contains("None")); // task not assigned
    }
}