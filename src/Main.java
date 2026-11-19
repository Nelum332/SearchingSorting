import java.util.Random;
import java.util.Scanner;

/**
 * Searching and Sorting Testbed - Programming Languages 3 final project.
 *
 * A menu based console program. Every function can be run again as many
 * times as wanted; the program ends only when q or Q is chosen.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();

    /** Size and value range of the small data sets of choices 3 and 4. */
    private static final int SMALL_SET_SIZE = 10;
    private static final int MIN_VALUE = -100;
    private static final int MAX_VALUE = 100;

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.hasNextLine() ? scanner.nextLine().trim() : "q";

            switch (choice) {
                case "1" -> linearSearching();
                case "2" -> binarySearching();
                case "3" -> squareSorting();
                case "4" -> logSorting();
                case "5" -> sortingPerformance();
                case "q", "Q" -> running = false;
                default -> {
                    System.out.println("Unknown choice " + choice + ".");
                    System.out.println();
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("Menu of Searching and Sorting Testbed.");
        System.out.println();
        System.out.println("1)   Linear searching");
        System.out.println("2)   Binary searching");
        System.out.println("3)   n^2 type of sorting");
        System.out.println("4)   n * log(n) type of sorting");
        System.out.println("5)   Sorting performance");
        System.out.println();
        System.out.println("q/Q) Quit");
        System.out.println();
        System.out.print("Your choice: ");
    }

    // ------------------------------------------------------------------
    //  1) and 2) Searching
    // ------------------------------------------------------------------

    private static void linearSearching() {
        System.out.print("In the list are values 0, ..., 9; which value would you like to search with linear search? ");
        Integer target = readInteger();
        if (target == null) {
            return;
        }
        Integer[] list = createList();
        boolean found = Searching.linearSearch(list, 0, list.length - 1, target);
        printFoundResult(found);
    }

    private static void binarySearching() {
        System.out.print("In the list are values 0, ..., 9; which value would you like to search with binary search? ");
        Integer target = readInteger();
        if (target == null) {
            return;
        }
        Integer[] list = createList();   // already sorted, which binary search requires
        boolean found = Searching.binarySearch(list, 0, list.length - 1, target);
        printFoundResult(found);
    }

    /** Creates a new list containing values 0, 1, ..., 9. */
    private static Integer[] createList() {
        Integer[] list = new Integer[10];
        for (int i = 0; i < list.length; i++) {
            list[i] = i;
        }
        return list;
    }

    private static void printFoundResult(boolean found) {
        System.out.println();
        System.out.println(found ? "Found" : "Not found");
        System.out.println();
    }

    /** Reads one integer from the user. Returns null if the input was not a number. */
    private static Integer readInteger() {
        String input = scanner.hasNextLine() ? scanner.nextLine().trim() : "";
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println();
            System.out.println("\"" + input + "\" is not an integer.");
            System.out.println();
            return null;
        }
    }

    // ------------------------------------------------------------------
    //  3) and 4) Sorting a small random data set
    // ------------------------------------------------------------------

    private static void squareSorting() {
        Integer[] data = createRandomData();
        System.out.println();
        printDataSet("Data set before insertion sorting:", data);
        Sorting.insertionSort(data);
        printDataSet("Data set after insertion sorting:", data);
    }

    private static void logSorting() {
        Integer[] data = createRandomData();
        System.out.println();
        printDataSet("Data set before quicksort:", data);
        Sorting.quickSort(data);
        printDataSet("Data set after quicksort:", data);
    }

    /** Creates ten random integers between MIN_VALUE and MAX_VALUE. */
    private static Integer[] createRandomData() {
        Integer[] data = new Integer[SMALL_SET_SIZE];
        for (int i = 0; i < data.length; i++) {
            data[i] = MIN_VALUE + random.nextInt(MAX_VALUE - MIN_VALUE + 1);
        }
        return data;
    }

    private static void printDataSet(String title, Integer[] data) {
        System.out.println(title);
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            if (i > 0) {
                line.append(' ');
            }
            line.append(data[i]);
        }
        System.out.println(line);
        System.out.println();
    }

    // ------------------------------------------------------------------
    //  5) Sorting performance
    // ------------------------------------------------------------------

    private static void sortingPerformance() {
        new PerformanceTest().run();
        System.out.println();
    }
}
