public class MergeSortMultithread {
    public static int[] mergeSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return arr;
        }
        mergeSort(arr, 0, arr.length - 1);
        return arr;
    }

    private static void mergeSort(int[] arr, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;

            // Create two threads for sorting left and right halves
            Thread leftThread = new Thread(() -> mergeSort(arr, left, mid));
            Thread rightThread = new Thread(() -> mergeSort(arr, mid + 1, right));

            // Start both threads
            leftThread.start();
            rightThread.start();

            try {
                // Wait for both threads to finish
                leftThread.join();
                rightThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Merge the sorted halves
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        // Merge logic remains the same
    }
}
