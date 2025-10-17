package day_2_dsa;
/*
 * PROBLEM 3: CONTAINER WITH MOST WATER (Asked in Amazon, Google, Microsoft)
 * ===========================================================================
 * 
 * Given array where arr[i] = height of line at position i.
 * Find two lines that form container with MAXIMUM water.
 * 
 * Example:
 * Input: height = [1, 8, 6, 2, 5, 4, 8, 3, 7]
 * Output: 49
 * 
 * Explanation:
 * Container formed by lines at index 1 (height=8) and index 8 (height=7)
 * Width = 8 - 1 = 7
 * Height = min(8, 7) = 7
 * Area = 7 × 7 = 49
 * 
 * 
 * VISUAL REPRESENTATION:
 * ----------------------
 * Index:  0  1  2  3  4  5  6  7  8
 * Height: 1  8  6  2  5  4  8  3  7
 * 
 *         |              |        |
 *         |  |           |        |
 *         |  |           |        |
 *         |  |     |     |        |
 *         |  |     |  |  |     |  |
 *         |  |  |  |  |  |  |  |  |
 *    _____|__|__|__|__|__|__|__|__|_____
 *    0    1  2  3  4  5  6  7  8
 * 
 * Container between index 1 and 8:
 * Water fills up to height 7 (shorter line)
 * Width = 7
 * Area = 7 × 7 = 49
 * 
 * 
 * APPROACH EXPLANATION:
 * ---------------------
 * 
 * BRUTE FORCE (What NOT to do):
 * Try all pairs (i, j) and calculate area
 * Time: O(n²) ❌
 * 
 * 
 * TWO POINTERS (Optimal):
 * Start with WIDEST container (left=0, right=n-1)
 * Then SHRINK width by moving pointer of SHORTER line
 * 
 * Why? Because:
 * - Area = width × min(height[left], height[right])
 * - Width decreases as we move pointers inward
 * - Only way to get larger area is by finding TALLER line
 * - Moving shorter pointer has POTENTIAL to find taller line
 * - Moving taller pointer will NEVER increase height (limited by shorter)
 * 
 * Algorithm:
 * left = 0, right = n-1
 * maxArea = 0
 * 
 * while left < right:
 *     width = right - left
 *     height = min(arr[left], arr[right])
 *     area = width × height
 *     maxArea = max(maxArea, area)
 *     
 *     if arr[left] < arr[right]:
 *         left++   // Move shorter line
 *     else:
 *         right--  // Move shorter line
 * 
 * return maxArea
 */

import java.util.Arrays;

public class ContainerWaterMed {

    // METHOD 1: BRUTE FORCE (For understanding)
    // Time: O(n²), Space: O(1)

    public static int maxAreaBruteForce(int[] height){
        int n = height.length;
        int maxArea = 0;

        for (int i = 0; i < n ; i++ ){
            for(int j = i+1; j< n ; j++){

                int width = j - i;
                int h = Math.min(height[i], height[j]);

                int area = width * h;

                maxArea = Math.max(maxArea , area);
            }
        }
        return maxArea;
    }
    // METHOD 2: TWO POINTERS (Optimal)
    // Time: O(n), Space: O(1)

    public static int maxAreaTwoPointers(int[] height){
        int left = 0;
        int right = height.length-1;
        int maxArea = 0;

        while(left < right){// Keep moving pointers inward

            int width = right - left;

            int h = Math.min(height[left], height[right]);

            int area = width * h;

            maxArea = Math.max(maxArea , area);
// Move pointer of SHORTER line (has potential to find taller line)
            if(height[left]<height[right]){
                left++;
            }else{
                right--;
            }
        }return maxArea;
    }
    public static void main(String[] args) {
        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        
        System.out.println("Heights: " + Arrays.toString(height));
        System.out.println();
        
        // Brute force
        int result1 = maxAreaBruteForce(height);
        System.out.println("Brute Force: Max area = " + result1);
        
        // Two pointers
        int result2 = maxAreaTwoPointers(height);
        System.out.println("Two Pointers: Max area = " + result2);
    }
    
}
/*
 * COMPLEXITY ANALYSIS:
 * ====================
 * 
 * BRUTE FORCE:
 * - Nested loops: O(n²)
 * - Space: O(1)
 * 
 * TWO POINTERS:
 * - Single pass: O(n)
 * - Space: O(1)
 * 
 * 
 * DRY RUN EXAMPLE:
 * ================
 * height = [1, 8, 6, 2, 5, 4, 8, 3, 7]
 *           0  1  2  3  4  5  6  7  8
 * 
 * Initial: left=0, right=8
 * width = 8, h = min(1, 7) = 1, area = 8×1 = 8
 * height[0]=1 < height[8]=7 → Move left++
 * maxArea = 8
 * 
 * left=1, right=8
 * width = 7, h = min(8, 7) = 7, area = 7×7 = 49
 * height[1]=8 > height[8]=7 → Move right--
 * maxArea = 49
 * 
 * left=1, right=7
 * width = 6, h = min(8, 3) = 3, area = 6×3 = 18
 * height[1]=8 > height[7]=3 → Move right--
 * maxArea = 49
 * 
 * Continue... Final maxArea = 49
 * 
 * 
 * WHY MOVE SHORTER LINE?
 * ======================
 * 
 * Current state: left=1 (h=8), right=8 (h=7)
 * Area = 7 × 7 = 49
 * 
 * Option 1: Move left (taller line)
 * - Width decreases
 * - Height still limited by right (7)
 * - Area can ONLY decrease or stay same
 * 
 * Option 2: Move right (shorter line)
 * - Width decreases
 * - But height MIGHT increase (if we find line > 7)
 * - Area has POTENTIAL to increase
 * 
 * Therefore: Always move shorter line!
 * 
 * 
 * PREBUILT METHODS USED:
 * ======================
 * 
 * 1. Math.min(a, b)
 *    - Returns smaller of two numbers
 *    - Example: Math.min(5, 3) = 3
 * 
 * 2. Math.max(a, b)
 *    - Returns larger of two numbers
 *    - Example: Math.max(5, 3) = 5
 * 
 * 
 * REMEMBER:
 * =========
 * ✓ Start with WIDEST container
 * ✓ Area = width × min(height[left], height[right])
 * ✓ Move pointer of SHORTER line
 * ✓ O(n) time, O(1) space
 * ✓ Very common in FAANG interviews!
 */