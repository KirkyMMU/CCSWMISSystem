package mis.ui;

import mis.services.DataManager;

/**
 * Main entry point for the MIS System.
 * 
 * <p>This class coordinates the overall execution of the system by:
 * <ul>
 *   <li>Creating a shared {@link DataManager} instance to store students, staff and courses.</li>
 *   <li>Displaying a welcome message to the user.</li>
 *   <li>Delegating control to the {@link MainMenu}, which routes to sub-menus.</li>
 *   <li>Maintaining a loop that continues until the user chooses to exit.</li>
 *   <li>Displaying a goodbye message upon termination.</li>
 * </ul>
 * </p>
 */
public class MISSystem
{
    /**
     * Application entry point.
     * 
     * @param args command-line arguments (not used in this application)
     */
    public static void main(String[] args)
    {
        /*
        Create the central data manager to store students, staff and courses.
        This instance is shared across all menus to ensure consistent state. 
        */
        DataManager manager = new DataManager();

        /*
        Flag to control the Main Menu loop.
        'true' means the system is running, 'false' ends the programme.
        */
        boolean running = true;

        // Display a welcome message at startup.
        System.out.println("\nWelcome to the MIS System.\n\n*Important Note*: At any point during this programme,\nyou can type \"menu\" to return to the Main Menu.");

        // Create the Main Menu, passing in the shared DataManager.
        MainMenu menu = new MainMenu(manager);

        /*
        Main Menu loop:
        Continues showing the menu until 'menu.show()' returns false
        which happens when the user confirms exit.
        */
        while(running)
        {
            running = menu.show();
        }
        
        // Display a goodbye message after the loop ends.
        System.out.println("\nGoodbye!\n");
    }
}