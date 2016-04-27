package BL;

import GUI.Main;

import java.text.SimpleDateFormat;
import java.util.Iterator;

/**
 * Created by r2-d2 on 25.04.16.
 */
public class TableMaker {
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
            String[] row = new String[Main.getColumnsForTableFlights().length];
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
        Main.setRowsForTableFlightsDeparture(rowsDepartureFlights);
        Main.setRowsForTableFlightsArrival(rowsArrivalFlights);
    }
}
