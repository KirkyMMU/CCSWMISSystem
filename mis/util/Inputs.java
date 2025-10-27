package mis.util;

import java.util.Scanner;

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
            return scanner.nextInt();
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
}
