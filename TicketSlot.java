public class TicketSlot {

    // Linear scan: first index with prices[i] >= newPrice
    // Time: O(n)   Space: O(1)
    static int findSlotLinear(int[] prices, int newPrice) {
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] >= newPrice) return i;
        }
        return prices.length;
    }

    // Binary search: if not found, 'low' ends at the insertion point
    // Time: O(log n)   Additional space: O(1)
    static int findSlot(int[] prices, int newPrice) {
        int low = 0, high = prices.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (prices[mid] == newPrice) return mid;
            else if (prices[mid] < newPrice) low = mid + 1;
            else high = mid - 1;
        }
        return low;
    }

    public static void main(String[] args) {
        int[] prices = {120, 150, 200, 260};
        System.out.println(findSlot(prices, 150));       // 1
        System.out.println(findSlot(prices, 210));       // 3
        System.out.println(findSlot(prices, 300));       // 4
        System.out.println(findSlot(new int[]{}, 50));   // 0
        System.out.println(findSlotLinear(prices, 210)); // 3
    }
}