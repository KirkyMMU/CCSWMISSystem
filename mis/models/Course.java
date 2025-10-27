package mis.models;

import mis.services.DataManager;
import java.util.ArrayList;

/**
 * Represents a course in the MIS System.
 * Tracks enrolled student IDs and provides listing functionality.
 */
public class Course
{
    // Unique course code (e.g. CS101)
    private String code;

    // Course title (e.g. Introduction to Programming)
    private String title;

    // List of student IDs enrolled in this course
    private ArrayList<Integer> enrolledStudentIds = new ArrayList<>();

    /**
     * Constructor to initialise a Course object.
     * @param code Unique course code
     * @param title Course title
     */
    public Course(String code, String title)
    {
        this.code = code;
        this.title = title;
    }

    // Getter for course code
    public String getCode()
    {
        return code;
    }

    // Getter for course title
    public String getTitle()
    {
        return title;
    }

    /**
     * Enrols a student by their ID.
     * Prevents duplicate enrolment
     * @param studentId ID of the student to enrol
     */
    public void enrolStudent(int studentId)
    {
        if(!enrolledStudentIds.contains(studentId))
        {
            enrolledStudentIds.add(studentId);
        }
    }

    /**
     * Lists all enrolled students using the DataManager to resolve IDs.
     * @param dm Reference to the DataManager for student lookup
     */
    public void listEnrolled(DataManager dm)
    {
        System.out.println("Students enrolled in: " + title + ":");
        for(int id : enrolledStudentIds)
        {
            Student s = dm.findStudentById(id);
            if(s != null)
            {
                System.out.println(s);
            }
        }
    }

    /**
     * Returns a string summary of the course.
     */
    @Override
    public String toString()
    {
        return code + ": " + title + " (" + enrolledStudentIds.size() + " enrolled)";
    }
}
