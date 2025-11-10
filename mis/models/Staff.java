package mis.models;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

/**
 * Represents a staff member in the MIS system.
 * Inherits common personal details from the abstract Person class.
 * Stores role and department information specific to staff.
 */
public class Staff extends Person
{
    // Job title or role of the staff member (e.g. Lecturer, Administrator)
    private String role;

    // Department the staff member belongs to (e.g. IT, Business, Science)
    private String department;

    /**
     * Constructor to initialise a Staff object.
     * @param id Unique staff ID
     * @param name Full name
     * @param email Email address
     * @param role Job title
     * @param department Department name
     */
    public Staff(int id, String name, String email, String role, String department)
    {
        super(id, name, email); // Calls the constructor of Person
        this.role = role;
        this.department = department;
    }

    // Getter for role
    public String getRole()
    {
        return role;
    }

    // Getter for department
    public String getDepartment()
    {
        return department;
    }

    // Setter for role
    public void setRole(String role)
    {
        this.role = role;
    }

    // Setter for department
    public void setDepartment(String department)
    {
        this.department = department;
    }

    // The current task assigned to the staff member.
    private String currentTask;

    /**
     * Assigns a task to the staff member.
     * Outputs a message to the console with the task assigned to the staff member and the deadline for completion.
     * Uses several imported date related libraries to ensure desired date format is returned.
     * @param task Description of the task
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

        // if statements to check whether deadline date is in desired range
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
        currentTask = task + " (Deadline: " + formattedDate + ")";
        System.out.println(getName() + " assigned to: " + currentTask);
    }

    /**
     * Returns a string representation of the staff member.
     * Includes inherited personal details, role, department and current assigned task ("None" if no task assigned).
     */
    @Override
    public String toString()
    {
        String taskDisplay;
        if(currentTask == null || currentTask.isEmpty())
        {
            taskDisplay = "None";
        }
        else
        {
            taskDisplay = currentTask;
        }
        return super.toString() + " - " + role + " in " + department + ", Current task: " + taskDisplay;
    }
}