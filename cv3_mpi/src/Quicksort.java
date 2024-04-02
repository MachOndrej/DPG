import java.util.Random;

class QuickSort
{
    /* This function takes last element as pivot, places the pivot element at its correct
    position in sorted array, and places all smaller (smaller than pivot) to left of pivot
    and all greater elements to right of pivot */
    int partition(int arr[], int low, int high)
    {
        int pivot = arr[high];
        int i = (low-1); // index of smaller element
        for (int j=low; j<high; j++)
        {
            // If current element is smaller than or equal to pivot
            if (arr[j] <= pivot)
            {
                i++;
                // swap arr[i] and arr[j]
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        // swap arr[i+1] and arr[high] (or pivot)
        int temp = arr[i+1];
        arr[i+1] = arr[high];
        arr[high] = temp;

        return i+1;
    }


    // The main function that implements QuickSort
    void sort(int arr[], int low, int high)
    {
        if (low < high)
        {
			/* pi is partitioning index, arr[pi] is
			now at right place */
            int pi = partition(arr, low, high);

            // Recursively sort elements before
            // partition and after partition
            sort(arr, low, pi-1);
            sort(arr, pi+1, high);
        }
    }

    // Main Code
    public static void main(String args[])
    {
        int number_of_elements = 1000000; // Size of preset array
        //int[] data = { 9, 1, 5, 3, 7, 2, 6, 8, 4, 10, 11, 13, 15, 12, 14, 18, 16, 20, 17, 19 }; // Preset array
        int[] data = new int[number_of_elements];
        // Create an instance of Random class
        Random random = new Random();
        // Fill the array with random integers
        for (int i = 0; i < number_of_elements; i++) {
            data[i] = random.nextInt();
        }
        long stat_timer = System.currentTimeMillis();
        QuickSort ob = new QuickSort();
        ob.sort(data, 0, number_of_elements-1);

        long duration = System.currentTimeMillis() - stat_timer;
        System.out.println("Quicksort: " + duration + " ms");
    }
}
