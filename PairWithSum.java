import java.util.HashSet;
import java.util.Set;

public class PairWithSum {

    // Approach 1: Brute force (check every pair)
    // Time: O(n^2)   Space: O(1)
    static boolean hasPairWithSumBruteForce(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) return true;
            }
        }
        return false;
    }

    // Approach 2: HashSet of seen values (check complement)
    // Time: O(n) average   Space: O(n)
    static boolean hasPairWithSum(int[] nums, int target) {
        Set<Integer> seen = new HashSet<>();
        for (int x : nums) {
            if (seen.contains(target - x)) return true;
            seen.add(x);
        }
        return false;
    }

    public static void main(String[] args) {
        int[] a = {2, 7, 11, 15};
        int[] b = {3, 4, 6};
        System.out.println(hasPairWithSum(a, 9));            // true
        System.out.println(hasPairWithSum(b, 20));           // false
        System.out.println(hasPairWithSumBruteForce(a, 9));  // true
        System.out.println(hasPairWithSumBruteForce(b, 20)); // false
    }
}