package day_2_dsa;

/*
 * PROBLEM 1: TWO SUM IN SORTED ARRAY
 * ===================================
 * 
 * Given SORTED array and target, find TWO numbers that sum to target.
 * Return their indices.
 * 
 * Example:
 * Input: arr = [2, 7, 11, 15], target = 9
 * Output: [0, 1]  (because 2 + 7 = 9)
 * 
 * 
 * APPROACH EXPLANATION:
 * ---------------------
 * 
 * BRUTE FORCE (What NOT to do):
 * for i = 0 to n:
 *     for j = i+1 to n:
 *         if arr[i] + arr[j] == target: return [i, j]
 * 
 * Time: O(n²)  Too slow!
 * 
 * 
 * TWO POINTER (Optimal):
 * Left = 0, Right = n-1
 * 
 * While left < right:
 *     sum = arr[left] + arr[right]
 *     
 *     if sum == target: FOUND!
 *     if sum < target: left++  (need bigger number)
 *     if sum > target: right-- (need smaller number)
 * 
 * Time: O(n) ✓
 * 
 * 
 * WHY THIS WORKS:
 * ---------------
 * Array is SORTED, so:
 * - Moving LEFT pointer → Gets BIGGER numbers
 * - Moving RIGHT pointer → Gets SMALLER numbers
 * 
 * Example: [1, 3, 5, 7, 9], target = 12
 * 
 * Step 1: L=1, R=9, sum=10 < 12 → Need bigger, move L++
 * Step 2: L=3, R=9, sum=12 = 12 → FOUND!
 */

import java.util.Arrays;


public class TwoPointerSimple {

    // METHOD 1: BRUTE FORCE (For understanding only)
    // Time: O(n²), Space: O(1)

    public static int[] twoSumBruteForce(int[]arr , int target){
        int n = arr.length;

// used int[] because we have to return an array of integers 

        for ( int i = 0; i<n ; i++){
            for (int j = i+1 ; j<n ; j++){
                if (arr[i]+arr[j]== target){
                    return new int[] {i , j}; //found
                }
            }
        }
        return new int[]{-1,-1};
    }

    // METHOD 2: TWO POINTERS (Optimal solution)
    // Time: O(n), Space: O(1)

    public static int[] twoSumTwoPointers( int arr[] , int target){
        int left = 0;
        int right = arr.length-1;

        while( left < right ) {
            
            int sum = arr[left] + arr[right];

            if (sum == target){

                return new int[] {left , right};

            }
            else if ( sum < target){

                left++; // Sum too small, need bigger number
                // Move left pointer forward (gets bigger number in sorted array)

            }else{ right--; }// Sum too large, need smaller number
                // Move right pointer backward (gets smaller number)

        }
        return new int[] {-1 , -1};

    }

public static void main(String[] args) {
        // Test case
        int[] arr = {2, 7, 11, 15};
        int target = 9;
        
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Target: " + target);
        System.out.println();
        
        // Test brute force
        int[] result1 = twoSumBruteForce(arr, target);
        System.out.println("Brute Force Result: " + Arrays.toString(result1));
        System.out.println("Numbers: " + arr[result1[0]] + " + " + arr[result1[1]] + " = " + target);
        System.out.println();
        
        // Test two pointers
        int[] result2 = twoSumTwoPointers(arr, target);
        System.out.println("Two Pointers Result: " + Arrays.toString(result2));
        System.out.println("Numbers: " + arr[result2[0]] + " + " + arr[result2[1]] + " = " + target);
    }
}




