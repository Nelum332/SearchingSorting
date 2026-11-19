# Searching and Sorting Testbed (PL3 final project)
Withana Arachchige Thilakshi Nelum Gunawardhane - target 5p

## How to run in IntelliJ
1. File > Open and select this SearchingSorting folder (it is a ready IntelliJ project).
2. If asked for an SDK, pick JDK 17 or newer (File > Project Structure > Project).
3. Right-click src/Main.java > Run 'Main.main()'.

## Files
- `Main.java` - menu and user interface
- `Searching.java` - linear and binary search (generic)
- `Sorting.java` - selection, insertion, bubble, quick and merge sort (generic)
- `CountingLong.java` - long wrapper whose compareTo counts comparisons in a shared static counter
- `PerformanceTest.java` - menu choice 5: sorts n, 2n, ..., 10n random items and prints comparisons and ms
