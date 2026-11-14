public class InsertAtTailDoubly {

    static class DNode {
        int data;
        DNode prev, next;
        DNode(int data) { this.data = data; }
    }

    // Time O(n), Space O(1)
    static DNode insertAtTailDoubly(DNode head, int value) {
        DNode newNode = new DNode(value);
        if (head == null) return newNode;
        DNode tail = head;
        while (tail.next != null) tail = tail.next;
        tail.next = newNode;
        newNode.prev = tail;
        return head;
    }

    static String str(DNode head) {
        StringBuilder sb = new StringBuilder();
        for (DNode c = head; c != null; c = c.next) {
            sb.append(c.data);
            if (c.next != null) sb.append(" <-> ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        DNode head = null;
        for (int v : new int[]{1, 2, 3}) head = insertAtTailDoubly(head, v);
        head = insertAtTailDoubly(head, 4);
        System.out.println(str(head));                          // 1 <-> 2 <-> 3 <-> 4
        System.out.println(str(insertAtTailDoubly(null, 7)));   // 7
    }
}