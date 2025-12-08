package mis.ui;

import mis.services.DataManager;
import mis.util.Inputs;

/**
 * Handles the main entry menu for the MIS System.
 * 
 * <p>Provides options to navigate to sub-menus for students, staff, courses,
 * reports and save/load operations. Encapsulates input handling and delegates
 * detailed operations to the respective menu classes.</p>
 * 
 * <p><b>Design notes:</b>
 * <ul>
 *   <li>Uses {@link Inputs} for validated user input.</li>
 *   <li>Delegates persistence and business logic to {@link DataManager} and sub-menus.</li>
 *   <li>Returns a boolean flag to indicate whether the system should continue running.</li>
 * </ul>
 * </p>
 */
public class MainMenu
{
    // Shared DataManager instance used across all sub-menus
    private final DataManager manager;

    /**
     * Constructs a MainMenu with the given DataManager.
     * 
     * @param manager the shared {@link DataManager} instance
     */
    public MainMenu(DataManager manager)
    {
        this.manager = manager;
    }

    /**
     * Displays the main menu and routes user choices to appropriate sub-menus.
     * 
     * @return {@code true} if the system should continue running,
     *         {@code false} if exit is confirmed
     */
    public boolean show()
    {
        System.out.println("\n ~~~~~ Main Menu ~~~~~\n");
        System.out.println("1. Students");
        System.out.println("2. Staff");
        System.out.println("3. Courses");
        System.out.println("4. Reports");
        System.out.println("5. Save/Load");
        System.out.println("6. Exit");

        int choice = Inputs.readInt("\nChoose an option (1-6):");

        switch(choice)
        {
            case 1 -> new StudentMenu(manager).show();
            case 2 -> new StaffMenu(manager).show();
            case 3 -> new CourseMenu(manager).show();
            case 4 -> new ReportsMenu(manager).show();
            case 5 -> new SaveLoadMenu(manager).show();
            case 6 ->
            {
                // Confirm exit before terminating loop
                boolean confirmExit = Inputs.confirm("\nAre you sure you want to exit?");
                if(confirmExit)
                {
                    return false; // signal to stop
                }
            }
            default -> System.out.println("\nInvalid option. Please try again.");
        }
        return true; // keep running by default
    }
}