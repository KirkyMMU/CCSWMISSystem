package mis.models;

import java.util.ArrayList;

/**
 * Represents a student in the MIS system.
 * Inherits common personal details from the abstract Person class.
 * Each student is associated with a specific Course object and stores a list of GCSE-style grades (1–9).
 */
public class Student extends Person
{
    // The course the student is enrolled in
    private Course course;

    // List of grades (1-9) following GCSE standards
    private ArrayList<Integer> grades = new ArrayList<>();

    /**
     * Constructor to initialise a Student object.
     * @param id Unique student ID
     * @param name Full name
     * @param email Email address
     */
    public Student(int id, String name, String email)
    {
        super(id, name, email); // Calls the constructor of Person
        this.course = null; // If student doesn't have course initially
    }

    /**  
     * Optional constructor with Course
     * @param id Unique student ID
     * @param name Full name
     * @param email Email address
     * @param course Student's course
     */
    public Student(int id, String name, String email, Course course)
    {
        super(id, name, email); // Calls the constructor of Person
        
        // Enrol student onto course
        this.course = course;
        if(course != null)
        {
            course.enrolStudent(id);
        }
    }
    // Getter for course
    public Course getCourse()
    {
        return course;
    }

    // Setter for course
    public void setCourse(Course course)
    {
        // De-enrol from old course if exists
        if(this.course != null)
        {
            this.course.removeStudent(getId());
        }
        this.course = course;
        if(course != null)
        {
            course.enrolStudent(getId());
        }
    }

    /**
     * Adds a grade to the student's record.
     * Accepts only valid GCSE grades (1 to 9).
     * @param grade Grade to add
     */
    public boolean addGrade(int grade)
    {
        if(grade >= 1 && grade <= 9)
        {
            grades.add(grade);
            return true;
        }
        else
        {
            System.out.println("Invalid grade " + grade + ". Grade must be between 1 and 9");
            return false;
        }
    }

    /**
     * Calculates the average grade.
     * Returns 0 if no grades are recorded.
     * @return Average grade as a double
     */
    public double calculateAverage()
    {
        if(grades.isEmpty())
        {
            return 0;
        }
        int total = 0;
        for(int g : grades)
        {
            total += g;
        }
        return total / (double) grades.size();
    }

    /**
     * Returns a string representation of the student.
     * Includes inherited personal details and course info.
     */
    @Override
    public String toString()
    {
        String courseInfo;
        if(course != null)
        {
            courseInfo = course.getCode();
        }
        else
        {
            courseInfo = "No course assigned.";
        }
        return super.toString() + " | Course: " + courseInfo;
    }
}