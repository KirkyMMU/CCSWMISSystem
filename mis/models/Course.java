package mis.models;

import mis.services.DataManager;
import java.util.ArrayList;

/**
 * Represents a course in the MIS System.
 * 
 * <p>A Course object encapsulates the unique code and title of a course,
 * along with a list of student IDs enrolled in it. This class provides
 * methods to enrol and remove students, list enrolled students and generate
 * a summary string representation.</p>
 * 
 * <p>Design notes:
 * <ul>
 *   <li>Students are tracked by their unique IDs rather than direct references,
 *       which simplifies persistence and avoids circular dependencies.</li>
 *   <li>The {@link DataManager} can be used to resolve student IDs into
 *       full {@link Student} objects when listing enrolments.</li>
 *   <li>Duplicate enrolments are prevented by checking the ID list before adding 
 *       a new student.</li>
 *   <li>The class provides a CSV export of enrolled IDs for use in reports or 
 *       persistence.</li>
 * </ul>
 * </p>
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
     * 
     * @param code unique course code
     * @param title course title
     */
    public Course(String code, String title)
    {
        this.code = code;
        this.title = title;
    }

    /**
     * Getter for course code.
     * 
     * @return the course code string
     */
    public String getCode()
    {
        return code;
    }

    /**
     * Getter for course title.
     * 
     * @return the course title string 
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Enrols a student by their ID.
     * 
     * <p>If the student is already enrolled, this method does nothing.
     * This prevents duplicate entries in the enrolment list.</p>
     * 
     * @param studentId the unique ID of the student to enrol
     */
    public void enrolStudent(int studentId)
    {
        if(!enrolledStudentIds.contains(studentId))
        {
            enrolledStudentIds.add(studentId);
        }
    }

    /**
     * Lists all enrolled students by resolving their IDs via the DataManager.
     * 
     * <p>This method prints each enrolled student's details to the console.
     * If a student ID cannot be resolved (e.g. student removed), it is skipped.</p>
     * 
     * @param manager the DataManager instance used to look up Student objects
     */
    public void listEnrolled(DataManager manager)
    {
        System.out.println("Students enrolled in " + title + ":");
        for(int id : enrolledStudentIds)
        {
            Student student = manager.findStudentById(id);
            if(student != null)
            {
                System.out.println(student);
            }
        }
    }

    /**
     * Removes a student from the course by their ID.
     * 
     * @param studentId the unique ID of the student to remove
     */
    public void removeStudent(int studentId)
    {
        enrolledStudentIds.remove(Integer.valueOf(studentId));
    }

    /**
     * Returns a comma-separated string of enrolled student IDs.
     * 
     * <p>If no students are enrolled, an empty string is returned.</p>
     * 
     * @return a CSV string of enrolled student IDs
     */
    public String getEnrolledIdsCSV()
    {
        if(enrolledStudentIds.isEmpty())
        {
            return "";
        }
        else
        {
            return enrolledStudentIds.stream()
                                     .map(x -> String.valueOf(x))
                                     .reduce((a, b) -> a + "," + b)
                                     .orElse("");
        }
    }

    /**
     * Returns a string summary of the course including code, title and
     * the number of enrolled students.
     * 
     * @return a formatted string summarising the course
     */
    @Override
    public String toString()
    {
        return code + ": " + title + " (" + enrolledStudentIds.size() + " enrolled)";
    }
}