import java.util.Arrays;
import java.util.Random;

public class MergesortSerial {
    final int N = 100000000;

    public static void main(String[] args) {
        MergesortSerial serial = new MergesortSerial();
        long duration = serial.sort();
        System.out.println("Serial ms: " + duration);
    }

    public long sort() {
        int[] original_array = new int[N];
        int[] tmp_array = new int[N];
        long start = System.currentTimeMillis();

        Random rnd = new Random();
        for (int i = 0; i < N; i++) {
            original_array[i] = rnd.nextInt(N + 30);
        }
        Utils u = new Utils();
        u.mergeSort(original_array, tmp_array, 0, N - 1);
        long stop = System.currentTimeMillis();
//        System.out.println(Arrays.toString(original_array));
        return stop - start;
    }

    /**
     * @param array pole k serazeni
     * @param aux   pomocne pole stejne delky jako array
     * @param left  prvni index na ktery se smi sahnout
     * @param right posledni index, na ktery se smi sahnout
     */
//    protected void mergeSort(int[] array, int[] aux, int left, int right) {
//        if (left == right) return;
//        int middleIndex = (left + right) / 2;
//        mergeSort(array, aux, left, middleIndex);
//        mergeSort(array, aux, middleIndex + 1, right);
//        merge(array, aux, left, right);
//
//        for (int i = left; i <= right; i++) {
//            array[i] = aux[i];
//        }
//    }
//
//    /**
//     * @param array pole k serazeni
//     * @param aux   pomocne pole (stejne velikosti jako razene)
//     * @param left  prvni index, na ktery smim sahnout
//     * @param right posledni index, na ktery smim sahnout
//     */
//    private void merge(int[] array, int[] aux, int left, int right) {
//        int middleIndex = (left + right) / 2;
//        int leftIndex = left;
//        int rightIndex = middleIndex + 1;
//        int auxIndex = left;
//        while (leftIndex <= middleIndex && rightIndex <= right) {
//            if (array[leftIndex] <= array[rightIndex]) {
//                aux[auxIndex] = array[leftIndex++];
//            } else {
//                aux[auxIndex] = array[rightIndex++];
//            }
//            auxIndex++;
//        }
//        while (leftIndex <= middleIndex) {
//            aux[auxIndex] = array[leftIndex++];
//            auxIndex++;
//        }
//        while (rightIndex <= right) {
//            aux[auxIndex] = array[rightIndex++];
//            auxIndex++;
//        }
//    }
}
