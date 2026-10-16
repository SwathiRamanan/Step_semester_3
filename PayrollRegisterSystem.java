import java.util.*;

// Problem 5: Payroll Register System
abstract class Staff {
    protected final String name;

    Staff(String name) { this.name = name; }

    abstract String getType();
    abstract double calculatePay();

    static String fmt(double v) {
        return v == Math.floor(v) ? String.valueOf((long) v) : String.valueOf(v);
    }

    @Override
    public String toString() {
        return "Payslip[name=" + name + ", type=" + getType() + ", pay=" + fmt(calculatePay()) + "]";
    }
}

class FullTime extends Staff {
    private final double salary;
    FullTime(String name, double salary) { super(name); this.salary = salary; }
    @Override
    String getType() { return "FullTime"; }
    @Override
    double calculatePay() { return salary; }
}

class PartTime extends Staff {
    private final double hours, rate;
    PartTime(String name, double hours, double rate) { super(name); this.hours = hours; this.rate = rate; }
    @Override
    String getType() { return "PartTime"; }
    @Override
    double calculatePay() { return hours * rate; }
}

class Intern extends Staff {
    private final double stipend;
    Intern(String name, double stipend) { super(name); this.stipend = stipend; }
    @Override
    String getType() { return "Intern"; }
    @Override
    double calculatePay() { return stipend; }
}

public class PayrollRegisterSystem {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            List<Staff> list = new ArrayList<>();

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] p = line.split("\\s+");

                switch (p[0].toLowerCase()) {
                    case "fulltime":
                        list.add(new FullTime(p[1], Double.parseDouble(p[2])));
                        break;
                    case "parttime":
                        list.add(new PartTime(p[1], Double.parseDouble(p[2]), Double.parseDouble(p[3])));
                        break;
                    case "intern":
                        list.add(new Intern(p[1], Double.parseDouble(p[2])));
                        break;
                    default:
                        break;
                }
            }

            Staff[] staff = list.toArray(new Staff[0]);   // payslips stored in an array
            double total = 0;
            Staff top = null;

            for (Staff s : staff) {
                System.out.println(s);            // polymorphic calculatePay()
                total += s.calculatePay();
                if (top == null || s.calculatePay() > top.calculatePay()) top = s;
            }

            System.out.println("Total " + Staff.fmt(total));
            if (top != null) System.out.println("top earner " + top.name);
        }
    }
}