public class CountNodes {

    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    // Time O(n), Space O(1)
    static int countNodes(Node head) {
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    public static void main(String[] args) {
        Node a = new Node(5);
        a.next = new Node(10);
        a.next.next = new Node(15);
        System.out.println(countNodes(a));     // 3
        System.out.println(countNodes(null));  // 0
    }
}