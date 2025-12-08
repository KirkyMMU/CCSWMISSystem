package mis.ui;

import mis.services.DataManager;
import mis.util.*;

/**
 * Handles save/load operations via a console sub-menu.
 * 
 * <p>Provides options to save the current system state to a file and
 * reload it later. Encapsulates input handling and delegates persistence
 * to {@link DataIO}.</p>
 * 
 * <p>Design notes:
 * <ul>
 *   <li>Uses {@link Inputs} for validated user input.</li>
 *   <li>Delegates persistence to {@link DataIO}.</li>
 *   <li>Each menu option is encapsulated in its own case branch.</li>
 * </ul>
 * </p>
 */
public class SaveLoadMenu
{
    // Shared DataManager instance used to save and load system state
    private final DataManager manager;

    /**
     * Constructs a SaveLoadMenu with the given DataManager.
     * 
     * @param manager the shared {@link DataManager} instance
     */
    public SaveLoadMenu(DataManager manager)
    {
        this.manager = manager;
    }

    // Displays the save/load menu and routes user choices to appropriate actions
    public void show()
    {
        int choice;
        do
        {
            System.out.println("\n ----- Save/Load Menu -----\n");
            System.out.println("1. Save Data");
            System.out.println("2. Load Data");
            System.out.println("3. Back");

            choice = Inputs.readInt("\nChoose an option (1-3):");

            switch(choice)
            {
                case 1 -> { DataIO.saveToFile(manager, "mis_data.txt"); return; }
                case 2 -> { DataIO.loadFromFile(manager, "mis_data.txt"); return; }
                case 3 -> { System.out.println("\nReturning to Main Menu..."); return; }
                default -> System.out.println("\nInvalid option.");
            }
        }
        while(choice != 3);
    }
}