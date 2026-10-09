public class WarehouseGrid {

    // Single pass over the grid
    // Time: O(m * n)   Additional space: O(1)
    // Returns {totalItems, row, col}
    static int[] warehouseSummary(int[][] grid) {
        int total = 0, max = Integer.MIN_VALUE, maxR = -1, maxC = -1;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                total += grid[r][c];
                if (grid[r][c] > max) { // strict '>' keeps the first occurrence
                    max = grid[r][c];
                    maxR = r;
                    maxC = c;
                }
            }
        }
        return new int[]{total, maxR, maxC};
    }

    public static void main(String[] args) {
        int[][] grid = {{4, 9, 2}, {7, 1, 6}, {3, 12, 5}};
        int[] s = warehouseSummary(grid);
        System.out.println("(" + s[0] + ", (" + s[1] + ", " + s[2] + "))"); // (49, (2, 1))
    }
}