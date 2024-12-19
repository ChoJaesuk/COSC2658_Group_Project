//AlgorithmTest.java: Runs tests on smaller grids (4x4 to 7x7) with all constraints fixed to `*`. This file is designed to compare performance against brute-force results.

public class AlgorithmTest {
    static int count = 0; // Tracks the number of valid paths found
    static long count2 = 0; // Tracks the number of recursive calls
    int size; // Size of the grid
    boolean[][] visited; // Tracks visited cells
    int[][] moves = {{-1, 0, 1, 0}, {0, 1, 0, -1}}; // Directions for moving up, right, down, and left
    int[] path; // Stores the path
    String s = "***************************************************************"; // Input string with '*' constraints

    // Constructor: Initializes grid size and visited state
    public AlgorithmTest(int size) {
        this.size = size;
        this.visited = new boolean[size + 2][size + 2]; // Includes grid boundaries
        this.path = new int[size * size - 1]; // Initializes path storage
    }

    // Starts the pathfinding process
    public void start() {
        // Set boundaries to true to prevent out-of-grid movement
        for (int i = 0; i < size + 2; i++) {
            visited[0][i] = true;
            visited[size + 1][i] = true;
            visited[i][0] = true;
            visited[i][size + 1] = true;
        }

        // Begin recursive backtracking
        permute(0, 1, 1);
        System.out.println("Paths found: " + count);
        System.out.println("Number of recursive calls: " + count2);
        count2 = 0;
    }

    // Main method: Runs tests for grids of different sizes
    public static void main(String[] args) {
        int[] sizes = {4, 5, 6, 7}; // Grid sizes to test

        for (int currentSize : sizes) {
            System.out.println("\nRunning for size: " + currentSize + "x" + currentSize);
            AlgorithmTest path = new AlgorithmTest(currentSize); // Create test instance
            count = 0; // Reset path count

            long startTime = System.currentTimeMillis();
            path.start();
            long endTime = System.currentTimeMillis();

            System.out.println("Time (ms): " + (endTime - startTime));
        }
    }

    // Recursive method for exploring all paths
    public void permute(int number, int row, int col) {
        count2++;
        // Exit conditions for paths leading to dead ends
        if (visited[row][col - 1] && visited[row][col + 1] && !visited[row - 1][col] && !visited[row + 1][col]) {
            return;
        }
        if (visited[row - 1][col] && visited[row + 1][col] && !visited[row][col - 1] && !visited[row][col + 1]) {
            return;
        }

        // Check if endpoint is reached and all cells are visited
        if (row == size && col == 1) {
            if (number == size * size - 1) {
                count++;
            }
            return;
        }

        // Exit if all cells are visited but endpoint is not reached
        if (number == size * size - 1) {
            return;
        }

        visited[row][col] = true; // Mark current cell as visited

        // Handle movement based on constraints
        if (s.charAt(number) != '*') {
            if (s.charAt(number) == 'R') {
                int newrow = row;
                int newcol = col + 1;
                if (!visited[newrow][newcol]) {
                    permute(number + 1, newrow, newcol);
                }
            }
            if (s.charAt(number) == 'L') {
                int newrow = row;
                int newcol = col - 1;
                if (!visited[newrow][newcol]) {
                    permute(number + 1, newrow, newcol);
                }
            }
            if (s.charAt(number) == 'U') {
                int newrow = row - 1;
                int newcol = col;
                if (!visited[newrow][newcol]) {
                    permute(number + 1, newrow, newcol);
                }
            }
            if (s.charAt(number) == 'D') {
                int newrow = row + 1;
                int newcol = col;
                if (!visited[newrow][newcol]) {
                    permute(number + 1, newrow, newcol);
                }
            }
        } else { // Explore all directions if no constraints
            if ((col > 2) && visited[row][col - 2] && (visited[row - 1][col - 1] || visited[row + 1][col - 1]) && !visited[row][col - 1]) {
                int newrow = row;
                int newcol = col - 1;
                permute(number + 1, newrow, newcol);
            } else if (col < size && visited[row][col + 2] && (visited[row - 1][col + 1] || visited[row + 1][col + 1]) && !visited[row][col + 1]) {
                int newrow = row;
                int newcol = col + 1;
                permute(number + 1, newrow, newcol);
            } else if (row > 2 && visited[row - 2][col] && visited[row - 1][col - 1] && (!visited[row - 1][col])) {
                int newrow = row - 1;
                int newcol = col;
                permute(number + 1, newrow, newcol);
            } else {
                for (int i = 0; i < 4; i++) {
                    int newrow = row + moves[0][i];
                    int newcol = col + moves[1][i];

                    if (visited[newrow][newcol]) {
                        continue;
                    }

                    permute(number + 1, newrow, newcol);
                }
            }
        }

        visited[row][col] = false; // Unmark current cell for backtracking
    }
}
