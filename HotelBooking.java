import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;

/* ---------- Room hierarchy: each category owns its pricing rule ---------- */
abstract class Room {
    private final int number;
    private final List<Reservation> reservations = new ArrayList<>();

    protected Room(int number) { this.number = number; }
    public int getNumber() { return number; }
    public abstract String getCategory();
    public abstract double calculatePrice(int nights);

    public String label() { return getCategory() + " Room " + number; }

    /** Check-out day is free for the next guest: ranges are [checkIn, checkOut). */
    public boolean isAvailable(LocalDate checkIn, LocalDate checkOut) {
        for (Reservation r : reservations)
            if (r.isActive() && r.overlaps(checkIn, checkOut)) return false;
        return true;
    }

    void addReservation(Reservation r) { reservations.add(r); }
}

class StandardRoom extends Room {
    public StandardRoom(int number) { super(number); }
    public String getCategory() { return "Standard"; }
    public double calculatePrice(int nights) { return 100.0 * nights; }
}

class DeluxeRoom extends Room {
    public DeluxeRoom(int number) { super(number); }
    public String getCategory() { return "Deluxe"; }
    public double calculatePrice(int nights) { return 180.0 * nights; }
}

class Suite extends Room {
    public Suite(int number) { super(number); }
    public String getCategory() { return "Suite"; }
    // larger rooms: higher nightly rate plus a one-time amenities fee
    public double calculatePrice(int nights) { return 300.0 * nights + 75.0; }
}

class Customer {
    private final String name;
    public Customer(String name) { this.name = name; }
    public String getName() { return name; }
}

class Reservation {
    static final int CANCELLATION_DEADLINE_DAYS = 2;   // must cancel at least 2 days before check-in

    private final Customer customer;
    private final Room room;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private boolean active = true;

    Reservation(Customer customer, Room room, LocalDate checkIn, LocalDate checkOut) {
        this.customer = customer;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public Customer getCustomer() { return customer; }
    public Room getRoom() { return room; }
    public boolean isActive() { return active; }
    public int getNights() { return (int) ChronoUnit.DAYS.between(checkIn, checkOut); }
    public double getPrice() { return room.calculatePrice(getNights()); }

    boolean overlaps(LocalDate from, LocalDate to) {
        return checkIn.isBefore(to) && from.isBefore(checkOut);
    }

    /** Reservation covers nights checkIn..checkOut-1, but is shown as checkIn-checkOut (e.g. Jan 1-5). */
    public String period() { return HotelService.formatPeriod(checkIn, checkOut); }

    void cancel(LocalDate today) {
        if (!active) throw new IllegalStateException("Reservation is already cancelled.");
        if (today.isAfter(checkIn.minusDays(CANCELLATION_DEADLINE_DAYS)))
            throw new IllegalStateException("Cannot cancel: the cancellation deadline has passed.");
        active = false;
    }
}

class HotelService {
    static String formatPeriod(LocalDate from, LocalDate to) {
        String fm = from.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        String tm = to.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        String end = from.getMonth() == to.getMonth() ? String.valueOf(to.getDayOfMonth())
                                                      : tm + " " + to.getDayOfMonth();
        return fm + " " + from.getDayOfMonth() + "-" + end;
    }

    static String formatRange(LocalDate from, LocalDate to) {
        return from.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + from.getDayOfMonth()
                + " to " + to.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " " + to.getDayOfMonth();
    }

    public boolean checkAvailability(Room room, LocalDate from, LocalDate to) {
        boolean ok = room.isAvailable(from, to);
        System.out.println(room.label() + " is " + (ok ? "" : "not ") + "available from "
                + formatRange(from, to) + ".");
        return ok;
    }

    public Reservation reserve(Customer c, Room room, LocalDate from, LocalDate to) {
        if (!to.isAfter(from)) {
            System.out.println("Check-out must be after check-in.");
            return null;
        }
        if (!room.isAvailable(from, to)) {
            System.out.println(room.label() + " is not available from "
                    + formatRange(from, to) + ".");
            return null;
        }
        Reservation r = new Reservation(c, room, from, to);
        room.addReservation(r);
        System.out.println(String.format(Locale.US, "Reservation confirmed for %s, %s (%s). Price: $%.2f.",
                c.getName(), room.label(), r.period(), r.getPrice()));
        return r;
    }

    public void cancel(Reservation r, LocalDate today) {
        try {
            r.cancel(today);
            System.out.println("Reservation for " + r.getCustomer().getName() + ", " + r.getRoom().label()
                    + " (" + r.period() + ") cancelled successfully.");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}

public class HotelBooking {
    public static void main(String[] args) {
        HotelService hotel = new HotelService();
        Room standard101 = new StandardRoom(101);
        Room deluxe201 = new DeluxeRoom(201);
        Customer a = new Customer("Customer A");
        Customer b = new Customer("Customer B");
        Customer c = new Customer("Customer C");
        LocalDate today = LocalDate.of(2026, 12, 15);

        hotel.checkAvailability(standard101, LocalDate.of(2027, 1, 1), LocalDate.of(2027, 1, 5));
        Reservation ra = hotel.reserve(a, standard101, LocalDate.of(2027, 1, 1), LocalDate.of(2027, 1, 5));
        hotel.reserve(b, standard101, LocalDate.of(2027, 1, 3), LocalDate.of(2027, 1, 7));   // overlap
        hotel.cancel(ra, today);                                                              // before deadline
        hotel.reserve(c, deluxe201, LocalDate.of(2027, 2, 10), LocalDate.of(2027, 2, 12));
    }
}