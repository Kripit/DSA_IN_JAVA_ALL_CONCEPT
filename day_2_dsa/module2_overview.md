# MODULE 2: TWO POINTER TECHNIQUE

## 📖 TABLE OF CONTENTS
1. What is Two Pointer Technique?
2. When to Use Two Pointers?
3. Types of Two Pointer Patterns
4. Mathematical Theory
5. Time Complexity Analysis
6. Problems (Simple → Medium → Job-Ready)

---

## 1️⃣ WHAT IS TWO POINTER TECHNIQUE?

**Definition:** Using TWO variables (pointers) to traverse an array/string, instead of nested loops.

**Real Life Analogy:**
Imagine you're searching for a pair of shoes in a line:
- **Method 1 (Brute Force):** Check EVERY shoe with EVERY other shoe (nested loops)
- **Method 2 (Two Pointers):** One person starts from LEFT, one from RIGHT, meet in middle

**Visual Representation:**
```
Array: [1, 2, 3, 4, 5, 6, 7, 8]
        ↑                    ↑
       LEFT               RIGHT

Move pointers based on condition!
```

---

## 2️⃣ WHEN TO USE TWO POINTERS?

✅ **Use Two Pointers when:**
1. Array/String is **SORTED** (or can be sorted)
2. Need to find **PAIR** of elements
3. Need to find **SUBARRAY** with certain property
4. Optimizing from O(n²) to O(n)

❌ **Don't use when:**
- Array is NOT sorted and sorting changes answer
- Need to check ALL pairs without any elimination logic

---

## 3️⃣ TYPES OF TWO POINTER PATTERNS

### **Pattern 1: Opposite Direction (Left & Right)**
```
[1, 2, 3, 4, 5, 6]
 ↑              ↑
 L              R

Move based on condition:
- If sum too small → L++
- If sum too large → R--
```

**Use Cases:**
- Two Sum (sorted array)
- Container with most water
- Valid palindrome

---

### **Pattern 2: Same Direction (Slow & Fast)**
```
[1, 2, 3, 4, 5, 6]
 ↑  ↑
 S  F

Both move forward, but at different speeds
```

**Use Cases:**
- Remove duplicates
- Move zeros
- Partition array

---

### **Pattern 3: Sliding Window (Special case)**
```
[1, 2, 3, 4, 5, 6]
    [----]
    L    R

Window slides forward maintaining size or condition
```

**Use Cases:**
- Maximum sum subarray
- Longest substring problems
(We covered this in Module 1!)

---

## 4️⃣ MATHEMATICAL THEORY

### **Why Two Pointers is O(n)?**

**Brute Force (Nested Loops):**
```
for (int i = 0; i < n; i++) {        // Runs n times
    for (int j = i+1; j < n; j++) {  // Runs (n-1) + (n-2) + ... + 1
        // Check pair (i, j)
    }
}
```

**Total operations:** 
```
(n-1) + (n-2) + (n-3) + ... + 2 + 1
= n(n-1)/2  (Sum of first n natural numbers formula)
= (n² - n)/2
≈ O(n²)  (ignore constants and lower terms)
```

**Two Pointers:**
```
left = 0, right = n-1
while (left < right) {
    // Check pair (left, right)
    // Move one pointer
}
```

**Total operations:**
- Each pointer moves at most n times
- Total: n + n = 2n = O(n)

**Improvement:** O(n²) → O(n) = **100x faster for n=100, 10000x faster for n=1000!**

---

### **Mathematical Proof (Two Sum Problem)**

**Problem:** Find two numbers that sum to target in SORTED array.

**Claim:** If current sum > target, we can ELIMINATE right element.

**Proof:**
- Let arr[left] + arr[right] > target
- For any index i where left ≤ i < right:
  - arr[i] + arr[right] ≥ arr[left] + arr[right] > target (array is sorted)
- Therefore, arr[right] cannot be part of solution
- Safe to eliminate (move right--)

**This is why sorting + two pointers works!**

---

## 5️⃣ TIME COMPLEXITY COMPARISON

| Approach | Time Complexity | Space Complexity | When to Use |
|----------|----------------|------------------|-------------|
| Brute Force | O(n²) | O(1) | Small arrays (n < 100) |
| Two Pointers | O(n) | O(1) | After sorting, for pairs |
| Sorting + Two Pointers | O(n log n) | O(1) | When sorting allowed |
| HashMap | O(n) | O(n) | When sorting NOT allowed |

**Note:** O(n log n) comes from sorting, then O(n) for two pointers.

---

## 6️⃣ PROBLEM DIFFICULTY LEVELS

### **SIMPLE (Foundation Building)**
1. Two Sum (Sorted Array)
2. Remove Duplicates from Sorted Array
3. Reverse String

### **MEDIUM (Interview Ready)**
1. 3Sum Problem
2. Container With Most Water
3. Trapping Rain Water

### **ADVANCED (90 LPA+ Companies)**
1. 4Sum Problem
2. Subarray Product Less Than K
3. Minimum Window Substring

---

## 🎯 PATTERN RECOGNITION GUIDE

**See these keywords? Think Two Pointers!**

| Keyword | Pattern Type | Approach |
|---------|-------------|----------|
| "Sorted array" + "pair" | Opposite Direction | Left & Right pointers |
| "Remove duplicates" | Same Direction | Slow & Fast pointers |
| "Subarray with sum/product" | Sliding Window | Left & Right (variable window) |
| "Palindrome" | Opposite Direction | Start from edges, move inward |
| "Move/partition elements" | Same Direction | Slow = boundary, Fast = scanner |

---

## 📈 LEARNING PATH

```
Module 2.1: Simple Problems (Foundation)
   ↓
Module 2.2: Medium Problems (Interview Prep)
   ↓
Module 2.3: Advanced Problems (Top Companies)
```

**After this module, you'll:**
- ✅ Recognize when to use two pointers
- ✅ Solve 80% of array/string pairing problems
- ✅ Optimize O(n²) solutions to O(n)
- ✅ Handle 90 LPA interview questions confidently

---

