import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/* ---------- Assignment hierarchy: each type owns its penalty rule ---------- */
abstract class Assignment {
    private final String title;
    private final int maxMarks;
    private final LocalDate dueDate;

    protected Assignment(String title, int maxMarks, LocalDate dueDate) {
        this.title = title;
        this.maxMarks = maxMarks;
        this.dueDate = dueDate;
    }
    public String getTitle() { return title; }
    public int getMaxMarks() { return maxMarks; }
    public LocalDate getDueDate() { return dueDate; }

    /** Penalty percentage lost per day late. */
    protected abstract double penaltyPercentPerDay();

    /** Total penalty percentage (capped at 100). */
    public double calculatePenaltyPercent(long daysLate) {
        return Math.min(100.0, penaltyPercentPerDay() * Math.max(0, daysLate));
    }

    public double applyPenalty(double awarded, long daysLate) {
        return awarded * (1 - calculatePenaltyPercent(daysLate) / 100.0);
    }
}

class CodingAssignment extends Assignment {
    public CodingAssignment(String title, int maxMarks, LocalDate due) { super(title, maxMarks, due); }
    protected double penaltyPercentPerDay() { return 10; }
}

class WrittenAssignment extends Assignment {
    public WrittenAssignment(String title, int maxMarks, LocalDate due) { super(title, maxMarks, due); }
    protected double penaltyPercentPerDay() { return 20; }
}

class Student {
    private final String name;
    public Student(String name) { this.name = name; }
    public String getName() { return name; }
}

enum SubmissionStatus { SUBMITTED, GRADED }

/* ---------- Submission guards its own status ---------- */
class Submission {
    private final Student student;
    private final Assignment assignment;
    private LocalDate submittedOn;
    private SubmissionStatus status;
    private double finalMarks;
    private double penaltyPercent;

    Submission(Student student, Assignment assignment, LocalDate submittedOn) {
        this.student = student;
        this.assignment = assignment;
        this.submittedOn = submittedOn;
        this.status = SubmissionStatus.SUBMITTED;
    }

    public Student getStudent() { return student; }
    public Assignment getAssignment() { return assignment; }
    public SubmissionStatus getStatus() { return status; }
    public double getFinalMarks() { return finalMarks; }
    public double getPenaltyPercent() { return penaltyPercent; }

    /** Late days are the Submission's responsibility: it knows both dates. */
    public long getDaysLate() {
        return Math.max(0, ChronoUnit.DAYS.between(assignment.getDueDate(), submittedOn));
    }

    void resubmit(LocalDate newDate) {
        if (status == SubmissionStatus.GRADED)
            throw new IllegalStateException("Cannot resubmit: '" + assignment.getTitle() + "' has already been graded.");
        this.submittedOn = newDate;
    }

    public double grade(double awarded) {
        if (status != SubmissionStatus.SUBMITTED)
            throw new IllegalStateException("Only a submitted work can be graded.");
        if (awarded < 0 || awarded > assignment.getMaxMarks())
            throw new IllegalArgumentException("Marks must be between 0 and " + assignment.getMaxMarks() + ".");
        long late = getDaysLate();
        penaltyPercent = assignment.calculatePenaltyPercent(late);
        finalMarks = assignment.applyPenalty(awarded, late);
        status = SubmissionStatus.GRADED;
        return finalMarks;
    }
}

/* ---------- Portal workflow (independent of assignment type) ---------- */
class SubmissionPortal {
    private final Map<String, Submission> submissions = new HashMap<>();

    private String key(Student s, Assignment a) { return s.getName() + "|" + a.getTitle(); }

    private static String fmt(double d) {
        return d == Math.rint(d) ? String.valueOf((long) d) : String.format("%.2f", d);
    }

    public void submit(Student student, Assignment assignment, LocalDate date) {
        try {
            Submission existing = submissions.get(key(student, assignment));
            Submission sub;
            if (existing == null) {
                sub = new Submission(student, assignment, date);
                submissions.put(key(student, assignment), sub);
            } else {
                existing.resubmit(date);
                sub = existing;
            }
            long late = sub.getDaysLate();
            String when = late == 0 ? "on time" : late + (late == 1 ? " day late" : " days late");
            System.out.println(student.getName() + "'s submission for '" + assignment.getTitle()
                    + "' received (" + when + "). Status: Submitted.");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    public void grade(Student student, Assignment assignment, double awarded) {
        Submission sub = submissions.get(key(student, assignment));
        if (sub == null) {
            System.out.println("Cannot grade: no submission found for " + student.getName() + ".");
            return;
        }
        try {
            double result = sub.grade(awarded);
            String penalty = sub.getPenaltyPercent() > 0
                    ? " after " + fmt(sub.getPenaltyPercent()) + "% late penalty" : "";
            System.out.println(student.getName() + " graded: " + fmt(result) + "/" + assignment.getMaxMarks()
                    + penalty + ". Status: Graded.");
        } catch (RuntimeException e) {
            System.out.println("Cannot grade: " + e.getMessage());
        }
    }
}

public class SubmissionPortalMain {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        Assignment lab = new CodingAssignment("Linked List Lab", 50, LocalDate.of(2026, 3, 10));
        Assignment essay = new WrittenAssignment("Design Essay", 50, LocalDate.of(2026, 3, 12));
        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        SubmissionPortal portal = new SubmissionPortal();

        portal.submit(asha, lab, LocalDate.of(2026, 3, 10));
        portal.submit(ravi, essay, LocalDate.of(2026, 3, 14));
        portal.grade(asha, lab, 45);
        portal.grade(ravi, essay, 40);
        portal.submit(asha, lab, LocalDate.of(2026, 3, 11));   // blocked
    }
}
