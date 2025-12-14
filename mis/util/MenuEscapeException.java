package mis.util;

/**
 * Signals that the user has entered the escape command ({@code "menu"})
 * during input validation requesting an immediate return to the main menu.
 * 
 * <p>This excpetion is thrown by methods in {@link Inputs} whenever the
 * user types the escape command instead of providing a valid value.
 * It is intended to bubble up to the calling menu class (e.g. 
 * {@code StudentMenu}, {@code CourseMenu}) where it should be caught and
 * handled by re-routing control flow back to the main menu.</p>
 * 
 * <b>Design Notes:</b>
 * <ul>
 *   <li>Extends {@link RuntimeException} so it can propagate without
 *       explicit declaration in method signatures.</li>
 *   <li>Encapsulates a fixed message for clarity in logs and debugging.</li>
 *   <li>Used exclusively as a control-flow mechanism not for error
 *       reporting.</li>
 * </ul>
 * 
 * <h2>Usage Example:</h2>
 * 
 * <pre>{@code
 * // Inside Inputs.readString():
 * if(input.equalsIgnoreCase("menu"))
 * {
 *    throw new MenuEscapeException;
 * }
 * 
 * // Inside CourseMenu.show():
 * try
 * {
 *    String code = Inputs.readString("Enter Course Code:");
 *    // normal logic...
 * }
 * catch(MenuEscapeException exception)
 * {
 *    System.out.println("Returning to Main Menu...");
 *    return; // exit submenu immediately
 * }
 * }</pre>
 * 
 * @see mis.util.Inputs
 */
public class MenuEscapeException extends RuntimeException
{
    /**
     * Constructs a new {@code MenuEscapeException} with a default message
     * indicating that the user requested to return to the main menu.
     */
    public MenuEscapeException()
    {
        super("User requested return to main menu.");
    }
}