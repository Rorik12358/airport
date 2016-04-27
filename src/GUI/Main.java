package GUI;

import BL.Flight;
import BL.Passenger;
import BL.TableMaker;
import BL.Ticket;
import DB.DBLoading;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by r2-d2 on 24.04.16.
 */
public class Main {
    private static List<Passenger> passengers = new LinkedList<>();
    private static List<Flight> flights = new LinkedList<>();
    private static List<Ticket> tickets = new LinkedList<>();

    public static List<Passenger> getPassengers() {
        return passengers;
    }
    public static List<Flight> getFlights() {
        return flights;
    }
    public static List<Ticket> getTickets() {
        return tickets;
    }


    public static void main(String[] args) throws InterruptedException, SQLException, ClassNotFoundException {
        DBLoading.loadFlights();
        DBLoading.loadTickets();
        DBLoading.loadPassengers();
        TableMaker.loadingRowsForFlights();

        Thread gui = new Thread(new UserInerface());
        gui.start();

    }
}
