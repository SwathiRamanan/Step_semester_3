public class Problem4 {

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
        }

        public void attendSession() {
            sessionsAttended++;
        }

        public int getSessionsAttended() {
            return sessionsAttended;
        }

        public String displayInfo() {
            return "Standard | Sessions: " + sessionsAttended;
        }
    }

    public static class PremiumMember extends GymMember {
        protected String trainerName;

        public PremiumMember(String memberId, int monthlyFee, String trainerName) {
            super(memberId, monthlyFee);
            this.trainerName = trainerName;
        }

        @Override
        public String displayInfo() {
            return "Premium | Trainer: " + trainerName + " | Sessions: " + sessionsAttended;
        }

        public String getTrainerName() {
            return trainerName;
        }
    }

    // Polymorphic loop + guarded downcast, single StringBuilder across iterations
    static String batchPrint(GymMember[] members) {
        StringBuilder sb = new StringBuilder();
        for (GymMember member : members) {
            sb.append(member.displayInfo());
            if (member instanceof PremiumMember) {
                PremiumMember pm = (PremiumMember) member; // safe: guarded by instanceof above
                sb.append(" [Trainer via downcast: ").append(pm.getTrainerName()).append("]");
            }
            sb.append(" | ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String result = batchPrint(new GymMember[]{
                new GymMember("MEM6", 1000),
                new PremiumMember("MEM7", 2000, "Coach Riya")
        });
        System.out.println(result);
        // Expected: Standard | Sessions: 0 | Premium | Trainer: Coach Riya | Sessions: 0 [Trainer via downcast: Coach Riya] |

        // Demonstrates why the guard matters: an unguarded downcast fails at runtime
        GymMember plain = new GymMember("MEM8", 1000);
        try {
            PremiumMember bad = (PremiumMember) plain;
            System.out.println("Cast succeeded (unexpected): " + bad);
        } catch (ClassCastException e) {
            System.out.println("ClassCastException at runtime");
        }
    }
}