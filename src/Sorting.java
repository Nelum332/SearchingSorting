/**
 * Sorting contains generic sorting algorithms.
 *
 * O(n^2) algorithms:       selection sort, insertion sort, bubble sort
 * O(n * log(n)) algorithms: quick sort, merge sort
 *
 * All comparisons are done with compareTo, so when the sorted objects are
 * CountingLong objects every comparison is counted automatically.
 */
public class Sorting {

    // ------------------------------------------------------------------
    //  O(n^2) sorting algorithms
    // ------------------------------------------------------------------

    /**
     * Selection sort: finds the smallest value and swaps it to the first
     * position, then the next smallest to the second position, and so on.
     */
    public static <T extends Comparable<T>> void selectionSort(T[] data) {
        for (int index = 0; index < data.length - 1; index++) {
            int min = index;
            for (int scan = index + 1; scan < data.length; scan++) {
                if (data[scan].compareTo(data[min]) < 0) {
                    min = scan;
                }
            }
            swap(data, min, index);
        }
    }

    /**
     * Insertion sort: takes values one at a time and inserts each into
     * its correct place in the already sorted beginning of the array.
     */
    public static <T extends Comparable<T>> void insertionSort(T[] data) {
        for (int index = 1; index < data.length; index++) {
            T key = data[index];
            int position = index;

            // shift larger values one step to the right
            while (position > 0 && data[position - 1].compareTo(key) > 0) {
                data[position] = data[position - 1];
                position--;
            }
            data[position] = key;
        }
    }

    /**
     * Bubble sort: compares neighbouring values and swaps them if they are
     * in the wrong order. After each pass the largest remaining value has
     * "bubbled" to the end.
     */
    public static <T extends Comparable<T>> void bubbleSort(T[] data) {
        for (int position = data.length - 1; position >= 0; position--) {
            for (int scan = 0; scan <= position - 1; scan++) {
                if (data[scan].compareTo(data[scan + 1]) > 0) {
                    swap(data, scan, scan + 1);
                }
            }
        }
    }

    // ------------------------------------------------------------------
    //  O(n * log(n)) sorting algorithms
    // ------------------------------------------------------------------

    /**
     * Quick sort: picks a partition element, moves smaller values to its
     * left and larger values to its right, then sorts both sides recursively.
     */
    public static <T extends Comparable<T>> void quickSort(T[] data) {
        quickSort(data, 0, data.length - 1);
    }

    private static <T extends Comparable<T>> void quickSort(T[] data, int min, int max) {
        if (min < max) {
            int indexOfPartition = partition(data, min, max);
            quickSort(data, min, indexOfPartition - 1);   // left part (smaller values)
            quickSort(data, indexOfPartition + 1, max);   // right part (larger values)
        }
    }

    /**
     * Partitions data[min..max] around the middle element and returns
     * the final index of that partition element.
     */
    private static <T extends Comparable<T>> int partition(T[] data, int min, int max) {
        int middle = (min + max) / 2;
        T partitionElement = data[middle];
        swap(data, middle, min);           // move partition element out of the way

        int left = min;
        int right = max;

        while (left < right) {
            // find an element that is > partition element
            while (left < right && data[left].compareTo(partitionElement) <= 0) {
                left++;
            }
            // find an element that is < partition element
            while (data[right].compareTo(partitionElement) > 0) {
                right--;
            }
            if (left < right) {
                swap(data, left, right);
            }
        }

        swap(data, min, right);            // move partition element to its final place
        return right;
    }

    /**
     * Merge sort: splits the array in halves until each part has one
     * element, then merges the sorted parts back together.
     */
    public static <T extends Comparable<T>> void mergeSort(T[] data) {
        mergeSort(data, 0, data.length - 1);
    }

    private static <T extends Comparable<T>> void mergeSort(T[] data, int min, int max) {
        if (min < max) {
            int mid = (min + max) / 2;
            mergeSort(data, min, mid);
            mergeSort(data, mid + 1, max);
            merge(data, min, mid, max);
        }
    }

    /**
     * Merges two sorted sub-arrays data[first..mid] and data[mid+1..last].
     * The temporary array is only as big as the part being merged, so the
     * extra memory work stays O(n) per level (keeps the O(n log n) timing honest).
     */
    @SuppressWarnings("unchecked")
    private static <T extends Comparable<T>> void merge(T[] data, int first, int mid, int last) {
        T[] temp = (T[]) new Comparable<?>[last - first + 1];

        int first1 = first;       // start of the left part
        int first2 = mid + 1;     // start of the right part
        int index = 0;            // next free place in temp

        // copy the smaller value from either part until one part runs out
        while (first1 <= mid && first2 <= last) {
            if (data[first1].compareTo(data[first2]) <= 0) {
                temp[index++] = data[first1++];
            } else {
                temp[index++] = data[first2++];
            }
        }
        // copy what is left of either part
        while (first1 <= mid) {
            temp[index++] = data[first1++];
        }
        while (first2 <= last) {
            temp[index++] = data[first2++];
        }
        // copy the merged values back into the original array
        for (index = 0; index < temp.length; index++) {
            data[first + index] = temp[index];
        }
    }

    // ------------------------------------------------------------------
    //  Helper
    // ------------------------------------------------------------------

    /** Swaps two elements of an array. */
    private static <T> void swap(T[] data, int index1, int index2) {
        T temp = data[index1];
        data[index1] = data[index2];
        data[index2] = temp;
    }
}
