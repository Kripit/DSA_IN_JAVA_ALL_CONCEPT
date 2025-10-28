# MODULE 5: DYNAMIC PROGRAMMING (DP)

##  WHAT IS DYNAMIC PROGRAMMING?

**Simple Definition:** DP = Recursion + Memoization (storing results)

**Real Life Example:**
You're climbing stairs. To reach step 5:
- Method 1 (Recursion): Calculate ways to reach step 4, then step 3, then step 2... REPEAT work!
- Method 2 (DP): Calculate once, REMEMBER answer, reuse it!

**The Magic:** Turn O(2^n) into O(n)!

---

##  CORE CONCEPTS (Must understand!)

### **1. Overlapping Subproblems**
Same problem calculated MULTIPLE times.

Example: Fibonacci
```
fib(5) = fib(4) + fib(3)
fib(4) = fib(3) + fib(2)
       → fib(3) calculated TWICE! ← Waste!
```

**DP Solution:** Calculate fib(3) once, SAVE it, REUSE it!

---

### **2. Optimal Substructure**
Optimal solution built from optimal solutions of subproblems.

Example: Shortest path A→C
```
If shortest A→B→C, then:
- A→B must be shortest
- B→C must be shortest
```

---

### **3. Two Approaches**

#### **Top-Down (Memoization)**
- Start from BIG problem
- Break into SMALLER problems
- STORE results in memo array/HashMap
- Use RECURSION

```java
int fib(int n, int[] memo) {
    if (memo[n] != -1) return memo[n];  // Already calculated
    memo[n] = fib(n-1, memo) + fib(n-2, memo);  // Calculate & store
    return memo[n];
}
```

#### **Bottom-Up (Tabulation)**
- Start from SMALLEST problem
- Build up to BIG problem
- Use ARRAY (dp table)
- Use LOOPS (no recursion)

```java
int fib(int n) {
    int[] dp = new int[n+1];
    dp[0] = 0;
    dp[1] = 1;
    for (int i = 2; i <= n; i++) {
        dp[i] = dp[i-1] + dp[i-2];  // Build from smaller
    }
    return dp[n];
}
```

---

##  WHEN TO USE DP?

 **Use DP when you see:**
1. "Find optimal" (minimum, maximum, longest, shortest)
2. "Count ways" (how many ways, total combinations)
3. Problem can be broken into SMALLER similar problems
4. Choices at each step (take/don't take)

 **Don't use DP when:**
- Problem has NO overlapping subproblems
- Greedy works (simpler!)
- Problem needs actual paths (use backtracking)

---

##  DP PATTERNS (Master these!)

### **Pattern 1: 1D DP**
- Single array: dp[i]
- Examples: Fibonacci, Climbing Stairs, House Robber
- Recurrence: dp[i] = f(dp[i-1], dp[i-2], ...)

### **Pattern 2: 2D DP**
- 2D array: dp[i][j]
- Examples: Longest Common Subsequence, Edit Distance
- Recurrence: dp[i][j] = f(dp[i-1][j], dp[i][j-1], dp[i-1][j-1])

### **Pattern 3: Knapsack Pattern**
- 2D: dp[item][capacity]
- Examples: 0/1 Knapsack, Coin Change
- Include/Exclude decision

### **Pattern 4: DP on Strings**
- Compare two strings
- Examples: Edit Distance, Longest Common Substring
- Often 2D DP

### **Pattern 5: DP on Grids**
- Navigate grid: dp[row][col]
- Examples: Unique Paths, Minimum Path Sum
- Move right/down decisions

---

##  THE DP FRAMEWORK (Follow these steps!)

### **Step 1: Define State**
What does dp[i] or dp[i][j] represent?

Example: "dp[i] = number of ways to reach stair i"

### **Step 2: Find Recurrence Relation**
How to calculate dp[i] from previous states?

Example: "dp[i] = dp[i-1] + dp[i-2]"

### **Step 3: Initialize Base Cases**
What are the starting values?

Example: "dp[0] = 1, dp[1] = 1"

### **Step 4: Determine Order**
In what order to fill the dp array?

Example: "Left to right (i = 0 to n)"

### **Step 5: Find Answer**
Where is the final answer?

Example: "dp[n]"

---

##  COMPLEXITY COMPARISON

| Problem | Recursion | DP | Improvement |
|---------|-----------|-----|-------------|
| Fibonacci | O(2^n) | O(n) | Exponential → Linear! |
| 0/1 Knapsack | O(2^n) | O(n×W) | Exponential → Polynomial! |
| LCS | O(2^(m+n)) | O(m×n) | Exponential → Polynomial! |

**Space Optimization:** Often O(n²) → O(n) by using rolling arrays!

---

##  PROBLEMS WE'LL SOLVE

### **SIMPLE (Foundation)**
**Problem 1: Climbing Stairs**
- How many ways to reach top?
- Can take 1 or 2 steps at a time
- **Pattern:** 1D DP
- **Teaches:** Basic recurrence relation

---

### **MEDIUM (Interview Ready)**
**Problem 2: House Robber**
- Rob houses to maximize money
- Can't rob adjacent houses
- **Pattern:** 1D DP with constraint
- **Teaches:** Include/Exclude decision

---

### **HARD (Top Companies)**
**Problem 3: Longest Common Subsequence (LCS)**
- Find longest subsequence in two strings
- **Pattern:** 2D DP on strings
- **Teaches:** Multi-dimensional DP, string matching

---

##  DP VS OTHER TECHNIQUES

| Technique | When to Use | Example |
|-----------|-------------|---------|
| **Greedy** | Local optimal = Global | Activity Selection |
| **Divide & Conquer** | Independent subproblems | Merge Sort |
| **Backtracking** | Need all solutions | N-Queens |
| **DP** | Overlapping subproblems + Optimal substructure | Fibonacci, Knapsack |

---

##  TOP COMPANIES' FAVORITE DP PROBLEMS

### **Amazon:**
- Climbing Stairs
- House Robber
- Coin Change
- Longest Increasing Subsequence

### **Google:**
- Edit Distance
- Longest Common Subsequence
- Word Break
- Regular Expression Matching

### **Microsoft:**
- Maximum Subarray
- Unique Paths
- Minimum Path Sum
- Best Time to Buy/Sell Stock

---

##  DP LEARNING PATH

```
LEVEL 1: 1D DP (Weeks 1-2)
├─ Climbing Stairs (Simple)
├─ House Robber (Medium)
├─ Coin Change (Medium)
└─ Longest Increasing Subsequence (Medium)

LEVEL 2: 2D DP (Weeks 3-4)
├─ Unique Paths (Simple)
├─ Longest Common Subsequence (Medium)
├─ Edit Distance (Hard)
└─ 0/1 Knapsack (Hard)

LEVEL 3: Advanced (Week 5+)
├─ DP on Trees
├─ DP with Bitmasking
├─ Matrix Chain Multiplication
└─ Palindrome Partitioning
```

---

##  WHY DP IS CRUCIAL

**Interview Statistics:**
- 40% of Medium/Hard problems use DP
- Top companies ask 2-3 DP questions per interview
- Salary impact: DP mastery = +20 LPA easily

**Real Applications:**
- Google Maps: Shortest path (Dijkstra + DP)
- Spell checker: Edit distance
- DNA sequencing: LCS
- Resource allocation: Knapsack
- Stock trading algorithms: DP on stocks

---

##  AFTER THIS MODULE, YOU'LL:

✅ Identify DP problems instantly  
✅ Write recurrence relations confidently  
✅ Convert recursion to DP smoothly  
✅ Optimize space from O(n²) to O(n)  
✅ Solve 90% of DP interview questions  

---

##  READY TO START?

**Next:** Problem 1 - Climbing Stairs (Foundation of ALL DP!)

I'll teach you:
- The recurrence relation (HOW to think)
- Recursion → Memoization → Tabulation (3 approaches)
- Space optimization (O(n) → O(1))
- Full dry run with examples

