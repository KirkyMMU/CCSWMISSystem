package mis.models;

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

    /**
     * Assigns a task to the staff member.
     * Currently outputs a simple message to the console.
     * @param task Description of the task
     */
    public void assignTask(String task)
    {
        System.out.println(getName() + "assigned to: " + task);
    }

    /**
     * Returns a string representation of the staff member.
     * Includes inherited personal details, role and department.
     */
    @Override
    public String toString()
    {
        return super.toString() + " - " + role + " in " + department;
    }
}