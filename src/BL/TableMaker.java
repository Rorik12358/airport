package BL;

import GUI.Main;
import GUI.UserInerface;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Iterator;

/**
 * Created by r2-d2 on 25.04.16.
 */
public class TableMaker {
    private final static String[] COLUMNS_FOR_TABLE_FLIGHTS = {"FLIGHT TYPE", "TIME",
            "FLIGHT NUMBER", "CITY", "TERMINAL", "STATUS", "GATE"};
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy 'at' HH:mm");
        iterator = Main.getFlights().iterator();
        int indexForRowsDepartureFlights = 0;
        int indexForRowsArrivalFlights = 0;
        while (iterator.hasNext()){
            Flight flight = iterator.next();
            String[] row = new String[COLUMNS_FOR_TABLE_FLIGHTS.length];
            int i = 0;
            FlightType type = flight.getType();
            row[i++] = type.name();
            row[i++] = dateFormat.format(flight.getTime());
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

    public static JTable createFlightsArrivalTable(){
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
    public static JTable createSelectedFlight(){
        JComboBox comboBoxFlights = UserInerface.getComboBoxFlights();
        String[][] selectedFlight = new String[1][COLUMNS_FOR_TABLE_FLIGHTS.length];
        Flight flight = null;
        for(Flight fl : Main.getFlights()) {
            if(fl.getFlightNumber().equals(comboBoxFlights.getSelectedItem().toString())){
                flight = fl;
            }
        }
        int i = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy 'at' HH:mm");
        selectedFlight[0][i++] = flight.getType().name();
        selectedFlight[0][i++] = dateFormat.format(flight.getTime());
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
}
