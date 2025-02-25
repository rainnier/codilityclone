public class Solution {
    // Example solution - hidden from candidates
    public int solution(int N) {
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
                currentGap = 0
            } else if (counting) {
                currentGap++;
            }
        }
        
        return maxGap;
    }
}