package business_logic;

import user_interface.UserInerface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by r2-d2 on 27.04.16.
 */
public class FlightsButtonActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if(UserInerface.getPanelView()!=UserInerface.getPanelFlightsVew()) {
            UserInerface.getPanel().remove(UserInerface.getPanelView());
            if(UserInerface.getPanelFlightsVew()==null) {
                try {
                    UserInerface.setPanelFlightsVew(UserInerface.createPanelFlightsView());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }

            }
            UserInerface.setPanelView(UserInerface.getPanelFlightsVew());
            UserInerface.getPanel().add(UserInerface.getPanelView(), "span");
            UserInerface.updateFrame();
        }
    }
}
