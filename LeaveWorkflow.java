import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/* ---------- Leave policy: separate from request/state management ---------- */
interface LeavePolicy {
    void validate(int days);                  // throws IllegalArgumentException if not allowed
}

class MaxDaysPolicy implements LeavePolicy {
    private final String type;
    private final int maxDays;
    MaxDaysPolicy(String type, int maxDays) { this.type = type; this.maxDays = maxDays; }
    public void validate(int days) {
        if (days > maxDays)
            throw new IllegalArgumentException(type + " employees can request at most " + maxDays + " days at a time.");
    }
}

/* ---------- Employee hierarchy ---------- */
abstract class Employee {
    private final String name;
    protected Employee(String name) { this.name = name; }
    public String getName() { return name; }
    public abstract LeavePolicy getLeavePolicy();
}

class FullTimeEmployee extends Employee {
    public FullTimeEmployee(String name) { super(name); }
    public LeavePolicy getLeavePolicy() { return new MaxDaysPolicy("Full-time", 20); }
}

class PartTimeEmployee extends Employee {
    public PartTimeEmployee(String name) { super(name); }
    public LeavePolicy getLeavePolicy() { return new MaxDaysPolicy("Part-time", 10); }
}

class Contractor extends Employee {
    public Contractor(String name) { super(name); }
    public LeavePolicy getLeavePolicy() { return new MaxDaysPolicy("Contractor", 5); }
}

enum LeaveStatus {
    PENDING, APPROVED, REJECTED;
    String label() { return name().charAt(0) + name().substring(1).toLowerCase(); }
}

/* ---------- LeaveRequest owns and guards its state ---------- */
class LeaveRequest {
    private final Employee employee;
    private final LocalDate start;
    private final LocalDate end;
    private LeaveStatus status = LeaveStatus.PENDING;
    private Manager reviewedBy;

    LeaveRequest(Employee employee, LocalDate start, LocalDate end) {
        this.employee = employee;
        this.start = start;
        this.end = end;
    }

    public Employee getEmployee() { return employee; }
    public LeaveStatus getStatus() { return status; }
    public Manager getReviewedBy() { return reviewedBy; }
    public int getDays() { return (int) ChronoUnit.DAYS.between(start, end) + 1; }

    public String period() {
        String sm = start.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        String em = end.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        String endPart = start.getMonth() == end.getMonth() ? String.valueOf(end.getDayOfMonth())
                                                            : em + " " + end.getDayOfMonth();
        return sm + " " + start.getDayOfMonth() + "-" + endPart;
    }

    /** Only PENDING requests may move, and only to APPROVED or REJECTED. */
    public void changeStatus(LeaveStatus next, Manager reviewer) {
        if (status != LeaveStatus.PENDING || next == LeaveStatus.PENDING)
            throw new IllegalStateException("Cannot change leave request status from "
                    + status.label() + " to " + next.label() + ".");
        status = next;
        reviewedBy = reviewer;
    }
}

class Manager {
    private final String name;
    public Manager(String name) { this.name = name; }
    public String getName() { return name; }

    public void approve(LeaveRequest r) { review(r, LeaveStatus.APPROVED, "approved"); }
    public void reject(LeaveRequest r)  { review(r, LeaveStatus.REJECTED, "rejected"); }

    private void review(LeaveRequest r, LeaveStatus next, String verb) {
        try {
            r.changeStatus(next, this);
            System.out.println(r.getEmployee().getName() + "'s leave request (" + r.period() + ") "
                    + verb + ". Status: " + r.getStatus().label() + ".");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}

/* ---------- Workflow: works for any Employee subtype ---------- */
class LeaveService {
    public LeaveRequest submit(Employee emp, LocalDate start, LocalDate end) {
        try {
            if (end.isBefore(start)) throw new IllegalArgumentException("End date cannot be before start date.");
            LeaveRequest r = new LeaveRequest(emp, start, end);
            emp.getLeavePolicy().validate(r.getDays());
            System.out.println("Leave request submitted for " + emp.getName() + " (" + r.period()
                    + "). Status: " + r.getStatus().label() + ".");
            return r;
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot submit leave request: " + e.getMessage());
            return null;
        }
    }

    public void changeStatus(LeaveRequest r, LeaveStatus next) {
        try {
            r.changeStatus(next, r.getReviewedBy());
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}

public class LeaveWorkflow {
    public static void main(String[] args) {
        LeaveService service = new LeaveService();
        Employee john = new FullTimeEmployee("John");
        Employee jane = new PartTimeEmployee("Jane");
        Manager alice = new Manager("Alice");
        Manager bob = new Manager("Bob");

        LeaveRequest johnReq = service.submit(john, LocalDate.of(2027, 1, 1), LocalDate.of(2027, 1, 5));
        alice.approve(johnReq);
        LeaveRequest janeReq = service.submit(jane, LocalDate.of(2027, 2, 10), LocalDate.of(2027, 2, 11));
        bob.reject(janeReq);
        service.changeStatus(johnReq, LeaveStatus.PENDING);     // blocked
    }
}
