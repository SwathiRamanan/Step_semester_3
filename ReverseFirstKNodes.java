public class ReverseFirstKNodes {

    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    // Time O(k), Space O(1)
    static Node reverseFirstKNodes(Node head, int k) {
        if (head == null || k <= 1) return head;
        Node prev = null, current = head, next = null;
        int count = 0;
        while (current != null && count < k) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
            count++;
        }
        head.next = current; // old head is now the k-th node; link to (k+1)-th
        return prev;         // new head
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
        System.out.println(str(reverseFirstKNodes(build(1, 2, 3, 4, 5), 3))); // 3 -> 2 -> 1 -> 4 -> 5
        System.out.println(str(reverseFirstKNodes(build(1, 2, 3, 4, 5), 5))); // 5 -> 4 -> 3 -> 2 -> 1
    }
}