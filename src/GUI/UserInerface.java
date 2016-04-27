package GUI;

import BL.Flight;
import BL.FlightsButtonActionListener;
import BL.PassengersButtonActionListener;
import BL.TableMaker;
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
    private static JComboBox comboBoxFlights;

    private static JFrame frame;
    private static JPanel panel;
    private static JPanel panelView;
    private static JPanel panelFlightsVew;
    private static JTable flightsArrivalTable;
    private static JTable flightsDepartureTable;
    private static JTable selectedFlight;
    private static JPanel panelPassengersView;

    public static JComboBox getComboBoxFlights() {
        return comboBoxFlights;
    }
    public static JFrame getFrame() {
        return frame;
    }
    public static JPanel getPanel() {
        return panel;
    }
    public static JTable getSelectedFlight() {
        return selectedFlight;
    }
    public static void setSelectedFlight(JTable selectedFlight) {
        UserInerface.selectedFlight = selectedFlight;
    }
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

        flightsArrivalTable = TableMaker.createFlightsArrivalTable();
        JScrollPane scrollPaneForArrivalFlight = new JScrollPane(flightsArrivalTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panelFlightsVew.add(scrollPaneForArrivalFlight);

        flightsDepartureTable = TableMaker.createFlightsDepartureTable();
        JScrollPane scrollPaneForDepartureFlight = new JScrollPane(flightsDepartureTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panelFlightsVew.add(scrollPaneForDepartureFlight, "wrap");

        comboBoxFlights = createComboBoxFlights();
        panelFlightsVew.add(comboBoxFlights,"wrap");

        selectedFlight = TableMaker.createSelectedFlight();
        JScrollPane scrollPaneForSelectedFlight = new JScrollPane(selectedFlight);
        panelFlightsVew.add(scrollPaneForSelectedFlight,"span");
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
