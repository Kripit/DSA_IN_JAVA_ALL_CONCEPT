/*
 * PROBLEM 2: TOP K FREQUENT ELEMENTS (Medium - Amazon, Google, Facebook!)
 * ========================================================================
 * 
 * Given array of integers and integer K,
 * return the K MOST FREQUENT elements.
 * 
 * Example 1:
 * Input: nums = [1, 1, 1, 2, 2, 3], k = 2
 * Output: [1, 2]
 * Explanation: 
 * 1 appears 3 times
 * 2 appears 2 times  
 * 3 appears 1 time
 * Top 2 frequent = [1, 2]
 * 
 * Example 2:
 * Input: nums = [1], k = 1
 * Output: [1]
 * 
 * 
 * THE KEY INSIGHT (TWO-STEP APPROACH):
 * ------------------------------------
 * 
 * STEP 1: COUNT FREQUENCIES
 * Use HashMap to count each number's frequency
 * {1: 3, 2: 2, 3: 1}
 * 
 * STEP 2: FIND TOP K FREQUENT
 * Use Min Heap to keep K most frequent
 * Compare by frequency, not by value!
 * 
 * WHY MIN HEAP?
 * -------------
 * We want TOP K by frequency
 * Min heap of size K keeps K highest frequencies
 * Top = Kth highest frequency
 * 
 * 
 * VISUAL EXAMPLE:
 * ---------------
 * nums = [1,1,1,2,2,3], k = 2
 * 
 * Step 1: Count frequencies
 * {1:3, 2:2, 3:1}
 * 
 * Step 2: Build min heap (by frequency)
 * Add (1, freq=3): [(1,3)]
 * Add (2, freq=2): [(2,2), (1,3)]  ← Min heap by frequency
 * Add (3, freq=1): [(2,2), (1,3)]  ← 1 < 2, skip (heap full)
 * 
 * Result: [1, 2]
 * 
 * 
 * CRITICAL: CUSTOM COMPARATOR
 * ----------------------------
 * 
 * We need to compare by FREQUENCY, not by VALUE!
 * 
 * Wrong: PriorityQueue<Integer> pq = new PriorityQueue<>();
 * (This compares values: 1 < 2 < 3)
 * 
 * Right: Compare by frequency in HashMap!
 * PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> map.get(a) - map.get(b));
 */

import java.util.*;


public class topKFrequentmid {

    
    // ================================================================
    //        APPROACH 1: SORTING (Brute Force Understanding)
    // ================================================================
    // Time: O(n log n) - Due to sorting
    // Space: O(n) - HashMap
    
    public static int[] topKFrequentSort(int[] nums , int k){

        // step 1 : count frequencies
    // ===============================
    // HashMap = key-value store (key = number, value = count of how many times it appears)
    // Example: nums = [1,1,1,2,2,3]
    // After loop: freqMap = {1=3, 2=2, 3=1}

        HashMap<Integer , Integer> freqMap = new HashMap<>();

        for (int num : nums ){

        // getOrDefault(num, 0) → returns current count if exists else 0

        // Update the frequency count of the current number 'num'
        // --------------------------------------------------------
        // freqMap = HashMap storing <number, frequency>
        freqMap.put(
        num,                               // KEY: the current number we are processing
        freqMap.getOrDefault(num, 0) + 1);   // VALUE: existing frequency + 1
        

        }
        // STEP 2: Convert to list and sort by frequency

        List<Integer> unique = new ArrayList<>(freqMap.keySet());

        // get all unique numbers(keys) from map into a list
        // freqMap.keySet() → gives u all keys ( unique number ) which is hashMap first integer value 

        // Sort unique numbers based on their frequency (descending order)
        // freqMap.get(b) - freqMap.get(a) → higher freq first
        // freqMap.get(x) gives how many times x appeared.

        unique.sort((a,b) -> freqMap.get(b) - freqMap.get(a));
        // Sort the list 'unique' based on frequency of each number (descending)
        // freqMap.get(b) - freqMap.get(a):
        // → compares how many times 'b' and 'a' appeared
        // → puts higher frequency first (so most frequent numbers come first)
        //.get(a) / .get(b) → fetches frequency from the HashMap for that number
         
        int [] result = new int[k];
        for (int i = 0 ; i< k ; i++ ){
            result[i] = unique.get(i);
        }
         
        return result;
    } 
    
    // ================================================================
    //        APPROACH 2: MIN HEAP (Optimal - O(n log k))
    // ================================================================
    // Time: O(n log k) - Better than sorting!
    // Space: O(n) - HashMap + O(k) heap

    public static int[] topKFrequentHeap(int[] nums, int k) {

        // STEP 1: Count frequencies using HashMap
        // Key = number, Value = frequency
        HashMap<Integer, Integer> freqMap = new HashMap<>();


        System.out.println("STEP 1: Counting frequencies");
        System.out.println("-".repeat(60));


        for ( int num : nums){
            // getOrDefault: if key exists return value, else return default (0)
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);           
        }
        
        System.out.println("Frequency map: " + freqMap);
        System.out.println();

        // STEP 2: Create MIN HEAP (compare by frequency)
        // CRITICAL: Custom comparator to compare by frequency!

        PriorityQueue<Integer> minHeap = new PriorityQueue<>(
            (a, b)-> freqMap.get(a) - freqMap.get(b)
            // This lambda compares FREQUENCIES
            // a and b are NUMBERS (keys)
            // freqMap.get(a) = frequency of number a
            // If freq(a) < freq(b): returns negative → a comes first (min heap)
            //smaller frequency → higher priority (stays at top)
        );
        System.out.println("STEP 2: Building min heap (size = " + k + ")");
        System.out.println("-".repeat(60));
        
        // Process each unique number
        for (int num : freqMap.keySet()) {
            System.out.print("Number " + num + " (freq=" + freqMap.get(num) + "): ");

            //ADD to heap

            minHeap.offer(num);
            
            // if size exceeds k , remove smallest frequency

            if (minHeap.size() > k){
                int removed = minHeap.poll();
                System.out.print("Removed " + removed + " (freq=" + freqMap.get(removed) + ")");       
            }

            System.out.println(" | Heap: " + minHeap);
        }
        System.out.println();
        System.out.println("Final heap (K most frequent): " + minHeap);


        // Step 3 : Extract result from heap

        int[] result = new int[k];
        for ( int i = 0 ; i < k ; i++ ){
            result[i] = minHeap.poll();
        }

        return result;
    }

    // ================================================================
    //        APPROACH 3: BUCKET SORT (Advanced - O(n))
    // ================================================================
    // Time: O(n) - Linear time!
    // Space: O(n)
    // Note: Only works because frequencies are bounded by n
    
    public static int[] topKFrequentBucket(int[] nums, int k) {
        int n = nums.length;
        
        // STEP 1: Count frequencies
        HashMap<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }
        
        // STEP 2: Create buckets
        // bucket[i] = list of numbers with frequency i
        // Max frequency = n (all same number)
        List<Integer>[] buckets = new List[n + 1];
        
        for (int i = 0; i <= n; i++) {
            buckets[i] = new ArrayList<>();
        }
        
        // Put each number in its frequency bucket
        for (int num : freqMap.keySet()) {
            int freq = freqMap.get(num);
            buckets[freq].add(num);
        }
        
        System.out.println("Bucket Sort Approach:");
        System.out.println("-".repeat(60));
        for (int i = n; i >= 0; i--) {
            if (!buckets[i].isEmpty()) {
                System.out.println("Frequency " + i + ": " + buckets[i]);
            }
        }
        System.out.println();
        
        // STEP 3: Collect top K from highest frequency buckets
        int[] result = new int[k];
        int index = 0;
        
        // Start from highest frequency (n) and go down
        for (int i = n; i >= 0 && index < k; i--) {
            for (int num : buckets[i]) {
                result[index++] = num;
                if (index == k) break;
            }
        }
        
        return result;
    }
    
    
    // ================================================================
    //                         MAIN METHOD
    // ================================================================
    
    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 2, 2, 3};
        int k = 2;
        
        System.out.println("=".repeat(70));
        System.out.println("TOP K FREQUENT ELEMENTS");
        System.out.println("=".repeat(70));
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("K = " + k);
        System.out.println("=".repeat(70));
        System.out.println();
        
        // Approach 1: Sorting
        System.out.println("APPROACH 1: SORTING");
        System.out.println("-".repeat(70));
        int[] result1 = topKFrequentSort(nums, k);
        System.out.println("Result: " + Arrays.toString(result1));
        System.out.println();
        
        // Approach 2: Min Heap (BEST for interviews!)
        System.out.println("APPROACH 2: MIN HEAP (OPTIMAL)");
        System.out.println("-".repeat(70));
        int[] result2 = topKFrequentHeap(nums, k);
        System.out.println("\nResult: " + Arrays.toString(result2));
        System.out.println();
        
        // Approach 3: Bucket Sort
        System.out.println("APPROACH 3: BUCKET SORT (LINEAR TIME!)");
        System.out.println("-".repeat(70));
        int[] result3 = topKFrequentBucket(nums, k);
        System.out.println("Result: " + Arrays.toString(result3));
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("COMPLEXITY COMPARISON:");
        System.out.println("=".repeat(70));
        System.out.println("Sorting:      Time O(n log n), Space O(n)");
        System.out.println("Min Heap:     Time O(n log k), Space O(n) ← BEST for interviews");
        System.out.println("Bucket Sort:  Time O(n),       Space O(n) ← Fastest!");
        System.out.println("=".repeat(70));
    }
}


/*
 * ================================================================
 *                    DETAILED WALKTHROUGH
 * ================================================================
 * 
 * EXAMPLE: nums = [1,1,1,2,2,3], k = 2
 * 
 * MIN HEAP APPROACH (STEP BY STEP):
 * ----------------------------------
 * 
 * STEP 1: Build frequency map
 * --------------------------
 * Process each number:
 * 1 → {1:1}
 * 1 → {1:2}
 * 1 → {1:3}
 * 2 → {1:3, 2:1}
 * 2 → {1:3, 2:2}
 * 3 → {1:3, 2:2, 3:1}
 * 
 * Final map: {1:3, 2:2, 3:1}
 * 
 * 
 * STEP 2: Build min heap (by frequency)
 * -------------------------------------
 * Min heap size = k = 2
 * Compare by frequency (not by number value!)
 * 
 * Process 1 (freq=3):
 *   Add to heap: [1]
 *   Size = 1 < k, keep
 * 
 * Process 2 (freq=2):
 *   Add to heap: [2, 1]  ← Min heap by frequency!
 *   (2 at top because freq(2)=2 < freq(1)=3)
 *   Size = 2 = k, heap full
 * 
 * Process 3 (freq=1):
 *   Add to heap: [3, 1, 2]
 *   Size = 3 > k, remove top!
 *   Remove 3 (freq=1, smallest)
 *   Heap: [2, 1]
 * 
 * FINAL HEAP: [2, 1] (numbers with top 2 frequencies)
 * Result: [1, 2] or [2, 1] (order doesn't matter)
 * 
 * 
 * CUSTOM COMPARATOR EXPLAINED:
 * ============================
 * 
 * (a, b) -> freqMap.get(a) - freqMap.get(b)
 * 
 * This is a LAMBDA EXPRESSION (shorthand function)
 * 
 * Parameters: a, b (two numbers to compare)
 * Returns: 
 *   - Negative if freq(a) < freq(b) → a comes first
 *   - Zero if freq(a) == freq(b)
 *   - Positive if freq(a) > freq(b) → b comes first
 * 
 * Example: Compare 1 and 2
 * a=1, b=2
 * freqMap.get(1) = 3
 * freqMap.get(2) = 2
 * Result: 3 - 2 = 1 (positive)
 * Meaning: 2 comes before 1 in min heap (2 has smaller freq)
 * 
 * 
 * BUCKET SORT APPROACH EXPLAINED:
 * ===============================
 * 
 * Key insight: Frequency range is [1, n]
 * Create n+1 buckets, one for each possible frequency
 * 
 * nums = [1,1,1,2,2,3]
 * Frequencies: {1:3, 2:2, 3:1}
 * 
 * Buckets (index = frequency):
 * bucket[0] = []
 * bucket[1] = [3]     ← Numbers with frequency 1
 * bucket[2] = [2]     ← Numbers with frequency 2
 * bucket[3] = [1]     ← Numbers with frequency 3
 * bucket[4] = []
 * ...
 * 
 * To get top K:
 * Start from highest frequency (bucket[n] down to bucket[0])
 * Collect first K numbers
 * 
 * From bucket[3]: take 1
 * From bucket[2]: take 2
 * Result: [1, 2]
 * 
 * Time: O(n) because:
 * - Count frequencies: O(n)
 * - Fill buckets: O(n)
 * - Collect K: O(n) worst case
 * Total: O(n) ← LINEAR TIME!
 * 
 * 
 * COMPLEXITY ANALYSIS:
 * ====================
 * 
 * SORTING APPROACH:
 * ----------------
 * Time: O(n log n)
 * - Count: O(n)
 * - Sort unique numbers: O(m log m) where m = unique count
 * - Worst case m = n
 * 
 * Space: O(n) for HashMap
 * 
 * 
 * MIN HEAP APPROACH:
 * -----------------
 * Time: O(n log k)
 * - Count frequencies: O(n)
 * - Process m unique numbers: O(m log k)
 * - Each heap operation: O(log k)
 * - Total: O(n + m log k) ≈ O(n log k)
 * 
 * Space: O(n) for HashMap + O(k) for heap
 * 
 * Why better than sorting?
 * When k << n (k much smaller than n)
 * log k << log n
 * Example: k=10, n=1,000,000
 * O(n log k) = O(1M × 3.3) ≈ 3.3M operations
 * O(n log n) = O(1M × 20) ≈ 20M operations
 * 6x FASTER!
 * 
 * 
 * BUCKET SORT APPROACH:
 * --------------------
 * Time: O(n)
 * - Count: O(n)
 * - Fill buckets: O(m) where m = unique
 * - Collect: O(n) worst case
 * - Total: O(n)
 * 
 * Space: O(n) for HashMap + O(n) for buckets
 * 
 * When to use?
 * - When need absolute best time
 * - When frequency range is small
 * - Advanced technique (mention in interview!)
 * 
 * 
 * HASHMAP METHODS EXPLAINED:
 * ==========================
 * 
 * 1. put(key, value)
 *    - Add or update key-value pair
 *    - Time: O(1) average
 * 
 * 2. get(key)
 *    - Get value for key
 *    - Returns null if key doesn't exist
 *    - Time: O(1) average
 * 
 * 3. getOrDefault(key, defaultValue)
 *    - Get value if exists, else return default
 *    - Useful for counting!
 *    - Time: O(1) average
 * 
 * 4. keySet()
 *    - Returns Set of all keys
 *    - Use to iterate over keys
 *    - Time: O(1) to get set
 * 
 * 5. containsKey(key)
 *    - Check if key exists
 *    - Returns boolean
 *    - Time: O(1) average
 * 
 * 
 * INTERVIEW TIPS:
 * ===============
 * 
 * 1. Ask about K vs N:
 *    "Is K much smaller than N?"
 *    If yes → Min heap optimal
 * 
 * 2. Ask about uniqueness:
 *    "Can array have duplicates?"
 *    (Always yes for this problem)
 * 
 * 3. Mention all approaches:
 *    "We can sort in O(n log n)..."
 *    "But min heap gives O(n log k)..."
 *    "And bucket sort gives O(n)..."
 * 
 * 4. Explain comparator:
 *    "We compare by frequency, not value"
 *    "That's why we need custom comparator"
 * 
 * 5. Code clearly:
 *    Min heap approach is BEST for interviews
 *    Clean, efficient, shows heap understanding
 * 
 * 
 * VARIATIONS:
 * ===========
 * 
 * 1. Top K Frequent Words:
 *    - Same logic but with strings
 *    - Need to break ties (lexicographic order)
 * 
 * 2. Kth Most Frequent Element:
 *    - Return single element
 *    - Same heap approach
 * 
 * 3. Frequency Sort:
 *    - Sort entire array by frequency
 *    - Use bucket sort or heap
 * 
 * 
 * REMEMBER:
 * =========
 * ✓ HashMap + Min Heap = Powerful combo
 * ✓ Custom comparator: (a,b) -> map.get(a) - map.get(b)
 * ✓ Time: O(n log k) better than O(n log n)
 * ✓ Keep heap size = K
 * ✓ Bucket sort gives O(n) but more complex
 * ✓ Always explain your comparator in interviews!
 */
