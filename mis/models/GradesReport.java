package mis.models;

import mis.services.DataManager;

/**
 * GradesReport is an implementation of the {@link Reportable} interface
 * that generates a summary of student grades within the MIS System.
 * 
 * <p>This report iterates through all students managed by the
 * {@link DataManager}, calculates their average grades and formats the 
 * results into a readable report string. Each student entry includes:</p>
 * <ul>
 *   <li>The student's name and unique ID.</li>
 *   <li>The course they are enrolled in (or "No course" if none).</li>
 *   <li>The student's average grade (or "N/A" if no grades recorded).</li>
 * </ul>
 * 
 * <p><b>Design notes:</b>
 * <ul>
 *   <li>Relies on {@code DataManager.getStudents()} to access the list of
 *       students.</li>
 *   <li>Delegates grade calculation to {@link Student#calculateAverage()}
 *       ensuring encapsulation of grade logic within the Student class.</li>
 *   <li>Uses {@link StringBuilder} for efficient string concatenation when
 *       building the report output.</li>
 *   <li>Currently outputs a plain text report. Future enhancements could
 *       include formatted tables or export to CSV/HTML formats.</li>
 * </ul>
 * </p>
 */
public class GradesReport implements Reportable
{
    // Reference to the DataManager service
    private DataManager manager;

    /**
     * Constructs a new GradesReport with the given DataManager.
     * 
     * @param manager the DataManager instance that this report will use to
     *                access student data.
     */
    public GradesReport(DataManager manager)
    {
        this.manager = manager;
    }

    /**
     * Generates the grades report.
     * 
     * <p>This method builds a string containing a header and a line for each
     * student. Each line includes the student's name, ID, course code (or
     * "No course") and average grade (or "N/A").</p>
     */
    @Override
    public String generateReport()
    {
        // Start with a header line
        StringBuilder builder = new StringBuilder("\n--- Grades Report ---\n");

        // Iterate through all students in the DataManager
        for(Student student : manager.getStudents())
        {
            // Calculate the student's average grade
            double avg = student.calculateAverage();

            // Determine course information (code or "No course")
            String courseInfo;
            if(student.getCourse() != null)
            {
                courseInfo = student.getCourse().getCode();
            }
            else
            {
                courseInfo = "No course";
            }

            // Append student details to the report
            builder.append(student.getName())
                   .append(" (ID: ").append(student.getId()).append(")")
                   .append(" - Course: ").append(courseInfo)
                   .append(" | Average Grade: ");
            
            // Handle case where no grades exist (average = 0)
            if(avg == 0)
            {
                builder.append("N/A");
            }
            else
            {
                builder.append(avg);
            }

            builder.append("\n");
        }
        // Return the completed report string
        return builder.toString();
    }
}