package mis.util;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// Utility class for reading validated user input from the console.
public class Inputs
    {
        private static Scanner scanner = new Scanner(System.in);

        /**
         * Reads a valid integer from the user.
         * Uses hasNextInt() to ensure an integer is inputted by the user.
         * @param prompt Message to display before reading input
         * @return Valid integer entered by the user
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
            scanner.nextLine(); // consume leftover newline
            return value;
    }

    /**
     * Reads a non-empty string from the user.
     * @param prompt Message to display before reading input
     * @return Trimmed, non-empty string
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
     * Accepts 'y', 'yes', 'n', or 'no' (case-insensitive).
     * @param prompt Confirmation message
     * @return true if confirmed, false otherwise
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
     * Reads a valid user-entered date in the desired format (dd/MM/yyyy).
     * Ensures date is:
     * - In the correct format
     * - In the future
     * - No more than 3 months ahead of today's date
     * 
     * @param prompt Message to display before reading input
     * @return a valid LocalDate object that meets all conditions
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
            catch(DateTimeParseException e)
            {
                // If parsing fails, inform the user and retry
                System.out.println("Invalid format. Please enter the date as DD/MM/YYYY.");
            }
        }
    }
}
