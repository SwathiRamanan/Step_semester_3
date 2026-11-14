public class RemoveFirstKNodes {

    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    // Time O(k), Space O(1)
    static Node removeFirstKNodes(Node head, int k) {
        while (k > 0 && head != null) {
            head = head.next;
            k--;
        }
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
        System.out.println(str(removeFirstKNodes(build(1, 2, 3, 4, 5), 2))); // 3 -> 4 -> 5
        System.out.println(str(removeFirstKNodes(build(1, 2), 5)));          // (empty list)
    }
}