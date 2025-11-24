package mis.models;

/**
 * Abstract base class representing a person in the MIS system.
 * 
 * <p>This class serves as the common superclass for all types of people
 * in the system such as {@link Student} and {@link Staff}. It defines
 * shared attributes (ID, name, email) and provides basic accessor and 
 * mutator methods.</p>
 * 
 * <p>Design notes:
 * <ul>
 *   <li>Marked as {@code abstract} to prevent direct instantiation - only
 *       subclasses should represent concrete people.</li>
 *   <li>Encapsulates identity and contact information that is common across
 *       all person types.</li>
 *   <li>Provides a {@code toString()} method for quick summaries useful in
 *       logs and debugging.</li>
 *   <li>Future extensions could include additional shared fields such as
 *       phone number or address.</li>
 * </ul>
 * </p>
 */
public abstract class Person
{
    // Unique identifier for the person (e.g., student ID or staff ID)
    private int id;

    // Full name of the person
    private String name;

    // Email address for contact
    private String email;

    /**
     * Constructor to initialise a Person object.
     * 
     * @param id the unique identifier for the person
     * @param name the full name of the person
     * @param email the email address of the person
     */
    public Person(int id, String name, String email)
    {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Getter for ID.
     * 
     * @return the person's ID
     */
    public int getId()
    {
        return id;
    }

    /**
     * Getter for name.
     * 
     * @return the person's name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Getter for email.
     * 
     * @return the person's email address
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Setter for name.
     * 
     * @param name the new full name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Setter for email
     * 
     * @param email the new email address
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Returns a string representation of the person.
     * 
     * <p>Format: {@code id: name (email)}. This is useful for displaying 
     * basic information in logs or debugging.</p>
     * 
     * @return a string summarising the person's details
     */
    @Override
    public String toString()
    {
        return id + ": " + name  + " (" + email + ")";
    }
}