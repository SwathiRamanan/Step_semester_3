import java.util.Scanner;
public class CancelledOrdersCleanup {

    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    /** Removes every node whose value equals code and returns the (possibly new) head. */
    public static Node removeAll(Node head, int code) {
        // 1. remove matching nodes from the front by moving head
        while (head != null && head.data == code) {
            head = head.next;
        }
        // 2. walk with a previous reference and skip matching nodes
        Node prev = head;
        while (prev != null && prev.next != null) {
            if (prev.next.data == code) {
                prev.next = prev.next.next;   // unlink, stay on prev
            } else {
                prev = prev.next;
            }
        }
        return head;
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
            int code = sc.nextInt();

            print(removeAll(head, code));
        }
    }
}