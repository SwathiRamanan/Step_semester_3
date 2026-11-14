public class ContestLeaderboard {

    static class Entry {
        String name;
        int marks;
        Entry(String name, int marks) { this.name = name; this.marks = marks; }
        @Override
        public String toString() { return name + " " + marks; }
    }


    static Entry[] leaderboard(Entry[] entries) {
        int n = entries.length;
        int[] count = new int[101];                 

        for (Entry e : entries) count[100 - e.marks]++;

        for (int i = 1; i < count.length; i++)       
            count[i] += count[i - 1];

        Entry[] output = new Entry[n];
        for (int i = n - 1; i >= 0; i--) {           
            int key = 100 - entries[i].marks;
            output[--count[key]] = entries[i];
        }
        return output;
    }

    static String str(Entry[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(", ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Entry[] entries = {
            new Entry("Asha", 90), new Entry("Ravi", 75), new Entry("Neha", 90),
            new Entry("Kiran", 100), new Entry("Meera", 75)
        };
        System.out.println(str(leaderboard(entries)));
        
    }


}