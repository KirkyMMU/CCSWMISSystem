package mis.models;

/**
 * Reportable is a functional interface that defines the contract for
 * all report-generating classes in the MIS System.
 * 
 * <p>Any class that implements this interface must provide its own
 * implementation of the {@link #generateReport()} method which returns
 * a string representation of the report.</p>
 * 
 * <p><b>Design notes:</b>
 * <ul>
 *   <li>Encourages consistency across different types of reports (e.g.
 *       attendance or grades).</li>
 *   <li>Allows polymorphic handling of reports, for example, a collection
 *       of {@code Reportable} objects can be iterated and each report
 *       generated without knowing the specific type.</li>
 *   <li>Supports extensibility; new report types can be added easily by
 *       implementing this interface.</li>
 *   <li>Return type is {@code String} to keep output flexible. It can be
 *       printed to console, written to a file or displayed in a UI.</li>
 * </ul>
 * </p>
 * 
 * <h3>Usage Example:</h3>
 * <pre>{@code
 * // Create reports using the interface
 * DataManager manager = new DataManager();
 * Reportable attendanceReport = new AttendanceReport(manager);
 * Reportable gradesReport = new GradesReport(manager);
 * 
 * // Store them polymorphically in a list
 * List<Reportable> reports = Arrays.asList(attendanceReport, gradesReport);
 * 
 * // Generate all reports without knowing their concrete types
 * for(Reportable report : reports)
 * {
 *    System.out.println(report.generateReport());
 * }
 * }</pre>
 */
public interface Reportable
{
    /**
     * Generates the report output.
     * 
     * <p>Implementations should build and return a string containing the
     * relevant report data in a readable format. The exact content and 
     * structure will vary depending on the report type.</p>
     * 
     * @return a string containing the generated report.
     */
    String generateReport();
}