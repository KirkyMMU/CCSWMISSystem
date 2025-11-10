package mis.services;

import mis.models.*;
import java.util.ArrayList;

/**
 * Service class responsible for managing collections of students, staff and courses.
 * Provides methods to add, remove, list and search entities in the MIS System.
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
     * Also enrols the student in their assigned course if provided.
     * @param student The Student object to add
     * @return true if the student was added successfully, false if the ID already exists
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
            }
            return true; // Student added successfully
        }
        return false; // Duplicate ID or null student
    }

    /**
     * Removes a student from the system by their ID.
     * @param id The unique student ID
     * @return true if removed successfully, false if not found
     */
      public boolean removeStudentById(int id)
      {
           for(int i = 0 ; i < students.size() ; i++)
           {
               if(students.get(i).getId() == id)
               {
                   students.remove(i);
                   return true;
               }
           }
           return false;
      }
     

    /**
     * Finds a student by their unique ID.
     * @param id The student ID to search for
     * @return The matching Student object, or null if not found
     */
    public Student findStudentById(int id)
    {
        for(Student s : students)
        {
            if(s.getId() == id)
            {
                return s;
            }
        }
        return null;
    }

    /**
     * Lists all students currently in the system.
     * Outputs to the console.
     */
    public void listStudents()
    {
        if(students.isEmpty())
        {
            System.out.println("\nNo students found.\n");
        }
        else
        {
            for(Student s : students)
            {
                System.out.println("\n" + s + "\n");
            }
        }
    }

    /**
     * Adds a staff member to the system if their ID is unique.
     * @param staff The Staff object to add
     * @return true if the staff was added successfully, false if the ID already exists
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
     * @param id The unique staff ID
     * @return true if removed successfully, false if not found
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
     * @param id The staff ID to search for
     * @return The matching Staff object, or null if not found
     */
    public Staff findStaffById(int id)
    {
        for(Staff s : staffMembers)
        {
            if (s.getId() == id)
            {
                return s;
            }
        }
        return null;
    }

    /**
     * Lists all staff members currently in the system.
     * Outputs to the console.
     */
    public void listStaff()
    {
        if(staffMembers.isEmpty())
        {
            System.out.println("\nNo staff members found.\n");
        }
        else
        {
            for(Staff s : staffMembers)
            {
                System.out.println(s);
            }
        }
    }

    /**
     * Adds a course to the system.
     * @param course The Course object to add
     */
    public boolean addCourse(Course course)
    {
        if(course != null && findCourseByCode(course.getCode()) == null)
        {
            courses.add(course);
            return true;
        }
        return false;
    }

    /**
     * Returns the list of all courses.
     * @return ArrayList of Course objects
     */
    public ArrayList<Course> getCourses()
    {
        return courses;
    }

    /**
     * Finds a course by its unique code.
     * @param code The course code to search for
     * @return The matching Course object, or null if not found
     */
    public Course findCourseByCode(String code)
    {
        for (Course c : courses)
        {
            if (c.getCode().equalsIgnoreCase(code))
            {
                return c;
            }
        }
        return null;
    }

}