
import java.util.*;

/* ---------- Payment methods: add a new one by adding one class ---------- */
interface PaymentMethod {
    String getName();
    boolean processPayment(double amount);
}

class CreditCardPayment implements PaymentMethod {
    private final String cardNumber;
    private final double creditLimit;
    public CreditCardPayment(String cardNumber, double creditLimit) {
        this.cardNumber = cardNumber;
        this.creditLimit = creditLimit;
    }
    public String getName() { return "Credit Card"; }
    public boolean processPayment(double amount) {
        return cardNumber != null && cardNumber.length() == 16 && amount <= creditLimit;
    }
}

class PayPalPayment implements PaymentMethod {
    private final String email;
    private final double balance;
    public PayPalPayment(String email, double balance) { this.email = email; this.balance = balance; }
    public String getName() { return "PayPal"; }
    public boolean processPayment(double amount) {
        return email != null && email.contains("@") && amount <= balance;
    }
}

class BankTransferPayment implements PaymentMethod {
    private final String accountNumber;
    private final double accountBalance;
    public BankTransferPayment(String accountNumber, double accountBalance) {
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
    }
    public String getName() { return "Bank Transfer"; }
    public boolean processPayment(double amount) {
        return accountNumber != null && !accountNumber.isEmpty() && amount <= accountBalance;
    }
}

class Customer {
    private final String name;
    public Customer(String name) { this.name = name; }
    public String getName() { return name; }
}

class Product {
    private final String name;
    private final double price;
    public Product(String name, double price) { this.name = name; this.price = price; }
    public String getName() { return name; }
    public double getPrice() { return price; }
}

class OrderItem {
    private final Product product;
    private final int quantity;
    OrderItem(Product product, int quantity) { this.product = product; this.quantity = quantity; }
    public double subtotal() { return product.getPrice() * quantity; }
}

enum OrderStatus {
    PENDING, PAID;
    String label() { return name().charAt(0) + name().substring(1).toLowerCase(); }
}

/* ---------- Order manages its items and its own status ---------- */
class Order {
    private final String id;
    private final Customer customer;
    private final List<OrderItem> items = new ArrayList<>();
    private OrderStatus status = OrderStatus.PENDING;

    public Order(String id, Customer customer) { this.id = id; this.customer = customer; }

    public String getId() { return id; }
    public Customer getCustomer() { return customer; }
    public OrderStatus getStatus() { return status; }
    public boolean isEmpty() { return items.isEmpty(); }

    public void addItem(Product p, int qty) {
        if (status == OrderStatus.PAID) throw new IllegalStateException("Cannot modify a paid order.");
        if (qty <= 0) throw new IllegalArgumentException("Quantity must be at least 1.");
        items.add(new OrderItem(p, qty));
    }

    public double getTotal() {
        double t = 0;
        for (OrderItem i : items) t += i.subtotal();
        return t;
    }

    /** Only the payment workflow (same package) can mark an order as paid. */
    void markPaid() { status = OrderStatus.PAID; }
}

/* ---------- Payment initiation: depends only on the PaymentMethod abstraction ---------- */
class PaymentService {
    public boolean pay(Order order, PaymentMethod method) {
        if (order.isEmpty()) {
            System.out.println("Cannot process payment for an empty order.");
            return false;
        }
        if (order.getStatus() == OrderStatus.PAID) {
            System.out.println("Order " + order.getId() + " is already paid.");
            return false;
        }
        System.out.println("Payment initiated via " + method.getName() + " for Order " + order.getId() + ".");
        boolean success = method.processPayment(order.getTotal());
        if (success) {
            order.markPaid();
            System.out.println("Payment for Order " + order.getId() + " successful. Order status: "
                    + order.getStatus().label() + ".");
        } else {
            System.out.println("Payment for Order " + order.getId() + " failed. Order status: "
                    + order.getStatus().label() + ".");
        }
        return success;
    }
}

public class PaymentProcessing {
    private static Order createOrder(String id, Customer c) {
        System.out.println("Order created for Customer " + c.getName() + ".");
        return new Order(id, c);
    }

    public static void main(String[] args) {
        PaymentService payments = new PaymentService();
        Product a = new Product("Product A", 100);
        Product b = new Product("Product B", 250);
        Product pc = new Product("Product C", 500);

        Customer x = new Customer("X");
        Order orderX = createOrder("X", x);
        orderX.addItem(a, 2);
        orderX.addItem(b, 1);
        payments.pay(orderX, new CreditCardPayment("1234567812345678", 5000));

        Customer y = new Customer("Y");
        Order orderY = createOrder("Y", y);
        payments.pay(orderY, new CreditCardPayment("1234567812345678", 5000));   // empty order

        Customer z = new Customer("Z");
        Order orderZ = createOrder("Z", z);
        orderZ.addItem(pc, 1);
        payments.pay(orderZ, new PayPalPayment("z@example.com", 50));            // insufficient -> fails
    }
}