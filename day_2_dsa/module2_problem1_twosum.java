/*
 * ============================================================================
 *                    MODULE 2.1: TWO SUM (SORTED ARRAY)
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * Given a SORTED array of integers and a target sum, find TWO numbers 
 * that add up to the target. Return their INDICES (positions).
 * 
 * Assume: Exactly ONE solution exists.
 * 
 * EXAMPLE 1:
 * Input:  arr = [2, 7, 11, 15], target = 9
 * Output: [0, 1]
 * Explanation: arr[0] + arr[1] = 2 + 7 = 9
 * 
 * EXAMPLE 2:
 * Input:  arr = [1, 3, 4, 5, 7, 11], target = 9
 * Output: [2, 4]
 * Explanation: arr[2] + arr[4] = 4 + 5 = 9
 * 
 * 
 * MATHEMATICAL INTUITION:
 * -----------------------
 * Array is SORTED: [1, 3, 4, 5, 7, 11]
 * Target = 9
 * 
 * Start with SMALLEST (left) and LARGEST (right):
 * left = 1, right = 11
 * Sum = 1 + 11 = 12
 * 
 * Is 12 > 9? YES
 * Can we make sum SMALLER? YES, by reducing right pointer!
 * Why? Because left is already smallest, only right can decrease sum.
 * 
 * Move right--: now right = 7
 * Sum = 1 + 7 = 8
 * 
 * Is 8 < 9? YES  
 * Can we make sum LARGER? YES, by increasing left pointer!
 * Why? Because right is already as small as possible from right side.
 * 
 * Move left++: now left = 3
 * Sum = 3 + 7 = 10
 * 
 * Is 10 > 9? YES
 * Move right--: now right = 5
 * Sum = 3 + 5 = 8
 * 
 * Is 8 < 9? YES
 * Move left++: now left = 4
 * Sum = 4 + 5 = 9 ✓ FOUND!
 * 
 * 
 * KEY INSIGHT (Why this works):
 * -----------------------------
 * In a SORTED array:
 * - If sum TOO SMALL: Need bigger numbers → Move LEFT pointer forward
 * - If sum TOO LARGE: Need smaller numbers → Move RIGHT pointer backward
 * - Each move ELIMINATES possibilities (that's why O(n) not O(n²))
 */

import java.util.Scanner;
import java.util.Arrays;

public class TwoSumSortedArray {
    
    // ========================================================================
    //                         APPROACH 1: BRUTE FORCE
    // ========================================================================
    // Check ALL possible pairs
    // Time: O(n²), Space: O(1)
    
    public static int[] twoSumBruteForce(int[] arr, int target) {
        int n = arr.length;  // Get array length
        
        System.out.println("\n--- BRUTE FORCE APPROACH ---");
        System.out.println("Checking all pairs:");
        
        // Outer loop: first number
        for (int i = 0; i < n; i++) {
            // Inner loop: second number (start from i+1 to avoid duplicates)
            for (int j = i + 1; j < n; j++) {
                int sum = arr[i] + arr[j];
                
                // Print what we're checking
                System.out.printf("arr[%d]=%d + arr[%d]=%d = %d", 
                                i, arr[i], j, arr[j], sum);
                
                if (sum == target) {
                    System.out.println(" ✓ MATCH!");
                    return new int[] {i, j};  // Found! Return indices
                } else {
                    System.out.println(" ✗");
                }
            }
        }
        
        // No solution found
        return new int[] {-1, -1};
    }
    
    
    // ========================================================================
    //                    APPROACH 2: TWO POINTERS (OPTIMIZED)
    // ========================================================================
    // Use left and right pointers
    // Time: O(n), Space: O(1)
    
    public static int[] twoSumTwoPointers(int[] arr, int target) {
        int n = arr.length;
        
        System.out.println("\n--- TWO POINTERS APPROACH ---");
        
        // Initialize two pointers
        int left = 0;        // Start from beginning (smallest element)
        int right = n - 1;   // Start from end (largest element)
        
        System.out.println("Starting pointers:");
        System.out.printf("left = %d (arr[%d] = %d)%n", left, left, arr[left]);
        System.out.printf("right = %d (arr[%d] = %d)%n", right, right, arr[right]);
        System.out.println();
        
        // Keep moving pointers until they meet
        while (left < right) {
            // Calculate current sum
            int currentSum = arr[left] + arr[right];
            
            // Print current state
            System.out.printf("Step: left=%d, right=%d | ", left, right);
            System.out.printf("arr[%d]=%d + arr[%d]=%d = %d | ", 
                            left, arr[left], right, arr[right], currentSum);
            
            // Check three cases:
            
            if (currentSum == target) {
                // CASE 1: Found the pair!
                System.out.println("✓ MATCH FOUND!");
                return new int[] {left, right};
                
            } else if (currentSum < target) {
                // CASE 2: Sum too small, need bigger numbers
                // Move left pointer forward (to get bigger number)
                System.out.println("Sum too small → Move left++");
                left++;
                
            } else {
                // CASE 3: Sum too large, need smaller numbers  
                // Move right pointer backward (to get smaller number)
                System.out.println("Sum too large → Move right--");
                right--;
            }
        }
        
        // No solution found (should not happen if problem guarantees solution)
        System.out.println("No solution found!");
        return new int[] {-1, -1};
    }
    
    
    // ========================================================================
    //                         MAIN METHOD (DRIVER CODE)
    // ========================================================================
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("=".repeat(70));
        System.out.println("         TWO SUM PROBLEM (SORTED ARRAY)");
        System.out.println("=".repeat(70));
        
        // Input: Array size
        System.out.print("Enter array size: ");
        int n = sc.nextInt();
        
        // Create array
        int[] arr = new int[n];
        
        // Input: Array elements (user must enter SORTED array)
        System.out.println("Enter " + n + " elements (in SORTED order):");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        
        // Input: Target sum
        System.out.print("Enter target sum: ");
        int target = sc.nextInt();
        
        // Display input
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INPUT:");
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Target: " + target);
        System.out.println("=".repeat(70));
        
        
        // APPROACH 1: Brute Force
        int[] result1 = twoSumBruteForce(arr, target);
        
        System.out.println("\n" + "-".repeat(70));
        System.out.println("BRUTE FORCE RESULT:");
        if (result1[0] != -1) {
            System.out.printf("Indices: [%d, %d]%n", result1[0], result1[1]);
            System.out.printf("Numbers: arr[%d]=%d, arr[%d]=%d%n", 
                            result1[0], arr[result1[0]], 
                            result1[1], arr[result1[1]]);
        } else {
            System.out.println("No solution found!");
        }
        System.out.println("-".repeat(70));
        
        
        // APPROACH 2: Two Pointers
        int[] result2 = twoSumTwoPointers(arr, target);
        
        System.out.println("\n" + "-".repeat(70));
        System.out.println("TWO POINTERS RESULT:");
        if (result2[0] != -1) {
            System.out.printf("Indices: [%d, %d]%n", result2[0], result2[1]);
            System.out.printf("Numbers: arr[%d]=%d, arr[%d]=%d%n", 
                            result2[0], arr[result2[0]], 
                            result2[1], arr[result2[1]]);
        } else {
            System.out.println("No solution found!");
        }
        System.out.println("-".repeat(70));
        
        
        // Complexity Analysis
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS:");
        System.out.println("=".repeat(70));
        System.out.println("Brute Force:");
        System.out.println("  Time Complexity: O(n²)");
        System.out.println("  Space Complexity: O(1)");
        System.out.println();
        System.out.println("Two Pointers:");
        System.out.println("  Time Complexity: O(n)");
        System.out.println("  Space Complexity: O(1)");
        System.out.println();
        System.out.println("Improvement: From O(n²) to O(n) - Massive speedup!");
        System.out.println("=".repeat(70));
        
        sc.close();
    }
}


/*
 * ============================================================================
 *                         DETAILED CONCEPT EXPLANATION
 * ============================================================================
 * 
 * 1. WHY SORTING IS IMPORTANT:
 * ----------------------------
 * Without sorting, we cannot use two pointers effectively!
 * 
 * Example: [3, 1, 4, 5, 2], target = 7
 * If we start left=3, right=2, sum=5 < 7
 * Should we move left or right? We DON'T KNOW!
 * Because array is not sorted, we don't know if next element is bigger or smaller.
 * 
 * With sorting: [1, 2, 3, 4, 5], target = 7
 * left=1, right=5, sum=6 < 7
 * We KNOW: Moving left++ will increase sum (because array is sorted)
 * 
 * 
 * 2. WHY WE MOVE SPECIFIC POINTER:
 * --------------------------------
 * Current state: left=i, right=j, sum = arr[i] + arr[j]
 * 
 * CASE A: sum < target (need to INCREASE sum)
 *   - Moving right-- will DECREASE sum (smaller number from right)
 *   - Moving left++ will INCREASE sum (bigger number from left)
 *   - Therefore: Move left++
 * 
 * CASE B: sum > target (need to DECREASE sum)
 *   - Moving left++ will INCREASE sum
 *   - Moving right-- will DECREASE sum  
 *   - Therefore: Move right--
 * 
 * 
 * 3. WHY THIS DOESN'T MISS SOLUTIONS:
 * -----------------------------------
 * Claim: Two pointers will find solution if it exists.
 * 
 * Proof by contradiction:
 * Assume solution exists at indices (p, q) where p < q.
 * Assume our algorithm misses it.
 * 
 * For algorithm to miss (p, q):
 *   - Either we skipped over p when moving left
 *   - Or we skipped over q when moving right
 * 
 * Case 1: We moved past p (left > p):
 *   - This means at some point: left=p, right=r (where r > q)
 *   - Sum at that point: arr[p] + arr[r]
 *   - Since arr[q] < arr[r] (sorted), and arr[p] + arr[q] = target
 *   - Then arr[p] + arr[r] > target
 *   - Algorithm would move right--, not left++
 *   - Contradiction! We wouldn't skip p.
 * 
 * Case 2: Similar proof for q.
 * 
 * Therefore, algorithm CANNOT miss solution!
 * 
 * 
 * 4. COMPLEXITY ANALYSIS IN DETAIL:
 * ---------------------------------
 * 
 * BRUTE FORCE:
 * -----------
 * for (i = 0; i < n; i++)           // Runs n times
 *     for (j = i+1; j < n; j++)     // Runs (n-1), (n-2), ..., 1 times
 *         check pair (i, j)         // O(1) operation
 * 
 * Total pairs checked: 1 + 2 + 3 + ... + (n-1) = n(n-1)/2
 * 
 * Using sum formula: Σ(i=1 to n-1) i = (n-1)×n / 2
 * 
 * Expanding: (n² - n) / 2
 * Dropping constants and lower terms: O(n²)
 * 
 * 
 * TWO POINTERS:
 * ------------
 * Initialization: O(1)
 * while (left < right)              // Loop runs at most n times
 *     calculate sum                 // O(1)
 *     move one pointer              // O(1)
 * 
 * Why "at most n times"?
 * - In worst case, we move from opposite ends to middle
 * - left starts at 0, can go up to n-1 (at most n moves)
 * - right starts at n-1, can go down to 0 (at most n moves)
 * - But they meet in middle, so total moves < n
 * 
 * Total: O(n)
 * 
 * 
 * 5. SPACE COMPLEXITY:
 * -------------------
 * Both approaches: O(1)
 * 
 * We only use:
 * - Few integer variables (left, right, sum, etc.)
 * - One result array of size 2
 * 
 * No extra data structures that grow with input size.
 * 
 * 
 * 6. WHEN TO USE WHICH APPROACH:
 * ------------------------------
 * 
 * Use BRUTE FORCE when:
 * - Array size very small (n < 100)
 * - Array cannot be sorted (would change answer)
 * - Need to count ALL pairs (not just find one)
 * 
 * Use TWO POINTERS when:
 * - Array is sorted (or can be sorted)
 * - Need to find just ONE pair
 * - Need optimal O(n) solution
 * 
 * Use HASHMAP when:
 * - Array is NOT sorted
 * - Sorting would take too long or change answer
 * - Can afford O(n) space
 */