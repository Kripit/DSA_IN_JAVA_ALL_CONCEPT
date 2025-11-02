/*
 * ============================================================================
 *                    PROBLEM 1: NUMBER OF ISLANDS (SIMPLE)
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * Given a 2D grid of '1's (land) and '0's (water),
 * count the number of ISLANDS.
 * 
 * An ISLAND is surrounded by water and formed by connecting adjacent lands
 * horizontally or vertically (NOT diagonally).
 * 
 * Example 1:
 * Input: grid = [
 *   ['1','1','0','0','0'],
 *   ['1','1','0','0','0'],
 *   ['0','0','1','0','0'],
 *   ['0','0','0','1','1']
 * ]
 * Output: 3
 * 
 * Explanation:
 * Island 1: Top-left 2x2 block of 1's
 * Island 2: Single 1 at (2,2)
 * Island 3: Bottom-right 2 cells of 1's
 * 
 * 
 * CONCEPT EXPLANATION:
 * --------------------
 * 
 * WHAT IS AN ISLAND?
 * Think of it like this:
 * - '1' = Land
 * - '0' = Water
 * - Island = Group of connected land cells
 * 
 * HOW TO COUNT ISLANDS?
 * 1. Go through each cell
 * 2. When we find a '1' (land), it's part of an island
 * 3. Use DFS to "explore" the entire island (mark all connected 1's)
 * 4. Count this as 1 island
 * 5. Continue searching for more islands
 * 
 * 
 * DFS (DEPTH FIRST SEARCH) EXPLAINED:
 * -----------------------------------
 * 
 * DFS = Go as DEEP as possible before backtracking
 * 
 * Think of exploring a cave:
 * - Enter cave
 * - Go down one tunnel completely
 * - If dead end, come back
 * - Try next tunnel
 * 
 * In our problem:
 * - Find a '1' (land)
 * - Explore all connected 1's (up, down, left, right)
 * - Mark them as visited (change to '0' or use visited array)
 * - This prevents counting same island twice
 * 
 * 
 * VISUALIZATION:
 * --------------
 * 
 * Grid:
 * 1 1 0
 * 1 0 1
 * 0 1 1
 * 
 * Step 1: Find first '1' at (0,0)
 * DFS explores: (0,0) → (0,1) → (1,0)
 * All connected, mark as visited
 * Island count = 1
 * 
 * Step 2: Find next unvisited '1' at (1,2)
 * DFS explores: (1,2) → (2,1) → (2,2)
 * All connected, mark as visited
 * Island count = 2
 * 
 * Total islands = 2
 */
    /*
     * ------------------------------------------------------------------------
     * DFS FUNCTION (Depth First Search)
     * ------------------------------------------------------------------------
     * This recursively explores all connected land cells.
     *
     * Tracing example for one island:
     *
     * Suppose we call dfs(0,0)
     * - grid[0][0] = '1' → mark it as '0' (visited)
     * - Then check 4 directions:
     *     (down)  dfs(1,0)
     *     (up)    dfs(-1,0)
     *     (right) dfs(0,1)
     *     (left)  dfs(0,-1)
     *
     * Each valid '1' leads to another DFS → spreading like paint.
     * When no more connected '1's remain, recursion unwinds.
     * ------------------------------------------------------------------------
     */

import java.util.Scanner;

public class NOofIslandsimple {

    // ========================================================================
    //                         APPROACH 1: DFS (RECURSIVE)
    // ========================================================================
    // This is the MOST COMMON and CLEAN approach
    // Time Complexity: O(rows × cols) - Visit each cell once
    // Space Complexity: O(rows × cols) - Recursion stack in worst case 
    
    public static int numIslandsDFS(char[][] grid){
        //EDGE CASE : empty grid 
        // if grid is null or empty , no islands

        if(grid == null || grid.length == 0){
            return 0;
        }

// get dimensions of grid

        int rows = grid.length; // no of rows
        int cols = grid[0].length; // no of columns

        int islandCount = 0;

        System.out.println("Starting DFS traversal:");
        System.out.println("Grid dimensions: " + rows + " x " + cols);
        System.out.println();
        
        for ( int row = 0 ; row < rows ; row++){
            for( int col = 0; col < cols ; col ++){

                // if current cell is '1'(land) , it's part of an island

                if(grid[row][col]=='1'){
                    //found a new island!
                    islandCount++;
                    System.out.println("Found new island starting at (" + row + "," + col + ")");
                    // Use DFS to explore and mark entire island
                    // This will change all connected '1's to '0's
                    // So we don't count same island again

                    dfs(grid , row , col , rows , cols);
                    System.out.println("Island " + islandCount + " fully explored");
                    System.out.println("Current grid state:");
                    printGrid(grid);
                    System.out.println();
                }
            }
        }

        return islandCount;
    }
    private static void dfs(char[][] grid , int row , int col , int rows , int cols){

        // BASE CASES (When to STOP recursion):
        
        // Case 1: Out of bounds
        // If row or col is outside grid boundaries, return
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return;  // Stop exploring this direction
        }

        // case 2: water or already visited 
        // if current cell is '0'(water) or already visited , return

        if(grid[row][col] == '0'){
            return;
        }
        // MARK AS VISITED
        // Change '1' to '0' to mark this cell as visited
        // This prevents infinite recursion and recounting

        grid[row][col] = '0'; 

        // RECURSIVE CASE: Explore all 4 directions
        // We can move: UP, DOWN, LEFT, RIGHT (NOT diagonal!)
        
        // up: row -1 , same col
        dfs(grid , row - 1 , col , rows , cols);

        // down: row +1 , same col
        dfs(grid , row+1 , col , rows, cols);
        // LEFT: same row, col - 1
        dfs(grid, row, col - 1, rows, cols);
        
        // RIGHT: same row, col + 1
        dfs(grid, row, col + 1, rows, cols);  

        // After exploring all directions, this cell is fully processed
        // Recursion will backtrack to previous cell

    }
    // ========================================================================
    //                         APPROACH 2: BFS (ITERATIVE)
    // ========================================================================
    // Using Queue for level-by-level exploration
    // Time Complexity: O(rows × cols)
    // Space Complexity: O(min(rows, cols)) - Queue size
    
    // Import needed for Queue
    // Queue: First In First Out (FIFO) data structure
    // LinkedList: Implements Queue interface


    public static int numIslandsBFS(char[][] grid){

        if(grid == null || grid.length == 0 ) return 0;

        int rows = grid.length;
        int cols = grid[0].length;

        int islandCount =0;

        for ( int row = 0 ; row < rows ; row++){
            for( int col = 0 ; col < cols ; col++){

                if(grid[row][col]=='1'){
                    islandCount++;
                    System.out.println("Found island " + islandCount + " at (" + row + "," + col + ")");

                    bfs(grid , row , col , rows , cols);
                    System.out.println("Island explored using BFS");
                    System.out.println();
                }
            }
        }
        return islandCount;
    }

    private static void bfs(char[][] grid, int startRow, int startCol, int rows, int cols) {
        // Queue: Import java.util.Queue and java.util.LinkedList
        // Queue stores cells to visit
        // We store position as int[] where [0]=row, [1]=col

        java.util.Queue<int[]> queue = new java.util.LinkedList<>();
        //Queue<int[]> → data structure that follows FIFO (First In, First Out) order.
        //new LinkedList<>() → gives the actual working implementation for that queue.

        // add starting cell to queue
        queue.offer(new int[]{startRow , startCol});

        // mark starting cell as visited
        grid[startRow][startCol] = '0';

        // direction arrays for moving up , down , left , right
        // this is like a common technique for grid problems

        int[][] directions ={
            {-1, 0},  // UP: row-1, col+0
            {1, 0},   // DOWN: row+1, col+0
            {0, -1},  // LEFT: row+0, col-1
            {0, 1}    // RIGHT: row+0, col+1
        };

        while (!queue.isEmpty()){
            // Remove cell from front of queue
            int[] current = queue.poll(); // poll()removes and returns front
            int currentRow = current[0];
            int currentCol = current[1];

            for(int[] dir : directions){

                int newRow = currentRow+dir[0]; // calculating new positions
                int newCol = currentCol + dir[1];
                if (newRow >= 0 && newRow < rows && 
                    newCol >= 0 && newCol < cols && 
                    grid[newRow][newCol] == '1') {
                    
                    // Valid land cell found!
                    queue.offer(new int[]{newRow, newCol});  // Add to queue
                    grid[newRow][newCol] = '0';  // Mark as visited

            }
        }

    }   
}
    // ========================================================================
    //                         HELPER METHODS
    // ========================================================================
    
    // Method to print grid (for visualization)
    private static void printGrid(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();  // New line after each row
        }
    }
    // ========================================================================
    //                         MAIN METHOD
    // ========================================================================
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=".repeat(70));
        System.out.println("         NUMBER OF ISLANDS PROBLEM");
        System.out.println("=".repeat(70));
        System.out.println();
        
        // Input grid dimensions
        System.out.print("Enter number of rows: ");
        int rows = scanner.nextInt();
        
        System.out.print("Enter number of columns: ");
        int cols = scanner.nextInt();
        
        // Create grid
        char[][] grid1 = new char[rows][cols];
        char[][] grid2 = new char[rows][cols];  // Copy for BFS
        
        // Input grid values
        System.out.println("\nEnter grid (use '1' for land, '0' for water):");
        System.out.println("Enter " + rows + " rows, each with " + cols + " characters:");
        
        for (int i = 0; i < rows; i++) {
            String row = scanner.next();  // Read one row as string
            for (int j = 0; j < cols; j++) {
                grid1[i][j] = row.charAt(j);  // Convert string to char array
                grid2[i][j] = row.charAt(j);  // Make copy
            }
        }
        
        // Display input
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INPUT GRID:");
        System.out.println("=".repeat(70));
        printGrid(grid1);
        System.out.println("=".repeat(70));
        System.out.println();
        
        // Approach 1: DFS (Recursive)
        System.out.println("APPROACH 1: DFS (RECURSIVE)");
        System.out.println("-".repeat(70));
        int result1 = numIslandsDFS(grid1);
        System.out.println("Total islands found (DFS): " + result1);
        System.out.println();
        
        // Approach 2: BFS (Iterative)
        System.out.println("\n" + "APPROACH 2: BFS (ITERATIVE)");
        System.out.println("-".repeat(70));
        int result2 = numIslandsBFS(grid2);
        System.out.println("Total islands found (BFS): " + result2);
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("Both approaches give same result: " + 
                         (result1 == result2 ? "✓" : "✗"));
        System.out.println("=".repeat(70));
        
        scanner.close();
    }
}


/*
 * ============================================================================
 *                         DETAILED CONCEPT EXPLANATION
 * ============================================================================
 * 
 * 
 * 1. WHAT IS DFS (DEPTH FIRST SEARCH)?
 * =====================================
 * 
 * DFS = Go DEEP first, then backtrack
 * 
 * Think of exploring a building:
 * - Enter a room
 * - Go through door 1, explore completely
 * - Come back, try door 2
 * - Continue until all doors explored
 * 
 * In code:
 * - Visit a cell
 * - Recursively visit its neighbors (up, down, left, right)
 * - When all neighbors explored, function returns (backtrack)
 * 
 * 
 * 2. HOW DFS WORKS IN THIS PROBLEM:
 * ==================================
 * 
 * Grid:
 * 1 1 0
 * 1 0 1
 * 0 1 1
 * 
 * Step-by-step:
 * 
 * a) Start at (0,0), find '1'
 * b) Call dfs(0,0)
 * c) Mark (0,0) as visited: change to '0'
 * d) Explore neighbors:
 *    - UP (−1,0): Out of bounds, return
 *    - DOWN (1,0): grid[1][0]='1', call dfs(1,0)
 *      - Mark (1,0) as '0'
 *      - Explore its neighbors... (recursion continues)
 *    - LEFT (0,−1): Out of bounds, return
 *    - RIGHT (0,1): grid[0][1]='1', call dfs(0,1)
 *      - Mark (0,1) as '0'
 *      - Explore its neighbors...
 * 
 * This continues until entire island explored!
 * 
 * 
 * 3. WHY CHANGE '1' TO '0'?
 * =========================
 * 
 * If we don't mark cells as visited:
 * - dfs(0,0) calls dfs(0,1)
 * - dfs(0,1) calls dfs(0,0) again!
 * - INFINITE RECURSION!
 * 
 * By changing '1' to '0':
 * - We mark cell as visited
 * - Prevent revisiting
 * - Prevent infinite loops
 * 
 * Alternative: Use separate boolean[][] visited array
 * But changing in-place is more space-efficient!
 * 
 * 
 * 4. WHAT IS BFS (BREADTH FIRST SEARCH)?
 * =======================================
 * 
 * BFS = Explore level by level
 * 
 * Think of ripples in water:
 * - Drop stone at center
 * - Ripple expands outward in circles
 * - Each circle = one level
 * 
 * In code:
 * - Use Queue (FIFO - First In First Out)
 * - Add starting cell to queue
 * - While queue not empty:
 *   - Remove cell from front
 *   - Add all valid neighbors to back
 * 
 * 
 * 5. DFS vs BFS IN THIS PROBLEM:
 * ==============================
 * 
 * Grid:
 * 1 1 0
 * 1 0 1
 * 
 * DFS order: (0,0) → (1,0) → (0,1) (go deep first)
 * BFS order: (0,0) → (1,0) → (0,1) (level by level)
 * 
 * Both visit same cells, just different order!
 * Both give same answer!
 * 
 * When to use which?
 * - DFS: Simpler code (recursion)
 * - BFS: Better for shortest path problems
 * 
 * 
 * 6. DIRECTION ARRAYS EXPLAINED:
 * ===============================
 * 
 * int[][] directions = {
 *     {-1, 0},  // UP
 *     {1, 0},   // DOWN
 *     {0, -1},  // LEFT
 *     {0, 1}    // RIGHT
 * };
 * 
 * This is a COMMON TECHNIQUE in grid problems!
 * 
 * Instead of writing:
 * check(row-1, col);  // UP
 * check(row+1, col);  // DOWN
 * check(row, col-1);  // LEFT
 * check(row, col+1);  // RIGHT
 * 
 * We use loop:
 * for (int[] dir : directions) {
 *     int newRow = row + dir[0];
 *     int newCol = col + dir[1];
 *     check(newRow, newCol);
 * }
 * 
 * Cleaner and extensible!
 * (Easy to add diagonals if needed)
 * 
 * 
 * 7. COMPLEXITY ANALYSIS:
 * =======================
 * 
 * Time Complexity: O(rows × cols)
 * - We visit each cell at most once
 * - Total cells = rows × cols
 * - Each cell visited once = O(rows × cols)
 * 
 * Space Complexity:
 * DFS: O(rows × cols) - Recursion stack in worst case
 * BFS: O(min(rows, cols)) - Queue size
 * 
 * Worst case for DFS:
 * All cells are '1' in a line:
 * 1 1 1 1 1 ...
 * Recursion depth = rows × cols
 * 
 * Worst case for BFS:
 * All cells are '1':
 * Queue can have at most one full diagonal
 * = min(rows, cols) cells
 * 
 * 
 * 8. JAVA COLLECTIONS EXPLAINED:
 * ===============================
 * 
 * Queue<int[]> queue = new LinkedList<>();
 * 
 * Queue: Interface (like a contract)
 * LinkedList: Implementation (actual data structure)
 * 
 * Why <int[]>?
 * - We store positions as [row, col]
 * - Each element in queue is an int array
 * 
 * Key methods:
 * - offer(element): Add to back of queue
 * - poll(): Remove and return from front
 * - peek(): Look at front (don't remove)
 * - isEmpty(): Check if empty
 * 
 * 
 * 9. COMMON MISTAKES:
 * ===================
 * 
 * Mistake 1: Not checking boundaries
 * if (grid[row][col] == '1')  // WRONG if row/col out of bounds!
 * Fix: Check boundaries first
 * 
 * Mistake 2: Not marking visited
 * Leads to infinite recursion/loops
 * 
 * Mistake 3: Using wrong base case
 * if (grid[row][col] != '1') return;  // CORRECT
 * if (grid[row][col] == '0') return;  // CORRECT
 * 
 * 
 * REMEMBER:
 * =========
 * ✓ DFS = Recursion, go deep first
 * ✓ BFS = Queue, level by level
 * ✓ Mark cells as visited (change to '0')
 * ✓ Check boundaries before accessing array
 * ✓ 4 directions: UP, DOWN, LEFT, RIGHT
 * ✓ Both DFS and BFS work, choose based on problem
 */


