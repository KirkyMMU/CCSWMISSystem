import mis.models.*;
import mis.services.DataManager;
import mis.util.DataIO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration-style tests for {@link DataIO} veriying persistence
 * of MIS system data to and from text files.
 * 
 * <p>Tests use temporary files provided by JUnit to avoid polluting
 * the project directory. Each test saves a {@link DataManager} state
 * to disk and reloads it, asserting that data integrity and relationships
 * are preserved.</p>
 *
 * <p><b>Design notes:</b></p>
 * <ul>
 *   <li>Tests are grouped using {@link Nested} classes for clarity.</li>
 *   <li>Assertions focus on restored state rather than exact file formatting.</li>
 *   <li>Error handling scenarios are included to verify gracefulness.</li>
 * </ul>
 */
class DataIOTests
{
    @TempDir
    Path tempDir;

    /**
     * Tests related to student persistence.
     */
    @Nested
    class StudentPersistenceTests
    {
        /**
         * Verifies that a student with grades, attendance and a course
         * is correctly saved and restored.
         */
        @Test
        void saveAndLoadRestoresStudentData()
        {
            DataManager original = new DataManager();

            Course course = new Course("CS101", "Computer Science");
            Student student = new Student(1, "Alice", "alice@example.com", course);
            student.addGrade(7);
            student.addGrade(9);
            student.setAttendancePercentage(95.0);

            original.addCourse(course);
            original.addStudent(student);

            Path file = tempDir.resolve("student.txt");

            DataIO.saveToFile(original, file.toString());

            DataManager loaded = new DataManager();
            DataIO.loadFromFile(loaded, file.toString());

            Student loadedStudent = loaded.findStudentById(1);

            assertAll
            (
                () -> assertNotNull(loadedStudent),
                () -> assertEquals("Alice", loadedStudent.getName()),
                () -> assertEquals(8.0, loadedStudent.calculateAverage()),
                () -> assertEquals(95.0, loadedStudent.getAttendancePercentage()),
                () -> assertEquals("CS101", loadedStudent.getCourse().getCode())
            );
        }
    }

    /**
     * Tests related to staff persistence.
     */
    @Nested
    class StaffPersistenceTests
    {
        /**
         * Verifies that staff role, department and tasks
         * are correctly saved and restored.
         */
        @Test
        void saveAndLoadRestoresStaffTasks()
        {
            DataManager original = new DataManager();

            Staff staff = new Staff(1, "Bob", "bob@example.com", "Lecturer", "IT");
            staff.getTasks().add("Prepare lesson plan");
            staff.getTasks().add("Mark coursework");

            original.addStaff(staff);

            Path file = tempDir.resolve("staff.txt");

            DataIO.saveToFile(original, file.toString());

            DataManager loaded = new DataManager();
            DataIO.loadFromFile(loaded, file.toString());

            Staff loadedStaff = loaded.findStaffById(1);

            assertAll
            (
                () -> assertNotNull(loadedStaff),
                () -> assertEquals("Bob", loadedStaff.getName()),
                () -> assertEquals("Lecturer", loadedStaff.getRole()),
                () -> assertEquals("IT", loadedStaff.getDepartment()),
                () -> assertTrue(loadedStaff.getTasks().contains("Prepare lesson plan")),
                () -> assertTrue(loadedStaff.getTasks().contains("Mark coursework"))
            );
        }
    }

    /**
     * Tests related to course persistence.
     */
    @Nested
    class CoursePersistenceTests
    {
        /**
         * Verifies that course enrolments are correctly saved and restored.
         */
        @Test
        void saveAndLoadRestoresCourseEnrolments()
        {
            DataManager original = new DataManager();

            Course course = new Course("CS101", "Computer Science");
            Student student = new Student(1, "Alice", "alice@example.com", course);

            original.addCourse(course);
            original.addStudent(student);

            Path file = tempDir.resolve("course.txt");

            DataIO.saveToFile(original, file.toString());

            DataManager loaded = new DataManager();
            DataIO.loadFromFile(loaded, file.toString());

            Course loadedCourse = loaded.findCourseByCode("CS101");

            assertAll
            (
                () -> assertNotNull(loadedCourse),
                () -> assertTrue(loadedCourse.getEnrolledIdsCSV().contains("1"))
            );
        }
    }

    /**
     * Tests related to error handling and gracefulness.
     */
    @Nested
    class ErrorHandlingTests
    {
        /**
         * Verifies that loading from an empty file does not crash
         * and leaves the DataManager empty.
         */
        @Test
        void loadFromEmptyFileDoesNotCrash() throws Exception
        {
            Path file = tempDir.resolve("empty.txt");
            file.toFile().createNewFile(); // create empty file

            DataManager loaded = new DataManager();
            DataIO.loadFromFile(loaded, file.toString());

            assertAll
            (
                () -> assertTrue(loaded.getStudents().isEmpty()),
                () -> assertTrue(loaded.getStaffMembers().isEmpty()),
                () -> assertTrue(loaded.getCourses().isEmpty())
            );
        }

        /**
         * Verifies that loading from a missing file does not crash
         * and leaves the DataManager empty.
         */
        @Test
        void loadFromMissingFileDoesNotCrash()
        {
            Path file = tempDir.resolve("missing.txt");

            DataManager loaded = new DataManager();
            DataIO.loadFromFile(loaded, file.toString());

            assertAll(
                () -> assertTrue(loaded.getStudents().isEmpty()),
                () -> assertTrue(loaded.getStaffMembers().isEmpty()),
                () -> assertTrue(loaded.getCourses().isEmpty())
            );
        }
    }
}