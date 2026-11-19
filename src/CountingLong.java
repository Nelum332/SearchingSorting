/**
 * CountingLong holds one long number. Every time two CountingLong objects
 * are compared with compareTo, a counter that is SHARED by all objects
 * (static) is incremented. This lets us count how many comparisons a
 * sorting algorithm makes.
 */
public class CountingLong implements Comparable<CountingLong> {

    /** One counter for the whole class, shared by all objects. */
    private static long comparisonCount = 0;

    private final long value;

    public CountingLong(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    /** Compares this number to another one and counts the comparison. */
    @Override
    public int compareTo(CountingLong other) {
        comparisonCount++;
        return Long.compare(value, other.value);
    }

    public static long getComparisonCount() {
        return comparisonCount;
    }

    public static void resetComparisonCount() {
        comparisonCount = 0;
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }
}
