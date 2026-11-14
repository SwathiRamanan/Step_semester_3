public class PlacementCutoff {

    // Quickselect: returns the k-th highest mark (1 <= k <= marks.length).
    static int kthHighest(int[] marks, int k) {
        int[] a = marks.clone();            
        int target = a.length - k;          
        int lo = 0, hi = a.length - 1;
        while (lo <= hi) {
            int p = partition(a, lo, hi);
            if (p == target) return a[p];
            else if (p < target) lo = p + 1; 
            else hi = p - 1;                 
        }
        return -1; 
    }

    
    private static int partition(int[] a, int lo, int hi) {
        int pivot = a[hi];
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (a[j] < pivot) {
                int t = a[i]; a[i] = a[j]; a[j] = t;
                i++;
            }
        }
        int t = a[i]; a[i] = a[hi]; a[hi] = t;
        return i;
    }

    public static void main(String[] args) {
        System.out.println(kthHighest(new int[]{72, 95, 60, 88, 79}, 2)); 
        System.out.println(kthHighest(new int[]{50, 90, 90, 70}, 2));     
    }

    
}