package business_logic;

/**
 * Created by r2-d2 on 23.04.16.
 */
public class Ticket {
    private static int id = 1;
    private TicketClass ticketClass;
    private double price;

    public Ticket(){
        id++;
    }

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

    public static int getId() {
        return id;
    }
}
