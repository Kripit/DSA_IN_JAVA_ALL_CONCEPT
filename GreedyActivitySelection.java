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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

class Activity {

    int start;
    int end;
    int index;// Original index (optional, useful to track original order)

// Constructor: initializes a new Activity object

    public Activity(int start , int end , int index){
        this.start = start;
        this.end = end;
        this.index = index; 
    }

    // toString method: defines how this object prints
    // Called automatically when System.out.println(activity) is used
    @Override
    public String toString() {
        return "A" + index + "(" + start + "-" + end + ")";

        //A1(1-4)
        //A2(3-5) example output 
        }
}

// See you have to learn new things It is not complicated just learn and see what im teaching i might not be good in teaching but see i provided example output to for each class do not skip go line by line



public class GreedyActivitySelection {

    // ArrayList<Activity> → this method returns a dynamic list of selected activities.
    // Activity[] activities → the input is a normal array of Activity objects.
    // So activities[0] is the first Activity, activities[1] is the second, etc.
    // selectActivities() -> the worker function name

    // This is NOT a class, it's a method inside the main class.
    // - Input: array of Activity objects (Activity[] activities)
    // - Output: ArrayList of selected activities (maximum non-overlapping)
    public static ArrayList<Activity> selectActivities(Activity[] activities) {

        // STEP 1: Sort activities by END time (earliest first)
        // Comparator is used because we are sorting objects, not numbers
        Arrays.sort(activities, new Comparator<Activity>() {
            @Override
            public int compare(Activity a, Activity b) {
                // Compare end times:
                // If a ends before b, result < 0 → a comes first
                // If a ends after b, result > 0 → b comes first
                return a.end - b.end;
            }
        });

        System.out.println("After sorting by end time:");
        for (Activity a : activities) {
            System.out.println(a);  // prints Aindex(start-end)
        }
        System.out.println();



        // STEP 2: Greedy selection

        ArrayList<Activity> selected = new ArrayList<>();


        selected.add(activities[0]);// Always pick the first activity (ends earliest)
        int lastEndTime = activities[0].end;// track end time of last picked activity

        System.out.println("Selected: " + activities[0] + " | Last end time: " + lastEndTime);

        for ( int i = 1 ; i <activities.length; i++){

            Activity current = activities[i];

            if (current.start>= lastEndTime){
                selected.add(current);// safe to select, no overlap
                lastEndTime = current.end;
                System.out.println("Selected: " + current + " | Last end time: " + lastEndTime);
            } else {
                // Overlaps with previous, skip this activity
                System.out.println("Skipped: " + current + " (overlaps)");
            }

            }
            return selected;
    }

     public static void main(String[] args) {

        // Step 0: Create activities array
        // Activity(start, end, index)
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

        // Call selectActivities method
        ArrayList<Activity> result = selectActivities(activities);

        // Print final result
        System.out.println("\n" + "=".repeat(60));
        System.out.println("FINAL RESULT");
        System.out.println("=".repeat(60));
        System.out.println("Maximum activities selected: " + result.size());
        System.out.println("Selected activities: " + result);
        System.out.println("=".repeat(60));
    }

    
}
