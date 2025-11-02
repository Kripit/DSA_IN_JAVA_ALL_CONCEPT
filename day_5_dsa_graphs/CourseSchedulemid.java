/*
 * ============================================================================
 *              PROBLEM 2: COURSE SCHEDULE (MEDIUM - Topological Sort)
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * There are total 'numCourses' courses labeled from 0 to numCourses-1.
 * You are given an array 'prerequisites' where prerequisites[i] = [a, b]
 * means you MUST take course b BEFORE taking course a.
 * 
 * Return TRUE if you can finish all courses, FALSE otherwise.
 * 
 * Example 1:
 * Input: numCourses = 2, prerequisites = [[1,0]]
 * Output: true
 * Explanation: Total 2 courses. To take course 1, you must take course 0 first.
 * So: 0 → 1 (possible!)
 * 
 * Example 2:
 * Input: numCourses = 2, prerequisites = [[1,0], [0,1]]
 * Output: false
 * Explanation: To take 0, you need 1. To take 1, you need 0.
 * This is a CYCLE! Impossible!
 * 
 * 
 * CONCEPT EXPLANATION:
 * --------------------
 * 
 * WHAT IS THIS PROBLEM REALLY ASKING?
 * 
 * This is a DIRECTED GRAPH problem:
 * - Each course = NODE/VERTEX
 * - Each prerequisite = DIRECTED EDGE
 * - [1,0] means: 0 → 1 (edge from 0 to 1)
 * 
 * Question becomes: Can we order courses such that prerequisites come first?
 * This is called TOPOLOGICAL SORT!
 * 
 * WHEN IS IT IMPOSSIBLE?
 * When there's a CYCLE in the graph!
 * 
 * Example of cycle:
 * Course 0 needs Course 1
 * Course 1 needs Course 2
 * Course 2 needs Course 0
 * 0 → 1 → 2 → 0 (CYCLE!)
 * 
 * 
 * TOPOLOGICAL SORT EXPLAINED:
 * ---------------------------
 * 
 * Topological Sort = Linear ordering where all edges go left to right
 * 
 * Example:
 * Graph: 0 → 1 → 3
 *        ↓
 *        2
 * 
 * Valid orderings:
 * - 0, 1, 2, 3
 * - 0, 2, 1, 3
 * 
 * Invalid: 1, 0, 2, 3 (edge 0→1 goes right to left!)
 * 
 * 
 * TWO APPROACHES:
 * ---------------
 * 
 * 1. DFS + Cycle Detection
 *    - Use DFS with 3 states: Unvisited, Visiting, Visited
 *    - If we revisit a "Visiting" node → Cycle!
 * 
 * 2. BFS + Kahn's Algorithm (In-degree based)
 *    - Count in-degree (incoming edges) for each node
 *    - Start with nodes having in-degree 0
 *    - Process and reduce in-degrees
 *    - If can't process all nodes → Cycle!
 * 
 * 
 * VISUALIZATION:
 * --------------
 * 
 * Example: [[1,0], [2,0], [3,1], [3,2]]
 * 
 * Graph:
 *     0
 *    / \
 *   1   2
 *    \ /
 *     3
 * 
 * In-degrees:
 * 0: 0 (no prerequisites)
 * 1: 1 (needs 0)
 * 2: 1 (needs 0)
 * 3: 2 (needs 1 and 2)
 * 
 * Process:
 * Start with 0 (in-degree = 0)
 * Remove 0, reduce in-degrees: 1 and 2 now have in-degree 0
 * Process 1 and 2, reduce in-degree of 3 to 0
 * Process 3
 * All processed → TRUE!
 */


import java.util.*;

public class CourseSchedulemid {
    // ========================================================================
    //                  APPROACH 1: DFS + CYCLE DETECTION
    // ========================================================================
    // Using DFS with 3 states to detect cycles
    // Time: O(V + E) where V = courses, E = prerequisites
    // Space: O(V + E) for graph + O(V) for recursion

    public static boolean canFinishDFS(int numCourses, int[][] prerequisites) {
        // STEP 1: Build adjacency list (graph representation)
        // ArrayList<Integer>[] is array of ArrayLists
        // Each index represents a course
        // The ArrayList at that index contains all courses that depend on it

        ArrayList<Integer>[] graph = new ArrayList[numCourses];

        // Initialize each ArrayList
        // Why? Java arrays of objects are null by default
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<>();
            // Now graph[i] is an empty ArrayList, not null
        }  
        
        for (int[] prereq: prerequisites){
            int course = prereq[0];
            int prerequisite = prereq[1];

            // Add edge: prerequisite → course
            graph[prerequisite].add(course);
        }
        System.out.println("Graph built (Adjacency List):");
        for (int i = 0; i < numCourses; i++) {
            System.out.println("Course " + i + " → " + graph[i]);
        }
        System.out.println();

        // STEP 2: Create state array for cycle detection
        // 0 = UNVISITED (white)
        // 1 = VISITING (gray) - currently in recursion stack
        // 2 = VISITED (black) - completely processed
        
        int[] state = new int[numCourses];
        // All initially 0 (unvisited)
        
        
        // STEP 3: DFS from each unvisited node
        for (int course = 0; course < numCourses; course++) {
            if (state[course] == 0) {  // If unvisited
                // Start DFS from this course
                if (hasCycleDFS(course, graph, state)) {
                    // Cycle detected!
                    return false;
                }
            }
        }
        
        // No cycle found, all courses can be completed!
        return true;
    }
   // DFS Helper to detect cycle
    // Returns true if cycle found
    private static boolean hasCycleDFS(int course, ArrayList<Integer>[] graph, int[] state) {
        
        // Mark current course as VISITING (in recursion stack)
        state[course] = 1;
        
        System.out.println("Visiting course " + course);
        
        // Explore all neighbors (courses that depend on current course)
        for (int neighbor : graph[course]) {   

            // Case 1 : neighbor unvisited
            if(state[neighbor]==0){
                if(hasCycleDFS(neighbor , graph, state)){
                    return true;
                }
            }
            // Case 2: Neighbor is VISITING (in current recursion path)
            else if(state[neighbor] == 1){
                // we reached a node thats already in recursion stacl
                // this means we found a cycle
                System.out.println("CYCLE DETECTED: " + course + " → " + neighbor);
                return true;               
            }
            // Case 3: Neighbor is VISITED (2)
            // Already processed, no need to visit again
        }
        
        // Mark current course as VISITED (completely processed)
        state[course] = 2;
        System.out.println("Course " + course + " fully processed");
        
        return false;  // No cycle found from this course
    }
    
    
    // ========================================================================
    //              APPROACH 2: BFS + KAHN'S ALGORITHM (IN-DEGREE)
    // ========================================================================
    // Using BFS with in-degree counting
    // Time: O(V + E)
    // Space: O(V + E)
    
    public static boolean canFinishBFS(int numCourses, int[][] prerequisites) {
        
        // STEP 1: Build adjacency list + calculate in-degrees
        
        ArrayList<Integer>[] graph = new ArrayList[numCourses];
        
        // in-degree: number of incoming edges to each node
        // in-degree[i] = how many prerequisites course i has
        int[] inDegree = new int[numCourses];
        
        // Initialize graph
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new ArrayList<>();
        }
        
        // Build graph and count in-degrees
        for (int[] prereq : prerequisites) {
            int course = prereq[0];
            int prerequisite = prereq[1];
            
            // Edge: prerequisite → course
            graph[prerequisite].add(course);
            
            // Increment in-degree of course
            // (one more prerequisite)
            inDegree[course]++;
        }
        
        System.out.println("In-degrees:");
        for (int i = 0; i < numCourses; i++) {
            System.out.println("Course " + i + ": " + inDegree[i] + " prerequisites");
        }
        System.out.println();
        
        
        // STEP 2: Initialize queue with all courses having in-degree 0
        // These courses have no prerequisites, can be taken first
        
        Queue<Integer> queue = new LinkedList<>();
        
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);  // Add to queue
                System.out.println("Course " + i + " has no prerequisites, adding to queue");
            }
        }
        
        System.out.println();
        
        
        // STEP 3: Process courses using BFS
        
        int processedCourses = 0;  // Count of courses we can complete
        
        while (!queue.isEmpty()) {
            // Take a course from queue
            int course = queue.poll();
            processedCourses++;  // We can complete this course
            
            System.out.println("Processing course " + course + 
                             " (total processed: " + processedCourses + ")");
            
            // For all courses that depend on this course
            for (int neighbor : graph[course]) {
                // Reduce their in-degree (one prerequisite satisfied)
                inDegree[neighbor]--;
                
                System.out.println("  Course " + neighbor + 
                                 " in-degree reduced to " + inDegree[neighbor]);
                
                // If in-degree becomes 0, all prerequisites satisfied!
                if (inDegree[neighbor] == 0) {
                    queue.offer(neighbor);  // Can now take this course
                    System.out.println("  Course " + neighbor + 
                                     " ready, adding to queue");
                }
            }
            System.out.println();
        }
        
        
        // STEP 4: Check if all courses processed
        // If processedCourses == numCourses: All courses can be completed
        // If processedCourses < numCourses: Some courses stuck (cycle exists)
        
        System.out.println("Total courses: " + numCourses);
        System.out.println("Processed courses: " + processedCourses);
        
        return processedCourses == numCourses;
    }
    
    
    // ========================================================================
    //                         MAIN METHOD
    // ========================================================================
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=".repeat(70));
        System.out.println("                 COURSE SCHEDULE PROBLEM");
        System.out.println("=".repeat(70));
        System.out.println();
        
        // Input number of courses
        System.out.print("Enter number of courses: ");
        int numCourses = scanner.nextInt();
        
        // Input number of prerequisites
        System.out.print("Enter number of prerequisites: ");
        int numPrereq = scanner.nextInt();
        
        // Input prerequisites
        int[][] prerequisites = new int[numPrereq][2];
        
        System.out.println("\nEnter prerequisites (format: course prerequisite):");
        System.out.println("Example: '1 0' means course 1 needs course 0");
        
        for (int i = 0; i < numPrereq; i++) {
            System.out.print("Prerequisite " + (i+1) + ": ");
            prerequisites[i][0] = scanner.nextInt();  // Course
            prerequisites[i][1] = scanner.nextInt();  // Prerequisite
        }
        
        // Display input
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INPUT:");
        System.out.println("=".repeat(70));
        System.out.println("Number of courses: " + numCourses);
        System.out.println("Prerequisites:");
        for (int i = 0; i < numPrereq; i++) {
            System.out.println("  Course " + prerequisites[i][0] + 
                             " needs Course " + prerequisites[i][1]);
        }
        System.out.println("=".repeat(70));
        System.out.println();
        
        // Approach 1: DFS
        System.out.println("APPROACH 1: DFS + CYCLE DETECTION");
        System.out.println("-".repeat(70));
        boolean result1 = canFinishDFS(numCourses, prerequisites);
        System.out.println("\nCan finish all courses (DFS)? " + result1);
        System.out.println();
        
        // Approach 2: BFS (Kahn's Algorithm)
        System.out.println("\nAPPROACH 2: BFS + KAHN'S ALGORITHM");
        System.out.println("-".repeat(70));
        boolean result2 = canFinishBFS(numCourses, prerequisites);
        System.out.println("\nCan finish all courses (BFS)? " + result2);
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("Both approaches agree: " + (result1 == result2 ? "✓" : "✗"));
        System.out.println("=".repeat(70));
        
        scanner.close();
    }
}


/*
 * ============================================================================
 *                      DETAILED CONCEPT EXPLANATION
 * ============================================================================
 * 
 * 
 * 1. WHAT IS TOPOLOGICAL SORT?
 * =============================
 * 
 * Topological Sort = Linear ordering of directed graph
 * Such that for every edge u → v, u comes before v
 * 
 * Real-world example: Getting dressed
 * - Must wear underwear before pants (dependency!)
 * - Must wear socks before shoes
 * 
 * Valid order: underwear → pants → socks → shoes
 * Invalid: shoes → socks (can't wear shoes first!)
 * 
 * 
 * 2. WHEN IS TOPOLOGICAL SORT POSSIBLE?
 * ======================================
 * 
 * ONLY when graph is DAG (Directed Acyclic Graph)
 * DAG = Directed + No cycles
 * 
 * If cycle exists: No valid ordering!
 * Example: A depends on B, B depends on A
 * Cannot decide which comes first!
 * 
 * 
 * 3. DFS APPROACH - 3 STATES:
 * ===========================
 * 
 * WHITE (0) = UNVISITED
 * - Haven't explored this node yet
 * 
 * GRAY (1) = VISITING
 * - Currently exploring this node
 * - Node is in recursion stack
 * 
 * BLACK (2) = VISITED
 * - Completely explored this node
 * - All descendants processed
 * 
 * CYCLE DETECTION RULE:
 * If we encounter a GRAY node during DFS → CYCLE!
 * 
 * Why? Gray means node is in current path
 * Reaching it again means we looped back!
 * 
 * Example:
 * Start DFS at A (mark GRAY)
 * → Go to B (mark GRAY)
 *   → Go to C (mark GRAY)
 *     → Go to A (already GRAY!)
 *     → CYCLE DETECTED!
 * 
 * 
 * 4. BFS APPROACH - KAHN'S ALGORITHM:
 * ===================================
 * 
 * Key concept: IN-DEGREE
 * In-degree = Number of incoming edges
 * 
 * Algorithm:
 * 1. Calculate in-degree for all nodes
 * 2. Add all nodes with in-degree 0 to queue
 * 3. While queue not empty:
 *    a. Remove node from queue
 *    b. For each neighbor, reduce in-degree by 1
 *    c. If neighbor's in-degree becomes 0, add to queue
 * 4. If processed all nodes → No cycle
 *    If couldn't process all → Cycle exists
 * 
 * Why this works?
 * - Nodes with in-degree 0 have no dependencies
 * - We process them first
 * - Then "remove" them from graph (reduce neighbors' in-degrees)
 * - Continue with newly freed nodes
 * - If cycle exists, some nodes never reach in-degree 0
 * 
 * 
 * 5. DRY RUN EXAMPLE (NO CYCLE):
 * ===============================
 * 
 * Courses: 4
 * Prerequisites: [[1,0], [2,0], [3,1], [3,2]]
 * 
 * Graph:
 *     0
 *    / \
 *   1   2
 *    \ /
 *     3
 * 
 * DFS APPROACH:
 * -------------
 * Start DFS at 0:
 * - Mark 0 as VISITING (1)
 * - Visit neighbor 1:
 *   - Mark 1 as VISITING (1)
 *   - Visit neighbor 3:
 *     - Mark 3 as VISITING (1)
 *     - No neighbors
 *     - Mark 3 as VISITED (2)
 *   - Mark 1 as VISITED (2)
 * - Visit neighbor 2:
 *   - Mark 2 as VISITING (1)
 *   - Visit neighbor 3:
 *     - 3 is VISITED (2), skip
 *   - Mark 2 as VISITED (2)
 * - Mark 0 as VISITED (2)
 * 
 * No GRAY nodes encountered → No cycle!
 * 
 * BFS APPROACH (KAHN'S):
 * ---------------------
 * In-degrees: [0, 1, 1, 2]
 * 
 * Queue: [0] (in-degree 0)
 * 
 * Process 0:
 * - Processed = 1
 * - Reduce in-degrees: [0, 0, 0, 2]
 * - Queue: [1, 2]
 * 
 * Process 1:
 * - Processed = 2
 * - Reduce in-degrees: [0, 0, 0, 1]
 * - Queue: [2]
 * 
 * Process 2:
 * - Processed = 3
 * - Reduce in-degrees: [0, 0, 0, 0]
 * - Queue: [3]
 * 
 * Process 3:
 * - Processed = 4
 * - Queue: []
 * 
 * Processed all 4 courses → No cycle!
 * 
 * 
 * 6. DRY RUN EXAMPLE (WITH CYCLE):
 * =================================
 * 
 * Courses: 3
 * Prerequisites: [[0,1], [1,2], [2,0]]
 * 
 * Graph: 0 → 1 → 2 → 0 (CYCLE!)
 * 
 * DFS APPROACH:
 * -------------
 * Start DFS at 0:
 * - Mark 0 as VISITING (1)
 * - Visit 1:
 *   - Mark 1 as VISITING (1)
 *   - Visit 2:
 *     - Mark 2 as VISITING (1)
 *     - Visit 0:
 *       - 0 is VISITING (1)!
 *       - CYCLE DETECTED!
 * 
 * Return FALSE!
 * 
 * BFS APPROACH:
 * ------------
 * In-degrees: [1, 1, 1] (all have prerequisites)
 * 
 * Queue: [] (no course with in-degree 0!)
 * 
 * Can't process any course!
 * Processed = 0, needed 3
 * Return FALSE!
 * 
 * 
 * 7. COMPLEXITY ANALYSIS:
 * =======================
 * 
 * Both DFS and BFS:
 * 
 * Time: O(V + E)
 * - V = number of vertices (courses)
 * - E = number of edges (prerequisites)
 * - We visit each vertex once: O(V)
 * - We traverse each edge once: O(E)
 * - Total: O(V + E)
 * 
 * Space: O(V + E)
 * - Graph storage: O(V + E)
 * - State/in-degree array: O(V)
 * - Queue/recursion stack: O(V)
 * - Total: O(V + E)
 * 
 * 
 * 8. WHEN TO USE WHICH?
 * =====================
 * 
 * DFS APPROACH:
 * Pros:
 * - Simpler to understand (recursion)
 * - Less code
 * - Can find actual topological order easily
 * 
 * Cons:
 * - Recursion overhead
 * - Stack overflow risk for large graphs
 * 
 * BFS APPROACH (KAHN'S):
 * Pros:
 * - Iterative (no recursion)
 * - More intuitive (process dependencies first)
 * - Better for very large graphs
 * 
 * Cons:
 * - Need to calculate in-degrees
 * - Slightly more code
 * 
 * Both are correct! Choose based on preference.
 * 
 * 
 * REMEMBER:
 * =========
 * ✓ Course Schedule = Topological Sort problem
 * ✓ Topological Sort only possible if NO CYCLES
 * ✓ DFS: Use 3 states (WHITE, GRAY, BLACK)
 * ✓ BFS: Use in-degrees (Kahn's algorithm)
 * ✓ If cycle: Some courses impossible
 * ✓ Time: O(V + E), Space: O(V + E)
 */
