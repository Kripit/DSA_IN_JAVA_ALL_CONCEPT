package day_7_dsa_trees;

/* 
 * ============================================================================
 *         PROBLEM 2: INVERT BINARY TREE (EASY - Famous Google Question!)
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * Given the root of a binary tree, invert the tree, and return its root.
 * 
 * "Invert" means swap the left and right children of all nodes.
 * This is also called "mirroring" the tree.
 * 
 * Example 1:
 * Input: root = [4,2,7,1,3,6,9]
 * 
 *      4               4
 *    /   \           /   \
 *   2     7    →    7     2
 *  / \   / \       / \   / \
 * 1   3 6   9     9   6 3   1
 * 
 * Output: [4,7,2,9,6,3,1]
 * 
 * Example 2:
 * Input: root = [2,1,3]
 * 
 *    2         2
 *   / \   →   / \
 *  1   3     3   1
 * 
 * Output: [2,3,1]
 * 
 * Example 3:
 * Input: root = []
 * Output: []
 * 
 * 
 * CONCEPT EXPLANATION:
 * --------------------
 * 
 * WHAT DOES "INVERT" MEAN?
 * -------------------------
 * Think of looking at a tree in a mirror:
 * - Left becomes right
 * - Right becomes left
 * - Do this for EVERY node in the tree
 * 
 * Visual:
 * ```
 * Original:           Mirror (Inverted):
 *     1                    1
 *    / \                  / \
 *   2   3                3   2
 *  /                          \
 * 4                            4
 * 
 * Notice: 4 moved from left side to right side!
 * ```
 * 
 * 
 * KEY INSIGHT:
 * ------------
 * At each node:
 * 1. Swap left and right children
 * 2. Recursively invert left subtree
 * 3. Recursively invert right subtree
 * 
 * That's it! Super simple once you see it.
 * 
 * Example:
 * ```
 *     1
 *    / \
 *   2   3
 * 
 * Step 1: At node 1, swap children
 *     1
 *    / \
 *   3   2    (swapped!)
 * 
 * Step 2: Invert left (node 3) - no children, done
 * Step 3: Invert right (node 2) - no children, done
 * 
 * Result:
 *     1
 *    / \
 *   3   2
 * ```
 * 
 * 
 * RECURSIVE THINKING:
 * -------------------
 * 
 * Base Case:
 * - If node is null → already inverted! (nothing to swap)
 * - If node is leaf → already inverted! (no children to swap)
 * 
 * Recursive Case:
 * - Invert left subtree (recursion handles it)
 * - Invert right subtree (recursion handles it)
 * - Swap left and right at current node
 * - Return current node
 * 
 * Trust me, this pattern works beautifully!
 * 
 * 
 * STEP-BY-STEP EXECUTION:
 * -----------------------
 * 
 * Tree:
 *       4
 *      / \
 *     2   7
 *    / \
 *   1   3
 * 
 * Call: invertTree(4)
 *   ├─ Invert left: invertTree(2)
 *   │   ├─ Invert left: invertTree(1)
 *   │   │   └─ No children, return 1
 *   │   ├─ Invert right: invertTree(3)
 *   │   │   └─ No children, return 3
 *   │   ├─ Swap: 2's children (1,3) → (3,1)
 *   │   └─ Return 2
 *   │
 *   ├─ Invert right: invertTree(7)
 *   │   ├─ No children
 *   │   └─ Return 7
 *   │
 *   ├─ Swap: 4's children (2,7) → (7,2)
 *   └─ Return 4
 * 
 * Result:
 *       4
 *      / \
 *     7   2
 *        / \
 *       3   1
 * 
 * Perfect! Tree is now inverted.
 * 
 * 
 * REAL-WORLD ANALOGY:
 * -------------------
 * - **Mirror image:** Your reflection in mirror
 * - **Photo flip:** Flipping a photo horizontally
 * - **Text reversal:** Right-to-left languages
 * - **Game map:** Flipping a game level
 * 
 * 
 * THE FAMOUS STORY:
 * -----------------
 * This problem became famous when a developer tweeted:
 * 
 * "Google: 90% of our engineers use the software you wrote (Homebrew),
 *  but you can't invert a binary tree on a whiteboard so fuck off."
 * 
 * It sparked huge debate: Are whiteboard coding interviews fair?
 * 
 * My take: This problem is actually GREAT for interviews because:
 * - Simple to understand
 * - Multiple approaches exist
 * - Tests recursion understanding
 * - Shows how you think under pressure
 * 
 * 
 * WHY THIS PROBLEM IS IMPORTANT:
 * ------------------------------
 * - Fundamental tree manipulation
 * - Teaches swapping in trees
 * - Practice with tree recursion
 * - Common in interviews (thanks to the meme!)
 * - Foundation for symmetric tree, same tree, etc.
 */

// TreeNode is defined in TreeNode.java in same package

public class InvertBinaryTreeEasy {
    
    // ================================================================
    //        APPROACH 1: RECURSIVE (Most Elegant!)
    // ================================================================
    // Time: O(n) - visit every node once
    // Space: O(h) - recursion stack, h = height
    //        Best: O(log n) for balanced
    //        Worst: O(n) for skewed
    
    public TreeNode invertTree(TreeNode root) {
        // Base case: null or leaf
        if (root == null) {
            return null;
        }
        
        // Recursive case:
        // 1. Invert left subtree
        TreeNode left = invertTree(root.left);
        
        // 2. Invert right subtree
        TreeNode right = invertTree(root.right);
        
        // 3. Swap them!
        root.left = right;
        root.right = left;
        
        // 4. Return current node
        return root;
    }
    
    
    // ================================================================
    //        APPROACH 2: RECURSIVE (One-Liner Style)
    // ================================================================
    // Same logic, more compact code
    
    public TreeNode invertTreeCompact(TreeNode root) {
        if (root == null) return null;
        
        // Swap and recurse in one line!
        TreeNode temp = root.left;
        root.left = invertTree(root.right);
        root.right = invertTree(temp);
        
        return root;
    }
    
    
    // ================================================================
    //        APPROACH 3: ITERATIVE (BFS - Level Order)
    // ================================================================
    // Time: O(n)
    // Space: O(w) - w is max width
    
    public TreeNode invertTreeIterative(TreeNode root) {
        if (root == null) return null;
        
        // Use queue for level-order traversal
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            
            // Swap children
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            
            // Add children to queue
            if (node.left != null) queue.offer(node.left);
            if (node.right != null) queue.offer(node.right);
        }
        
        return root;
    }
    
    
    // ================================================================
    //        APPROACH 4: ITERATIVE (DFS - Stack)
    // ================================================================
    // Time: O(n)
    // Space: O(h)
    
    public TreeNode invertTreeDFS(TreeNode root) {
        if (root == null) return null;
        
        java.util.Stack<TreeNode> stack = new java.util.Stack<>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            
            // Swap children
            TreeNode temp = node.left;
            node.left = node.right;
            node.right = temp;
            
            // Push children to stack
            if (node.left != null) stack.push(node.left);
            if (node.right != null) stack.push(node.right);
        }
        
        return root;
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
    
    // Convert tree to array (level-order)
    public static Integer[] treeToArray(TreeNode root) {
        if (root == null) return new Integer[0];
        
        java.util.List<Integer> result = new java.util.ArrayList<>();
        java.util.Queue<TreeNode> queue = new java.util.LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            
            if (node != null) {
                result.add(node.val);
                queue.offer(node.left);
                queue.offer(node.right);
            } else {
                result.add(null);
            }
        }
        
        // Remove trailing nulls
        while (!result.isEmpty() && result.get(result.size() - 1) == null) {
            result.remove(result.size() - 1);
        }
        
        return result.toArray(new Integer[0]);
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
    
    // Print array
    public static void printArray(Integer[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(",");
        }
        System.out.println("]");
    }
    
    
    // ================================================================
    //                         MAIN METHOD
    // ================================================================
    
    public static void main(String[] args) {
        InvertBinaryTreeEasy solution = new InvertBinaryTreeEasy();
        
        System.out.println("=".repeat(70));
        System.out.println("INVERT BINARY TREE - THE FAMOUS GOOGLE PROBLEM");
        System.out.println("=".repeat(70));
        System.out.println();
        
        // Test Case 1: Normal binary tree
        System.out.println("TEST CASE 1: Normal Binary Tree");
        System.out.println("-".repeat(70));
        Integer[] arr1 = {4, 2, 7, 1, 3, 6, 9};
        TreeNode root1 = createTree(arr1);
        
        System.out.println("BEFORE Inversion:");
        printTree(root1, "", false);
        System.out.print("Array: ");
        printArray(treeToArray(root1));
        System.out.println();
        
        TreeNode inverted1 = solution.invertTree(root1);
        
        System.out.println("AFTER Inversion:");
        printTree(inverted1, "", false);
        System.out.print("Array: ");
        printArray(treeToArray(inverted1));
        System.out.println("Expected: [4,7,2,9,6,3,1]");
        System.out.println();
        
        // Test Case 2: Small tree
        System.out.println("TEST CASE 2: Small Tree");
        System.out.println("-".repeat(70));
        Integer[] arr2 = {2, 1, 3};
        TreeNode root2 = createTree(arr2);
        
        System.out.println("BEFORE:");
        printTree(root2, "", false);
        
        TreeNode inverted2 = solution.invertTreeIterative(root2);
        
        System.out.println("\nAFTER:");
        printTree(inverted2, "", false);
        System.out.print("Array: ");
        printArray(treeToArray(inverted2));
        System.out.println("Expected: [2,3,1]");
        System.out.println();
        
        // Test Case 3: Empty tree
        System.out.println("TEST CASE 3: Empty Tree");
        System.out.println("-".repeat(70));
        TreeNode root3 = null;
        System.out.println("Input: null");
        TreeNode inverted3 = solution.invertTree(root3);
        System.out.println("Output: " + (inverted3 == null ? "null" : "not null"));
        System.out.println("Expected: null");
        System.out.println();
        
        // Test Case 4: Single node
        System.out.println("TEST CASE 4: Single Node");
        System.out.println("-".repeat(70));
        Integer[] arr4 = {1};
        TreeNode root4 = createTree(arr4);
        System.out.println("BEFORE: [1]");
        TreeNode inverted4 = solution.invertTreeDFS(root4);
        System.out.print("AFTER: ");
        printArray(treeToArray(inverted4));
        System.out.println("Expected: [1]");
        System.out.println();
        
        // Test Case 5: Left skewed tree
        System.out.println("TEST CASE 5: Left Skewed Tree");
        System.out.println("-".repeat(70));
        Integer[] arr5 = {1, 2, null, 3, null};
        TreeNode root5 = createTree(arr5);
        
        System.out.println("BEFORE:");
        printTree(root5, "", false);
        
        TreeNode inverted5 = solution.invertTree(root5);
        
        System.out.println("\nAFTER (becomes right skewed!):");
        printTree(inverted5, "", false);
        System.out.println();
        
        // Test Case 6: Perfect binary tree
        System.out.println("TEST CASE 6: Perfect Binary Tree");
        System.out.println("-".repeat(70));
        Integer[] arr6 = {1, 2, 3, 4, 5, 6, 7};
        TreeNode root6 = createTree(arr6);
        
        System.out.println("BEFORE:");
        printTree(root6, "", false);
        
        TreeNode inverted6 = solution.invertTreeCompact(root6);
        
        System.out.println("\nAFTER:");
        printTree(inverted6, "", false);
        System.out.println();
        
        // Complexity summary
        System.out.println("=".repeat(70));
        System.out.println("APPROACH COMPARISON");
        System.out.println("=".repeat(70));
        System.out.println("Recursive:");
        System.out.println("  Time:  O(n) - visit all nodes");
        System.out.println("  Space: O(h) - recursion stack");
        System.out.println("  ✓ CLEANEST CODE - Best for interviews!");
        System.out.println();
        System.out.println("Iterative BFS:");
        System.out.println("  Time:  O(n)");
        System.out.println("  Space: O(w) - queue width");
        System.out.println("  Good for level-by-level swapping");
        System.out.println();
        System.out.println("Iterative DFS:");
        System.out.println("  Time:  O(n)");
        System.out.println("  Space: O(h) - stack");
        System.out.println("  Alternative to recursion");
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
 * Let's trace STEP BY STEP for this tree:
 *       1
 *      / \
 *     2   3
 *    /
 *   4
 * 
 * 
 * VISUALIZATION OF RECURSION:
 * 
 * invertTree(1):                              [Start at root]
 *   │
 *   ├─ left = invertTree(2):                 [Process left subtree]
 *   │   │
 *   │   ├─ left = invertTree(4):             [Go deeper]
 *   │   │   ├─ left = invertTree(null) → null
 *   │   │   ├─ right = invertTree(null) → null
 *   │   │   ├─ Swap: null ↔ null (no change)
 *   │   │   └─ Return 4
 *   │   │
 *   │   ├─ right = invertTree(null) → null
 *   │   ├─ Swap: 2's children: (4,null) → (null,4)
 *   │   └─ Return 2
 *   │       Result:
 *   │         2
 *   │          \
 *   │           4  (moved from left to right!)
 *   │
 *   ├─ right = invertTree(3):                [Process right subtree]
 *   │   ├─ left = invertTree(null) → null
 *   │   ├─ right = invertTree(null) → null
 *   │   ├─ Swap: null ↔ null
 *   │   └─ Return 3 (no children, stays same)
 *   │
 *   ├─ Swap: 1's children: (2,3) → (3,2)
 *   └─ Return 1
 *       Final Result:
 *         1
 *        / \
 *       3   2
 *            \
 *             4
 * 
 * 
 * THE BEAUTY OF SWAPPING:
 * -----------------------
 * 
 * Why swap AFTER recursion?
 * 
 * When we do:
 *   TreeNode left = invertTree(root.left);
 *   TreeNode right = invertTree(root.right);
 *   root.left = right;
 *   root.right = left;
 * 
 * We're saying:
 * 1. First, invert left subtree completely
 * 2. Then, invert right subtree completely
 * 3. Finally, swap the inverted subtrees
 * 
 * This ensures the ENTIRE subtree is inverted before swapping!
 * 
 * Example:
 * ```
 *     1
 *    / \
 *   2   3
 *  /
 * 4
 * 
 * After inverting left subtree (rooted at 2):
 *     1
 *    / \
 *   2   3     (2's subtree now has 4 on right)
 *    \
 *     4
 * 
 * After inverting right subtree (3 has no children, stays same)
 * 
 * After swapping at root:
 *     1
 *    / \
 *   3   2     (swapped!)
 *        \
 *         4
 * ```
 * 
 * 
 * WHY ITERATIVE WORKS:
 * --------------------
 * 
 * BFS Approach:
 * - Process level by level
 * - At each node, swap children
 * - Add children to queue for next level
 * 
 * Tree:
 *       1
 *      / \
 *     2   3
 *    /
 *   4
 * 
 * Level 0: queue = [1]
 * - Process 1: swap (2,3) → (3,2)
 * - Add 3 and 2 to queue
 * - Tree now: 1 → 3, 2
 * 
 * Level 1: queue = [3, 2]
 * - Process 3: no children
 * - Process 2: swap (4,null) → (null,4)
 * - Add 4 to queue
 * - Tree now: 2 → null, 4
 * 
 * Level 2: queue = [4]
 * - Process 4: no children
 * 
 * Done! Tree inverted.
 * 
 * 
 * COMMON MISTAKES I MADE:
 * -----------------------
 * 
 * 1. **Swapping before recursion:**
 *    BAD:
 *    root.left = root.right;
 *    root.right = root.left;  // WRONG! Both point to same node!
 *    invertTree(root.left);
 *    invertTree(root.right);
 *    
 *    GOOD:
 *    TreeNode temp = root.left;  // Save it first!
 *    root.left = root.right;
 *    root.right = temp;
 * 
 * 2. **Forgetting to return:**
 *    BAD:  public void invertTree(TreeNode root) {
 *              // Modifies tree but doesn't return!
 *          }
 *    
 *    GOOD: public TreeNode invertTree(TreeNode root) {
 *              ...
 *              return root;  // Return the root!
 *          }
 * 
 * 3. **Not handling null:**
 *    BAD:  public TreeNode invertTree(TreeNode root) {
 *              TreeNode left = invertTree(root.left);  // Crash if root is null!
 *          }
 *    
 *    GOOD: public TreeNode invertTree(TreeNode root) {
 *              if (root == null) return null;  // Check first!
 *              ...
 *          }
 * 
 * 4. **Overthinking it:**
 *    I spent way too long trying to be clever!
 *    The simple swap + recurse approach is the best.
 * 
 * 
 * EDGE CASES TO HANDLE:
 * ---------------------
 * 
 * 1. **Empty tree (null):**
 *    Input: null
 *    Output: null
 *    Just return null immediately
 * 
 * 2. **Single node:**
 *    Input: [1]
 *    Output: [1]
 *    No children to swap, return as is
 * 
 * 3. **Skewed tree:**
 *    Input: [1,2,null,3,null]
 *      1
 *     /
 *    2
 *   /
 *  3
 *    
 *    Output: [1,null,2,null,3]
 *      1
 *       \
 *        2
 *         \
 *          3
 *    Left-skewed becomes right-skewed!
 * 
 * 4. **Perfect tree:**
 *    Symmetric tree stays symmetric (just values move)
 * 
 * 
 * INTERVIEW VARIATIONS:
 * ---------------------
 * 
 * Q1: "Can you do it without recursion?"
 * A: Yes! Use BFS (queue) or DFS (stack)
 *    Show the iterative BFS approach
 * 
 * Q2: "What if you can't modify the original tree?"
 * A: Create new nodes as you go
 *    TreeNode newNode = new TreeNode(root.val);
 *    newNode.left = invertTree(root.right);  // Swap while creating
 *    newNode.right = invertTree(root.left);
 *    return newNode;
 * 
 * Q3: "Is the tree symmetric after inverting?"
 * A: Only if original was symmetric!
 *    Inverting doesn't create symmetry
 * 
 * Q4: "What about n-ary trees?"
 * A: Reverse the children array
 *    Collections.reverse(node.children);
 *    Then recurse on each child
 * 
 * 
 * SPACE COMPLEXITY ANALYSIS:
 * --------------------------
 * 
 * Recursive: O(h)
 * - Call stack depth = height
 * - Balanced: O(log n)
 * - Skewed: O(n)
 * 
 * Iterative BFS: O(w)
 * - Queue holds one level at a time
 * - w = max width of tree
 * - Perfect tree: w = n/2 (bottom level)
 * - Skewed tree: w = 1
 * 
 * Iterative DFS: O(h)
 * - Stack depth = height (like recursion)
 * 
 * Example (perfect tree):
 *       1
 *      / \
 *     2   3
 *    / \ / \
 *   4 5 6 7
 * 
 * Recursive/DFS: O(log 7) = O(3) = O(height)
 * BFS: O(4) = O(width at bottom)
 * 
 * 
 * WHEN TO USE EACH APPROACH:
 * --------------------------
 * 
 * RECURSIVE:
 * ✓ Interviews (most elegant)
 * ✓ Natural for trees
 * ✓ Code is shortest
 * 
 * ITERATIVE BFS:
 * ✓ Avoiding recursion limits
 * ✓ Want to see levels clearly
 * ✓ Debugging step by step
 * 
 * ITERATIVE DFS:
 * ✓ Alternative to recursion
 * ✓ More control over traversal
 * 
 * 
 * THE GOOGLE INTERVIEW STORY:
 * ---------------------------
 * 
 * Context: Max Howell (creator of Homebrew) tweeted:
 * 
 * "Google: 90% of our engineers use the software you wrote,
 *  but you can't invert a binary tree on a whiteboard so fuck off."
 * 
 * This went viral! People debated:
 * - Are algorithm problems too academic?
 * - Should we test practical skills instead?
 * - Is whiteboard coding realistic?
 * 
 * My opinion:
 * - This problem IS useful
 * - Tests: recursion, tree thinking, problem solving
 * - BUT: It shouldn't be the ONLY thing tested
 * - Real interviews should mix: algorithms + system design + practical coding
 * 
 * Lesson: Know this problem, but also know how to:
 * - Design systems
 * - Write clean code
 * - Debug effectively
 * - Communicate well
 * 
 * 
 * INTERVIEW TIPS:
 * ---------------
 * 
 * 1. **Start with explanation:**
 *    "I need to swap left and right children at every node"
 * 
 * 2. **Draw it out:**
 *    Show before and after on whiteboard
 *    Interviewer loves visuals!
 * 
 * 3. **Mention the meme:**
 *    "Oh, the famous Google problem!"
 *    Shows you're aware of interview culture
 * 
 * 4. **Code recursive first:**
 *    It's the cleanest solution
 * 
 * 5. **Offer iterative:**
 *    "Would you like to see the BFS approach?"
 *    Shows versatility
 * 
 * 6. **State complexity:**
 *    "O(n) time, O(h) space for recursion"
 * 
 * 7. **Test edge cases:**
 *    Mention null, single node, skewed tree
 * 
 * 
 * FOLLOW-UP PROBLEMS:
 * -------------------
 * 
 * Once you master this:
 * - Symmetric Tree (check if tree is its own mirror)
 * - Same Tree (compare two trees)
 * - Mirror Reflection (specific path problems)
 * - Serialize/Deserialize (convert tree to string)
 * 
 * 
 * REMEMBER:
 * ---------
 * ✓ Swap left and right at each node
 * ✓ Recurse on both subtrees
 * ✓ Base case: null returns null
 * ✓ O(n) time, O(h) space
 * ✓ Don't forget to use temp variable when swapping!
 * 
 * This problem is simpler than it looks.
 * Just swap, recurse, trust the process! 🔄
 * 
 * And hey, now you can pass that Google interview 😉
 */
