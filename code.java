package test;

public class Path {
    static int count = 0;

    int size;
    boolean[][] visited;
    int[][] moves = {{-1, 0, 1, 0}, {0, 1, 0, -1}};
    int[] path;
    String s = "*****DR******R******R********************R*D************L******";

    public Path(int size) {
        this.size = size;
        this.visited = new boolean[size + 2][size + 2];
        this.path = new int[size * size - 1];
    }

    public void start() {
        for (int i = 0; i < size + 2; i++) {
            visited[0][i] = true;
            visited[size + 1][i] = true;
            visited[i][0] = true;
            visited[i][size + 1] = true;
        }
        permute(0, 1, 1);
        System.out.println("Paths found: " + count);
    }

    public static void main(String[] args) {
        int[] sizes = {4, 5, 6, 7};

        for (int currentSize : sizes) {
            System.out.println("\nRunning for size: " + currentSize + "x" + currentSize);
            Path path = new Path(currentSize);
            count = 0; // Reset count for each size

            long startTime = System.currentTimeMillis();
            path.start();
            long endTime = System.currentTimeMillis();

            System.out.println("Time (ms): " + (endTime - startTime));
        }
    }

    public void permute(int number, int row, int col) {
        if (visited[row][col - 1] && visited[row][col + 1] && !visited[row - 1][col] && !visited[row + 1][col]) {
            return;
        }

        if (visited[row - 1][col] && visited[row + 1][col] && !visited[row][col - 1] && !visited[row][col + 1]) {
            return;
        }

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

        visited[row][col] = false;
    }
}
