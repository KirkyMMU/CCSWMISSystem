package mis.util;

/**
 * Exception thrown when the user types the escape command ("menu")
 * to return to the main menu.
 */
public class MenuEscapeException extends RuntimeException
{
    public MenuEscapeException()
    {
        super("User requested return to main menu.");
    }
}