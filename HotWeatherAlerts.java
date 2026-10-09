public class HotWeatherAlerts {

    // Brute force: recompute each block's sum from scratch
    // Time: O((N - k + 1) * k) = O(N * k)   Space: O(1)
    static int countAlertsBruteForce(int[] readings, int k, int threshold) {
        int count = 0;
        for (int i = 0; i + k <= readings.length; i++) {
            long sum = 0;
            for (int j = i; j < i + k; j++) sum += readings[j];
            if (sum >= (long) k * threshold) count++;
        }
        return count;
    }

    // Sliding window: add the new reading, drop the one that left
    // Time: O(N)   Space: O(1)
    // Compare sum >= k * threshold to avoid decimal division.
    static int countAlerts(int[] readings, int k, int threshold) {
        long target = (long) k * threshold;
        long sum = 0;
        int count = 0;
        for (int i = 0; i < readings.length; i++) {
            sum += readings[i];
            if (i >= k) sum -= readings[i - k];
            if (i >= k - 1 && sum >= target) count++;
        }
        return count;
    }

    public static void main(String[] args) {
        int[] readings = {2, 2, 2, 2, 5, 5, 5, 8};
        System.out.println(countAlerts(readings, 3, 4));           // 3
        System.out.println(countAlertsBruteForce(readings, 3, 4)); // 3
    }
}