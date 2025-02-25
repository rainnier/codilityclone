public class BinaryGap {
    public int solution(Integer N) {
        // write your code here
        
        // Example: For N = 1041 (10000010001 in binary)
        // Should return 5 because the longest binary gap is 5
        // Convert to binary string
        String binary = Integer.toBinaryString(N);
        
        int maxGap = 0;
        int currentGap = 0;
        boolean counting = false;
        
        for (char c : binary.toCharArray()) {
            if (c == '1') {
                if (counting) {
                    maxGap = Math.max(maxGap, currentGap);
                }
                counting = true;
                currentGap = 0;
            } else if (counting) {
                currentGap++;
            }
        }
        
        return maxGap;
    }
}