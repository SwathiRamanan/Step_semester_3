import java.util.*;

/* ---------- Question hierarchy: each type owns its evaluation ---------- */
abstract class Question {
    private final int number;
    private final String text;
    private final int points;

    protected Question(int number, String text, int points) {
        this.number = number;
        this.text = text;
        this.points = points;
    }
    public int getNumber() { return number; }
    public String getText() { return text; }
    public int getPoints() { return points; }

    protected abstract boolean isCorrect(String answer);

    /** Points earned for the given answer (0 if wrong or unanswered). */
    public int evaluate(String answer) {
        return (answer != null && isCorrect(answer)) ? points : 0;
    }
}

class MultipleChoiceQuestion extends Question {
    private final String correctOption;
    public MultipleChoiceQuestion(int n, String text, int points, String correctOption) {
        super(n, text, points);
        this.correctOption = correctOption;
    }
    protected boolean isCorrect(String a) { return correctOption.equalsIgnoreCase(a.trim()); }
}

class TrueFalseQuestion extends Question {
    private final boolean correct;
    public TrueFalseQuestion(int n, String text, int points, boolean correct) {
        super(n, text, points);
        this.correct = correct;
    }
    protected boolean isCorrect(String a) { return Boolean.parseBoolean(a.trim()) == correct
            && (a.trim().equalsIgnoreCase("true") || a.trim().equalsIgnoreCase("false")); }
}

class ShortAnswerQuestion extends Question {
    private final String expected;
    public ShortAnswerQuestion(int n, String text, int points, String expected) {
        super(n, text, points);
        this.expected = expected;
    }
    protected boolean isCorrect(String a) { return expected.equalsIgnoreCase(a.trim()); }
}

class Student {
    private final String name;
    public Student(String name) { this.name = name; }
    public String getName() { return name; }
}

class Examination {
    private final String title;
    private final List<Question> questions = new ArrayList<>();

    public Examination(String title) { this.title = title; }
    public String getTitle() { return title; }
    public void addQuestion(Question q) { questions.add(q); }
    public List<Question> getQuestions() { return Collections.unmodifiableList(questions); }
    public int getTotalPoints() { int t = 0; for (Question q : questions) t += q.getPoints(); return t; }
    public Question findQuestion(int number) {
        for (Question q : questions) if (q.getNumber() == number) return q;
        return null;
    }
}

enum AttemptStatus { IN_PROGRESS, SUBMITTED }

class Attempt {
    private final Student student;
    private final Examination exam;
    private final Map<Integer, String> answers = new LinkedHashMap<>();
    private AttemptStatus status = AttemptStatus.IN_PROGRESS;

    Attempt(Student student, Examination exam) { this.student = student; this.exam = exam; }

    public Student getStudent() { return student; }
    public Examination getExam() { return exam; }
    public AttemptStatus getStatus() { return status; }

    public void recordAnswer(int questionNumber, String answer) {
        if (status == AttemptStatus.SUBMITTED)
            throw new IllegalStateException("Cannot change answers for a submitted examination.");
        if (exam.findQuestion(questionNumber) == null)
            throw new IllegalArgumentException("Question " + questionNumber + " does not exist.");
        answers.put(questionNumber, answer);
    }

    /** Evaluates every question polymorphically; no type checks here. */
    public String submit() {
        if (status == AttemptStatus.SUBMITTED)
            throw new IllegalStateException("This examination is already submitted.");
        status = AttemptStatus.SUBMITTED;

        StringJoiner sj = new StringJoiner(", ");
        int score = 0;
        for (Question q : exam.getQuestions()) {
            int earned = q.evaluate(answers.get(q.getNumber()));
            score += earned;
            sj.add("Question " + q.getNumber() + ": " + (earned > 0 ? "Correct" : "Incorrect")
                    + " (" + earned + " points)");
        }
        return "Result: " + sj + ". Total score: " + score + "/" + exam.getTotalPoints() + ".";
    }
}

class ExamService {
    private final Map<String, Attempt> attempts = new HashMap<>();

    private String key(Student s, Examination e) { return s.getName() + "|" + e.getTitle(); }

    public Attempt start(Student s, Examination e) {
        Attempt existing = attempts.get(key(s, e));
        if (existing != null && existing.getStatus() == AttemptStatus.SUBMITTED) {
            System.out.println(s.getName() + " has already submitted " + e.getTitle() + ".");
            return null;
        }
        Attempt a = existing != null ? existing : new Attempt(s, e);
        attempts.put(key(s, e), a);
        System.out.println(e.getTitle() + " started by " + s.getName() + ".");
        return a;
    }

    public void answer(Attempt a, int questionNumber, String answer) {
        try {
            a.recordAnswer(questionNumber, answer);
            System.out.println("Answer recorded for Question " + questionNumber + ".");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void submit(Attempt a) {
        try {
            String result = a.submit();
            System.out.println(a.getExam().getTitle() + " submitted by " + a.getStudent().getName() + ".");
            System.out.println(result);
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}

public class OnlineExam {
    public static void main(String[] args) {
        Examination examA = new Examination("Exam A");
        examA.addQuestion(new MultipleChoiceQuestion(1, "Pick the right option", 5, "C"));
        examA.addQuestion(new TrueFalseQuestion(2, "Java supports multiple class inheritance", 5, false));

        ExamService service = new ExamService();
        Student s1 = new Student("Student 1");

        Attempt attempt = service.start(s1, examA);
        service.answer(attempt, 1, "C");
        service.answer(attempt, 2, "True");
        service.submit(attempt);
        service.answer(attempt, 1, "B");        // blocked
    }
}
