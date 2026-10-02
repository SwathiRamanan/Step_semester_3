import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

interface NotificationChannel {
    String getName();
    void send(Student student, String message);
}

class EmailChannel implements NotificationChannel {
    public String getName() { return "Email"; }
    public void send(Student s, String message) { System.out.println("[Email \u2192 " + s.getName() + "] " + message); }
}

class SmsChannel implements NotificationChannel {
    public String getName() { return "SMS"; }
    public void send(Student s, String message) { System.out.println("[SMS \u2192 " + s.getName() + "] " + message); }
}

class AppChannel implements NotificationChannel {
    public String getName() { return "App"; }
    public void send(Student s, String message) { System.out.println("[App \u2192 " + s.getName() + "] " + message); }
}

class Student {
    private final String name;
    private final String department;
    private final Set<NotificationChannel> channels = new LinkedHashSet<>();

    public Student(String name, String department, NotificationChannel... preferred) {
        this.name = name;
        this.department = department;
        channels.addAll(Arrays.asList(preferred));
    }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public void addChannel(NotificationChannel c) { channels.add(c); }
    public void removeChannel(NotificationChannel c) { channels.remove(c); }
    public Set<NotificationChannel> getChannels() { return Collections.unmodifiableSet(channels); }
}

class Notice {
    private final String title;
    private final Set<String> targetDepartments;

    public Notice(String title, String... departments) {
        if (title == null || title.trim().isEmpty())
            throw new IllegalArgumentException("A title is required.");
        if (departments == null || departments.length == 0)
            throw new IllegalArgumentException("At least one target department is required.");
        this.title = title.trim();
        this.targetDepartments = new LinkedHashSet<>(Arrays.asList(departments));
    }
    public String getTitle() { return title; }
    public Set<String> getTargetDepartments() { return Collections.unmodifiableSet(targetDepartments); }
    public boolean targets(String department) { return targetDepartments.contains(department); }
}

class NoticeBoard {
    private final List<Student> students = new ArrayList<>();

    public void registerStudent(Student s) { students.add(s); }

    public List<Student> findRecipients(Notice notice) {
        List<Student> result = new ArrayList<>();
        for (Student s : students) if (notice.targets(s.getDepartment())) result.add(s);
        return result;
    }

    public void post(Notice notice) {
        System.out.println("Notice '" + notice.getTitle() + "' posted to "
                + String.join(", ", notice.getTargetDepartments()) + ".");
        for (Student s : findRecipients(notice))
            for (NotificationChannel ch : s.getChannels())
                ch.send(s, notice.getTitle());
    }

    public void post(String title, String... departments) {
        try {
            post(new Notice(title, departments));
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot post notice: " + e.getMessage());
        }
    }
}

public class NoticeBroadcaster {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        NoticeBoard board = new NoticeBoard();
        board.registerStudent(new Student("Asha", "CSE", new EmailChannel(), new AppChannel()));
        board.registerStudent(new Student("Ravi", "ECE", new SmsChannel()));

        board.post("Lab Closed Tomorrow", "CSE");
        board.post("Fee Deadline Extended", "CSE", "ECE");
        board.post("Sports Day");                          
    }
}
