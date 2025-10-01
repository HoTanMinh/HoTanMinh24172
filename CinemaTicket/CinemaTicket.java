package CinemaTicket;
public class CinemaTicket {
    private static int nextId = 1;
    private final int id;
    private final String movie;
    private final String showTime;
    private final String seat;
    private final double price;
    private final String customer;

    public CinemaTicket(String movie, String showTime, String seat, double price, String customer) {
        this.id = nextId++;
        this.movie = movie;
        this.showTime = showTime;
        this.seat = seat;
        this.price = price;
        this.customer = customer;
    }

    public int getId() { return id; }
    public String getMovie() { return movie; }
    public String getShowTime() { return showTime; }
    public String getSeat() { return seat; }
    public double getPrice() { return price; }
    public String getCustomer() { return customer; }

    @Override
    public String toString() {
        return "ID: " + id + " | Phim: " + movie + " | Suat: " + showTime +
               " | Ghe: " + seat + " | Gia: " + price + " | Khach: " + customer;
    }
}

