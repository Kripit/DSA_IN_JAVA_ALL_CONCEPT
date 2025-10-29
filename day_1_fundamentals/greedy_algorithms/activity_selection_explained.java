/*
 * ACTIVITY SELECTION - GREEDY ALGORITHM
 * ======================================
 * CONCEPT EXPLANATION AT THE BOTTOM OF FILE
 */

import java.util.Arrays;      
import java.util.ArrayList;   
import java.util.Comparator;  
import java.util.Scanner;


// STEP 1: Create a CLASS to represent Activity
// Class = Blueprint, like a form with blank fields
class Activity {
    // These are PROPERTIES (variables) of Activity
    int start;   // When activity starts
    int end;     // When activity ends
    int index;   // Which activity number (for tracking)
    
    // CONSTRUCTOR - Runs when we create new Activity object
    // Constructor has SAME NAME as class
    // No return type (not even void)
    public Activity(int start, int end, int index) {
        // 'this' means "this object's variable"
        // Without 'this', Java gets confused between parameter and variable
        this.start = start;   // Object's start = parameter start
        this.end = end;       // Object's end = parameter end
        this.index = index;   // Object's index = parameter index
    }
    
    // toString() - Decides how this object looks when printed
    // @Override means: "I'm replacing Object class's toString()"
    // Without this, printing gives: Activity@1a2b3c (memory address)
    @Override
    public String toString() {
        // Returns a formatted string
        return "Activity" + index + "[" + start + "-" + end + "]";
    }
}


public class ActivitySelectionExplained {
    
    // METHOD to solve activity selection problem
    // 'static' means: can call without creating object
    // 'public' means: accessible from anywhere
    // Returns: ArrayList of Activity objects
    // Takes: Array of Activity objects as input
    public static ArrayList<Activity> selectActivities(Activity[] activities) {
        
        // STEP 1: SORT activities by END time
        // Why? Activity ending early leaves more room for others
        
        // Arrays.sort() is built-in method to sort arrays
        // By default, it sorts numbers in ascending order (1,2,3...)
        // But Activity is not a number! So we need CUSTOM SORTING
        
        // We provide Comparator - tells Java HOW to sort
        Arrays.sort(activities, new Comparator<Activity>() {
            // Comparator is an INTERFACE
            // Interface = contract that says "implement compare() method"
            
            // @Override means: implementing interface's method
            @Override
            public int compare(Activity a, Activity b) {
                // This method decides: should 'a' come before 'b'?
                
                // Return NEGATIVE: a comes first (a < b)
                // Return ZERO: a and b equal
                // Return POSITIVE: b comes first (a > b)
                
                return a.end - b.end;
                // Example: a.end=4, b.end=7
                // Returns: 4 - 7 = -3 (negative)
                // Meaning: a comes first (we want earlier ending first!)
                
                // If a.end=10, b.end=5
                // Returns: 10 - 5 = 5 (positive)
                // Meaning: b comes first
            }
        });
        
        // ALTERNATIVE: Lambda expression (shorter way)
        // Arrays.sort(activities, (a, b) -> a.end - b.end);
        // Does SAME thing as above, just shorter syntax
        
        
        // Print sorted activities
        System.out.println("Activities sorted by end time:");
        System.out.println("-".repeat(50));
        for (Activity a : activities) {
            // Enhanced for loop (for-each loop)
            // Read as: "for each Activity 'a' in activities array"
            // Automatically goes through each element
            System.out.println(a);  // Calls toString() automatically
        }
        System.out.println();
        
        
        // STEP 2: SELECT activities greedily
        
        // ArrayList to store selected activities
        // ArrayList is DYNAMIC array (size can grow)
        // <Activity> means: this ArrayList holds Activity objects
        ArrayList<Activity> selected = new ArrayList<>();
        
        // Always select FIRST activity (ends earliest after sorting)
        selected.add(activities[0]);  
        // .add() adds element to ArrayList
        // activities[0] is first element (index starts from 0)
        
        int lastEndTime = activities[0].end;
        // Track when last selected activity ends
        // We use this to check if next activity overlaps
        
        System.out.println("Selection process:");
        System.out.println("-".repeat(50));
        System.out.println("✓ Selected: " + activities[0] + 
                         " (first activity always selected)");
        
        // Loop through remaining activities (start from index 1)
        for (int i = 1; i < activities.length; i++) {
            // i = current index
            // activities.length = total number of activities
            // .length is used for ARRAYS
            
            Activity current = activities[i];
            // Get current activity we're checking
            
            // Check if current activity can be selected
            // Rule: current must start AFTER OR WHEN last selected ends
            if (current.start >= lastEndTime) {
                // >= means "greater than or equal to"
                // No overlap! We can select this activity
                
                selected.add(current);  // Add to selected list
                lastEndTime = current.end;  // Update last end time
                
                System.out.println("✓ Selected: " + current + 
                                 " (starts at " + current.start + 
                                 " >= " + (lastEndTime - current.end + current.start) + ")");
            } 
            else {
                // Overlap detected! Skip this activity
                System.out.println("✗ Skipped: " + current + 
                                 " (starts at " + current.start + 
                                 " < " + lastEndTime + ", overlaps!)");
            }
        }
        
        return selected;  
        // Return the ArrayList of selected activities
    }
    
    
    // MAIN METHOD - Entry point of program
    // Program execution starts here
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        // Scanner is used to take INPUT from user
        // System.in means: read from keyboard
        
        System.out.println("=".repeat(60));
        System.out.println("ACTIVITY SELECTION PROBLEM");
        System.out.println("=".repeat(60));
        
        // Ask user for number of activities
        System.out.print("Enter number of activities: ");
        int n = sc.nextInt();  
        // nextInt() reads one integer from user
        
        // Create array of Activity objects
        // Array size = n (user input)
        Activity[] activities = new Activity[n];
        
        // Take input for each activity
        System.out.println("\nEnter start and end time for each activity:");
        for (int i = 0; i < n; i++) {
            System.out.print("Activity " + (i+1) + " - Start time: ");
            int start = sc.nextInt();
            
            System.out.print("Activity " + (i+1) + " - End time: ");
            int end = sc.nextInt();
            
            // Create new Activity OBJECT using constructor
            // 'new' keyword creates object in memory
            // Constructor runs and initializes the object
            activities[i] = new Activity(start, end, i+1);
            // Store this object in array at position i
        }
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("INPUT RECEIVED:");
        System.out.println("=".repeat(60));
        for (int i = 0; i < n; i++) {
            System.out.println(activities[i]);
        }
        System.out.println("=".repeat(60));
        System.out.println();
        
        // Call our method to select activities
        ArrayList<Activity> result = selectActivities(activities);
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("FINAL RESULT");
        System.out.println("=".repeat(60));
        System.out.println("Maximum activities you can do: " + result.size());
        // .size() returns size of ArrayList (not .length!)
        
        System.out.println("Selected activities: ");
        for (Activity a : result) {
            System.out.println("  " + a);
        }
        System.out.println("=".repeat(60));
        
        sc.close();  
        // Always close Scanner to free resources
    }
}


/*
 * ========================================================================
 *                     CONCEPT EXPLANATIONS
 * ========================================================================
 * 
 * 1. CLASS vs OBJECT
 * ------------------
 * Class Activity = Blueprint (form design)
 * 
 * Activity a1 = new Activity(1, 4, 1);  ← This creates OBJECT
 * Activity a2 = new Activity(3, 5, 2);  ← Another OBJECT
 * 
 * Same blueprint (class), different objects!
 * 
 * 
 * 2. CONSTRUCTOR
 * --------------
 * public Activity(int start, int end, int index) { ... }
 * 
 * - Runs AUTOMATICALLY when you write: new Activity(...)
 * - Used to SET INITIAL VALUES
 * - Same name as class
 * - No return type
 * 
 * 
 * 3. this KEYWORD
 * ---------------
 * this.start = start;
 * 
 * Left side (this.start) = Object's variable
 * Right side (start) = Parameter passed to constructor
 * 
 * Without 'this', both would refer to parameter!
 * 
 * 
 * 4. @Override ANNOTATION
 * -----------------------
 * @Override
 * public String toString() { ... }
 * 
 * - Tells Java: "I'm replacing parent class's method"
 * - Not mandatory but RECOMMENDED
 * - Helps catch typos (if method name wrong, Java will warn)
 * 
 * 
 * 5. ARRAYS vs ARRAYLIST
 * ----------------------
 * Array:
 *   Activity[] arr = new Activity[5];  // Fixed size = 5
 *   arr[0] = new Activity(1, 4, 1);
 *   arr.length  // Size
 * 
 * ArrayList:
 *   ArrayList<Activity> list = new ArrayList<>();  // Dynamic size
 *   list.add(new Activity(1, 4, 1));  // Can keep adding
 *   list.size()  // Size
 * 
 * 
 * 6. COMPARATOR INTERFACE
 * -----------------------
 * Interface = Contract that says "you MUST implement compare() method"
 * 
 * compare(a, b) decides order:
 *   Return negative → a before b
 *   Return zero     → a equals b  
 *   Return positive → b before a
 * 
 * a.end - b.end:
 *   If a.end=4, b.end=7: returns -3 (a first) ✓
 *   If a.end=10, b.end=5: returns 5 (b first) ✓
 * 
 * 
 * 7. LAMBDA EXPRESSION (Shortcut)
 * --------------------------------
 * Long way:
 *   Arrays.sort(activities, new Comparator<Activity>() {
 *       public int compare(Activity a, Activity b) {
 *           return a.end - b.end;
 *       }
 *   });
 * 
 * Short way (Lambda):
 *   Arrays.sort(activities, (a, b) -> a.end - b.end);
 * 
 * Both do SAME thing!
 * 
 * 
 * 8. ENHANCED FOR LOOP
 * --------------------
 * for (Activity a : activities) {
 *     System.out.println(a);
 * }
 * 
 * Read as: "for each Activity 'a' in activities array"
 * 
 * Equivalent to:
 * for (int i = 0; i < activities.length; i++) {
 *     Activity a = activities[i];
 *     System.out.println(a);
 * }
 * 
 * 
 * 9. ARRAY INDEXING
 * -----------------
 * Activity[] arr = new Activity[3];
 * 
 * arr[0] = first element
 * arr[1] = second element  
 * arr[2] = third element
 * arr.length = 3
 * 
 * ALWAYS: Index starts from 0, ends at (length - 1)
 * 
 * 
 * 10. COMMON MISTAKES
 * -------------------
 * ✗ for (int i = 0; i <= arr.length; i++)  // Will give error!
 * ✓ for (int i = 0; i < arr.length; i++)   // Correct
 * 
 * ✗ arr.size()      // Arrays don't have size()
 * ✓ arr.length      // Correct for arrays
 * 
 * ✗ list.length     // ArrayList doesn't have length
 * ✓ list.size()     // Correct for ArrayList
 */