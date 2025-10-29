/*
 * PROBLEM 1: KTH LARGEST ELEMENT IN ARRAY (Simple - Amazon favorite!)
 * ====================================================================
 * 
 * Given an unsorted array and integer K,
 * find the Kth LARGEST element.
 * 
 * Example 1:
 * Input: nums = [3, 2, 1, 5, 6, 4], k = 2
 * Output: 5
 * Explanation: Sorted = [6, 5, 4, 3, 2, 1]
 *              2nd largest = 5
 * 
 * Example 2:
 * Input: nums = [3, 2, 3, 1, 2, 4, 5, 5, 6], k = 4
 * Output: 4
 * Explanation: Sorted = [6, 5, 5, 4, 3, 3, 2, 2, 1]
 *              4th largest = 4
 * 
 * 
 * THE KEY INSIGHT (Why use Heap):
 * --------------------------------
 * 
 * We DON'T need to sort entire array!
 * We only need Kth largest.
 * 
 * TRICK: Maintain MIN HEAP of size K
 * - Min heap keeps smallest at top
 * - If size > K, remove smallest
 * - After processing all, top = Kth largest!
 * 
 * WHY MIN HEAP NOT MAX HEAP?
 * ---------------------------
 * 
 * Min Heap approach (CORRECT):
 * Keep K largest elements
 * Top of min heap = Kth largest
 * Size: K elements only
 * 
 * Max Heap approach (WRONG for this):
 * Would need entire array
 * Then remove K-1 elements
 * Size: n elements → inefficient!
 * 
 * 
 * VISUAL EXAMPLE:
 * ---------------
 * nums = [3, 2, 1, 5, 6, 4], k = 2
 * 
 * Min Heap (size = 2):
 * 
 * Add 3: [3]
 * Add 2: [2, 3]           (size = 2, full!)
 * Add 1: [2, 3] (1 < 2, skip)
 * Add 5: [3, 5] (remove 2, add 5)
 * Add 6: [5, 6] (remove 3, add 6)
 * Add 4: [5, 6] (4 < 5, skip)
 * 
 * Top of heap = 5 = 2nd largest ✓
 */

import java.util.Arrays;
import java.util.PriorityQueue;

public class kth_largest_number_easy {
    
    
    // ================================================================
    //           APPROACH 1: SORTING (Brute Force)
    // ================================================================
    // Time: O(n log n) - Full sort
    // Space: O(1) or O(n) depending on sort algorithm

    public static int findKthLargestSort(int[] nums , int k){
        Arrays.sort(nums);
        // Kth largest = (n - k)th index in sorted array
        // Example: [1,2,3,4,5,6], k=2
        // 2nd largest = index 4 = nums[6-2] = nums[4] = 5

        return nums[nums.length-k];
    }


    // ================================================================
    //           APPROACH 2: MIN HEAP (Optimal for K << n)
    // ================================================================
    // Time: O(n log k) - Better than O(n log n) when k is small!
    // Space: O(k) - Only K elements in heap

    public static int findKthLargestMinHeap(int[] nums , int k){
        // CREATE MIN HEAP
        // PriorityQueue in Java = Min Heap by default
        // Smallest element always at top
//The auto-balancing mechanism (Heapify)

//offer(num) → adds element to the end, then percolates up (bubbles up) until parent ≤ child.

//poll() → removes top (root), replaces with last element, then percolates down until heap property restored.

//All that happens automatically, so you never manually sort anything

        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        System.out.println("Building min heap of size " + k + ":");
        System.out.println("Keep K largest elements, smallest on top");
        System.out.println();

        for (int i = 0 ; i < nums.length ; i++){
            int num = nums[i];

            System.out.print("Processing " + num + ": ");
            
            // CASE 1: Heap not full yet (size < k)
            
            if(minHeap.size()<k){
                minHeap.offer(num); // offer = add
                System.out.println("Added to heap. Heap: " + minHeap);
            }
            // CASE 2: Heap full (size = k)

            else{
                // Check if current number larger than smallest in heap

                if(num > minHeap.peek()){

                    minHeap.poll(); // poll = remove top
                    minHeap.offer(num);

                }else{
                    System.out.println("Skipped (smaller than top). Heap: " + minHeap);
                }
            }

        }

        System.out.println();
        System.out.println("Final heap: " + minHeap);
        System.out.println("Top element = Kth largest = " + minHeap.peek());
        
        // Top of min heap = Kth largest
        return minHeap.peek();

//We’re using a Min Heap of size K.
//→ That means at all times, the heap will contain the K largest elements we’ve seen so far.
//→ The smallest among those K elements sits at the top (peek()).



    }
    // ================================================================
    //           APPROACH 3: MAX HEAP (Less efficient but valid)
    // ================================================================
    // Time: O(n log n) - Add all, then remove K-1
    // Space: O(n) - All elements in heap
    
    public static int findKthLargestMaxHeap(int[] nums , int k){
         // CREATE MAX HEAP
        // Collections.reverseOrder() = reverse comparator = max heap
        
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        
        //Add all the elements

        for(int num:nums){
            maxHeap.offer(num);
        }

        // Remove top k-1 elements

        for (int i = 0 ; i < k - 1 ; i ++){
            maxHeap.poll();
        }

        //top now = kth largest 

        return maxHeap.peek();

    } 

    // ================================================================
    //                         MAIN METHOD
    // ================================================================
    
    public static void main(String[] args) {
        int[] nums = {3, 2, 1, 5, 6, 4};
        int k = 2;
        
        System.out.println("=".repeat(70));
        System.out.println("KTH LARGEST ELEMENT PROBLEM");
        System.out.println("=".repeat(70));
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("K = " + k + " (find 2nd largest)");
        System.out.println("=".repeat(70));
        System.out.println();
        
        // Approach 1: Sorting
        System.out.println("APPROACH 1: SORTING");
        System.out.println("-".repeat(70));
        int result1 = findKthLargestSort(nums.clone(), k);
        System.out.println("Result: " + result1);
        System.out.println();
        
        // Approach 2: Min Heap (BEST!)
        System.out.println("APPROACH 2: MIN HEAP (OPTIMAL)");
        System.out.println("-".repeat(70));
        int result2 = findKthLargestMinHeap(nums, k);
        System.out.println("\nResult: " + result2);
        System.out.println();
        
        // Approach 3: Max Heap
        System.out.println("APPROACH 3: MAX HEAP");
        System.out.println("-".repeat(70));
        int result3 = findKthLargestMaxHeap(nums, k);
        System.out.println("Result: " + result3);
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY COMPARISON:");
        System.out.println("=".repeat(70));
        System.out.println("Sorting:   Time O(n log n), Space O(1)");
        System.out.println("Min Heap:  Time O(n log k), Space O(k) ← BEST when k << n");
        System.out.println("Max Heap:  Time O(n log n), Space O(n)");
        System.out.println("=".repeat(70));
    }

}
/*
 * ================================================================
 *                    DETAILED WALKTHROUGH
 * ================================================================
 * 
 * EXAMPLE: nums = [3, 2, 1, 5, 6, 4], k = 2
 * 
 * MIN HEAP APPROACH (STEP BY STEP):
 * ----------------------------------
 * 
 * Goal: Keep K largest elements in min heap
 * Top of min heap = Kth largest
 * 
 * Initial: minHeap = [], k = 2
 * 
 * Step 1: Process 3
 *   Heap size < 2 → Add 3
 *   minHeap = [3]
 * 
 * Step 2: Process 2
 *   Heap size < 2 → Add 2
 *   minHeap = [2, 3]  (Min heap: 2 at top)
 *   NOW HEAP IS FULL (size = k)
 * 
 * Step 3: Process 1
 *   Is 1 > top (2)? NO
 *   Skip 1 (not in top 2 largest)
 *   minHeap = [2, 3]
 * 
 * Step 4: Process 5
 *   Is 5 > top (2)? YES
 *   Remove 2, Add 5
 *   minHeap = [3, 5]  (3 at top)
 *   (2 is out, 5 is in → we keep 2 largest)
 * 
 * Step 5: Process 6
 *   Is 6 > top (3)? YES
 *   Remove 3, Add 6
 *   minHeap = [5, 6]  (5 at top)
 * 
 * Step 6: Process 4
 *   Is 4 > top (5)? NO
 *   Skip 4
 *   minHeap = [5, 6]
 * 
 * FINAL: Top of heap = 5 = 2nd largest ✓
 * 
 * Largest elements in array: 6, 5
 * 2nd largest = 5 ✓
 * 
 * 
 * WHY MIN HEAP WORKS:
 * -------------------
 * 
 * Min heap of size K keeps K LARGEST elements.
 * Smallest of these K elements is at top.
 * That smallest = Kth largest overall!
 * 
 * Think of it:
 * Top 2 largest: [6, 5]
 * Min of top 2 = 5 = 2nd largest ✓
 * 
 * Top 3 largest: [6, 5, 4]
 * Min of top 3 = 4 = 3rd largest ✓
 * 
 * 
 * COMPLEXITY ANALYSIS:
 * ====================
 * 
 * MIN HEAP APPROACH:
 * ------------------
 * Time: O(n log k)
 * - Process n elements: O(n)
 * - Each heap operation: O(log k)
 * - Total: O(n log k)
 * 
 * When k = 10 and n = 1,000,000:
 * O(n log k) = O(1,000,000 × 3.3) ≈ 3.3 million operations
 * 
 * vs Sorting:
 * O(n log n) = O(1,000,000 × 20) ≈ 20 million operations
 * 
 * MIN HEAP IS 6x FASTER!
 * 
 * Space: O(k) - Only K elements in heap
 * 
 * 
 * SORTING APPROACH:
 * -----------------
 * Time: O(n log n)
 * Space: O(1) if in-place sort
 * 
 * Simple but slower when k << n
 * 
 * 
 * MAX HEAP APPROACH:
 * ------------------
 * Time: O(n log n) - Add all + remove K-1
 * Space: O(n) - All elements in heap
 * 
 * Not efficient but valid
 * 
 * 
 * WHEN TO USE WHICH:
 * ==================
 * 
 * Use MIN HEAP when:
 * ✓ k is SMALL (k << n)
 * ✓ k = 10 and n = 1,000,000 → Perfect!
 * ✓ Want O(k) space
 * ✓ Streaming data (can't sort entire array)
 * 
 * Use SORTING when:
 * ✓ k is LARGE (k ≈ n)
 * ✓ k = n/2 → Sorting might be faster
 * ✓ Simple code is priority
 * 
 * 
 * PRIORITY QUEUE OPERATIONS EXPLAINED:
 * ====================================
 * 
 * 1. offer(element) / add(element)
 *    - Add element to heap
 *    - Maintains heap property
 *    - Time: O(log k)
 *    - offer preferred (returns boolean)
 * 
 * 2. peek()
 *    - Look at top element (don't remove)
 *    - Min heap: returns smallest
 *    - Max heap: returns largest
 *    - Time: O(1)
 * 
 * 3. poll() / remove()
 *    - Remove and return top element
 *    - Reorganizes heap
 *    - Time: O(log k)
 * 
 * 4. size()
 *    - Returns number of elements
 *    - Time: O(1)
 * 
 * 5. isEmpty()
 *    - Check if heap empty
 *    - Time: O(1)
 * 
 * 
 * INTERVIEW TIPS:
 * ===============
 * 
 * 1. Ask about K vs N:
 *    "Is K much smaller than N?"
 *    If yes → Min heap optimal
 * 
 * 2. Clarify Kth largest:
 *    "Is 1st largest the maximum?"
 *    "Can we have duplicates?"
 * 
 * 3. Mention optimization:
 *    "We can optimize from O(n log n) to O(n log k)"
 * 
 * 4. Explain why min heap:
 *    "Min heap keeps K largest, top is Kth largest"
 * 
 * 
 * VARIATIONS:
 * ===========
 * 
 * 1. Kth Smallest:
 *    Use MAX HEAP of size K
 *    Keep K smallest, top is Kth smallest
 * 
 * 2. Kth Largest in Stream:
 *    Maintain min heap of size K
 *    Add new elements, maintain size
 * 
 * 3. Find Median:
 *    Use TWO heaps (next problem!)
 * 
 * 
 * REMEMBER:
 * =========
 * ✓ Kth LARGEST → Use MIN HEAP of size K
 * ✓ Kth SMALLEST → Use MAX HEAP of size K
 * ✓ Min heap keeps smallest at top
 * ✓ Time: O(n log k) vs O(n log n)
 * ✓ Space: O(k) vs O(1) or O(n)
 * ✓ Best when k << n
 */

