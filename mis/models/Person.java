package mis.models;

/**
 * Abstract base class representing a person in the MIS system.
 * Used as a superclass for Student and Staff.
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
     * @param id Unique ID
     * @param name Full name
     * @param email Email address
     */
    public Person(int id, String name, String email)
    {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getter for ID
    public int getId()
    {
        return id;
    }

    // Getter for name
    public String getName()
    {
        return name;
    }

    // Getter for email
    public String getEmail()
    {
        return email;
    }

    // Setter for name
    public void setName(String name)
    {
        this.name = name;
    }

    // Setter for email
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Returns a string representation of the person.
     * Useful for displaying basic info in logs or UI.
     */
    @Override
    public String toString()
    {
        return id + ": " + name  + " (" + email + ")";
    }
}
