/*
 * ============================================================================
 *              MODULE 2.2: REMOVE DUPLICATES FROM SORTED ARRAY
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * Given a SORTED array, remove duplicates IN-PLACE such that each element 
 * appears only ONCE. Return the new length.
 * 
 * IMPORTANT: 
 * - Modify array IN-PLACE (don't create new array)
 * - Order of elements should be maintained
 * - Don't worry about elements beyond new length
 * 
 * EXAMPLE 1:
 * Input:  arr = [1, 1, 2]
 * Output: length = 2, arr = [1, 2, _]
 * Explanation: First 2 elements are unique. Third element doesn't matter.
 * 
 * EXAMPLE 2:
 * Input:  arr = [0, 0, 1, 1, 1, 2, 2, 3, 3, 4]
 * Output: length = 5, arr = [0, 1, 2, 3, 4, _, _, _, _, _]
 * 
 * 
 * MATHEMATICAL INTUITION:
 * -----------------------
 * Array: [1, 1, 2, 2, 3]
 * 
 * We need TWO pointers:
 * 1. SLOW pointer (i): Points to position where next unique element should go
 * 2. FAST pointer (j): Scans through array to find unique elements
 * 
 * Initial state:
 * [1, 1, 2, 2, 3]
 *  i  j
 * 
 * Rule: If arr[j] != arr[i], we found new unique element!
 *       Move i forward and copy arr[j] to arr[i]
 * 
 * Step-by-step:
 * 
 * Start: i=0, j=0
 * [1, 1, 2, 2, 3]
 *  i  j
 * arr[j]=1, arr[i]=1 → Same, just move j
 * 
 * i=0, j=1
 * [1, 1, 2, 2, 3]
 *  i     j
 * arr[j]=1, arr[i]=1 → Same, move j
 * 
 * i=0, j=2
 * [1, 1, 2, 2, 3]
 *  i        j
 * arr[j]=2, arr[i]=1 → Different! Found new unique!
 * Move i++, copy arr[j] to arr[i]
 * 
 * i=1, j=2
 * [1, 2, 2, 2, 3]
 *     i     j
 * 
 * Continue...
 * 
 * 
 * KEY INSIGHT:
 * -----------
 * - SLOW pointer (i): Maintains boundary of unique elements
 * - FAST pointer (j): Explores array to find new unique elements
 * - We compare arr[j] with arr[i] (not arr[j-1]) because i points to last unique
 */

import java.util.Scanner;
import java.util.Arrays;

public class RemoveDuplicatesSortedArray {
    
    // ========================================================================
    //                    APPROACH 1: BRUTE FORCE (Extra Space)
    // ========================================================================
    // Create new array, copy only unique elements
    // Time: O(n), Space: O(n)
    // NOT what problem asks (wants in-place), but good to understand
    
    public static int removeDuplicatesBruteForce(int[] arr) {
        int n = arr.length;
        
        if (n == 0) return 0;  // Edge case: empty array
        
        System.out.println("\n--- BRUTE FORCE (Using Extra Array) ---");
        
        // Create temporary array to store unique elements
        int[] temp = new int[n];
        int uniqueCount = 0;
        
        // Add first element (always unique)
        temp[uniqueCount++] = arr[0];
        System.out.printf("Added arr[0]=%d to temp%n", arr[0]);
        
        // Check remaining elements
        for (int i = 1; i < n; i++) {
            // Compare with last added unique element
            if (arr[i] != temp[uniqueCount - 1]) {
                temp[uniqueCount++] = arr[i];
                System.out.printf("arr[%d]=%d is unique, added to temp%n", i, arr[i]);
            } else {
                System.out.printf("arr[%d]=%d is duplicate, skipped%n", i, arr[i]);
            }
        }
        
        // Copy back to original array
        System.out.println("\nCopying unique elements back to original array:");
        for (int i = 0; i < uniqueCount; i++) {
            arr[i] = temp[i];
        }
        System.out.println("Result: " + Arrays.toString(arr));
        
        return uniqueCount;
    }
    
    
    // ========================================================================
    //                 APPROACH 2: TWO POINTERS (IN-PLACE) - OPTIMAL
    // ========================================================================
    // Use slow and fast pointers
    // Time: O(n), Space: O(1)
    
    public static int removeDuplicatesTwoPointers(int[] arr) {
        int n = arr.length;
        
        // Edge case: empty array
        if (n == 0) return 0;
        
        System.out.println("\n--- TWO POINTERS (In-Place) ---");
        
        // i = SLOW pointer (boundary of unique elements)
        // j = FAST pointer (scanning for unique elements)
        int i = 0;  // Start from first position
        
        System.out.println("Initial array: " + Arrays.toString(arr));
        System.out.println("\nScanning process:");
        
        // j starts from 1 (compare with i=0)
        for (int j = 1; j < n; j++) {
            
            System.out.printf("Step %d: i=%d, j=%d | ", j, i, j);
            System.out.printf("arr[i]=%d, arr[j]=%d | ", arr[i], arr[j]);
            
            // If current element is different from last unique
            if (arr[j] != arr[i]) {
                // Found new unique element!
                i++;  // Move boundary forward
                arr[i] = arr[j];  // Place new unique at boundary
                
                System.out.printf("Different! Move i to %d, arr[%d]=%d%n", 
                                i, i, arr[i]);
                System.out.println("     Array now: " + Arrays.toString(arr));
            } else {
                // Duplicate found, just move j
                System.out.println("Duplicate, skip");
            }
        }
        
        // Length of unique elements = i + 1 (because i is index, length is index+1)
        int uniqueLength = i + 1;
        
        System.out.println("\nFinal unique section: " + 
                         Arrays.toString