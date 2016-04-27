package resorses;

import java.sql.*;

/**
 * Created by r2-d2 on 24.04.16.
 */
public class WritingTickets {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class cls = Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection("jdbc:h2:~/airportDataBase","sa","");
        Statement statementPass = connection.createStatement();
        ResultSet resultSet = statementPass.executeQuery("SELECT * FROM PASSENGERS");
        int index = 1;
        while (resultSet.next()){
            String flightNumber = resultSet.getString("FLIGHT_NUMBER");
            int ticketClass = resultSet.getInt("TICKET_CLASS");
            double price = 500;

            if(flightNumber.equals("#FLGHT1") || flightNumber.equals("#FLGHT2")){
                if(ticketClass == 1){
                    price = 722.15;
                } else {
                    price = 440.98;
                }
            }

            if(flightNumber.equals("#FLGHT3") || flightNumber.equals("#FLGHT4")){
                if(ticketClass == 1){
                    price = 999.99;
                } else {
                    price = 750;
                }
            }

            if(flightNumber.equals("#FLGHT5") || flightNumber.equals("#FLGHT6")){
                if(ticketClass == 1){
                    price = 497;
                } else {
                    price = 325.55;
                }
            }

            if(flightNumber.equals("#FLGHT7") || flightNumber.equals("#FLGHT8")){
                if(ticketClass == 1){
                    price = 1400;
                } else {
                    price = 100;
                }
            }

            if(flightNumber.equals("#FLGHT9") || flightNumber.equals("#FLGHT10")){
                if(ticketClass == 1){
                    price = 782;
                } else {
                    price = 555.55;
                }
            }

            if(flightNumber.equals("#FLGHT11") || flightNumber.equals("#FLGHT12")){
                if(ticketClass == 1){
                    price = 800;
                } else {
                    price = 572.12;
                }
            }

            if(flightNumber.equals("#FLGHT13") || flightNumber.equals("#FLGHT14")){
                if(ticketClass == 1){
                    price = 577.15;
                } else {
                    price = 470;
                }
            }

            if(flightNumber.equals("#FLGHT15") || flightNumber.equals("#FLGHT16")){
                if(ticketClass == 1){
                    price = 1122;
                } else {
                    price = 970;
                }
            }

            if(flightNumber.equals("#FLGHT17") || flightNumber.equals("#FLGHT18")){
                if(ticketClass == 1){
                    price = 577;
                } else {
                    price = 498.12;
                }
            }

            if(flightNumber.equals("#FLGHT19") || flightNumber.equals("#FLGHT20")){
                double basePrice = 1077;
                if(ticketClass == 1){
                    price = 1597;
                } else {
                    price = 1077;
                }
            }

            PreparedStatement statementInsTickets = connection.prepareStatement("INSERT INTO TICKETS VALUES(?,?,?,?)");
            statementInsTickets.setInt(1,index);
            statementInsTickets.setInt(2, ticketClass);
            statementInsTickets.setDouble(3, price);
            statementInsTickets.setString(4,flightNumber);
            statementInsTickets.execute();
            index++;
        }

    }
}
