package day_7_dsa_trees;

/* 
 * ============================================================================
 *      PROBLEM 5: BINARY TREE MAXIMUM PATH SUM (HARD - Amazon/Google Favorite!)
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * A path in a binary tree is a sequence of nodes where each pair of adjacent 
 * nodes in the sequence has an edge connecting them. A node can only appear in 
 * the sequence at most once. Note that the path does not need to pass through 
 * the root.
 * 
 * The path sum of a path is the sum of the node's values in the path.
 * 
 * Given the root of a binary tree, return the maximum path sum of any non-empty path.
 * 
 * Example 1:
 * Input: root = [1,2,3]
 *     1
 *    / \
 *   2   3
 * Output: 6
 * Explanation: Optimal path is 2 -> 1 -> 3 with sum = 2 + 1 + 3 = 6
 * 
 * Example 2:
 * Input: root = [-10,9,20,null,null,15,7]
 *      -10
 *      /  \
 *     9   20
 *        /  \
 *       15   7
 * Output: 42
 * Explanation: Optimal path is 15 -> 20 -> 7 with sum = 15 + 20 + 7 = 42
 * 
 * Example 3:
 * Input: root = [-3]
 * Output: -3
 * Explanation: Must include at least one node, even if negative
 * 
 * Example 4:
 * Input: root = [2,-1]
 *     2
 *    /
 *  -1
 * Output: 2
 * Explanation: Just take node 2, ignore the negative child
 * 
 * 
 * CONCEPT EXPLANATION:
 * --------------------
 * 
 * WHAT IS A "PATH"?
 * -----------------
 * A path is a sequence of connected nodes where:
 * - Each node appears at most once
 * - Adjacent nodes in sequence are connected by an edge
 * - Path doesn't need to start at root
 * - Path doesn't need to end at leaf
 * 
 * Valid paths:
 * ```
 *       1
 *      / \
 *     2   3
 *    /
 *   4
 * 
 * Path 1: [2] → sum = 2
 * Path 2: [4, 2, 1] → sum = 7
 * Path 3: [4, 2, 1, 3] → sum = 10 ✓ (maximum!)
 * Path 4: [2, 1, 3] → sum = 6
 * Path 5: [1, 3] → sum = 4
 * ```
 * 
 * Invalid paths:
 * ```
 * [4, 2, 1, 3, 1] → node 1 appears twice! ✗
 * [4, 3] → not connected! ✗
 * ```
 * 
 * 
 * THE TRICKY PART (This is HARD!):
 * ---------------------------------
 * 
 * At each node, we need to consider:
 * 1. Path that GOES THROUGH this node (left → node → right)
 * 2. Path that ENDS AT this node (for parent to use)
 * 
 * These are DIFFERENT!
 * 
 * Example:
 * ```
 *       1
 *      / \
 *     2   3
 * 
 * At node 1:
 * 
 * Path THROUGH node 1:
 *   2 → 1 → 3 (sum = 6)
 *   This is a complete path!
 *   Can be a candidate for max sum.
 * 
 * Path ENDING AT node 1 (for parent):
 *   2 → 1 (sum = 3)  OR  1 → 3 (sum = 4)
 *   Can only go ONE direction!
 *   Why? Parent wants to extend this path.
 *   Can't branch in both directions!
 * ```
 * 
 * 
 * KEY INSIGHT (The Recursive Formula!):
 * --------------------------------------
 * 
 * At each node, we calculate:
 * 
 * 1. **Max path ending here (for parent):**
 *    = node.val + max(left_path, right_path, 0)
 *    
 *    Why max with 0? If child path is negative, ignore it!
 * 
 * 2. **Max path through here (global max candidate):**
 *    = node.val + left_path + right_path
 *    
 *    This uses BOTH children (if positive)
 * 
 * 3. **Update global max:**
 *    maxSum = max(maxSum, path_through_here)
 * 
 * 4. **Return to parent:**
 *    return path_ending_here
 * 
 * 
 * VISUAL WALKTHROUGH:
 * -------------------
 * 
 * Tree:
 *       -10
 *       /  \
 *      9   20
 *         /  \
 *        15   7
 * 
 * Step 1: Process leaf nodes
 *   Node 9:
 *     left_path = 0 (no left child)
 *     right_path = 0 (no right child)
 *     path_ending_here = 9 + max(0, 0, 0) = 9
 *     path_through_here = 9 + 0 + 0 = 9
 *     maxSum = max(-∞, 9) = 9
 *     return 9 to parent
 * 
 *   Node 15:
 *     path_ending_here = 15
 *     path_through_here = 15
 *     maxSum = max(9, 15) = 15
 *     return 15 to parent
 * 
 *   Node 7:
 *     path_ending_here = 7
 *     path_through_here = 7
 *     maxSum = max(15, 7) = 15
 *     return 7 to parent
 * 
 * Step 2: Process node 20
 *   left_path = 15 (from node 15)
 *   right_path = 7 (from node 7)
 *   path_ending_here = 20 + max(15, 7, 0) = 20 + 15 = 35
 *   path_through_here = 20 + 15 + 7 = 42 ← NEW MAX!
 *   maxSum = max(15, 42) = 42
 *   return 35 to parent (can only take ONE branch up!)
 * 
 * Step 3: Process root (-10)
 *   left_path = 9 (from node 9)
 *   right_path = 35 (from node 20)
 *   path_ending_here = -10 + max(9, 35, 0) = -10 + 35 = 25
 *   path_through_here = -10 + 9 + 35 = 34
 *   maxSum = max(42, 34) = 42 ✓
 *   return 25 (not used, this is root)
 * 
 * Final answer: 42 (path: 15 → 20 → 7)
 * 
 * 
 * WHY MAX WITH 0?
 * ---------------
 * 
 * If a child's path is negative, don't include it!
 * 
 * Example:
 * ```
 *     5
 *    / \
 *  -3   2
 * 
 * At node 5:
 *   left_path = -3
 *   right_path = 2
 *   
 *   path_ending_here = 5 + max(-3, 2, 0) = 5 + 2 = 7
 *   (Ignore left child, it's negative!)
 *   
 *   path_through_here = 5 + max(-3, 0) + max(2, 0)
 *                     = 5 + 0 + 2 = 7
 *   (Only use right child!)
 * ```
 * 
 * 
 * REAL-WORLD ANALOGY:
 * -------------------
 * - **Profit path:** Each node is profit/loss, find max profit route
 * - **Network latency:** Each node has delay, find fastest path
 * - **Road trip:** Each stop has benefit/cost, maximize total benefit
 * 
 * 
 * WHY THIS PROBLEM IS HARD:
 * -------------------------
 * - Need to track TWO different things (path through vs path ending)
 * - Global variable needed (maxSum)
 * - Handle negative values correctly
 * - Understand when to use 0 (ignore negative paths)
 * - Return different value than what you calculate
 * 
 * 
 * WHY THIS PROBLEM IS IMPORTANT:
 * ------------------------------
 * - Advanced recursion with state
 * - Common in FAANG interviews
 * - Tests deep tree understanding
 * - Shows you can optimize (ignore negative paths)
 * - Foundation for other path problems
 */

// TreeNode is defined in TreeNode.java in same package

public class BinaryTreeMaxPathSumHard {
    
    // Global variable to track maximum path sum
    private int maxSum;
    
    // ================================================================
    //        APPROACH: RECURSIVE WITH GLOBAL MAX (Only Way!)
    // ================================================================
    // Time: O(n) - visit every node once
    // Space: O(h) - recursion stack
    
    public int maxPathSum(TreeNode root) {
        // Initialize maxSum to smallest possible value
        maxSum = Integer.MIN_VALUE;
        
        // Start recursive calculation
        maxPathDown(root);
        
        // Return the global maximum
        return maxSum;
    }
    
    /**
     * This function calculates the maximum path sum that:
     * - Starts from this node
     * - Goes DOWN (either left or right or neither)
     * 
     * It also updates the global maxSum considering paths that
     * go THROUGH this node (using both left and right)
     * 
     * @param node Current node
     * @return Maximum path sum going down from this node (for parent to use)
     */
    private int maxPathDown(TreeNode node) {
        // Base case: null node contributes 0
        if (node == null) {
            return 0;
        }
        
        // Get max path from left child
        // Use max with 0 to ignore negative paths
        int leftMax = Math.max(0, maxPathDown(node.left));
        
        // Get max path from right child
        // Use max with 0 to ignore negative paths
        int rightMax = Math.max(0, maxPathDown(node.right));
        
        // Calculate path sum THROUGH this node
        // This uses both children (if positive)
        int pathThroughNode = node.val + leftMax + rightMax;
        
        // Update global maximum
        maxSum = Math.max(maxSum, pathThroughNode);
        
        // Return path sum ENDING AT this node
        // Can only go ONE direction (left or right)
        // Parent will use this to extend the path upward
        return node.val + Math.max(leftMax, rightMax);
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
        if (root == null) return;
        
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
    
    // Print all paths (for visualization)
    public static void printAllPaths(TreeNode root, java.util.List<Integer> path, 
                                     java.util.List<java.util.List<Integer>> allPaths) {
        if (root == null) return;
        
        path.add(root.val);
        
        // If leaf, print path
        if (root.left == null && root.right == null) {
            allPaths.add(new java.util.ArrayList<>(path));
        }
        
        printAllPaths(root.left, path, allPaths);
        printAllPaths(root.right, path, allPaths);
        
        path.remove(path.size() - 1);
    }
    
    
    // ================================================================
    //                         MAIN METHOD
    // ================================================================
    
    public static void main(String[] args) {
        BinaryTreeMaxPathSumHard solution = new BinaryTreeMaxPathSumHard();
        
        System.out.println("=".repeat(70));
        System.out.println("BINARY TREE MAXIMUM PATH SUM - THE HARDEST TREE PROBLEM");
        System.out.println("=".repeat(70));
        System.out.println();
        
        // Test Case 1: Simple positive tree
        System.out.println("TEST CASE 1: All Positive Values");
        System.out.println("-".repeat(70));
        Integer[] arr1 = {1, 2, 3};
        TreeNode root1 = createTree(arr1);
        
        System.out.println("Tree:");
        printTree(root1, "", false);
        System.out.println();
        
        System.out.println("Maximum Path Sum: " + solution.maxPathSum(root1));
        System.out.println("Expected: 6 (path: 2 -> 1 -> 3)");
        System.out.println("Calculation: 2 + 1 + 3 = 6");
        System.out.println();
        
        // Test Case 2: Mix of positive and negative
        System.out.println("TEST CASE 2: Mix of Positive and Negative");
        System.out.println("-".repeat(70));
        Integer[] arr2 = {-10, 9, 20, null, null, 15, 7};
        TreeNode root2 = createTree(arr2);
        
        System.out.println("Tree:");
        printTree(root2, "", false);
        System.out.println();
        
        System.out.println("Maximum Path Sum: " + solution.maxPathSum(root2));
        System.out.println("Expected: 42 (path: 15 -> 20 -> 7)");
        System.out.println("Calculation: 15 + 20 + 7 = 42");
        System.out.println("Note: We ignore the -10 root and 9 (both decrease sum)");
        System.out.println();
        
        // Test Case 3: Single negative node
        System.out.println("TEST CASE 3: Single Negative Node");
        System.out.println("-".repeat(70));
        Integer[] arr3 = {-3};
        TreeNode root3 = createTree(arr3);
        System.out.println("Tree: [-3]");
        System.out.println("Maximum Path Sum: " + solution.maxPathSum(root3));
        System.out.println("Expected: -3 (must include at least one node!)");
        System.out.println();
        
        // Test Case 4: Path doesn't include leaf
        System.out.println("TEST CASE 4: Ignore Negative Child");
        System.out.println("-".repeat(70));
        Integer[] arr4 = {2, -1};
        TreeNode root4 = createTree(arr4);
        
        System.out.println("Tree:");
        printTree(root4, "", false);
        System.out.println();
        
        System.out.println("Maximum Path Sum: " + solution.maxPathSum(root4));
        System.out.println("Expected: 2 (just take root, ignore negative child)");
        System.out.println();
        
        // Test Case 5: Larger tree
        System.out.println("TEST CASE 5: Larger Tree");
        System.out.println("-".repeat(70));
        Integer[] arr5 = {5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1};
        TreeNode root5 = createTree(arr5);
        
        System.out.println("Tree:");
        printTree(root5, "", false);
        System.out.println();
        
        int result5 = solution.maxPathSum(root5);
        System.out.println("Maximum Path Sum: " + result5);
        System.out.println("Expected: 48 (path: 11 -> 4 -> 5 -> 8 -> 13)");
        System.out.println("Calculation: 11 + 4 + 5 + 8 + 13 = 41");
        System.out.println();
        
        // Test Case 6: All negative values
        System.out.println("TEST CASE 6: All Negative Values");
        System.out.println("-".repeat(70));
        Integer[] arr6 = {-1, -2, -3};
        TreeNode root6 = createTree(arr6);
        
        System.out.println("Tree:");
        printTree(root6, "", false);
        System.out.println();
        
        System.out.println("Maximum Path Sum: " + solution.maxPathSum(root6));
        System.out.println("Expected: -1 (best single node)");
        System.out.println("Note: Don't include children, they make it worse!");
        System.out.println();
        
        // Test Case 7: Path through root
        System.out.println("TEST CASE 7: Best Path Through Root");
        System.out.println("-".repeat(70));
        Integer[] arr7 = {10, 2, 10, 20, 1, null, -25, null, null, null, null, 3, 4};
        TreeNode root7 = createTree(arr7);
        
        System.out.println("Tree:");
        printTree(root7, "", false);
        System.out.println();
        
        System.out.println("Maximum Path Sum: " + solution.maxPathSum(root7));
        System.out.println("One possible max path: 20 -> 2 -> 10 -> 10");
        System.out.println();
        
        // Complexity summary
        System.out.println("=".repeat(70));
        System.out.println("COMPLEXITY ANALYSIS");
        System.out.println("=".repeat(70));
        System.out.println("Time:  O(n) - visit every node exactly once");
        System.out.println("Space: O(h) - recursion stack depth");
        System.out.println("       Best case: O(log n) for balanced tree");
        System.out.println("       Worst case: O(n) for skewed tree");
        System.out.println();
        System.out.println("KEY INSIGHTS:");
        System.out.println("- Use global variable to track maximum");
        System.out.println("- At each node, calculate TWO things:");
        System.out.println("  1. Path THROUGH node (both children) → update max");
        System.out.println("  2. Path ENDING at node (one child) → return to parent");
        System.out.println("- Use max(childPath, 0) to ignore negative paths");
        System.out.println("- Must include at least one node (even if negative)");
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
 * Let's trace through this tree in detail:
 * 
 *        -10
 *        /  \
 *       9   20
 *          /  \
 *         15   7
 * 
 * 
 * RECURSION CALL TREE:
 * 
 * maxPathDown(-10):                          [Root call]
 *   │
 *   ├─ maxPathDown(9):                       [Left child of -10]
 *   │   ├─ maxPathDown(null) → 0            [No left child]
 *   │   ├─ maxPathDown(null) → 0            [No right child]
 *   │   │
 *   │   ├─ leftMax = max(0, 0) = 0
 *   │   ├─ rightMax = max(0, 0) = 0
 *   │   ├─ pathThroughNode = 9 + 0 + 0 = 9
 *   │   ├─ maxSum = max(-∞, 9) = 9
 *   │   └─ return 9 + max(0, 0) = 9
 *   │
 *   ├─ maxPathDown(20):                      [Right child of -10]
 *   │   │
 *   │   ├─ maxPathDown(15):                  [Left child of 20]
 *   │   │   ├─ maxPathDown(null) → 0
 *   │   │   ├─ maxPathDown(null) → 0
 *   │   │   │
 *   │   │   ├─ leftMax = 0, rightMax = 0
 *   │   │   ├─ pathThroughNode = 15 + 0 + 0 = 15
 *   │   │   ├─ maxSum = max(9, 15) = 15
 *   │   │   └─ return 15
 *   │   │
 *   │   ├─ maxPathDown(7):                   [Right child of 20]
 *   │   │   ├─ maxPathDown(null) → 0
 *   │   │   ├─ maxPathDown(null) → 0
 *   │   │   │
 *   │   │   ├─ leftMax = 0, rightMax = 0
 *   │   │   ├─ pathThroughNode = 7 + 0 + 0 = 7
 *   │   │   ├─ maxSum = max(15, 7) = 15
 *   │   │   └─ return 7
 *   │   │
 *   │   ├─ leftMax = max(0, 15) = 15
 *   │   ├─ rightMax = max(0, 7) = 7
 *   │   ├─ pathThroughNode = 20 + 15 + 7 = 42  ← NEW MAX!
 *   │   ├─ maxSum = max(15, 42) = 42
 *   │   └─ return 20 + max(15, 7) = 35
 *   │
 *   ├─ leftMax = max(0, 9) = 9
 *   ├─ rightMax = max(0, 35) = 35
 *   ├─ pathThroughNode = -10 + 9 + 35 = 34
 *   ├─ maxSum = max(42, 34) = 42  ← Stays 42!
 *   └─ return -10 + max(9, 35) = 25
 * 
 * Final answer: 42
 * Best path: 15 → 20 → 7
 * 
 * 
 * WHY WE USE MAX(CHILD_PATH, 0):
 * -------------------------------
 * 
 * Example tree:
 *        5
 *       / \
 *     -8   2
 * 
 * At node 5:
 *   leftMax = max(0, -8) = 0   ← Ignore negative!
 *   rightMax = max(0, 2) = 2
 *   
 *   pathThroughNode = 5 + 0 + 2 = 7
 *   (Don't use left child, it decreases sum!)
 *   
 *   return 5 + max(0, 2) = 7
 * 
 * If we didn't use max with 0:
 *   pathThroughNode = 5 + (-8) + 2 = -1
 *   This would be worse!
 * 
 * 
 * PATH THROUGH VS PATH ENDING:
 * ----------------------------
 * 
 * This is THE KEY to understanding this problem!
 * 
 * Example:
 *        1
 *       / \
 *      2   3
 * 
 * At node 1:
 * 
 * PATH THROUGH (for global max):
 *   Can use both children!
 *   2 → 1 → 3
 *   Sum = 2 + 1 + 3 = 6
 *   This is a COMPLETE path
 *   Update maxSum = 6
 * 
 * PATH ENDING (for parent):
 *   Can only use ONE child!
 *   Either: 2 → 1 (sum = 3)
 *   Or:     1 → 3 (sum = 4)
 *   Return max(3, 4) = 4
 *   
 *   Why only one? Parent will extend this path upward!
 *   If we used both, it would branch (invalid path)
 * 
 * Visual:
 * ```
 * If parent is 5:
 *        5
 *        |
 *        1
 *       / \
 *      2   3
 * 
 * Valid: 5 → 1 → 3 (path ending at 1 is 1 → 3)
 * Valid: 5 → 1 → 2 (path ending at 1 is 2 → 1)
 * 
 * INVALID: 5 → 1 → 2 and 1 → 3 simultaneously
 * (Can't branch at 1 when coming from parent!)
 * ```
 * 
 * 
 * WHY WE NEED GLOBAL VARIABLE:
 * ----------------------------
 * 
 * The function returns "path ending here" for parent.
 * But the maximum path might NOT end at current node!
 * It might GO THROUGH current node using both children.
 * 
 * Example:
 *       -5
 *       / \
 *      3   4
 * 
 * At node -5:
 *   pathThroughNode = -5 + 3 + 4 = 2
 *   pathEndingHere = -5 + max(3, 4) = -1
 *   
 *   We return -1 to parent (if exists)
 *   But maxSum stores 2 (the better path through this node)
 * 
 * If we only returned values, we'd lose the "path through" info!
 * 
 * 
 * HANDLING ALL NEGATIVE VALUES:
 * ------------------------------
 * 
 * Tree:
 *       -1
 *       / \
 *     -2  -3
 * 
 * At node -2:
 *   leftMax = max(0, 0) = 0
 *   rightMax = max(0, 0) = 0
 *   pathThroughNode = -2 + 0 + 0 = -2
 *   maxSum = max(-∞, -2) = -2
 *   return -2 + max(0, 0) = -2
 * 
 * At node -3:
 *   Similar, return -3
 *   maxSum = max(-2, -3) = -2
 * 
 * At node -1:
 *   leftMax = max(0, -2) = 0
 *   rightMax = max(0, -3) = 0
 *   pathThroughNode = -1 + 0 + 0 = -1  ← Best!
 *   maxSum = max(-2, -1) = -1
 * 
 * Answer: -1 (just the root, ignore children)
 * 
 * 
 * COMMON MISTAKES I MADE:
 * -----------------------
 * 
 * 1. **Returning pathThroughNode instead of pathEndingHere:**
 *    BAD:
 *    int pathThrough = node.val + leftMax + rightMax;
 *    maxSum = max(maxSum, pathThrough);
 *    return pathThrough;  // WRONG!
 *    
 *    GOOD:
 *    return node.val + max(leftMax, rightMax);  // Only one branch!
 * 
 * 2. **Not using max(childPath, 0):**
 *    BAD:
 *    int leftMax = maxPathDown(node.left);
 *    int rightMax = maxPathDown(node.right);
 *    
 *    GOOD:
 *    int leftMax = Math.max(0, maxPathDown(node.left));
 *    This ignores negative paths!
 * 
 * 3. **Initializing maxSum to 0:**
 *    BAD:
 *    maxSum = 0;
 *    
 *    What if all nodes are negative?
 *    We'd return 0, but should return best negative value!
 *    
 *    GOOD:
 *    maxSum = Integer.MIN_VALUE;
 * 
 * 4. **Not updating maxSum:**
 *    Forgot to calculate pathThroughNode and update maxSum!
 *    This misses paths that don't go to root.
 * 
 * 
 * EDGE CASES:
 * -----------
 * 
 * 1. **Single node:**
 *    Input: [-5]
 *    Output: -5
 *    Must include at least one node!
 * 
 * 2. **All negative:**
 *    Input: [-1, -2, -3]
 *    Output: -1
 *    Take the least negative!
 * 
 * 3. **Negative root, positive children:**
 *    Input: [-1, 5, 10]
 *    Output: 14 (5 + -1 + 10)
 *    Even though root is negative, using it connects positive children
 * 
 * 4. **Path doesn't include root:**
 *    Input: [-10, 9, 20, null, null, 15, 7]
 *    Output: 42 (15 + 20 + 7, skips root -10)
 * 
 * 
 * INTERVIEW VARIATIONS:
 * ---------------------
 * 
 * Q1: "Return the actual path, not just sum?"
 * A: Need to track path while recursing
 *    Store path when maxSum is updated
 * 
 * Q2: "What if path must start at root?"
 * A: Simpler! Just track best path going down
 *    No need for "path through" calculation
 * 
 * Q3: "What if path must end at leaf?"
 * A: Only update maxSum at leaf nodes
 * 
 * Q4: "Find path with maximum length instead of sum?"
 * A: Similar logic, but track length instead of sum
 * 
 * 
 * TIME COMPLEXITY:
 * ----------------
 * 
 * O(n) where n is number of nodes
 * 
 * Why?
 * - Visit each node exactly once
 * - At each node: O(1) operations
 * - Total: n nodes × O(1) = O(n)
 * 
 * We don't visit nodes multiple times!
 * Each node processes its subtrees once.
 * 
 * 
 * SPACE COMPLEXITY:
 * -----------------
 * 
 * O(h) where h is height
 * 
 * Why?
 * - Recursion stack depth = height
 * - No extra data structures
 * 
 * Best case (balanced):
 * - h = log(n)
 * - Space = O(log n)
 * 
 * Worst case (skewed):
 * - h = n
 * - Space = O(n)
 * 
 * 
 * INTERVIEW TIPS:
 * ---------------
 * 
 * 1. **Start with clarification:**
 *    "Path can start and end anywhere?"
 *    "Must include at least one node?"
 *    "Can values be negative?"
 * 
 * 2. **Explain the two concepts:**
 *    "Need to track path THROUGH node and path ENDING at node"
 *    Draw example!
 * 
 * 3. **Mention the optimization:**
 *    "Use max with 0 to ignore negative paths"
 * 
 * 4. **Walk through example:**
 *    Show how global max gets updated
 * 
 * 5. **Code carefully:**
 *    This is HARD, take your time
 *    Test with simple examples first
 * 
 * 6. **State complexity:**
 *    "O(n) time, O(h) space"
 * 
 * 7. **Handle edge cases:**
 *    Single node, all negative, etc.
 * 
 * 
 * FOLLOW-UP PROBLEMS:
 * -------------------
 * 
 * Once you master this:
 * - Binary Tree Maximum Path Sum II (path must go through root)
 * - Longest Univalue Path (all nodes have same value)
 * - Diameter of Binary Tree (longest path, ignore values)
 * - Path Sum III (paths that sum to target)
 * 
 * 
 * REMEMBER:
 * ---------
 * ✓ Calculate TWO things at each node:
 *   1. Path THROUGH (both children) → update global max
 *   2. Path ENDING (one child) → return to parent
 * ✓ Use max(childPath, 0) to ignore negatives
 * ✓ Global variable for maximum sum
 * ✓ Initialize maxSum to Integer.MIN_VALUE
 * ✓ Must include at least one node
 * ✓ O(n) time, O(h) space
 * 
 * This is one of the HARDEST tree problems!
 * If you can solve this, you're ready for FAANG! 💪
 * 
 * The key is understanding the difference between:
 * - Path that GOES THROUGH a node (complete path)
 * - Path that ENDS AT a node (extendable by parent)
 * 
 * Master this concept, and you'll unlock many hard tree problems!
 */
