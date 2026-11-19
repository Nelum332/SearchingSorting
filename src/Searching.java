/**
 * Searching contains generic linear and binary search algorithms.
 * Both methods work on any objects that implement Comparable.
 */
public class Searching {

    /**
     * Searches the array using linear search: every element is checked
     * one by one from index min to index max. The data does not have to
     * be in any particular order. Performance is O(n).
     *
     * @param data   the array to be searched
     * @param min    the first index to search
     * @param max    the last index to search
     * @param target the element being searched for
     * @return true if the target was found
     */
    public static <T extends Comparable<T>> boolean linearSearch(T[] data, int min, int max, T target) {
        int index = min;
        boolean found = false;

        while (!found && index <= max) {
            found = data[index].compareTo(target) == 0;
            index++;
        }
        return found;
    }

    /**
     * Searches the array using binary search. The data MUST be sorted.
     * The middle element is compared to the target and half of the
     * remaining candidates is dropped on every step. Performance is O(log n).
     *
     * @param data   the sorted array to be searched
     * @param min    the first index of the search range
     * @param max    the last index of the search range
     * @param target the element being searched for
     * @return true if the target was found
     */
    public static <T extends Comparable<T>> boolean binarySearch(T[] data, int min, int max, T target) {
        if (min > max) {
            return false;                       // no candidates left
        }

        int midpoint = (min + max) / 2;
        int result = data[midpoint].compareTo(target);

        if (result == 0) {
            return true;                        // found it
        } else if (result > 0) {
            return binarySearch(data, min, midpoint - 1, target);  // search left half
        } else {
            return binarySearch(data, midpoint + 1, max, target);  // search right half
        }
    }
}
