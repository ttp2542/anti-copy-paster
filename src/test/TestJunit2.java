import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestJunit2 {

    String message = "Robert";

    @Test
    public void testPrintMessage() {
        System.out.println("Message = " + message );
        assertEquals("Robert", message);
    }
}
