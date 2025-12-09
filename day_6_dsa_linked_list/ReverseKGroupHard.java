package day_6_dsa_linked_list;

/*
 * ============================================================================
 *     PROBLEM 5: REVERSE NODES IN K-GROUP (HARD - Advanced Pointers!)
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * Given the head of a linked list, reverse the nodes of the list k at a time,
 * and return the modified list.
 * 
 * k is a positive integer and is less than or equal to the length of the
 * linked list. If the number of nodes is not a multiple of k then left-out
 * nodes, in the end, should remain as it is.
 * 
 * You may not alter the values in the list's nodes, only nodes themselves
 * may be changed.
 * 
 * Example 1:
 * Input: head = [1,2,3,4,5], k = 2
 * Output: [2,1,4,3,5]
 * Explanation: 
 * - Group 1: [1,2] reversed to [2,1]
 * - Group 2: [3,4] reversed to [4,3]
 * - Remaining: [5] stays as is (less than k)
 * 
 * Example 2:
 * Input: head = [1,2,3,4,5], k = 3
 * Output: [3,2,1,4,5]
 * Explanation:
 * - Group 1: [1,2,3] reversed to [3,2,1]
 * - Remaining: [4,5] stays as is (less than k)
 * 
 * 
 * CONCEPT EXPLANATION:
 * --------------------
 * 
 * WHAT IS "K-GROUP REVERSAL"?
 * ----------------------------
 * Think of it like flipping cards in groups:
 * 
 * Original: [1, 2, 3, 4, 5, 6, 7] with k=3
 * 
 * Step 1: Take first 3 cards → [1, 2, 3]
 *         Flip them → [3, 2, 1]
 * 
 * Step 2: Take next 3 cards → [4, 5, 6]
 *         Flip them → [6, 5, 4]
 * 
 * Step 3: Only 1 card left [7]
 *         Less than k, leave as is
 * 
 * Result: [3, 2, 1, 6, 5, 4, 7]
 * 
 * 
 * VISUALIZATION:
 * --------------
 * 
 * Example: head = [1,2,3,4,5], k = 2
 * 
 * Original:
 * 1 → 2 → 3 → 4 → 5 → NULL
 * 
 * Step 1: Reverse first k=2 nodes [1,2]
 * Before: 1 → 2 → (rest)
 * After:  2 → 1 → (rest)
 * 
 * Step 2: Reverse next k=2 nodes [3,4]
 * Before: 2 → 1 → 3 → 4 → (rest)
 * After:  2 → 1 → 4 → 3 → (rest)
 * 
 * Step 3: Only 1 node left [5]
 * Less than k=2, leave as is
 * 
 * Final: 2 → 1 → 4 → 3 → 5 → NULL ✓
 * 
 * 
 * KEY TECHNIQUE: GROUP-WISE REVERSAL
 * -----------------------------------
 * 
 * Three main challenges:
 * 1. Count k nodes (if less than k, don't reverse)
 * 2. Reverse k nodes
 * 3. Connect reversed group to rest of list
 * 
 * We need to track:
 * - prevGroupEnd: Last node of previous group
 * - groupStart: First node of current group
 * - groupEnd: Last node of current group
 * - nextGroupStart: First node of next group
 * 
 * Like managing train cars:
 * PrevCar ← [Car1 Car2 Car3] → NextCar
 *           Reverse this group!
 */

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class ReverseKGroupHard {
    
    // ================================================================
    //        APPROACH 1: ITERATIVE GROUP REVERSAL - OPTIMAL!
    // ================================================================
    // Time: O(n) - Visit each node twice (count + reverse)
    // Space: O(1) - Only pointers
    
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || k == 1) return head;
        
        // Dummy node to handle head changes
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        ListNode prevGroupEnd = dummy;
        
        while (true) {
            // Check if we have k nodes remaining
            ListNode kthNode = getKthNode(prevGroupEnd, k);
            if (kthNode == null) {
                break;  // Less than k nodes left, done!
            }
            
            // Save next group start
            ListNode nextGroupStart = kthNode.next;
            
            // Reverse current group
            ListNode groupStart = prevGroupEnd.next;
            ListNode prev = nextGroupStart;  // Connect to next group
            ListNode curr = groupStart;
            
            // Reverse k nodes
            while (curr != nextGroupStart) {
                ListNode temp = curr.next;
                curr.next = prev;
                prev = curr;
                curr = temp;
            }
            
            // Connect reversed group to previous part
            prevGroupEnd.next = kthNode;  // kthNode is now first
            prevGroupEnd = groupStart;    // groupStart is now last
        }
        
        return dummy.next;
    }
    
    // Helper: Get the kth node after start
    private ListNode getKthNode(ListNode start, int k) {
        ListNode curr = start;
        while (curr != null && k > 0) {
            curr = curr.next;
            k--;
        }
        return curr;  // null if less than k nodes
    }
    
    
    // ================================================================
    //        APPROACH 2: RECURSIVE (Elegant but uses stack)
    // ================================================================
    // Time: O(n)
    // Space: O(n/k) - Recursion depth
    
    public ListNode reverseKGroupRecursive(ListNode head, int k) {
        // Count if we have k nodes
        ListNode curr = head;
        int count = 0;
        while (curr != null && count < k) {
            curr = curr.next;
            count++;
        }
        
        // If less than k nodes, return as is
        if (count < k) return head;
        
        // Reverse first k nodes
        ListNode prev = null;
        curr = head;
        for (int i = 0; i < k; i++) {
            ListNode temp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = temp;
        }
        
        // Recursively reverse rest and connect
        head.next = reverseKGroupRecursive(curr, k);
        
        return prev;  // New head after reversing
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
        if (head == null) {
            System.out.println("NULL (empty list)");
            return;
        }
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) System.out.print(" → ");
            current = current.next;
        }
        System.out.println();
    }
    
    public static int[] listToArray(ListNode head) {
        if (head == null) return new int[0];
        
        int count = 0;
        ListNode temp = head;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        
        int[] arr = new int[count];
        temp = head;
        for (int i = 0; i < count; i++) {
            arr[i] = temp.val;
            temp = temp.next;
        }
        return arr;
    }
    
    public static String arrayToString(int[] arr) {
        if (arr.length == 0) return "[]";
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
    
    
    // ================================================================
    //                         MAIN METHOD
    // ================================================================
    
    public static void main(String[] args) {
        ReverseKGroupHard solution = new ReverseKGroupHard();
        
        System.out.println("=".repeat(70));
        System.out.println("REVERSE NODES IN K-GROUP - ADVANCED POINTER MANIPULATION");
        System.out.println("=".repeat(70));
        System.out.println();
        
        // Test Case 1: k=2
        System.out.println("TEST CASE 1: Reverse in groups of 2");
        System.out.println("-".repeat(70));
        int[] arr1 = {1, 2, 3, 4, 5};
        ListNode head1 = createList(arr1);
        System.out.print("Original: ");
        printList(head1);
        System.out.println("k = 2");
        
        ListNode result1 = solution.reverseKGroup(head1, 2);
        System.out.print("Result:   ");
        printList(result1);
        System.out.println("Expected: [2,1,4,3,5]");
        System.out.println();
        
        // Test Case 2: k=3
        System.out.println("TEST CASE 2: Reverse in groups of 3");
        System.out.println("-".repeat(70));
        int[] arr2 = {1, 2, 3, 4, 5};
        ListNode head2 = createList(arr2);
        System.out.print("Original: ");
        printList(head2);
        System.out.println("k = 3");
        
        ListNode result2 = solution.reverseKGroup(head2, 3);
        System.out.print("Result:   ");
        printList(result2);
        System.out.println("Expected: [3,2,1,4,5]");
        System.out.println();
        
        // Test Case 3: k=1 (no change)
        System.out.println("TEST CASE 3: k=1 (no reversal needed)");
        System.out.println("-".repeat(70));
        int[] arr3 = {1, 2, 3};
        ListNode head3 = createList(arr3);
        System.out.print("Original: ");
        printList(head3);
        System.out.println("k = 1");
        
        ListNode result3 = solution.reverseKGroup(head3, 1);
        System.out.print("Result:   ");
        printList(result3);
        System.out.println("Expected: [1,2,3] (no change)");
        System.out.println();
        
        // Test Case 4: k equals list length
        System.out.println("TEST CASE 4: k equals list length");
        System.out.println("-".repeat(70));
        int[] arr4 = {1, 2, 3, 4};
        ListNode head4 = createList(arr4);
        System.out.print("Original: ");
        printList(head4);
        System.out.println("k = 4");
        
        ListNode result4 = solution.reverseKGroup(head4, 4);
        System.out.print("Result:   ");
        printList(result4);
        System.out.println("Expected: [4,3,2,1] (entire list reversed)");
        System.out.println();
        
        // Test Case 5: Recursive approach
        System.out.println("TEST CASE 5: Recursive Approach (k=2)");
        System.out.println("-".repeat(70));
        int[] arr5 = {1, 2, 3, 4, 5, 6};
        ListNode head5 = createList(arr5);
        System.out.print("Original: ");
        printList(head5);
        System.out.println("k = 2");
        
        ListNode result5 = solution.reverseKGroupRecursive(head5, 2);
        System.out.print("Result:   ");
        printList(result5);
        System.out.println("Expected: [2,1,4,3,6,5]");
        System.out.println();
        
        // Complexity comparison
        System.out.println("=".repeat(70));
        System.out.println("APPROACH COMPARISON");
        System.out.println("=".repeat(70));
        System.out.println("Iterative:");
        System.out.println("  Time:  O(n) - Two passes per group");
        System.out.println("  Space: O(1) - Only pointers");
        System.out.println("  ✓ OPTIMAL - Best for production code!");
        System.out.println();
        System.out.println("Recursive:");
        System.out.println("  Time:  O(n)");
        System.out.println("  Space: O(n/k) - Recursion stack");
        System.out.println("  Elegant but uses stack space");
        System.out.println("=".repeat(70));
    }
}


/*
 * ============================================================================
 *                    DETAILED WALKTHROUGH & INSIGHTS
 * ============================================================================
 * 
 * STEP-BY-STEP EXECUTION:
 * -----------------------
 * 
 * Example: head = [1,2,3,4,5], k = 2
 * 
 * Initial state:
 * dummy → 1 → 2 → 3 → 4 → 5 → NULL
 * prevGroupEnd = dummy
 * 
 * === ITERATION 1: Reverse first k=2 nodes ===
 * 
 * Step 1: Find kth node
 * getKthNode(dummy, 2) → node 2
 * kthNode = 2
 * nextGroupStart = 3
 * 
 * Step 2: Reverse nodes [1,2]
 * Before: dummy → 1 → 2 → 3 → 4 → 5
 * 
 * Reversal process:
 * curr = 1, prev = 3 (nextGroupStart)
 * - 1.next = 3 (temp=2)
 * - curr = 2, prev = 1
 * - 2.next = 1 (temp=3)
 * - curr = 3 (exit loop)
 * 
 * After reversal: 2 → 1 → 3 → 4 → 5
 * 
 * Step 3: Connect to previous part
 * dummy.next = 2 (kthNode, new first)
 * prevGroupEnd = 1 (groupStart, new last)
 * 
 * Current state: dummy → 2 → 1 → 3 → 4 → 5
 * 
 * === ITERATION 2: Reverse next k=2 nodes ===
 * 
 * prevGroupEnd = 1
 * 
 * Step 1: Find kth node
 * getKthNode(1, 2) → node 4
 * kthNode = 4
 * nextGroupStart = 5
 * 
 * Step 2: Reverse nodes [3,4]
 * Before: ... → 1 → 3 → 4 → 5
 * 
 * Reversal process:
 * curr = 3, prev = 5
 * - 3.next = 5 (temp=4)
 * - curr = 4, prev = 3
 * - 4.next = 3 (temp=5)
 * - curr = 5 (exit loop)
 * 
 * After reversal: 4 → 3 → 5
 * 
 * Step 3: Connect to previous part
 * 1.next = 4
 * prevGroupEnd = 3
 * 
 * Current state: dummy → 2 → 1 → 4 → 3 → 5
 * 
 * === ITERATION 3: Check remaining ===
 * 
 * prevGroupEnd = 3
 * getKthNode(3, 2) → null (only 1 node left)
 * Break loop!
 * 
 * Final result: 2 → 1 → 4 → 3 → 5 ✓
 * 
 * 
 * POINTER DIAGRAM:
 * ----------------
 * 
 * Before reversing group [3,4]:
 * 
 * prevGroupEnd   groupStart  kthNode   nextGroupStart
 *      ↓             ↓          ↓            ↓
 *      1      →      3    →     4      →     5      →  NULL
 * 
 * After reversing [3,4]:
 * 
 * prevGroupEnd           kthNode   groupStart  nextGroupStart
 *      ↓                    ↓          ↓            ↓
 *      1      →             4    →     3      →     5      →  NULL
 * 
 * See how roles change:
 * - kthNode (4) becomes first in group
 * - groupStart (3) becomes last in group
 * 
 * 
 * WHY DUMMY NODE?
 * ---------------
 * 
 * Problem: First group changes the head!
 * 
 * Without dummy:
 * Original: 1 → 2 → 3 → 4 → 5
 * After k=2: 2 → 1 → ... (head changed to 2!)
 * Need special handling for head
 * 
 * With dummy:
 * dummy → 1 → 2 → 3 → 4 → 5
 * After k=2: dummy → 2 → 1 → ...
 * Just return dummy.next (always correct head!)
 * 
 * 
 * RECURSIVE APPROACH EXPLAINED:
 * -----------------------------
 * 
 * Think: Reverse first k, then solve rest recursively
 * 
 * reverseKGroup([1,2,3,4,5], k=2):
 * 
 * Step 1: Check if we have k=2 nodes ✓
 * 
 * Step 2: Reverse first k=2 nodes [1,2]
 * After: 2 → 1 → (rest starting at 3)
 * 
 * Step 3: Recursively solve rest
 * 1.next = reverseKGroup([3,4,5], k=2)
 * 
 * Recursive call reverseKGroup([3,4,5], k=2):
 * - Reverse [3,4] → 4 → 3 → (rest)
 * - 3.next = reverseKGroup([5], k=2)
 * 
 * Recursive call reverseKGroup([5], k=2):
 * - Only 1 node, less than k=2
 * - Return as is: [5]
 * 
 * Unwinding:
 * - 3.next = 5
 * - Return 4 → 3 → 5
 * - 1.next = 4 → 3 → 5
 * - Return 2 → 1 → 4 → 3 → 5 ✓
 * 
 * 
 * EDGE CASES HANDLING:
 * --------------------
 * 
 * 1. k = 1:
 *    No reversal needed, return original list
 * 
 * 2. k = list length:
 *    Reverse entire list once
 * 
 * 3. List length < k:
 *    Return original list (no reversal)
 * 
 * 4. Empty list:
 *    Return null
 * 
 * 5. Last group < k nodes:
 *    Leave as is (per problem statement)
 * 
 * 
 * COMMON MISTAKES:
 * ----------------
 * 
 * 1. Forgetting to check k nodes available:
 *    Always count before reversing!
 * 
 * 2. Losing connection to next group:
 *    Save nextGroupStart before reversing
 * 
 * 3. Wrong prevGroupEnd update:
 *    BAD:  prevGroupEnd = kthNode
 *    GOOD: prevGroupEnd = groupStart
 *    Why: groupStart becomes last after reversal!
 * 
 * 4. Not handling last incomplete group:
 *    Must check if k nodes exist
 * 
 * 5. Modifying values instead of pointers:
 *    Problem says "only nodes may be changed"
 * 
 * 
 * INTERVIEW VARIATIONS:
 * ---------------------
 * 
 * Q1: "Reverse alternate k groups?"
 * A: Add a flag to skip every other group
 * 
 * Q2: "What if we reverse incomplete last group too?"
 * A: Remove the count check, always reverse
 * 
 * Q3: "Reverse k nodes from end?"
 * A: First count length, then calculate groups from end
 * 
 * Q4: "Use constant extra space?"
 * A: Iterative approach already O(1) space!
 * 
 * 
 * WHEN TO USE THIS PATTERN:
 * -------------------------
 * 
 * K-group pattern useful for:
 * ✓ Reverse in groups
 * ✓ Rotate in groups
 * ✓ Process chunks of data
 * ✓ Batch operations on linked list
 * 
 * Key: Break problem into manageable groups!
 * 
 * 
 * COMPLEXITY ANALYSIS:
 * --------------------
 * 
 * Time Complexity: O(n)
 * - Each node visited twice:
 *   1. Once to count/check k nodes
 *   2. Once to reverse
 * - Total: 2n = O(n)
 * 
 * Space Complexity:
 * - Iterative: O(1)
 *   Only fixed number of pointers
 * - Recursive: O(n/k)
 *   Stack depth = number of groups
 * 
 * 
 * INTERVIEW TIPS:
 * ---------------
 * 
 * 1. Clarify requirements:
 *    "Should last incomplete group be reversed?"
 * 
 * 2. Use dummy node:
 *    "I'll use dummy to handle head changes cleanly"
 * 
 * 3. Draw it out:
 *    Show how pointers change for one group
 * 
 * 4. Explain the steps:
 *    "First count k nodes, then reverse, then connect"
 * 
 * 5. State complexity:
 *    "O(n) time, O(1) space with iterative approach"
 * 
 * 6. Mention alternative:
 *    "There's also a recursive solution, but uses O(n/k) space"
 * 
 * 
 * REMEMBER:
 * ---------
 * ✓ Always check if k nodes available
 * ✓ Save nextGroupStart before reversing
 * ✓ Update prevGroupEnd to groupStart (it's now last!)
 * ✓ Use dummy node for clean head handling
 * ✓ O(n) time, O(1) space with iterative - OPTIMAL!
 * 
 * This is HARD level for a reason - lots of pointer juggling! 🎪
 * Practice drawing it out - visual understanding is key! 🎨
 * 
 * CONGRATULATIONS! You've completed all 5 linked list problems! 🎉
 * From basic reversal to advanced k-group manipulation!
 * You now have a solid foundation in linked list algorithms! 💪
 */
