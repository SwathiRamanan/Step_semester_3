// Problem 3: BookInventory - "one door in, one door out" style class

public class BookInventory {

    private int copiesTotal;
    private int copiesAvailable;

    public BookInventory(int copiesTotal) {
        this.copiesTotal = copiesTotal;
        this.copiesAvailable = copiesTotal; // start with everything on the shelf
    }

    // takes one copy out, but never lets the count go below zero
    public void checkOut() {
        if (copiesAvailable > 0) {
            copiesAvailable = copiesAvailable - 1;
        }
        // if no copies are available, do nothing (silent rejection)
    }

    // returns one copy, but never lets the count go above copiesTotal
    public void checkIn() {
        if (copiesAvailable < copiesTotal) {
            copiesAvailable = copiesAvailable + 1;
        }
        // if already full, do nothing (silent rejection)
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    // quick manual test
    public static void main(String[] args) {
        BookInventory b = new BookInventory(3);
        b.checkOut();
        b.checkOut();
        b.checkOut();
        b.checkOut(); // 4th call, should be rejected
        System.out.println(b.getCopiesAvailable()); // 0

        b.checkIn();
        b.checkIn();
        b.checkIn();
        b.checkIn(); // 4th call, should be rejected
        System.out.println(b.getCopiesAvailable()); // 3
    }
}