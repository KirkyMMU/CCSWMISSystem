package mis.models;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

/**
 * Represents a staff member in the MIS System.
 * 
 * <p>This class extends the abstract {@link Person} class, inheriting 
 * common personal attributes such as ID, name and email. It adds staff-specific
 * attributes like role and department and provides functionality for
 * assigning tasks with deadlines.</p>
 * 
 * <p><b>Design notes:</b>
 * <ul>
 *   <li>Encapsulates staff metadata (role, department) to distinguish staff
 *       from students.</li>
 *   <li>Includes task assignment logic with deadline vaidation to ensure
 *       realistic scheduling.</li>
 *   <li>Uses Java's {@link java.time} API for date handling and formatting.</li>
 *   <li>Overrides {@code toString()} to provide a comprehensive summary 
 *       including inherited details and staff-specific information.</li>
 * </ul>
 * </p>
 */
public class Staff extends Person
{
    // Job title or role of the staff member (e.g. Lecturer, Administrator)
    private String role;

    // Department the staff member belongs to (e.g. IT, Business, Science)
    private String department;

    /**
     * Constructor to initialise a Staff object.
     * 
     * @param id         unique staff ID
     * @param name       full name of the staff member
     * @param email      email address of the staff member
     * @param role       job title or role
     * @param department department name
     */
    public Staff(int id, String name, String email, String role, String department)
    {
        super(id, name, email); // Calls the constructor of Person
        this.role = role;
        this.department = department;
    }

    /** 
     * Getter for role.
     * 
     * @return the job title or role of the staff member
     */
    public String getRole()
    {
        return role;
    }

    /**
     * Getter for department.
     * 
     * @return the department name that relates to the staff member
     */
    public String getDepartment()
    {
        return department;
    }

    /**
     * Setter for role.
     * 
     * @param role the job title or role to set for the staff member
     */
    public void setRole(String role)
    {
        this.role = role;
    }

    /**
     * Setter for department.
     * 
     * @param department the department name to set for the staff member
     */
    public void setDepartment(String department)
    {
        this.department = department;
    }

    // List of tasks assigned to the staff member.
    private ArrayList<String> tasks = new ArrayList<>();

    /**
     * Assigns a task with a deadline to a staff member.
     * 
     * <p>This method validates the deadline to ensure it is:
     * <ul>
     *   <li>In the future (not today or past dates).</li>
     *   <li>Within 90 days (approx. 3 months) from the current date.</li>
     * </ul>
     * If validation fails, an error message is printed and no task is assigned.</p>
     * 
     * <p>On success, the task is stored with a formatted deadline and a confirmation
     * message is printed to the console.</p>
     * 
     * @param task     description of the task
     * @param deadline deadline date for task completion
     */
    public void assignTask(String task, LocalDate deadline)
    {
        // Today's date
        LocalDate today = LocalDate.now();

        // Difference between today and deadline in days
        long daysBetween = ChronoUnit.DAYS.between(today, deadline);

        // Desired date format for entry
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = deadline.format(formatter);

        // Validate deadline range
        if(daysBetween <= 0)
        {
            System.out.println("Deadline must be in the future.");
            return;
        }
        else if(daysBetween > 90)
        {
            System.out.println("Deadline can not be more than 3 months from today.");
            return;
        }

        // Assign task with formatted deadline
        String taskEntry = task + " (Deadline: " + formattedDate + ")";
        tasks.add(taskEntry);
    }

    /**
     * Returns all tasks assigned to this staff member.
     * 
     * @return list of task strings
     */
    public ArrayList<String> getTasks()
    {
        return tasks;
    }

    /**
     * Returns a comma-separated string of tasks for persistence.
     * 
     * @return CSV string of tasks or empty string if none exist
     */
    public String getTasksCSV()
    {
        if(tasks.isEmpty())
        {
            return "";
        }
        return String.join(",", tasks);
    }

    /**
     * Removes a task from this staff member's task list.
     *
     * <p>This method searches the internal {@code tasks} list for the given
     * task description and removes it if found. If the task does not exist,
     * no changes are made.</p>
     *
     * <p>Steps:
     * <ol>
     *   <li>Receive the task description to remove.</li>
     *   <li>Check if the {@code tasks} list contains the task.</li>
     *   <li>If present, remove the task and return {@code true}.</li>
     *   <li>If not present, return {@code false} to indicate no removal occurred.</li>
     * </ol>
     * </p>
     *
     * @param task the description of the task to remove
     * @return {@code true} if the task was removed, {@code false} otherwise
     */
    public boolean removeTask(String task)
    {
        return tasks.remove(task);
    }


    /**
     * Returns a string representation of the staff member.
     * 
     * <p>Includes:
     * <ul>
     *   <li>Inherited personal details (ID, name, email).</li>
     *   <li>Role and department information.</li>
     *   <li>Current assigned task or "None" if no task is assigned.</li>
     * </ul>
     * </p>
     * 
     * @return a formatted string summarising the staff member
     */
    @Override
    public String toString()
    {
        String taskDisplay;
        if(tasks.isEmpty())
        {
            taskDisplay = "None";
        }
        else
        {
            taskDisplay = String.join("; ", tasks);
        }
        return super.toString() + " - " + role + " in " + department + ", Current task(s): " + taskDisplay;
    }
}