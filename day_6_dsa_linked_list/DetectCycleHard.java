package day_6_dsa_linked_list;

/*
 * ============================================================================
 *     PROBLEM 4: LINKED LIST CYCLE DETECTION (HARD - Floyd's Algorithm!)
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * Given head, the head of a linked list, determine if the linked list has
 * a CYCLE in it.
 * 
 * There is a cycle in a linked list if there is some node in the list that
 * can be reached again by continuously following the next pointer.
 * 
 * Return true if there is a cycle in the linked list. Otherwise, return false.
 * 
 * Example 1:
 * Input: head = [3,2,0,-4], pos = 1
 *        (tail connects to node at index 1)
 * Output: true
 * 
 * Example 2:
 * Input: head = [1,2], pos = 0
 *        (tail connects to node at index 0)
 * Output: true
 * 
 * Example 3:
 * Input: head = [1], pos = -1
 *        (no cycle)
 * Output: false
 * 
 * 
 * CONCEPT EXPLANATION:
 * --------------------
 * 
 * WHAT IS A CYCLE?
 * ----------------
 * Normal list (no cycle):
 * 1 → 2 → 3 → 4 → NULL
 * 
 * List with cycle:
 * 1 → 2 → 3 → 4
 *     ↑_________|
 * 
 * Node 4 points back to node 2!
 * If you keep following next:
 * 1 → 2 → 3 → 4 → 2 → 3 → 4 → 2 → ... (infinite loop!)
 * 
 * 
 * REAL-WORLD ANALOGY:
 * -------------------
 * Imagine two runners on a circular track:
 * - SLOW runner: runs 1 lap per hour
 * - FAST runner: runs 2 laps per hour
 * 
 * If track is circular (cycle):
 * - Fast runner will eventually LAP the slow runner
 * - They WILL meet!
 * 
 * If track has an end (no cycle):
 * - Fast runner reaches end
 * - They DON'T meet
 * 
 * This is FLOYD'S CYCLE DETECTION ALGORITHM!
 * Also called "Tortoise and Hare" algorithm
 * 
 * 
 * VISUALIZATION:
 * --------------
 * 
 * Example: 1 → 2 → 3 → 4 → 2 (cycle)
 *              ↑_________|
 * 
 * Step 0:
 * slow = 1, fast = 1
 * 
 * Step 1:
 * slow = 2 (1 step)
 * fast = 3 (2 steps)
 * slow != fast
 * 
 * Step 2:
 * slow = 3 (1 step)
 * fast = 2 (2 steps: 4 → 2)
 * slow != fast
 * 
 * Step 3:
 * slow = 4 (1 step)
 * fast = 4 (2 steps: 3 → 4)
 * slow == fast ✓ CYCLE DETECTED!
 * 
 * 
 * WHY DOES THIS WORK?
 * -------------------
 * 
 * Mathematical proof:
 * 
 * Let's say cycle starts at node C
 * Let d = distance from head to C
 * Let c = length of cycle
 * 
 * When slow enters cycle at C:
 * - slow has moved d steps
 * - fast has moved 2d steps
 * - fast is (2d - d) = d steps ahead in cycle
 * 
 * Now both in cycle:
 * - Every step, fast gains 1 position on slow
 * - Distance between them: c - (d mod c)
 * - After c - (d mod c) steps, they meet!
 * 
 * They WILL meet if cycle exists!
 */

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; next = null; }
}

public class DetectCycleHard {
    
    // ================================================================
    //    APPROACH 1: FLOYD'S ALGORITHM (Fast & Slow Pointers) - OPTIMAL!
    // ================================================================
    // Time: O(n) - Each node visited at most twice
    // Space: O(1) - Only two pointers
    
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;  // Empty or single node, no cycle
        }
        
        // Initialize slow and fast pointers
        ListNode slow = head;
        ListNode fast = head;
        
        // Move slow 1 step, fast 2 steps
        while (fast != null && fast.next != null) {
            slow = slow.next;           // Move 1 step
            fast = fast.next.next;      // Move 2 steps
            
            // If they meet, cycle exists!
            if (slow == fast) {
                return true;
            }
        }
        
        // Fast reached end, no cycle
        return false;
    }
    
    
    // ================================================================
    //    BONUS: FIND WHERE CYCLE STARTS (Advanced!)
    // ================================================================
    // Returns the node where cycle begins, or null if no cycle
    
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        
        // PHASE 1: Detect if cycle exists
        ListNode slow = head;
        ListNode fast = head;
        boolean hasCycle = false;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                hasCycle = true;
                break;
            }
        }
        
        if (!hasCycle) {
            return null;  // No cycle
        }
        
        // PHASE 2: Find cycle start
        // Reset slow to head, keep fast at meeting point
        slow = head;
        
        // Move both at same speed (1 step)
        // They will meet at cycle start!
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        
        return slow;  // This is the cycle start!
    }
    
    
    // ================================================================
    //    APPROACH 2: HASH SET (Simple but uses extra space)
    // ================================================================
    // Time: O(n)
    // Space: O(n) - Store all visited nodes
    
    public boolean hasCycleHashSet(ListNode head) {
        java.util.HashSet<ListNode> visited = new java.util.HashSet<>();
        
        ListNode current = head;
        while (current != null) {
            // If we've seen this node before, cycle!
            if (visited.contains(current)) {
                return true;
            }
            visited.add(current);
            current = current.next;
        }
        
        return false;  // Reached end, no cycle
    }
    
    
    // ================================================================
    //    HELPER METHODS FOR TESTING
    // ================================================================
    
    // Create list with cycle at given position
    public static ListNode createCycleList(int[] arr, int pos) {
        if (arr == null || arr.length == 0) return null;
        
        ListNode head = new ListNode(arr[0]);
        ListNode current = head;
        ListNode cycleNode = null;
        
        // Remember node at pos for creating cycle
        if (pos == 0) cycleNode = head;
        
        for (int i = 1; i < arr.length; i++) {
            current.next = new ListNode(arr[i]);
            current = current.next;
            if (i == pos) cycleNode = current;
        }
        
        // Create cycle if pos is valid
        if (pos >= 0 && pos < arr.length) {
            current.next = cycleNode;
        }
        
        return head;
    }
    
    public static void printListInfo(ListNode head, int pos) {
        if (head == null) {
            System.out.println("Empty list");
            return;
        }
        
        System.out.print("List: ");
        ListNode current = head;
        int count = 0;
        while (current != null && count < 10) {  // Limit to prevent infinite loop
            System.out.print(current.val);
            if (count < 9 && current.next != null) System.out.print(" → ");
            current = current.next;
            count++;
        }
        
        if (pos >= 0) {
            System.out.println(" (cycle back to index " + pos + ")");
        } else {
            System.out.println(" → NULL (no cycle)");
        }
    }
    
    
    // ================================================================
    //                         MAIN METHOD
    // ================================================================
    
    public static void main(String[] args) {
        DetectCycleHard solution = new DetectCycleHard();
        
        System.out.println("=".repeat(70));
        System.out.println("LINKED LIST CYCLE DETECTION - FLOYD'S ALGORITHM");
        System.out.println("=".repeat(70));
        System.out.println();
        
        // Test Case 1: Cycle exists (pos = 1)
        System.out.println("TEST CASE 1: Cycle at Position 1");
        System.out.println("-".repeat(70));
        int[] arr1 = {3, 2, 0, -4};
        int pos1 = 1;
        ListNode head1 = createCycleList(arr1, pos1);
        printListInfo(head1, pos1);
        boolean result1 = solution.hasCycle(head1);
        System.out.println("Has Cycle: " + result1);
        System.out.println("Expected: true");
        
        ListNode cycleStart1 = solution.detectCycle(head1);
        System.out.println("Cycle starts at node with value: " + 
                          (cycleStart1 != null ? cycleStart1.val : "null"));
        System.out.println("Expected: 2");
        System.out.println();
        
        // Test Case 2: Cycle at head (pos = 0)
        System.out.println("TEST CASE 2: Cycle at Position 0 (head)");
        System.out.println("-".repeat(70));
        int[] arr2 = {1, 2};
        int pos2 = 0;
        ListNode head2 = createCycleList(arr2, pos2);
        printListInfo(head2, pos2);
        boolean result2 = solution.hasCycle(head2);
        System.out.println("Has Cycle: " + result2);
        System.out.println("Expected: true");
        
        ListNode cycleStart2 = solution.detectCycle(head2);
        System.out.println("Cycle starts at node with value: " + 
                          (cycleStart2 != null ? cycleStart2.val : "null"));
        System.out.println("Expected: 1");
        System.out.println();
        
        // Test Case 3: No cycle
        System.out.println("TEST CASE 3: No Cycle");
        System.out.println("-".repeat(70));
        int[] arr3 = {1, 2, 3, 4};
        int pos3 = -1;  // -1 means no cycle
        ListNode head3 = createCycleList(arr3, pos3);
        printListInfo(head3, pos3);
        boolean result3 = solution.hasCycle(head3);
        System.out.println("Has Cycle: " + result3);
        System.out.println("Expected: false");
        
        ListNode cycleStart3 = solution.detectCycle(head3);
        System.out.println("Cycle starts at: " + 
                          (cycleStart3 != null ? cycleStart3.val : "null"));
        System.out.println("Expected: null");
        System.out.println();
        
        // Test Case 4: Single node, no cycle
        System.out.println("TEST CASE 4: Single Node");
        System.out.println("-".repeat(70));
        int[] arr4 = {1};
        int pos4 = -1;
        ListNode head4 = createCycleList(arr4, pos4);
        printListInfo(head4, pos4);
        boolean result4 = solution.hasCycle(head4);
        System.out.println("Has Cycle: " + result4);
        System.out.println("Expected: false");
        System.out.println();
        
        // Test Case 5: HashSet approach
        System.out.println("TEST CASE 5: HashSet Approach (Same as Case 1)");
        System.out.println("-".repeat(70));
        ListNode head5 = createCycleList(new int[]{3, 2, 0, -4}, 1);
        boolean result5 = solution.hasCycleHashSet(head5);
        System.out.println("Has Cycle (HashSet): " + result5);
        System.out.println("Expected: true");
        System.out.println();
        
        // Complexity comparison
        System.out.println("=".repeat(70));
        System.out.println("APPROACH COMPARISON");
        System.out.println("=".repeat(70));
        System.out.println("Floyd's Algorithm (Two Pointers):");
        System.out.println("  Time:  O(n)");
        System.out.println("  Space: O(1) - Only two pointers");
        System.out.println("  ✓ OPTIMAL - Best for interviews!");
        System.out.println();
        System.out.println("HashSet Approach:");
        System.out.println("  Time:  O(n)");
        System.out.println("  Space: O(n) - Store all nodes");
        System.out.println("  Simple but not space-optimal");
        System.out.println("=".repeat(70));
    }
}


/*
 * ============================================================================
 *                    DETAILED WALKTHROUGH & INSIGHTS
 * ============================================================================
 * 
 * FLOYD'S ALGORITHM STEP-BY-STEP:
 * --------------------------------
 * 
 * Example: 3 → 2 → 0 → -4 → 2 (cycle)
 *              ↑__________|
 * 
 * Initial:
 * slow = 3
 * fast = 3
 * 
 * Iteration 1:
 * slow moves 1: 3 → 2
 * fast moves 2: 3 → 2 → 0
 * slow = 2, fast = 0
 * slow != fast, continue
 * 
 * Iteration 2:
 * slow moves 1: 2 → 0
 * fast moves 2: 0 → -4 → 2
 * slow = 0, fast = 2
 * slow != fast, continue
 * 
 * Iteration 3:
 * slow moves 1: 0 → -4
 * fast moves 2: 2 → 0 → -4
 * slow = -4, fast = -4
 * slow == fast, CYCLE DETECTED! ✓
 * 
 * Return true
 * 
 * 
 * FINDING CYCLE START (ADVANCED):
 * --------------------------------
 * 
 * After detecting cycle at meeting point:
 * 
 * PHASE 1 result: slow and fast met at node -4
 * 
 * PHASE 2: Find cycle start
 * 
 * Reset slow to head:
 * slow = 3
 * fast = -4 (stays at meeting point)
 * 
 * Move both at SAME SPEED (1 step each):
 * 
 * Step 1:
 * slow: 3 → 2
 * fast: -4 → 2
 * slow = 2, fast = 2
 * THEY MEET! ✓
 * 
 * Return 2 (this is cycle start!)
 * 
 * 
 * WHY DOES PHASE 2 WORK?
 * ----------------------
 * 
 * Mathematical proof:
 * 
 * Let:
 * - d = distance from head to cycle start
 * - c = cycle length
 * - k = distance from cycle start to meeting point
 * 
 * When they meet:
 * - slow traveled: d + k
 * - fast traveled: d + k + nc (n full cycles)
 * 
 * Since fast is 2x speed:
 * 2(d + k) = d + k + nc
 * d + k = nc
 * d = nc - k
 * 
 * This means:
 * Distance from head to cycle start (d) =
 * Distance from meeting point to cycle start (c - k)
 * 
 * So if we:
 * 1. Start one pointer at head
 * 2. Start other at meeting point
 * 3. Move both at same speed
 * They meet at cycle start! 🎯
 * 
 * 
 * EDGE CASES HANDLING:
 * --------------------
 * 
 * 1. Empty list (head = null):
 *    Return false immediately
 * 
 * 2. Single node (head.next = null):
 *    Can't have cycle, return false
 * 
 * 3. Two nodes with cycle:
 *    1 ⇄ 2
 *    Algorithm still works!
 * 
 * 4. Cycle at head:
 *    List: 1 → 2 → 1
 *    Works perfectly!
 * 
 * 
 * COMMON MISTAKES:
 * ----------------
 * 
 * 1. Wrong loop condition:
 *    BAD:  while (fast != null)
 *    GOOD: while (fast != null && fast.next != null)
 *    Why: Need to check fast.next before fast.next.next
 * 
 * 2. Checking equality before moving:
 *    BAD:  if (slow == fast) return true;
 *          slow = slow.next; fast = fast.next.next;
 *    GOOD: slow = slow.next; fast = fast.next.next;
 *          if (slow == fast) return true;
 *    Why: Start at same position, need to move first
 * 
 * 3. Infinite loop in printing:
 *    Always limit iterations when printing cyclic list!
 * 
 * 4. Not handling null in detectCycle:
 *    Must check if hasCycle is false before phase 2
 * 
 * 
 * INTERVIEW VARIATIONS:
 * ---------------------
 * 
 * Q1: "Find the length of the cycle?"
 * A: After meeting, keep one pointer still
 *    Move other until they meet again
 *    Count steps = cycle length
 * 
 * Q2: "Remove the cycle?"
 * A: Find cycle start using detectCycle
 *    Traverse to node before cycle start
 *    Set its next to null
 * 
 * Q3: "Find the node before cycle starts?"
 * A: Find cycle start, then find node pointing to it
 * 
 * Q4: "Cycle in array (not linked list)?"
 * A: Same algorithm! Treat array indices as "next"
 * 
 * 
 * WHEN TO USE FLOYD'S ALGORITHM:
 * ------------------------------
 * 
 * This pattern works for:
 * ✓ Cycle detection in linked list
 * ✓ Finding duplicate in array
 * ✓ Happy number problem
 * ✓ Any "linked" structure with potential cycle
 * 
 * Key idea: Fast pointer catches up to slow if cycle exists!
 * 
 * 
 * COMPLEXITY ANALYSIS:
 * --------------------
 * 
 * Time Complexity: O(n)
 * - If no cycle: fast reaches end in n/2 steps
 * - If cycle: slow and fast meet in at most n steps
 * - Worst case: O(n)
 * 
 * Space Complexity: O(1)
 * - Only two pointers (slow and fast)
 * - No extra data structures
 * 
 * HashSet approach: O(n) space
 * Floyd's is BETTER!
 * 
 * 
 * INTERVIEW TIPS:
 * ---------------
 * 
 * 1. Explain the analogy:
 *    "It's like runners on a track - fast will lap slow if circular"
 * 
 * 2. Draw it out:
 *    Show how pointers move and eventually meet
 * 
 * 3. Handle edge cases:
 *    "Let me check for empty list and single node"
 * 
 * 4. Mention space efficiency:
 *    "This is O(1) space, better than HashSet approach"
 * 
 * 5. Know the name:
 *    "This is Floyd's Cycle Detection Algorithm"
 * 
 * 6. Bonus points:
 *    Explain how to find cycle start (phase 2)
 * 
 * 
 * REMEMBER:
 * ---------
 * ✓ Fast moves 2x speed of slow
 * ✓ If cycle exists, they WILL meet
 * ✓ Check fast != null AND fast.next != null
 * ✓ Move pointers BEFORE checking equality
 * ✓ O(n) time, O(1) space - OPTIMAL!
 * 
 * This is a CLASSIC algorithm - know it well! 🏆
 * Named after Robert Floyd (1960s)
 * Used in many real-world applications!
 */
