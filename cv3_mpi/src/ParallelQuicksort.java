import mpi.MPI;
import java.util.Arrays;
import java.util.Random;

public class ParallelQuicksort {
    // Function to swap two numbers
    public static void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
    // Quick Sort function
    public static void quicksort(int[] arr, int start, int end) {
        int pivot, index;
        // Base Case
        if (end <= 1)
            return;
        // Pick pivot and swap with first element Pivot is middle element
        pivot = arr[start + end / 2];
        swap(arr, start, start + end / 2);
        // Partitioning Steps
        index = start;
        // Iterate over the range [start, end]
        for (int i = start + 1; i < start + end; i++) {
            // Swap if the element is less than the pivot element
            if (arr[i] < pivot) {
                index++;
                swap(arr, i, index);
            }
        }

        // Swap the pivot into place
        swap(arr, start, index);

        // Recursive Call for sorting of quick sort function
        quicksort(arr, start, index - start);
        quicksort(arr, index + 1, start + end - index - 1);
    }
    // Function that merges the two arrays
    public static int[] merge(int[] arr1, int n1, int[] arr2, int n2) {
        int[] result = new int[n1 + n2];
        int i = 0, j = 0, k = 0;

        while (i < n1 && j < n2) {
            if (arr1[i] < arr2[j]) {
                result[k++] = arr1[i++];
            } else {
                result[k++] = arr2[j++];
            }
        }
        while (i < n1) {
            result[k++] = arr1[i++];
        }
        while (j < n2) {
            result[k++] = arr2[j++];
        }
        return result;
    }

    // Main Code
    public static void main(String[] args) {
        MPI.Init(args);
        int number_of_process = MPI.COMM_WORLD.Size();
        int rank_of_process = MPI.COMM_WORLD.Rank();
        int number_of_elements = 1000000; // Size of preset array
        //int[] data = { 9, 1, 5, 3, 7, 2, 6, 8, 4, 10, 11, 13, 15, 12, 14, 18, 16, 20, 17, 19 }; // Preset array
        int[] data = new int[number_of_elements];
        // Create an instance of Random class
        Random random = new Random();
        // Fill the array with random integers
        for (int i = 0; i < number_of_elements; i++) {
            data[i] = random.nextInt();
        }
        int chunk_size, own_chunk_size;
        int[] chunk;
        double time_taken = 0;

        // Blocks all process until reach this point
        MPI.COMM_WORLD.Barrier();
        // Starts Timer
        long start_timer = System.currentTimeMillis();

        // Broadcast the Size to all the process from root process
        MPI.COMM_WORLD.Bcast(new int[]{number_of_elements}, 0, 1, MPI.INT, 0);

        // Compute the chunk size to distribute data among MPI processes
        if (number_of_elements % number_of_process == 0) {
            // If the number of elements is evenly divisible by the number of processes, each process will receive
            // an equal chunk of data.
            chunk_size = number_of_elements / number_of_process;
        } else {
            // If not evenly divisible by the number of processes, distribute the elements as evenly as possible
            chunk_size = number_of_elements / number_of_process - 1;
        }

        // Calculating total size of chunk according to bits
        chunk = new int[chunk_size];

        // Scatter the chuck size data to all process
        MPI.COMM_WORLD.Scatter(data, 0, chunk_size, MPI.INT, chunk, 0, chunk_size, MPI.INT, 0);

        // Compute size of own chunk and then sort them using quick sort
        if (number_of_elements >= chunk_size * (rank_of_process + 1)) {
            // If there are enough elements remaining for a full chunk for the current process
            own_chunk_size = chunk_size;
        } else {
            // If there are fewer elements remaining than a full chunk for the current process
            own_chunk_size = number_of_elements - chunk_size * rank_of_process;
        }

        // Sorting array with quick sort for every chunk as called by process
        quicksort(chunk, 0, own_chunk_size);

        // Handle the exchange and merging of sorted chunks between MPI processes during parallel quicksort
        for (int step = 1; step < number_of_process; step = 2 * step) {
            if (rank_of_process % (2 * step) != 0) {
                MPI.COMM_WORLD.Send(chunk, 0, own_chunk_size, MPI.INT, rank_of_process - step, 0);
                break;
            }

            if (rank_of_process + step < number_of_process) {
                int received_chunk_size = (number_of_elements >= chunk_size * (rank_of_process + 2 * step)) ?
                        (chunk_size * step) :
                        (number_of_elements - chunk_size * (rank_of_process + step));
                int[] chunk_received = new int[received_chunk_size];
                MPI.COMM_WORLD.Recv(chunk_received, 0, received_chunk_size, MPI.INT, rank_of_process + step, 0);
                data = merge(chunk, own_chunk_size, chunk_received, received_chunk_size);
                chunk = data;
                own_chunk_size += received_chunk_size;
            }
        }

        // Stop the timer and calculate duration
        long stop_timer = System.currentTimeMillis();
        time_taken = stop_timer - start_timer;

        // Display the sorted array and time
        if (rank_of_process == 0) {
            if (number_of_elements < 20) {
                System.out.println("Sorted array is: " + Arrays.toString(chunk));
            }
            System.out.printf("Quicksort %d ints on %d procs: %f ms\n",
                            number_of_elements, number_of_process, time_taken);
        }

        MPI.Finalize();
    }
}
