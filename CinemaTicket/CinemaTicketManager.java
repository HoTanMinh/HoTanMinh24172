package CinemaTicket;

import java.util.ArrayList;
import java.util.List;

public class CinemaTicketManager {
    private final List<CinemaTicket> tickets;

    public CinemaTicketManager() {
        tickets = new ArrayList<>();
    }

    public CinemaTicket addTicket(String movieName, String showTime, String seat,
                                  double price, String customerName) {
        CinemaTicket t = new CinemaTicket(movieName, showTime, seat, price, customerName);
        tickets.add(t);
        return t;
    }

    public boolean removeTicketById(int id) {
        return tickets.removeIf(t -> t.getId() == id);
    }

    public List<CinemaTicket> findByCustomerName(String name) {
        List<CinemaTicket> res = new ArrayList<>();
        if (name == null) return res;
        String key = name.trim().toLowerCase();
        for (CinemaTicket t : tickets) {
            if (t.getCustomerName() != null && t.getCustomerName().toLowerCase().contains(key)) {
                res.add(t);
            }
        }
        return res;
    }

    public CinemaTicket findById(int id) {
        for (CinemaTicket t : tickets) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    public List<CinemaTicket> getAllTickets() {
        return new ArrayList<>(tickets);
    }

    public double getTotalRevenue() {
        double sum = 0;
        for (CinemaTicket t : tickets) sum += t.getPrice();
        return sum;
    }

    public int getTicketCount() {
        return tickets.size();
    }
}
