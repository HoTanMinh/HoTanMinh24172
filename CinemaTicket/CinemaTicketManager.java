package CinemaTicket;
import java.util.ArrayList;
import java.util.List;

public class CinemaTicketManager {
    private final List<CinemaTicket> tickets = new ArrayList<>();

    public void add(CinemaTicket t) { tickets.add(t); }
    public boolean remove(int id) { return tickets.removeIf(t -> t.getId() == id); }
    public CinemaTicket findById(int id) {
        for (CinemaTicket t : tickets) if (t.getId() == id) return t;
        return null;
    }
    public List<CinemaTicket> findByCustomer(String name) {
        List<CinemaTicket> res = new ArrayList<>();
        for (CinemaTicket t : tickets)
            if (t.getCustomer().toLowerCase().contains(name.toLowerCase())) res.add(t);
        return res;
    }
    public double total() { return tickets.stream().mapToDouble(CinemaTicket::getPrice).sum(); }
    public int count() { return tickets.size(); }
    public List<CinemaTicket> getAll() { return tickets; }
}
