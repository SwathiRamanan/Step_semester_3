// Problem 5, part 1: a truly immutable LoanReceipt

public final class LoanReceipt {

    private final String memberId;
    private final String[] bookIds;

    public LoanReceipt(String memberId, String[] bookIds) {
        this.memberId = memberId;
        // defensive copy going IN, so outside changes to the original array
        // can never affect this object
        this.bookIds = copyArray(bookIds);
    }

    public String getMemberId() {
        return memberId;
    }

    public String[] getBookIds() {
        // defensive copy going OUT, so the caller can't mutate our real data
        return copyArray(bookIds);
    }

    // "wither" method: returns a brand new object instead of changing this one
    public LoanReceipt withCorrectedBookId(int index, String newId) {
        String[] newBookIds = copyArray(bookIds);
        newBookIds[index] = newId;
        return new LoanReceipt(memberId, newBookIds);
    }

    // small helper used both directions
    private String[] copyArray(String[] source) {
        String[] copy = new String[source.length];
        for (int i = 0; i < source.length; i++) {
            copy[i] = source[i];
        }
        return copy;
    }

    // quick manual test
    public static void main(String[] args) {
        LoanReceipt r = new LoanReceipt("LIB-8841", new String[]{"BK-100", "BK-101"});

        String[] ids = r.getBookIds();
        ids[0] = "HACKED"; // this should NOT affect the real receipt
        System.out.println(r.getBookIds()[0]); // BK-100

        LoanReceipt corrected = r.withCorrectedBookId(1, "BK-102");
        System.out.println(java.util.Arrays.toString(r.getBookIds()));         // [BK-100, BK-101]
        System.out.println(java.util.Arrays.toString(corrected.getBookIds())); // [BK-100, BK-102]
    }
}