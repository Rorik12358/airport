package GUI;

import BL.Flight;
import BL.FlightsButtonActionListener;
import BL.PassengersButtonActionListener;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/**
 * Created by r2-d2 on 24.04.16.
 */
public class UserInerface implements Runnable {
    private static JButton flightsButton = new JButton("Flights Board");
    private static JButton ticketsButton = new JButton("Prices of ticketsButton");
    private static JButton passengersButton = new JButton("Passengers");

    private static JFrame frame;
    private static JPanel panel;

    public static JFrame getFrame() {
        return frame;
    }

    public static JPanel getPanel() {
        return panel;
    }

    private static JPanel panelView;
    private static JPanel panelFlightsVew;
    private static JTable flightsArrivalTable;
    private static JTable flightsDepartureTable;

    private static JPanel panelPassengersView;

    public static JTable getFlightsDepartureTable() {
        return flightsDepartureTable;
    }
    public static JTable getFlightsArrivalTable() {
        return flightsArrivalTable;
    }

    public static JPanel getPanelView() {
        return panelView;
    }
    public static void setPanelView(JPanel panelView) {
        UserInerface.panelView = panelView;
    }
    public static JPanel getPanelFlightsVew() {
        return panelFlightsVew;
    }
    public static void setPanelFlightsVew(JPanel panelFlightsVew) {
        UserInerface.panelFlightsVew = panelFlightsVew;
    }
    public static JPanel getPanelPassengersView() {
        return panelPassengersView;
    }
    public static void setPanelPassengersView(JPanel panelPassengersView) {
        UserInerface.panelPassengersView = panelPassengersView;
    }

    @Override
    public void run() {
        frame = new JFrame("Airport --Romchik&Co--");
        frame.setSize(1020,400);
        frame.setLocation(10,10);
        panel = new JPanel(new MigLayout());
        frame.add(panel);
        panel.add(flightsButton);
        panel.add(ticketsButton);
        panel.add(passengersButton,"wrap");


        //Start creating Flight view
        panelFlightsVew = new JPanel(new MigLayout());

        flightsArrivalTable = new JTable(Main.getRowsForTableFlightsArrival(),Main.getColumnsForTableFlights()){
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
        JScrollPane scrollPaneForArrivalFlight = new JScrollPane(flightsArrivalTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panelFlightsVew.add(scrollPaneForArrivalFlight);

        flightsDepartureTable = new JTable(Main.getRowsForTableFlightsDeparture(), Main.getColumnsForTableFlights()){
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
        JScrollPane scrollPaneForDepartureFlight = new JScrollPane(flightsDepartureTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panelFlightsVew.add(scrollPaneForDepartureFlight, "wrap");

        JComboBox comboBoxFlights = createComboBoxFlights();
        panelFlightsVew.add(comboBoxFlights);
        String[][] selectedFlight = new String[Main.getColumnsForTableFlights().length][];
        Flight flight = null;
        for(Flight fl : Main.getFlights()) {
            if(fl.getFlightNumber().equals(comboBoxFlights.getSelectedItem().toString())){
                flight = fl;
            }
        }
        int i = 0;
        selectedFlight[0][i++] = flight.getType().name();
        //selectedFlight[0][i++] =
        //end of creating Flights view

        panelView = panelFlightsVew;
        panel.add(panelView, "span");

        passengersButton.addActionListener(new PassengersButtonActionListener());
        flightsButton.addActionListener(new FlightsButtonActionListener());

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static JComboBox createComboBoxFlights(String all){
        String[] itemsForComboBoxFlights = new String[Main.getFlights().size()+1];
        itemsForComboBoxFlights[0] = "ALL";
        for(int i = 1; i < itemsForComboBoxFlights.length; i++){
            itemsForComboBoxFlights[i] = Main.getFlights().get(i-1).getFlightNumber();
        }
        return new JComboBox(itemsForComboBoxFlights);
    }
    public static JComboBox createComboBoxFlights(){
        String[] itemsForComboBoxFlights = new String[Main.getFlights().size()];
        for(int i = 0; i < itemsForComboBoxFlights.length; i++){
            itemsForComboBoxFlights[i] = Main.getFlights().get(i).getFlightNumber();
        }
        return new JComboBox(itemsForComboBoxFlights);
    }
}
