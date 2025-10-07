/*
 * GREEDY ALGORITHM - LEVEL 1 (SIMPLE)
 * ====================================
 * 
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
 */
 
import java.util.ArrayList; // for storing result coins
import java.util.Arrays;


public class GreedyCoinChange{
   
    public static int findMinCoins(int[] coins , int amount){

    // coins : array of available coin demoinations
    // amount : target amount we need to make 

    //ArrayList to store which coins we picked , it is dynamic array - can shrink / grow . we dont know how many coins needed beforehand thats why we are using this 
    ArrayList<Integer> result = new ArrayList<>();

    int remaining = amount;

    int count = 0; // count for number of coins used 

    for (int i = 0 ; i<coins.length ; i++){

        while(remaining>=coins[i]){

            result.add(coins[i]); // ADD this coin simple 

            remaining -= coins[i];
            count++;

             System.out.println("Picked coin: " + coins[i] + 
                                 " | Remaining: " + remaining);
        }
        // because the array is descending, the first coin that satisfies remaining >= coins[i] is automatically the largest one that doesn’t exceed the remaining amount.

        if (remaining == 0) {
                break; // Exit the loop early
            }
    }

    System.out.println("\nCoins used: " + result);
        System.out.println("Total coins: " + count);
        
        return count;
}



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