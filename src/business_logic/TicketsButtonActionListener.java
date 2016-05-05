package business_logic;

import user_interface.UserInerface;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Created by r2-d2 on 04.05.16.
 */
public class TicketsButtonActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        if(UserInerface.getPanelView()!=UserInerface.getPanelTicketsView()) {
            UserInerface.getPanel().remove(UserInerface.getPanelView());
            if(UserInerface.getPanelTicketsView()==null) {
                try {
                    UserInerface.setPanelTicketsView(UserInerface.createPanelTicketsView());
                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }

            }
            UserInerface.setPanelView(UserInerface.getPanelTicketsView());
            UserInerface.getPanel().add(UserInerface.getPanelView(), "span");
            UserInerface.updateFrame();
        }
    }
}
