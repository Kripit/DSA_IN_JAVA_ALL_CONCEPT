# JAVA FUNDAMENTALS FOR DSA

## 1. CLASS vs OBJECT

**Class** = Blueprint (design of a house)
**Object** = Actual thing created from blueprint (actual house)

```java
// Class - Blueprint
class Car {
    String color;
    int speed;
}

// Object - Actual car created
Car myCar = new Car();  // This creates an object
myCar.color = "Red";
myCar.speed = 100;
```

**Think of it:**
- Class = Cookie cutter
- Object = Actual cookie made from cutter

---

## 2. CONSTRUCTOR

**Constructor** = Special method that runs when you create an object
- Same name as class
- No return type (not even void)
- Used to initialize object

```java
class Student {
    String name;
    int age;
    
    // Constructor
    Student(String n, int a) {
        name = n;
        age = a;
    }
}

// Usage
Student s1 = new Student("Rahul", 20);
// Constructor runs automatically and sets name="Rahul", age=20
```

---

## 3. this KEYWORD

**this** = Refers to the CURRENT object

```java
class Person {
    String name;
    
    Person(String name) {
        this.name = name;  
        // this.name = object's name variable
        // name = parameter passed to constructor
        // Without 'this', both would be parameter!
    }
}
```

**Why needed?**
When parameter name SAME as variable name, use `this.` to distinguish.

---

## 4. @Override ANNOTATION

**@Override** = Tells Java "I'm replacing parent's method"
- Not mandatory but GOOD PRACTICE
- Helps catch spelling mistakes
- Makes code clear

```java
class Animal {
    void sound() {
        System.out.println("Some sound");
    }
}

class Dog extends Animal {
    @Override  // Replacing parent's sound() method
    void sound() {
        System.out.println("Bark!");
    }
}
```

**toString() Override:**
Every class in Java has toString() method from Object class (default).
We override it to customize how object prints.

```java
class Person {
    String name;
    
    // Without override
    // Person@15db9742 (prints memory address)
    
    @Override
    public String toString() {
        return "Person: " + name;
    }
    // Now prints: Person: Rahul
}
```

---

## 5. ARRAYS vs ARRAYLIST

### ARRAY (Fixed size)
```java
int[] arr = new int[5];  // Size = 5, CANNOT change
arr[0] = 10;
arr[1] = 20;
// arr.length gives size
```

### ARRAYLIST (Dynamic size)
```java
ArrayList<Integer> list = new ArrayList<>();
list.add(10);      // Adds element
list.add(20);      // Can keep adding, size grows automatically
list.get(0);       // Gets element at index 0
list.size();       // Returns current size
list.remove(0);    // Removes element at index 0
```

**When to use what?**
- Array: When size is FIXED (like "10 students")
- ArrayList: When size can CHANGE (like "add students one by one")

---

## 6. INTERFACE

**Interface** = Contract that says "you MUST implement these methods"
- Cannot create objects directly
- Used for common behavior across different classes

```java
// Interface
interface Animal {
    void sound();  // No body, just declaration
}

// Class implementing interface
class Dog implements Animal {
    @Override
    public void sound() {
        System.out.println("Bark");  // MUST provide implementation
    }
}
```

**Comparator Interface:**
Used for custom sorting. Has one method: `compare()`

---

## 7. COMPARATOR (For Custom Sorting)

**Problem:** Arrays.sort() sorts in ascending order (1,2,3...)
**Solution:** Use Comparator to define YOUR OWN sorting logic

```java
// Sorting integers in DESCENDING order
Integer[] arr = {3, 1, 4, 1, 5};

Arrays.sort(arr, new Comparator<Integer>() {
    public int compare(Integer a, Integer b) {
        return b - a;  // Descending (flip a and b)
    }
});
// Result: [5, 4, 3, 1, 1]
```

**How compare() works:**
- Return NEGATIVE → a comes first (a < b)
- Return ZERO → a and b are equal
- Return POSITIVE → b comes first (a > b)

**Examples:**
```java
// Ascending
return a - b;  // If a=5, b=10: returns -5 (negative, so a first)

// Descending
return b - a;  // If a=5, b=10: returns 5 (positive, so b first)
```

---

## 8. LAMBDA EXPRESSION (Short form)

**Lambda** = Shortcut for writing Comparator (Java 8+)

**Long way:**
```java
Arrays.sort(arr, new Comparator<Integer>() {
    public int compare(Integer a, Integer b) {
        return a - b;
    }
});
```

**Short way (Lambda):**
```java
Arrays.sort(arr, (a, b) -> a - b);
```

Both do SAME thing! Lambda is just shorter.

**Format:**
```
(parameters) -> expression
```

---

## 9. COMMON PREBUILT METHODS

### Arrays class
```java
Arrays.sort(arr);              // Sorts array
Arrays.toString(arr);          // Converts to string for printing
Arrays.copyOf(arr, newSize);   // Copies array
```

### String class
```java
String s = "hello";
s.length();           // Returns 5
s.charAt(0);          // Returns 'h'
s.substring(1, 3);    // Returns "el"
s.toUpperCase();      // Returns "HELLO"
```

### Math class
```java
Math.max(5, 10);      // Returns 10
Math.min(5, 10);      // Returns 5
Math.abs(-5);         // Returns 5
Math.pow(2, 3);       // Returns 8.0 (2^3)
```

---

## 10. POLYMORPHISM (Quick concept)

**Polymorphism** = "Many forms"
Same method name, different behavior

**Example:**
```java
class Shape {
    void draw() { }
}

class Circle extends Shape {
    @Override
    void draw() {
        System.out.println("Drawing circle");
    }
}

class Square extends Shape {
    @Override
    void draw() {
        System.out.println("Drawing square");
    }
}

// Same method name (draw), different behavior!
```

---

## 11. THINGS TO REMEMBER

### Variable Naming
```java
int count;           // camelCase for variables
final int MAX = 100; // UPPERCASE for constants
class MyClass { }    // PascalCase for classes
```

### Index in Arrays
```java
int[] arr = {10, 20, 30};
// Index:     0   1   2
// arr[0] = 10
// arr[1] = 20
// arr[2] = 30
// arr.length = 3 (size)
```

### Common Mistakes
```java
// WRONG
for (int i = 0; i <= arr.length; i++)  // <= will give error!

// CORRECT
for (int i = 0; i < arr.length; i++)   // < is correct
```

---

## 12. SCANNER (Taking Input)

```java
Scanner sc = new Scanner(System.in);

int num = sc.nextInt();        // Reads one integer
String word = sc.next();       // Reads one word (till space)
String line = sc.nextLine();   // Reads entire line
double d = sc.nextDouble();    // Reads decimal number

sc.close();  // Always close when done!
```

---

## 13. FOR LOOP TYPES

### Regular for loop
```java
for (int i = 0; i < 5; i++) {
    System.out.println(i);  // Prints 0,1,2,3,4
}
```

### Enhanced for loop (for-each)
```java
int[] arr = {10, 20, 30};
for (int num : arr) {
    System.out.println(num);  // Prints 10, 20, 30
}
// Read as: "for each num in arr"
```

---

## 14. IMPORTANT OPERATORS

```java
// Arithmetic
+  -  *  /  %   // Add, Subtract, Multiply, Divide, Modulo(remainder)

// Comparison (returns true/false)
==  !=  >  <  >=  <=

// Logical
&&  (AND)
||  (OR)
!   (NOT)

// Assignment
=   (assign)
+=  (add and assign: a += 5 means a = a + 5)
-=  *=  /=  (similar)

// Increment/Decrement
i++  (i = i + 1)
i--  (i = i - 1)
```

---

## SUMMARY CHEAT SHEET

| Concept | Quick Meaning |
|---------|---------------|
| `class` | Blueprint for objects |
| `object` | Instance created from class |
| `new` | Creates new object |
| `this` | Refers to current object |
| `@Override` | Replacing parent's method |
| `Interface` | Contract to implement methods |
| `Comparator` | Custom sorting logic |
| `Lambda` | Shortcut syntax |
| `ArrayList` | Dynamic array |
| `Array` | Fixed size collection |
| `.length` | Size of array |
| `.size()` | Size of ArrayList |
| `arr[i]` | Access element at index i |

---

**Remember:** 
- **Class** = Design
- **Object** = Actual thing
- **this** = Me (current object)
- **@Override** = I'm replacing parent's version
- **Comparator** = Custom sorting rules
- **Lambda** = Short way to write Comparator