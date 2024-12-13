package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BruteForcePath {
    private static final int GRID_SIZE = 6;
    private static int totalPaths = 0;

    public static void main(String[] args) {
        boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
        ArrayList<Character> directions = new ArrayList<>(Arrays.asList('U', 'D', 'L', 'R'));

        long startTime = System.currentTimeMillis();
        generatePaths(0, 0, visited, new ArrayList<>(), directions);
        long endTime = System.currentTimeMillis();

        System.out.println("Total paths: " + totalPaths);
        System.out.println("Execution time (ms): " + (endTime - startTime));
    }

    private static void generatePaths(int row, int col, boolean[][] visited, ArrayList<Character> path, ArrayList<Character> directions) {
        // Mark current cell as visited
        visited[row][col] = true;
        path.add('X'); // Placeholder for current position

        // If path length matches required size, count as valid
        if (path.size() == GRID_SIZE * GRID_SIZE) {
            totalPaths++;
        } else {
            // Shuffle directions to simulate brute force randomness
            Collections.shuffle(directions);

            for (char dir : directions) {
                int newRow = row;
                int newCol = col;

                switch (dir) {
                    case 'U':
                        newRow = row - 1;
                        break;
                    case 'D':
                        newRow = row + 1;
                        break;
                    case 'L':
                        newCol = col - 1;
                        break;
                    case 'R':
                        newCol = col + 1;
                        break;
                }

                if (isSafe(newRow, newCol, visited)) {
                    generatePaths(newRow, newCol, visited, path, directions);
                }
            }
        }

        // Backtrack
        path.remove(path.size() - 1);
        visited[row][col] = false;
    }

    private static boolean isSafe(int row, int col, boolean[][] visited) {
        return row >= 0 && col >= 0 && row < GRID_SIZE && col < GRID_SIZE && !visited[row][col];
    }
}
