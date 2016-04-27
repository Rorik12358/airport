package DB;

import BL.*;
import GUI.Main;

import java.sql.*;
import java.util.Calendar;
import java.util.Iterator;

/**
 * Created by r2-d2 on 24.04.16.
 */
public class DBLoading {
    public static void loadPassengers() throws ClassNotFoundException, SQLException {
        Class cls = Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection("jdbc:h2:~/airportDataBase","sa","");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT FIRST_NAME, LAST_NAME, NATIONALITY, PASSPORT, BIRTHDAY,  SEX.SEX, TICKET_CLASS.TICKET_CLASS, FLIGHT_NUMBER  FROM PASSENGERS JOIN SEX ON PASSENGERS.SEX = SEX.ID JOIN TICKET_CLASS ON PASSENGERS.TICKET_CLASS = TICKET_CLASS.ID");
        while (resultSet.next()){
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
            while (iterator.hasNext()){
                Flight flight = iterator.next();
                String f = flight.getFlightNumber();
                String r = resultSet.getString("FLIGHT_NUMBER");
                if(flight.getFlightNumber().equals(resultSet.getString("FLIGHT_NUMBER"))){
                    flight.getPassengers().add(passenger);
                }
            }
        }
        connection.close();
    }

    public static void loadFlights() throws ClassNotFoundException, SQLException {
        Class cls = Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection("jdbc:h2:~/airportDataBase","sa","");
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
        connection.close();
    }

    public static void loadTickets() throws ClassNotFoundException, SQLException {
        Class cls = Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection("jdbc:h2:~/airportDataBase","sa","");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT  TICKET_CLASS.TICKET_CLASS, PRICE, FLIGHT_NUMBER  FROM TICKETS JOIN TICKET_CLASS ON TICKETS.TICKET_CLASS = TICKET_CLASS.ID");
        while (resultSet.next()) {
            Ticket ticket = new Ticket();
            ticket.setTicketClass(TicketClass.valueOf(resultSet.getString("TICKET_CLASS")));
            ticket.setPrice(resultSet.getDouble("PRICE"));
            Main.getTickets().add(ticket);
            Iterator<Flight> iterator = Main.getFlights().iterator();
            while (iterator.hasNext()){
                Flight flight = iterator.next();
                if(flight.getFlightNumber().equals(resultSet.getString("FLIGHT_NUMBER"))){
                    flight.getTickets().add(ticket);
                }
            }
        }
        connection.close();

    }
}
