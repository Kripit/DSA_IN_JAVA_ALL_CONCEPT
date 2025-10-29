/*
 * GREEDY ALGORITHM - LEVEL 2 (MEDIUM)
 * ====================================
 * PROBLEM: Activity Selection / Meeting Rooms
 * 
 * You have N activities with start and end times.
 * Select MAXIMUM number of activities that don't overlap.
 * You can only do ONE activity at a time.
 * 
 * EXAMPLE:
 * Activity 1: (1, 4)   -> start=1, end=4
 * Activity 2: (3, 5)   -> start=3, end=5
 * Activity 3: (0, 6)   -> start=0, end=6
 * Activity 4: (5, 7)   -> start=5, end=7
 * Activity 5: (3, 9)   -> start=3, end=9
 * Activity 6: (5, 9)   -> start=5, end=9
 * Activity 7: (6, 10)  -> start=6, end=10
 * Activity 8: (8, 11)  -> start=8, end=11
 * Activity 9: (8, 12)  -> start=8, end=12
 * Activity 10: (2, 14) -> start=2, end=14
 * Activity 11: (12, 16)-> start=12, end=16
 * 
 * GREEDY STRATEGY:
 * Sort by END time (ascending)
 * Pick activity that ends earliest
 * Why? Leaves more room for future activities!
 * 
 * ML/AI CONNECTION:
 * - GPU scheduling (schedule max number of training jobs)
 * - Resource allocation (assign compute to tasks)
 * - Time-series event selection
 */

import java.util.Arrays;      // For sorting
import java.util.ArrayList;   // For storing selected activities
import java.util.Comparator;  // For custom sorting

// Class to represent an Activity
class Activity {
    int start;  // Start time
    int end;    // End time
    int index;  // Original index (for tracking)
    
    // Constructor - creates new Activity object
    public Activity(int start, int end, int index) {
        this.start = start; // 'this' refers to current object
        this.end = end;
        this.index = index;
    }
    
    // toString method - defines how to print this object
    // Automatically called when we do System.out.println(activity)
    @Override
    public String toString() {
        return "A" + index + "(" + start + "-" + end + ")";
    }
}


public class GreedyActivitySelection {
    
    // Method to find maximum non-overlapping activities
    public static ArrayList<Activity> selectActivities(Activity[] activities) {
        // activities: array of all available activities
        
        // STEP 1: Sort activities by END time (ascending order)
        // Why end time? Activity ending early leaves more room for others
        // Comparator is an interface that defines custom sorting logic
        Arrays.sort(activities, new Comparator<Activity>() {
            // compare method: returns negative if a<b, 0 if a==b, positive if a>b
            @Override
            public int compare(Activity a, Activity b) {
                // Compare by end time
                return a.end - b.end;
                // If a.end < b.end: returns negative -> a comes first
                // If a.end > b.end: returns positive -> b comes first
            }
        });
        
        // Alternative shorter syntax (Java 8+):
        // Arrays.sort(activities, (a, b) -> a.end - b.end);
        
        System.out.println("After sorting by end time:");
        for (Activity a : activities) {
            // Enhanced for loop: 'a' takes each element from activities
            System.out.println(a);
        }
        System.out.println();
        
        
        // STEP 2: Greedily select activities
        ArrayList<Activity> selected = new ArrayList<>();
        
        // Always select FIRST activity (ends earliest after sorting)
        selected.add(activities[0]);
        int lastEndTime = activities[0].end; // Track when last selected activity ends
        
        System.out.println("Selected: " + activities[0] + 
                         " | Last end time: " + lastEndTime);
        
        // Iterate through remaining activities
        for (int i = 1; i < activities.length; i++) {
            // i starts from 1 (skip first, already selected)
            
            Activity current = activities[i]; // Current activity we're considering
            
            // Check if current activity starts AFTER or WHEN last selected ends
            if (current.start >= lastEndTime) {
                // No overlap! Can select this activity
                selected.add(current);
                lastEndTime = current.end; // Update last end time
                
                System.out.println("Selected: " + current + 
                                 " | Last end time: " + lastEndTime);
            } else {
                // Overlap detected! Skip this activity
                System.out.println("Skipped: " + current + 
                                 " (overlaps with previous)");
            }
        }
        
        return selected; // Return list of selected activities
    }
    
    
    public static void main(String[] args) {
        // Create activities array
        // Format: Activity(start, end, index)
        Activity[] activities = {
            new Activity(1, 4, 1),
            new Activity(3, 5, 2),
            new Activity(0, 6, 3),
            new Activity(5, 7, 4),
            new Activity(3, 9, 5),
            new Activity(5, 9, 6),
            new Activity(6, 10, 7),
            new Activity(8, 11, 8),
            new Activity(8, 12, 9),
            new Activity(2, 14, 10),
            new Activity(12, 16, 11)
        };
        
        System.out.println("=".repeat(60));
        System.out.println("GREEDY ACTIVITY SELECTION PROBLEM");
        System.out.println("=".repeat(60));
        System.out.println("Total activities: " + activities.length);
        System.out.println("=".repeat(60));
        System.out.println();
        
        // Call selection method
        ArrayList<Activity> result = selectActivities(activities);
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("FINAL RESULT");
        System.out.println("=".repeat(60));
        System.out.println("Maximum activities selected: " + result.size());
        System.out.println("Selected activities: " + result);
        System.out.println("=".repeat(60));
    }
}

/*
 * TIME COMPLEXITY ANALYSIS:
 * -------------------------
 * Sorting: O(n log n) - where n = number of activities
 * Iterating: O(n) - single pass through sorted array
 * 
 * TOTAL: O(n log n) - dominated by sorting
 * 
 * 
 * SPACE COMPLEXITY:
 * ----------------
 * O(n) - for storing selected activities (worst case: all selected)
 * O(1) - if we only count selected activities (not store them)
 * 
 * 
 * WHY THIS GREEDY WORKS:
 * ----------------------
 * Proof by contradiction:
 * - Assume optimal solution doesn't include first activity (earliest end)
 * - Replace first activity in optimal with our first activity
 * - Still no overlap (our activity ends earlier!)
 * - Contradiction! Our greedy choice is optimal
 * 
 * 
 * PREBUILT COMPONENTS EXPLAINED:
 * ------------------------------
 * 1. Arrays.sort() - Built-in sorting (uses Dual-Pivot Quicksort)
 *    - O(n log n) average case
 *    - Stable for objects (maintains relative order of equal elements)
 * 
 * 2. Comparator<T> - Interface for custom comparison logic
 *    - Has one method: compare(T o1, T o2)
 *    - Return negative if o1 < o2, zero if equal, positive if o1 > o2
 * 
 * 3. ArrayList<T> - Dynamic array (resizable)
 *    - add(element): O(1) amortized (sometimes O(n) when resizing)
 *    - get(index): O(1)
 *    - size(): O(1)
 *    - Internally uses array that doubles when full
 * 
 * 4. Enhanced for loop: for (Type var : collection)
 *    - Syntactic sugar for iterating
 *    - Equivalent to: for (int i=0; i<arr.length; i++)
 */