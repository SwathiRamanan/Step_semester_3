public class MovieTicket {

    private String seatNumber;
    protected String screenId;
    protected double ticketPrice;
    public String movieTitle;

    public MovieTicket(String seatNumber, String screenId,
                       double ticketPrice, String movieTitle) {
        this.seatNumber = seatNumber;
        this.screenId = screenId;
        this.ticketPrice = ticketPrice;
        this.movieTitle = movieTitle;
    }

    public static void main(String[] args) {

        MovieTicket ticket = new MovieTicket(
            "A1",
            "SCREEN-1",
            250.0,
            "Avengers"
        );

        System.out.println("Movie: " + ticket.movieTitle);
        System.out.println("Price: " + ticket.ticketPrice);
    }
}