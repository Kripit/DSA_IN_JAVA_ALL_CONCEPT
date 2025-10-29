/*
 * PROBLEM 3: LONGEST COMMON SUBSEQUENCE (LCS) - HARD
 * ===================================================
 * 
 * Given two strings, find length of LONGEST COMMON SUBSEQUENCE.
 * 
 * SUBSEQUENCE: Characters in same relative order, but NOT necessarily contiguous.
 * 
 * Example 1:
 * Input: text1 = "abcde", text2 = "ace"
 * Output: 3
 * Explanation: LCS is "ace" (length 3)
 * 
 * Example 2:
 * Input: text1 = "abc", text2 = "abc"
 * Output: 3
 * Explanation: LCS is "abc" (length 3)
 * 
 * Example 3:
 * Input: text1 = "abc", text2 = "def"
 * Output: 0
 * Explanation: No common subsequence
 * 
 * 
 * SUBSEQUENCE vs SUBSTRING:
 * -------------------------
 * 
 * SUBSTRING: Characters must be CONTIGUOUS
 * Example: "abc" → substrings: "a", "ab", "abc", "b", "bc", "c"
 * 
 * SUBSEQUENCE: Characters in ORDER but can SKIP
 * Example: "abc" → subsequences: "", "a", "b", "c", "ab", "ac", "bc", "abc"
 * 
 * "ace" is subsequence of "abcde" (skip 'b' and 'd')
 * "ace" is NOT substring of "abcde"
 * 
 * 
 * THE KEY INSIGHT (2D DP):
 * ------------------------
 * 
 * Compare strings CHARACTER BY CHARACTER from end:
 * 
 * text1 = "abcde"
 * text2 = "ace"
 * 
 * Case 1: Last characters MATCH (text1[i] == text2[j])
 * → Include this character in LCS
 * → LCS(i, j) = 1 + LCS(i-1, j-1)
 * 
 * Example: "abcde" and "ace"
 * Last chars: 'e' == 'e' ✓
 * LCS = 1 + LCS("abcd", "ac")
 * 
 * Case 2: Last characters DON'T MATCH
 * → Try skipping from either string
 * → LCS(i, j) = max(LCS(i-1, j), LCS(i, j-1))
 * 
 * Example: "abc" and "def"
 * Last chars: 'c' != 'f'
 * LCS = max(LCS("ab", "def"), LCS("abc", "de"))
 * 
 * 
 * 2D DP TABLE VISUALIZATION:
 * ---------------------------
 * 
 * text1 = "abc"
 * text2 = "ac"
 * 
 *       ""  a  c
 *    "" 0   0  0
 *    a  0   1  1
 *    b  0   1  1
 *    c  0   1  2
 * 
 * dp[i][j] = LCS length of text1[0..i-1] and text2[0..j-1]
 */

public class LongestCommonSubsequence {
    
    // ================================================================
    //           APPROACH 1: RECURSION (Understanding only)
    // ================================================================
    // Time: O(2^(m+n)) - Exponential!
    // Space: O(m+n) - Recursion stack
    
    public static int lcsRecursion(String text1, String text2, int i, int j) {
        // BASE CASE: Reached end of either string
        if (i == 0 || j == 0) {
            return 0;  // No characters left to match
        }
        
        // CASE 1: Characters match
        if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
            return 1 + lcsRecursion(text1, text2, i - 1, j - 1);
        }
        
        // CASE 2: Characters don't match
        // Try skipping from either string, take maximum
        int skipText1 = lcsRecursion(text1, text2, i - 1, j);
        int skipText2 = lcsRecursion(text1, text2, i, j - 1);
        
        return Math.max(skipText1, skipText2);
    }
    
    
    // ================================================================
    //        APPROACH 2: MEMOIZATION (Top-Down DP)
    // ================================================================
    // Time: O(m × n)
    // Space: O(m × n)
    
    public static int lcsMemo(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        
        // Create memo table
        int[][] memo = new int[m + 1][n + 1];
        
        // Initialize with -1 (not calculated)
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                memo[i][j] = -1;
            }
        }
        
        return lcsMemoHelper(text1, text2, m, n, memo);
    }
    
    private static int lcsMemoHelper(String text1, String text2, 
                                     int i, int j, int[][] memo) {
        // BASE CASE
        if (i == 0 || j == 0) return 0;
        
        // CHECK MEMO
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        
        // CALCULATE
        if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
            memo[i][j] = 1 + lcsMemoHelper(text1, text2, i - 1, j - 1, memo);
        } else {
            int skip1 = lcsMemoHelper(text1, text2, i - 1, j, memo);
            int skip2 = lcsMemoHelper(text1, text2, i, j - 1, memo);
            memo[i][j] = Math.max(skip1, skip2);
        }
        
        return memo[i][j];
    }
    
    
    // ================================================================
    //        APPROACH 3: TABULATION (Bottom-Up DP) - BEST!
    // ================================================================
    // Time: O(m × n)
    // Space: O(m × n)
    
    public static int lcsTabulation(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        
        // CREATE DP TABLE
        // dp[i][j] = LCS length of text1[0..i-1] and text2[0..j-1]
        int[][] dp = new int[m + 1][n + 1];
        
        // BASE CASE: dp[0][j] = 0 and dp[i][0] = 0
        // Already initialized to 0 by default
        
        System.out.println("Building DP table:");
        System.out.println("text1 = \"" + text1 + "\"");
        System.out.println("text2 = \"" + text2 + "\"");
        System.out.println();
        
        // FILL DP TABLE
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char c1 = text1.charAt(i - 1);
                char c2 = text2.charAt(j - 1);
                
                if (c1 == c2) {
                    // Characters match → include in LCS
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                    System.out.printf("dp[%d][%d]: '%c'=='%c' → 1 + dp[%d][%d] = %d%n",
                                    i, j, c1, c2, i-1, j-1, dp[i][j]);
                } else {
                    // Characters don't match → take max
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    System.out.printf("dp[%d][%d]: '%c'!='%c' → max(dp[%d][%d]=%d, dp[%d][%d]=%d) = %d%n",
                                    i, j, c1, c2, i-1, j, dp[i-1][j], 
                                    i, j-1, dp[i][j-1], dp[i][j]);
                }
            }
        }
        
        System.out.println("\nFinal DP Table:");
        printDPTable(dp, text1, text2);
        
        return dp[m][n];
    }
    
    
    // Helper: Print DP table beautifully
    private static void printDPTable(int[][] dp, String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        
        // Print header
        System.out.print("      ");
        for (int j = 0; j < n; j++) {
            System.out.printf("%3c", text2.charAt(j));
        }
        System.out.println();
        
        // Print table
        for (int i = 0; i <= m; i++) {
            if (i == 0) {
                System.out.print("  ");
            } else {
                System.out.printf("%c ", text1.charAt(i - 1));
            }
            
            for (int j = 0; j <= n; j++) {
                System.out.printf("%3d", dp[i][j]);
            }
            System.out.println();
        }
    }
    
    
    // ================================================================
    //        BONUS: RECONSTRUCT THE ACTUAL LCS STRING
    // ================================================================
    
    public static String reconstructLCS(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        
        // Build DP table first
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        
        // BACKTRACK to build LCS string
        StringBuilder lcs = new StringBuilder();
        int i = m, j = n;
        
        while (i > 0 && j > 0) {
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                // Characters match → part of LCS
                lcs.append(text1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                // Move up
                i--;
            } else {
                // Move left
                j--;
            }
        }
        
        // Reverse (we built backwards)
        return lcs.reverse().toString();
    }
    
    
    // ================================================================
    //                         MAIN METHOD
    // ================================================================
    
    public static void main(String[] args) {
        String text1 = "abcde";
        String text2 = "ace";
        
        System.out.println("=".repeat(70));
        System.out.println("LONGEST COMMON SUBSEQUENCE (LCS)");
        System.out.println("=".repeat(70));
        System.out.println("Text 1: \"" + text1 + "\"");
        System.out.println("Text 2: \"" + text2 + "\"");
        System.out.println("=".repeat(70));
        System.out.println();
        
        // Approach 1: Recursion
        System.out.println("APPROACH 1: PURE RECURSION");
        System.out.println("-".repeat(70));
        int result1 = lcsRecursion(text1, text2, text1.length(), text2.length());
        System.out.println("LCS Length: " + result1);
        System.out.println();
        
        // Approach 2: Memoization
        System.out.println("APPROACH 2: MEMOIZATION");
        System.out.println("-".repeat(70));
        int result2 = lcsMemo(text1, text2);
        System.out.println("LCS Length: " + result2);
        System.out.println();
        
        // Approach 3: Tabulation
        System.out.println("APPROACH 3: TABULATION (Bottom-Up DP)");
        System.out.println("-".repeat(70));
        int result3 = lcsTabulation(text1, text2);
        System.out.println("\nLCS Length: " + result3);
        System.out.println();
        
        // Bonus: Reconstruct LCS
        System.out.println("BONUS: RECONSTRUCT LCS STRING");
        System.out.println("-".repeat(70));
        String lcsString = reconstructLCS(text1, text2);
        System.out.println("LCS: \"" + lcsString + "\"");
        
        System.out.println("\n" + "=".repeat(70));
    }
}


/*
 * ================================================================
 *                    DETAILED WALKTHROUGH
 * ================================================================
 * 
 * EXAMPLE: text1 = "abcde", text2 = "ace"
 * 
 * BUILDING DP TABLE:
 * ------------------
 * 
 *         ""  a  c  e
 *     ""   0  0  0  0
 *     a    0  1  1  1
 *     b    0  1  1  1
 *     c    0  1  2  2
 *     d    0  1  2  2
 *     e    0  1  2  3
 * 
 * Step-by-step:
 * 
 * dp[1][1]: text1[0]='a', text2[0]='a' → Match!
 *           dp[1][1] = 1 + dp[0][0] = 1 + 0 = 1
 * 
 * dp[1][2]: text1[0]='a', text2[1]='c' → No match
 *           dp[1][2] = max(dp[0][2], dp[1][1]) = max(0, 1) = 1