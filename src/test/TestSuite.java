import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//JUnit Suite Test
@RunWith(Suite.class)

@Suite.SuiteClasses({
        TestJunit1.class ,TestJunit2.class
})

public class TestSuite {
    public static void main(String[] args){
        TestJunit1 test1 = new TestJunit1();
        TestJunit2 test2 = new TestJunit2();
        test1.testPrintMessage();
        test2.testPrintMessage();
    }
}