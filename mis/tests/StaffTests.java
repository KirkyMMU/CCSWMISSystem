import mis.models.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Staff} class.
 * 
 * <p>This test suite verifies staff-specific behaviour within the MIS system,
 * focusing primarily on task management and staff metadata including:</p>
 * <ul>
 *   <li>Task assignment with valid and invalid deadlines</li>
 *   <li>Handling of multiple tasks</li>
 *   <li>Task removal behaviour</li>
 *   <li>CSV output for persistence</li>
 *   <li>Role and department management</li>
 * </ul>
 *
 * <p><b>Design notes:</b></p>
 * <ul>
 *   <li>Each test creates its own {@link Staff} instance to ensure isolation.</li>
 *   <li>Deadline validation is tested using relative dates.</li>
 *   <li>Assertions focus on observable behaviour rather than internal state.</li>
 *   <li>Tests are organised using {@link Nested} classes to group related scenarios.</li>
 * </ul>
 */
class StaffTests
{
    /**
     * Tests related to assigning tasks to staff members
     * and validating task deadlines.
     */
    @Nested
    class TaskAssignmentTests
    {
        @Test
        void assignValidTaskSucceeds()
        {
            Staff staff = new Staff(101, "Bob", "bob@example.com", "Lecturer", "IT");
            LocalDate deadline = LocalDate.now().plusDays(7);

            staff.assignTask("Prepare lesson plan", deadline);

            assertTrue(staff.toString().contains("Prepare lesson plan"));
        }

        @Test
        void assignTaskWithPastDeadlineFails()
        {
            Staff staff = new Staff(101, "Bob", "bob@example.com", "Lecturer", "IT");
            LocalDate pastDate = LocalDate.now().minusDays(1);

            staff.assignTask("Review syllabus", pastDate);

            assertTrue(staff.toString().contains("None"));
        }

        @Test
        void assignTaskBeyondNinetyDaysFails()
        {
            Staff staff = new Staff(101, "Bob", "bob@example.com", "Lecturer", "IT");
            LocalDate farFuture = LocalDate.now().plusDays(120);

            staff.assignTask("Plan next term", farFuture);

            assertTrue(staff.toString().contains("None"));
        }

        @Test
        void assignMultipleTasksSucceeds()
        {
            Staff staff = new Staff(101, "Bob", "bob@example.com", "Lecturer", "IT");

            staff.assignTask("Prepare lesson plan", LocalDate.now().plusDays(7));
            staff.assignTask("Mark coursework", LocalDate.now().plusDays(14));

            String output = staff.toString();

            assertAll
            (
                () -> assertTrue(output.contains("Prepare lesson plan")),
                () -> assertTrue(output.contains("Mark coursework"))
            );
        }
    }

    /**
     * Tests related to removing tasks from a staff member.
     */
    @Nested
    class TaskRemovalTests
    {
        @Test
        void removeExistingTaskSucceeds()
        {
            Staff staff = new Staff(101, "Bob", "bob@example.com", "Lecturer", "IT");
            LocalDate deadline = LocalDate.now().plusDays(7);

            staff.assignTask("Prepare lesson plan", deadline);
            String task = staff.getTasks().get(0);

            boolean removed = staff.removeTask(task);

            assertAll
            (
                () -> assertTrue(removed),
                () -> assertTrue(staff.toString().contains("None"))
            );
        }

        @Test
        void removeNonExistentTaskFails()
        {
            Staff staff = new Staff(101, "Bob", "bob@example.com", "Lecturer", "IT");

            boolean removed = staff.removeTask("Non-existent task");

            assertFalse(removed);
        }
    }

    /**
     * Tests related to task persistence and output formatting.
     */
    @Nested
    class TaskOutputTests
    {
        @Test
        void getTasksCSVReturnsCommaSeparatedTasks()
        {
            Staff staff = new Staff(101, "Bob", "bob@example.com", "Lecturer", "IT");

            staff.assignTask("Prepare lesson plan", LocalDate.now().plusDays(7));
            staff.assignTask("Mark coursework", LocalDate.now().plusDays(14));

            String csv = staff.getTasksCSV();

            assertAll
            (
                () -> assertTrue(csv.contains("Prepare lesson plan")),
                () -> assertTrue(csv.contains("Mark coursework")),
                () -> assertTrue(csv.contains(","))
            );
        }
    }

    /**
     * Tests related to staff metadata such as role
     * and department.
     */
    @Nested
    class StaffMetadataTests
    {
        @Test
        void setRoleAndDepartmentUpdatesValues()
        {
            Staff staff = new Staff(101, "Bob", "bob@example.com", "Lecturer", "IT");

            staff.setRole("Senior Lecturer");
            staff.setDepartment("Computer Science");

            assertAll
            (
                () -> assertEquals("Senior Lecturer", staff.getRole()),
                () -> assertEquals("Computer Science", staff.getDepartment())
            );
        }
    }
}