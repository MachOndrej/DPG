public class QuickSortMultithread {
    public static int[] multiThreadQuickSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return arr;
        }
        multiThreadQuickSort(arr, 0, arr.length - 1);
        return arr;
    }

    private static void multiThreadQuickSort(int[] arr, int low, int high) {
        if (low < high) {
            // Partition the array
            int pivotIndex = partition(arr, low, high);

            // Recursively sort the left and right partitions using separate threads
            Thread leftThread = new Thread(() -> multiThreadQuickSort(arr, low, pivotIndex - 1));
            Thread rightThread = new Thread(() -> multiThreadQuickSort(arr, pivotIndex + 1, high));

            leftThread.start();
            rightThread.start();

            try {
                // Wait for both threads to finish
                leftThread.join();
                rightThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
