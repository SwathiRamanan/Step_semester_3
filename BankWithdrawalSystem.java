import java.util.*;

// Problem 1: Bank Account Withdrawal System
abstract class Account {
    protected final String id;
    protected double balance;

    Account(String id, double balance) {
        this.id = id;
        this.balance = balance;
    }

    // Returns null if the withdrawal is allowed, otherwise the rejection reason.
    protected abstract String violation(double amount);

    String withdraw(double amount) {
        if (amount <= 0) return id + " rejected: invalid amount";
        String reason = violation(amount);
        if (reason != null) return id + " rejected: " + reason;
        balance -= amount;
        return id + " balance " + fmt(balance);
    }

    static String fmt(double v) {
        return v == Math.floor(v) ? String.valueOf((long) v) : String.valueOf(v);
    }
}

class SavingsAccount extends Account {
    private static final double MIN_BALANCE = 1000;

    SavingsAccount(String id, double balance) { super(id, balance); }

    @Override
    protected String violation(double amount) {
        return (balance - amount < MIN_BALANCE) ? "minimum balance 1000" : null;
    }
}

class CurrentAccount extends Account {
    private static final double OVERDRAFT_LIMIT = 5000;

    CurrentAccount(String id, double balance) { super(id, balance); }

    @Override
    protected String violation(double amount) {
        return (balance - amount < -OVERDRAFT_LIMIT) ? "overdraft limit 5000" : null;
    }
}

public class BankWithdrawalSystem {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Map<String, Account> accounts = new HashMap<>();

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) continue;
                String[] p = line.split("\\s+");

                switch (p[0].toUpperCase()) {
                    case "SAVINGS":
                        accounts.put(p[1], new SavingsAccount(p[1], Double.parseDouble(p[2])));
                        break;
                    case "CURRENT":
                        accounts.put(p[1], new CurrentAccount(p[1], Double.parseDouble(p[2])));
                        break;
                    case "WITHDRAW":
                        Account acc = accounts.get(p[1]);
                        if (acc == null) System.out.println("Account not found");
                        else System.out.println(acc.withdraw(Double.parseDouble(p[2])));
                        break;
                    default:
                        break;
                }
            }
        }
    }
}