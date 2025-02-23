   import org.junit.Rule;
   import org.junit.Test;
   import org.junit.rules.ErrorCollector;
   import static org.hamcrest.Matchers.is; // For is() matcher

   public class TestCases {

       @Rule
       public ErrorCollector collector = new ErrorCollector();

       @Test
       public void testWithMultipleAssertions() {
        Solution solution = new Solution();
           collector.checkThat("TEST 1", solution.solution(1041), is(5));  // 10000010001 in binary
           collector.checkThat("TEST 2",solution.solution(15), is(0));    // 1111 in binary
           collector.checkThat("TEST 3",solution.solution(9), is(2));     // 1001 in binary
           collector.checkThat("TEST 4",solution.solution(529),is(4));   // 1000010001 in binary
        collector.checkThat("TEST 5",solution.solution(20),is(1));    // 10100 in binary
        collector.checkThat("TEST 6",solution.solution(32),is(1));    // 100000 in binary
       }
   }