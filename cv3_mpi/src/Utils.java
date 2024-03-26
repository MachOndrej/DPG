public class Utils {

    /**
     * @param array pole k serazeni
     * @param tmp   pomocne pole stejne delky jako array
     * @param left  prvni index na ktery se smi sahnout
     * @param right posledni index, na ktery se smi sahnout
     */
    // Klasicky mergesort
    protected void mergeSort(int[] array, int[] tmp, int left, int right) {
        if (left == right)
            return;
        int middleIndex = (left + right) / 2;
        mergeSort(array, tmp, left, middleIndex);
        mergeSort(array, tmp, middleIndex + 1, right);
        merge(array, tmp, left, right);

        for (int i = left; i <= right; i++) {
            array[i] = tmp[i];
        }
    }

    /**
     * @param array pole k serazeni
     * @param tmp   pomocne pole (stejne velikosti jako razene)
     * @param left  prvni index, na ktery smim sahnout
     * @param right posledni index, na ktery smim sahnout
     */
    private void merge(int[] array, int[] tmp, int left, int right) {
        int middleIndex = (left + right) / 2;
        int leftIndex = left;
        int rightIndex = middleIndex + 1;
        int tmpIndex = left;
        while (leftIndex <= middleIndex && rightIndex <= right) {
            if (array[leftIndex] <= array[rightIndex]) {
                tmp[tmpIndex] = array[leftIndex++];
            } else {
                tmp[tmpIndex] = array[rightIndex++];
            }
            tmpIndex++;
        }
        while (leftIndex <= middleIndex) {
            tmp[tmpIndex] = array[leftIndex++];
            tmpIndex++;
        }
        while (rightIndex <= right) {
            tmp[tmpIndex] = array[rightIndex++];
            tmpIndex++;
        }
    }
}
