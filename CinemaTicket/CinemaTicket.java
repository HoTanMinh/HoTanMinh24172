package CinemaTicket;

import java.text.DecimalFormat;

public class CinemaTicket {
    private static int nextId = 1;

    private int id;
    private String movieName;
    private String showTime;
    private String seat;
    private double price;
    private String customerName;

    public CinemaTicket(String movieName, String showTime, String seat,
                        double price, String customerName) {
        this.id = nextId++;
        this.movieName = movieName;
        this.showTime = showTime;
        this.seat = seat;
        this.price = price;
        this.customerName = customerName;
    }

    public int getId() { return id; }
    public String getMovieName() { return movieName; }
    public String getShowTime() { return showTime; }
    public String getSeat() { return seat; }
    public double getPrice() { return price; }
    public String getCustomerName() { return customerName; }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return "ID: " + id
                + " | Phim: " + movieName
                + " | Suat: " + showTime
                + " | Ghe: " + seat
                + " | Gia: " + df.format(price)
                + " | Khach: " + customerName;
    }
}
