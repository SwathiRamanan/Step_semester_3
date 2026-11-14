public class AttendanceDisorderScore {

    
    static long countInversions(int[] roll) {
        int[] copy = roll.clone();          
        int[] temp = new int[copy.length];
        return mergeSortCount(copy, temp, 0, copy.length - 1);
    }

    private static long mergeSortCount(int[] a, int[] temp, int lo, int hi) {
        if (lo >= hi) return 0;
        int mid = lo + (hi - lo) / 2;
        long count = mergeSortCount(a, temp, lo, mid)
                   + mergeSortCount(a, temp, mid + 1, hi);

        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <= hi) {
            if (a[i] <= a[j]) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
                count += (mid - i + 1);                 }
        }
        while (i <= mid) temp[k++] = a[i++];
        while (j <= hi)  temp[k++] = a[j++];
        for (k = lo; k <= hi; k++) a[k] = temp[k];
        return count;
    }

    public static void main(String[] args) {
        System.out.println(countInversions(new int[]{2, 4, 1, 3, 5})); 
        System.out.println(countInversions(new int[]{5, 4, 3, 2, 1})); 
    }

    
}