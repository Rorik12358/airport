package user_interface;

import business_logic.Flight;
import business_logic.Passenger;
import business_logic.Ticket;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by r2-d2 on 24.04.16.
 */
public class Main {
    private static List<Passenger> passengers = new LinkedList<>();
    private static List<Flight> flights = new LinkedList<>();
    private static List<Passenger> selectedPassengers = new LinkedList<>();
    private static List<Flight> selectedFlights = new LinkedList<>();
    private static List<Ticket> selectedTickets = new LinkedList<>();

    public static List<Flight> getSelectedFlights() {
        return selectedFlights;
    }
    public static List<Passenger> getPassengers() {
        return passengers;
    }
    public static List<Flight> getFlights() {
        return flights;
    }
    public static List<Passenger> getSelectedPassengers() {
        return selectedPassengers;
    }
    public static List<Ticket> getSelectedTickets() {
        return selectedTickets;
    }

    public static void main(String[] args) throws InterruptedException, SQLException, ClassNotFoundException {
        Thread gui = new Thread(new UserInerface());
        gui.start();
    }
}
