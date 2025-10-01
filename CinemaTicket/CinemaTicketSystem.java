package CinemaTicket;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.List;

public class CinemaTicketSystem extends JFrame {
    private CinemaTicketManager manager;

    private JTextArea displayArea;

    private JTextField tfMovie;
    private JTextField tfShowTime;
    private JTextField tfSeat;
    private JTextField tfPrice;
    private JTextField tfCustomer;

    public CinemaTicketSystem() {
        super("Quan ly ve rap chieu phim");
        manager = new CinemaTicketManager();
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setBackground(new Color(255, 255, 220));
        JScrollPane scroll = new JScrollPane(displayArea);
        scroll.setPreferredSize(new Dimension(880, 260));
        add(scroll, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnAdd = new JButton("Them Ve");
        JButton btnRemove = new JButton("Xoa Ve");
        JButton btnDisplay = new JButton("Hien Thi");
        JButton btnTotal = new JButton("Tinh Tong Tien");
        JButton btnFind = new JButton("Tim Ve");
        JButton btnCount = new JButton("Hien Thi So Luong");

        btnPanel.add(btnAdd);
        btnPanel.add(btnRemove);
        btnPanel.add(btnDisplay);
        btnPanel.add(btnTotal);
        btnPanel.add(btnFind);
        btnPanel.add(btnCount);

        add(btnPanel, BorderLayout.CENTER);

        JPanel form = new JPanel();
        form.setLayout(null);
        form.setPreferredSize(new Dimension(880, 200));

        JLabel lblTitle = new JLabel("Thong tin ve");
        lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTitle.setBounds(10, 5, 200, 25);
        form.add(lblTitle);

        int lblX = 10, tfX = 220, rowY = 35, rowHeight = 28, gapY = 36;

        form.add(makeLabel("Ten phim:", lblX, rowY, 200, rowHeight));
        tfMovie = makeTextField(tfX, rowY, 620, rowHeight);
        form.add(tfMovie);

        rowY += gapY;
        form.add(makeLabel("Suat chieu (dd/MM/yyyy HH:mm):", lblX, rowY, 200, rowHeight));
        tfShowTime = makeTextField(tfX, rowY, 620, rowHeight);
        form.add(tfShowTime);

        rowY += gapY;
        form.add(makeLabel("Ghe:", lblX, rowY, 200, rowHeight));
        tfSeat = makeTextField(tfX, rowY, 200, rowHeight);
        form.add(tfSeat);

        form.add(makeLabel("Gia ve:", lblX + 430, rowY, 80, rowHeight));
        tfPrice = makeTextField(tfX + 430, rowY, 200, rowHeight);
        form.add(tfPrice);

        rowY += gapY;
        form.add(makeLabel("Ten khach:", lblX, rowY, 200, rowHeight));
        tfCustomer = makeTextField(tfX, rowY, 300, rowHeight);
        form.add(tfCustomer);

        add(form, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> handleAdd());
        btnDisplay.addActionListener(e -> handleDisplayAll());
        btnRemove.addActionListener(e -> handleRemove());
        btnFind.addActionListener(e -> handleFind());
        btnTotal.addActionListener(e -> handleTotal());
        btnCount.addActionListener(e -> handleCount());
    }

    private JLabel makeLabel(String text, int x, int y, int w, int h) {
        JLabel l = new JLabel(text);
        l.setBounds(x, y, w, h);
        return l;
    }

    private JTextField makeTextField(int x, int y, int w, int h) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, w, h);
        return tf;
    }

    private void handleAdd() {
        String movie = tfMovie.getText().trim();
        String show = tfShowTime.getText().trim();
        String seat = tfSeat.getText().trim();
        String priceS = tfPrice.getText().trim();
        String customer = tfCustomer.getText().trim();

        if (movie.isEmpty() || show.isEmpty() || seat.isEmpty() || priceS.isEmpty() || customer.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui long nhap day du thong tin.", "Thieu thong tin", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceS);
            if (price < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Gia ve khong hop le.", "Loi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CinemaTicket t = manager.addTicket(movie, show, seat, price, customer);
        displayArea.append("Da them: " + t.toString() + "\n");
        clearForm();
    }

    private void handleDisplayAll() {
        displayArea.setText("");
        List<CinemaTicket> list = manager.getAllTickets();
        if (list.isEmpty()) {
            displayArea.setText("Khong co ve nao.\n");
            return;
        }
        for (CinemaTicket t : list) displayArea.append(t.toString() + "\n");
    }

    private void handleRemove() {
        String s = JOptionPane.showInputDialog(this, "Nhap ID ve can xoa:", "Xoa ve", JOptionPane.QUESTION_MESSAGE);
        if (s == null || s.trim().isEmpty()) return;
        try {
            int id = Integer.parseInt(s.trim());
            boolean ok = manager.removeTicketById(id);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Xoa ve ID " + id + " thanh cong.");
                handleDisplayAll();
            } else {
                JOptionPane.showMessageDialog(this, "Khong tim thay ve co ID " + id + ".", "Khong tim thay", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID khong hop le.", "Loi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleFind() {
        String s = JOptionPane.showInputDialog(this, "Nhap ID hoac ten khach de tim:", "Tim ve", JOptionPane.QUESTION_MESSAGE);
        if (s == null || s.trim().isEmpty()) return;

        displayArea.setText("");
        try {
            int id = Integer.parseInt(s.trim());
            CinemaTicket t = manager.findById(id);
            if (t != null) displayArea.append(t.toString() + "\n");
            else displayArea.append("Khong tim thay ve ID " + id + ".\n");
            return;
        } catch (NumberFormatException ignored) {}

        List<CinemaTicket> found = manager.findByCustomerName(s);
        if (found.isEmpty()) {
            displayArea.append("Khong tim thay ve cho: " + s + "\n");
        } else {
            for (CinemaTicket t : found) displayArea.append(t.toString() + "\n");
        }
    }

    private void handleTotal() {
        double total = manager.getTotalRevenue();
        DecimalFormat df = new DecimalFormat("#,##0.00");
        JOptionPane.showMessageDialog(this, "Tong tien hien tai: " + df.format(total), "Tong tien", JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleCount() {
        int count = manager.getTicketCount();
        JOptionPane.showMessageDialog(this, "So luong ve: " + count, "So luong ve", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearForm() {
        tfMovie.setText("");
        tfShowTime.setText("");
        tfSeat.setText("");
        tfPrice.setText("");
        tfCustomer.setText("");
        tfMovie.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CinemaTicketSystem app = new CinemaTicketSystem();
            app.setVisible(true);
        });
    }
}
