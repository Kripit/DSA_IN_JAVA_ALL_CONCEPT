/*
 * PROBLEM 1: CLIMBING STAIRS (Foundation of DP)
 * ==============================================
 * 
 * You're climbing a staircase with N steps.
 * Each time you can climb 1 or 2 steps.
 * How many DISTINCT WAYS can you reach the top?
 * 
 * Example 1:
 * Input: n = 2
 * Output: 2
 * Explanation: Two ways:
 * 1. 1 step + 1 step
 * 2. 2 steps
 * 
 * Example 2:
 * Input: n = 3
 * Output: 3
 * Explanation: Three ways:
 * 1. 1 step + 1 step + 1 step
 * 2. 1 step + 2 steps
 * 3. 2 steps + 1 step
 * 
 * 
 * THE KEY INSIGHT (How to think in DP):
 * -------------------------------------
 * 
 * To reach step N, you must have come from:
 * - Step (N-1) → then take 1 step
 * - Step (N-2) → then take 2 steps
 * 
 * So: ways(N) = ways(N-1) + ways(N-2)
 * 
 * This is FIBONACCI SEQUENCE!
 * 
 * 
 * VISUAL EXPLANATION:
 * -------------------
 * 
 * N = 4
 * 
 *           [4]
 *          /   \
 *        [3]   [2]
 *       /  \   /  \
 *     [2] [1] [1] [0]
 *     / \  |   |
 *   [1][0][0] [0]
 *    |
 *   [0]
 * 
 * Notice: [2], [1], [0] calculated MULTIPLE times!
 * This is OVERLAPPING SUBPROBLEMS → Use DP!
 * 
 * 
 * THREE APPROACHES:
 * -----------------
 * 1. RECURSION (Slow, exponential)
 * 2. MEMOIZATION (Top-Down DP)
 * 3. TABULATION (Bottom-Up DP) ← Best!
 * 4. SPACE OPTIMIZED (Only store last 2 values)
 */
import java.util.Arrays;


public class hillClimbstairs {
    // ================================================================
    //           APPROACH 1: PURE RECURSION (What NOT to do!)
    // ================================================================
                    // Time: O(2^n) - EXPONENTIAL! Very slow!
                    // Space: O(n) - Recursion stack



    public static int climbStairsRecursion(int n){

        if(n==0) return 1; // 1 way: don't move(reached already)
        if(n==1) return 1; // 1 way: take 1 step

        return climbStairsRecursion(n-1) + climbStairsRecursion(n-2);
        // From step n, we came from either (n-1) or (n-2)
    }

    // ================================================================
    //        APPROACH 2: MEMOIZATION (Top-Down DP)
    // ================================================================
    // Time: O(n) - Each subproblem solved once
    // Space: O(n) - Memo array + recursion stack


    public static int climbStairsMemo(int n){

        // Create memo array to store results
        // memo[i] = -1 means not calculated yet

        int[] memo = new int[n+1];

        Arrays.fill(memo,-1);// Initialize with -1

        return climbStairsMemoHelper(n,memo);
    }
    private static int climbStairsMemoHelper(int n, int[] memo) {

        if (n==0) return 1;
        if ( n== 1 ) return 1;
        

        if(memo[n]!=-1){
            return memo[n];
        }

        memo[n] = climbStairsMemoHelper(n-1, memo) +
                  climbStairsMemoHelper(n-2, memo);
        
        return memo[n];


    }

  // ================================================================
 //        APPROACH 3: TABULATION (Bottom-Up DP) - BEST!
    // ================================================================
        // Time: O(n) - Single loop
        // Space: O(n) - DP array

    public static int climbStairsTabulation(int n) {
        // Edge cases
        if (n == 0 || n == 1) return 1;
        
        // CREATE DP ARRAY
        // dp[i] = number of ways to reach step i
        int[] dp = new int[n + 1];
        
        // BASE CASES (Starting values)
        dp[0] = 1;  // 1 way to stay at ground
        dp[1] = 1;  // 1 way to reach step 1
        
        // FILL DP ARRAY (Build from bottom up)
        for (int i = 2; i <= n; i++) {
            // RECURRENCE RELATION
            dp[i] = dp[i - 1] + dp[i - 2];
            
            System.out.printf("dp[%d] = dp[%d] + dp[%d] = %d + %d = %d%n",
                            i, i-1, i-2, dp[i-1], dp[i-2], dp[i]);
        }
        
        // ANSWER: Ways to reach step n
        return dp[n];
    }  
    
    // ================================================================
    //        APPROACH 4: SPACE OPTIMIZED - O(1) Space!
    // ================================================================
    // Time: O(n)
    // Space: O(1) - Only two variables!

    public static int climbStairsOptimized(int n) {
        if (n == 0 || n == 1) return 1;
        
        // We only need last 2 values, not entire array!
        int prev2 = 1;  // dp[i-2]
        int prev1 = 1;  // dp[i-1]
        int current = 0;
        
        for (int i = 2; i <= n; i++) {
            current = prev1 + prev2;  // dp[i] = dp[i-1] + dp[i-2]
            
            // SHIFT: Move values for next iteration
            prev2 = prev1;  // Old prev1 becomes prev2
            prev1 = current;  // Current becomes prev1
            
            System.out.printf("Step %d: prev2=%d, prev1=%d, current=%d%n",
                            i, prev2, prev1-current+prev2, current);
        }
        
        return current;
    }

    // ================================================================
    //                         MAIN METHOD
    // ================================================================
    
    public static void main(String[] args) {
        int n = 5;  // Test with 5 steps
        
        System.out.println("=".repeat(70));
        System.out.println("CLIMBING STAIRS PROBLEM (n = " + n + ")");
        System.out.println("=".repeat(70));
        System.out.println();
        
        // Approach 1: Pure Recursion (slow!)
        System.out.println("APPROACH 1: PURE RECURSION");
        System.out.println("-".repeat(70));
        long start = System.nanoTime();
        int result1 = climbStairsRecursion(n);
        long end = System.nanoTime();
        System.out.println("Result: " + result1);
        System.out.println("Time: " + (end - start) / 1000 + " microseconds");
        System.out.println();
        
        // Approach 2: Memoization
        System.out.println("APPROACH 2: MEMOIZATION (Top-Down DP)");
        System.out.println("-".repeat(70));
        start = System.nanoTime();
        int result2 = climbStairsMemo(n);
        end = System.nanoTime();
        System.out.println("Result: " + result2);
        System.out.println("Time: " + (end - start) / 1000 + " microseconds");
        System.out.println();
        
        // Approach 3: Tabulation
        System.out.println("APPROACH 3: TABULATION (Bottom-Up DP)");
        System.out.println("-".repeat(70));
        start = System.nanoTime();
        int result3 = climbStairsTabulation(n);
        end = System.nanoTime();
        System.out.println("Result: " + result3);
        System.out.println("Time: " + (end - start) / 1000 + " microseconds");
        System.out.println();
        
        // Approach 4: Space Optimized
        System.out.println("APPROACH 4: SPACE OPTIMIZED");
        System.out.println("-".repeat(70));
        start = System.nanoTime();
        int result4 = climbStairsOptimized(n);
        end = System.nanoTime();
        System.out.println("Result: " + result4);
        System.out.println("Time: " + (end - start) / 1000 + " microseconds");
        System.out.println();
        
        // Complexity Comparison
        System.out.println("=".repeat(70));
        System.out.println("COMPLEXITY COMPARISON:");
        System.out.println("=".repeat(70));
        System.out.println("Recursion:        Time O(2^n), Space O(n)");
        System.out.println("Memoization:      Time O(n),   Space O(n)");
        System.out.println("Tabulation:       Time O(n),   Space O(n)");
        System.out.println("Space Optimized:  Time O(n),   Space O(1) ← BEST!");
        System.out.println("=".repeat(70));
    }


/*
 * ================================================================
 *                    DETAILED WALKTHROUGH
 * ================================================================
 * 
 * EXAMPLE: n = 5
 * 
 * RECURSION TREE (Shows overlapping subproblems):
 * -----------------------------------------------
 * 
 *                      climb(5)
 *                    /          \
 *              climb(4)          climb(3)
 *             /        \         /        \
 *        climb(3)  climb(2)  climb(2)  climb(1)
 *        /      \   /     \   /     \      |
 *   climb(2) climb(1) ...  ... ...  ...   1
 *    /    \     |
 * climb(1) climb(0) 1
 *    |       |
 *    1       1
 * 
 * Notice:
 * - climb(3) calculated TWICE
 * - climb(2) calculated THREE times
 * - climb(1) calculated FIVE times
 * 
 * Total function calls for n=5: 15 calls!
 * For n=40: Over 2 BILLION calls! ← Exponential!
 * 
 * 
 * TABULATION DRY RUN (n=5):
 * -------------------------
 * 
 * Initial:
 * dp = [1, 1, ?, ?, ?, ?]
 *       0  1  2  3  4  5
 * 
 * i=2: dp[2] = dp[1] + dp[0] = 1 + 1 = 2
 * dp = [1, 1, 2, ?, ?, ?]
 * 
 * i=3: dp[3] = dp[2] + dp[1] = 2 + 1 = 3
 * dp = [1, 1, 2, 3, ?, ?]
 * 
 * i=4: dp[4] = dp[3] + dp[2] = 3 + 2 = 5
 * dp = [1, 1, 2, 3, 5, ?]
 * 
 * i=5: dp[5] = dp[4] + dp[3] = 5 + 3 = 8
 * dp = [1, 1, 2, 3, 5, 8]
 * 
 * Answer: dp[5] = 8 ways!
 * 
 * 
 * SPACE OPTIMIZATION DRY RUN:
 * ---------------------------
 * 
 * We only need LAST TWO values!
 * 
 * Initial: prev2=1, prev1=1
 * 
 * i=2: current = 1 + 1 = 2
 *      Shift: prev2=1, prev1=2
 * 
 * i=3: current = 2 + 1 = 3
 *      Shift: prev2=2, prev1=3
 * 
 * i=4: current = 3 + 2 = 5
 *      Shift: prev2=3, prev1=5
 * 
 * i=5: current = 5 + 3 = 8
 * 
 * Answer: 8 ways!
 * 
 * Space used: Only 3 variables (O(1))!
 * 
 * 
 * WHY IS THIS FIBONACCI?
 * =======================
 * 
 * Fibonacci: F(n) = F(n-1) + F(n-2)
 * F(0)=0, F(1)=1, F(2)=1, F(3)=2, F(4)=3, F(5)=5, F(6)=8...
 * 
 * Climbing Stairs: climb(n) = climb(n-1) + climb(n-2)
 * climb(0)=1, climb(1)=1, climb(2)=2, climb(3)=3, climb(4)=5, climb(5)=8...
 * 
 * Same recurrence! Just different base cases.
 * Climbing stairs = Fibonacci shifted by 1!
 * 
 * 
 * THE DP FRAMEWORK APPLIED:
 * =========================
 * 
 * STEP 1: Define State
 * → dp[i] = number of ways to reach step i
 * 
 * STEP 2: Recurrence Relation
 * → dp[i] = dp[i-1] + dp[i-2]
 * → (Can come from step i-1 or i-2)
 * 
 * STEP 3: Base Cases
 * → dp[0] = 1 (one way: don't move)
 * → dp[1] = 1 (one way: take 1 step)
 * 
 * STEP 4: Order
 * → Fill from left to right (i = 2 to n)
 * 
 * STEP 5: Answer
 * → dp[n]
 * 
 * 
 * REMEMBER:
 * =========
 * ✓ Recurrence: dp[i] = dp[i-1] + dp[i-2]
 * ✓ Memoization: Top-Down (recursion + cache)
 * ✓ Tabulation: Bottom-Up (loops + array)
 * ✓ Space Optimization: Only keep last 2 values
 * ✓ This is Fibonacci in disguise!
 */
}
