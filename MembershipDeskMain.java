
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

interface MembershipPlan {
    String getName();
    int getMonths();
    double calculateFee();
}

abstract class BasePlan implements MembershipPlan {
    protected static final double BASE_RATE = 1000.0;   // per month
    private final String name;
    private final int months;
    private final double discountPercent;

    protected BasePlan(String name, int months, double discountPercent) {
        this.name = name;
        this.months = months;
        this.discountPercent = discountPercent;
    }
    public String getName() { return name; }
    public int getMonths() { return months; }
    public double calculateFee() { return BASE_RATE * months * (1 - discountPercent / 100.0); }
}

class MonthlyPlan extends BasePlan   { public MonthlyPlan()   { super("Monthly", 1, 0); } }
class QuarterlyPlan extends BasePlan { public QuarterlyPlan() { super("Quarterly", 3, 10); } }
class AnnualPlan extends BasePlan    { public AnnualPlan()    { super("Annual", 12, 25); } }

enum MembershipStatus { ACTIVE, FROZEN, EXPIRED;
    String label() { return name().charAt(0) + name().substring(1).toLowerCase(); }
}

class Member {
    private final String name;
    public Member(String name) { this.name = name; }
    public String getName() { return name; }
}


class Membership {
    private final Member member;
    private final MembershipPlan plan;
    private final double fee;
    private MembershipStatus status = MembershipStatus.ACTIVE;

    public Membership(Member member, MembershipPlan plan) {
        this.member = member;
        this.plan = plan;
        this.fee = plan.calculateFee();
    }

    public Member getMember() { return member; }
    public MembershipPlan getPlan() { return plan; }
    public double getFee() { return fee; }
    public MembershipStatus getStatus() { return status; }

    public void checkIn() {
        if (status != MembershipStatus.ACTIVE)
            throw new IllegalStateException("Check-in denied: " + member.getName()
                    + "'s membership is " + status.label() + ".");
    }

    public void freeze() {
        if (status == MembershipStatus.EXPIRED) throw new IllegalStateException("Cannot freeze an Expired membership.");
        if (status == MembershipStatus.FROZEN)  throw new IllegalStateException("Membership is already Frozen.");
        status = MembershipStatus.FROZEN;
    }

    public void unfreeze() {
        if (status == MembershipStatus.EXPIRED) throw new IllegalStateException("Cannot unfreeze an Expired membership.");
        if (status == MembershipStatus.ACTIVE)  throw new IllegalStateException("Membership is not Frozen.");
        status = MembershipStatus.ACTIVE;
    }

    public void expire() {
        if (status == MembershipStatus.EXPIRED) throw new IllegalStateException("Membership is already Expired.");
        status = MembershipStatus.EXPIRED;
    }
}


class MembershipDesk {
    public Membership buy(Member member, MembershipPlan plan) {
        Membership m = new Membership(member, plan);
        System.out.println(String.format("%s membership created for %s. Fee: \u20B9%,.2f. Status: %s.",
                plan.getName(), member.getName(), m.getFee(), m.getStatus().label()));
        return m;
    }

    public void checkIn(Membership m) {
        try {
            m.checkIn();
            System.out.println(m.getMember().getName() + " checked in successfully.");
        } catch (IllegalStateException e) { System.out.println(e.getMessage()); }
    }

    public void freeze(Membership m) {
        try {
            m.freeze();
            System.out.println(m.getMember().getName() + "'s membership frozen. Status: " + m.getStatus().label() + ".");
        } catch (IllegalStateException e) { System.out.println(e.getMessage()); }
    }

    public void unfreeze(Membership m) {
        try {
            m.unfreeze();
            System.out.println(m.getMember().getName() + "'s membership unfrozen. Status: " + m.getStatus().label() + ".");
        } catch (IllegalStateException e) { System.out.println(e.getMessage()); }
    }

    public void expire(Membership m) {
        try {
            m.expire();
            System.out.println(m.getMember().getName() + "'s membership expired. Status: " + m.getStatus().label() + ".");
        } catch (IllegalStateException e) { System.out.println(e.getMessage()); }
    }
}

public class MembershipDeskMain{
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        MembershipDesk desk = new MembershipDesk();
        Membership asha = desk.buy(new Member("Asha"), new QuarterlyPlan());
        Membership ravi = desk.buy(new Member("Ravi"), new MonthlyPlan());

        desk.checkIn(asha);
        desk.freeze(asha);
        desk.checkIn(asha);       
        desk.expire(ravi);
        desk.freeze(ravi);        
    }
}