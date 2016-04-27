package BL;

import GUI.Main;
import GUI.UserInerface;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

/**
 * Created by r2-d2 on 26.04.16.
 */
public class PassengersButtonActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if(UserInerface.getPanelView()!=UserInerface.getPanelPassengersView()) {
            if (UserInerface.getPanelPassengersView() == null) {
                JPanel passengerView = new JPanel(new MigLayout());
                String[] cols = {"FLIGHT NUMBER", "FIRST NAME", "LAST NAME", "NATIONALITY", "PASSPORT",
                        "BIRTHDAY", "SEX", "TICKET CLASS"};
                String[][] rows = new String[Main.getPassengers().size()][];
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                int indexOfRow = 0;
                for (Flight flight : Main.getFlights()) {
                    for (Passenger passenger : flight.getPassengers()) {
                        String[] row = new String[cols.length];
                        int i = 0;
                        row[i++] = flight.getFlightNumber();
                        row[i++] = passenger.getFirstName();
                        row[i++] = passenger.getLastName();
                        row[i++] = passenger.getNationality();
                        row[i++] = passenger.getPassport();
                        row[i++] = format.format(passenger.getBirthday().getTime());
                        row[i++] = passenger.getSex().name();
                        row[i++] = passenger.getTicketClass().name();
                        rows[indexOfRow++] = row;
                    }
                }
                JTable tablePassengers = new JTable(rows, cols) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                tablePassengers.setPreferredScrollableViewportSize(new Dimension(1000, 200));
                JScrollPane scrollPaneforTablePassengers = new JScrollPane(tablePassengers);
                passengerView.add(UserInerface.createComboBoxFlights("all"), "wrap");
                passengerView.add(scrollPaneforTablePassengers);
                UserInerface.setPanelPassengersView(passengerView);
            }
            UserInerface.getPanel().remove(UserInerface.getPanelView());
            UserInerface.setPanelView(UserInerface.getPanelPassengersView());
            UserInerface.getPanel().add(UserInerface.getPanelView(), "span");
            UserInerface.getFrame().setVisible(false);
            UserInerface.getFrame().setVisible(true);
        }
    }
}
