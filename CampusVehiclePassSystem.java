import java.util.*;

// Problem 2: Campus Vehicle Pass System
interface Chargeable {
    String bookChargingBay();
}

abstract class Vehicle {
    protected final String passNumber;
    protected final String owner;

    Vehicle(String passNumber, String owner) {
        this.passNumber = passNumber;
        this.owner = owner;
    }

    abstract String getType();
    abstract int getPassFee();

    String passInfo() {
        return passNumber + " (" + getType() + ") pass fee " + getPassFee();
    }
}

class Bike extends Vehicle {
    Bike(String no, String owner) { super(no, owner); }
    @Override
    String getType() { return "Bike"; }
    @Override
    int getPassFee() { return 300; }
}

class Car extends Vehicle {
    Car(String no, String owner) { super(no, owner); }
    @Override
    String getType() { return "Car"; }
    @Override
    int getPassFee() { return 1000; }
}

class EBike extends Vehicle implements Chargeable {
    EBike(String no, String owner) { super(no, owner); }
    @Override
    String getType() { return "EBike"; }
    @Override
    int getPassFee() { return 300; }
    @Override
    public String bookChargingBay() { return passNumber + " charging bay allotted"; }
}

class ECar extends Vehicle implements Chargeable {
    ECar(String no, String owner) { super(no, owner); }
    @Override
    String getType() { return "ECar"; }
    @Override
    int getPassFee() { return 1000; }
    @Override
    public String bookChargingBay() { return passNumber + " charging bay allotted"; }
}

public class CampusVehiclePassSystem {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Map<String, Vehicle> vehicles = new HashMap<>();

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] p = line.split("\\s+");

                if (p[0].equalsIgnoreCase("PASS")) {
                    // accepts "ECar" as well as "E-Car"
                    String type = p[1].replace("-", "").toLowerCase();
                    String no = p[2];
                    String owner = p.length > 3 ? p[3] : "";
                    Vehicle v;
                    switch (type) {
                        case "bike":  v = new Bike(no, owner); break;
                        case "car":   v = new Car(no, owner); break;
                        case "ebike": v = new EBike(no, owner); break;
                        case "ecar":  v = new ECar(no, owner); break;
                        default:
                            System.out.println("Unknown vehicle type " + p[1]);
                            continue;
                    }
                    vehicles.put(no, v);
                    System.out.println(v.passInfo());
                } else if (p[0].equalsIgnoreCase("CHARGE")) {
                    Vehicle v = vehicles.get(p[1]);
                    if (v == null) {
                        System.out.println(p[1] + " not found");
                    } else if (v instanceof Chargeable) {
                        System.out.println(((Chargeable) v).bookChargingBay());
                    } else {
                        System.out.println(p[1] + " rejected: charging unsupported");
                    }
                }
            }
        }
    }
}