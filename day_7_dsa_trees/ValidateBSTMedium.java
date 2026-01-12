package day_7_dsa_trees;

/*
 * ============================================================================
 *      PROBLEM 3: VALIDATE BINARY SEARCH TREE (MEDIUM - Interview Classic!)
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * Given the root of a binary tree, determine if it is a valid binary search tree (BST).
 * 
 * A valid BST is defined as follows:
 * - The left subtree of a node contains only nodes with keys LESS than the node's key
 * - The right subtree of a node contains only nodes with keys GREATER than the node's key
 * - Both left and right subtrees must also be BSTs
 * 
 * Example 1:
 * Input: root = [2,1,3]
 *     2
 *    / \
 *   1   3
 * Output: true
 * Explanation: 1 < 2 < 3, valid BST
 * 
 * Example 2:
 * Input: root = [5,1,4,null,null,3,6]
 *      5
 *     / \
 *    1   4
 *       / \
 *      3   6
 * Output: false
 * Explanation: 4 is in right subtree of 5, so should be > 5, but 3 is < 5!
 * 
 * Example 3:
 * Input: root = [5,4,6,null,null,3,7]
 *      5
 *     / \
 *    4   6
 *       / \
 *      3   7
 * Output: false
 * Explanation: 3 is in right subtree of 5, but 3 < 5. Invalid!
 * 
 * 
 * CONCEPT EXPLANATION:
 * --------------------
 * 
 * WHAT IS A BST? (Binary Search Tree)
 * ------------------------------------
 * A BST is like a sorted array, but in tree form!
 * 
 * BST Property:
 * - For ANY node:
 *   - ALL nodes in left subtree < current node
 *   - ALL nodes in right subtree > current node
 * 
 * Visual:
 * ```
 * VALID BST:
 *       5
 *      / \
 *     3   7
 *    / \   \
 *   1   4   9
 * 
 * Check node 5:
 * - Left subtree: 3,1,4 (all < 5) ✓
 * - Right subtree: 7,9 (all > 5) ✓
 * 
 * INVALID BST:
 *       5
 *      / \
 *     3   7
 *    / \   \
 *   1   6   9
 * 
 * Check node 5:
 * - Left subtree: 3,1,6
 *   But 6 > 5! NOT all < 5 ✗
 * ```
 * 
 * 
 * THE TRICKY PART (This is where people fail!):
 * ----------------------------------------------
 * 
 * ❌ WRONG APPROACH:
 * Just check: left.val < root.val < right.val
 * 
 * Why? Because you're only checking immediate children!
 * 
 * Example where naive approach fails:
 * ```
 *       5
 *      / \
 *     1   4
 *        / \
 *       3   6
 * 
 * Naive check at node 4:
 * - left (3) < 4 ✓
 * - right (6) > 4 ✓
 * Looks valid!
 * 
 * But wait: 4 is in RIGHT subtree of 5
 * So EVERYTHING in that subtree should be > 5
 * But 3 < 5 and 4 < 5!
 * This is INVALID!
 * ```
 * 
 * 
 * KEY INSIGHT (This is the solution!):
 * -------------------------------------
 * 
 * At each node, we need to check:
 * - Is current value within a RANGE?
 * 
 * Range = [min allowed, max allowed]
 * 
 * - Root: range is [-∞, +∞]
 * - Go left: update max to current value
 * - Go right: update min to current value
 * 
 * Example:
 * ```
 *       5           range: [-∞, +∞]
 *      / \
 *     3   7
 *    / \
 *   1   4
 * 
 * Check 5: is 5 in [-∞, +∞]? YES ✓
 * 
 * Go left to 3: range becomes [-∞, 5]
 * Check 3: is 3 in [-∞, 5]? YES ✓
 * 
 * Go left to 1: range becomes [-∞, 3]
 * Check 1: is 1 in [-∞, 3]? YES ✓
 * 
 * Go right to 4: range becomes [3, 5]
 * Check 4: is 4 in [3, 5]? YES ✓
 * 
 * Go right to 7: range becomes [5, +∞]
 * Check 7: is 7 in [5, +∞]? YES ✓
 * 
 * All checks pass → Valid BST!
 * ```
 * 
 * Another example (invalid):
 * ```
 *       5           range: [-∞, +∞]
 *      / \
 *     1   4         
 *        / \
 *       3   6
 * 
 * Check 5: OK
 * Go right to 4: range becomes [5, +∞]
 * Check 4: is 4 in [5, +∞]? NO! 4 < 5 ✗
 * Invalid!
 * ```
 * 
 * 
 * REAL-WORLD ANALOGY:
 * -------------------
 * - **Age restrictions:** Person must be within valid age range for activity
 * - **Product pricing:** Price must be within min-max range
 * - **Tournament seeding:** Rankings must follow bracket rules
 * 
 * 
 * WHY THIS PROBLEM IS IMPORTANT:
 * ------------------------------
 * - BSTs are EVERYWHERE (databases, file systems, etc.)
 * - Tests understanding of tree properties
 * - Common in interviews
 * - Foundation for AVL trees, Red-Black trees, etc.
 * - Shows you understand the difference between local and global constraints
 */

// TreeNode is defined in TreeNode.java in same package

public class ValidateBSTMedium {
    
    // ================================================================
    //        APPROACH 1: RECURSIVE WITH RANGE (Best!)
    // ================================================================
    // Time: O(n) - visit every node once
    // Space: O(h) - recursion stack
    
    public boolean isValidBST(TreeNode root) {
        // Start with range [-infinity, +infinity]
        return isValidBSTHelper(root, null, null);
    }
    
    private boolean isValidBSTHelper(TreeNode node, Integer min, Integer max) {
        // Base case: empty tree is valid BST
        if (node == null) {
            return true;
        }
        
        // Check if current node is within range
        // If min exists, current must be > min
        if (min != null && node.val <= min) {
            return false;
        }
        
        // If max exists, current must be < max
        if (max != null && node.val >= max) {
            return false;
        }
        
        // Recursively check subtrees with updated ranges
        // Left subtree: all values must be < current (update max)
        // Right subtree: all values must be > current (update min)
        return isValidBSTHelper(node.left, min, node.val) &&
               isValidBSTHelper(node.right, node.val, max);
    }
    
    
    // ================================================================
    //        APPROACH 2: INORDER TRAVERSAL (Elegant!)
    // ================================================================
    // Time: O(n)
    // Space: O(h)
    // 
    // Key insight: Inorder traversal of BST gives SORTED array!
    // So we just check if inorder is strictly increasing.
    
    private Integer prev = null;
    
    public boolean isValidBSTInorder(TreeNode root) {
        prev = null;  // Reset for each test
        return inorderCheck(root);
    }
    
    private boolean inorderCheck(TreeNode node) {
        if (node == null) return true;
        
        // Check left subtree
        if (!inorderCheck(node.left)) {
            return false;
        }
        
        // Check current node
        // Must be greater than previous node in inorder
        if (prev != null && node.val <= prev) {
            return false;
        }
        prev = node.val;
        
        // Check right subtree
        return inorderCheck(node.right);
    }
    
    
    // ================================================================
    //        APPROACH 3: ITERATIVE INORDER (No Recursion!)
    // ================================================================
    // Time: O(n)
    // Space: O(h)
    
    public boolean isValidBSTIterative(TreeNode root) {
        java.util.Stack<TreeNode> stack = new java.util.Stack<>();
        TreeNode current = root;
        Integer prev = null;
        
        while (current != null || !stack.isEmpty()) {
            // Go all the way left
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            
            // Process node
            current = stack.pop();
            
            // Check if strictly increasing
            if (prev != null && current.val <= prev) {
                return false;
            }
            prev = current.val;
            
            // Go right
            current = current.right;
        }
        
        return true;
    }
    
    
    // ================================================================
    //        APPROACH 4: USING LONG FOR EDGE CASES
    // ================================================================
    // Handles case where node values are Integer.MIN_VALUE or MAX_VALUE
    
    public boolean isValidBSTWithLong(TreeNode root) {
        return isValidBSTLongHelper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    private boolean isValidBSTLongHelper(TreeNode node, long min, long max) {
        if (node == null) return true;
        
        if (node.val <= min || node.val >= max) {
            return false;
        }
        
        return isValidBSTLongHelper(node.left, min, node.val) &&
               isValidBSTLongHelper(node.right, node.val, max);
    }
    
    
    // ================================================================
    //        HELPER METHODS
    // ================================================================
    
    // Create tree from array (level-order)
    public static TreeNode createTree(Integer[] arr) {
        if (arr == null || arr.length == 0 || arr[0] == null) return null;
        
        TreeNode root = new TreeNode(arr[0]);
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.offer(root);
        int i = 1;
        
        while (!queue.isEmpty() && i < arr.length) {
            TreeNode node = queue.poll();
            
            if (i < arr.length && arr[i] != null) {
                node.left = new TreeNode(arr[i]);
                queue.offer(node.left);
            }
            i++;
            
            if (i < arr.length && arr[i] != null) {
                node.right = new TreeNode(arr[i]);
                queue.offer(node.right);
            }
            i++;
        }
        
        return root;
    }
    
    // Print tree structure
    public static void printTree(TreeNode root, String prefix, boolean isLeft) {
        if (root == null) {
            return;
        }
        
        System.out.println(prefix + (isLeft ? "├── " : "└── ") + root.val);
        
        if (root.left != null || root.right != null) {
            if (root.left != null) {
                printTree(root.left, prefix + (isLeft ? "│   " : "    "), true);
            }
            if (root.right != null) {
                printTree(root.right, prefix + (isLeft ? "│   " : "    "), false);
            }
        }
    }
    
    // Print inorder traversal (should be sorted for valid BST)
    public static void printInorder(TreeNode root, java.util.List<Integer> result) {
        if (root == null) return;
        printInorder(root.left, result);
        result.add(root.val);
        printInorder(root.right, result);
    }
    
    
    // ================================================================
    //                         MAIN METHOD
    // ================================================================
    
    public static void main(String[] args) {
        ValidateBSTMedium solution = new ValidateBSTMedium();
        
        System.out.println("=".repeat(70));
        System.out.println("VALIDATE BINARY SEARCH TREE - THE RANGE TRICK");
        System.out.println("=".repeat(70));
        System.out.println();
        
        // Test Case 1: Valid BST
        System.out.println("TEST CASE 1: Valid BST");
        System.out.println("-".repeat(70));
        Integer[] arr1 = {2, 1, 3};
        TreeNode root1 = createTree(arr1);
        
        System.out.println("Tree:");
        printTree(root1, "", false);
        
        java.util.List<Integer> inorder1 = new java.util.ArrayList<>();
        printInorder(root1, inorder1);
        System.out.println("Inorder: " + inorder1 + " (sorted = valid!)");
        
        System.out.println("\nIs Valid BST (Range method)? " + solution.isValidBST(root1));
        System.out.println("Is Valid BST (Inorder method)? " + solution.isValidBSTInorder(root1));
        System.out.println("Expected: true");
        System.out.println();
        
        // Test Case 2: Invalid - right child smaller than root
        System.out.println("TEST CASE 2: Invalid - Child violates root");
        System.out.println("-".repeat(70));
        Integer[] arr2 = {5, 1, 4, null, null, 3, 6};
        TreeNode root2 = createTree(arr2);
        
        System.out.println("Tree:");
        printTree(root2, "", false);
        System.out.println("\nProblem: 4 is right child of 5, but 4 < 5!");
        System.out.println("Also: 3 is in right subtree of 5, but 3 < 5!");
        
        java.util.List<Integer> inorder2 = new java.util.ArrayList<>();
        printInorder(root2, inorder2);
        System.out.println("Inorder: " + inorder2 + " (NOT sorted!)");
        
        System.out.println("\nIs Valid BST? " + solution.isValidBST(root2));
        System.out.println("Expected: false");
        System.out.println();
        
        // Test Case 3: Invalid - deeper violation
        System.out.println("TEST CASE 3: Invalid - Deeper Violation");
        System.out.println("-".repeat(70));
        Integer[] arr3 = {5, 4, 6, null, null, 3, 7};
        TreeNode root3 = createTree(arr3);
        
        System.out.println("Tree:");
        printTree(root3, "", false);
        System.out.println("\nProblem: 3 is in right subtree of 5, but 3 < 5!");
        System.out.println("This is the TRICKY case!");
        
        java.util.List<Integer> inorder3 = new java.util.ArrayList<>();
        printInorder(root3, inorder3);
        System.out.println("Inorder: " + inorder3 + " (NOT sorted!)");
        
        System.out.println("\nIs Valid BST? " + solution.isValidBST(root3));
        System.out.println("Expected: false");
        System.out.println();
        
        // Test Case 4: Valid larger BST
        System.out.println("TEST CASE 4: Valid Larger BST");
        System.out.println("-".repeat(70));
        Integer[] arr4 = {10, 5, 15, 3, 7, 13, 18};
        TreeNode root4 = createTree(arr4);
        
        System.out.println("Tree:");
        printTree(root4, "", false);
        
        java.util.List<Integer> inorder4 = new java.util.ArrayList<>();
        printInorder(root4, inorder4);
        System.out.println("Inorder: " + inorder4 + " (sorted!)");
        
        System.out.println("\nIs Valid BST (Iterative)? " + solution.isValidBSTIterative(root4));
        System.out.println("Expected: true");
        System.out.println();
        
        // Test Case 5: Single node
        System.out.println("TEST CASE 5: Single Node");
        System.out.println("-".repeat(70));
        Integer[] arr5 = {1};
        TreeNode root5 = createTree(arr5);
        System.out.println("Tree: [1]");
        System.out.println("Is Valid BST? " + solution.isValidBST(root5));
        System.out.println("Expected: true");
        System.out.println();
        
        // Test Case 6: Duplicate values (invalid!)
        System.out.println("TEST CASE 6: Duplicate Values");
        System.out.println("-".repeat(70));
        Integer[] arr6 = {5, 5, 5};
        TreeNode root6 = createTree(arr6);
        System.out.println("Tree:");
        printTree(root6, "", false);
        System.out.println("\nProblem: BST requires STRICT inequality!");
        System.out.println("Left child should be < parent, not <=");
        System.out.println("\nIs Valid BST? " + solution.isValidBST(root6));
        System.out.println("Expected: false");
        System.out.println();
        
        // Approach comparison
        System.out.println("=".repeat(70));
        System.out.println("APPROACH COMPARISON");
        System.out.println("=".repeat(70));
        System.out.println("Range Method:");
        System.out.println("  Time:  O(n)");
        System.out.println("  Space: O(h)");
        System.out.println("  ✓ Most intuitive");
        System.out.println("  ✓ Recommended for interviews!");
        System.out.println();
        System.out.println("Inorder Method:");
        System.out.println("  Time:  O(n)");
        System.out.println("  Space: O(h)");
        System.out.println("  ✓ Clever use of BST property");
        System.out.println("  ✓ Shows deep understanding");
        System.out.println();
        System.out.println("Iterative Inorder:");
        System.out.println("  Time:  O(n)");
        System.out.println("  Space: O(h)");
        System.out.println("  ✓ No recursion");
        System.out.println("  ✓ More complex code");
        System.out.println("=".repeat(70));
    }
}


/*
 * ============================================================================
 *                    DETAILED WALKTHROUGH & INSIGHTS
 * ============================================================================
 * 
 * UNDERSTANDING THE RANGE APPROACH:
 * ----------------------------------
 * 
 * Let's trace through this tree step by step:
 *       5
 *      / \
 *     3   7
 *    / \
 *   1   4
 * 
 * 
 * CALL STACK WITH RANGES:
 * 
 * isValidBST(5, min=null, max=null):
 *   Check: is 5 in (null, null)? YES ✓
 *   │
 *   ├─ isValidBST(3, min=null, max=5):
 *   │   Check: is 3 in (null, 5)? YES (3 < 5) ✓
 *   │   │
 *   │   ├─ isValidBST(1, min=null, max=3):
 *   │   │   Check: is 1 in (null, 3)? YES (1 < 3) ✓
 *   │   │   └─ return true
 *   │   │
 *   │   └─ isValidBST(4, min=3, max=5):
 *   │       Check: is 4 in (3, 5)? YES (3 < 4 < 5) ✓
 *   │       └─ return true
 *   │
 *   └─ isValidBST(7, min=5, max=null):
 *       Check: is 7 in (5, null)? YES (7 > 5) ✓
 *       └─ return true
 * 
 * All checks pass → return true ✓
 * 
 * 
 * NOW WITH INVALID TREE:
 * 
 *       5
 *      / \
 *     1   4
 *        / \
 *       3   6
 * 
 * isValidBST(5, min=null, max=null):
 *   Check: is 5 in (null, null)? YES ✓
 *   │
 *   ├─ isValidBST(1, min=null, max=5):
 *   │   Check: is 1 in (null, 5)? YES ✓
 *   │   └─ return true
 *   │
 *   └─ isValidBST(4, min=5, max=null):
 *       Check: is 4 in (5, null)?
 *       4 <= 5? YES → FAIL ✗
 *       └─ return false
 * 
 * Found violation! Return false.
 * 
 * 
 * WHY INORDER WORKS:
 * ------------------
 * 
 * BST Property: Inorder traversal gives SORTED sequence!
 * 
 * Example:
 *       5
 *      / \
 *     3   7
 *    / \
 *   1   4
 * 
 * Inorder: 1, 3, 4, 5, 7
 * Is this strictly increasing? YES → Valid BST!
 * 
 * Invalid example:
 *       5
 *      / \
 *     1   4
 *        / \
 *       3   6
 * 
 * Inorder: 1, 3, 4, 5, 6
 * Wait... this looks sorted!
 * 
 * Actually:
 * Inorder: 1, 5, 3, 4, 6
 * (Order: left of 5 (1), then 5, then inorder of right subtree)
 * 
 * Let me redo:
 * - Visit left subtree of 5: just 1
 * - Visit 5
 * - Visit right subtree of 5:
 *   - Visit left of 4: 3
 *   - Visit 4
 *   - Visit right of 4: 6
 * 
 * So inorder is: 1, 5, 3, 4, 6
 * Is 5 < 3? NO! Not sorted → Invalid!
 * 
 * 
 * INORDER STEP BY STEP:
 * ----------------------
 * 
 * Valid tree:
 *       5
 *      / \
 *     3   7
 *    /
 *   1
 * 
 * Inorder traversal:
 * 1. Go left to 3
 * 2. Go left to 1
 * 3. Visit 1 (prev = 1)
 * 4. Back to 3, visit 3 (3 > 1? YES, prev = 3)
 * 5. Back to 5, visit 5 (5 > 3? YES, prev = 5)
 * 6. Go right to 7, visit 7 (7 > 5? YES, prev = 7)
 * 
 * All increasing → Valid!
 * 
 * 
 * COMMON MISTAKES I MADE:
 * -----------------------
 * 
 * 1. **Only checking immediate children:**
 *    BAD:
 *    if (node.left != null && node.left.val >= node.val) return false;
 *    if (node.right != null && node.right.val <= node.val) return false;
 *    
 *    This fails for:
 *         5
 *        / \
 *       1   6
 *          / \
 *         4   7
 *    
 *    At node 6: 4 < 6 and 7 > 6 ✓
 *    But 4 < 5! Should fail!
 * 
 * 2. **Using Integer.MIN_VALUE as initial min:**
 *    BAD:
 *    isValidBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
 *    
 *    What if tree has node with value Integer.MIN_VALUE?
 *    Our check would incorrectly fail!
 *    
 *    GOOD:
 *    Use null or Long.MIN_VALUE/MAX_VALUE
 * 
 * 3. **Forgetting equal case:**
 *    BAD:
 *    if (node.val < min || node.val > max) return false;
 *    
 *    GOOD:
 *    if (node.val <= min || node.val >= max) return false;
 *    
 *    Why? BST requires STRICT inequality!
 *    Duplicate values make it invalid.
 * 
 * 4. **Not resetting prev in inorder method:**
 *    If you run multiple tests, prev keeps old value!
 *    Reset it at the start.
 * 
 * 
 * EDGE CASES TO HANDLE:
 * ---------------------
 * 
 * 1. **Empty tree:**
 *    Input: null
 *    Output: true
 *    Empty tree is valid BST by definition
 * 
 * 2. **Single node:**
 *    Input: [5]
 *    Output: true
 *    One node is always valid
 * 
 * 3. **Duplicate values:**
 *    Input: [2,2,2]
 *    Output: false
 *    BST doesn't allow duplicates (in this definition)
 * 
 * 4. **Integer.MIN_VALUE or MAX_VALUE:**
 *    Input: [-2147483648]
 *    Be careful with range bounds!
 *    Use Long or null to handle this
 * 
 * 5. **Two nodes:**
 *    Input: [1,2] or [2,1]
 *    Should both be valid (depending on structure)
 * 
 * 
 * INTERVIEW VARIATIONS:
 * ---------------------
 * 
 * Q1: "What if duplicate values are allowed in left subtree?"
 * A: Change <= to < in min check:
 *    if (min != null && node.val < min) return false;
 * 
 * Q2: "Find the first invalid node?"
 * A: Keep track of the node where violation occurs
 *    Return that node instead of boolean
 * 
 * Q3: "What's the closest valid BST?"
 * A: This becomes much harder!
 *    Need to restructure tree to satisfy BST property
 * 
 * Q4: "Is this a balanced BST?"
 * A: Need to check both BST property AND balance
 *    Balance: |left_height - right_height| <= 1 at each node
 * 
 * 
 * SPACE COMPLEXITY DEEP DIVE:
 * ----------------------------
 * 
 * All approaches: O(h) where h is height
 * 
 * Why?
 * - Recursive: call stack depth = height
 * - Iterative: stack stores path from root to current
 * 
 * Best case (balanced tree):
 * - Height = log(n)
 * - Space = O(log n)
 * 
 * Worst case (skewed tree):
 * - Height = n
 * - Space = O(n)
 * 
 * Example worst case:
 *   1
 *    \
 *     2
 *      \
 *       3
 *        \
 *         4
 * 
 * When checking node 4:
 * - Call stack: [1, 2, 3, 4]
 * - Depth = 4 = n
 * 
 * 
 * WHICH APPROACH TO USE?
 * ----------------------
 * 
 * RANGE METHOD:
 * ✓ Most intuitive
 * ✓ Easy to explain in interview
 * ✓ Handles all edge cases naturally
 * → RECOMMENDED FOR INTERVIEWS!
 * 
 * INORDER METHOD:
 * ✓ Shows you know BST properties
 * ✓ Elegant solution
 * ✓ Can mention as alternative
 * 
 * ITERATIVE:
 * ✓ If recursion is not allowed
 * ✓ More complex code
 * ✓ Use if specifically asked
 * 
 * 
 * INTERVIEW TIPS:
 * ---------------
 * 
 * 1. **Start with clarification:**
 *    "Can the BST have duplicate values?"
 *    "What should I return for empty tree?"
 * 
 * 2. **Explain the trap:**
 *    "The naive approach of just checking immediate children fails"
 *    Draw the counter-example!
 * 
 * 3. **Present the range idea:**
 *    "I'll track valid range at each node"
 *    "Left updates max, right updates min"
 * 
 * 4. **Code it clean:**
 *    Use helper function for recursive approach
 * 
 * 5. **Test with examples:**
 *    Valid tree, invalid tree, edge cases
 * 
 * 6. **State complexity:**
 *    "O(n) time - visit all nodes"
 *    "O(h) space - recursion stack"
 * 
 * 7. **Mention alternative:**
 *    "I could also use inorder traversal"
 *    "BST's inorder is sorted!"
 * 
 * 
 * FOLLOW-UP PROBLEMS:
 * -------------------
 * 
 * Once you master this:
 * - Lowest Common Ancestor in BST (use BST property!)
 * - Kth Smallest Element in BST (inorder traversal)
 * - Convert Sorted Array to BST (binary search + recursion)
 * - Recover BST (two nodes swapped)
 * 
 * 
 * REMEMBER:
 * ---------
 * ✓ Don't just check immediate children!
 * ✓ Use range: [min, max] at each node
 * ✓ Left updates max, right updates min
 * ✓ Use null for initial unbounded range
 * ✓ BST requires STRICT inequality (no duplicates)
 * ✓ Inorder of valid BST is sorted
 * ✓ O(n) time, O(h) space
 * 
 * This problem separates those who truly understand BSTs
 * from those who just memorize! Master it! 🌲
 */
