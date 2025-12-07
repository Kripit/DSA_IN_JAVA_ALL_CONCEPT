/*
 * ============================================================================
 *                  PROBLEM 3: WORD LADDER (HARD - BFS Shortest Path)
 * ============================================================================
 * 
 * PROBLEM STATEMENT:
 * ------------------
 * Given two words (beginWord and endWord) and a dictionary's word list,
 * find the LENGTH of SHORTEST transformation sequence from beginWord to endWord.
 * 
 * RULES:
 * 1. Only one letter can be changed at a time
 * 2. Each transformed word must exist in the word list
 * 3. beginWord is NOT in the dictionary (but you can start from it)
 * 
 * Return 0 if no transformation sequence exists.
 * 
 * Example 1:
 * Input:
 * beginWord = "hit"
 * endWord = "cog"
 * wordList = ["hot","dot","dog","lot","log","cog"]
 * 
 * Output: 5
 * 
 * Explanation:
 * One shortest path: "hit" → "hot" → "dot" → "dog" → "cog"
 * Length = 5 (including beginWord)
 * 
 * Example 2:
 * Input:
 * beginWord = "hit"
 * endWord = "cog"
 * wordList = ["hot","dot","dog","lot","log"]
 * 
 * Output: 0
 * Explanation: endWord "cog" is not in wordList, impossible!
 * 
 * 
 * CONCEPT EXPLANATION:
 * --------------------
 * 
 * WHAT IS THIS PROBLEM?
 * 
 * This is a GRAPH SHORTEST PATH problem disguised as a word problem!
 * 
 * Think of it as:
 * - Each word = NODE in graph
 * - Two words are connected if they differ by exactly ONE letter
 * - Find SHORTEST PATH from beginWord to endWord
 * 
 * Example Graph:
 * hit → hot → dot → dog → cog
 *       ↓     ↓     ↓
 *      lot → log ----↑
 * 
 * This is an UNWEIGHTED graph (all edges have weight 1)
 * For shortest path in UNWEIGHTED graph → Use BFS!
 * 
 * 
 * WHY BFS NOT DFS?
 * ----------------
 * 
 * BFS explores level by level
 * First time we reach target = shortest path!
 * 
 * DFS goes deep first
 * Might find long path before short path
 * Would need to explore ALL paths to find shortest
 * 
 * Example:
 * hit → hot → lot → log → cog (length 5)
 * hit → hot → dot → dog → cog (length 5)
 * 
 * BFS will find one of these first (both shortest)
 * DFS might explore many longer paths first
 * 
 * 
 * THE ALGORITHM:
 * --------------
 * 
 * 1. Check if endWord in dictionary (if not, impossible)
 * 2. Use BFS starting from beginWord
 * 3. For each word, try changing each letter (a-z)
 * 4. If new word is in dictionary and not visited, add to queue
 * 5. Track level (distance from start)
 * 6. First time we reach endWord = shortest path!
 * 
 * 
 * OPTIMIZATION:
 * -------------
 * 
 * Instead of generating all possible words (26^wordLength),
 * we only check words in the dictionary!
 * 
 * Two approaches:
 * 1. For each word, try all 26 letters in each position
 * 2. Compare current word with all words in dictionary
 * 
 * Approach 1 better when dictionary is LARGE
 * Approach 2 better when dictionary is SMALL
 * 
 * We'll use Approach 1 (more common in interviews)
 */

import java.util.*;

public class WordLadder {
    
    // ========================================================================
    //                    APPROACH 1: BFS (OPTIMAL FOR SHORTEST PATH)
    // ========================================================================
    // Time: O(M^2 × N) where M = word length, N = dictionary size
    // Space: O(M × N) for queue and visited set
    
    public static int ladderLengthBFS(String beginWord, String endWord, List<String> wordList) {
        
        // STEP 1: Convert wordList to HashSet for O(1) lookup
        // Why HashSet? 
        // - contains() is O(1) vs O(n) for List
        // - We'll check many words, so this speeds up significantly
        
        HashSet<String> wordSet = new HashSet<>(wordList);
        
        // Edge case: endWord not in dictionary
        if (!wordSet.contains(endWord)) {
            System.out.println("End word not in dictionary. Impossible!");
            return 0;
        }
        
        System.out.println("Dictionary: " + wordSet);
        System.out.println("Begin: " + beginWord + " → End: " + endWord);
        System.out.println();
        
        
        // STEP 2: Initialize BFS
        // Queue stores: [word, level]
        // level = distance from beginWord (number of transformations + 1)
        
        Queue<Pair> queue = new LinkedList<>();
        queue.offer(new Pair(beginWord, 1));  // Start at level 1
        
        // Visited set to avoid revisiting words
        HashSet<String> visited = new HashSet<>();
        visited.add(beginWord);
        
        System.out.println("Starting BFS from: " + beginWord);
        System.out.println();
        
        
        // STEP 3: BFS traversal
        while (!queue.isEmpty()) {
            // Get current word and level
            Pair current = queue.poll();
            String word = current.word;
            int level = current.level;
            
            System.out.println("Processing: " + word + " (level " + level + ")");
            
            // Check if we reached target
            if (word.equals(endWord)) {
                System.out.println("Reached end word!");
                return level;  // Return shortest path length
            }
            
            // Try changing each character
            // word.length() gives length of string
            // word.charAt(i) gets character at position i
            
            for (int i = 0; i < word.length(); i++) {
                // Try all 26 letters (a to z)
                for (char c = 'a'; c <= 'z'; c++) {
                    
                    // Create new word by changing character at position i
                    // We need to convert String to char array to modify it
                    
                    char[] wordArray = word.toCharArray();  // Convert to array
                    wordArray[i] = c;  // Change character at position i
                    String newWord = new String(wordArray);  // Convert back to String
                    
                    // Check if this new word is valid and unvisited
                    if (wordSet.contains(newWord) && !visited.contains(newWord)) {
                        System.out.println("  Found valid transformation: " + word + " → " + newWord);
                        
                        // Mark as visited
                        visited.add(newWord);
                        
                        // Add to queue with incremented level
                        queue.offer(new Pair(newWord, level + 1));
                    }
                }
            }
            System.out.println();
        }
        
        // If we exit loop without finding endWord
        System.out.println("No path found!");
        return 0;
    }
    
    
    // ========================================================================
    //              APPROACH 2: BIDIRECTIONAL BFS (OPTIMIZED)
    // ========================================================================
    // Search from both ends simultaneously
    // Faster when branching factor is large
    // Time: O(M^2 × N/2) - roughly half the work
    // Space: O(M × N)
    
    public static int ladderLengthBidirectional(String beginWord, String endWord, List<String> wordList) {
        
        HashSet<String> wordSet = new HashSet<>(wordList);
        
        if (!wordSet.contains(endWord)) {
            return 0;
        }
        
        System.out.println("Using Bidirectional BFS");
        System.out.println();
        
        // Two sets: one from begin, one from end
        // We expand the smaller set each time
        HashSet<String> beginSet = new HashSet<>();
        HashSet<String> endSet = new HashSet<>();
        HashSet<String> visited = new HashSet<>();
        
        beginSet.add(beginWord);
        endSet.add(endWord);
        
        int level = 1;
        
        while (!beginSet.isEmpty() && !endSet.isEmpty()) {
            
            // Always expand the smaller set (optimization)
            if (beginSet.size() > endSet.size()) {
                HashSet<String> temp = beginSet;
                beginSet = endSet;
                endSet = temp;
            }
            
            System.out.println("Level " + level + ":");
            System.out.println("  Expanding from: " + beginSet);
            
            HashSet<String> nextLevel = new HashSet<>();
            
            for (String word : beginSet) {
                // Try all transformations
                for (int i = 0; i < word.length(); i++) {
                    char[] wordArray = word.toCharArray();
                    
                    for (char c = 'a'; c <= 'z'; c++) {
                        wordArray[i] = c;
                        String newWord = new String(wordArray);
                        
                        // If other end reached this word, we found shortest path!
                        if (endSet.contains(newWord)) {
                            System.out.println("  Both searches met at: " + newWord);
                            return level + 1;
                        }
                        
                        // If valid and unvisited, add to next level
                        if (wordSet.contains(newWord) && visited.add(newWord)) {
                            nextLevel.add(newWord);
                        }
                    }
                }
            }
            
            beginSet = nextLevel;
            level++;
            System.out.println();
        }
        
        return 0;
    }
    
    
    // ========================================================================
    //                         HELPER CLASS
    // ========================================================================
    
    // Pair class to store word and its level in queue
    // This is a simple container class
    static class Pair {
        String word;
        int level;
        
        // Constructor: creates new Pair object
        Pair(String word, int level) {
            this.word = word;
            this.level = level;
        }
    }
    
    
    // ========================================================================
    //                         MAIN METHOD
    // ========================================================================
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=".repeat(70));
        System.out.println("                    WORD LADDER PROBLEM");
        System.out.println("=".repeat(70));
        System.out.println();
        
        // Input begin word
        System.out.print("Enter begin word: ");
        String beginWord = scanner.next().toLowerCase();  // toLowerCase() converts to lowercase
        
        // Input end word
        System.out.print("Enter end word: ");
        String endWord = scanner.next().toLowerCase();
        
        // Input dictionary size
        System.out.print("Enter number of words in dictionary: ");
        int n = scanner.nextInt();
        
        // Input dictionary
        List<String> wordList = new ArrayList<>();
        System.out.println("Enter dictionary words:");
        for (int i = 0; i < n; i++) {
            String word = scanner.next().toLowerCase();
            wordList.add(word);
        }
        
        // Display input
        System.out.println("\n" + "=".repeat(70));
        System.out.println("INPUT:");
        System.out.println("=".repeat(70));
        System.out.println("Begin Word: " + beginWord);
        System.out.println("End Word: " + endWord);
        System.out.println("Dictionary: " + wordList);
        System.out.println("=".repeat(70));
        System.out.println();
        
        // Approach 1: Standard BFS
        System.out.println("APPROACH 1: STANDARD BFS");
        System.out.println("-".repeat(70));
        int result1 = ladderLengthBFS(beginWord, endWord, new ArrayList<>(wordList));
        System.out.println("Shortest transformation length: " + result1);
        System.out.println();
        
        // Approach 2: Bidirectional BFS
        System.out.println("\nAPPROACH 2: BIDIRECTIONAL BFS");
        System.out.println("-".repeat(70));
        int result2 = ladderLengthBidirectional(beginWord, endWord, wordList);
        System.out.println("Shortest transformation length: " + result2);
        
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
 * 1. WHY THIS IS A GRAPH PROBLEM:
 * ===============================
 * 
 * We can think of words as nodes and transformations as edges:
 * 
 * Example:
 * hit → hot (changed 'i' to 'o')
 * hot → dot (changed 'h' to 'd')
 * dot → dog (changed 't' to 'g')
 * 
 * Graph:
 *     hit
 *      |
 *     hot
 *    /   \
 *  lot   dot
 *   |     |
 *  log   dog
 *    \   /
 *     cog
 * 
 * Problem: Find shortest path from hit to cog
 * Solution: BFS (for unweighted shortest path)
 * 
 * 
 * 2. WHY BFS IS OPTIMAL HERE:
 * ===========================
 * 
 * BFS properties:
 * - Explores level by level (distance 1, then 2, then 3...)
 * - First time we reach target = shortest path
 * - Guaranteed to find shortest path in unweighted graph
 * 
 * All edges have same weight (1 transformation)
 * So BFS gives optimal solution!
 * 
 * DFS would need to explore ALL paths to find shortest
 * BFS stops as soon as target found!
 * 
 * 
 * 3. STRING MANIPULATION IN JAVA:
 * ================================
 * 
 * String in Java is IMMUTABLE (cannot be changed)
 * To modify a string, we convert to char array:
 * 
 * String word = "hit";
 * char[] arr = word.toCharArray();  // Convert to array ['h','i','t']
 * arr[0] = 'b';  // Modify array ['b','i','t']
 * String newWord = new String(arr);  // Create new String "bit"
 * 
 * Why this process?
 * - Strings are immutable for safety and performance
 * - char[] is mutable, can change individual characters
 * - Create new String from modified array
 * 
 * 
 * 4. HASHSET vs ARRAYLIST:
 * ========================
 * 
 * ArrayList (List):
 * - contains(element): O(n) - must search entire list
 * - Good for: ordered access, iteration
 * 
 * HashSet (Set):
 * - contains(element): O(1) - direct hash lookup
 * - Good for: fast membership checking
 * 
 * In this problem:
 * We check "is word in dictionary?" many times
 * HashSet makes this O(1) instead of O(n)
 * Huge performance improvement!
 * 
 * Example:
 * Dictionary size = 1000 words
 * We check each word ~26×wordLength times
 * 
 * With ArrayList: 26 × 3 × 1000 = 78,000 operations per word!
 * With HashSet: 26 × 3 × 1 = 78 operations per word!
 * 
 * 1000x faster!
 * 
 * 
 * 5. DRY RUN EXAMPLE:
 * ===================
 * 
 * Input:
 * beginWord = "hit"
 * endWord = "cog"
 * wordList = ["hot","dot","dog","lot","log","cog"]
 * 
 * BFS Process:
 * 
 * Level 1: ["hit"]
 * - Try all transformations of "hit"
 * - "hot" is in dictionary → Add to queue
 * 
 * Level 2: ["hot"]
 * - Try all transformations of "hot"
 * - "dot" in dictionary → Add
 * - "lot" in dictionary → Add
 * 
 * Level 3: ["dot", "lot"]
 * - From "dot":
 *   - "dog" in dictionary → Add
 * - From "lot":
 *   - "log" in dictionary → Add
 * 
 * Level 4: ["dog", "log"]
 * - From "dog":
 *   - "cog" in dictionary → FOUND!
 *   - Return level 5 (hit→hot→dot→dog→cog)
 * 
 * 
 * 6. BIDIRECTIONAL BFS EXPLAINED:
 * ================================
 * 
 * Instead of searching from start only:
 * Search from BOTH start and end simultaneously!
 * 
 * Normal BFS:
 * start → → → → → end
 * 
 * Bidirectional BFS:
 * start → → ← ← end
 * Meet in middle!
 * 
 * Why faster?
 * - Normal BFS explores b^d nodes (b=branching, d=depth)
 * - Bidirectional explores 2×b^(d/2) nodes
 * - Much smaller! (exponential vs square root)
 * 
 * Example:
 * Branching factor = 10, depth = 6
 * Normal: 10^6 = 1,000,000 nodes
 * Bidirectional: 2×10^3 = 2,000 nodes
 * 500x faster!
 * 
 * 
 * 7. COMPLEXITY ANALYSIS:
 * =======================
 * 
 * Let M = word length, N = dictionary size
 * 
 * TIME COMPLEXITY:
 * 
 * For each word:
 * - Try changing each of M positions: M iterations
 * - For each position, try 26 letters: 26 iterations
 * - Create new word and check dictionary: O(M)
 * - Total per word: M × 26 × M = O(M^2)
 * 
 * We might visit all N words in worst case:
 * Total: O(M^2 × N)
 * 
 * SPACE COMPLEXITY:
 * 
 * - Queue: O(N) in worst case (all words in queue)
 * - Visited set: O(N)
 * - Word set: O(N)
 * - Each word takes O(M) space
 * Total: O(M × N)
 * 
 * 
 * 8. OPTIMIZATION TECHNIQUES:
 * ===========================
 * 
 * 1. Use HashSet for dictionary
 *    - O(1) lookup vs O(N) for ArrayList
 * 
 * 2. Use visited set
 *    - Prevent revisiting same word
 *    - Avoid infinite loops
 * 
 * 3. Bidirectional BFS
 *    - Search from both ends
 *    - Roughly 2x faster
 * 
 * 4. Early termination
 *    - Stop as soon as target found
 *    - Don't explore rest of level
 * 
 * 
 * 9. EDGE CASES:
 * ==============
 * 
 * 1. endWord not in dictionary
 *    - Impossible to reach
 *    - Return 0
 * 
 * 2. beginWord == endWord
 *    - Already at target
 *    - Return 1
 * 
 * 3. No path exists
 *    - BFS exhausts all options
 *    - Return 0
 * 
 * 4. Empty dictionary
 *    - Can't transform anything
 *    - Return 0
 * 
 * 
 * 10. COMMON MISTAKES:
 * ====================
 * 
 * Mistake 1: Not using HashSet
 * → Very slow for large dictionaries
 * 
 * Mistake 2: Not marking visited
 * → Infinite loops, TLE
 * 
 * Mistake 3: Returning level instead of level+1
 * → Off-by-one error
 * 
 * Mistake 4: Using DFS instead of BFS
 * → Might not find shortest path
 * 
 * Mistake 5: Not checking if endWord in dictionary
 * → Unnecessary computation
 * 
 * 
 * REMEMBER:
 * =========
 * ✓ This is shortest path problem → Use BFS
 * ✓ Convert dictionary to HashSet for O(1) lookup
 * ✓ Try all 26 letters at each position
 * ✓ Mark words as visited to avoid loops
 * ✓ Return level when endWord found
 * ✓ Bidirectional BFS for optimization
 * ✓ Time: O(M^2 × N), Space: O(M × N)
 */