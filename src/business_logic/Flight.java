package business_logic;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by r2-d2 on 23.04.16.
 */
public class Flight {
    private FlightType type;
    private Date time;
    private String flightNumber;
    private String city;
    private String terminal;
    private FlightStatus status;
    private String gate;
    private List<Passenger> passengers = new LinkedList<>();
    private List<Ticket> tickets = new LinkedList<>();

    @Override
    public String toString() {
        return "Flight{" +
                "type=" + type +
                ", time=" + time +
                ", flightNumber='" + flightNumber + '\'' +
                ", city='" + city + '\'' +
                ", terminal='" + terminal + '\'' +
                ", status=" + status +
                ", gate='" + gate + '\'' +
                ", passengers=" + passengers +
                ", tickets=" + tickets +
                '}';
    }

    public FlightType getType() {
        return type;
    }

    public void setType(FlightType type) {
        this.type = type;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
