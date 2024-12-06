package test;

import java.util.Scanner;

public class Path {
    static int count = 0;
    
    int size = 8;
    static int count1 = 0;
    boolean[][] visited = new boolean[size + 2][size + 2];
    
    int[][] moves = {{-1, 0, 1, 0}, {0, 1, 0, -1}};
    int[] path = new int[size * size - 1];
    String s = "***************************************************************";
    
    public void start() {
        System.out.println("Calculating..."); // Added to indicate the computation has started
        for (int i = 0; i < size + 2; i++) {
            visited[0][i] = true;
            visited[size + 1][i] = true;
            visited[i][0] = true;
            visited[i][size + 1] = true;
        }
        permute(0, 1, 1);
        System.out.println("Calculation complete"); // Added to indicate the computation has finished
    }
    
    public static void main(String[] args) {
        Path path = new Path();
        
        Scanner scanner = new Scanner(System.in);
        String userInput;
        boolean isValid = false;

        // Placeholder string to guide the user
        System.out.println("Edit the following string (63 characters):");
        System.out.println(path.s);
        System.out.println("Use *, R, L, U, D only.");

        // Input validation loop
        while (!isValid) {
            System.out.print("Enter your input: ");
            userInput = scanner.nextLine();
            
            if (userInput.length() != 63) {
                System.out.println("Error: Input must be exactly 63 characters.");
            } else if (!userInput.matches("[*RLUD]*")) {
                System.out.println("Error: Input contains invalid characters. Only *, R, L, U, D are allowed.");
            } else {
                path.s = userInput; // Update the path string after validation
                isValid = true;
            }
        }

        long startTime = System.currentTimeMillis();
        path.start();
        long endTime = System.currentTimeMillis();
        System.out.println("Number of paths: " + count); // Enhanced to provide better feedback
        System.out.println("Time (ms): " + (endTime - startTime)); // Enhanced for better clarity
    }
    
    public void permute(int number, int row, int col) {
        if (visited[row][col - 1] && visited[row][col + 1] && !visited[row - 1][col] && !visited[row + 1][col]) {
            return;
        }
        
        if (visited[row - 1][col] && visited[row + 1][col] && !visited[row][col - 1] && !visited[row][col + 1]) {
            return;
        }
        
        if (row == size && col == 1) {
            if (number == size * size - 1) // 63
                count++;
            return;
        }
        
        if (number == size * size - 1)
            return;
        
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
            } else if (col < 6 && visited[row][col + 2] && (visited[row - 1][col + 1] || visited[row + 1][col + 1]) && !visited[row][col + 1]) {
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
                    
                    if (visited[newrow][newcol])
                        continue;
                    
                    permute(number + 1, newrow, newcol);
                }
            }
        }
        visited[row][col] = false;
    }
}