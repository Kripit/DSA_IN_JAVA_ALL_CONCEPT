package day_7_dsa_trees;

/*
 * ============================================================================
 *    PROBLEM 4: LOWEST COMMON ANCESTOR OF BST (MEDIUM - Interview Favorite!)
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * Given a binary search tree (BST), find the lowest common ancestor (LCA) of 
 * two given nodes in the BST.
 * 
 * According to the definition of LCA:
 * "The lowest common ancestor is defined between two nodes p and q as the 
 *  lowest node in T that has both p and q as descendants (where we allow 
 *  a node to be a descendant of itself)."
 * 
 * Example 1:
 * Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
 *         6
 *       /   \
 *      2     8
 *     / \   / \
 *    0   4 7   9
 *       / \
 *      3   5
 * Output: 6
 * Explanation: LCA of nodes 2 and 8 is 6.
 * 
 * Example 2:
 * Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
 * Output: 2
 * Explanation: LCA of 2 and 4 is 2, since a node can be descendant of itself.
 * 
 * Example 3:
 * Input: root = [2,1], p = 2, q = 1
 * Output: 2
 * 
 * 
 * CONCEPT EXPLANATION:
 * --------------------
 * 
 * WHAT IS "LOWEST COMMON ANCESTOR"?
 * ----------------------------------
 * Think of a family tree:
 * - You and your cousin both have a common grandparent
 * - The LOWEST (closest to you) common ancestor is your grandparent
 * 
 * Visual:
 * ```
 *         Grandpa
 *        /       \
 *    Parent1    Parent2
 *      |          |
 *     You      Cousin
 * 
 * LCA(You, Cousin) = Grandpa
 * ```
 * 
 * In tree terms:
 * ```
 *         6
 *       /   \
 *      2     8
 *     / \
 *    0   4
 * 
 * LCA(0, 4) = 2  (not 6, because 2 is lower/closer)
 * LCA(2, 8) = 6  (6 is the split point)
 * LCA(2, 4) = 2  (node can be its own ancestor!)
 * ```
 * 
 * 
 * KEY INSIGHT FOR BST (This makes it easy!):
 * -------------------------------------------
 * 
 * In a BST, we have this amazing property:
 * - If p and q are BOTH less than current node → LCA is in LEFT subtree
 * - If p and q are BOTH greater than current node → LCA is in RIGHT subtree
 * - Otherwise (one left, one right, or one is current) → current node IS the LCA!
 * 
 * Example:
 * ```
 *         6
 *       /   \
 *      2     8
 *     / \   / \
 *    0   4 7   9
 * 
 * Find LCA(0, 4):
 * At 6: 0 < 6 and 4 < 6 → both left → go left
 * At 2: 0 < 2 but 4 > 2 → split! → LCA is 2 ✓
 * 
 * Find LCA(7, 9):
 * At 6: 7 > 6 and 9 > 6 → both right → go right
 * At 8: 7 < 8 but 9 > 8 → split! → LCA is 8 ✓
 * 
 * Find LCA(2, 8):
 * At 6: 2 < 6 but 8 > 6 → split! → LCA is 6 ✓
 * ```
 * 
 * 
 * WHY THIS WORKS:
 * ---------------
 * 
 * The LCA is the "split point" where:
 * - One node is in the left subtree
 * - Other node is in the right subtree
 * - OR one of them IS the current node
 * 
 * BST property lets us determine this WITHOUT searching!
 * - Just compare values
 * - O(h) time, not O(n)!
 * 
 * 
 * VISUAL WALKTHROUGH:
 * -------------------
 * 
 * Tree:
 *         6
 *       /   \
 *      2     8
 *     / \   / \
 *    0   4 7   9
 *       / \
 *      3   5
 * 
 * Find LCA(3, 5):
 * 
 * At node 6:
 *   3 < 6? YES
 *   5 < 6? YES
 *   Both left! Go left.
 * 
 * At node 2:
 *   3 > 2? YES
 *   5 > 2? YES
 *   Both right! Go right.
 * 
 * At node 4:
 *   3 < 4? YES
 *   5 > 4? YES
 *   Split! LCA found! Return 4 ✓
 * 
 * 
 * REAL-WORLD ANALOGY:
 * -------------------
 * - **File system:** Common parent directory of two files
 * - **Organization chart:** Common manager of two employees
 * - **Network routing:** Common node in path between two computers
 * - **Git:** Common commit ancestor of two branches
 * 
 * 
 * WHY THIS PROBLEM IS IMPORTANT:
 * ------------------------------
 * - Common in interviews (especially for BST!)
 * - Tests understanding of BST properties
 * - Shows how to optimize using structure
 * - Foundation for more complex tree problems
 * - Real applications in routing, path finding, etc.
 */

// TreeNode is defined in TreeNode.java in same package

public class LowestCommonAncestorMedium {
    
    // ================================================================
    //        APPROACH 1: RECURSIVE (Clean & Elegant!)
    // ================================================================
    // Time: O(h) - height of tree (not O(n)! We don't visit all nodes!)
    // Space: O(h) - recursion stack
    
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // Base case: reached null (shouldn't happen if p, q exist)
        if (root == null) {
            return null;
        }
        
        // If both p and q are less than root, LCA is in left subtree
        if (p.val < root.val && q.val < root.val) {
            return lowestCommonAncestor(root.left, p, q);
        }
        
        // If both p and q are greater than root, LCA is in right subtree
        if (p.val > root.val && q.val > root.val) {
            return lowestCommonAncestor(root.right, p, q);
        }
        
        // Otherwise, we found the split point!
        // This happens when:
        // 1. p and q are on different sides of root
        // 2. OR one of them IS the root
        return root;
    }
    
    
    // ================================================================
    //        APPROACH 2: ITERATIVE (No Recursion!)
    // ================================================================
    // Time: O(h)
    // Space: O(1) - no extra space!
    
    public TreeNode lowestCommonAncestorIterative(TreeNode root, TreeNode p, TreeNode q) {
        TreeNode current = root;
        
        while (current != null) {
            // Both in left subtree
            if (p.val < current.val && q.val < current.val) {
                current = current.left;
            }
            // Both in right subtree
            else if (p.val > current.val && q.val > current.val) {
                current = current.right;
            }
            // Found split point!
            else {
                return current;
            }
        }
        
        return null;  // Should never reach here if p, q exist in tree
    }
    
    
    // ================================================================
    //        BONUS: BINARY TREE LCA (Not BST!)
    // ================================================================
    // Time: O(n) - might need to visit all nodes
    // Space: O(h)
    // 
    // This works for ANY binary tree (not just BST)
    // Can't use value comparison, need to actually search!
    
    public TreeNode lowestCommonAncestorBinaryTree(TreeNode root, TreeNode p, TreeNode q) {
        // Base case
        if (root == null || root == p || root == q) {
            return root;
        }
        
        // Search in left and right subtrees
        TreeNode left = lowestCommonAncestorBinaryTree(root.left, p, q);
        TreeNode right = lowestCommonAncestorBinaryTree(root.right, p, q);
        
        // If both found something, current node is LCA
        if (left != null && right != null) {
            return root;
        }
        
        // Otherwise, return whichever is not null
        return left != null ? left : right;
    }
    
    
    // ================================================================
    //        HELPER METHODS
    // ================================================================
    
    // Create BST from sorted array (for testing)
    public static TreeNode createBST(int[] arr, int start, int end) {
        if (start > end) return null;
        
        int mid = start + (end - start) / 2;
        TreeNode node = new TreeNode(arr[mid]);
        node.left = createBST(arr, start, mid - 1);
        node.right = createBST(arr, mid + 1, end);
        
        return node;
    }
    
    // Find node with given value
    public static TreeNode findNode(TreeNode root, int val) {
        if (root == null) return null;
        if (root.val == val) return root;
        if (val < root.val) return findNode(root.left, val);
        return findNode(root.right, val);
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
    
    // Print path from root to node
    public static boolean printPath(TreeNode root, TreeNode target, String path) {
        if (root == null) return false;
        
        path += root.val;
        
        if (root == target) {
            System.out.print(path);
            return true;
        }
        
        if (printPath(root.left, target, path + " -> ") ||
            printPath(root.right, target, path + " -> ")) {
            return true;
        }
        
        return false;
    }
    
    
    // ================================================================
    //                         MAIN METHOD
    // ================================================================
    
    public static void main(String[] args) {
        LowestCommonAncestorMedium solution = new LowestCommonAncestorMedium();
        
        System.out.println("=".repeat(70));
        System.out.println("LOWEST COMMON ANCESTOR IN BST - THE SPLIT POINT");
        System.out.println("=".repeat(70));
        System.out.println();
        
        // Create BST: [0,2,3,4,5,6,7,8,9]
        int[] arr = {0, 2, 3, 4, 5, 6, 7, 8, 9};
        TreeNode root = createBST(arr, 0, arr.length - 1);
        
        System.out.println("BST Structure:");
        printTree(root, "", false);
        System.out.println();
        
        // Test Case 1: Nodes on different sides
        System.out.println("TEST CASE 1: Nodes on Different Sides");
        System.out.println("-".repeat(70));
        TreeNode p1 = findNode(root, 2);
        TreeNode q1 = findNode(root, 8);
        
        System.out.print("Path to " + p1.val + ": ");
        printPath(root, p1, "");
        System.out.println();
        
        System.out.print("Path to " + q1.val + ": ");
        printPath(root, q1, "");
        System.out.println();
        
        TreeNode lca1 = solution.lowestCommonAncestor(root, p1, q1);
        System.out.println("\nLCA(" + p1.val + ", " + q1.val + ") = " + lca1.val);
        System.out.println("Expected: 5 (split point)");
        System.out.println();
        
        // Test Case 2: One node is ancestor of other
        System.out.println("TEST CASE 2: One Node is Ancestor");
        System.out.println("-".repeat(70));
        TreeNode p2 = findNode(root, 2);
        TreeNode q2 = findNode(root, 3);
        
        System.out.print("Path to " + p2.val + ": ");
        printPath(root, p2, "");
        System.out.println();
        
        System.out.print("Path to " + q2.val + ": ");
        printPath(root, q2, "");
        System.out.println();
        
        TreeNode lca2 = solution.lowestCommonAncestorIterative(root, p2, q2);
        System.out.println("\nLCA(" + p2.val + ", " + q2.val + ") = " + lca2.val);
        System.out.println("Expected: 2 (node can be its own ancestor)");
        System.out.println();
        
        // Test Case 3: Both nodes in same subtree
        System.out.println("TEST CASE 3: Both Nodes in Same Subtree");
        System.out.println("-".repeat(70));
        TreeNode p3 = findNode(root, 0);
        TreeNode q3 = findNode(root, 3);
        
        System.out.print("Path to " + p3.val + ": ");
        printPath(root, p3, "");
        System.out.println();
        
        System.out.print("Path to " + q3.val + ": ");
        printPath(root, q3, "");
        System.out.println();
        
        TreeNode lca3 = solution.lowestCommonAncestor(root, p3, q3);
        System.out.println("\nLCA(" + p3.val + ", " + q3.val + ") = " + lca3.val);
        System.out.println("Expected: 2 (both in left subtree of 5)");
        System.out.println();
        
        // Test Case 4: Deepest nodes
        System.out.println("TEST CASE 4: Leaf Nodes");
        System.out.println("-".repeat(70));
        TreeNode p4 = findNode(root, 0);
        TreeNode q4 = findNode(root, 9);
        
        System.out.print("Path to " + p4.val + ": ");
        printPath(root, p4, "");
        System.out.println();
        
        System.out.print("Path to " + q4.val + ": ");
        printPath(root, q4, "");
        System.out.println();
        
        TreeNode lca4 = solution.lowestCommonAncestor(root, p4, q4);
        System.out.println("\nLCA(" + p4.val + ", " + q4.val + ") = " + lca4.val);
        System.out.println("Expected: 5 (root is split point)");
        System.out.println();
        
        // Test Case 5: Adjacent nodes
        System.out.println("TEST CASE 5: Adjacent Nodes");
        System.out.println("-".repeat(70));
        TreeNode p5 = findNode(root, 3);
        TreeNode q5 = findNode(root, 4);
        
        TreeNode lca5 = solution.lowestCommonAncestorIterative(root, p5, q5);
        System.out.println("LCA(" + p5.val + ", " + q5.val + ") = " + lca5.val);
        System.out.println("Expected: 3");
        System.out.println();
        
        // Complexity comparison
        System.out.println("=".repeat(70));
        System.out.println("APPROACH COMPARISON");
        System.out.println("=".repeat(70));
        System.out.println("BST Recursive:");
        System.out.println("  Time:  O(h) - only traverse one path!");
        System.out.println("  Space: O(h) - recursion stack");
        System.out.println("  ✓ Clean and elegant");
        System.out.println();
        System.out.println("BST Iterative:");
        System.out.println("  Time:  O(h)");
        System.out.println("  Space: O(1) - no extra space!");
        System.out.println("  ✓ Most efficient");
        System.out.println("  ✓ RECOMMENDED for BST!");
        System.out.println();
        System.out.println("Binary Tree (not BST):");
        System.out.println("  Time:  O(n) - might visit all nodes");
        System.out.println("  Space: O(h)");
        System.out.println("  Use when tree is not BST");
        System.out.println("=".repeat(70));
    }
}


/*
 * ============================================================================
 *                    DETAILED WALKTHROUGH & INSIGHTS
 * ============================================================================
 * 
 * UNDERSTANDING THE BST APPROACH:
 * --------------------------------
 * 
 * Tree:
 *         6
 *       /   \
 *      2     8
 *     / \   / \
 *    0   4 7   9
 *       / \
 *      3   5
 * 
 * Find LCA(3, 5):
 * 
 * Step 1: Start at root (6)
 *   p = 3, q = 5
 *   Is 3 < 6? YES
 *   Is 5 < 6? YES
 *   Both less than 6 → go LEFT
 * 
 * Step 2: At node 2
 *   Is 3 < 2? NO (3 > 2)
 *   Is 5 < 2? NO (5 > 2)
 *   Both greater than 2 → go RIGHT
 * 
 * Step 3: At node 4
 *   Is 3 < 4? YES
 *   Is 5 < 4? NO (5 > 4)
 *   SPLIT! One less, one greater → LCA found!
 *   Return 4 ✓
 * 
 * Notice: We only visited 3 nodes (6, 2, 4)!
 * Not all 9 nodes. This is O(h), not O(n)!
 * 
 * 
 * WHY THE "SPLIT" WORKS:
 * ----------------------
 * 
 * When p and q are on DIFFERENT sides of current node:
 * - One is in left subtree
 * - One is in right subtree
 * - Current node is their first common ancestor!
 * 
 * Visual:
 * ```
 *         6         ← Both 0 and 9 have 6 as ancestor
 *       /   \
 *      2     8      ← 0 is under 2, 9 is under 8
 *     /       \
 *    0         9   ← Different branches!
 * 
 * LCA(0, 9) = 6 (the split point)
 * ```
 * 
 * When one IS the current node:
 * ```
 *      2
 *     / \
 *    0   4
 * 
 * LCA(2, 4):
 * At node 2:
 * - p = 2 (equals current!)
 * - q = 4 (greater than current)
 * - Not both less, not both greater
 * - So 2 is the LCA!
 * ```
 * 
 * 
 * ITERATIVE VS RECURSIVE:
 * -----------------------
 * 
 * Recursive:
 * - More intuitive
 * - Easier to write
 * - Uses O(h) space for call stack
 * 
 * Iterative:
 * - O(1) space!
 * - Slightly more efficient
 * - No risk of stack overflow
 * 
 * Code comparison:
 * 
 * Recursive:
 *   if (both < root) return lca(root.left, p, q);
 *   if (both > root) return lca(root.right, p, q);
 *   return root;
 * 
 * Iterative:
 *   while (current != null) {
 *       if (both < current) current = current.left;
 *       else if (both > current) current = current.right;
 *       else return current;
 *   }
 * 
 * Almost identical logic!
 * 
 * 
 * BINARY TREE LCA (Not BST):
 * ---------------------------
 * 
 * When you can't use BST property:
 * - Can't compare values to decide direction
 * - Need to actually SEARCH for nodes
 * - Must potentially visit all nodes
 * 
 * Algorithm:
 * 1. If current node is p or q, return it
 * 2. Search left subtree
 * 3. Search right subtree
 * 4. If found in BOTH sides → current is LCA
 * 5. If found in ONE side → return that side
 * 
 * Example:
 * ```
 *         1
 *       /   \
 *      2     3
 *     / \
 *    4   5
 * 
 * Find LCA(4, 5):
 * 
 * At node 1:
 *   left = lca(2, 4, 5)
 *   right = lca(3, 4, 5)
 * 
 * At node 2:
 *   left = lca(4, 4, 5) → returns 4 (found p!)
 *   right = lca(5, 4, 5) → returns 5 (found q!)
 *   Both found! Return 2
 * 
 * Back at node 1:
 *   left = 2 (found!)
 *   right = null (not found)
 *   Return left (2) ✓
 * ```
 * 
 * This is O(n) because we might search entire tree!
 * 
 * 
 * COMMON MISTAKES I MADE:
 * -----------------------
 * 
 * 1. **Forgetting node can be its own ancestor:**
 *    BAD:
 *    if (root == p || root == q) {
 *        // Thought this was error case!
 *    }
 *    
 *    GOOD:
 *    This is valid! Return root immediately.
 * 
 * 2. **Only checking one node:**
 *    BAD:
 *    if (p.val < root.val) return lca(root.left, p, q);
 *    
 *    GOOD:
 *    Check BOTH p and q!
 *    if (p.val < root.val && q.val < root.val) ...
 * 
 * 3. **Using == for value comparison:**
 *    BAD:
 *    if (p == root.val) ...
 *    
 *    GOOD:
 *    if (p.val == root.val) ...
 *    OR better: if (p == root) ... (compare nodes!)
 * 
 * 4. **Not handling null:**
 *    Always check if root is null first!
 * 
 * 
 * EDGE CASES:
 * -----------
 * 
 * 1. **One node is ancestor of other:**
 *    Input: p = 2, q = 4
 *         2
 *          \
 *           4
 *    Output: 2
 *    Node is its own ancestor!
 * 
 * 2. **Nodes are adjacent:**
 *    Input: p = 2, q = 3
 *         2
 *          \
 *           3
 *    Output: 2
 * 
 * 3. **Root is LCA:**
 *    Input: p = 0, q = 9
 *         5
 *        / \
 *       0   9
 *    Output: 5
 * 
 * 4. **Deep tree:**
 *    Still O(h), not O(n)!
 * 
 * 
 * INTERVIEW VARIATIONS:
 * ---------------------
 * 
 * Q1: "What if it's not a BST?"
 * A: Use the binary tree approach (O(n))
 *    Can't use value comparison
 * 
 * Q2: "What if nodes might not exist in tree?"
 * A: Add verification step
 *    First check if both nodes exist
 * 
 * Q3: "Find distance between two nodes?"
 * A: Find LCA first
 *    Then: distance = dist(LCA, p) + dist(LCA, q)
 * 
 * Q4: "Find LCA of multiple nodes?"
 * A: Find LCA of first two
 *    Then find LCA of result and third node
 *    Repeat
 * 
 * Q5: "What if we have parent pointers?"
 * A: Move both up until they meet!
 *    Like finding intersection of linked lists
 * 
 * 
 * TIME COMPLEXITY ANALYSIS:
 * -------------------------
 * 
 * BST: O(h) where h is height
 * 
 * Why only O(h) and not O(n)?
 * - At each node, we choose ONE direction
 * - Never visit both children
 * - Path length = height
 * 
 * Best case (balanced BST):
 * - h = log(n)
 * - Time = O(log n)
 * 
 * Worst case (skewed BST):
 * - h = n
 * - Time = O(n)
 * 
 * Example (balanced):
 *         6
 *       /   \
 *      2     8
 *     / \   / \
 *    0   4 7   9
 * 
 * Height = 3, Nodes = 7
 * To find any LCA: visit at most 3 nodes
 * 
 * Example (skewed):
 *   1
 *    \
 *     2
 *      \
 *       3
 *        \
 *         4
 * 
 * Height = 4, Nodes = 4
 * To find LCA(1, 4): visit all 4 nodes
 * 
 * 
 * SPACE COMPLEXITY:
 * -----------------
 * 
 * Recursive: O(h)
 * - Call stack stores path
 * 
 * Iterative: O(1)
 * - Only stores current pointer!
 * - No extra space needed
 * - This is why iterative is preferred
 * 
 * 
 * WHEN TO USE EACH:
 * -----------------
 * 
 * BST ITERATIVE:
 * ✓ Most efficient (O(1) space)
 * ✓ RECOMMENDED for interviews!
 * ✓ Clean code
 * 
 * BST RECURSIVE:
 * ✓ More intuitive
 * ✓ Easier to explain
 * ✓ Good alternative
 * 
 * BINARY TREE (not BST):
 * ✓ When BST property doesn't exist
 * ✓ More general solution
 * ✓ O(n) time required
 * 
 * 
 * INTERVIEW TIPS:
 * ---------------
 * 
 * 1. **Clarify BST property:**
 *    "Is this a BST or just binary tree?"
 *    "Can I use value comparison?"
 * 
 * 2. **Explain the split:**
 *    "The LCA is where paths to p and q diverge"
 *    Draw it on whiteboard!
 * 
 * 3. **Mention the optimization:**
 *    "Because it's BST, I only need O(h) not O(n)"
 * 
 * 4. **Code iterative:**
 *    Shows you understand space efficiency
 * 
 * 5. **Mention binary tree version:**
 *    "For non-BST, I would use postorder traversal"
 * 
 * 6. **Test edge cases:**
 *    Node is its own ancestor
 *    Root is LCA
 *    Adjacent nodes
 * 
 * 7. **State complexity:**
 *    "O(h) time, O(1) space for iterative"
 * 
 * 
 * FOLLOW-UP PROBLEMS:
 * -------------------
 * 
 * Once you master this:
 * - Distance Between Nodes in BST
 * - Path Sum II (all root-to-leaf paths)
 * - Lowest Common Ancestor of Binary Tree (not BST)
 * - Lowest Common Ancestor of Deepest Leaves
 * 
 * 
 * REMEMBER:
 * ---------
 * ✓ BST property makes this O(h) not O(n)!
 * ✓ Split point is where paths diverge
 * ✓ Both less → go left
 * ✓ Both greater → go right
 * ✓ Otherwise → found it!
 * ✓ Iterative is O(1) space
 * ✓ Node can be its own ancestor
 * 
 * This problem shows the POWER of BST property!
 * Use the structure wisely! 🌲
 */
