import java.util.Arrays;

public class MergeTokens {

    // Two pointers over two sorted arrays
    // Time: O(m + n)   Space: O(m + n) for the output (O(1) extra beyond it)
    // Compare: join + sort is O((m + n) log(m + n)), which ignores the existing order.
    static int[] mergeTokens(int[] a, int[] b) {
        int[] res = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) res[k++] = a[i++];
            else res[k++] = b[j++];
        }
        while (i < a.length) res[k++] = a[i++];
        while (j < b.length) res[k++] = b[j++];
        return res;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(mergeTokens(new int[]{3, 8, 15, 20}, new int[]{5, 8, 12}))); // [3, 5, 8, 8, 12, 15, 20]
        System.out.println(Arrays.toString(mergeTokens(new int[]{}, new int[]{4, 9})));                  // [4, 9]
    }
}