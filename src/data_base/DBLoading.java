package data_base;

import business_logic.*;
import user_interface.Main;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by r2-d2 on 24.04.16.
 */
public class DBLoading {
    private static String addressDB = "jdbc:h2:~/airportDataBase";
    private static String loginDB = "sa";
    private static String passwordDB = "";
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    public final static SimpleDateFormat PRECISELLY_FORMAT = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm");

    public static void loadPassengers() throws ClassNotFoundException, SQLException {
        Class cls = Class.forName("org.h2.Driver");
        try(Connection connection = DriverManager.getConnection(addressDB,loginDB,passwordDB)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT FIRST_NAME, LAST_NAME, NATIONALITY, PASSPORT, BIRTHDAY,  SEX.SEX, TICKET_CLASS.TICKET_CLASS, FLIGHT_NUMBER  FROM PASSENGERS JOIN SEX ON PASSENGERS.SEX = SEX.ID JOIN TICKET_CLASS ON PASSENGERS.TICKET_CLASS = TICKET_CLASS.ID");
            while (resultSet.next()) {
                Passenger passenger = new Passenger();
                passenger.setFirstName(resultSet.getString("FIRST_NAME"));
                passenger.setLastName(resultSet.getString("LAST_NAME"));
                passenger.setNationality(resultSet.getString("NATIONALITY"));
                passenger.setPassport(resultSet.getString("PASSPORT"));

                Calendar birthday = Calendar.getInstance();
                Date birthdayDate = resultSet.getDate("BIRTHDAY");
                birthday.setTime(birthdayDate);
                passenger.setBirthday(birthday);

                Sex sex = Sex.valueOf(resultSet.getString("SEX"));
                passenger.setSex(sex);
                TicketClass ticketClass = TicketClass.valueOf(resultSet.getString("TICKET_CLASS"));
                passenger.setTicketClass(ticketClass);
                Main.getPassengers().add(passenger);
                Iterator<Flight> iterator = Main.getFlights().iterator();
                while (iterator.hasNext()) {
                    Flight flight = iterator.next();
                    String f = flight.getFlightNumber();
                    String r = resultSet.getString("FLIGHT_NUMBER");
                    if (flight.getFlightNumber().equals(resultSet.getString("FLIGHT_NUMBER"))) {
                        flight.getPassengers().add(passenger);
                    }
                }
            }
        }
    }

    public static void loadFlights() throws ClassNotFoundException, SQLException {
        Class cls = Class.forName("org.h2.Driver");
        try(Connection connection = DriverManager.getConnection(addressDB,loginDB,passwordDB)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT FLIGHT_NUMBER, FLIGHT_TYPE.FLIGHT_TYPE, TIME, CITY, TERMINAL, FLIGHT_STATUS.FLIGHT_STATUS, GATE FROM FLIGHTS JOIN FLIGHT_TYPE ON FLIGHTS.FLIGHT_TYPE = FLIGHT_TYPE.ID JOIN FLIGHT_STATUS ON FLIGHTS.FLIGHT_STATUS = FLIGHT_STATUS.ID ");
            while (resultSet.next()) {
                Flight flight = new Flight();
                flight.setType(FlightType.valueOf(resultSet.getString("FLIGHT_TYPE")));
                flight.setTime(resultSet.getTimestamp("TIME"));
                flight.setFlightNumber(resultSet.getString("FLIGHT_NUMBER"));
                flight.setCity(resultSet.getString("CITY"));
                flight.setTerminal(resultSet.getString("TERMINAL"));
                flight.setStatus(FlightStatus.valueOf(resultSet.getString("FLIGHT_STATUS")));
                flight.setGate(resultSet.getString("GATE"));
                Main.getFlights().add(flight);
            }
        }
    }

    public static void loadTickets() throws ClassNotFoundException, SQLException {
        Class cls = Class.forName("org.h2.Driver");
        try(Connection connection = DriverManager.getConnection(addressDB,loginDB,passwordDB)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT  TICKET_CLASS.TICKET_CLASS, PRICE, FLIGHT_NUMBER  FROM TICKETS JOIN TICKET_CLASS ON TICKETS.TICKET_CLASS = TICKET_CLASS.ID");
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setTicketClass(TicketClass.valueOf(resultSet.getString("TICKET_CLASS")));
                ticket.setPrice(resultSet.getDouble("PRICE"));
                Iterator<Flight> iterator = Main.getFlights().iterator();
                ggg:
                while (iterator.hasNext()) {
                    Flight flight = iterator.next();
                    if (flight.getFlightNumber().equals(resultSet.getString("FLIGHT_NUMBER"))) {
                        flight.getTickets().add(ticket);
                        break ggg;
                    }
                }
            }
        }

    }

    public static void deletePassengersFromDB(List<Passenger> passengersForDeleting) throws ClassNotFoundException, SQLException {
        Class cls = Class.forName("org.h2.Driver");
        try(Connection connection = DriverManager.getConnection(addressDB,loginDB,passwordDB)) {
            PreparedStatement deletePassengersPrepareStatement = connection.prepareStatement("DELETE FROM PASSENGERS WHERE FIRST_NAME = ? AND LAST_NAME = ? AND NATIONALITY = ? AND PASSPORT = ? AND BIRTHDAY = ? AND SEX = ? AND TICKET_CLASS = ?");
            for (Passenger passenger : passengersForDeleting) {
                deletePassengersPrepareStatement.setString(1, passenger.getFirstName());
                deletePassengersPrepareStatement.setString(2, passenger.getLastName());
                deletePassengersPrepareStatement.setString(3, passenger.getNationality());
                deletePassengersPrepareStatement.setString(4, passenger.getPassport());
                deletePassengersPrepareStatement.setDate(5, new Date(passenger.getBirthday().getTime().getTime()));
                deletePassengersPrepareStatement.setInt(6, passenger.getSex().ordinal() + 1);
                deletePassengersPrepareStatement.setInt(7, passenger.getTicketClass().ordinal() + 1);
                deletePassengersPrepareStatement.execute();
            }
        }
    }

    public static void insertPassenger(Passenger passenger,String flightNumber) throws ClassNotFoundException, SQLException {
        Class cls = Class.forName("org.h2.Driver");
        try(Connection connection = DriverManager.getConnection(addressDB,loginDB,passwordDB)){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PASSENGERS VALUES(?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, passenger.getFirstName());
            preparedStatement.setString(2, passenger.getLastName());
            preparedStatement.setString(3, passenger.getNationality());
            preparedStatement.setString(4, passenger.getPassport());
            preparedStatement.setDate(5, new Date(passenger.getBirthday().getTime().getTime()));
            preparedStatement.setInt(6, passenger.getSex().ordinal() + 1);
            preparedStatement.setInt(7, passenger.getTicketClass().ordinal() + 1);
            preparedStatement.setString(8, flightNumber);
            preparedStatement.execute();
        }
    }

    public static void updatePassenger(Passenger passenger, String firstName, String lastName, String nationality, String passport,
                                       String birthday, String sex, String ticketClass, String flightNumber) throws SQLException, ClassNotFoundException, ParseException {
        Class cls = Class.forName("org.h2.Driver");
        try(Connection connection = DriverManager.getConnection(addressDB,loginDB,passwordDB)) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PASSENGERS SET FIRST_NAME = ?, LAST_NAME = ?, NATIONALITY = ?, PASSPORT = ?, BIRTHDAY = ?, SEX = ?, TICKET_CLASS = ?, FLIGHT_NUMBER = ? WHERE FIRST_NAME = ? AND LAST_NAME = ? AND NATIONALITY = ? AND PASSPORT = ? AND BIRTHDAY = ? AND SEX = ? AND TICKET_CLASS = ? AND FLIGHT_NUMBER = ?");
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, nationality);
            preparedStatement.setString(4, passport);
            preparedStatement.setDate(5, new Date(FORMAT.parse(birthday).getTime()));
            preparedStatement.setInt(6, Sex.valueOf(sex).ordinal() + 1);
            preparedStatement.setInt(7, TicketClass.valueOf(ticketClass).ordinal() + 1);
            preparedStatement.setString(8, flightNumber);

            preparedStatement.setString(9, passenger.getFirstName());
            preparedStatement.setString(10, passenger.getLastName());
            preparedStatement.setString(11, passenger.getNationality());
            preparedStatement.setString(12, passenger.getPassport());
            preparedStatement.setDate(13, new Date(passenger.getBirthday().getTime().getTime()));
            preparedStatement.setInt(14, passenger.getSex().ordinal() + 1);
            preparedStatement.setInt(15, passenger.getTicketClass().ordinal() + 1);
            String fightNumberOfPass = null;
            ggg:
            for (Flight flight : Main.getFlights()) {
                if (flight.getPassengers().contains(passenger)) {
                    fightNumberOfPass = flight.getFlightNumber();
                    break ggg;
                }
            }
            preparedStatement.setString(16, fightNumberOfPass);
            preparedStatement.execute();
        }
    }
    public static void deleteFlightFromDB(Flight flight) throws ClassNotFoundException, SQLException {
        Class cls = Class.forName("org.h2.Driver");
        try(Connection connection = DriverManager.getConnection(addressDB,loginDB,passwordDB)){
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM FLIGHTS WHERE FLIGHT_NUMBER = ?");
            preparedStatement.setString(1,flight.getFlightNumber());
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement("DELETE FROM TICKETS WHERE FLIGHT_NUMBER = ?");
            preparedStatement.setString(1,flight.getFlightNumber());
            preparedStatement.execute();
            preparedStatement = connection.prepareStatement("DELETE FROM PASSENGERS WHERE FLIGHT_NUMBER = ?");
            preparedStatement.setString(1,flight.getFlightNumber());
            preparedStatement.execute();
        }
    }

    public static void insertFlightAndTickets(Flight flight) throws ClassNotFoundException {
        Class cls = Class.forName("org.h2.Driver");
        try(Connection connection = DriverManager.getConnection(addressDB,loginDB,passwordDB)){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO FLIGHTS VALUES(?,?,?,?,?,?,?)");
            preparedStatement.setString(1,flight.getFlightNumber());
            preparedStatement.setInt(2, flight.getType().ordinal()+1);
            preparedStatement.setTimestamp(3, new Timestamp(flight.getTime().getTime()));
            preparedStatement.setString(4, flight.getCity());
            preparedStatement.setString(5, flight.getTerminal());
            preparedStatement.setInt(6, flight.getStatus().ordinal()+1);
            preparedStatement.setString(7, flight.getGate());
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("INSERT INTO TICKETS (ID, TICKET_CLASS, PRICE, FLIGHT_NUMBER) VALUES (?,?,?,?)");
            for(Ticket ticket:flight.getTickets()) {
                preparedStatement.setInt(1, ticket.getId());
                preparedStatement.setInt(2, ticket.getTicketClass().ordinal()+1);
                preparedStatement.setDouble(3, ticket.getPrice());
                preparedStatement.setString(4, flight.getFlightNumber());
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateFlight(Flight flight, String flightType, String time, String flightNumber, String city, String terminal, String status, String gate) throws SQLException, ClassNotFoundException, ParseException {
        Class cls = Class.forName("org.h2.Driver");
        try(Connection connection = DriverManager.getConnection(addressDB,loginDB,passwordDB)){
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE FLIGHTS SET FLIGHT_NUMBER = ?, FLIGHT_TYPE = ?, TIME = ?, CITY = ?, TERMINAL = ?, FLIGHT_STATUS = ?, GATE = ? WHERE FLIGHT_NUMBER = ? AND FLIGHT_TYPE = ? AND TIME = ? AND CITY = ? AND TERMINAL = ? AND FLIGHT_STATUS = ? AND GATE = ?");
            preparedStatement.setString(1, flightNumber);
            preparedStatement.setInt(2, FlightType.valueOf(flightType).ordinal()+1);
            preparedStatement.setTimestamp(3, new Timestamp(PRECISELLY_FORMAT.parse(time).getTime()));
            preparedStatement.setString(4, city);
            preparedStatement.setString(5, terminal);
            preparedStatement.setInt(6, FlightStatus.valueOf(status).ordinal()+1);
            preparedStatement.setString(7, gate);

            preparedStatement.setString(8, flight.getFlightNumber());
            preparedStatement.setInt(9, flight.getType().ordinal()+1);
            preparedStatement.setTimestamp(10, new Timestamp(flight.getTime().getTime()));
            preparedStatement.setString(11, flight.getCity());
            preparedStatement.setString(12, flight.getTerminal());
            preparedStatement.setInt(13, flight.getStatus().ordinal()+1);
            preparedStatement.setString(14, flight.getGate());

            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("UPDATE PASSENGERS SET FLIGHT_NUMBER = ? WHERE FLIGHT_NUMBER = ?");
            preparedStatement.setString(1, flightNumber);
            preparedStatement.setString(2, flight.getFlightNumber());
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("UPDATE TICKETS SET FLIGHT_NUMBER = ? WHERE FLIGHT_NUMBER = ?");
            preparedStatement.setString(1, flightNumber);
            preparedStatement.setString(2, flight.getFlightNumber());
            preparedStatement.execute();
        }
    }

    public static void updateTicket(Ticket ticket, double price) throws ClassNotFoundException, SQLException {
        Class cls = Class.forName("org.h2.Driver");
        try(Connection connection = DriverManager.getConnection(addressDB,loginDB,passwordDB)) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE TICKETS SET PRICE = ? WHERE TICKET_CLASS = ? AND PRICE = ? AND FLIGHT_NUMBER = ?");
            preparedStatement.setDouble(1, price);
            preparedStatement.setInt(2, ticket.getTicketClass().ordinal()+1);
            preparedStatement.setDouble(3, ticket.getPrice());
            for(Flight flight : Main.getFlights()){
                if(flight.getTickets().contains(ticket)){
                    preparedStatement.setString(4, flight.getFlightNumber());
                    break;
                }
            }
            preparedStatement.execute();
        }

    }
}
