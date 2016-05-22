package user_interface;

import business_logic.*;
import data_base.DBLoading;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by r2-d2 on 25.04.16.
 */
public class GUIObjectsMaker {
    private final static String[] COLUMNS_FOR_TABLE_FLIGHTS = {"FLIGHT TYPE", "TIME",
            "FLIGHT NUMBER", "CITY", "TERMINAL", "STATUS", "GATE"};
    private final static String[] COLS_FOR_SELECTED_PASSENGERS = {"FLIGHT NUMBER", "FIRST NAME", "LAST NAME", "NATIONALITY", "PASSPORT",
            "BIRTHDAY", "SEX", "TICKET CLASS"};
    private final static String[] COLUMNS_FOR_TICKETS = {"FLIGHT NUMBER", "TICKET CLASS", "PRICE"};
    private static String[][] rowsForTableFlightsDeparture;
    private static String[][] rowsForTableFlightsArrival;
    public static void loadingRowsForFlights(){
        Iterator<Flight> iterator = Main.getFlights().iterator();
        int quantityOfDepartureFlights = 0;
        int quantityOfArrivalFlights = 0;
        while (iterator.hasNext()){
            if(iterator.next().getType() == FlightType.DEPARTURE){
                quantityOfDepartureFlights++;
            } else {
                quantityOfArrivalFlights++;
            }
        }
        String[][] rowsDepartureFlights = new String[quantityOfDepartureFlights][];
        String[][] rowsArrivalFlights = new String[quantityOfArrivalFlights][];
        iterator = Main.getFlights().iterator();
        int indexForRowsDepartureFlights = 0;
        int indexForRowsArrivalFlights = 0;
        while (iterator.hasNext()){
            Flight flight = iterator.next();
            String[] row = new String[COLUMNS_FOR_TABLE_FLIGHTS.length];
            int i = 0;
            FlightType type = flight.getType();
            row[i++] = type.name();
            row[i++] = DBLoading.PRECISELLY_FORMAT.format(flight.getTime());
            row[i++] = flight.getFlightNumber();
            row[i++] = flight.getCity();
            row[i++] = flight.getTerminal();
            row[i++] = flight.getStatus().name();
            row[i++] = flight.getGate();
            if(type.equals(FlightType.ARRIVAL)){

                rowsArrivalFlights[indexForRowsArrivalFlights++] = row;
            } else {
                rowsDepartureFlights[indexForRowsDepartureFlights++] = row;
            }
        }
        rowsForTableFlightsDeparture = rowsDepartureFlights;
        rowsForTableFlightsArrival = rowsArrivalFlights;
    }

    public static JTable createFlightsArrivalTable() throws SQLException, ClassNotFoundException {
        JTable flightsArrivalTable = new JTable(rowsForTableFlightsArrival,COLUMNS_FOR_TABLE_FLIGHTS){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        flightsArrivalTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        flightsArrivalTable.getColumnModel().getColumn(0).setPreferredWidth(90);
        flightsArrivalTable.getColumnModel().getColumn(1).setPreferredWidth(170);
        flightsArrivalTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        flightsArrivalTable.getColumnModel().getColumn(5).setPreferredWidth(90);
        flightsArrivalTable.getColumnModel().getColumn(6).setPreferredWidth(40);
        flightsArrivalTable.setPreferredScrollableViewportSize(new Dimension(500,100));
        return flightsArrivalTable;
    }
    public static JTable createFlightsDepartureTable(){
        JTable flightsDepartureTable = new JTable(rowsForTableFlightsDeparture, COLUMNS_FOR_TABLE_FLIGHTS){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        flightsDepartureTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        flightsDepartureTable.getColumnModel().getColumn(0).setPreferredWidth(90);
        flightsDepartureTable.getColumnModel().getColumn(1).setPreferredWidth(170);
        flightsDepartureTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        flightsDepartureTable.getColumnModel().getColumn(5).setPreferredWidth(90);
        flightsDepartureTable.getColumnModel().getColumn(6).setPreferredWidth(40);
        flightsDepartureTable.setPreferredScrollableViewportSize(new Dimension(500,100));
        return flightsDepartureTable;

    }
    public static JTable createSelectedFlight(JComboBox ComboBoxFlights){
        Main.getSelectedFlights().clear();
        String[][] selectedFlight = new String[1][COLUMNS_FOR_TABLE_FLIGHTS.length];
        Flight flight = null;
        for(Flight fl : Main.getFlights()) {
            if(fl.getFlightNumber().equals(ComboBoxFlights.getSelectedItem().toString())){
                flight = fl;
                Main.getSelectedFlights().add(flight);
            }
        }
        int i = 0;
        selectedFlight[0][i++] = flight.getType().name();
        selectedFlight[0][i++] = DBLoading.PRECISELLY_FORMAT.format(flight.getTime());
        selectedFlight[0][i++] = flight.getFlightNumber();
        selectedFlight[0][i++] = flight.getCity();
        selectedFlight[0][i++] = flight.getTerminal();
        selectedFlight[0][i++] = flight.getStatus().name();
        selectedFlight[0][i++] = flight.getGate();
        JTable selFl = new JTable(selectedFlight, COLUMNS_FOR_TABLE_FLIGHTS){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        selFl.setPreferredScrollableViewportSize(new Dimension(660,16));
        selFl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        selFl.getColumnModel().getColumn(0).setPreferredWidth(90);
        selFl.getColumnModel().getColumn(1).setPreferredWidth(170);
        selFl.getColumnModel().getColumn(2).setPreferredWidth(120);
        selFl.getColumnModel().getColumn(5).setPreferredWidth(90);
        selFl.getColumnModel().getColumn(6).setPreferredWidth(40);
        return selFl;
    }
    public static JComboBox createComboBoxFlights(){
        String[] itemsForComboBoxFlights = new String[Main.getFlights().size()];
        for(int i = 0; i < itemsForComboBoxFlights.length; i++){
            itemsForComboBoxFlights[i] = Main.getFlights().get(i).getFlightNumber();
        }
        return new JComboBox(itemsForComboBoxFlights);
    }
    public static JComboBox createComboBoxFlights(String all){
        String[] itemsForComboBoxFlights = new String[Main.getFlights().size()+1];
        itemsForComboBoxFlights[0] = "ALL";
        for(int i = 1; i < itemsForComboBoxFlights.length; i++){
            itemsForComboBoxFlights[i] = Main.getFlights().get(i-1).getFlightNumber();
        }
        return new JComboBox(itemsForComboBoxFlights);
    }
    public static JComboBox createComboBoxTicketClass(){
        String[] itemsForComboBoxTicketClass = new String[TicketClass.values().length+1];
        itemsForComboBoxTicketClass[0] = "ALL";
        for(int i = 1; i < itemsForComboBoxTicketClass.length; i++){
            itemsForComboBoxTicketClass[i] = TicketClass.values()[i-1].name();
        }
        return new JComboBox(itemsForComboBoxTicketClass);
    }
    public static JTable createSelectedPassengers(JComboBox flightsComboBox, String firstName, String lastName, String passport){
        Main.getSelectedPassengers().clear();
        if(flightsComboBox.getSelectedItem().toString().equals("ALL")){
            Main.getSelectedPassengers().addAll(Main.getPassengers());
        } else {
            for(Flight fl : Main.getFlights()){
                if(flightsComboBox.getSelectedItem().toString().equals(fl.getFlightNumber())){
                    Main.getSelectedPassengers().addAll(fl.getPassengers());
                    break;
                }
            }
        }
        Iterator<Passenger> iterator;
        if(!firstName.equals("")){
            iterator = Main.getSelectedPassengers().iterator();
            while (iterator.hasNext()){
                Passenger passenger = iterator.next();
                if(!passenger.getFirstName().equals(firstName)){
                    iterator.remove();
                }
            }
        }
        if(!lastName.equals("")){
            iterator = Main.getSelectedPassengers().iterator();
            while (iterator.hasNext()){
                Passenger passenger = iterator.next();
                if(!passenger.getLastName().equals(lastName)){
                    iterator.remove();
                }
            }
        }
        if(!passport.equals("")){
            iterator = Main.getSelectedPassengers().iterator();
            while (iterator.hasNext()){
                Passenger passenger = iterator.next();
                if(!passenger.getPassport().equals(passport)){
                    iterator.remove();
                }
            }
        }
        String[][] rows = new String[Main.getSelectedPassengers().size()][];
        int indexOfRow = 0;
        for(Passenger passenger : Main.getSelectedPassengers()){
            String[] row = new String[COLS_FOR_SELECTED_PASSENGERS.length];
            int i = 0;
            Flight flight = null;
            for(Flight fl : Main.getFlights()){
                if(fl.getPassengers().contains(passenger)){
                    flight = fl;
                }
            }
            row[i++] = flight.getFlightNumber();
            row[i++] = passenger.getFirstName();
            row[i++] = passenger.getLastName();
            row[i++] = passenger.getNationality();
            row[i++] = passenger.getPassport();
            row[i++] = DBLoading.FORMAT.format(passenger.getBirthday().getTime());
            row[i++] = passenger.getSex().name();
            row[i++] = passenger.getTicketClass().name();
            rows[indexOfRow++] = row;
        }

        JTable selectedPassengerTable = new JTable(rows,COLS_FOR_SELECTED_PASSENGERS){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        selectedPassengerTable.setPreferredScrollableViewportSize(new Dimension(1000, 200));
        return selectedPassengerTable;
    }
    public static void showWarningDataValidationMessage(){
        JFrame warningFrame = new JFrame("INCORRECT INPUT DATA");
        warningFrame.setLocation(200,200);
        warningFrame.setSize(300,100);
        JTextArea warn = new JTextArea("SORRY. YOU WROTE INCORRECT INPUT DATA");
        warn.setEditable(false);
        warningFrame.add(warn);
        warningFrame.setVisible(true);
    }
    public static void showSuccessInsertMessage(){
        JFrame warningFrame = new JFrame("DATA WAS INSERTED");
        warningFrame.setLocation(200,200);
        warningFrame.setSize(300,100);
        JTextArea warn = new JTextArea("YOUR DATA WAW INSERTED SUCCESSFULLY");
        warn.setEditable(false);
        warningFrame.add(warn);
        warningFrame.setVisible(true);
    }
    public static void showWarningSelectionMessage(){
        JFrame warningFrame = new JFrame("INCORECT SELECTED DATA");
        warningFrame.setLocation(200,200);
        warningFrame.setSize(300,100);
        JTextArea warn = new JTextArea("YOU SHOULD SELECT ONLY 1 ITEM FOR UPDATING");
        warn.setEditable(false);
        warningFrame.add(warn);
        warningFrame.setVisible(true);
    }
    public static void insertFlightAndTickets(Flight flight){
        JFrame insertFlightframe = new JFrame("INSERT FLIGHT");
        insertFlightframe.setLocation(200,200);
        insertFlightframe.setSize(250,180);
        JPanel panel = new JPanel(new MigLayout());

        JTextArea businessPriceTextArea = new JTextArea("Business price: ");
        businessPriceTextArea.setEditable(false);
        TextField businessPriceTextField = new TextField();
        businessPriceTextField.setMinimumSize(new Dimension(90,16));
        panel.add(businessPriceTextArea);
        panel.add(businessPriceTextField,"wrap");

        JTextArea economyPriceTextArea = new JTextArea("Economy price: ");
        economyPriceTextArea.setEditable(false);
        TextField economyPriceTextField = new TextField();
        economyPriceTextField.setMinimumSize(new Dimension(90,16));
        panel.add(economyPriceTextArea);
        panel.add(economyPriceTextField,"wrap");

        JButton insertFlButton = new JButton("INSERT FLIGHT");
        insertFlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String businessPrice = businessPriceTextField.getText();
                String economyPrice = economyPriceTextField.getText();
                if(CheckingInputInformation.isPriceValid(businessPrice) && CheckingInputInformation.isPriceValid(economyPrice)){
                    double businessPriceDouble = Double.parseDouble(businessPrice);
                    double economyPriceDouble = Double.parseDouble(economyPrice);
                    java.util.List ticketList = new LinkedList<>();
                    Ticket businessTicket = new Ticket();
                    businessTicket.setTicketClass(TicketClass.BUSINESS);
                    businessTicket.setPrice(businessPriceDouble);
                    ticketList.add(businessTicket);

                    Ticket economyTicket = new Ticket();
                    economyTicket.setTicketClass(TicketClass.ECONOMY);
                    economyTicket.setPrice(economyPriceDouble);
                    ticketList.add(economyTicket);

                    flight.setTickets(ticketList);

                    Main.getFlights().add(flight);

                    UserInerface.setPanelTicketsView(null);
                    UserInerface.setPanelPassengersView(null);

                    try {
                        DBLoading.insertFlightAndTickets(flight);

                        UserInerface.getPanel().remove(UserInerface.getPanelView());
                        UserInerface.setPanelFlightsVew(UserInerface.createPanelFlightsView());
                        UserInerface.setPanelView(null);
                        UserInerface.setPanelView(UserInerface.getPanelFlightsVew());
                        UserInerface.getPanel().add(UserInerface.getPanelView(),"span");
                        UserInerface.updateFrame();

                        insertFlightframe.setVisible(false);
                        showSuccessInsertMessage();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }

                } else {
                    GUIObjectsMaker.showWarningDataValidationMessage();
                }
            }
        });

        panel.add(insertFlButton);
        insertFlightframe.add(panel);
        insertFlightframe.setVisible(true);
    }
    public static JTable createSelectedTickets(JComboBox flightComboBox, JComboBox ticketClassComboBox){
        Main.getSelectedTickets().clear();
        ggg:
        if(flightComboBox.getSelectedItem().toString().equals("ALL")){
            for(Flight flight:Main.getFlights()){
                Main.getSelectedTickets().addAll(flight.getTickets());
            }
        } else {
            for(Flight flight:Main.getFlights()){
                if(flightComboBox.getSelectedItem().toString().equals(flight.getFlightNumber())){
                    Main.getSelectedTickets().addAll(flight.getTickets());
                    break ggg;
                }
            }
        }
        if(!ticketClassComboBox.getSelectedItem().toString().equals("ALL")){
            Iterator<Ticket> iterator = Main.getSelectedTickets().iterator();
            while (iterator.hasNext()) {
                Ticket ticket = iterator.next();
                if(!ticketClassComboBox.getSelectedItem().toString().equals(ticket.getTicketClass().toString())){
                    iterator.remove();
                }
            }
        }
        String[][] rows = new String[Main.getSelectedTickets().size()][];
        int index = 0;
        for(Ticket ticket:Main.getSelectedTickets()){
            String[] row = new String[COLUMNS_FOR_TICKETS.length];
            int i = 0;
            ggg:
            for(Flight flight:Main.getFlights()){
                if(flight.getTickets().contains(ticket)){
                    row[i++] = flight.getFlightNumber();
                    break ggg;
                }
            }
            row[i++] = ticket.getTicketClass().name();
            row[i++] = String.valueOf(ticket.getPrice());
            rows[index++] = row;
        }
        return new JTable(rows,COLUMNS_FOR_TICKETS){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
}
