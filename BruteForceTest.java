package test;
public class BruteForceTest {
    // 경로 개수를 추적하는 정적 변수
    static int count = 0;
    static long count2 = 0;
    int size; // 격자 크기
    boolean[][] visited; // 방문 상태를 추적
    int[][] moves = {{-1, 0, 1, 0}, {0, 1, 0, -1}}; // 상하좌우 이동

    // 생성자: 격자 크기와 초기 상태를 설정
    public BruteForceTest(int size) {
        this.size = size;
        this.visited = new boolean[size + 2][size + 2]; // 경계 포함
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

        // 브루트포스로 모든 경로 탐색 시작
        generatePaths(1, 1, 0);
        System.out.println("Paths found: " + count); // 경로 개수 출력
        System.out.println("Number of recursive calls: "+ count2);
    }

    // 메인 메서드: 다양한 격자 크기 테스트
    public static void main(String[] args) {
        // 테스트할 격자 크기
        int[] sizes = {4, 5, 6, 7}; // 8x8은 제외하고 7x7까지만 실행

        for (int currentSize : sizes) {
            count2=0;
            System.out.println("\nRunning for size: " + currentSize + "x" + currentSize);
            BruteForceTest test = new BruteForceTest(currentSize); // BruteForceTest 객체 생성
            count = 0; // count 초기화

            long startTime = System.currentTimeMillis(); // 실행 시간 측정 시작
            test.start(); // 경로 탐색 시작
            long endTime = System.currentTimeMillis(); // 실행 시간 측정 종료

            System.out.println("Time (ms): " + (endTime - startTime)); // 실행 시간 출력
        }
    }

    // 브루트포스 방식으로 모든 경로를 생성
    public void generatePaths(int row, int col, int steps) {
        // 이미 격자의 모든 셀을 방문했고 끝점에 도달한 경우
        count2++;
        if (steps == size * size - 1 && row == size && col == 1) {
            count++;
            return;
        }

        // 현재 셀이 마지막 단계가 아닌데 셀을 초과한 경우
        if (steps == size * size - 1) {
            return;
        }

        // 현재 셀 방문 표시
        visited[row][col] = true;

        // 네 가지 방향으로 이동
        for (int i = 0; i < 4; i++) {
            int newRow = row + moves[0][i];
            int newCol = col + moves[1][i];

            // 새 셀이 유효한지 확인
            if (!visited[newRow][newCol]) {
                generatePaths(newRow, newCol, steps + 1);
            }
        }

        // 백트래킹: 방문 상태 되돌리기
        visited[row][col] = false;
    }
}