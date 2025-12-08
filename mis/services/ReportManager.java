package mis.services;

import mis.models.*;

/**
 * ReportManager is responsible for coordinating the generation of
 * reports within the MIS system.
 * 
 * <p>This class acts as a facade over the reporting subsystem, providing
 * simple methods to build specific reports (e.g. grades, attendance)
 * without requiring client code to directly instantiate or manage
 * individual report classes.</p>
 * 
 * <p><b>Design notes:</b>
 * <ul>
 *   <li>Encapsulates the creation of {@link Reportable} implementations
 *       such as {@link GradesReport} and {@link AttendanceReport}.</li>
 *   <li>Delegates data access to the {@link DataManager} which provides
 *       the underlying student, staff and course collections.</li>
 *   <li>Supports extensibility; new report types can be added by implementing
 *       {@link Reportable} and exposing a new build method.</li>
 *   <li>Returns reports as {@code String} objects allowing flexible output
 *       (console, file, UI display).</li>
 * </ul>
 * </p>
 * 
 * <h3>Usage Example:</h3>
 * <pre>{@code
 * // Initialise ReportManager with DataManager
 * DataManager dataManager = new DataManager();
 * ReportManager reportManager = new ReportManager(dataManager);
 * 
 * // Build and print reports
 * System.out.println(reportManager.buildGradesReport());
 * System.out.println(reportManager.buildAttendanceReport());
 * }</pre>
 */
public class ReportManager
{
    // Reference to DataManager instance for data access
    private DataManager dataManager;

    /**
     * Constructs a new ReportManager with the specified DataManager.
     * 
     * @param dataManager the DataManager instance used to supply data
     *                    for reports.
     */
    public ReportManager(DataManager dataManager)
    {
        this.dataManager = dataManager;
    }

    /**
     * Builds the grades report.
     * 
     * <p>Creates a {@link GradesReport} using the current DataManager
     * and returns the generated report string.</p>
     * 
     * @return a string containing the grades report.
     */
    public String buildGradesReport()
    {
        Reportable report = new GradesReport(dataManager);
        return report.generateReport();
    }

    /**
     * Builds the attendance report.
     * 
     * <p>Creates an {@link AttendanceReport} using the current DataManager
     * and returns the generated report string.</p>
     * 
     * @return a string containing the attendance report.
     */
    public String buildAttendanceReport()
    {
        Reportable report = new AttendanceReport(dataManager);
        return report.generateReport();
    }
}