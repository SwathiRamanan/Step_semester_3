public class ContainerArea {

    // Brute force: check every pair of walls
    // Time: O(n^2)   Space: O(1)
    static int maxContainerAreaBruteForce(int[] heights) {
        int best = 0;
        for (int i = 0; i < heights.length; i++)
            for (int j = i + 1; j < heights.length; j++)
                best = Math.max(best, Math.min(heights[i], heights[j]) * (j - i));
        return best;
    }

    // Optimal: two pointers from both ends
    // Time: O(n)   Additional space: O(1)
    // Moving the taller wall can never help (width shrinks, height still capped
    // by the shorter wall), so always move the shorter one. For very large n,
    // O(n) vs O(n^2) is the difference between milliseconds and hours.
    static int maxContainerArea(int[] heights) {
        int left = 0, right = heights.length - 1, best = 0;
        while (left < right) {
            best = Math.max(best, Math.min(heights[left], heights[right]) * (right - left));
            if (heights[left] < heights[right]) left++;
            else right--;
        }
        return best;
    }

    public static void main(String[] args) {
        int[] h = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        System.out.println(maxContainerArea(h));           // 49
        System.out.println(maxContainerAreaBruteForce(h)); // 49
    }
}