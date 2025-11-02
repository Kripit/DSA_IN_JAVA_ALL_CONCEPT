# MODULE 7: GRAPHS - FROM ABSOLUTE ZERO TO HERO!

## 🎯 WHAT IS A GRAPH?

**Simple Definition:** A graph is a collection of **nodes (vertices)** connected by **edges (lines)**.

**Real Life Examples:**
1. **Social Network:**
   - Nodes = People
   - Edges = Friendship connections
   - Facebook, LinkedIn use graphs!

2. **Google Maps:**
   - Nodes = Cities/Intersections
   - Edges = Roads
   - Find shortest path = Graph algorithm!

3. **Website Links:**
   - Nodes = Web pages
   - Edges = Hyperlinks
   - Google Search = Graph traversal!

---

## 📊 GRAPH TERMINOLOGY (Must Know!)

### **1. VERTEX (NODE):**
Individual point in graph
```
    A ← Vertex/Node
```

### **2. EDGE:**
Connection between two vertices
```
A --- B
   ↑
  Edge
```

### **3. DIRECTED vs UNDIRECTED:**

**UNDIRECTED:** Two-way connection
```
A --- B  (A can go to B, B can go to A)
```

**DIRECTED:** One-way connection (arrow)
```
A → B  (A can go to B, but B cannot go to A)
```

### **4. WEIGHTED vs UNWEIGHTED:**

**UNWEIGHTED:** All edges same cost
```
A --- B --- C  (each edge = 1)
```

**WEIGHTED:** Edges have different costs
```
A --5-- B --3-- C  (edge weights shown)
```

### **5. DEGREE:**
Number of edges connected to a vertex

**In Undirected Graph:**
```
    B
   / \
  A   C
Degree of B = 2 (connected to A and C)
```

**In Directed Graph:**
```
A → B → C
In-degree of B = 1 (one incoming edge)
Out-degree of B = 1 (one outgoing edge)
```

### **6. PATH:**
Sequence of vertices connected by edges
```
A → B → C → D
Path: A to D
Length: 3 edges
```

### **7. CYCLE:**
Path that starts and ends at same vertex
```
A → B → C → A
This is a cycle!
```

### **8. CONNECTED GRAPH:**
Every vertex reachable from every other vertex

**Connected:**
```
A --- B
|     |
C --- D
All connected!
```

**NOT Connected:**
```
A --- B    C --- D
Disconnected!
```

---

## 🏗️ GRAPH REPRESENTATION (Two Ways!)

### **METHOD 1: ADJACENCY MATRIX**

**What is it?**
2D array where `matrix[i][j] = 1` means edge from i to j

**Example:**
```
Graph:
0 --- 1
|     |
2 --- 3

Adjacency Matrix:
    0  1  2  3
0 [ 0  1  1  0 ]
1 [ 1  0  0  1 ]
2 [ 1  0  0  1 ]
3 [ 0  1  1  0 ]

matrix[0][1] = 1 means edge between 0 and 1
matrix[0][3] = 0 means NO edge between 0 and 3
```

**In Java:**
```java
int[][] graph = new int[4][4];
graph[0][1] = 1; // Edge from 0 to 1
graph[1][0] = 1; // Edge from 1 to 0 (undirected)
```

**Pros:**
✓ Check if edge exists: O(1) - just access matrix[i][j]
✓ Simple to implement

**Cons:**
✗ Space: O(V²) - even if few edges
✗ Iterate all neighbors: O(V) - check entire row

**When to use:** Dense graphs (many edges)

---

### **METHOD 2: ADJACENCY LIST** ← MOST COMMON!

**What is it?**
Array of lists where `list[i]` contains all neighbors of vertex i

**Example:**
```
Graph:
0 --- 1
|     |
2 --- 3

Adjacency List:
0: [1, 2]     (0 connected to 1 and 2)
1: [0, 3]     (1 connected to 0 and 3)
2: [0, 3]     (2 connected to 0 and 3)
3: [1, 2]     (3 connected to 1 and 2)
```

**In Java:**
```java
// Method 1: Array of ArrayList
ArrayList<Integer>[] graph = new ArrayList[4];
for (int i = 0; i < 4; i++) {
    graph[i] = new ArrayList<>();
}
graph[0].add(1); // Edge from 0 to 1
graph[0].add(2); // Edge from 0 to 2

// Method 2: HashMap (if vertices are not 0,1,2...)
HashMap<Integer, List<Integer>> graph = new HashMap<>();
graph.put(0, new ArrayList<>());
graph.get(0).add(1);
```

**Pros:**
✓ Space: O(V + E) - only store existing edges!
✓ Iterate neighbors: O(degree) - fast!
✓ Most commonly used!

**Cons:**
✗ Check if edge exists: O(degree) - must search list

**When to use:** Sparse graphs (few edges) ← MOST REAL GRAPHS!

---

## 🔍 GRAPH TRAVERSAL (Two Main Methods!)

### **1. DFS (Depth First Search)**
Go as DEEP as possible before backtracking

**Think of it:** Exploring a maze, go down one path completely before trying another

**Order:** Deep first!
```
    1
   / \
  2   3
 / \
4   5

DFS: 1 → 2 → 4 → 5 → 3
(Go deep on left, then backtrack and go right)
```

**Implementation:** Use STACK (or recursion)

---

### **2. BFS (Breadth First Search)**
Explore all neighbors at current level before going deeper

**Think of it:** Ripple in water, expanding outward in layers

**Order:** Layer by layer!
```
    1       ← Level 0
   / \
  2   3     ← Level 1
 / \
4   5       ← Level 2

BFS: 1 → 2 → 3 → 4 → 5
(Complete each level before going deeper)
```

**Implementation:** Use QUEUE

---

## 📊 DFS vs BFS COMPARISON

| Aspect | DFS | BFS |
|--------|-----|-----|
| Data Structure | Stack (or recursion) | Queue |
| Memory | O(height) | O(width) |
| Use For | Cycles, Paths, Connectivity | Shortest path, Level-wise |
| Implementation | Easier (recursion) | Need Queue |
| When Path Needed | Not optimal | Optimal (shortest) |

---

## 🎯 COMMON GRAPH PROBLEMS

### **Problem Types:**

1. **Traversal:**
   - Visit all vertices
   - DFS or BFS

2. **Connectivity:**
   - Are all vertices connected?
   - Number of connected components
   - DFS or BFS

3. **Cycle Detection:**
   - Is there a cycle?
   - DFS with visited tracking

4. **Shortest Path:**
   - Shortest path between two vertices
   - BFS (unweighted) or Dijkstra (weighted)

5. **Topological Sort:**
   - Order tasks with dependencies
   - DFS or BFS (Kahn's algorithm)

---

## 💻 JAVA COLLECTIONS FOR GRAPHS

### **1. ArrayList<Integer>**
Dynamic array, grows automatically

```java
ArrayList<Integer> list = new ArrayList<>();
list.add(5);              // Add element - O(1) amortized
list.get(0);              // Access by index - O(1)
list.remove(0);           // Remove by index - O(n)
list.size();              // Get size - O(1)
list.contains(5);         // Check if contains - O(n)
list.clear();             // Remove all - O(n)
```

**For graphs:** Store neighbors of each vertex

---

### **2. LinkedList<Integer>** (Also implements Queue!)
Doubly linked list

```java
LinkedList<Integer> list = new LinkedList<>();
list.add(5);              // Add at end - O(1)
list.addFirst(3);         // Add at start - O(1)
list.addLast(7);          // Add at end - O(1)
list.removeFirst();       // Remove from start - O(1)
list.removeLast();        // Remove from end - O(1)
```

**For graphs:** Used as Queue in BFS

---

### **3. Queue<Integer>** (Interface)
FIFO (First In First Out)

```java
Queue<Integer> queue = new LinkedList<>();
queue.offer(5);           // Add element - O(1)
queue.poll();             // Remove and return front - O(1)
queue.peek();             // Look at front (don't remove) - O(1)
queue.isEmpty();          // Check if empty - O(1)
queue.size();             // Get size - O(1)
```

**For graphs:** BFS traversal

---

### **4. Stack<Integer>**
LIFO (Last In First Out)

```java
Stack<Integer> stack = new Stack<>();
stack.push(5);            // Add on top - O(1)
stack.pop();              // Remove from top - O(1)
stack.peek();             // Look at top (don't remove) - O(1)
stack.isEmpty();          // Check if empty - O(1)
stack.size();             // Get size - O(1)
```

**For graphs:** DFS iterative implementation

---

### **5. HashSet<Integer>**
Stores unique elements, no duplicates

```java
HashSet<Integer> set = new HashSet<>();
set.add(5);               // Add element - O(1)
set.remove(5);            // Remove element - O(1)
set.contains(5);          // Check if contains - O(1)
set.size();               // Get size - O(1)
set.clear();              // Remove all - O(n)
```

**For graphs:** Track visited vertices

---

### **6. HashMap<Integer, List<Integer>>**
Key-value pairs

```java
HashMap<Integer, List<Integer>> map = new HashMap<>();
map.put(0, new ArrayList<>());     // Add key-value - O(1)
map.get(0);                        // Get value by key - O(1)
map.containsKey(0);                // Check if key exists - O(1)
map.keySet();                      // Get all keys - O(1)
map.values();                      // Get all values - O(1)
```

**For graphs:** Adjacency list when vertices aren't 0,1,2...

---

## 🎯 PROBLEMS WE'LL SOLVE

### **SIMPLE (Foundation):**
**Problem 1: Number of Islands (DFS)**
- 2D grid, find connected components
- Classic DFS problem
- Teaches: DFS on grid, visited tracking

---

### **MEDIUM (Interview Ready):**
**Problem 2: Course Schedule (Cycle Detection + Topological Sort)**
- Detect cycle in directed graph
- Topological sort using BFS
- Teaches: Directed graph, cycle detection, Kahn's algorithm

---

### **HARD (Top Companies):**
**Problem 3: Word Ladder (BFS Shortest Path)**
- Transform one word to another
- Minimum steps needed
- Teaches: BFS for shortest path, graph modeling

---

## 💪 WHY GRAPHS ARE CRITICAL

**Interview Statistics:**
- 40% of hard problems involve graphs
- Amazon asks 2-3 graph questions per interview
- Google loves graph problems
- Required for system design rounds

**Real Applications:**
- **Social Networks:** Friend recommendations
- **Maps:** GPS navigation
- **Web:** Page ranking (Google)
- **Networks:** Routing algorithms
- **Games:** Pathfinding AI
- **Databases:** Query optimization

---

## 🚀 LEARNING PATH

```
LEVEL 1: Build Graph (Adjacency List)
↓
LEVEL 2: DFS Traversal
↓
LEVEL 3: BFS Traversal
↓
LEVEL 4: Cycle Detection
↓
LEVEL 5: Shortest Path
↓
LEVEL 6: Topological Sort
```

---

## 🎯 READY TO START?

**Next: Problem 1 - Number of Islands (DFS Foundation)**

I'll teach you:
- How to represent graph as adjacency list
- DFS implementation (recursive & iterative)
- Visited tracking with boolean array
- Grid traversal
- Full dry run step by step
- Every Java syntax explained!

Type: **"Problem 1"** to begin! 🔥

**Remember:** Graphs might seem hard, but I'll break it down so simply that you'll become an EXPERT! Trust the process! 💪