import mis.util.Inputs;
import mis.util.InputsTestHelper;
import mis.util.MenuEscapeException;

import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Inputs} verifying validated console input behaviour.
 *
 * <p>Tests simulate user input using {@link ByteArrayInputStream} and cover:</p>
 * <ul>
 *   <li>Valid input scenarios</li>
 *   <li>Invalid input scenarios (with eventual valid input)</li>
 *   <li>Escape command ("menu") behaviour</li>
 * </ul>
 *
 * <p><b>Design notes:</b></p>
 * <ul>
 *   <li>Each method is tested in isolation using {@link Nested} classes.</li>
 *   <li>Tests assert return values or exceptions, not printed output.</li>
 *   <li>Looping behaviour is simulated by feeding multiple inputs in sequence.</li>
 *   <li>The static {@link java.util.Scanner} in {@link Inputs} is reset before each test
 *       using a package-private helper so that simulated input streams are correctly bound.</li>
 * </ul>
 */
class InputsTests
{
    @Nested
    class ReadIntTests
    {
        /**
         * Valid integer input should be returned correctly.
         */
        @Test
        void validIntegerIsReturned()
        {
            System.setIn(new ByteArrayInputStream("42\n".getBytes()));
            InputsTestHelper.resetScanner();
            assertEquals(42, Inputs.readInt("Enter number:"));
        }

        /**
         * Invalid input followed by valid input should eventually return the valid integer.
         */
        @Test
        void invalidThenValidIntegerIsReturned()
        {
            System.setIn(new ByteArrayInputStream("abc\n99\n".getBytes()));
            InputsTestHelper.resetScanner();
            assertEquals(99, Inputs.readInt("Enter number:"));
        }

        /**
         * Escape command "menu" should throw {@link MenuEscapeException}.
         */
        @Test
        void menuInputThrowsException()
        {
            System.setIn(new ByteArrayInputStream("menu\n".getBytes()));
            InputsTestHelper.resetScanner();
            assertThrows(MenuEscapeException.class, () -> Inputs.readInt("Enter number:"));
        }
    }

    @Nested
    class ReadStringTests
    {
        /**
         * Non-empty string should be returned correctly.
         */
        @Test
        void nonEmptyStringIsReturned()
        {
            System.setIn(new ByteArrayInputStream("Hello\n".getBytes()));
            InputsTestHelper.resetScanner();
            assertEquals("Hello", Inputs.readString("Enter text:"));
        }

        /**
         * Empty input followed by valid string should eventually return the valid string.
         */
        @Test
        void emptyThenValidStringIsReturned()
        {
            System.setIn(new ByteArrayInputStream("\nWorld\n".getBytes()));
            InputsTestHelper.resetScanner();
            assertEquals("World", Inputs.readString("Enter text:"));
        }

        /**
         * Escape command "menu" should throw {@link MenuEscapeException}.
         */
        @Test
        void menuInputThrowsException()
        {
            System.setIn(new ByteArrayInputStream("menu\n".getBytes()));
            InputsTestHelper.resetScanner();
            assertThrows(MenuEscapeException.class, () -> Inputs.readString("Enter text:"));
        }
    }

    @Nested
    class ConfirmTests
    {
        /**
         * "y" or "yes" should return true.
         */
        @Test
        void yesInputReturnsTrue()
        {
            System.setIn(new ByteArrayInputStream("y\n".getBytes()));
            InputsTestHelper.resetScanner();
            assertTrue(Inputs.confirm("Confirm?"));

            System.setIn(new ByteArrayInputStream("yes\n".getBytes()));
            InputsTestHelper.resetScanner();
            assertTrue(Inputs.confirm("Confirm?"));
        }

        /**
         * "n" or "no" should return false.
         */
        @Test
        void noInputReturnsFalse()
        {
            System.setIn(new ByteArrayInputStream("n\n".getBytes()));
            InputsTestHelper.resetScanner();
            assertFalse(Inputs.confirm("Confirm?"));

            System.setIn(new ByteArrayInputStream("no\n".getBytes()));
            InputsTestHelper.resetScanner();
            assertFalse(Inputs.confirm("Confirm?"));
        }

        /**
         * Invalid input followed by valid input should eventually return the valid result.
         */
        @Test
        void invalidThenValidInputIsHandled()
        {
            System.setIn(new ByteArrayInputStream("maybe\nyes\n".getBytes()));
            InputsTestHelper.resetScanner();
            assertTrue(Inputs.confirm("Confirm?"));
        }

        /**
         * Escape command "menu" should throw {@link MenuEscapeException}.
         */
        @Test
        void menuInputThrowsException()
        {
            System.setIn(new ByteArrayInputStream("menu\n".getBytes()));
            InputsTestHelper.resetScanner();
            assertThrows(MenuEscapeException.class, () -> Inputs.confirm("Confirm?"));
        }
    }

    @Nested
    class ReadValidDateTests
    {
        /**
         * Valid date within 3 months should be returned correctly.
         */
        @Test
        void validDateIsReturned()
        {
            LocalDate today = LocalDate.now();
            LocalDate validDate = today.plusDays(7);
            String input = validDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n";

            System.setIn(new ByteArrayInputStream(input.getBytes()));
            InputsTestHelper.resetScanner();
            assertEquals(validDate, Inputs.readValidDate("Enter date:"));
        }

        /**
         * Past date followed by valid date should eventually return the valid date.
         */
        @Test
        void pastDateThenValidDateIsHandled()
        {
            LocalDate today = LocalDate.now();
            LocalDate pastDate = today.minusDays(1);
            LocalDate validDate = today.plusDays(10);

            String input = pastDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                           validDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n";

            System.setIn(new ByteArrayInputStream(input.getBytes()));
            InputsTestHelper.resetScanner();
            assertEquals(validDate, Inputs.readValidDate("Enter date:"));
        }

        /**
         * Date beyond 3 months followed by valid date should eventually return the valid date.
         */
        @Test
        void farFutureDateThenValidDateIsHandled()
        {
            LocalDate today = LocalDate.now();
            LocalDate farFuture = today.plusMonths(4);
            LocalDate validDate = today.plusDays(15);

            String input = farFuture.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n" +
                           validDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n";

            System.setIn(new ByteArrayInputStream(input.getBytes()));
            InputsTestHelper.resetScanner();
            assertEquals(validDate, Inputs.readValidDate("Enter date:"));
        }

        /**
         * Invalid format followed by valid date should eventually return the valid date.
         */
        @Test
        void invalidFormatThenValidDateIsHandled()
        {
            LocalDate today = LocalDate.now();
            LocalDate validDate = today.plusDays(5);

            String input = "2025-12-31\n" + // wrong format
                           validDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n";

            System.setIn(new ByteArrayInputStream(input.getBytes()));
            InputsTestHelper.resetScanner();
            assertEquals(validDate, Inputs.readValidDate("Enter date:"));
        }

        /**
         * Escape command "menu" should throw {@link MenuEscapeException}.
         */
        @Test
        void menuInputThrowsException()
        {
            System.setIn(new ByteArrayInputStream("menu\n".getBytes()));
            InputsTestHelper.resetScanner();
            assertThrows(MenuEscapeException.class, () -> Inputs.readValidDate("Enter date:"));
        }
    }
}