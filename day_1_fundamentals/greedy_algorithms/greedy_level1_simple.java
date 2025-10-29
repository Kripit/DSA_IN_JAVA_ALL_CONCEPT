/*
 * GREEDY ALGORITHM - LEVEL 1 (SIMPLE)
 * ====================================
 * PROBLEM: Coin Change (Greedy Version)
 * 
 * Given coins: [1, 2, 5, 10, 20, 50, 100, 500, 2000]
 * Find minimum number of coins to make amount = 93
 * 
 * GREEDY STRATEGY:
 * Always pick the LARGEST coin that doesn't exceed remaining amount
 * 
 * Example: amount = 93
 * Step 1: Pick 50 (largest ≤ 93), remaining = 43
 * Step 2: Pick 20 (largest ≤ 43), remaining = 23
 * Step 3: Pick 20 (largest ≤ 23), remaining = 3
 * Step 4: Pick 2 (largest ≤ 3), remaining = 1
 * Step 5: Pick 1 (largest ≤ 1), remaining = 0
 * 
 * Answer: 5 coins [50, 20, 20, 2, 1]
 * 
 * ML CONNECTION:
 * - Resource allocation (assign max resources to highest priority task)
 * - Memory management (allocate largest free block first)
 */

import java.util.ArrayList; // For storing result coins
import java.util.Arrays;    // For printing arrays nicely

public class GreedyCoinChange {
    
    // Method to find minimum coins using Greedy approach
    public static int findMinCoins(int[] coins, int amount) {
        // coins: array of available coin denominations
        // amount: target amount we need to make
        
        // ArrayList to store which coins we picked
        // ArrayList is dynamic array - can grow/shrink
        // Why ArrayList? We don't know how many coins needed beforehand
        ArrayList<Integer> result = new ArrayList<>();
        
        // Variable to track remaining amount
        int remaining = amount;
        
        // Counter for number of coins used
        int count = 0;
        
        // Start from LARGEST coin (coins array is already sorted descending)
        // Why? Greedy = pick biggest possible at each step
        for (int i = 0; i < coins.length; i++) {
            // i: index in coins array
            // coins[i]: current coin denomination we're checking
            
            // While current coin is smaller than or equal to remaining amount
            // AND we still have amount left to make
            while (remaining >= coins[i]) {
                // Why while? We might need MULTIPLE coins of same denomination
                // Example: amount=40, coin=20, we need TWO 20-rupee coins
                
                result.add(coins[i]);  // Add this coin to result
                remaining -= coins[i]; // Reduce remaining amount
                count++;               // Increment coin count
                
                // Print what just happened (for learning)
                System.out.println("Picked coin: " + coins[i] + 
                                 " | Remaining: " + remaining);
            }
            
            // If remaining becomes 0, we're done!
            if (remaining == 0) {
                break; // Exit the loop early
            }
        }
        
        // Print final result
        System.out.println("\nCoins used: " + result);
        System.out.println("Total coins: " + count);
        
        return count; // Return number of coins used
    }
    
    
    // Main method - where program starts
    public static void main(String[] args) {
        // Define available coin denominations
        // SORTED in DESCENDING order (largest first)
        // Why descending? Greedy picks largest first
        int[] coins = {2000, 500, 100, 50, 20, 10, 5, 2, 1};
        
        // Target amount
        int amount = 93;
        
        System.out.println("=".repeat(50));
        System.out.println("GREEDY COIN CHANGE PROBLEM");
        System.out.println("=".repeat(50));
        System.out.println("Coins available: " + Arrays.toString(coins));
        System.out.println("Target amount: " + amount);
        System.out.println("=".repeat(50));
        System.out.println();
        
        // Call our greedy function
        int minCoins = findMinCoins(coins, amount);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("RESULT: Minimum coins needed = " + minCoins);
        System.out.println("=".repeat(50));
    }
}

/*
 * TIME COMPLEXITY ANALYSIS:
 * -------------------------
 * Outer loop: Runs for each coin type = O(n) where n = number of coin types
 * Inner while loop: In worst case, picks same coin multiple times
 *                   Example: amount=93, coin=1, runs 93 times
 * 
 * Worst case: O(n * amount) if we have coin=1
 * Best case: O(n) if large coins cover amount quickly
 * 
 * SPACE COMPLEXITY:
 * ----------------
 * O(amount) in worst case - if we pick 93 coins of denomination 1
 * 
 * 
 * IMPORTANT NOTE - WHEN GREEDY FAILS:
 * -----------------------------------
 * Greedy works for standard coin systems (1,2,5,10,20...)
 * BUT fails for arbitrary coins!
 * 
 * Example: coins = [1, 3, 4], amount = 6
 * Greedy: 4 + 1 + 1 = 3 coins
 * Optimal: 3 + 3 = 2 coins
 * 
 * For such cases, need DYNAMIC PROGRAMMING (we'll learn later!)
 */