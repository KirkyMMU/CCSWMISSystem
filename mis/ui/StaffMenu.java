package mis.ui;

import mis.models.Staff;
import mis.services.DataManager;
import mis.util.Inputs;
import mis.util.MenuEscapeException;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Handles staff-related operations via a console sub-menu.
 * 
 * <p>Provides options to add, list, assign tasks and remove staff members.
 * Encapsulates input handling and delegates persistence to {@link DataManager}.</p>
 * 
 * <p><b>Design notes:</b>
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
        while(true)
        {
            System.out.println("\n ----- Staff Menu -----\n");
            System.out.println("1. Add Staff");
            System.out.println("2. List Staff");
            System.out.println("3. Assign Task");
            System.out.println("4. Remove Task");
            System.out.println("5. Remove Staff");

            try
            {
                int choice = Inputs.readInt("\nChoose an option (1-5):");

                switch(choice)
                {
                    case 1 -> { addStaff(); return; }
                    case 2 -> { manager.listStaff(); return; }
                    case 3 -> { assignTask(); return; }
                    case 4 -> { removeTask(); return; }
                    case 5 -> { removeStaff(); return; }
                    default -> System.out.println("\nInvalid option.");
                }
            }
            catch(MenuEscapeException exception)
            {
                System.out.println("\nReturning to Main Menu...");
                return;
            }
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
        try
        {
            int id = Inputs.readInt("\nEnter Staff ID:");
            String name = Inputs.readString("Enter Staff Name:");
            String email = Inputs.readString("Enter Staff Email:");
            String role = Inputs.readString("Enter Staff Role:");
            String department = Inputs.readString("Enter Staff Department:");

            Staff staff = new Staff(id, name, email, role, department);
            boolean added = manager.addStaff(staff);
            if(added)
            {
                System.out.println("\nStaff added successfully.");
            }
            else
            {
                System.out.println("\nStaff ID already exists. Staff not added.");
            }
        }
        catch(MenuEscapeException exception)
        {
            throw exception;
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
        try
        {
            int staffId = Inputs.readInt("\nEnter Staff ID:");
            Staff staff = manager.findStaffById(staffId);

            if (staff != null) 
            {
                String task = Inputs.readString("Enter task description:");
                LocalDate deadline = Inputs.readValidDate("Enter task deadline:");
                staff.assignTask(task, deadline);
                System.out.println("\nTask assigned.");
            } 
            else 
            {
                System.out.println("\nStaff member not found.");
            }
        }
        catch(MenuEscapeException exception)
        {
            throw exception;
        }
    }
    
    /**
     * Removes a task from a staff member by description.
     *
     * <p>Prompts the user for a staff ID then finds the staff member in the
     * {@link DataManager}. Prompts for the task description to remove then
     * calls {@link Staff#removeTask(String)} and reports success/failure.
     */
    private void removeTask()
    {
        try
        {
            int id = Inputs.readInt("\nEnter Staff ID:");
            Staff staff = manager.findStaffById(id);

            if(staff != null)
            {
                // Show tasks with numbers
                ArrayList<String> tasks = staff.getTasks();
                if (tasks.isEmpty()) 
                {
                    System.out.println("\nNo tasks assigned to this staff member.");
                    return;
                }

                boolean validChoice = false;
                while(!validChoice)
                {
                    System.out.println("\n--- Tasks for " + staff.getName() + " ---");
                    for(int i = 0; i < tasks.size(); i++)
                    {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }

                    int choice = Inputs.readInt("\nEnter the number of the task to remove:");

                    if(choice >= 1 && choice <= tasks.size())
                    {
                        String removedTask = tasks.remove(choice - 1);
                        System.out.println("\nRemoved task: " + removedTask);
                        validChoice = true; // exit loop
                    }
                    else if(tasks.size() == 1)
                    {
                        System.out.println("\nInvalid choice. Please enter \"1\" to remove the task for this staff member." );
                    }
                    else
                    {
                        System.out.println("\nInvalid choice. Please enter a number between 1 and " + tasks.size() + ".");
                    } // else
                } // while
            } // if
            else
            {
                System.out.println("\nStaff member with ID " + id + " was not found.");
            }
        } // try
        catch(MenuEscapeException exception)
        {
            throw exception;
        }
    } // void

    /**
     * Removes a staff member by their ID.
     * 
     * <p>Prompts the user for a staff ID then attempts to remove the staff
     * from the DataManager. Displays a success or failure message depending
     * on whether the staff member was found.</p>
     */
    private void removeStaff()
    {
        try
        {
            int id = Inputs.readInt("\nEnter Staff ID to remove:");
            boolean removed = manager.removeStaffById(id);

            if(removed)
            {
                System.out.println("\nStaff removed successfully.");
            }
            else
            {
                System.out.println("\nStaff not found.");
            }
        }
        catch(MenuEscapeException exception)
        {
            throw exception;
        }
    }
}