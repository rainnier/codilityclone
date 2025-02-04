import org.junit.Test;
import static org.junit.Assert.*;

public class TestCases {
    @Test
    public void testBinaryGap() {
        BinaryGap solution = new BinaryGap();
        
        assertEquals(5, solution.solution(1041));  // 10000010001 in binary
        assertEquals(0, solution.solution(15));    // 1111 in binary
        assertEquals(2, solution.solution(9));     // 1001 in binary
        assertEquals(4, solution.solution(529));   // 1000010001 in binary
        assertEquals(1, solution.solution(20));    // 10100 in binary
        assertEquals(0, solution.solution(32));    // 100000 in binary
    }
}