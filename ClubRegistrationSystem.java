import java.util.*;

// Problem 3: Student Club Registration Management
class Student {
    private final String rollNumber;
    private final String name;

    Student(String rollNumber, String name) {
        this.rollNumber = rollNumber;
        this.name = name;
    }

    // Uniqueness is decided by roll number only.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student other = (Student) o;
        return rollNumber.equals(other.rollNumber);
    }

    @Override
    public int hashCode() {
        return rollNumber.hashCode();
    }

    @Override
    public String toString() {
        return rollNumber + " " + name;
    }
}

public class ClubRegistrationSystem {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Set<Student> members = new HashSet<>();
            List<String[]> containsOps = new ArrayList<>();

            // Phase 1: all ADD operations (in order)
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] p = line.split("\\s+", 3);
                String name = p.length > 2 ? p[2] : "";

                if (p[0].equalsIgnoreCase("ADD")) {
                    System.out.println(members.add(new Student(p[1], name)) ? "Added" : "duplicate rejected");
                } else if (p[0].equalsIgnoreCase("CONTAINS")) {
                    containsOps.add(new String[]{p[1], name});
                }
            }

            // Phase 2: report count, then answer CONTAINS queries
            System.out.println("member count " + members.size());
            for (String[] q : containsOps) {
                System.out.println("contains: " + members.contains(new Student(q[0], q[1])));
            }
        }
    }
}