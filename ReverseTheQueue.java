import java.util.Scanner;
public class ReverseTheQueue {

    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    /** Reverses the list in place and returns the new head. */
    public static Node reverse(Node head) {
        Node previous = null;
        Node current = head;
        while (current != null) {
            Node next = current.next;   // remember the rest of the list
            current.next = previous;    // turn this node's link around
            previous = current;         // move previous forward
            current = next;             // move current forward
        }
        return previous;
    }

    private static void print(Node head) {
        StringBuilder sb = new StringBuilder();
        for (Node cur = head; cur != null; cur = cur.next) {
            sb.append(cur.data).append(" -> ");
        }
        sb.append("null");
        System.out.println(sb);
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int n = sc.nextInt();
            Node head = null;
            Node tail = null;
            for (int i = 0; i < n; i++) {
                Node node = new Node(sc.nextInt());
                if (head == null) {
                    head = node;
                } else {
                    tail.next = node;
                }
                tail = node;
            }

            print(reverse(head));
        }
    }
}