package day_6_dsa_linked_list;

/*
 * ============================================================================
 *         PROBLEM 2: MIDDLE OF LINKED LIST (MEDIUM - Two Pointer!)
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * Given the head of a singly linked list, return the MIDDLE node.
 * If there are two middle nodes, return the SECOND middle node.
 * 
 * Example 1:
 * Input: head = [1,2,3,4,5]
 * Output: 3
 * Explanation: The middle node is 3
 * 
 * Example 2:
 * Input: head = [1,2,3,4,5,6]
 * Output: 4
 * Explanation: Two middle nodes (3 and 4), return second one (4)
 * 
 * 
 * CONCEPT EXPLANATION:
 * --------------------
 * 
 * WHAT IS "MIDDLE"?
 * For ODD length: Exact middle node
 * For EVEN length: Second of two middle nodes
 * 
 * Example (ODD):
 * 1 → 2 → 3 → 4 → 5
 *         ↑
 *      Middle (position 3)
 * 
 * Example (EVEN):
 * 1 → 2 → 3 → 4 → 5 → 6
 *             ↑
 *      Second middle (position 4)
 * 
 * 
 * KEY TECHNIQUE: FAST & SLOW POINTERS (Tortoise & Hare)
 * ------------------------------------------------------
 * 
 * Think of two runners on a track:
 * - SLOW runner: moves 1 step at a time
 * - FAST runner: moves 2 steps at a time
 * 
 * When FAST reaches the end:
 * SLOW will be at the MIDDLE!
 * 
 * Why? Fast moves twice as fast as slow.
 * When fast covers entire distance (n),
 * slow covers half distance (n/2) = middle!
 * 
 * 
 * VISUALIZATION:
 * --------------
 * 
 * List: 1 → 2 → 3 → 4 → 5
 * 
 * Step 0:
 * slow → 1
 * fast → 1
 * 
 * Step 1:
 * slow → 2 (moved 1 step)
 * fast → 3 (moved 2 steps)
 * 
 * Step 2:
 * slow → 3 (moved 1 step)
 * fast → 5 (moved 2 steps)
 * 
 * Step 3:
 * fast.next = null (end reached!)
 * slow is at 3 = MIDDLE! ✓
 */

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class MiddleOfLinkedListMedium {
    
    // ================================================================
    //        APPROACH 1: TWO POINTER (Fast & Slow) - OPTIMAL!
    // ================================================================
    // Time: O(n) - One pass through list
    // Space: O(1) - Only two pointers
    
    public ListNode middleNode(ListNode head) {
        // Edge case: empty or single node
        if (head == null || head.next == null) {
            return head;
        }
        
        // Initialize both pointers at head
        ListNode slow = head;
        ListNode fast = head;
        
        // Move fast 2 steps, slow 1 step
        // When fast reaches end, slow is at middle
        while (fast != null && fast.next != null) {
            slow = slow.next;           // Move 1 step
            fast = fast.next.next;      // Move 2 steps
        }
        
        return slow;  // slow is now at middle
    }
    
    
    // ================================================================
    //        APPROACH 2: COUNT & Traverse (Naive but educational)
    // ================================================================
    // Time: O(n) - Two passes
    // Space: O(1) - Only counter
    
    public ListNode middleNodeNaive(ListNode head) {
        if (head == null) return null;
        
        // PASS 1: Count nodes
        int count = 0;
        ListNode temp = head;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        
        // PASS 2: Go to middle (count/2)
        int middle = count / 2;
        temp = head;
        for (int i = 0; i < middle; i++) {
            temp = temp.next;
        }
        
        return temp;
    }
    
    
    // ================================================================
    //        HELPER METHODS
    // ================================================================
    
    public static ListNode createList(int[] arr) {
        if (arr == null || arr.length == 0) return null;
        ListNode head = new ListNode(arr[0]);
        ListNode current = head;
        for (int i = 1; i < arr.length; i++) {
            current.next = new ListNode(arr[i]);
            current = current.next;
        }
        return head;
    }
    
    public static void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) System.out.print(" → ");
            current = current.next;
        }
        System.out.println(" → NULL");
    }
    
    public static void printFromNode(ListNode node, String label) {
        System.out.print(label + ": ");
        if (node == null) {
            System.out.println("NULL");
            return;
        }
        ListNode current = node;
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) System.out.print(" → ");
            current = current.next;
        }
        System.out.println();
    }
    
    
    // ================================================================
    //                         MAIN METHOD
    // ================================================================
    
    public static void main(String[] args) {
        MiddleOfLinkedListMedium solution = new MiddleOfLinkedListMedium();
        
        System.out.println("=".repeat(70));
        System.out.println("MIDDLE OF LINKED LIST - FAST & SLOW POINTER TECHNIQUE");
        System.out.println("=".repeat(70));
        System.out.println();
        
        // Test Case 1: Odd length
        System.out.println("TEST CASE 1: Odd Length List (1,2,3,4,5)");
        System.out.println("-".repeat(70));
        int[] arr1 = {1, 2, 3, 4, 5};
        ListNode head1 = createList(arr1);
        System.out.print("Original list: ");
        printList(head1);
        
        ListNode middle1 = solution.middleNode(head1);
        System.out.println("Middle node: " + middle1.val);
        printFromNode(middle1, "From middle");
        System.out.println();
        
        // Test Case 2: Even length
        System.out.println("TEST CASE 2: Even Length List (1,2,3,4,5,6)");
        System.out.println("-".repeat(70));
        int[] arr2 = {1, 2, 3, 4, 5, 6};
        ListNode head2 = createList(arr2);
        System.out.print("Original list: ");
        printList(head2);
        
        ListNode middle2 = solution.middleNode(head2);
        System.out.println("Middle node: " + middle2.val + " (second middle)");
        printFromNode(middle2, "From middle");
        System.out.println();
        
        // Test Case 3: Two nodes
        System.out.println("TEST CASE 3: Two Nodes (1,2)");
        System.out.println("-".repeat(70));
        int[] arr3 = {1, 2};
        ListNode head3 = createList(arr3);
        System.out.print("Original list: ");
        printList(head3);
        
        ListNode middle3 = solution.middleNode(head3);
        System.out.println("Middle node: " + middle3.val);
        System.out.println();
        
        // Test Case 4: Single node
        System.out.println("TEST CASE 4: Single Node (1)");
        System.out.println("-".repeat(70));
        int[] arr4 = {1};
        ListNode head4 = createList(arr4);
        System.out.print("Original list: ");
        printList(head4);
        
        ListNode middle4 = solution.middleNode(head4);
        System.out.println("Middle node: " + middle4.val);
        System.out.println();
        
        // Complexity comparison
        System.out.println("=".repeat(70));
        System.out.println("APPROACH COMPARISON");
        System.out.println("=".repeat(70));
        System.out.println("Two Pointer (Fast & Slow):");
        System.out.println("  Time:  O(n) - Single pass");
        System.out.println("  Space: O(1) - Two pointers");
        System.out.println("  ✓ OPTIMAL - Recommended approach!");
        System.out.println();
        System.out.println("Count & Traverse:");
        System.out.println("  Time:  O(n) - Two passes");
        System.out.println("  Space: O(1) - Counter variable");
        System.out.println("  Less efficient (two passes)");
        System.out.println("=".repeat(70));
    }
}


/*
 * ============================================================================
 *                    DETAILED WALKTHROUGH & INSIGHTS
 * ============================================================================
 * 
 * FAST & SLOW POINTER TECHNIQUE EXPLAINED:
 * -----------------------------------------
 * 
 * Example: Find middle of [1,2,3,4,5]
 * 
 * Initial:
 * slow = 1, fast = 1
 * 
 * Iteration 1:
 * slow moves 1 step → slow = 2
 * fast moves 2 steps → fast = 3
 * List: 1 → [2] → 3 → 4 → 5
 *            ↑slow      ↑fast
 * 
 * Iteration 2:
 * slow moves 1 step → slow = 3
 * fast moves 2 steps → fast = 5
 * List: 1 → 2 → [3] → 4 → 5
 *                ↑slow      ↑fast
 * 
 * Check loop condition:
 * fast = 5, fast.next = null
 * Loop exits!
 * 
 * Return slow = 3 ✓
 * 
 * 
 * WHY DOES THIS WORK?
 * -------------------
 * 
 * Mathematical proof:
 * - Let n = length of list
 * - Fast pointer moves at 2x speed
 * - When fast covers n distance, slow covers n/2
 * - n/2 = middle position!
 * 
 * For ODD length (n=5):
 * - Fast reaches end after 2.5 iterations
 * - Slow at position 2.5 → position 3 (middle!)
 * 
 * For EVEN length (n=6):
 * - Fast reaches end after 3 iterations
 * - Slow at position 3 → position 4 (second middle!)
 * 
 * 
 * EDGE CASES HANDLING:
 * --------------------
 * 
 * 1. Empty list (head = null):
 *    - Return null immediately
 * 
 * 2. Single node (head.next = null):
 *    - Return head (only node is middle)
 * 
 * 3. Two nodes:
 *    - fast starts at node 1
 *    - fast.next = node 2, fast.next.next = null
 *    - Loop executes once
 *    - slow = node 2 (second middle) ✓
 * 
 * 
 * INTERVIEW VARIATIONS:
 * ---------------------
 * 
 * Q1: "Return FIRST middle for even length?"
 * A: Move fast.next.next instead of fast.next in condition
 *    while (fast.next != null && fast.next.next != null)
 * 
 * Q2: "Delete the middle node?"
 * A: Keep prev pointer, use slow.next to skip middle
 *    prev.next = slow.next
 * 
 * Q3: "Find middle of second half?"
 * A: Use fast & slow to find middle, then find middle of slow→end
 * 
 * Q4: "Check if list is palindrome?"
 * A: Find middle, reverse second half, compare both halves
 * 
 * 
 * COMMON MISTAKES:
 * ----------------
 * 
 * 1. Wrong loop condition:
 *    BAD:  while (fast.next != null)
 *    GOOD: while (fast != null && fast.next != null)
 *    Why: fast might be null, causing NullPointerException
 * 
 * 2. Moving pointers wrong way:
 *    BAD:  slow = slow.next.next (too fast!)
 *    GOOD: slow = slow.next
 * 
 * 3. Not checking null before fast.next.next:
 *    BAD:  fast = fast.next.next (might crash!)
 *    GOOD: Check fast != null first
 * 
 * 
 * WHEN TO USE THIS TECHNIQUE:
 * ---------------------------
 * 
 * Fast & Slow pointers are useful for:
 * ✓ Finding middle of list
 * ✓ Detecting cycles (we'll see in next problem!)
 * ✓ Finding nth node from end
 * ✓ Checking palindrome
 * ✓ Splitting list into halves
 * 
 * 
 * COMPLEXITY ANALYSIS:
 * --------------------
 * 
 * Time Complexity: O(n)
 * - Visit each node at most once
 * - Even though fast moves 2x, still O(n)
 * 
 * Space Complexity: O(1)
 * - Only two pointers (slow and fast)
 * - No extra data structures
 * 
 * Better than naive O(2n) approach!
 * 
 * 
 * INTERVIEW TIPS:
 * ---------------
 * 
 * 1. Explain the technique:
 *    "I'll use fast and slow pointers - it's a common pattern"
 * 
 * 2. Draw it out:
 *    Show how pointers move step by step
 * 
 * 3. Handle edge cases first:
 *    "Let me check if list is empty or has one node"
 * 
 * 4. Mention variations:
 *    "This technique also works for cycle detection"
 * 
 * 5. State complexity:
 *    "This is O(n) time and O(1) space - optimal!"
 * 
 * 
 * REMEMBER:
 * ---------
 * ✓ Fast moves 2x speed of slow
 * ✓ When fast reaches end, slow is at middle
 * ✓ Always check fast != null AND fast.next != null
 * ✓ This technique is reusable (cycles, nth from end, etc.)
 * ✓ O(n) time, O(1) space - OPTIMAL!
 * 
 * MASTER THIS PATTERN - It appears EVERYWHERE! 🚀
 */
