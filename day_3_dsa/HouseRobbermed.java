package day_3_dsa;
/*
 * PROBLEM 2: HOUSE ROBBER (MEDIUM - Amazon, Microsoft favorite!)
 * ===============================================================
 * 
 * You are a robber planning to rob houses along a street.
 * Each house has certain amount of money.
 * 
 * CONSTRAINT: Cannot rob two ADJACENT houses (alarm will trigger!)
 * 
 * Find MAXIMUM amount you can rob without alerting police.
 * 
 * Example 1:
 * Input: [1, 2, 3, 1]
 * Output: 4
 * Explanation: Rob house 0 (money=1) and house 2 (money=3)
 * Total = 1 + 3 = 4
 * 
 * Example 2:
 * Input: [2, 7, 9, 3, 1]
 * Output: 12
 * Explanation: Rob house 0 (money=2), house 2 (money=9), house 4 (money=1)
 * Total = 2 + 9 + 1 = 12
 * 
 * 
 * THE KEY INSIGHT (INCLUDE/EXCLUDE DECISION):
 * -------------------------------------------
 * 
 * At each house i, you have TWO CHOICES:
 * 
 * 1. ROB house i:
 *    - Get money from house i
 *    - CANNOT rob house i-1 (adjacent!)
 *    - Add to max money from house i-2
 *    → rob(i) = nums[i] + rob(i-2)
 * 
 * 2. DON'T ROB house i:
 *    - Get 0 from this house
 *    - Can use max money up to house i-1
 *    → skip(i) = rob(i-1)
 * 
 * Take MAXIMUM of both choices:
 * dp[i] = max(nums[i] + dp[i-2], dp[i-1])
 * 
 * 
 * VISUAL EXPLANATION:
 * -------------------
 * houses = [2, 7, 9, 3, 1]
 * 
 * House 0: Rob 2 → money = 2
 * House 1: Rob 7 OR keep 2? → max(7, 2) = 7
 * House 2: Rob 9+2=11 OR keep 7? → max(11, 7) = 11
 * House 3: Rob 3+7=10 OR keep 11? → max(10, 11) = 11
 * House 4: Rob 1+11=12 OR keep 11? → max(12, 11) = 12
 * 
 * Answer: 12
 */

import java.util.Arrays;
public class HouseRobbermed {
    
    // ================================================================
    //           APPROACH 1: RECURSION (Understanding only)
    // ================================================================
    // Time: O(2^n) - Exponential!
    // Space: O(n) - Recursion stack
    
    public static int robRecursion(int[] nums, int index) {
        // BASE CASES
        if (index < 0) return 0;  // No houses left
        if (index == 0) return nums[0];  // Only first house
        
        // CHOICE 1: Rob current house
        int robCurrent = nums[index] + robRecursion(nums, index - 2);
        
        // CHOICE 2: Skip current house
        int skipCurrent = robRecursion(nums, index - 1);
        
        // Return maximum
        return Math.max(robCurrent, skipCurrent);
    }
    
    
    // ================================================================
    //        APPROACH 2: MEMOIZATION (Top-Down DP)
    // ================================================================
    // Time: O(n)
    // Space: O(n)
     
    public static int robMemo( int[] nums){
        int n = nums.length;
        int[] memo = new int[n];
        Arrays.fill(memo,-1);

        return robMemoHelper(nums, n - 1, memo);
    }

    private static int robMemoHelper(int[] nums, int index, int[] memo) {

    if(index<0) return 0;
    if(index==0) return nums[0];

    if(memo[index]!= -1){
        return memo[index];
    }

    // CALCULATE
    int robCurrent = nums[index] + robMemoHelper(nums, index - 2, memo);
    int skipCurrent = robMemoHelper(nums, index - 1, memo);
        
    memo[index] = Math.max(robCurrent, skipCurrent);
    return memo[index];    


    }
    // ================================================================
    //        APPROACH 3: TABULATION (Bottom-Up DP) - BEST!
    // ================================================================
    // Time: O(n)
    // Space: O(n)
    
    public static int robTabulation(int[] nums) {
        int n = nums.length;
        
        // Edge cases
        if (n == 0) return 0;
        if (n == 1) return nums[0];
        
        // CREATE DP ARRAY
        // dp[i] = maximum money that can be robbed up to house i
        int[] dp = new int[n];
        
        // BASE CASES
        dp[0] = nums[0];  // Only one house, rob it
        dp[1] = Math.max(nums[0], nums[1]);  // Rob better of first two
        
        System.out.println("Initial:");
        System.out.println("dp[0] = " + dp[0] + " (rob house 0)");
        System.out.println("dp[1] = " + dp[1] + " (max of house 0 and 1)");
        System.out.println();
        
        // FILL DP ARRAY
        for (int i = 2; i < n; i++) {
            // CHOICE 1: Rob current house
            int robCurrent = nums[i] + dp[i - 2];
            
            // CHOICE 2: Skip current house
            int skipCurrent = dp[i - 1];
            
            // Take maximum
            dp[i] = Math.max(robCurrent, skipCurrent);
            
            System.out.printf("House %d (money=%d):%n", i, nums[i]);
            System.out.printf("  Rob:  %d + dp[%d]=%d = %d%n", 
                            nums[i], i-2, dp[i-2], robCurrent);
            System.out.printf("  Skip: dp[%d] = %d%n", i-1, skipCurrent);
            System.out.printf("  dp[%d] = max(%d, %d) = %d%n", 
                            i, robCurrent, skipCurrent, dp[i]);
            System.out.println();
        }
        
        return dp[n - 1];
    }
    
    
    // ================================================================
    //        APPROACH 4: SPACE OPTIMIZED - O(1) Space!
    // ================================================================
    // Time: O(n)
    // Space: O(1)
    
    public static int robOptimized(int[] nums) {
        int n = nums.length;
        
        if (n == 0) return 0;
        if (n == 1) return nums[0];
        
        // We only need last 2 values!
        int prev2 = nums[0];  // dp[i-2]
        int prev1 = Math.max(nums[0], nums[1]);  // dp[i-1]
        
        for (int i = 2; i < n; i++) {
            int robCurrent = nums[i] + prev2;
            int skipCurrent = prev1;
            int current = Math.max(robCurrent, skipCurrent);
            
            // SHIFT values
            prev2 = prev1;
            prev1 = current;
        }
        
        return prev1;
    }
    
    
    // ================================================================
    //                         MAIN METHOD
    // ================================================================
    
    public static void main(String[] args) {
        int[] nums = {2, 7, 9, 3, 1};
        
        System.out.println("=".repeat(70));
        System.out.println("HOUSE ROBBER PROBLEM");
        System.out.println("=".repeat(70));
        System.out.println("Houses: " + Arrays.toString(nums));
        System.out.println("=".repeat(70));
        System.out.println();
        
        // Approach 1: Recursion
        System.out.println("APPROACH 1: PURE RECURSION");
        System.out.println("-".repeat(70));
        int result1 = robRecursion(nums, nums.length - 1);
        System.out.println("Maximum money: " + result1);
        System.out.println();
        
        // Approach 2: Memoization
        System.out.println("APPROACH 2: MEMOIZATION");
        System.out.println("-".repeat(70));
        int result2 = robMemo(nums);
        System.out.println("Maximum money: " + result2);
        System.out.println();
        
        // Approach 3: Tabulation
        System.out.println("APPROACH 3: TABULATION (Bottom-Up DP)");
        System.out.println("-".repeat(70));
        int result3 = robTabulation(nums);
        System.out.println("Maximum money: " + result3);
        System.out.println();
        
        // Approach 4: Space Optimized
        System.out.println("APPROACH 4: SPACE OPTIMIZED");
        System.out.println("-".repeat(70));
        int result4 = robOptimized(nums);
        System.out.println("Maximum money: " + result4);
        
        System.out.println("\n" + "=".repeat(70));
    }
}


/*
 * ================================================================
 *                    DETAILED WALKTHROUGH
 * ================================================================
 * 
 * EXAMPLE: nums = [2, 7, 9, 3, 1]
 * 
 * TABULATION DRY RUN:
 * -------------------
 * 
 * Step 0:
 * dp[0] = 2 (rob house 0)
 * Houses: [2]
 * 
 * Step 1:
 * dp[1] = max(nums[0], nums[1]) = max(2, 7) = 7
 * Houses: [2, 7]
 * Better to rob house 1 only!
 * 
 * Step 2:
 * Choice 1 (rob house 2): 9 + dp[0] = 9 + 2 = 11
 * Choice 2 (skip house 2): dp[1] = 7
 * dp[2] = max(11, 7) = 11
 * Houses: [2, 7, 9] → Rob houses 0 and 2
 * 
 * Step 3:
 * Choice 1 (rob house 3): 3 + dp[1] = 3 + 7 = 10
 * Choice 2 (skip house 3): dp[2] = 11
 * dp[3] = max(10, 11) = 11
 * Houses: [2, 7, 9, 3] → Keep previous (houses 0 and 2)
 * 
 * Step 4:
 * Choice 1 (rob house 4): 1 + dp[2] = 1 + 11 = 12
 * Choice 2 (skip house 4): dp[3] = 11
 * dp[4] = max(12, 11) = 12
 * Houses: [2, 7, 9, 3, 1] → Rob houses 0, 2, and 4!
 * 
 * Final DP array: [2, 7, 11, 11, 12]
 * Answer: 12
 * 
 * 
 * WHY THIS WORKS (Mathematical Proof):
 * ====================================
 * 
 * Let dp[i] = max money up to house i
 * 
 * At house i, we have two options:
 * 
 * 1. ROB house i:
 *    - Get nums[i]
 *    - Cannot use dp[i-1] (adjacent constraint)
 *    - Must use dp[i-2]
 *    - Total: nums[i] + dp[i-2]
 * 
 * 2. SKIP house i:
 *    - Get 0 from this house
 *    - Use best result up to i-1
 *    - Total: dp[i-1]
 * 
 * Optimal solution must be one of these two!
 * Therefore: dp[i] = max(nums[i] + dp[i-2], dp[i-1])
 * 
 * 
 * THE DP FRAMEWORK APPLIED:
 * =========================
 * 
 * STEP 1: Define State
 * → dp[i] = maximum money that can be robbed from houses 0 to i
 * 
 * STEP 2: Recurrence Relation
 * → dp[i] = max(nums[i] + dp[i-2], dp[i-1])
 * 
 * STEP 3: Base Cases
 * → dp[0] = nums[0]
 * → dp[1] = max(nums[0], nums[1])
 * 
 * STEP 4: Order
 * → Fill from left to right (i = 2 to n-1)
 * 
 * STEP 5: Answer
 * → dp[n-1]
 * 
 * 
 * SPACE OPTIMIZATION EXPLAINED:
 * =============================
 * 
 * Notice: dp[i] only depends on dp[i-1] and dp[i-2]
 * We don't need the ENTIRE array!
 * 
 * Instead of: dp = [2, 7, 11, 11, 12]
 * Just keep: prev2, prev1, current
 * 
 * At each step:
 * - Calculate current using prev1 and prev2
 * - Shift: prev2 = prev1, prev1 = current
 * 
 * Space: O(n) → O(1)
 * 
 * 
 * VARIATIONS OF THIS PROBLEM:
 * ===========================
 * 
 * 1. House Robber II (Circular):
 *    - Houses arranged in circle
 *    - First and last are adjacent
 *    - Solution: Run twice (exclude first OR exclude last)
 * 
 * 2. House Robber III (Tree):
 *    - Houses arranged as binary tree
 *    - Cannot rob parent and child
 *    - Solution: DP on trees
 * 
 * 3. Delete and Earn:
 *    - Same pattern, different story
 *    - If pick nums[i], delete nums[i-1] and nums[i+1]
 * 
 * 
 * REMEMBER:
 * =========
 * ✓ Include/Exclude decision pattern
 * ✓ dp[i] = max(include, exclude)
 * ✓ Include: nums[i] + dp[i-2]
 * ✓ Exclude: dp[i-1]
 * ✓ Can optimize to O(1) space
 */

