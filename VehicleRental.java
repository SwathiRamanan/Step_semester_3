import java.util.Locale;

/* ---------- Vehicle hierarchy: each category owns its pricing rule ---------- */
abstract class Vehicle {
    private final String name;
    private boolean available = true;            // private: only changed via rent/return

    protected Vehicle(String name) { this.name = name; }
    public String getName() { return name; }
    public boolean isAvailable() { return available; }

    public abstract double calculateRental(int days);

    void markRented() {
        if (!available) throw new IllegalStateException(name + " is currently unavailable.");
        available = false;
    }
    void markReturned() { available = true; }
}

class Sedan extends Vehicle {
    public Sedan(String name) { super(name); }
    public double calculateRental(int days) { return 50.0 * days; }
}

class SUV extends Vehicle {
    public SUV(String name) { super(name); }
    public double calculateRental(int days) { return 80.0 * days; }
}

class Truck extends Vehicle {
    public Truck(String name) { super(name); }
    // trucks carry a flat handling fee on top of the daily rate
    public double calculateRental(int days) { return 120.0 * days + 40.0; }
}

class Customer {
    private final String name;
    public Customer(String name) { this.name = name; }
    public String getName() { return name; }
}

class Rental {
    private final Customer customer;
    private final Vehicle vehicle;
    private final int days;
    private boolean active = true;

    Rental(Customer customer, Vehicle vehicle, int days) {
        this.customer = customer;
        this.vehicle = vehicle;
        this.days = days;
    }
    public Customer getCustomer() { return customer; }
    public Vehicle getVehicle() { return vehicle; }
    public int getDays() { return days; }
    public boolean isActive() { return active; }
    public double getCharge() { return vehicle.calculateRental(days); }
    void close() { active = false; }
}

/* ---------- Common rental logic: never changes when a new category is added ---------- */
class RentalService {
    public Rental rent(Customer customer, Vehicle vehicle, int days) {
        if (days <= 0) {
            System.out.println("Rental duration must be at least 1 day.");
            return null;
        }
        if (!vehicle.isAvailable()) {
            System.out.println(vehicle.getName() + " is currently unavailable.");
            return null;
        }
        vehicle.markRented();
        Rental r = new Rental(customer, vehicle, days);
        System.out.println(String.format(Locale.US, "%s rented successfully by %s. Rental charge: $%.2f.",
                vehicle.getName(), customer.getName(), r.getCharge()));
        return r;
    }

    public void returnVehicle(Rental rental) {
        if (rental == null || !rental.isActive()) {
            System.out.println("No active rental to return.");
            return;
        }
        rental.close();
        rental.getVehicle().markReturned();
        System.out.println(rental.getVehicle().getName() + " returned by " + rental.getCustomer().getName() + ".");
    }
}

public class VehicleRental {
    public static void main(String[] args) {
        RentalService service = new RentalService();
        Vehicle sedanA = new Sedan("Sedan A");
        Vehicle suvB = new SUV("SUV B");
        Customer c1 = new Customer("Customer 1");
        Customer c2 = new Customer("Customer 2");
        Customer c3 = new Customer("Customer 3");

        Rental r1 = service.rent(c1, sedanA, 3);
        service.rent(c2, sedanA, 2);          // unavailable
        service.returnVehicle(r1);
        service.rent(c3, suvB, 5);
    }
}
