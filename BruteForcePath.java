package test;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteForcePath {
    private static final int GRID_SIZE = 7; // 격자 크기
    private static int totalPaths = 0; // 가능한 경로 개수

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
        // 현재 위치를 방문 처리
        visited[row][col] = true;
        path.add('X'); // 현재 위치를 임시로 경로에 추가

        // 모든 칸을 방문한 경우
        if (path.size() == GRID_SIZE * GRID_SIZE) {
            totalPaths++;
        } else {
            // 고정된 순서로 모든 방향 탐색
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

                // 새로운 위치가 유효한 경우
                if (isSafe(newRow, newCol, visited)) {
                    generatePaths(newRow, newCol, visited, path, directions);
                }
            }
        }

        // 백트래킹 (상태 복구)
        path.remove(path.size() - 1);
        visited[row][col] = false;
    }

    private static boolean isSafe(int row, int col, boolean[][] visited) {
        // 위치가 격자 안에 있고 아직 방문하지 않은 경우만 true 반환
        return row >= 0 && col >= 0 && row < GRID_SIZE && col < GRID_SIZE && !visited[row][col];
    }
}
