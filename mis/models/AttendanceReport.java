package mis.models;

import mis.services.DataManager;

/**
 * AttendanceReport is an implementation of the {@link Reportable}
 * interface designed to generate reports related to student attendance. 
 *
 * <p>Currently, this class serves as a stub and does not yet provide
 * functional reporting logic. It exists to establish the structure 
 * and integration point for future attendance reporting features.</p>
 * 
 * <p>Design notes:
 * <ul>
 *   <li>It holds a reference to the {@link DataManager}, which manages
 *       collections of students, staff and courses. This will allow 
 *       the report to query attendance data once such functionality 
 *       is implemented.</li>
 *   <li>Implements the {@code Reportable} interface, ensuring that 
 *       all reports in the MIS System share a common contract via
 *       the {@code generateReport()} method.</li>
 *   <li>Future versions of this class may include logic to calculate
 *       attendance percentages and produce summaries grouped by course
 *       or student.</li>
 * </ul>
 * </p>
 */
public class AttendanceReport implements Reportable
{
    // Reference to the DataManager service
    private DataManager manager;

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
     * <p>Currently, this method returns a stub message indicating
     * that the report is not yet implemented. In future, this method
     * will:
     * <ul>
     *   <li>Query the DataManager for student and course data.</li>
     *   <li>Calculate attendance percentages.</li>
     *   <li>Format the results into a readable report string.</li>
     * </ul>
     * </p>
     * 
     * @return a string containing the attendance report output.
     */
    @Override
    public String generateReport()
    {
        // Stub implementation: returns a placeholder message
        return "\n--- Attendance Report ---\n(Not yet implemented)\n";
    }
}