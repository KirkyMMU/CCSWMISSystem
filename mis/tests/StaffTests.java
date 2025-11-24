import mis.models.*;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Staff} class.
 * 
 * <p>These tests verify the behaviour of task assignment for staff
 * members, ensuring that deadlines are validated correctly and tasks
 * are refelcted in the staff member's string representation.</p>
 * 
 * <p>Design notes:
 * <ul>
 *   <li>Uses JUnit 5 annotations (@Test) and assertions from
 *       {@link org.junit.jupiter.api.Assertions}.</li>
 *   <li>Focusses on the {@link Staff#assignTask(String, LocalDate)} 
 *       method and its impact on {@link Staff#toString()} output.</li>
 *   <li>Tests both valid and invalid deadline scenarios.</li>
 * </ul>
 * </p>
 */
public class StaffTests
{
    /**
     * Verifies that assigning a valid task with a future deadline succeeds.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create a {@link Staff} instance.</li>
     *   <li>Assign a task with a deadline 7 days in the future.</li>
     *   <li>Assert that the staff member's string representation contains
     *       the task description.</li>
     * </ol>
     * </p>
     */
    @Test
    void testAssignValidTask()
    {
        Staff st = new Staff(101, "Bob", "bob@example.com", "Lecturer", "IT");
        LocalDate deadline = LocalDate.now().plusDays(7);
        st.assignTask("Prepare lesson plan", deadline);

        // Verify that the task appears in the staff member's details
        assertTrue(st.toString().contains("Prepare lesson plan"));
    }

    /**
     * Verifies that assigning a task with a past deadline fails.
     * 
     * <p>Steps:
     * <ol>
     *   <li>Create a {@link Staff} instance.</li>
     *   <li>Attempt to assign a task with a deadline in the past.</li>
     *   <li>Assert that the staff memeber's string representation shows
     *       "None" for the current task, indicating no assignment.</li>
     * </ol>
     * </p>
     */
    @Test
    void testAssignTaskPastDeadlineFails()
    {
        Staff st = new Staff(101, "Bob", "bob@example.com", "Lecturer", "IT");
        LocalDate pastDate = LocalDate.now().minusDays(1);
        st.assignTask("Review syllabus", pastDate);

        // Verify that no task was assigned
        assertTrue(st.toString().contains("None"));
    }
}