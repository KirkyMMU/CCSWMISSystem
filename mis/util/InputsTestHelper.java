package mis.util;

import java.util.Scanner;

/**
 * Test helper utility for {@link Inputs}.
 *
 * <p>This class provides a method to reset the static {@link Scanner}
 * instance used by {@link Inputs} so that it correctly binds to the
 * current {@code System.in} stream. This is necessary in unit tests
 * where {@code System.in} is redirected to a simulated input stream
 * (e.g. {@link java.io.ByteArrayInputStream}).</p>
 *
 * <p><b>Design notes:</b></p>
 * <ul>
 *   <li>Not intended for production use, only for testing.</li>
 *   <li>Ensures that {@link Inputs} methods read from the correct
 *       input stream after {@code System.setIn(...)} is called.</li>
 *   <li>Package-private access to {@link Inputs#setScanner(Scanner)}
 *       keeps this functionality hidden from application code.</li>
 * </ul>
 */
public class InputsTestHelper
{
    /**
     * Reinitialises the {@link Scanner} in {@link Inputs} to point at
     * the current {@code System.in}.
     *
     * <p>Call this method in test setup after redirecting
     * {@code System.in} to a simulated input stream.</p>
     */
    public static void resetScanner()
    {
        Inputs.setScanner(new Scanner(System.in));
    }
}