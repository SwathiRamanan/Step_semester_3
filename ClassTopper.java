public class ClassTopper {

    // Nested loops: sum each student's marks, keep best (strictly greater -> smallest index wins ties)
    // Time: O(m * n)   Additional space: O(1)
    static int[] findTopper(int[][] marks) {
        int bestRow = 0, bestTotal = -1;
        for (int i = 0; i < marks.length; i++) {
            int total = 0;
            for (int j = 0; j < marks[i].length; j++) {
                total += marks[i][j];
            }
            if (total > bestTotal) {
                bestTotal = total;
                bestRow = i;
            }
        }
        return new int[]{bestRow, bestTotal};
    }

    public static void main(String[] args) {
        int[][] marks = {{78, 85, 90}, {88, 92, 79}, {65, 70, 95}};
        int[] r = findTopper(marks);
        System.out.println("(" + r[0] + ", " + r[1] + ")"); // (1, 259)
    }
}