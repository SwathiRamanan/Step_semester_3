public class FeeAccount {

    String name;

    FeeAccount(String name) {
        this.name = name;
    }

    void pay(double amount) {
        System.out.println("Paid in one go (day-scholar account)");
    }

    static int hostelCount = 0;
    static int dayScholarCount = 0;

    static void processPayment(FeeAccount account, double amount) {

        if (account instanceof HostelFeeAccount) {

            System.out.println(
                    "Paid in two installments (hostel account)"
            );

            hostelCount++;

        } else {

            System.out.println(
                    "Paid in one go (day-scholar account)"
            );

            dayScholarCount++;
        }
    }

    public static void main(String[] args) {

        FeeAccount[] accounts = {
            new HostelFeeAccount("Hostel 1"),
            new HostelFeeAccount("Hostel 2"),
            new FeeAccount("Student 1"),
            new FeeAccount("Student 2")
        };

        double amount = 60000;

        for (FeeAccount account : accounts) {
            processPayment(account, amount);
        }

        System.out.println(
                "Hostel accounts processed: " + hostelCount
                + " | Day-scholar accounts processed: "
                + dayScholarCount
        );
    }
}

class HostelFeeAccount extends FeeAccount {

    HostelFeeAccount(String name) {
        super(name);
    }
}