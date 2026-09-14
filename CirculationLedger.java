// Problem 5, part 3: nightly circulation processor

public class CirculationLedger {

    // one-time, class-level setup
    static String sharedBranchCode;

    static {
        sharedBranchCode = "PAGETURNER-MAIN";
    }

    static String processNightlyCirculation(LoanReceipt[] receipts) {

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        for (int i = 0; i < receipts.length; i++) {

            LoanReceipt current = receipts[i];

            if (current == null) {
                nullSkipped++;
                continue;
            }

            if (current instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }

            processed++;
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + referenceOnly + " reference-only | "
                + regular + " regular";
    }

    public static void main(String[] args) {

        LoanReceipt[] receipts = {
            new ReferenceOnlyLoanReceipt(
                "LIB-001",
                new String[]{"BK-200"},
                "Reading Room 3"
            ),
            null,
            new LoanReceipt(
                "LIB-002",
                new String[]{"BK-201"}
            )
        };

        System.out.println(processNightlyCirculation(receipts));
    }
}