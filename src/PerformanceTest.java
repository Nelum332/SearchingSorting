import java.util.Random;
import java.util.function.Consumer;

/**
 * PerformanceTest sorts random data sets of sizes n, 2n, ..., 10n with
 * five sorting algorithms (three O(n^2) and two O(n log n)) and prints how
 * many comparisons and milliseconds each sort took.
 *
 * The output is comma separated in the row labels so it is easy to copy
 * into Excel and draw a chart of comparisons / ms as a function of size.
 */
public class PerformanceTest {

    /** Base size n. Data set sizes are n, 2n, ..., STEPS * n. */
    private static final int N = 1000;
    private static final int STEPS = 10;

    /** Column widths of the printed table. */
    private static final int LABEL_WIDTH = 34;
    private static final int COLUMN_WIDTH = 20;

    private static final String[] NAMES = {
            "bubbleSort", "insertionSort", "mergeSort", "quickSort", "selectionSort"
    };

    /** The sort methods, in the same order as NAMES. */
    private static final java.util.List<Consumer<CountingLong[]>> SORTS = java.util.List.of(
            Sorting::bubbleSort,
            Sorting::insertionSort,
            Sorting::mergeSort,
            Sorting::quickSort,
            Sorting::selectionSort
    );

    private final Random random = new Random();

    /** Runs the whole test and prints the results table. */
    public void run() {
        long[][] comparisons = new long[NAMES.length][STEPS];
        long[][] millis = new long[NAMES.length][STEPS];

        warmUp();

        for (int step = 0; step < STEPS; step++) {
            int size = N * (step + 1);
            long[] original = randomNumbers(size);   // same data for every algorithm

            for (int alg = 0; alg < NAMES.length; alg++) {
                CountingLong[] data = toCountingLongs(original);

                CountingLong.resetComparisonCount();
                long start = System.nanoTime();
                SORTS.get(alg).accept(data);
                long end = System.nanoTime();

                comparisons[alg][step] = CountingLong.getComparisonCount();
                millis[alg][step] = (end - start) / 1_000_000;
            }
        }

        printResults(comparisons, millis);
    }

    /**
     * Runs every algorithm a few times on small data before measuring, so
     * that Java's JIT compiler has already optimised the code and the first
     * measured data set is not unfairly slow.
     */
    private void warmUp() {
        for (int round = 0; round < 5; round++) {
            long[] numbers = randomNumbers(N);
            for (Consumer<CountingLong[]> sort : SORTS) {
                sort.accept(toCountingLongs(numbers));
            }
        }
    }

    private long[] randomNumbers(int size) {
        long[] numbers = new long[size];
        for (int i = 0; i < size; i++) {
            numbers[i] = random.nextInt(1_000_000);
        }
        return numbers;
    }

    private CountingLong[] toCountingLongs(long[] numbers) {
        CountingLong[] data = new CountingLong[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            data[i] = new CountingLong(numbers[i]);
        }
        return data;
    }

    private void printResults(long[][] comparisons, long[][] millis) {
        // header row: the data set sizes
        System.out.print(pad("", LABEL_WIDTH));
        for (int step = 0; step < STEPS; step++) {
            String size = String.valueOf(N * (step + 1));
            System.out.print(step < STEPS - 1 ? pad(size, COLUMN_WIDTH) : size);
        }
        System.out.println();

        // two rows per algorithm: comparisons and milliseconds
        for (int alg = 0; alg < NAMES.length; alg++) {
            printRow(NAMES[alg] + ",random,comparisons", comparisons[alg]);
            printRow(NAMES[alg] + ",random,ms", millis[alg]);
        }
    }

    private void printRow(String label, long[] values) {
        System.out.print(pad(label, LABEL_WIDTH));
        for (int i = 0; i < values.length; i++) {
            String value = String.valueOf(values[i]);
            // pad every column except the last one, so lines have no trailing spaces
            System.out.print(i < values.length - 1 ? pad(value, COLUMN_WIDTH) : value);
        }
        System.out.println();
    }

    /** Adds spaces after text so that it is exactly width characters long. */
    private String pad(String text, int width) {
        return String.format("%-" + width + "s", text);
    }
}
