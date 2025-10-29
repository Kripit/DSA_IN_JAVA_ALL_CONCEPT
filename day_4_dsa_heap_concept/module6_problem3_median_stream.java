/*
 * PROBLEM 3: MEDIAN FROM DATA STREAM (Hard - Google, Amazon, Facebook!)
 * ======================================================================
 * 
 * Design a data structure that supports:
 * 1. addNum(int num) - Add integer to data stream
 * 2. findMedian() - Return median of all elements so far
 * 
 * MEDIAN:
 * - Middle element when sorted
 * - If even count: average of two middle elements
 * 
 * Example:
 * addNum(1)    → data = [1],         median = 1
 * addNum(2)    → data = [1, 2],      median = (1+2)/2 = 1.5
 * addNum(3)    → data = [1, 2, 3],   median = 2
 * 
 * 
 * THE KEY INSIGHT (TWO HEAP TECHNIQUE):
 * -------------------------------------
 * 
 * Don't sort every time! Use TWO HEAPS:
 * 
 * MAX HEAP (left side):  Stores smaller half
 * MIN HEAP (right side): Stores larger half
 * 
 * VISUALIZATION:
 * 
 *     MAX HEAP          MIN HEAP
 *   (smaller half)   (larger half)
 *   
 *        [5]              [8]
 *       /   \            /   \
 *      3     4          9    10
 *     / \
 *    1   2
 * 
 * Top of MAX = 5 (largest of small half)
 * Top of MIN = 8 (smallest of large half)
 * 
 * MEDIAN:
 * - If sizes equal: (5 + 8) / 2 = 6.5
 * - If left bigger: 5 (top of max heap)
 * 
 * 
 * WHY THIS WORKS:
 * ---------------
 * 
 * Imagine sorted array: [1, 2, 3, 4, 5 | 6, 7, 8, 9, 10]
 *                        ← MAX HEAP →   ← MIN HEAP →
 * 
 * Median = middle element(s)
 * Max heap top = largest of left half (closest to median)
 * Min heap top = smallest of right half (closest to median)
 * 
 * BALANCE RULE:
 * - Sizes can differ by at most 1
 * - If equal: median = average of tops
 * - If unequal: median = top of larger heap
 * 
 * 
 * ALGORITHM:
 * ----------
 * 
 * addNum(num):
 * 1. Add to appropriate heap
 * 2. Balance heaps (ensure size difference ≤ 1)
 * 
 * findMedian():
 * 1. If sizes equal: (maxHeap.top + minHeap.top) / 2
 * 2. If unequal: top of larger heap
 */

import java.util.PriorityQueue;
import java.util.Collections;

public class MedianFinder {
    
    // MAX HEAP: Stores smaller half of numbers
    // Java's PriorityQueue is min heap by default
    // Collections.reverseOrder() makes it max heap
    private PriorityQueue<Integer> maxHeap;  // Left side
    
    // MIN HEAP: Stores larger half of numbers
    private PriorityQueue<Integer> minHeap;  // Right side
    
    
    // CONSTRUCTOR: Initialize both heaps
    public MedianFinder() {
        // MAX HEAP (largest element at top)
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        
        // MIN HEAP (smallest element at top)
        minHeap = new PriorityQueue<>();
        
        System.out.println("MedianFinder initialized!");
        System.out.println("MAX HEAP (left/smaller): stores smaller half");
        System.out.println("MIN HEAP (right/larger): stores larger half");
        System.out.println();
    }
    
    
    // ADD NUMBER: O(log n)
    public void addNum(int num) {
        System.out.println("=".repeat(60));
        System.out.println("Adding: " + num);
        System.out.println("-".repeat(60));
        
        // STEP 1: Decide which heap to add to
        
        // If max heap empty OR num <= max heap top
        // Add to max heap (left/smaller side)
        if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
            maxHeap.offer(num);
            System.out.println("Added to MAX HEAP (smaller half)");
        } else {
            // num > max heap top
            // Add to min heap (right/larger side)
            minHeap.offer(num);
            System.out.println("Added to MIN HEAP (larger half)");
        }
        
        System.out.println("Before balance:");
        printHeaps();
        
        
        // STEP 2: Balance heaps
        // Ensure size difference is at most 1
        
        // Case 1: Left side too big
        if (maxHeap.size() > minHeap.size() + 1) {
            int moved = maxHeap.poll();
            minHeap.offer(moved);
            System.out.println("Balanced: Moved " + moved + " from MAX to MIN");
        }
        // Case 2: Right side too big
        else if (minHeap.size() > maxHeap.size()) {
            int moved = minHeap.poll();
            maxHeap.offer(moved);
            System.out.println("Balanced: Moved " + moved + " from MIN to MAX");
        } else {
            System.out.println("Already balanced!");
        }
        
        System.out.println("\nAfter balance:");
        printHeaps();
        System.out.println("=".repeat(60));
        System.out.println();
    }
    
    
    // FIND MEDIAN: O(1)
    public double findMedian() {
        int leftSize = maxHeap.size();
        int rightSize = minHeap.size();
        
        System.out.println("Finding median:");
        System.out.println("MAX HEAP size: " + leftSize);
        System.out.println("MIN HEAP size: " + rightSize);
        
        // CASE 1: Equal sizes
        if (leftSize == rightSize) {
            // Median = average of two middle elements
            double median = (maxHeap.peek() + minHeap.peek()) / 2.0;
            System.out.println("Equal sizes → median = (" + maxHeap.peek() + 
                             " + " + minHeap.peek() + ") / 2 = " + median);
            return median;
        }
        // CASE 2: Left side bigger (max heap has more)
        else {
            // Median = top of max heap
            double median = maxHeap.peek();
            System.out.println("Left bigger → median = " + median);
            return median;
        }
    }
    
    
    // Helper: Print both heaps
    private void printHeaps() {
        System.out.println("MAX HEAP (left): " + maxHeap + " size=" + maxHeap.size());
        System.out.println("MIN HEAP (right): " + minHeap + " size=" + minHeap.size());
    }
    
    
    // MAIN METHOD - Test the MedianFinder
    public static void main(String[] args) {
        MedianFinder mf = new MedianFinder();
        
        System.out.println("=".repeat(60));
        System.out.println("TEST: MEDIAN FROM DATA STREAM");
        System.out.println("=".repeat(60));
        System.out.println();
        
        // Test sequence
        int[] nums = {5, 15, 1, 3, 8, 7, 9, 10, 20, 12};
        
        for (int num : nums) {
            mf.addNum(num);
            double median = mf.findMedian();
            System.out.println("Current median: " + median);
            System.out.println("\n");
        }
        
        System.out.println("=".repeat(60));
        System.out.println("FINAL STATE");
        System.out.println("=".repeat(60));
        mf.printHeaps();
        System.out.println("Final median: " + mf.findMedian());
        System.out.println("=".repeat(60));
    }
}


/*
 * ================================================================
 *                    DETAILED WALKTHROUGH
 * ================================================================
 * 
 * EXAMPLE: Add numbers [5, 15, 1, 3]
 * 
 * STEP-BY-STEP EXECUTION:
 * -----------------------
 * 
 * Add 5:
 * ------
 * maxHeap empty, add 5 to maxHeap
 * MAX: [5], MIN: []
 * Balance: maxHeap.size=1, minHeap.size=0 (diff=1, OK)
 * Median: 5 (only maxHeap has element)
 * 
 * Add 15:
 * -------
 * 15 > maxHeap.top (5), add to minHeap
 * MAX: [5], MIN: [15]
 * Balance: sizes equal (1, 1), OK
 * Median: (5 + 15) / 2 = 10.0
 * 
 * Add 1:
 * ------
 * 1 < maxHeap.top (5), add to maxHeap
 * MAX: [5, 1], MIN: [15]
 * (Max heap internally: [5] with child [1])
 * Balance: maxHeap.size=2, minHeap.size=1 (diff=1, OK)
 * Median: 5 (top of larger maxHeap)
 * 
 * Add 3:
 * ------
 * 3 < maxHeap.top (5), add to maxHeap
 * MAX: [5, 3, 1], MIN: [15]
 * Balance: maxHeap.size=3, minHeap.size=1 (diff=2, TOO BIG!)
 * Move 5 from maxHeap to minHeap
 * MAX: [3, 1], MIN: [5, 15]
 * Now sizes: 2 and 2 (balanced!)
 * Median: (3 + 5) / 2 = 4.0
 * 
 * Sorted view: [1, 3 | 5, 15]
 *              ←MAX→  ←MIN→
 * Median = (3 + 5) / 2 = 4 ✓
 * 
 * 
 * WHY THIS WORKS (Proof):
 * =======================
 * 
 * INVARIANTS (always maintained):
 * 1. All elements in maxHeap ≤ all elements in minHeap
 * 2. |maxHeap.size - minHeap.size| ≤ 1
 * 
 * From these two invariants:
 * - Heaps represent sorted array split in middle
 * - Tops are closest to median
 * - Size difference ensures we know where median is
 * 
 * 
 * BALANCING LOGIC EXPLAINED:
 * ==========================
 * 
 * After adding element, sizes can be:
 * 
 * Case 1: maxHeap.size = minHeap.size
 * → Balanced! Do nothing.
 * 
 * Case 2: maxHeap.size = minHeap.size + 1
 * → Balanced! (One extra on left is OK)
 * 
 * Case 3: maxHeap.size > minHeap.size + 1
 * → Unbalanced! Move top from max to min
 * 
 * Case 4: minHeap.size > maxHeap.size
 * → Unbalanced! (Right shouldn't be bigger)
 * → Move top from min to max
 * 
 * Why allow maxHeap to be bigger by 1?
 * → Convention: when odd count, maxHeap gets extra
 * → Makes median finding consistent
 * 
 * 
 * COMPLEXITY ANALYSIS:
 * ====================
 * 
 * addNum(num):
 * -----------
 * - Add to heap: O(log n)
 * - Balance (move between heaps): O(log n)
 * - Total: O(log n)
 * 
 * findMedian():
 * ------------
 * - Just peek at tops: O(1)
 * 
 * Space: O(n) - Store all n numbers in heaps
 * 
 * 
 * ALTERNATIVE APPROACHES:
 * =======================
 * 
 * 1. Sort every time:
 *    - addNum: O(n log n) - sort entire array
 *    - findMedian: O(1) - access middle
 *    - Total: O(n² log n) for n additions
 *    - BAD!
 * 
 * 2. Keep sorted (insertion sort):
 *    - addNum: O(n) - find position, insert
 *    - findMedian: O(1) - access middle
 *    - Total: O(n²) for n additions
 *    - Better but still slow
 * 
 * 3. Two heaps (our approach):
 *    - addNum: O(log n)
 *    - findMedian: O(1)
 *    - Total: O(n log n) for n additions
 *    - OPTIMAL!
 * 
 * 
 * EDGE CASES:
 * ===========
 * 
 * 1. First element:
 *    - Add to maxHeap
 *    - Median = that element
 * 
 * 2. Two elements:
 *    - One in each heap
 *    - Median = average
 * 
 * 3. All same number:
 *    - Distribution doesn't matter
 *    - Median = that number
 * 
 * 4. Negative numbers:
 *    - Works perfectly!
 *    - Comparison still valid
 * 
 * 
 * INTERVIEW TIPS:
 * ===============
 * 
 * 1. Draw the picture:
 *    - Show two heaps visually
 *    - Mark tops clearly
 * 
 * 2. Explain invariants:
 *    - Left ≤ Right
 *    - Size difference ≤ 1
 * 
 * 3. Walk through example:
 *    - Add 2-3 numbers
 *    - Show balancing
 * 
 * 4. Mention complexity:
 *    - O(log n) add
 *    - O(1) median
 *    - Better than sorting
 * 
 * 5. Code clearly:
 *    - Name variables well (maxHeap, minHeap)
 *    - Comment the balancing logic
 * 
 * 
 * VARIATIONS:
 * ===========
 * 
 * 1. Median of sliding window:
 *    - Need to remove old elements too
 *    - Use TreeMap or multiset
 * 
 * 2. K-th percentile:
 *    - Same concept
 *    - Adjust size ratio (not 50-50)
 * 
 * 3. Running average:
 *    - Easier! Just sum/count
 *    - Don't need heaps
 * 
 * 
 * REMEMBER:
 * =========
 * ✓ Two heaps: MAX (left) + MIN (right)
 * ✓ MAX heap stores smaller half
 * ✓ MIN heap stores larger half
 * ✓ Balance: size difference ≤ 1
 * ✓ Median: average of tops or top of larger
 * ✓ addNum: O(log n), findMedian: O(1)
 * ✓ This is THE optimal solution!
 */