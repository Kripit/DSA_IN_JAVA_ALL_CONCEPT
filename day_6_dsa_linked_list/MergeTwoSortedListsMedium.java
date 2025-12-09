package day_6_dsa_linked_list;

/*
 * ============================================================================
 *     PROBLEM 3: MERGE TWO SORTED LINKED LISTS (MEDIUM - Classic!)
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * You are given the heads of two sorted linked lists list1 and list2.
 * 
 * Merge the two lists into one SORTED list. The list should be made by
 * splicing together the nodes of the first two lists.
 * 
 * Return the head of the merged linked list.
 * 
 * Example 1:
 * Input: list1 = [1,2,4], list2 = [1,3,4]
 * Output: [1,1,2,3,4,4]
 * 
 * Example 2:
 * Input: list1 = [], list2 = [0]
 * Output: [0]
 * 
 * Example 3:
 * Input: list1 = [], list2 = []
 * Output: []
 * 
 * 
 * CONCEPT EXPLANATION:
 * --------------------
 * 
 * WHAT IS MERGING?
 * Think of two sorted decks of cards:
 * Deck A: 1, 3, 5
 * Deck B: 2, 4, 6
 * 
 * Goal: Combine into ONE sorted deck
 * Result: 1, 2, 3, 4, 5, 6
 * 
 * HOW?
 * 1. Compare top cards of both decks
 * 2. Pick the smaller one
 * 3. Add to result
 * 4. Repeat until both decks empty
 * 
 * 
 * VISUALIZATION:
 * --------------
 * 
 * list1: 1 → 2 → 4
 * list2: 1 → 3 → 4
 * 
 * Step 1: Compare 1 vs 1 (equal, take from list1)
 * Result: 1 →
 * list1: 2 → 4
 * list2: 1 → 3 → 4
 * 
 * Step 2: Compare 2 vs 1 (1 smaller, take from list2)
 * Result: 1 → 1 →
 * list1: 2 → 4
 * list2: 3 → 4
 * 
 * Step 3: Compare 2 vs 3 (2 smaller, take from list1)
 * Result: 1 → 1 → 2 →
 * list1: 4
 * list2: 3 → 4
 * 
 * Step 4: Compare 4 vs 3 (3 smaller, take from list2)
 * Result: 1 → 1 → 2 → 3 →
 * list1: 4
 * list2: 4
 * 
 * Step 5: Compare 4 vs 4 (equal, take from list1)
 * Result: 1 → 1 → 2 → 3 → 4 →
 * list1: empty
 * list2: 4
 * 
 * Step 6: list1 empty, append remaining list2
 * Result: 1 → 1 → 2 → 3 → 4 → 4 ✓
 * 
 * 
 * KEY TECHNIQUE: DUMMY NODE
 * -------------------------
 * 
 * Problem: How to handle the head of result?
 * 
 * Without dummy:
 * - Need special case for first node
 * - Complex logic to determine head
 * 
 * With dummy:
 * - Dummy node points to result
 * - Simple: just return dummy.next
 * - No special cases needed!
 * 
 * Dummy node pattern:
 * dummy → (actual result starts here)
 *   ↑
 * Return dummy.next
 */

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class MergeTwoSortedListsMedium {
    
    // ================================================================
    //        APPROACH 1: ITERATIVE with DUMMY NODE - OPTIMAL!
    // ================================================================
    // Time: O(m + n) where m, n are lengths of lists
    // Space: O(1) - Only pointers, no new nodes created
    
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        // Create dummy node to simplify edge cases
        ListNode dummy = new ListNode(-1);
        ListNode current = dummy;  // Pointer to build result
        
        // Compare and merge while both lists have nodes
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;  // Add list1 node to result
                list1 = list1.next;    // Move list1 pointer
            } else {
                current.next = list2;  // Add list2 node to result
                list2 = list2.next;    // Move list2 pointer
            }
            current = current.next;    // Move result pointer
        }
        
        // Append remaining nodes (one list is empty now)
        if (list1 != null) {
            current.next = list1;
        } else {
            current.next = list2;
        }
        
        // Return actual head (skip dummy)
        return dummy.next;
    }
    
    
    // ================================================================
    //        APPROACH 2: RECURSIVE (Elegant but uses stack space)
    // ================================================================
    // Time: O(m + n)
    // Space: O(m + n) - Recursion stack
    
    public ListNode mergeTwoListsRecursive(ListNode list1, ListNode list2) {
        // Base cases
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        
        // Recursive case: pick smaller and recurse
        if (list1.val <= list2.val) {
            list1.next = mergeTwoListsRecursive(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoListsRecursive(list1, list2.next);
            return list2;
        }
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
        
        // Count nodes
        int count = 0;
        ListNode temp = head;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        
        // Fill array
        int[] arr = new int[count];
        temp = head;
        for (int i = 0; i < count; i++) {
            arr[i] = temp.val;
            temp = temp.next;
        }
        return arr;
    }
    
    
    // ================================================================
    //                         MAIN METHOD
    // ================================================================
    
    public static void main(String[] args) {
        MergeTwoSortedListsMedium solution = new MergeTwoSortedListsMedium();
        
        System.out.println("=".repeat(70));
        System.out.println("MERGE TWO SORTED LINKED LISTS - DUMMY NODE TECHNIQUE");
        System.out.println("=".repeat(70));
        System.out.println();
        
        // Test Case 1: Both lists have elements
        System.out.println("TEST CASE 1: Normal Case");
        System.out.println("-".repeat(70));
        int[] arr1 = {1, 2, 4};
        int[] arr2 = {1, 3, 4};
        ListNode list1 = createList(arr1);
        ListNode list2 = createList(arr2);
        
        System.out.print("List 1: ");
        printList(list1);
        System.out.print("List 2: ");
        printList(list2);
        
        ListNode merged1 = solution.mergeTwoLists(list1, list2);
        System.out.print("Merged: ");
        printList(merged1);
        System.out.println("Expected: [1,1,2,3,4,4]");
        System.out.println();
        
        // Test Case 2: One empty list
        System.out.println("TEST CASE 2: One Empty List");
        System.out.println("-".repeat(70));
        ListNode list3 = null;
        ListNode list4 = createList(new int[]{0});
        
        System.out.print("List 1: ");
        printList(list3);
        System.out.print("List 2: ");
        printList(list4);
        
        ListNode merged2 = solution.mergeTwoLists(list3, list4);
        System.out.print("Merged: ");
        printList(merged2);
        System.out.println();
        
        // Test Case 3: Both empty
        System.out.println("TEST CASE 3: Both Empty");
        System.out.println("-".repeat(70));
        ListNode list5 = null;
        ListNode list6 = null;
        
        System.out.print("List 1: ");
        printList(list5);
        System.out.print("List 2: ");
        printList(list6);
        
        ListNode merged3 = solution.mergeTwoLists(list5, list6);
        System.out.print("Merged: ");
        printList(merged3);
        System.out.println();
        
        // Test Case 4: Different lengths
        System.out.println("TEST CASE 4: Different Lengths");
        System.out.println("-".repeat(70));
        int[] arr7 = {1, 3, 5, 7, 9};
        int[] arr8 = {2, 4};
        ListNode list7 = createList(arr7);
        ListNode list8 = createList(arr8);
        
        System.out.print("List 1: ");
        printList(list7);
        System.out.print("List 2: ");
        printList(list8);
        
        ListNode merged4 = solution.mergeTwoLists(list7, list8);
        System.out.print("Merged: ");
        printList(merged4);
        System.out.println();
        
        // Test Case 5: Recursive approach
        System.out.println("TEST CASE 5: Recursive Approach");
        System.out.println("-".repeat(70));
        int[] arr9 = {1, 2, 4};
        int[] arr10 = {1, 3, 4};
        ListNode list9 = createList(arr9);
        ListNode list10 = createList(arr10);
        
        System.out.print("List 1: ");
        printList(list9);
        System.out.print("List 2: ");
        printList(list10);
        
        ListNode merged5 = solution.mergeTwoListsRecursive(list9, list10);
        System.out.print("Merged (Recursive): ");
        printList(merged5);
        System.out.println();
        
        // Complexity comparison
        System.out.println("=".repeat(70));
        System.out.println("APPROACH COMPARISON");
        System.out.println("=".repeat(70));
        System.out.println("Iterative (Dummy Node):");
        System.out.println("  Time:  O(m + n)");
        System.out.println("  Space: O(1)");
        System.out.println("  ✓ OPTIMAL - Recommended for interviews!");
        System.out.println();
        System.out.println("Recursive:");
        System.out.println("  Time:  O(m + n)");
        System.out.println("  Space: O(m + n) - Stack space");
        System.out.println("  Elegant but uses extra space");
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
 * Example: Merge [1,2,4] and [1,3,4]
 * 
 * Initial state:
 * list1 → 1 → 2 → 4
 * list2 → 1 → 3 → 4
 * dummy → (empty)
 * current = dummy
 * 
 * Iteration 1:
 * Compare: 1 (list1) vs 1 (list2)
 * 1 <= 1, so pick list1
 * dummy → 1
 * current = 1
 * list1 = 2 → 4
 * list2 = 1 → 3 → 4
 * 
 * Iteration 2:
 * Compare: 2 (list1) vs 1 (list2)
 * 2 > 1, so pick list2
 * dummy → 1 → 1
 * current = 1 (second)
 * list1 = 2 → 4
 * list2 = 3 → 4
 * 
 * Iteration 3:
 * Compare: 2 (list1) vs 3 (list2)
 * 2 <= 3, so pick list1
 * dummy → 1 → 1 → 2
 * current = 2
 * list1 = 4
 * list2 = 3 → 4
 * 
 * Iteration 4:
 * Compare: 4 (list1) vs 3 (list2)
 * 4 > 3, so pick list2
 * dummy → 1 → 1 → 2 → 3
 * current = 3
 * list1 = 4
 * list2 = 4
 * 
 * Iteration 5:
 * Compare: 4 (list1) vs 4 (list2)
 * 4 <= 4, so pick list1
 * dummy → 1 → 1 → 2 → 3 → 4
 * current = 4
 * list1 = null
 * list2 = 4
 * 
 * Loop exits (list1 is null)
 * 
 * Append remaining:
 * current.next = list2 (which is 4)
 * dummy → 1 → 1 → 2 → 3 → 4 → 4
 * 
 * Return dummy.next = [1,1,2,3,4,4] ✓
 * 
 * 
 * WHY DUMMY NODE IS BRILLIANT:
 * ----------------------------
 * 
 * Without dummy node:
 * ListNode head = null;
 * if (list1.val <= list2.val) {
 *     head = list1;
 *     list1 = list1.next;
 * } else {
 *     head = list2;
 *     list2 = list2.next;
 * }
 * // Now continue merging...
 * // Need to handle current pointer separately
 * // More complex!
 * 
 * With dummy node:
 * ListNode dummy = new ListNode(-1);
 * ListNode current = dummy;
 * // Uniform logic from start!
 * // No special case for head
 * // Just return dummy.next at end
 * 
 * DUMMY NODE PATTERN:
 * Works for ANY problem where building new list!
 * 
 * 
 * RECURSIVE APPROACH EXPLAINED:
 * -----------------------------
 * 
 * Think of it like this:
 * merge(l1, l2) = smaller of (l1, l2) + merge(rest)
 * 
 * Example: merge([1,2,4], [1,3,4])
 * 
 * Step 1: Compare 1 vs 1
 * Pick 1 from l1
 * Result: 1 + merge([2,4], [1,3,4])
 * 
 * Step 2: Compare 2 vs 1
 * Pick 1 from l2
 * Result: 1 + 1 + merge([2,4], [3,4])
 * 
 * Step 3: Compare 2 vs 3
 * Pick 2 from l1
 * Result: 1 + 1 + 2 + merge([4], [3,4])
 * 
 * Step 4: Compare 4 vs 3
 * Pick 3 from l2
 * Result: 1 + 1 + 2 + 3 + merge([4], [4])
 * 
 * Step 5: Compare 4 vs 4
 * Pick 4 from l1
 * Result: 1 + 1 + 2 + 3 + 4 + merge([], [4])
 * 
 * Step 6: l1 empty, return l2
 * Result: 1 + 1 + 2 + 3 + 4 + 4 ✓
 * 
 * RECURSION UNWINDING:
 * Each call returns merged list from that point
 * Building result from bottom up!
 * 
 * 
 * EDGE CASES HANDLING:
 * --------------------
 * 
 * 1. Both lists empty:
 *    dummy.next = null
 *    Return null ✓
 * 
 * 2. One list empty:
 *    While loop never executes
 *    Append non-empty list
 *    Return that list ✓
 * 
 * 3. Different lengths:
 *    Loop until shorter ends
 *    Append rest of longer
 *    Works perfectly ✓
 * 
 * 4. Equal values:
 *    Pick from list1 (using <=)
 *    Both orders valid ✓
 * 
 * 
 * COMMON MISTAKES:
 * ----------------
 * 
 * 1. Forgetting to return dummy.next:
 *    BAD:  return dummy
 *    GOOD: return dummy.next
 *    Why: dummy is placeholder, real list starts at dummy.next
 * 
 * 2. Not moving current pointer:
 *    BAD:  current.next = list1 (but current stays same)
 *    GOOD: current = current.next (move along!)
 * 
 * 3. Creating new nodes:
 *    BAD:  current.next = new ListNode(list1.val)
 *    GOOD: current.next = list1 (reuse existing!)
 *    Why: Problem says "splice together", not create new
 * 
 * 4. Wrong comparison:
 *    BAD:  if (list1.val < list2.val)
 *    GOOD: if (list1.val <= list2.val)
 *    Why: For equal values, need to pick one (pick list1)
 * 
 * 
 * INTERVIEW VARIATIONS:
 * ---------------------
 * 
 * Q1: "Merge K sorted lists?"
 * A: Use min-heap (priority queue)
 *    Time: O(n log k) where n = total nodes, k = lists
 * 
 * Q2: "Merge in descending order?"
 * A: Change comparison to >=
 * 
 * Q3: "Merge and remove duplicates?"
 * A: After merging, skip nodes with same value
 * 
 * Q4: "Merge using O(1) space (in-place)?"
 * A: Already O(1)! Just rearranging pointers
 * 
 * 
 * WHEN TO USE THIS PATTERN:
 * -------------------------
 * 
 * Dummy node technique useful for:
 * ✓ Merging lists
 * ✓ Removing nodes
 * ✓ Partitioning lists
 * ✓ Inserting at head
 * ✓ Building new list from old
 * 
 * General rule:
 * If building new list and don't know head initially,
 * USE DUMMY NODE!
 * 
 * 
 * COMPLEXITY ANALYSIS:
 * --------------------
 * 
 * Time Complexity: O(m + n)
 * - m = length of list1
 * - n = length of list2
 * - Visit each node exactly once
 * 
 * Space Complexity:
 * - Iterative: O(1)
 *   Only dummy and current pointers
 * - Recursive: O(m + n)
 *   Stack depth = length of result
 * 
 * Iterative is BETTER for interviews!
 * 
 * 
 * INTERVIEW TIPS:
 * ---------------
 * 
 * 1. Explain dummy node:
 *    "I'll use dummy node to simplify edge cases"
 * 
 * 2. Walk through example:
 *    Show how pointers move step by step
 * 
 * 3. Mention space efficiency:
 *    "This is O(1) space - just rearranging pointers"
 * 
 * 4. Handle edge cases:
 *    "Let me check if either list is empty"
 * 
 * 5. Code cleanly:
 *    Clear variable names (dummy, current, list1, list2)
 * 
 * 
 * REMEMBER:
 * ---------
 * ✓ Dummy node simplifies head handling
 * ✓ Compare and pick smaller each iteration
 * ✓ Append remaining nodes at end
 * ✓ Return dummy.next (not dummy!)
 * ✓ O(m+n) time, O(1) space - OPTIMAL!
 * 
 * This is MERGE from Merge Sort - foundational algorithm! 🎯
 */
