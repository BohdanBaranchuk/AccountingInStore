import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 *
 * JUnit tests. The Suite runner.
 *
 *
 * @author  atom Earth
 * @version 1.2
 * @since   2017-01-13
 *
 * @see BaseTest
 * @see AdditionalTest
 * @see TestSuite
 */
public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestSuite.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}
