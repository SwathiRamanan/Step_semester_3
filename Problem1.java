public class Problem1 {

    // ---- GymMember: base class with validated constructor ----
    public static class GymMember {
        protected String memberId;
        protected int monthlyFee;
        protected int sessionsAttended;

        public GymMember(String memberId, int monthlyFee) {
            if (memberId == null || memberId.trim().isEmpty() || memberId.length() < 4) {
                throw new IllegalArgumentException("Invalid memberId: " + memberId);
            }
            this.memberId = memberId;
            this.monthlyFee = monthlyFee;
            this.sessionsAttended = 0;
        }

        public void attendSession() {
            sessionsAttended++;
        }

        public int getSessionsAttended() {
            return sessionsAttended;
        }
    }

    // ---- PremiumMember: single inheritance, forwards shared fields via super ----
    public static class PremiumMember extends GymMember {
        protected String trainerName;

        public PremiumMember(String memberId, int monthlyFee, String trainerName) {
            super(memberId, monthlyFee);
            this.trainerName = trainerName;
        }
    }

    // ---- Batch sign-up: try/catch per entry, no pre-validation ----
    static String signUpBatch(String[] memberIds, int monthlyFee) {
        int signedUp = 0;
        int rejected = 0;
        for (String id : memberIds) {
            try {
                new GymMember(id, monthlyFee);
                signedUp++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        return "Signed Up: " + signedUp + " | Rejected: " + rejected;
    }

    public static void main(String[] args) {
        // Example 1
        try {
            new GymMember("GM1", 1000);
            System.out.println("construction succeeded (unexpected)");
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        // Example 2
        PremiumMember p = new PremiumMember("MEM01", 2000, "Coach Riya");
        p.attendSession();
        p.attendSession();
        System.out.println(p.getSessionsAttended()); // 2

        // Example 3
        System.out.println(signUpBatch(
                new String[]{"MEM1", "GM1", "MEM2", " ", "MEM3"}, 1000));
        // Expected: Signed Up: 3 | Rejected: 2
    }
}