//Algorithm.java: Implements the main algorithm for pathfinding on an 8x8 grid. It accepts user-defined constraints (`*`, `R`, `L`, `U`, `D`) and validates input before calculating valid paths.

import java.util.Scanner;

public class Algorithm {
    static int count = 0;

    int size = 8;
    static int count1 = 0;
    boolean[][] visited = new boolean[size + 2][size + 2];

    int[][] moves = { { -1, 0, 1, 0 }, { 0, 1, 0, -1 } };
    int[] path = new int[size * size - 1];
    String s = "***************************************************************";

    public void start() {
        System.out.println("Calculating...");

        // Initialize the borders of the grid as visited
        for (int i = 0; i < size + 2; i++) {
            visited[0][i] = true;
            visited[size + 1][i] = true;
            visited[i][0] = true;
            visited[i][size + 1] = true;
        }

        // Start the pathfinding algorithm
        permute(0, 1, 1);
        System.out.println("Calculation complete");
    }

    public static void main(String[] args) {
        Algorithm path = new Algorithm();

        Scanner scanner = new Scanner(System.in);
        String userInput;
        boolean isValid = false;

        // Provide instructions for valid input
        System.out.println("Please provide a string of exactly 63 characters.");
        System.out.println("The string should contain directions using the characters:");
        System.out.println("  - 'R' for Right, 'L' for Left, 'U' for Up, 'D' for Down.");
        System.out.println("  - Use '*' for unrestricted movement.");
        System.out.println("For example, you can copy and use the following sample string:");
        System.out.println(path.s);
        System.out.println("Ensure your input follows this format exactly.");

        // Validate user input
        while (!isValid) {
            System.out.print("Enter your input: ");
            userInput = scanner.nextLine();

            boolean lengthValid = userInput.length() == 63;
            boolean charValid = userInput.matches("[*RLUD]*");

            if (!lengthValid || !charValid) {
                System.out.println("Error:");

                if (!lengthValid) {
                    System.out.println("  - Input must be exactly 63 characters. Your input length is "
                            + userInput.length() + ".");
                }

                if (!charValid) {
                    System.out.println("  - Input contains invalid characters:");
                    for (int i = 0; i < userInput.length(); i++) {
                        char c = userInput.charAt(i);
                        if ("*RLUD".indexOf(c) == -1) {
                            System.out.println("    * Invalid character found: '" + c + "' at position " + (i + 1) + ".");
                        }
                    }
                }
            } else {
                path.s = userInput;
                isValid = true;
                System.out.println("Input accepted.");
            }
        }

        long startTime = System.currentTimeMillis();
        path.start();
        long endTime = System.currentTimeMillis();
        System.out.println("Number of paths: " + count);
        System.out.println("Time (ms): " + (endTime - startTime));
    }

    public void permute(int number, int row, int col) {
        // Terminate early if the current cell causes a deadlock
        if (visited[row][col - 1] && visited[row][col + 1] && !visited[row - 1][col] && !visited[row + 1][col]) {
            return;
        }

        if (visited[row - 1][col] && visited[row + 1][col] && !visited[row][col - 1] && !visited[row][col + 1]) {
            return;
        }

        // Check if the endpoint is reached
        if (row == size && col == 1) {
            if (number == size * size - 1) {
                count++;
            }
            return;
        }

        if (number == size * size - 1) {
            return;
        }

        visited[row][col] = true;

        // Handle movement based on user input
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
        } else {
            // Explore unrestricted movement in all directions
            if ((col > 2) && visited[row][col - 2] && (visited[row - 1][col - 1] || visited[row + 1][col - 1])
                    && !visited[row][col - 1]) {
                int newrow = row;
                int newcol = col - 1;
                permute(number + 1, newrow, newcol);
            } else if (col < 6 && visited[row][col + 2] && (visited[row - 1][col + 1] || visited[row + 1][col + 1])
                    && !visited[row][col + 1]) {
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
        visited[row][col] = false;
    }
}
