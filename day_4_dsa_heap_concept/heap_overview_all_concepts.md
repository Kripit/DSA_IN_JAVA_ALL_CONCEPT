# MODULE 6: HEAP / PRIORITY QUEUE

## 🎯 WHAT IS A HEAP?

**Simple Definition:** A **tree-based** data structure where:
- **Min Heap:** Parent smaller than children (smallest at top)
- **Max Heap:** Parent larger than children (largest at top)

**Real Life Example:**
Think of a **priority line at hospital**:
- Critical patients (high priority) go first
- Not first-come-first-serve
- Always serve HIGHEST priority next

**That's exactly what a heap does!**

---

## 💡 HEAP vs ARRAY vs SORTED ARRAY

| Operation | Array | Sorted Array | Heap |
|-----------|-------|--------------|------|
| Insert | O(1) | O(n) | O(log n) ✓ |
| Find Min/Max | O(n) | O(1) | O(1) ✓ |
| Delete Min/Max | O(n) | O(n) | O(log n) ✓ |
| Search | O(n) | O(log n) | O(n) |

**Heap wins for:** Insert + Delete Min/Max operations!

---

## 🏗️ HEAP STRUCTURE (How it looks)

### **Visual Representation:**

```
Max Heap Example:
         50
       /    \
      30    40
     /  \   /
    10  20 35

Array: [50, 30, 40, 10, 20, 35]
Index:  0   1   2   3   4   5
```

**Key Property (Max Heap):**
- `arr[i] >= arr[2*i + 1]` (left child)
- `arr[i] >= arr[2*i + 2]` (right child)

**Parent-Child Relationship:**
- Parent of index `i`: `(i-1)/2`
- Left child of index `i`: `2*i + 1`
- Right child of index `i`: `2*i + 2`

---

## 🔑 TWO TYPES OF HEAPS

### **1. MIN HEAP (Smallest at top)**
```
     5
   /   \
  10   15
 / \   /
20 30 40

Use: Find smallest element quickly
```

### **2. MAX HEAP (Largest at top)**
```
     40
   /   \
  30   20
 / \   /
15 10 5

Use: Find largest element quickly
```

---

## 💻 PRIORITY QUEUE (Java's Heap Implementation)

**PriorityQueue in Java:**
- Implements Min Heap by default
- Auto-maintains heap property
- No need to implement from scratch!

```java
// Min Heap (default)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// Max Heap (reverse order)
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
```

**Common Operations:**
```java
pq.add(element);      // O(log n) - Insert
pq.peek();            // O(1) - Get top (don't remove)
pq.poll();            // O(log n) - Remove top
pq.size();            // O(1) - Get size
pq.isEmpty();         // O(1) - Check empty
```

---

## 🎯 WHEN TO USE HEAP?

✅ **Use Heap when you see:**
1. "Find **Kth largest/smallest**"
2. "**Top K** elements"
3. "**Median** from stream"
4. "Merge **K sorted** lists/arrays"
5. "**Running minimum/maximum**"
6. "**Frequency** based problems"

❌ **Don't use Heap when:**
- Need ALL elements sorted (use sorting)
- Need random access (use array)
- Simple max/min of entire array (just iterate)

---

## 🔥 CORE HEAP OPERATIONS (How it works internally)

### **1. HEAPIFY UP (After Insert)**
```
Insert 50 in Min Heap:
     10              10              10
    /  \    add→    /  \    up→     /  \
   20  30          20  30          20  30
                   /               /
                  50              50

Then bubble up if needed (not here, 50 > 20)
```

### **2. HEAPIFY DOWN (After Delete)**
```
Remove 10 (root) from Min Heap:
     10              30              20
    /  \   remove→  /  \   down→    /  \
   20  30          20  30          30  30
   
Replace with last, then sink down
```

---

## 📊 HEAP PROBLEM PATTERNS

### **Pattern 1: Top K Elements**
- Find K largest/smallest
- Maintain heap of size K
- **Time:** O(n log k)

### **Pattern 2: K-Way Merge**
- Merge K sorted arrays
- Use heap to track smallest from each
- **Time:** O(n log k)

### **Pattern 3: Running Median**
- Two heaps: max heap (left), min heap (right)
- Balance sizes
- **Time:** O(log n) per insert

### **Pattern 4: Frequency Problems**
- Count frequencies → Put in heap
- Get top K frequent
- **Time:** O(n log k)

---

## 🎯 PROBLEMS WE'LL SOLVE

### **SIMPLE (Foundation)**
**Problem 1: Kth Largest Element**
- Find Kth largest in unsorted array
- **Pattern:** Min heap of size K
- **Teaches:** Basic heap usage

---

### **MEDIUM (Interview Ready)**
**Problem 2: Top K Frequent Elements**
- Find K most frequent numbers
- **Pattern:** HashMap + Heap
- **Teaches:** Frequency + heap combo

---

### **HARD (Top Companies)**
**Problem 3: Median from Data Stream**
- Find median as numbers arrive
- **Pattern:** Two heaps (max + min)
- **Teaches:** Advanced heap technique

---

## 💡 HEAP vs OTHER DATA STRUCTURES

| Need | Use | Why |
|------|-----|-----|
| Always sorted | Sorted Array | O(1) access |
| Top K elements | Heap | O(log k) operations |
| All K elements in order | Sorting | O(n log n) |
| Frequent updates | Heap | O(log n) insert/delete |
| Single max/min | Variable | O(1) space |

---

## 🔥 COMPLEXITY CHEAT SHEET

| Operation | Time | Explanation |
|-----------|------|-------------|
| Build Heap | O(n) | Heapify all elements |
| Insert | O(log n) | Bubble up |
| Delete | O(log n) | Bubble down |
| Peek | O(1) | Just access root |
| Search | O(n) | No order except parent-child |

---

## 💪 WHY HEAP IS CRITICAL FOR INTERVIEWS

**Interview Statistics:**
- 25% of Amazon questions use heaps
- 15% of Google questions
- Top K = Most common pattern
- Always asked in system design (task scheduling)

**Real Applications:**
- **Task Scheduling:** OS task priority
- **Dijkstra's Algorithm:** Shortest path
- **Load Balancing:** Assign to least loaded server
- **Data Compression:** Huffman coding
- **Event Simulation:** Process events by time

---

## 🎯 HEAP MASTERY CHECKLIST

**After this module, you'll:**
- ✅ Understand heap structure deeply
- ✅ Use Java's PriorityQueue like a pro
- ✅ Solve Top K problems instantly
- ✅ Implement two-heap technique
- ✅ Recognize heap patterns immediately
- ✅ Optimize from O(n log n) to O(n log k)

---

## 🚀 LEARNING PATH

```
LEVEL 1: Basic Heap Usage
├─ Kth Largest Element (Simple)
└─ Understand add/poll/peek

LEVEL 2: Heap + Other DS
├─ Top K Frequent (Medium)
└─ Combine HashMap + Heap

LEVEL 3: Advanced Patterns
├─ Median from Stream (Hard)
└─ Two-heap technique
```

---

## 📝 IMPORTANT JAVA SYNTAX

### **Creating Heaps:**
```java
// Min Heap (default)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// Max Heap
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

// Custom Comparator (for objects)
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
```

### **Common Operations:**
```java
pq.add(5);           // Insert 5
pq.offer(5);         // Insert 5 (preferred)
int top = pq.peek(); // Get top (don't remove)
int removed = pq.poll(); // Remove and return top
int size = pq.size();
boolean empty = pq.isEmpty();
```

### **Custom Objects:**
```java
class Task {
    int priority;
    String name;
}

PriorityQueue<Task> pq = new PriorityQueue<>((a, b) -> b.priority - a.priority);
```

---

## 🔥 READY TO START?

**Next:** Problem 1 - Kth Largest Element

I'll teach you:
- Min heap vs Max heap approach
- When to use which
- Optimization tricks
- Full dry run with visualization
- Amazon interview tips

Type: **"Problem 1"** to begin! 🚀

**Remember:** Heap is THE KEY to cracking Amazon interviews! Master this and you're 70% ready! 💪