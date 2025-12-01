package mis.util;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for reading validated user input from the console.
 * 
 * <p>Provides static helper methods to safely capture user input:
 * <ul>
 *   <li>{@link #readInt(String)} for integers</li>
 *   <li>{@link #readString(String)} for non-empty strings</li>
 *   <li>{@link #confirm(String)} for yes/no confirmations</li>
 *   <li>{@link #readValidDate(String)} for dates in DD/MM/YYYY format</li>
 * </ul>
 * </p>
 * 
 * <p>Design notes:
 * <ul>
 *   <li>Uses a single shared {@link Scanner} instance bound to {@code System.in}.</li>
 *   <li>All methods loop until valid input is provided.</li>
 *   <li>Validation rules are explained inline for clarity.</li>
 * </ul>
 * </p>
 */
public class Inputs
    {
        // Shared Scanner instance for reading console input.
        private static Scanner scanner = new Scanner(System.in);

        /**
         * Reads a valid integer from the user.
         * 
         * <p>Prompts the user with the given message then loops until a valid
         * integer is entered. Invalid input is discarded with {@code scanner.next()}.</p>
         * 
         * @param prompt message to display before reading input
         * @return valid integer entered by the user
         */
        public static int readInt(String prompt)
        {
            System.out.print(prompt + " ");
            while(!scanner.hasNextInt())
            {
                System.out.println("Invalid number. Please enter a whole number.");
                scanner.next(); // discard invalid input
                System.out.print(prompt + " ");
            }
            int value = scanner.nextInt();
            scanner.nextLine(); // consume leftover newline so next input works correctly
            return value;
    }

    /**
     * Reads a non-empty string from the user.
     * 
     * <p>Prompts the user with the given message then loops until a non-empty, trimmed
     * string is entered.</p>
     * 
     * @param prompt message to display before reading input
     * @return trimmed, non-empty string
     */
    public static String readString(String prompt)
    {
        String input = "";
        while(input.isEmpty())
        {
            System.out.print(prompt + " ");
            input = scanner.nextLine().trim();
            if(input.isEmpty())
            {
                System.out.println("Input cannot be empty. Please try again.");
            }
        }
        return input;
    }

    /**
     * Asks the user to confirm an action with yes/no.
     * 
     * <p>Accepts {@code y}, {@code yes}, {@code n} or {@code no} (case-insensitive).
     * Loops until a valid response is entered.</p>
     * 
     * @param prompt confirmation message
     * @return {@code true} if confirmed,
     *         {@code false} otherwise
     */
    public static boolean confirm(String prompt)
    {
        while(true)
        {
            System.out.print(prompt + " (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if(input.equals("y") || input.equals("yes"))
            {
                return true;
            }
            if(input.equals("n") || input.equals("no"))
            {
                return false;
            }
            System.out.println("Invalid response. Please enter 'y' or 'n'.");
        }
    }

    /**
     * Reads a valid user-entered date in the format {@code dd/MM/yyyy}.
     * 
     * <p>Validation rules:
     * <ul>
     *   <li>Date must be in the correct format.</li>
     *   <li>Date must be in the future (after today).</li>
     *   <li>Date must not be more than 3 months ahead of today's date.</li>
     * </ul>
     * </p>
     * 
     * <p>Loops until a valid date is entered. Invalid formats or out-of-range
     * dates trigger error messages and re-prompting.</p>
     * 
     * @param prompt message to display before reading input
     * @return a valid {@link LocalDate} object that meets all conditions
     */
    public static LocalDate readValidDate(String prompt)
    {
        // Formatter to match expected date input
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while(true)
        {
            // Prompt user for input
            System.out.print(prompt + " (DD/MM/YYYY): ");
            String input = scanner.nextLine().trim();
        
            try
            {
                // Attempt to parse the input using the formatter
                LocalDate date = LocalDate.parse(input, formatter);

                // Get today's date and calculate the maximum allowed date
                LocalDate today = LocalDate.now();
                LocalDate maxDate = today.plusMonths(3);

                // Check if the date is in the future and within 3 months
                if(date.isAfter(today) && !date.isAfter(maxDate))
                {
                    return date;
                }
                else
                {
                    System.out.println("Date must be in the future and within 3 months from today.");
                }
            }
            catch(DateTimeParseException error)
            {
                // If parsing fails, inform the user and retry
                System.out.println("Invalid format. Please enter the date as DD/MM/YYYY.");
            }
        }
    }
}