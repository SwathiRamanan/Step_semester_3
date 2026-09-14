public class Problem2 {

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
            return "Standard Member | Sessions: " + sessionsAttended;
        }
    }

    // Generation 2: single inheritance
    public static class PremiumMember extends GymMember {
        protected String trainerName;

        public PremiumMember(String memberId, int monthlyFee, String trainerName) {
            super(memberId, monthlyFee);
            this.trainerName = trainerName;
        }

        @Override
        public String displayInfo() {
            return "Premium Member | Trainer: " + trainerName + " | Sessions: " + sessionsAttended;
        }
    }

    // Generation 3: multilevel inheritance (GymMember -> PremiumMember -> EliteMember)
    public static class EliteMember extends PremiumMember {
        private String lockerNumber;

        public EliteMember(String memberId, int monthlyFee, String trainerName, String lockerNumber) {
            super(memberId, monthlyFee, trainerName);
            this.lockerNumber = lockerNumber;
        }

        @Override
        public String displayInfo() {
            return "Elite Member | Trainer: " + trainerName + " | Locker: " + lockerNumber
                    + " | Sessions: " + sessionsAttended;
        }
    }

    // Hierarchical inheritance sibling: independent of PremiumMember
    public static class GroupClassMember extends GymMember {
        private String className;

        public GroupClassMember(String memberId, int monthlyFee, String className) {
            super(memberId, monthlyFee);
            this.className = className;
        }

        @Override
        public String displayInfo() {
            return "Group Class Member | Class: " + className + " | Sessions: " + sessionsAttended;
        }
    }

    // instanceof-only classification, no manual "type" field anywhere
    static String classifyGeneration(GymMember member) {
        if (member instanceof EliteMember) {
            return "Multilevel descendant (3 generations deep)";
        } else if (member instanceof GroupClassMember) {
            return "Hierarchical sibling (independent branch)";
        } else if (member instanceof PremiumMember) {
            return "Direct subclass";
        } else {
            return "Base class";
        }
    }

    // Polymorphic summation - no type checks needed
    static int getTotalSessionsAttended(GymMember[] members) {
        int total = 0;
        for (GymMember m : members) {
            total += m.getSessionsAttended();
        }
        return total;
    }

    public static void main(String[] args) {
        System.out.println(new GymMember("MEM1", 1000).displayInfo());
        System.out.println(new PremiumMember("MEM2", 2000, "Coach Riya").displayInfo());
        System.out.println(new EliteMember("MEM3", 3000, "Coach Arjun", "L12").displayInfo());
        System.out.println(new GroupClassMember("MEM4", 1500, "Zumba").displayInfo());

        EliteMember eliteMember = new EliteMember("MEM3", 3000, "Coach Arjun", "L12");
        GroupClassMember groupClassMember = new GroupClassMember("MEM4", 1500, "Zumba");
        System.out.println(classifyGeneration(eliteMember));       // Multilevel descendant (3 generations deep)
        System.out.println(classifyGeneration(groupClassMember));  // Hierarchical sibling (independent branch)

        PremiumMember premiumMember = new PremiumMember("MEM2", 2000, "Coach Riya");
        premiumMember.attendSession();
        premiumMember.attendSession();
        premiumMember.attendSession(); // 3

        eliteMember.attendSession();
        eliteMember.attendSession();   // 2

        groupClassMember.attendSession();
        groupClassMember.attendSession();
        groupClassMember.attendSession();
        groupClassMember.attendSession(); // 4

        System.out.println(getTotalSessionsAttended(
                new GymMember[]{premiumMember, eliteMember, groupClassMember})); // 9
    }
}