public class PrintCircular {

    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    // Time O(n), Space O(1)
    static void printCircular(Node head) {
        if (head == null) {
            System.out.println();
            return;
        }
        StringBuilder sb = new StringBuilder();
        Node current = head;
        do {
            sb.append(current.data).append(' ');
            current = current.next;
        } while (current != head);
        System.out.println(sb.toString().trim());
    }

    public static void main(String[] args) {
        Node a = new Node(10), b = new Node(20), c = new Node(30);
        a.next = b; b.next = c; c.next = a;
        printCircular(a);     // 10 20 30
        printCircular(null);  // (blank line)
    }
}