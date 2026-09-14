public class Problem5 {

    public static class GymMember {
        private static int counter = 2000; // shared static counter

        protected final String membershipNumber; // final: set once, never reassignable
        protected int monthlyFee;
        private int feesPaid = 0;
        private String lastPaymentMode = null;

        public GymMember(int monthlyFee) {
            counter++;
            this.membershipNumber = "GYM-" + counter;
            this.monthlyFee = monthlyFee;
        }

        public void payFee(int amount) {
            feesPaid += amount;
        }

        // Overload records the mode, then delegates to the flat-amount version
        public void payFee(int amount, String mode) {
            this.lastPaymentMode = mode;
            payFee(amount);
        }

        public int getFeesPaid() {
            return feesPaid;
        }

        public String getMembershipNumber() {
            return membershipNumber;
        }

        // Format check via charAt()/Character checks, length verified first
        public static boolean isValidReferralCode(String code) {
            if (code == null || code.length() != 4) {
                return false;
            }
            if (code.charAt(0) != 'G') {
                return false;
            }
            if (!Character.isDigit(code.charAt(1)) || !Character.isDigit(code.charAt(2))) {
                return false;
            }
            if (!Character.isUpperCase(code.charAt(3))) {
                return false;
            }
            return true;
        }

        public static int getMembersEnrolled() {
            return counter - 2000;
        }
    }

    // Hierarchical sibling, independent of PremiumMember
    public static class GroupClassMember extends GymMember {
        private String className;

        public GroupClassMember(int monthlyFee, String className) {
            super(monthlyFee);
            this.className = className;
        }
    }

    // instanceof-based split; never throws on a null entry
    static String processWeeklyCheckIn(GymMember[] members) {
        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;

        for (GymMember member : members) {
            if (member == null) {
                nullSkipped++;
                continue;
            }
            processed++;
            if (member instanceof GroupClassMember) {
                group++;
            } else {
                individual++;
            }
        }
        return processed + " processed | " + nullSkipped + " null skipped | "
                + group + " group | " + individual + " individual";
    }

    public static void main(String[] args) {
        GymMember m1 = new GymMember(1000);
        System.out.println(m1.getMembershipNumber()); // GYM-2001
        System.out.println(GymMember.getMembersEnrolled()); // 1

        System.out.println(GymMember.isValidReferralCode("G45B")); // true
        System.out.println(GymMember.isValidReferralCode("G4B")); // false
        System.out.println(GymMember.isValidReferralCode("X45B")); // false

        m1.payFee(500);
        m1.payFee(500, "UPI");
        System.out.println(m1.getFeesPaid()); // 1000

        String result = processWeeklyCheckIn(new GymMember[] {
                new GroupClassMember(1500, "Zumba"),
                null,
                new GymMember(1000)
        });
        System.out.println(result);
        // Expected: 2 processed | 1 null skipped | 1 group | 1 individual
    }
}