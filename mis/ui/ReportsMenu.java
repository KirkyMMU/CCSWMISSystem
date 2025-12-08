package mis.ui;

import mis.services.*;
import mis.util.Inputs;

/**
 * Handles report-related operations via a console sub-menu.
 * 
 * <p>Provides options to generate and display reports such as grades and attendance.
 * Encapsulates input handling and delegates report generation to {@link ReportManager}.</p>
 * 
 * <p>Design notes:
 * <ul>
 *   <li>Uses {@link Inputs} for validated user input.</li>
 *   <li>Delegates report generation to {@link ReportManager}.</li>
 *   <li>Each menu option is encapsulated in its own case branch.</li>
 * </ul>
 * </p>
 */
public class ReportsMenu
{
    // Shared DataManager instance used to generate reports
    private final DataManager manager;

    /**
     * Constructs a ReportsMenu with the given DataManager.
     * 
     * @param manager the shared {@link DataManager} instance
     */
    public ReportsMenu(DataManager manager)
    {
        this.manager = manager;
    }

    // Displays the reports menu and routes user choices to appropriate actions
    public void show()
    {
        int choice;
        do
        {
            System.out.println("\n ----- Reports Menu -----\n");
            System.out.println("1. Grades Report");
            System.out.println("2. Attendance Report");
            System.out.println("3. Back");

            choice = Inputs.readInt("\nChoose an option (1-3):");

            // Create a ReportManager for generating reports
            ReportManager reportManager = new ReportManager(manager);

            switch(choice)
            {
                case 1 -> { System.out.println(reportManager.buildGradesReport()); return; }
                case 2 -> { System.out.println(reportManager.buildAttendanceReport()); return; }
                case 3 -> { System.out.println("\nReturning to Main Menu..."); return; }
                default -> System.out.println("\nInvalid option.");
            }
        }
        while(choice != 3);
    }
}