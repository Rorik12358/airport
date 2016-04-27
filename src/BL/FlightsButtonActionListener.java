package BL;

import GUI.UserInerface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by r2-d2 on 27.04.16.
 */
public class FlightsButtonActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if(UserInerface.getPanelView()!=UserInerface.getPanelFlightsVew()) {
            UserInerface.setSelectedFlight(TableMaker.createSelectedFlight());

            UserInerface.getPanel().remove(UserInerface.getPanelView());
            UserInerface.setPanelView(UserInerface.getPanelFlightsVew());
            UserInerface.getPanel().add(UserInerface.getPanelView(), "span");
            UserInerface.getFrame().setVisible(false);
            UserInerface.getFrame().setVisible(true);
        }
    }
}
