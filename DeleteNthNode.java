public class DeleteNthNode {

    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    // n is 1-based and assumed valid. Time O(n), Space O(1)
    static Node deleteNthNode(Node head, int n) {
        if (head == null) return null;
        if (n == 1) return head.next;
        Node current = head;
        for (int i = 0; i < n - 2; i++) current = current.next;
        current.next = current.next.next;
        return head;
    }

    static Node build(int... vals) {
        Node head = null, tail = null;
        for (int v : vals) {
            Node n = new Node(v);
            if (head == null) head = tail = n;
            else { tail.next = n; tail = n; }
        }
        return head;
    }

    static String str(Node head) {
        if (head == null) return "(empty list)";
        StringBuilder sb = new StringBuilder();
        for (Node c = head; c != null; c = c.next) {
            sb.append(c.data);
            if (c.next != null) sb.append(" -> ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(str(deleteNthNode(build(10, 20, 30, 40), 3))); // 10 -> 20 -> 40
        System.out.println(str(deleteNthNode(build(10, 20, 30, 40), 1))); // 20 -> 30 -> 40
    }
}