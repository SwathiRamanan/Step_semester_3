import java.util.Arrays;

public class NoticeBoardShuffle {

    static int[] bubbleStats(int[] scores) {
        int n = scores.length;
        int passes = 0, swaps = 0;
        for (int i = 0; i < n - 1; i++) {
            passes++;                 
            boolean swapped = false;  
            for (int j = 0; j < n - 1 - i; j++) {
                if (scores[j] > scores[j + 1]) {
                    int t = scores[j];
                    scores[j] = scores[j + 1];
                    scores[j + 1] = t;
                    swaps++;
                    swapped = true;
                }
            }
            if (!swapped) break;      
        }
        return new int[]{passes, swaps};
    }

    public static void main(String[] args) {
        int[] a = {5, 1, 4, 2, 8};
        int[] r1 = bubbleStats(a);
        System.out.println(Arrays.toString(a) + "  passes = " + r1[0] + ", swaps = " + r1[1]);

        int[] b = {1, 2, 3, 4};
        int[] r2 = bubbleStats(b);
        System.out.println(Arrays.toString(b) + "  passes = " + r2[0] + ", swaps = " + r2[1]);
    }

    
}