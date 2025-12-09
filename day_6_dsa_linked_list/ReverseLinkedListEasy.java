package day_6_dsa_linked_list;

/*
 * ============================================================================
 *              PROBLEM 1: REVERSE LINKED LIST (EASY - FOUNDATION!)
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * Given the head of a singly linked list, reverse the list and return the reversed list.
 * 
 * Example 1:
 * Input: head = [1,2,3,4,5]
 * Output: [5,4,3,2,1]
 * 
 * Example 2:
 * Input: head = [1,2]
 * Output: [2,1]
 * 
 * Example 3:
 * Input: head = []
 * Output: []
 * 
 * 
 * CONCEPT EXPLANATION:
 * --------------------
 * 
 * WHAT DOES "REVERSE" MEAN?
 * Original: 1 → 2 → 3 → 4 → NULL
 * Reversed: NULL ← 1 ← 2 ← 3 ← 4
 * 
 * We need to FLIP all the arrows (pointers)!
 * 
 * 
 * KEY INSIGHT:
 * ------------
 * Think of it like reversing a train:
 * - Original: Engine → Coach1 → Coach2 → Coach3
 * - Reversed: Coach3 → Coach2 → Coach1 → Engine
 * 
 * To reverse, we need to:
 * 1. Break each connection
 * 2. Point it backwards
 * 3. Move forward to next node
 * 
 * 
 * VISUALIZATION (Step by Step):
 * ------------------------------
 * 
 * Initial: 1 → 2 → 3 → NULL
 * 
 * Step 0:
 * prev = NULL
 * curr = 1
 * next = 2
 * 
 * Step 1: Reverse pointer of 1
 * NULL ← 1    2 → 3 → NULL
 *       prev curr
 * 
 * Step 2: Move pointers
 * NULL ← 1    2 → 3 → NULL
 *            prev curr
 * 
 * Step 3: Reverse pointer of 2
 * NULL ← 1 ← 2    3 → NULL
 *               prev curr
 * 
 * Step 4: Reverse pointer of 3
 * NULL ← 1 ← 2 ← 3
 *                  prev (new head!)
 * 
 * 
 * TWO APPROACHES:
 * ---------------
 * 1. ITERATIVE (3 pointers: prev, curr, next)
 * 2. RECURSIVE (use call stack)
 * 
 * 
 * WHY THIS PROBLEM IS IMPORTANT:
 * ------------------------------
 * - Tests basic pointer manipulation
 * - Foundation for many linked list problems
 * - Asked in 80% of interviews
 * - Appears in: Palindrome check, Reverse in groups, etc.
 */

/**
 * Definition for singly-linked list node
 */
class ListNode {
    int val;
    ListNode next;
    
    ListNode() {}
    
    ListNode(int val) {
        this.val = val;
    }
    
    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

public class ReverseLinkedListEasy {
    
    // ================================================================
    //        APPROACH 1: ITERATIVE (3 Pointers) - RECOMMENDED!
    // ================================================================
    // Time: O(n) - Visit each node once
    // Space: O(1) - Only 3 pointers
    
    /**
     * ALGORITHM:
     * ----------
     * 1. Use 3 pointers: prev, curr, next
     * 2. Initialize: prev = null, curr = head
     * 3. For each node:
     *    - Save next node (before we lose reference)
     *    - Reverse current's pointer to prev
     *    - Move prev to current
     *    - Move current to next
     * 4. Return prev (new head)
     */
    public ListNode reverseListIterative(ListNode head) {
        // Edge case: empty or single node
        if (head == null || head.next == null) {
            return head;
        }
        
        ListNode prev = null;       // Previous node (starts as null)
        ListNode curr = head;       // Current node
        ListNode next = null;       // Next node (temporary storage)
        
        // Traverse the list
        while (curr != null) {
            // STEP 1: Save next node (before we break link)
            next = curr.next;
            
            // STEP 2: Reverse the pointer
            // Make current point backwards to prev
            curr.next = prev;
            
            // STEP 3: Move pointers forward
            prev = curr;      // prev moves to current
            curr = next;      // current moves to next
            
            // Visual of each iteration:
            // NULL ← 1    2 → 3 → NULL
            //       prev curr next
        }
        
        // When loop ends, curr is null, prev is new head
        return prev;
    }
    
    
    // ================================================================
    //        APPROACH 2: RECURSIVE (Elegant but tricky)
    // ================================================================
    // Time: O(n) - Visit each node once
    // Space: O(n) - Recursion call stack
    
    /**
     * RECURSIVE THINKING:
     * -------------------
     * Base case: If node is null or last node, return it
     * Recursive case:
     *   1. Reverse rest of the list
     *   2. Make next node point back to current
     *   3. Break current's forward pointer
     * 
     * Example: 1 → 2 → 3 → NULL
     * 
     * Call stack:
     * reverse(1)
     *   reverse(2)
     *     reverse(3) → returns 3 (base case)
     *     Make 3.next = 2, 2.next = null
     *     returns 3
     *   Make 2.next = 1, 1.next = null
     *   returns 3
     * 
     * Result: 3 → 2 → 1 → NULL
     */
    public ListNode reverseListRecursive(ListNode head) {
        // Base case: empty list or single node
        if (head == null || head.next == null) {
            return head;
        }
        
        // Recursively reverse the rest of the list
        // newHead will be the last node (new head after reversal)
        ListNode newHead = reverseListRecursive(head.next);
        
        // Make next node point back to current node
        // If current is 1 and next is 2:
        // head.next.next = head means 2.next = 1
        head.next.next = head;
        
        // Break the forward pointer
        // Prevents cycle
        head.next = null;
        
        // Return the new head (last node of original list)
        return newHead;
    }
    
    
    // ================================================================
    //        HELPER METHODS (For Testing & Visualization)
    // ================================================================
    
    /**
     * Create a linked list from array
     */
    public static ListNode createList(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        
        ListNode head = new ListNode(arr[0]);
        ListNode current = head;
        
        for (int i = 1; i < arr.length; i++) {
            current.next = new ListNode(arr[i]);
            current = current.next;
        }
        
        return head;
    }
    
    /**
     * Print linked list
     */
    public static void printList(ListNode head) {
        if (head == null) {
            System.out.println("Empty list");
            return;
        }
        
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) {
                System.out.print(" → ");
            }
            current = current.next;
        }
        System.out.println(" → NULL");
    }
    
    /**
     * Compare two lists
     */
    public static boolean areListsEqual(ListNode l1, ListNode l2) {
        while (l1 != null && l2 != null) {
            if (l1.val != l2.val) {
                return false;
            }
            l1 = l1.next;
            l2 = l2.next;
        }
        return l1 == null && l2 == null;
    }
    
    
    // ================================================================
    //                         MAIN METHOD
    // ================================================================
    
    public static void main(String[] args) {
        ReverseLinkedListEasy solution = new ReverseLinkedListEasy();
        
        System.out.println("=".repeat(70));
        System.out.println("REVERSE LINKED LIST - COMPREHENSIVE EXAMPLES");
        System.out.println("=".repeat(70));
        System.out.println();
        
        // Test Case 1: Normal list
        System.out.println("TEST CASE 1: Normal List");
        System.out.println("-".repeat(70));
        int[] arr1 = {1, 2, 3, 4, 5};
        ListNode head1 = createList(arr1);
        System.out.print("Original:  ");
        printList(head1);
        
        ListNode reversed1 = solution.reverseListIterative(head1);
        System.out.print("Reversed:  ");
        printList(reversed1);
        System.out.println();
        
        // Test Case 2: Two nodes
        System.out.println("TEST CASE 2: Two Nodes");
        System.out.println("-".repeat(70));
        int[] arr2 = {1, 2};
        ListNode head2 = createList(arr2);
        System.out.print("Original:  ");
        printList(head2);
        
        ListNode reversed2 = solution.reverseListRecursive(head2);
        System.out.print("Reversed:  ");
        printList(reversed2);
        System.out.println();
        
        // Test Case 3: Single node
        System.out.println("TEST CASE 3: Single Node");
        System.out.println("-".repeat(70));
        int[] arr3 = {1};
        ListNode head3 = createList(arr3);
        System.out.print("Original:  ");
        printList(head3);
        
        ListNode reversed3 = solution.reverseListIterative(head3);
        System.out.print("Reversed:  ");
        printList(reversed3);
        System.out.println();
        
        // Test Case 4: Empty list
        System.out.println("TEST CASE 4: Empty List");
        System.out.println("-".repeat(70));
        ListNode head4 = null;
        System.out.print("Original:  ");
        printList(head4);
        
        ListNode reversed4 = solution.reverseListIterative(head4);
        System.out.print("Reversed:  ");
        printList(reversed4);
        System.out.println();
        
        // Complexity Analysis
        System.out.println("=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS");
        System.out.println("=".repeat(70));
        System.out.println("Iterative Approach:");
        System.out.println("  Time Complexity:  O(n) - visit each node once");
        System.out.println("  Space Complexity: O(1) - only 3 pointers");
        System.out.println();
        System.out.println("Recursive Approach:");
        System.out.println("  Time Complexity:  O(n) - visit each node once");
        System.out.println("  Space Complexity: O(n) - recursion call stack");
        System.out.println("=".repeat(70));
    }
}


/*
 * ============================================================================
 *                    DETAILED WALKTHROUGH & INSIGHTS
 * ============================================================================
 * 
 * ITERATIVE APPROACH STEP-BY-STEP:
 * ---------------------------------
 * 
 * Example: Reverse 1 → 2 → 3 → NULL
 * 
 * Initial state:
 * prev = null
 * curr = 1
 * next = null
 * 
 * Iteration 1:
 * -----------
 * next = curr.next        → next = 2
 * curr.next = prev        → 1.next = null  (reverse!)
 * prev = curr             → prev = 1
 * curr = next             → curr = 2
 * 
 * State: null ← 1    2 → 3 → null
 *              prev curr
 * 
 * Iteration 2:
 * -----------
 * next = curr.next        → next = 3
 * curr.next = prev        → 2.next = 1  (reverse!)
 * prev = curr             → prev = 2
 * curr = next             → curr = 3
 * 
 * State: null ← 1 ← 2    3 → null
 *                   prev curr
 * 
 * Iteration 3:
 * -----------
 * next = curr.next        → next = null
 * curr.next = prev        → 3.next = 2  (reverse!)
 * prev = curr             → prev = 3
 * curr = next             → curr = null (EXIT LOOP)
 * 
 * State: null ← 1 ← 2 ← 3
 *                       prev (NEW HEAD!)
 * 
 * Return prev (which is 3)
 * 
 * 
 * RECURSIVE APPROACH DETAILED:
 * ----------------------------
 * 
 * Call stack for 1 → 2 → 3 → null:
 * 
 * reverse(1):
 *   head = 1, head.next = 2
 *   Call reverse(2)
 *   
 *   reverse(2):
 *     head = 2, head.next = 3
 *     Call reverse(3)
 *     
 *     reverse(3):
 *       head = 3, head.next = null
 *       BASE CASE: return 3
 *     
 *     Back in reverse(2):
 *     newHead = 3
 *     head.next.next = head  → 3.next = 2
 *     head.next = null       → 2.next = null
 *     return 3
 *   
 *   Back in reverse(1):
 *   newHead = 3
 *   head.next.next = head  → 2.next = 1
 *   head.next = null       → 1.next = null
 *   return 3
 * 
 * Final: 3 → 2 → 1 → null
 * 
 * 
 * COMMON MISTAKES & HOW TO AVOID:
 * --------------------------------
 * 
 * 1. MISTAKE: Not saving next before reversing
 *    BAD:  curr.next = prev;  // Lost reference to next!
 *    GOOD: next = curr.next; curr.next = prev;
 * 
 * 2. MISTAKE: Returning head instead of prev
 *    BAD:  return head;  // head is now the last node!
 *    GOOD: return prev;  // prev is the new head
 * 
 * 3. MISTAKE: Forgetting to break forward link in recursion
 *    BAD:  head.next.next = head;  // Creates cycle!
 *    GOOD: head.next.next = head; head.next = null;
 * 
 * 4. MISTAKE: Not handling edge cases
 *    - Empty list (head == null)
 *    - Single node (head.next == null)
 * 
 * 
 * INTERVIEW TIPS:
 * ---------------
 * 
 * 1. Draw it out!
 *    - Interviewers LOVE when you draw
 *    - Shows clear thinking
 *    - Helps catch bugs
 * 
 * 2. Start with iterative
 *    - Easier to explain
 *    - Better space complexity
 *    - Then mention recursive if asked
 * 
 * 3. Walk through example
 *    - Use small example (3-4 nodes)
 *    - Show each step
 *    - Prove correctness
 * 
 * 4. Mention variations:
 *    - "I can also do this recursively"
 *    - "For doubly linked list, we'd also reverse prev pointers"
 *    - "This is O(n) time, O(1) space - optimal!"
 * 
 * 
 * FOLLOW-UP QUESTIONS YOU MIGHT GET:
 * -----------------------------------
 * 
 * Q1: "Can you reverse only part of the list?"
 * A: Yes! Keep track of start/end, reverse middle section
 * 
 * Q2: "What if it's a doubly linked list?"
 * A: Also need to swap prev pointers, otherwise same logic
 * 
 * Q3: "Can you do it in-place?"
 * A: Yes, both solutions are in-place (only change pointers)
 * 
 * Q4: "What if list has a cycle?"
 * A: First detect cycle, then decide how to handle
 * 
 * 
 * RELATED PROBLEMS:
 * -----------------
 * - Palindrome Linked List (reverse half, compare)
 * - Reverse Nodes in k-Group (reverse in chunks)
 * - Swap Nodes in Pairs (reverse pairs)
 * - Reorder List (reverse second half, merge)
 * 
 * 
 * REMEMBER:
 * ---------
 * ✓ Reverse = flip all pointers
 * ✓ Need 3 pointers for iterative
 * ✓ Always save next before reversing
 * ✓ Return prev (new head), not old head
 * ✓ Handle edge cases (null, single node)
 * 
 * THIS IS THE FOUNDATION - MASTER IT! 
 */
