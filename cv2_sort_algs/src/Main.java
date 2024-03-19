import java.util.Arrays;
import java.util.Random;
import java.util.HashSet;

import static java.lang.Math.abs;

public class Main {
    static int sampleSize = 1000;
    static int minValue = 1;
    static int maxValue = 10000;

    public static int[] uniqueRandom(int length) {
        if (length > maxValue - minValue + 1) {
            throw new IllegalArgumentException("Sample size exceeds the range of available numbers.");
        }

        Random rand = new Random();
        int[] nums = new int[length];
        boolean[] check = new boolean[maxValue - minValue + 1];

        for (int k = 0; k < length; k++) {
            int rnd;
            do {
                rnd = rand.nextInt(maxValue - minValue + 1) + minValue;
            } while (check[rnd - minValue]);

            nums[k] = rnd;
            check[rnd - minValue] = true;
        }
        return nums;
    }

    public static boolean areAllUnique(int[] arr) {
        HashSet<Integer> set = new HashSet<>();
        for (int num : arr) {
            if (set.contains(num)) {
                return false; // Not all values are unique
            }
            set.add(num);
        }
        return true; // All values are unique
    }

    public static void main(String args[]) {
        int[] sampleArray = uniqueRandom(sampleSize);
//        System.out.println(Arrays.toString(sampleArray));
//        System.out.println("Array len: " + sampleArray.length);
//        boolean allUnique = areAllUnique(sampleArray);
//        System.out.println("Unique?  " + allUnique);
        long startTime1 = System.nanoTime();
        int[] quicksorted = QuickSort.quickSort(sampleArray);
        //System.out.println("Sorted array (QuickSort): " + Arrays.toString(quicksorted));
        long endTime1 = System.nanoTime();
        System.out.println("Time spent QuickSort: " + (endTime1 - startTime1) + " nanoseconds");

        long startTime2 = System.nanoTime();
        int[] quicksortedmultithread = QuickSortMultithread.multiThreadQuickSort(sampleArray);
        //System.out.println("Sorted array (QuickSort - Multithread): " + Arrays.toString(quicksortedmultithread));
        long endTime2 = System.nanoTime();
        System.out.println("Time spent QuickSort M.: " + (endTime2 - startTime2) + " nanoseconds");
        System.out.println("-> Difference = |Single - Multi| = " + abs((endTime1 - startTime1)-(endTime2 - startTime2)));

        long startTime3 = System.nanoTime();
        int[] mergedsorted = MergeSort.mergeSort(sampleArray);
        //System.out.println("Sorted array (MergeSort): " + Arrays.toString(mergedsorted));
        long endTime3 = System.nanoTime();
        System.out.println("Time spent MergeSort: " + (endTime3 - startTime3) + " nanoseconds");

        long startTime4 = System.nanoTime();
        int[] mergesortedmultithread = MergeSortMultithread.mergeSort(sampleArray);
        //System.out.println("Sorted array (MergeSort - Multihthread): " + Arrays.toString(mergesortedmultithread));
        long endTime4 = System.nanoTime();
        System.out.println("Time spent MergeSort M.: " + (endTime4 - startTime4) + " nanoseconds");
        System.out.println("-> Difference = |Single - Multi| = " + abs((endTime3 - startTime3)-(endTime4 - startTime4)));

    }

}
