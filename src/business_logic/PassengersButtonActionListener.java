package business_logic;

import user_interface.UserInerface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by r2-d2 on 26.04.16.
 */
public class PassengersButtonActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if(UserInerface.getPanelView()!=UserInerface.getPanelPassengersView()) {
            if (UserInerface.getPanelPassengersView() == null) {
                try {
                    UserInerface.setPanelPassengersView(UserInerface.createPanelPassengerView());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
            UserInerface.getPanel().remove(UserInerface.getPanelView());
            UserInerface.setPanelView(UserInerface.getPanelPassengersView());
            UserInerface.getPanel().add(UserInerface.getPanelView(), "span");
            UserInerface.updateFrame();
        }
    }
}
