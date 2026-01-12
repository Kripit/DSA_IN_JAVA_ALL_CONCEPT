package day_7_dsa_trees;

/* 
 * ============================================================================
 *         PROBLEM 1: MAXIMUM DEPTH OF BINARY TREE (EASY - Start Here!)
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * Given the root of a binary tree, return its maximum depth.
 * 
 * A binary tree's maximum depth is the number of nodes along the longest path
 * from the root node down to the farthest leaf node.
 * 
 * Example 1:
 * Input: root = [3,9,20,null,null,15,7]
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * Output: 3
 * 
 * Example 2:
 * Input: root = [1,null,2]
 *     1
 *      \
 *       2
 * Output: 2
 * 
 * Example 3:
 * Input: root = []
 * Output: 0
 * 
 * 
 * CONCEPT EXPLANATION:
 * --------------------
 * 
 * WHAT IS "DEPTH" OR "HEIGHT"?
 * ----------------------------
 * Think of a tree like a building:
 * - Depth/Height = Number of floors
 * - Root is ground floor (level 0 or level 1, depending on convention)
 * - Each level down adds 1 to depth
 * 
 * Visual:
 * ```
 *        1          ← Level 0 (or level 1)
 *       / \
 *      2   3        ← Level 1 (or level 2)
 *     / \
 *    4   5          ← Level 2 (or level 3)
 * 
 * Max Depth = 3 (counting from 1) or
 * Max Depth = 2 (counting from 0) + 1 = 3 levels total
 * ```
 * 
 * In this problem, we count NUMBER OF NODES in longest path.
 * 
 * 
 * KEY INSIGHT (This is important!):
 * ---------------------------------
 * The depth of a tree = 1 + max(depth of left subtree, depth of right subtree)
 * 
 * Why?
 * - Current node adds 1 to the depth
 * - Then we take the MAX of left and right (we want the LONGEST path)
 * 
 * Example:
 * ```
 *       1
 *      / \
 *     2   3
 *    /
 *   4
 * 
 * Depth of left subtree (rooted at 2) = 2  (nodes 2, 4)
 * Depth of right subtree (rooted at 3) = 1  (node 3)
 * Depth of whole tree = 1 + max(2, 1) = 3  (nodes 1, 2, 4)
 * ```
 * 
 * 
 * RECURSIVE THINKING:
 * -------------------
 * This is a PERFECT problem to understand recursion in trees!
 * 
 * Base Case:
 * - If node is null → depth is 0 (no nodes = no depth)
 * 
 * Recursive Case:
 * - Find depth of left subtree
 * - Find depth of right subtree
 * - Return 1 + max(left, right)
 * 
 * Trust the recursion! Don't try to trace the entire tree in your head.
 * Just think: "What do I do at THIS node?"
 * 
 * 
 * STEP-BY-STEP EXECUTION:
 * -----------------------
 * 
 * Tree:
 *       1
 *      / \
 *     2   3
 *    /
 *   4
 * 
 * Call: maxDepth(1)
 *   ├─ Call: maxDepth(2)
 *   │   ├─ Call: maxDepth(4)
 *   │   │   ├─ Call: maxDepth(null) → return 0
 *   │   │   └─ Call: maxDepth(null) → return 0
 *   │   │   └─ return 1 + max(0, 0) = 1
 *   │   ├─ Call: maxDepth(null) → return 0
 *   │   └─ return 1 + max(1, 0) = 2
 *   ├─ Call: maxDepth(3)
 *   │   ├─ Call: maxDepth(null) → return 0
 *   │   └─ Call: maxDepth(null) → return 0
 *   │   └─ return 1 + max(0, 0) = 1
 *   └─ return 1 + max(2, 1) = 3 ✓
 * 
 * 
 * REAL-WORLD ANALOGY:
 * -------------------
 * - **Company hierarchy:** How many levels from CEO to lowest employee?
 * - **File system:** How deep is the folder nesting?
 * - **Tournament bracket:** How many rounds until champion?
 * 
 * 
 * WHY THIS PROBLEM IS IMPORTANT:
 * ------------------------------
 * - Teaches basic tree recursion
 * - Foundation for balanced tree concepts
 * - Used in many other problems (diameter, balanced tree, etc.)
 * - Common in interviews as warm-up question
 */

// TreeNode is defined in TreeNode.java in same package

public class MaxDepthOfTreeEasy {
    
    // ================================================================
    //        APPROACH 1: RECURSIVE (Most Intuitive!)
    // ================================================================
    // Time: O(n) - visit every node once
    // Space: O(h) - recursion stack, h = height
    //        Best case: O(log n) for balanced tree
    //        Worst case: O(n) for skewed tree
    
    public int maxDepth(TreeNode root) {
        // Base case: empty tree has depth 0
        if (root == null) {
            return 0;
        }
        
        // Recursive case:
        // 1. Find depth of left subtree
        int leftDepth = maxDepth(root.left);
        
        // 2. Find depth of right subtree
        int rightDepth = maxDepth(root.right);
        
        // 3. Current depth = 1 + max of both sides
        return 1 + Math.max(leftDepth, rightDepth);
    }
    
    
    // ================================================================
    //        APPROACH 2: ITERATIVE (Level Order - BFS)
    // ================================================================
    // Time: O(n)
    // Space: O(w) - w is max width of tree (max nodes at any level)
    
    public int maxDepthIterative(TreeNode root) {
        if (root == null) return 0;
        
        // Use queue for level-order traversal
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.offer(root);
        int depth = 0;
        
        // Process level by level
        while (!queue.isEmpty()) {
            int levelSize = queue.size();  // Number of nodes at current level
            depth++;  // We're going one level deeper
            
            // Process all nodes at current level
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                
                // Add children for next level
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
        }
        
        return depth;
    }
    
    
    // ================================================================
    //        APPROACH 3: ITERATIVE (DFS with Stack)
    // ================================================================
    // Time: O(n)
    // Space: O(h)
    
    public int maxDepthDFS(TreeNode root) {
        if (root == null) return 0;
        
        // Stack stores (node, depth at that node)
        java.util.Stack<Pair> stack = new java.util.Stack<>();
        stack.push(new Pair(root, 1));
        int maxDepth = 0;
        
        while (!stack.isEmpty()) {
            Pair current = stack.pop();
            TreeNode node = current.node;
            int currentDepth = current.depth;
            
            // Update max depth
            maxDepth = Math.max(maxDepth, currentDepth);
            
            // Push children with increased depth
            if (node.left != null) {
                stack.push(new Pair(node.left, currentDepth + 1));
            }
            if (node.right != null) {
                stack.push(new Pair(node.right, currentDepth + 1));
            }
        }
        
        return maxDepth;
    }
    
    // Helper class for DFS approach
    static class Pair {
        TreeNode node;
        int depth;
        Pair(TreeNode node, int depth) {
            this.node = node;
            this.depth = depth;
        }
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
            
            // Left child
            if (i < arr.length && arr[i] != null) {
                node.left = new TreeNode(arr[i]);
                queue.offer(node.left);
            }
            i++;
            
            // Right child
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
            System.out.println(prefix + (isLeft ? "├── " : "└── ") + "null");
            return;
        }
        
        System.out.println(prefix + (isLeft ? "├── " : "└── ") + root.val);
        
        if (root.left != null || root.right != null) {
            if (root.left != null) {
                printTree(root.left, prefix + (isLeft ? "│   " : "    "), true);
            } else {
                System.out.println(prefix + (isLeft ? "│   " : "    ") + "├── null");
            }
            
            if (root.right != null) {
                printTree(root.right, prefix + (isLeft ? "│   " : "    "), false);
            } else {
                System.out.println(prefix + (isLeft ? "│   " : "    ") + "└── null");
            }
        }
    }
    
    
    // ================================================================
    //                         MAIN METHOD
    // ================================================================
    
    public static void main(String[] args) {
        MaxDepthOfTreeEasy solution = new MaxDepthOfTreeEasy();
        
        System.out.println("=".repeat(70));
        System.out.println("MAXIMUM DEPTH OF BINARY TREE - TREE RECURSION FOUNDATION");
        System.out.println("=".repeat(70));
        System.out.println();
        
        // Test Case 1: Normal binary tree
        System.out.println("TEST CASE 1: Normal Binary Tree");
        System.out.println("-".repeat(70));
        Integer[] arr1 = {3, 9, 20, null, null, 15, 7};
        TreeNode root1 = createTree(arr1);
        
        System.out.println("Tree Structure:");
        printTree(root1, "", false);
        System.out.println();
        
        System.out.println("Max Depth (Recursive): " + solution.maxDepth(root1));
        System.out.println("Max Depth (Iterative BFS): " + solution.maxDepthIterative(root1));
        System.out.println("Max Depth (Iterative DFS): " + solution.maxDepthDFS(root1));
        System.out.println("Expected: 3");
        System.out.println();
        
        // Test Case 2: Right skewed tree
        System.out.println("TEST CASE 2: Right Skewed Tree");
        System.out.println("-".repeat(70));
        Integer[] arr2 = {1, null, 2};
        TreeNode root2 = createTree(arr2);
        
        System.out.println("Tree Structure:");
        printTree(root2, "", false);
        System.out.println();
        
        System.out.println("Max Depth: " + solution.maxDepth(root2));
        System.out.println("Expected: 2");
        System.out.println();
        
        // Test Case 3: Empty tree
        System.out.println("TEST CASE 3: Empty Tree");
        System.out.println("-".repeat(70));
        TreeNode root3 = null;
        System.out.println("Tree: null");
        System.out.println("Max Depth: " + solution.maxDepth(root3));
        System.out.println("Expected: 0");
        System.out.println();
        
        // Test Case 4: Single node
        System.out.println("TEST CASE 4: Single Node");
        System.out.println("-".repeat(70));
        Integer[] arr4 = {1};
        TreeNode root4 = createTree(arr4);
        System.out.println("Tree: [1]");
        System.out.println("Max Depth: " + solution.maxDepth(root4));
        System.out.println("Expected: 1");
        System.out.println();
        
        // Test Case 5: Deep left-skewed tree
        System.out.println("TEST CASE 5: Left Skewed Tree (Deep)");
        System.out.println("-".repeat(70));
        Integer[] arr5 = {1, 2, null, 3, null, 4, null, 5};
        TreeNode root5 = createTree(arr5);
        
        System.out.println("Tree Structure:");
        printTree(root5, "", false);
        System.out.println();
        
        System.out.println("Max Depth: " + solution.maxDepth(root5));
        System.out.println("Expected: 5");
        System.out.println();
        
        // Test Case 6: Perfect binary tree
        System.out.println("TEST CASE 6: Perfect Binary Tree");
        System.out.println("-".repeat(70));
        Integer[] arr6 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root6 = createTree(arr6);
        
        System.out.println("Tree Structure:");
        printTree(root6, "", false);
        System.out.println();
        
        System.out.println("Max Depth: " + solution.maxDepth(root6));
        System.out.println("Expected: 3");
        System.out.println();
        
        // Complexity summary
        System.out.println("=".repeat(70));
        System.out.println("APPROACH COMPARISON");
        System.out.println("=".repeat(70));
        System.out.println("Recursive DFS:");
        System.out.println("  Time:  O(n) - visit all nodes");
        System.out.println("  Space: O(h) - recursion stack height");
        System.out.println("  ✓ MOST INTUITIVE - Recommended for interviews!");
        System.out.println();
        System.out.println("Iterative BFS (Level Order):");
        System.out.println("  Time:  O(n)");
        System.out.println("  Space: O(w) - queue width (max nodes at any level)");
        System.out.println("  Good for visualizing level-by-level");
        System.out.println();
        System.out.println("Iterative DFS (Stack):");
        System.out.println("  Time:  O(n)");
        System.out.println("  Space: O(h) - stack height");
        System.out.println("  Shows you can do DFS iteratively");
        System.out.println("=".repeat(70));
    }
}


/*
 * ============================================================================
 *                    DETAILED WALKTHROUGH & INSIGHTS
 * ============================================================================
 * 
 * UNDERSTANDING THE RECURSIVE SOLUTION:
 * --------------------------------------
 * 
 * Let's trace through step by step for this tree:
 *       1
 *      / \
 *     2   3
 *    /
 *   4
 * 
 * 
 * CALL STACK VISUALIZATION:
 * 
 * maxDepth(1):                                    [Main call]
 *   │
 *   ├─ leftDepth = maxDepth(2):                  [Left subtree]
 *   │   │
 *   │   ├─ leftDepth = maxDepth(4):              [Node 4]
 *   │   │   │
 *   │   │   ├─ leftDepth = maxDepth(null):       [No left child]
 *   │   │   │   └─ return 0
 *   │   │   │
 *   │   │   ├─ rightDepth = maxDepth(null):      [No right child]
 *   │   │   │   └─ return 0
 *   │   │   │
 *   │   │   └─ return 1 + max(0, 0) = 1
 *   │   │
 *   │   ├─ rightDepth = maxDepth(null):          [No right child of 2]
 *   │   │   └─ return 0
 *   │   │
 *   │   └─ return 1 + max(1, 0) = 2
 *   │
 *   ├─ rightDepth = maxDepth(3):                 [Right subtree]
 *   │   │
 *   │   ├─ leftDepth = maxDepth(null):           [No left child]
 *   │   │   └─ return 0
 *   │   │
 *   │   ├─ rightDepth = maxDepth(null):          [No right child]
 *   │   │   └─ return 0
 *   │   │
 *   │   └─ return 1 + max(0, 0) = 1
 *   │
 *   └─ return 1 + max(2, 1) = 3 ✓
 * 
 * Final Answer: 3
 * 
 * 
 * WHY RECURSIVE SOLUTION IS BEAUTIFUL:
 * -------------------------------------
 * 
 * 1. **Simplicity:**
 *    - Only 5 lines of code!
 *    - Easy to understand and remember
 * 
 * 2. **Natural fit:**
 *    - Trees are recursive structures
 *    - Each subtree is also a tree
 *    - Solution mirrors the structure
 * 
 * 3. **Trust the recursion:**
 *    - Don't try to trace entire tree
 *    - Just think: "What happens at THIS node?"
 *    - Trust that recursion handles subtrees
 * 
 * 
 * ITERATIVE BFS EXPLAINED:
 * ------------------------
 * 
 * Tree:
 *       1
 *      / \
 *     2   3
 *    /
 *   4
 * 
 * Level 0: queue = [1], depth = 0
 * - Process level, depth becomes 1
 * - Add children: queue = [2, 3]
 * 
 * Level 1: queue = [2, 3], depth = 1
 * - Process level, depth becomes 2
 * - Add children: queue = [4]
 * 
 * Level 2: queue = [4], depth = 2
 * - Process level, depth becomes 3
 * - No children: queue = []
 * 
 * Loop ends, return depth = 3 ✓
 * 
 * 
 * ITERATIVE DFS EXPLAINED:
 * ------------------------
 * 
 * Same tree. Stack stores (node, depth):
 * 
 * Initial: stack = [(1, 1)], maxDepth = 0
 * 
 * Iteration 1:
 * - Pop (1, 1), maxDepth = 1
 * - Push (2, 2) and (3, 2)
 * - stack = [(2, 2), (3, 2)]
 * 
 * Iteration 2:
 * - Pop (3, 2), maxDepth = 2
 * - No children
 * - stack = [(2, 2)]
 * 
 * Iteration 3:
 * - Pop (2, 2), maxDepth = 2
 * - Push (4, 3)
 * - stack = [(4, 3)]
 * 
 * Iteration 4:
 * - Pop (4, 3), maxDepth = 3
 * - No children
 * - stack = []
 * 
 * Return maxDepth = 3 ✓
 * 
 * 
 * EDGE CASES TO REMEMBER:
 * -----------------------
 * 
 * 1. **Empty tree (root = null):**
 *    - Return 0 immediately
 *    - No nodes = no depth
 * 
 * 2. **Single node:**
 *    - Both children are null
 *    - Return 1 + max(0, 0) = 1 ✓
 * 
 * 3. **Skewed tree (all left or all right):**
 *    - Depth = number of nodes
 *    - Worst case for space complexity
 * 
 * 4. **Perfect binary tree:**
 *    - All levels full
 *    - Depth = log₂(n+1) rounded up
 * 
 * 
 * COMMON MISTAKES I MADE:
 * -----------------------
 * 
 * 1. **Forgetting null check:**
 *    BAD:  public int maxDepth(TreeNode root) {
 *              int left = maxDepth(root.left);  // CRASH if root is null!
 *          }
 *    
 *    GOOD: public int maxDepth(TreeNode root) {
 *              if (root == null) return 0;     // Check first!
 *              ...
 *          }
 * 
 * 2. **Returning max instead of 1 + max:**
 *    BAD:  return Math.max(leftDepth, rightDepth);
 *    GOOD: return 1 + Math.max(leftDepth, rightDepth);
 *    Why: Need to count current node too!
 * 
 * 3. **Not understanding what to return:**
 *    - Null node: return 0 (no nodes)
 *    - Leaf node: return 1 (one node)
 *    - Internal node: return 1 + max(children)
 * 
 * 4. **Confusing height vs depth:**
 *    - For this problem, they're the same!
 *    - Some define height from bottom, depth from top
 *    - Here, we count nodes in longest path
 * 
 * 
 * INTERVIEW VARIATIONS:
 * ---------------------
 * 
 * Q1: "Find minimum depth instead?"
 * A: Similar, but use min instead of max
 *    Watch out: if one child is null, don't use that side!
 * 
 * Q2: "Return the path to deepest node?"
 * A: Keep track of path while recursing
 *    When depth increases, update path
 * 
 * Q3: "What if it's not a binary tree (n children)?"
 * A: Take max of all children instead of just left/right
 * 
 * Q4: "Find diameter of tree?"
 * A: At each node, calculate left_depth + right_depth
 *    Keep track of maximum sum
 * 
 * 
 * SPACE COMPLEXITY DEEP DIVE:
 * ----------------------------
 * 
 * Recursive: O(h) where h is height
 * 
 * Why? Call stack!
 * - Each recursive call adds to stack
 * - Max stack depth = height of tree
 * 
 * Best case (balanced tree):
 * - Height = log₂(n)
 * - Space = O(log n)
 * 
 * Worst case (skewed tree):
 * - Height = n (all nodes in one line)
 * - Space = O(n)
 * 
 * Example worst case:
 *     1
 *      \
 *       2
 *        \
 *         3
 *          \
 *           4
 * Height = 4 = n, so space is O(n)
 * 
 * 
 * WHEN TO USE EACH APPROACH:
 * --------------------------
 * 
 * RECURSIVE:
 * ✓ Interviews (most intuitive)
 * ✓ Clean code needed
 * ✓ Tree is reasonably balanced
 * 
 * ITERATIVE BFS:
 * ✓ Need to process level by level
 * ✓ Want to avoid recursion limits
 * ✓ Visualizing levels is important
 * 
 * ITERATIVE DFS:
 * ✓ Showing deep understanding
 * ✓ Language without good recursion support
 * ✓ Want more control over traversal
 * 
 * 
 * INTERVIEW TIPS:
 * ---------------
 * 
 * 1. **Start with recursive:**
 *    "I'll use recursion as it's most natural for trees"
 * 
 * 2. **Explain the idea:**
 *    "Depth = 1 + max of left and right subtrees"
 * 
 * 3. **Mention base case:**
 *    "Null node has depth 0"
 * 
 * 4. **State complexity:**
 *    "O(n) time as we visit all nodes"
 *    "O(h) space for recursion stack"
 * 
 * 5. **If asked for iterative:**
 *    "I can do BFS with queue or DFS with stack"
 * 
 * 
 * REMEMBER:
 * ---------
 * ✓ Always check for null first!
 * ✓ Depth = 1 + max(left, right)
 * ✓ Base case: null = 0
 * ✓ O(n) time, O(h) space
 * ✓ Trust the recursion!
 * 
 * This problem is THE foundation for tree recursion.
 * Master this pattern - you'll use it EVERYWHERE! 🌳
 */
