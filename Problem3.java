import java.util.Arrays;

public class Problem3 {

    public static class GymMember {
        protected String memberId;
        protected int monthlyFee;

        // private array field; only base class writes to it, via chargeLateFee
        private int[] lateFeeHistory = new int[10];
        private int feeCount = 0;

        public GymMember(String memberId, int monthlyFee) {
            if (memberId == null || memberId.trim().isEmpty() || memberId.length() < 4) {
                throw new IllegalArgumentException("Invalid memberId: " + memberId);
            }
            this.memberId = memberId;
            this.monthlyFee = monthlyFee;
        }

        protected void chargeLateFee(int amount) {
            lateFeeHistory[feeCount] = amount;
            feeCount++;
        }

        // Defensive copy - callers never touch the real internal array
        public int[] getLateFeeHistory() {
            return Arrays.copyOf(lateFeeHistory, feeCount);
        }

        public int getTotalLateFees() {
            int total = 0;
            for (int i = 0; i < feeCount; i++) {
                total += lateFeeHistory[i];
            }
            return total;
        }
    }

    public static class PremiumMember extends GymMember {
        protected String trainerName;

        public PremiumMember(String memberId, int monthlyFee, String trainerName) {
            super(memberId, monthlyFee);
            this.trainerName = trainerName;
        }

        // Halve the fee, then reuse the parent's deduction + recording logic
        @Override
        protected void chargeLateFee(int amount) {
            super.chargeLateFee(amount / 2);
        }
    }

    public static void main(String[] args) {
        PremiumMember p = new PremiumMember("MEM5", 2000, "Coach Riya");
        p.chargeLateFee(200);
        System.out.println(p.getTotalLateFees()); // 100

        int[] history = p.getLateFeeHistory();
        history[0] = 999; // tampering with the returned copy
        System.out.println(Arrays.toString(p.getLateFeeHistory())); // [100]
    }
}