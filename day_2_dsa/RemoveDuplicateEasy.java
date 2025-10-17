package day_2_dsa;
/*
 * PROBLEM 2: REMOVE DUPLICATES FROM SORTED ARRAY
 * ================================================
 * 
 * Given SORTED array, remove duplicates IN-PLACE.
 * Return length of unique elements.
 * 
 * Example:
 * Input:  arr = [1, 1, 2, 2, 3]
 * Output: 3
 * Modified arr = [1, 2, 3, _, _]  (first 3 elements are unique)
 * 
 * 
 * APPROACH EXPLANATION:
 * ---------------------
 * 
 * KEY IDEA: Use SLOW & FAST pointers
 * 
 * SLOW (i): Points to position of last unique element
 * FAST (j): Scans array to find next unique element
 * 
 * Algorithm:
 * i = 0  (first element always unique)
 * for j = 1 to n:
 *     if arr[j] != arr[i]:  // Found new unique!
 *         i++               // Move boundary
 *         arr[i] = arr[j]   // Place unique element
 * 
 * return i + 1  (length = index + 1)
 * 
 * 
 * VISUAL EXPLANATION:
 * -------------------
 * arr = [1, 1, 2, 2, 3]
 * 
 * Initially: i=0, j=1
 * [1, 1, 2, 2, 3]
 *  i  j
 * arr[j]=1, arr[i]=1 → Same, just move j
 * 
 * i=0, j=2
 * [1, 1, 2, 2, 3]
 *  i     j
 * arr[j]=2, arr[i]=1 → Different! New unique!
 * i++ → i=1
 * arr[i] = arr[j] → arr[1] = 2
 * 
 * [1, 2, 2, 2, 3]
 *     i     j
 * 
 * Continue until j reaches end...
 * Final: [1, 2, 3, _, _], return i+1 = 3
 */

import java.util.Arrays;

public class RemoveDuplicateEasy{

// METHOD 1: Using extra space (What NOT to do in interview)
    // Time: O(n), Space: O(n)
    public static int removeDuplicatesExtraSpace(int[] arr){

        int n = arr.length;

        if( n==0 ) return 0;

        int[] temp = new int[n];
        int j=0;

        temp[j++]=arr[0];

        //copy only unique elements 

        for ( int i = 1 ; i < n ; i++ ){

            if( arr[i] != arr[i-1] ){

                temp[j++] = arr[i];
            }
        }
        for(int i = 0; i < n ; i++){

            arr[i] = temp[i];
        }

        return j;// Length of unique elements


    }  

 // METHOD 2: TWO POINTERS - IN PLACE (Optimal solution)
    // Time: O(n), Space: O(1)

    public static int removeDuplicatesTwoPointers(int[] arr){

        int n = arr.length;
        

        // edge case : array empty 
        if(n==0) return 0;

        // i = SLOW pointer (boundary of unique section)
        // j = FAST pointer (scanner)

        int i = 0;

        for( int j = 1; j < n ; j++ ){
            
            if( arr[j] != arr[i] ) {
                
                i++;// Move boundary forward

                arr[i] = arr[j];// Place new unique element

            }// If same, just continue (j automatically increments)
        }
        return i+1;
        // Return length of unique section
        // Length = last index + 1
    }
    public static void main(String[] args) {
        // Test case 1
        int[] arr1 = {1, 1, 2};
        System.out.println("Original: " + Arrays.toString(arr1));
        int len1 = removeDuplicatesTwoPointers(arr1);
        System.out.println("After removal: " + Arrays.toString(arr1));
        System.out.println("New length: " + len1);
        System.out.println();
        
        // Test case 2
        int[] arr2 = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        System.out.println("Original: " + Arrays.toString(arr2));
        int len2 = removeDuplicatesTwoPointers(arr2);
        System.out.println("After removal: " + Arrays.toString(arr2));
        System.out.println("New length: " + len2);
    }
    
}
