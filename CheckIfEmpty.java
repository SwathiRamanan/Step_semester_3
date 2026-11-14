public class CheckIfEmpty {

    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    // Time O(1), Space O(1)
    static boolean checkIfEmpty(Node head) {
        return head == null;
    }

    public static void main(String[] args) {
        System.out.println(checkIfEmpty(null));          // true
        System.out.println(checkIfEmpty(new Node(8)));   // false
    }
}