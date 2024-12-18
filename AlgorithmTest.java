package test;

public class AlgorithmTest {
    // 경로 개수를 추적하는 정적 변수
    static int count = 0;
    static long count2 = 0;
    int size; // 격자 크기
    boolean[][] visited; // 방문 상태를 추적
    int[][] moves = {{-1, 0, 1, 0}, {0, 1, 0, -1}}; // 상하좌우 이동
    int[] path; // 경로 저장
    String s = "***************************************************************"; // '*'가 포함된 문자열

    // 생성자: 격자 크기와 초기 상태를 설정
    public AlgorithmTest(int size) {
        this.size = size;
        this.visited = new boolean[size + 2][size + 2]; // 경계 포함
        this.path = new int[size * size - 1]; // 이동 경로
    }

    // 경로 탐색 시작
    public void start() {
        // 경계를 true로 설정하여 격자 외부로 나가는 것을 방지
        for (int i = 0; i < size + 2; i++) {
            visited[0][i] = true;
            visited[size + 1][i] = true;
            visited[i][0] = true;
            visited[i][size + 1] = true;
        }
        // 백트래킹 알고리즘으로 경로 탐색 시작
        permute(0, 1, 1);
        System.out.println("Paths found: " + count); // 경로 개수 출력
        System.out.println("Number of recursive calls: "+ count2);
        count2=0;
    }

    // 메인 메서드: 다양한 격자 크기 테스트
    public static void main(String[] args) {
        // 테스트할 격자 크기
        int[] sizes = {4, 5, 6, 7}; // 8x8은 제외하고 7x7까지만 실행

        // 각 격자 크기에 대해 테스트 실행
        for (int currentSize : sizes) {
            System.out.println("\nRunning for size: " + currentSize + "x" + currentSize);
            AlgorithmTest path = new AlgorithmTest(currentSize); // AlgorithmTest 객체 생성
            count = 0; // count 초기화

            long startTime = System.currentTimeMillis(); // 실행 시간 측정 시작
            path.start(); // 경로 탐색 시작
            long endTime = System.currentTimeMillis(); // 실행 시간 측정 종료

            System.out.println("Time (ms): " + (endTime - startTime)); // 실행 시간 출력
        }
    }

    // 백트래킹으로 모든 경로를 탐색
    public void permute(int number, int row, int col) {
        // 최적화 조건: 이동 불가능한 경우 조기 종료
        count2++;
        if (visited[row][col - 1] && visited[row][col + 1] && !visited[row - 1][col] && !visited[row + 1][col]) {
            return;
        }
        if (visited[row - 1][col] && visited[row + 1][col] && !visited[row][col - 1] && !visited[row][col + 1]) {
            return;
        }

        // 도착 지점에 도달하고 모든 셀을 방문한 경우
        if (row == size && col == 1) {
            if (number == size * size - 1) {
                count++;
            }
            return;
        }

        // 셀을 다 방문하지 않고 끝난 경우
        if (number == size * size - 1) {
            return;
        }

        // 현재 셀 방문 표시
        visited[row][col] = true;

        // '*'가 아닌 특정 방향으로만 이동할 경우 처리
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
        } else { // '*'인 경우 모든 방향으로 이동
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

        // 백트래킹: 방문 표시 해제
        visited[row][col] = false;
    }
}
