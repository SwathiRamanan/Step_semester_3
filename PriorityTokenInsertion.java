import java.util.Scanner;
public class PriorityTokenInsertion {

    private static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public void addFirst(int token) {
        Node node = new Node(token);
        node.next = head;
        head = node;
        if (tail == null) {
            tail = node;
        }
        size++;
    }

    public void addLast(int token) {
        Node node = new Node(token);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public boolean insertAt(int index, int token) {
        if (index < 0 || index > size) {
            return false;
        }
        if (index == 0) {
            addFirst(token);
            return true;
        }
        if (index == size) {
            addLast(token);
            return true;
        }
        // walk to the node just before the index
        Node prev = head;
        for (int i = 0; i < index - 1; i++) {
            prev = prev.next;
        }
        Node node = new Node(token);
        node.next = prev.next;   // set the new node's next first
        prev.next = node;        // then link the previous node to it
        size++;
        return true;
    }

    public void print() {
        StringBuilder sb = new StringBuilder();
        for (Node cur = head; cur != null; cur = cur.next) {
            sb.append(cur.data).append(" -> ");
        }
        sb.append("null");
        System.out.println(sb);
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            PriorityTokenInsertion list = new PriorityTokenInsertion();

            int n = sc.nextInt();
            for (int i = 0; i < n; i++) {
                list.addLast(sc.nextInt());
            }

            int m = sc.nextInt();
            for (int i = 0; i < m; i++) {
                String op = sc.next();
                if (op.equalsIgnoreCase("addFirst")) {
                    list.addFirst(sc.nextInt());
                } else if (op.equalsIgnoreCase("addLast")) {
                    list.addLast(sc.nextInt());
                } else if (op.equalsIgnoreCase("insertAt")) {
                    int index = sc.nextInt();
                    int token = sc.nextInt();
                    list.insertAt(index, token);
                }
            }

            list.print();
        }
    }
}