public class DeleteLastNodeDoubly {

    static class DNode {
        int data;
        DNode prev, next;
        DNode(int data) { this.data = data; }
    }

    // Time O(n), Space O(1)
    static DNode deleteLastNodeDoubly(DNode head) {
        if (head == null) return null;      // empty list
        if (head.next == null) return null; // single node
        DNode last = head;
        while (last.next != null) last = last.next;
        last.prev.next = null;
        last.prev = null;
        return head;
    }

    static DNode build(int... vals) {
        DNode head = null, tail = null;
        for (int v : vals) {
            DNode n = new DNode(v);
            if (head == null) head = tail = n;
            else { tail.next = n; n.prev = tail; tail = n; }
        }
        return head;
    }

    static String str(DNode head) {
        if (head == null) return "(empty list)";
        StringBuilder sb = new StringBuilder();
        for (DNode c = head; c != null; c = c.next) {
            sb.append(c.data);
            if (c.next != null) sb.append(" <-> ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(str(deleteLastNodeDoubly(build(1, 2, 3)))); // 1 <-> 2
        System.out.println(str(deleteLastNodeDoubly(build(5))));       // (empty list)
    }
}