package mis.services;

import mis.models.*;
import java.util.ArrayList;

/**
 * Service class responsible for managing collections of students, staff and courses.
 * 
 * <p>The DataManager acts as the central hub of the MIS system, providing methods to
 * add, remove, list and search for entities. It ensures consistency across relationships
 * (e.g. students enrolled in courses) and enforces uniqueness of IDs.</p>
 * 
 * <p>Design notes:
 * <ul>
 *   <li>Maintains in-memory collections of {@link Student}, {@link Staff} and
 *       {@link Course} objects.</li>
 *   <li>Provides utility methods for CRUD operations (create, read, update, delete).</li>
 *   <li>Ensures referential integrity; when students are added/removed, their course
 *       enrolements are updated accordingly.</li>
 *   <li>Intended as a lightweight service layer without persistent storage.</li>
 * </ul>
 * </p>
 */
public class DataManager
{
    // List of all students in the system
    private ArrayList<Student> students = new ArrayList<>();

    // List of all staff members in the system
    private ArrayList<Staff> staffMembers = new ArrayList<>();

    // List of all courses offered in the system
    private ArrayList<Course> courses = new ArrayList<>();

    /**
     * Adds a student to the system if their ID is unique.
     * 
     * <p>Behaviour:
     * <ul>
     *   <li>Checks for duplicate IDs before adding.</li>
     *   <li>If the student has a course assigned, enrols them in that course.</li>
     *   <li>If the course is not already tracked by DataManager, adds it to the course
     *       list.</li>
     * </ul>
     * </p>
     * 
     * @param student the Student object to add
     * @return {@code true} if the student was added successfully,
     *         {@code false} if the ID already exists or the student is null
     */
    public boolean addStudent(Student student)
    {
        if (student != null && findStudentById(student.getId()) == null)
        {
            students.add(student);
            // Enrol student in their course if one is assigned
            Course course = student.getCourse();
            if (course != null)
            {
                course.enrolStudent(student.getId());

                // Ensure course is tracked in the system
                if(findCourseByCode(course.getCode()) == null)
                {
                    courses.add(course);
                }
            }
            return true; // Student added successfully
        }
        return false; // Duplicate ID or null student
    }

    /**
     * Removes a student from the system by their ID.
     * 
     * <p>Behaviour:
     * <ul>
     *   <li>Searches for the student by ID.</li>
     *   <li>If found, de-enrols them from their course (if assigned).</li>
     *   <li>Removes the student from the internal list.</li>
     * </ul>
     * </p>
     * 
     * @param id the unique student ID
     * @return {@code true} if the student was removed successfully,
     *         {@code false} if no student with the given ID was found
     */
    public boolean removeStudentById(int id)
    {
        for(int i = 0 ; i < students.size() ; i++)
        {
            Student student = students.get(i);
            if(student.getId() == id)
            {
                // De-enrol from course if assigned
                Course course = student.getCourse();
                if(course != null)
                {
                    course.removeStudent(id);
                }
                students.remove(i);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Finds a student by their unique ID.
     * 
     * @param id the student ID to search for
     * @return the matching Student object or {@code null} if not found
     */
    public Student findStudentById(int id)
    {
        for(Student student : students)
        {
            if(student.getId() == id)
            {
                return student;
            }
        }
        return null;
    }

    /**
     * Lists all students currently in the system.
     * 
     * <p>Outputs each student's details to the console using their
     * {@code toString()} representation. If no students exist, prints a message
     * indicating the list is empty.</p>
     */
    public void listStudents()
    {
        if(students.isEmpty())
        {
            System.out.println("\nNo students found.\n");
        }
        else
        {
            for(Student student : students)
            {
                System.out.println("\n" + student);
            }
        }
    }

    /**
     * Returns the list of all students.
     * 
     * <p>Note: This returns the internal list directly. External code should
     * avoid modifying it directly to maintain data integrity.</p>
     * 
     * @return an {@link ArrayList} of all student objects
     */
    public ArrayList<Student> getStudents()
    {
        return students;
    }

    /**
     * Adds a staff member to the system if their ID is unique.
     * 
     * <p>Behaviour:
     * <ul>
     *   <li>Checks for duplicate IDs before adding.</li>
     *   <li>Null values are rejected to prevent invalid entries.</li>
     *   <li>If successful, the staff member is added to the internal list.</li>
     * </ul>
     * </p>
     * 
     * @param staff the Staff object to add
     * @return {@code true} if the staff member was added successfully,
     *         {@code false} if the ID already exists or the staff is null
     */
    public boolean addStaff(Staff staff)
    {
        if(staff != null && findStaffById(staff.getId()) == null)
        {
            staffMembers.add(staff);
            return true; // Staff added successfully
        }
        return false; // Duplicate ID or null value
    }

    /**
     * Removes a staff member from the system by their ID.
     * 
     * <p>Behaviour:
     * <ul>
     *   <li>Iterates through the staff list to find a matching ID.</li>
     *   <li>If found, removes the staff member from the internal list.</li>
     *   <li>If not found, returns false.</li>
     * </ul>
     * </p>
     * 
     * @param id the unique staff ID
     * @return {@code true} if the staff member was removed successfully,
     *         {@code false} if no staff member with the given ID was found
     */
      public boolean removeStaffById(int id)
      {
           for(int i = 0 ; i < staffMembers.size() ; i++)
           {
               if(staffMembers.get(i).getId() == id)
               {
                   staffMembers.remove(i);
                   return true;
               }
           }
           return false;
      }
    

    /**
     * Finds a staff member by their unique ID.
     * 
     * @param id the staff ID to search for
     * @return the matching Staff object or {@code null} if not found
     */
    public Staff findStaffById(int id)
    {
        for(Staff staff : staffMembers)
        {
            if (staff.getId() == id)
            {
                return staff;
            }
        }
        return null;
    }

    /**
     * Lists all staff members currently in the system.
     * 
     * <p>Outputs each staff member's details to the console using their
     * {@code toString()} representation. If no staff exist, prints a message
     * indicating the list is empty.</p>
     */
    public void listStaff()
    {
        if(staffMembers.isEmpty())
        {
            System.out.println("\nNo staff members found.\n");
        }
        else
        {
            for(Staff staff : staffMembers)
            {
                System.out.println("\n" + staff);
            }
        }
    }

    /**
     * Returns the list of all staff members.
     * 
     * <p>Note: This returns the internal list directly. External code should
     * avoid modifying it directly to maintain data integrity.</p>
     * 
     * @return an {@link ArrayList} of all staff objects
     */
    public ArrayList<Staff> getStaffMembers()
    {
        return staffMembers;
    }

    /**
     * Adds a course to the system.
     * 
     * <p>Behaviour:
     * <ul>
     *   <li>Checks for null values to prevent invalid entries.</li>
     *   <li>Ensures uniqueness of course codes (case-insensitive).</li>
     *   <li>If a course with the same code already exists, rejects the new 
     *       course and prints a message indicating the existing course is used
     *       instead.</li>
     *   <li>If unique, adds the course to the internal list.</li>
     * </ul>
     * </p>
     * 
     * @param course the Course object to add
     * @return {@code true} if the course was added successfully,
     *         {@code false} if the course is null or a duplicate
     */
    public boolean addCourse(Course course)
    {
        if(course != null)
        {
            Course existing = findCourseByCode(course.getCode());
            if(existing == null)
            {
                courses.add(course);
                return true;
            }
            else
            {
                // If code exists, don't add duplicate
                System.out.println("Course with code: " + course.getCode() + " already exists. Using existing course: " + existing.getTitle());
                return false;
            }
        }
        return false;
    }

    /**
     * Returns the list of all courses.
     * 
     * <p>If no courses exist, prints a message to the console and returns
     * the empty list.</p>
     * 
     * @return an {@link ArrayList} of all Course objects
     */
    public ArrayList<Course> getCourses()
    {
        if(!courses.isEmpty())
        {
            return courses;
        }
        else
        {
            System.out.println("\nNo Courses found.\n");
            return courses;
        }
    }

    /**
     * Finds a course by its unique code.
     * 
     * <p>Search is case-insensitive.</p>
     * 
     * @param code the course code to search for
     * @return the matching Course object or {@code null} if not found
     */
    public Course findCourseByCode(String code)
    {
        for (Course course : courses)
        {
            if (course.getCode().equalsIgnoreCase(code))
            {
                return course;
            }
        }
        return null;
    }

    /**
     * Removes a course from the system by its code.
     * 
     * <p>Behaviour:
     * <ul>
     *   <li>Searches for the course by code (case-insensitive).</li>
     *   <li>If found, removes the course from the internal list.</li>
     *   <li>Before removal, iterates through all the students and nullifies
     *       their course reference if they were enrolled in the removed course.</li>
     *   <li>If not found, returns false.</li>
     * </ul>
     * </p>
     * 
     * @param code the course code to search for
     * @return {@code true} if the course was removed successfully,
     *         {@code false} if no course with the given code was found
     */
    public boolean removeCourseByCode(String code)
    {
        for(int i = 0 ; i < courses.size() ; i++)
        {
            Course course = courses.get(i);
            if(course.getCode().equalsIgnoreCase(code))
            {
                // Nullify course for all students enrolled in this course
                for(Student student : students)
                {
                    if(student.getCourse() != null && student.getCourse().getCode().equalsIgnoreCase(code))
                    {
                        student.setCourse(null);
                    }
                }
                courses.remove(i);
                return true;
            }
        }
        return false;
    }
}