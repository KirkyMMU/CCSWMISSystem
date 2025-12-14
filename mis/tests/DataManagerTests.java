import mis.models.*;
import mis.services.DataManager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link DataManager} service class.
 *
 * <p>This test suite verifies the core responsibilities of the DataManager
 * which acts as the central coordination layer of the MIS system. Tests cover:</p>
 * <ul>
 *   <li>Student lifecycle management</li>
 *   <li>Staff lifecycle management</li>
 *   <li>Course lifecycle management</li>
 *   <li>Referential integrity between students and courses</li>
 *   <li>Defensive handling of invalid or non-existent data</li>
 * </ul>
 *
 * <p><b>Design notes:</b></p>
 * <ul>
 *   <li>Each test uses a fresh {@link DataManager} instance to ensure isolation.</li>
 *   <li>Assertions validate both return values and side effects.</li>
 *   <li>Tests are organised using {@link Nested} classes to group related scenarios.</li>
 * </ul>
 */
class DataManagerTests
{
    private DataManager manager;

    /**
     * Creates a fresh {@link DataManager} instance before each test
     * to ensure test independence.
     */
    @BeforeEach
    void setup()
    {
        manager = new DataManager();
    }

    /**
     * Tests related to adding, removing and searching for students
     * within the DataManager.
     */
    @Nested
    class StudentManagementTests
    {
        @Test
        void addStudentSuccessfully()
        {
            Student student = new Student(1, "Alice", "alice@example.com");

            assertAll
            (
                () -> assertTrue(manager.addStudent(student)),
                () -> assertEquals(student, manager.findStudentById(1))
            );
        }

        @Test
        void addDuplicateStudentIdFails()
        {
            Student student1 = new Student(1, "Alice", "alice@example.com");
            Student student2 = new Student(1, "Bob", "bob@example.com");

            manager.addStudent(student1);

            assertFalse(manager.addStudent(student2));
        }

        @Test
        void addNullStudentFails()
        {
            assertFalse(manager.addStudent(null));
        }

        @Test
        void removeStudentAlsoDeEnrolsFromCourse()
        {
            Course course = new Course("CS101", "Computer Science");
            Student student = new Student(1, "Alice", "alice@example.com", course);

            manager.addCourse(course);
            manager.addStudent(student);

            assertAll
            (
                () -> assertTrue(manager.removeStudentById(1)),
                () -> assertFalse(course.getEnrolledIdsCSV().contains("1"))
            );
        }

        @Test
        void removeNonExistentStudentFails()
        {
            assertFalse(manager.removeStudentById(999));
        }

        @Test
        void findNonExistentStudentReturnsNull()
        {
            assertNull(manager.findStudentById(999));
        }

        @Test
        void addStudentAddsCourseIfNotTracked()
        {
            Course course = new Course("CS101", "Computer Science");
            Student student = new Student(1, "Alice", "alice@example.com", course);

            manager.addStudent(student);

            assertEquals(course, manager.findCourseByCode("CS101"));
        }
    }

    /**
     * Tests related to adding, removing and searching for staff
     * members within the DataManager.
     */
    @Nested
    class StaffManagementTests
    {
        @Test
        void addStaffSuccessfully()
        {
            Staff staff = new Staff(1, "Bob", "bob@example.com", "Lecturer", "IT");

            assertAll
            (
                () -> assertTrue(manager.addStaff(staff)),
                () -> assertEquals(staff, manager.findStaffById(1))
            );
        }

        @Test
        void addDuplicateStaffFails()
        {
            Staff staff1 = new Staff(1, "Bob", "bob@example.com", "Lecturer", "IT");
            Staff staff2 = new Staff(1, "Jane", "jane@example.com", "Admin", "HR");

            manager.addStaff(staff1);

            assertFalse(manager.addStaff(staff2));
        }

        @Test
        void removeNonExistentStaffFails()
        {
            assertFalse(manager.removeStaffById(999));
        }
    }
 
    /**
     * Tests related to adding, removing and locating courses
     * within the DataManager.
     */
    @Nested
    class CourseManagementTests
    {
        @Test
        void addCourseSuccessfully()
        {
            Course course = new Course("CS101", "Computer Science");

            assertAll
            (
                () -> assertTrue(manager.addCourse(course)),
                () -> assertEquals(course, manager.findCourseByCode("CS101"))
            );
        }

        @Test
        void addDuplicateCourseCodeFails()
        {
            Course course1 = new Course("CS101", "Computer Science");
            Course course2 = new Course("cs101", "Duplicate Course");

            manager.addCourse(course1);

            assertFalse(manager.addCourse(course2));
        }

        @Test
        void removeCourseNullifiesStudentCourse()
        {
            Course course = new Course("CS101", "Computer Science");
            Student student = new Student(1, "Alice", "alice@example.com", course);

            manager.addCourse(course);
            manager.addStudent(student);

            assertAll
            (
                () -> assertTrue(manager.removeCourseByCode("CS101")),
                () -> assertNull(student.getCourse())
            );
        }

        @Test
        void removeNonExistentCourseFails()
        {
            assertFalse(manager.removeCourseByCode("CS999"));
        }
    }
}