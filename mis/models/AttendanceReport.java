package mis.models;

import mis.services.DataManager;

/**
 * Generates a formatted report of student attendance statistics.
 *
 * <p>{@code AttendanceReport} implements the {@link Reportable} interface
 * and queries the {@link DataManager} for all students and courses. It
 * produces a multi‑section report including:</p>
 * <ul>
 *   <li>Each student's ID, name, and attendance percentage.</li>
 *   <li>Average attendance per course (if courses have enrolled students).</li>
 *   <li>An overall average attendance across all students in the system.</li>
 * </ul>
 *
 * <p><b>Design notes:</b></p>
 * <ul>
 *   <li>Attendance values are stored per student and persisted via
 *       {@link mis.util.DataIO}.</li>
 *   <li>Percentages are formatted to one decimal place followed by a
 *       percent sign.</li>
 *   <li>If no students exist, the report indicates that overall attendance
 *       is not available.</li>
 *   <li>Course averages are only shown if at least one student is enrolled
 *       on the course.</li>
 * </ul>
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