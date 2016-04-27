package BL;

/**
 * Created by r2-d2 on 23.04.16.
 */
public class Ticket {
    private TicketClass ticketClass;
    private double price;

    public TicketClass getTicketClass() {
        return ticketClass;
    }

    public void setTicketClass(TicketClass ticketClass) {
        this.ticketClass = ticketClass;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
