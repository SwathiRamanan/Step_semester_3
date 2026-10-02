import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/* ---------- Wash types: new type = new class, no change elsewhere ---------- */
interface WashType {
    String getName();
    int getDurationMinutes();
    double getCharge();
}

class QuickWash implements WashType {
    public String getName() { return "Quick"; }
    public int getDurationMinutes() { return 30; }
    public double getCharge() { return 20; }
}

class NormalWash implements WashType {
    public String getName() { return "Normal"; }
    public int getDurationMinutes() { return 45; }
    public double getCharge() { return 30; }
}

class HeavyWash implements WashType {
    public String getName() { return "Heavy"; }
    public int getDurationMinutes() { return 60; }
    public double getCharge() { return 45; }
}

/* ---------- Core classes ---------- */
class Student {
    private final String name;
    public Student(String name) { this.name = name; }
    public String getName() { return name; }
}

class WashCycle {
    private final Student student;
    private final WashingMachine machine;
    private final WashType washType;

    WashCycle(Student student, WashingMachine machine, WashType washType) {
        this.student = student;
        this.machine = machine;
        this.washType = washType;
    }
    public Student getStudent() { return student; }
    public WashingMachine getMachine() { return machine; }
    public WashType getWashType() { return washType; }
    public double calculateCharge() { return washType.getCharge(); }
}

class MachineBusyException extends Exception {
    public MachineBusyException(String id) {
        super("Machine " + id + " is currently busy.");
    }
}

class WashingMachine {
    private final String id;
    private WashCycle currentCycle;          // null => free; private => no outside change

    public WashingMachine(String id) { this.id = id; }
    public String getId() { return id; }
    public boolean isBusy() { return currentCycle != null; }

    public WashCycle startWash(Student student, WashType type) throws MachineBusyException {
        if (isBusy()) throw new MachineBusyException(id);
        currentCycle = new WashCycle(student, this, type);
        return currentCycle;
    }

    public void completeCycle() {
        if (!isBusy()) throw new IllegalStateException(id + " has no wash in progress.");
        currentCycle = null;
    }
}

/* ---------- Common booking logic (never changes when wash types are added) ---------- */
class LaundryService {
    public void startWash(Student student, WashingMachine machine, WashType type) {
        try {
            WashCycle cycle = machine.startWash(student, type);
            System.out.println(String.format("%s wash started on %s for %s (%d min). Charge: \u20B9%.2f.",
                    type.getName(), machine.getId(), student.getName(),
                    type.getDurationMinutes(), cycle.calculateCharge()));
        } catch (MachineBusyException e) {
            System.out.println("Machine " + machine.getId() + " is currently busy.");
        }
    }

    public void completeWash(WashingMachine machine) {
        machine.completeCycle();
        System.out.println(machine.getId() + " cycle completed. " + machine.getId() + " is now free.");
    }
}

public class LaundryQueue {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        WashingMachine m1 = new WashingMachine("M1");
        WashingMachine m2 = new WashingMachine("M2");
        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");
        LaundryService service = new LaundryService();

        service.startWash(asha, m1, new QuickWash());
        service.startWash(ravi, m1, new HeavyWash());   // busy
        service.startWash(ravi, m2, new HeavyWash());
        service.completeWash(m1);
        service.startWash(neha, m1, new NormalWash());
    }
}