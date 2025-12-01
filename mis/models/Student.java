package mis.models;

import java.util.ArrayList;

/**
 * Represents a student in the MIS system.
 * 
 * <p>This class extends the abstract {@link Person} class, inheriting
 * common personal details such as ID, name and email. It adds
 * student-specific attributes and behaviours, including:</p>
 * <ul>
 *   <li>Association with a {@link Course} object (the course the student
 *       is enrolled in).</li>
 *   <li>Storage of GCSE-style grades (integers 1-9).</li>
 *   <li>A randomly generated attendance percentage.</li>
 *   <li>Methods to enrol/de-enrol from courses, add grades, caluclate
 *       averages and export grades in CSV format.</li>
 * </ul>
 * 
 * <p>Design notes:
 * <ul>
 *   <li>Grades are stored as integers to reflect GCSE standards (1-9).</li>
 *   <li>Course enrolment is managed bidirectionally; setting a course also
 *       updates the course's enrolment list.</li>
 *   <li>Validation ensures only valid grades are added.</li>
 *   <li>Average calculation returns 0 if no grades exist allowing reports
 *       to handle missing data gracefully.</li>
 * </ul>
 * </p>
 */
public class Student extends Person
{
    // The course the student is enrolled in (null if not assigned)
    private Course course;

    // Attendance percentage (0–100), generated when student is created
    private double attendancePercentage;

    // List of grades (1-9) following GCSE standards
    private ArrayList<Integer> grades = new ArrayList<>();

    /**
     * Constructor to initialise a Student object without an inital course.
     * 
     * @param id    unique student ID
     * @param name  full name of the student
     * @param email email address of the student
     */
    public Student(int id, String name, String email)
    {
        super(id, name, email); // Calls the constructor of Person
        this.course = null;     // Student may not have a course initially
        this.attendancePercentage = generateAttendance(); // Generate random attendance for student
    }

    /**  
     * Constructor to initialise a Student object with an initial course.
     * 
     * <p>If a course is provided, the student is automatically enrolled
     * in that course.</p>
     * 
     * @param id     unique student ID
     * @param name   full name of the student
     * @param email  email address of the student
     * @param course the course the student is enrolled in
     */
    public Student(int id, String name, String email, Course course)
    {
        super(id, name, email); // Calls the constructor of Person
        
        // Enrol student onto course if provided
        this.course = course;
        if(course != null)
        {
            course.enrolStudent(id);
        }
        this.attendancePercentage = generateAttendance(); // Generate random attendance for student
    }

    /**
     * Getter for course.
     * 
     * @return the course the student is enrolled in (may be null)
     */
    public Course getCourse()
    {
        return course;
    }

    /**
     * Getter for attendance percentage.
     * 
     * @return the randomly generated attendance percentage for the student
     */
    public double getAttendancePercentage()
    {
        return attendancePercentage;
    }

    /**
     * Setter for course.
     * 
     * <p>If the student was previously enrolled in another course, they
     * are de-enrolled from that course before being added to the new one.</p>
     * 
     * @param course the new course to enrol the student in
     */
    public void setCourse(Course course)
    {
        // De-enrol from old course if exists
        if(this.course != null)
        {
            this.course.removeStudent(getId());
        }

        // Assign new course and enrol
        this.course = course;
        if(course != null)
        {
            course.enrolStudent(getId());
        }
    }

    /**
     * Setter for attendance percentage
     * 
     * @param attendancePercentage the attendance percentage as a double
     */
    public void setAttendancePercentage(double attendancePercentage)
    {
        this.attendancePercentage = attendancePercentage;
    }

    /**
     * Adds a grade to the student's record.
     * 
     * <p>Only valid GCSE grades (1-9) are accepted. Invalid grades trigger
     * a warning message and are not added.</p>
     * 
     * @param grade the grade to add
     * @return {@code true} if the grade was valid and added,
     *         {@code false} otherwise
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
            System.out.println("\nInvalid grade \"" + grade + "\". Grade must be between 1 and 9.\nGrade not added.");
            return false;
        }
    }

    /**
     * Calculates the average grade for the student.
     * 
     * <p>If no grades are recorded, returns 0. This allows reports to
     * distinguish between students with no grades and those with low 
     * averages.</p>
     * 
     * @return the average grade as a double
     */
    public double calculateAverage()
    {
        if(grades.isEmpty())
        {
            return 0;
        }
        int total = 0;
        for(int grade : grades)
        {
            total += grade;
        }
        return total / (double) grades.size();
    }

    /** Weighted random generator for attendance (bias towards 100%).
     * 
     * @return a double representing the attendance percentage for the student
     */
    private static double generateAttendance()
    {
        double random = Math.random();       // uniform 0–1
        double biased = 1 - (random * random);    // bias toward 1
        return 85 + (biased * 15);      // scale to 85–100
    }


    /**
     * Returns the student's grades as a comma-separated string.
     * 
     * <p>If no grades are recorded, returns an empty string.</p>
     * 
     * @return a CSV string of grades
     */
    public String getGradesCSV()
    {
        if(grades.isEmpty())
        {
            return "";
        }
        else
        {
            return grades.stream()
                         .map(x -> String.valueOf(x))
                         .reduce((a, b) -> a + "," + b)
                         .orElse("");
        }
    }

    /**
     * Returns a string representation of the student.
     * 
     * <p>Includes inherited personal details (Id, name, email, attendance percentage) 
     * and course information (course code or "No course assigned").</p>
     * 
     * @return a formatted string summarising the student
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
        // with formatted percentage attendance
        return super.toString() + " | Course: " + courseInfo + " | Attendance: " + String.format("%.1f%%", getAttendancePercentage());
    }
}