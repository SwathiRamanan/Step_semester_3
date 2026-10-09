import java.util.HashMap;
import java.util.Map;

public class PopularOrder {

    // Approach 1: for each item, rescan the whole list to count it
    // Time: O(n^2)   Space: O(1)
    static String[] mostPopularBruteForce(String[] orders) {
        String best = orders[0];
        int bestCount = 0;
        for (int i = 0; i < orders.length; i++) {
            int count = 0;
            for (int j = 0; j < orders.length; j++) {
                if (orders[j].equals(orders[i])) count++;
            }
            if (count > bestCount) { // strict '>' -> earliest item wins ties
                bestCount = count;
                best = orders[i];
            }
        }
        return new String[]{best, String.valueOf(bestCount)};
    }

    // Approach 2: HashMap counts in one pass, then rescan in original order for ties
    // Time: O(n)   Space: O(k), k = number of distinct items
    static String[] mostPopular(String[] orders) {
        Map<String, Integer> counts = new HashMap<>();
        for (String item : orders) {
            counts.put(item, counts.getOrDefault(item, 0) + 1);
        }
        String best = null;
        int bestCount = 0;
        for (String item : orders) {
            int c = counts.get(item);
            if (c > bestCount) {
                bestCount = c;
                best = item;
            }
        }
        return new String[]{best, String.valueOf(bestCount)};
    }

    public static void main(String[] args) {
        String[] o1 = {"dosa", "idli", "vada", "dosa", "idli", "dosa", "tea"};
        String[] o2 = {"tea", "coffee", "coffee", "tea"};
        String[] r1 = mostPopular(o1);
        String[] r2 = mostPopular(o2);
        System.out.println("(" + r1[0] + ", " + r1[1] + ")"); // (dosa, 3)
        System.out.println("(" + r2[0] + ", " + r2[1] + ")"); // (tea, 2)
        System.out.println(mostPopularBruteForce(o2)[0]);     // tea
    }
}