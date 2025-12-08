package mis.models;

import mis.services.DataManager;

/**
 * AttendanceReport is an implementation of the {@link Reportable}
 * interface designed to generate reports related to student attendance. 
 *
 * <p>This class queries the {@link DataManager} for all students and
 * displays their attendance percentages. Attendance values are stored
 * per student and persisted via {@link mis.util.DataIO}.</p>
 *
 * <p><b>Design notes:</b>
 * <ul>
 *   <li>Attendance values are generated when students are created and
 *       typically fall between 85–100%, with 100% being common.</li>
 *   <li>Reports are formatted as a readable string, showing each
 *       student's ID, name, and attendance percentage.</li>
 *   <li>Future versions may include averages by course or highlight
 *       outliers.</li>
 * </ul>
 * </p>
 */
public class AttendanceReport implements Reportable
{
    // Reference to the DataManager service for accessing students.
    private final DataManager manager;

    /**
     * Constructs a new AttendanceReport with the given DataManager.
     * 
     * @param manager the DataManager instance that this report will
     *                use to access system data.
     */
    public AttendanceReport(DataManager manager)
    {
        this.manager = manager;
    }

    /**
     * Generates the attendance report.
     *
     * <p>Iterates through all students in the system and displays their
     * attendance percentage with an overall percentage value for all students
     * displayed at the end of the report. Values are formatted to one 
     * decimal place followed by a percent sign.</p>
     *
     * @return a string containing the attendance report output
     */
    @Override
    public String generateReport()
    {
        StringBuilder report = new StringBuilder("\n--- Attendance Report ---\n");

        double total = 0;
        int count = 0;

        // Loop through all students and append their attendance info
        for(Student student : manager.getStudents())
        {
            double attendance = student.getAttendancePercentage();

            report.append("ID: ").append(student.getId())
                  .append(" | Name: ").append(student.getName())
                  .append(" | Attendance: ")
                  .append(String.format("%.1f%%", student.getAttendancePercentage()))
                  .append("\n");

            total += attendance;
            count++;

        }

        // Course-level averages
        if(!manager.getCourses().isEmpty())
        {
            // Loop through each course in the data manager
            report.append("\n--- Course Averages ---\n");
            for(Course course : manager.getCourses())
            {
                double courseTotal = 0;
                int courseCount = 0;

                // Loop through enrolled student IDs and find attendance
                for(int studentId : course.getEnrolledIds())
                {
                    Student student = manager.findStudentById(studentId);
                    if(student != null)
                    {
                        courseTotal += student.getAttendancePercentage();
                        courseCount++;
                    }
                }

                // Only show average if course has enrolled students
                if(courseCount > 0)
                {
                    double average = courseTotal / courseCount;
                    report.append("Course ").append(course.getCode())
                          .append(" Average Attendance: ")
                          .append(String.format("%.1f%%", average))
                          .append("\n");
                }
            }
        }

        // Overall average attendance
        if(count > 0)
        {
            double average = total / count;
            report.append("\nOverall Average Attendance: ")
                  .append(String.format("%.1f%%", average))
                  .append("\n");
        }
        else
        {
            report.append("\nNo students found. Overall Average Attendance: N/A\n");
        }

        return report.toString();
    }
}