public class InsertAtEndCircular {

    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    // Time O(n), Space O(1)
    static Node insertAtEndCircular(Node head, int value) {
        Node newNode = new Node(value);
        if (head == null) {
            newNode.next = newNode; // points to itself
            return newNode;
        }
        Node last = head;
        while (last.next != head) last = last.next;
        last.next = newNode;
        newNode.next = head;
        return head;
    }

    static String str(Node head) {
        if (head == null) return "(empty list)";
        StringBuilder sb = new StringBuilder();
        Node c = head;
        do {
            sb.append(c.data).append(" -> ");
            c = c.next;
        } while (c != head);
        return sb.append("(back to ").append(head.data).append(")").toString();
    }

    public static void main(String[] args) {
        Node a = new Node(1), b = new Node(2), c = new Node(3);
        a.next = b; b.next = c; c.next = a;
        System.out.println(str(insertAtEndCircular(a, 4)));     // 1 -> 2 -> 3 -> 4 -> (back to 1)
        System.out.println(str(insertAtEndCircular(null, 9)));  // 9 -> (back to 9)
    }
}