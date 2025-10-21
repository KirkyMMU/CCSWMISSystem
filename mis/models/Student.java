package mis.models;

import java.util.ArrayList;

/**
 * Represents a student in the MIS system.
 * Inherits common personal details from the abstract Person class.
 * Stores course information and GCSE-style grades (1-9)
 */
public class Student extends Person
{
    // The course the student is enrolled in (e.g. Computing, Business)
    private String course;

    // List of grades (1-9) following GCSE standards
    private ArrayList<Integer> grades = new ArrayList<>();

    /**
     * Constructor to initialise a Student object.
     * @param id Unique student ID
     * @param name Full name
     * @param email Email address
     * @param course Course name
     */
    public Student(int id, String name, String email, String course)
    {
        super(id, name, email); // Calls the constructor of Person
        this.course = course;
    }

    // Getter for course
    public String getCourse()
    {
        return course;
    }

    // Setter for course
    public void setCourse(String course)
    {
        this.course = course;
    }

    /**
     * Adds a grade to the student's record.
     * Accepts only valid GCSE grades (1 to 9).
     * @param grade Grade to add
     */
    public void addGrade(int grade)
    {
        if (grade >= 0 && grade <= 9)
        {
            grades.add(grade);
        }
    }

    /**
     * Calculates the average grade.
     * Returns 0 if no grades are recorded.
     * @return Average grade as a double
     */
    public double calculateAverage()
    {
        if (grades.isEmpty())
        {
            return 0;
        }
        int total = 0;
        for (int g : grades)
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
        return super.toString() + " - Course: " + course;
    }
}