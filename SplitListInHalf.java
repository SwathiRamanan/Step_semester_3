public class SplitListInHalf {

    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    // Fast and slow pointers. First half gets the extra middle node.
    // Time O(n), Space O(1)
    static Node splitListInHalf(Node head) {
        if (head == null) return null;
        Node slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        Node second = slow.next;
        slow.next = null; // terminate first half
        return second;
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
        Node a = build(1, 2, 3, 4, 5);
        Node a2 = splitListInHalf(a);
        System.out.println("First half: " + str(a) + "  Returned: " + str(a2));

        Node b = build(1, 2, 3, 4);
        Node b2 = splitListInHalf(b);
        System.out.println("First half: " + str(b) + "  Returned: " + str(b2));
    }
}