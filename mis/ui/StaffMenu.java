package mis.ui;

import mis.models.Staff;
import mis.services.DataManager;
import mis.util.Inputs;
import java.time.LocalDate;

/**
 * Handles staff-related operations via a console sub-menu.
 * 
 * <p>Provides options to add, list, assign tasks and remove staff members.
 * Encapsulates input handling and delegates persistence to {@link DataManager}.</p>
 * 
 * <p>Design notes:
 * <ul>
 *   <li>Uses {@link Inputs} for validated user input.</li>
 *   <li>Delegates storage and retrieval to {@link DataManager}.</li>
 *   <li>Each menu option is encapsulated in its own private helper method.</li>
 * </ul>
 * </p>
 */
public class StaffMenu 
{
    // Shared DataManager instance used to manage staff records
    private final DataManager manager;

    /**
     * Constructs a StaffMenu with the given DataManager.
     * 
     * @param manager the shared {@link DataManager} instance
     */
    public StaffMenu(DataManager manager)
    {
        this.manager = manager;
    }

    // Displays the staff menu and routes user choices to appropriate actions
    public void show()
    {
        System.out.println("\n----- Staff Menu -----");
        System.out.println("1. Add Staff");
        System.out.println("2. List Staff");
        System.out.println("3. Assign Task");
        System.out.println("4. Remove Staff");
        System.out.println("5. Back");

        int choice = Inputs.readInt("Choose an option (1-5):");

        switch(choice)
        {
            case 1 -> addStaff();
            case 2 -> manager.listStaff();
            case 3 -> assignTask();
            case 4 -> removeStaff();
            case 5 -> System.out.println("\nReturning to Main Menu...\n");
            default -> System.out.println("\nInvalid option.\n");
        }
    }

    /**
     * Adds a new staff member to the DataManager.
     * 
     * <p>Prompts the user for staff details (ID, name, email, role, department)
     * then attempts to add the staff member. Displays a success or failure message
     * depending on whether the staff ID already exists.</p>
     */
    private void addStaff()
    {
        int id = Inputs.readInt("Enter Staff ID:");
        String name = Inputs.readString("Enter Staff Name:");
        String email = Inputs.readString("Enter Staff Email:");
        String role = Inputs.readString("Enter Staff Role:");
        String department = Inputs.readString("Enter Staff Department:");

        Staff staff = new Staff(id, name, email, role, department);
        boolean added = manager.addStaff(staff);
        if(added)
        {
            System.out.println("\nStaff added successfully.\n");
        }
        else
        {
            System.out.println("\nStaff ID already exists. Staff not added.\n");
        }
    }

    /**
     * Assigns a task to a staff member.
     * 
     * <p>Prompts the user for a staff ID, task description and deadline.
     * If the staff member exists, the task is assigned. Displays a success
     * message if assigned or an error message if the staff member cannot be found.</p>
     */
    private void assignTask()
    {
        int staffId = Inputs.readInt("Enter Staff ID:");
        Staff staff = manager.findStaffById(staffId);

        if (staff != null) 
        {
            String task = Inputs.readString("Enter task description:");
            LocalDate deadline = Inputs.readValidDate("Enter task deadline:");
            staff.assignTask(task, deadline);
            System.out.println("\nTask assigned.\n");
        } 
        else 
        {
            System.out.println("\nStaff member not found.\n");
        }
    }
    
    /**
     * Removes a staff member by their ID.
     * 
     * <p>Prompts the user for a staff ID then attempts to remove the staff
     * from the DataManager. Displays a success or failure message depending
     * on whether the staff member was found.</p>
     */
    private void removeStaff()
    {
        int id = Inputs.readInt("Enter Staff ID to remove:");
        boolean removed = manager.removeStaffById(id);

        if(removed)
        {
            System.out.println("\nStaff removed successfully.\n");
        }
        else
        {
            System.out.println("\nStaff not found.\n");
        }
    }
}