import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestJunit1 {

    int id = 4;

    @Test
    public void testPrintMessage() {
        System.out.println("Id = " + id );
        assertEquals(4, id);
    }
}