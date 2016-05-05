package resorses;

import javax.swing.*;
import java.sql.SQLException;

/**
 * Created by r2-d2 on 26.04.16.
 */
public class TryGUI {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String[] cols = {"Name","Second Name"};
        String[][] rows = {{"Jon","Biba"},{"Juliya","Sex"}};
        JFrame frame = new JFrame("Try user_interface");
        frame.setLocation(10,100);
        frame.setSize(300,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTable table = new JTable(rows, cols);
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel panel = new JPanel();
        panel.add(scrollPane);
        frame.add(panel);
        frame.setVisible(true);
    }
}
