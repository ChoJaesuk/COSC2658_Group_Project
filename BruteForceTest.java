//BruteForceTest.java: Uses a brute-force approach to calculate paths for 4x4 to 7x7 grids. It serves as a baseline for comparison with `AlgorithmTest.java`.

public class BruteForceTest {
    static int count = 0; // Tracks the number of valid paths found
    static long count2 = 0; // Tracks the number of recursive calls
    int size; // Size of the grid
    boolean[][] visited; // Tracks visited cells
    int[][] moves = {{-1, 0, 1, 0}, {0, 1, 0, -1}}; // Directions for moving up, right, down, and left

    // Constructor: Initializes grid size and visited state
    public BruteForceTest(int size) {
        this.size = size;
        this.visited = new boolean[size + 2][size + 2]; // Includes grid boundaries
    }

    // Starts the pathfinding process using brute force
    public void start() {
        // Set boundaries to true to prevent out-of-grid movement
        for (int i = 0; i < size + 2; i++) {
            visited[0][i] = true;
            visited[size + 1][i] = true;
            visited[i][0] = true;
            visited[i][size + 1] = true;
        }

        // Explore all possible paths
        generatePaths(1, 1, 0);
        System.out.println("Paths found: " + count);
        System.out.println("Number of recursive calls: " + count2);
    }

    // Main method: Runs tests for grids of different sizes
    public static void main(String[] args) {
        int[] sizes = {4, 5, 6, 7}; // Grid sizes to test

        for (int currentSize : sizes) {
            count2 = 0;
            System.out.println("\nRunning for size: " + currentSize + "x" + currentSize);
            BruteForceTest test = new BruteForceTest(currentSize); // Create test instance
            count = 0; // Reset path count

            long startTime = System.currentTimeMillis();
            test.start();
            long endTime = System.currentTimeMillis();

            System.out.println("Time (ms): " + (endTime - startTime));
        }
    }

    // Recursively generates all possible paths
    public void generatePaths(int row, int col, int steps) {
        count2++;
        // Check if all cells are visited and endpoint is reached
        if (steps == size * size - 1 && row == size && col == 1) {
            count++;
            return;
        }

        // Exit if all cells are visited but endpoint is not reached
        if (steps == size * size - 1) {
            return;
        }

        visited[row][col] = true; // Mark current cell as visited

        // Explore all four directions
        for (int i = 0; i < 4; i++) {
            int newRow = row + moves[0][i];
            int newCol = col + moves[1][i];

            // Check if the new cell is valid and not visited
            if (!visited[newRow][newCol]) {
                generatePaths(newRow, newCol, steps + 1);
            }
        }

        visited[row][col] = false; // Unmark current cell for backtracking
    }
}
