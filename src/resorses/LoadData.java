package resorses;

import data_base.DBLoading;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Created by r2-d2 on 23.04.16.
 */
public class LoadData {
    public static void loadPassengers() {
        try(BufferedReader reader = new BufferedReader(new FileReader("src/resorses/names"))) {
            Class cls = Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(DBLoading.addressDB,DBLoading.loginDB,DBLoading.passwordDB);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM PASSENGERS");
            String line;
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            Random random = new Random();
            String[] nationalities = new String[]{"ukrainian","american","russian","italian","german", "british", "australian"};
            int nt = 0;
            int index = 0;
            int numberOfLight = 1;
            while ((line = reader.readLine())!=null){
                StringTokenizer tokenizer = new StringTokenizer(line);
                String firstName = tokenizer.nextToken();
                String secondName = tokenizer.nextToken();
                PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO PASSENGERS VALUES(?,?,?,?,?,?,?,?)");
                prepareStatement.setString(1,firstName);
                prepareStatement.setString(2,secondName);
                prepareStatement.setString(3,nationalities[nt]);
                if(nt < nationalities.length-1){
                    nt++;
                } else {
                    nt = 0;
                }
                char ch1 = (char) (random.nextInt(15)+100);
                char ch2 = (char) (random.nextInt(15)+100);
                StringBuilder pasp = new StringBuilder();
                pasp.append(ch1).append(ch2).append(index);
                prepareStatement.setString(4,pasp.toString().toUpperCase());
                java.sql.Date birthday = new java.sql.Date(random.nextLong()/10000000);
                prepareStatement.setDate(5,birthday);
                prepareStatement.setInt(6,random.nextInt(2)+1);
                prepareStatement.setInt(7,random.nextInt(2)+1);
                StringBuilder flight = new StringBuilder("#FLGHT");
                flight.append(numberOfLight);
                if(numberOfLight == 20){
                    numberOfLight = 1;
                } else{
                    numberOfLight++;
                }
                prepareStatement.setString(8,flight.toString());
                index++;
                prepareStatement.execute();
            }
            connection.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void loadFlights() throws ClassNotFoundException, SQLException {
        Class clsc = Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection(DBLoading.addressDB,DBLoading.loginDB,DBLoading.passwordDB);
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM FLIGHTS");
        String[] cities = {"Kiev","New York","Moscow","Cape Town", "Berlin","Istanbul","Prague",
                "London","Rome","Venissa"};
        String[] terminals = {"A","B","C"};
        int cityIndex = 0;
        Random random = new Random();
        java.util.Date now = new Date();
        for(int i = 0; i < 20; i++){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO FLIGHTS VALUES(?,?,?,?,?,?,?)");
            preparedStatement.setString(1,"#FLGHT"+(i+1));
            int flightType = i%2+1;
            preparedStatement.setInt(2, flightType);
//            Date time = new Date(now.getTime()+1036800000+random.nextInt(2*432000000));
            Timestamp time = new Timestamp(now.getTime()+1036800000+random.nextInt(432000000));
//            time.setHours(random.nextInt(25));
//            time.setMinutes(random.nextInt(61));
            preparedStatement.setTimestamp(3,time);
            preparedStatement.setString(4,cities[cityIndex]);
            if(i%2==1){
                cityIndex++;
            }
            String ternimal;
            if(flightType == 1){
                ternimal = terminals[random.nextInt(2)];
            } else {
                ternimal = terminals[2];
            }
            preparedStatement.setString(5,ternimal);
            preparedStatement.setInt(6,random.nextInt(9)+1);
            preparedStatement.setString(7,""+(random.nextInt(3)+1));
            preparedStatement.executeUpdate();
        }
        connection.close();
    }
    public static void loadPrices() throws ClassNotFoundException, SQLException {
        Class cls = Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection(DBLoading.addressDB,DBLoading.loginDB,DBLoading.passwordDB);
        Statement statement = connection.createStatement();
        statement.execute("DELETE FROM TICKETS");
        Statement statementPass = connection.createStatement();
        ResultSet resultSet = statementPass.executeQuery("SELECT * FROM FLIGHTS");
        int index = 1;
        while (resultSet.next()){
            int i = 0;
            while(i<2) {
                String flightNumber = resultSet.getString("FLIGHT_NUMBER");
                int ticketClass = (index + 1) % 2 + 1;
                double price = 500;

                if (flightNumber.equals("#FLGHT1") || flightNumber.equals("#FLGHT2")) {
                    if (ticketClass == 1) {
                        price = 722.15;
                    } else {
                        price = 440.98;
                    }
                }

                if (flightNumber.equals("#FLGHT3") || flightNumber.equals("#FLGHT4")) {
                    if (ticketClass == 1) {
                        price = 999.99;
                    } else {
                        price = 750;
                    }
                }

                if (flightNumber.equals("#FLGHT5") || flightNumber.equals("#FLGHT6")) {
                    if (ticketClass == 1) {
                        price = 497;
                    } else {
                        price = 325.55;
                    }
                }

                if (flightNumber.equals("#FLGHT7") || flightNumber.equals("#FLGHT8")) {
                    if (ticketClass == 1) {
                        price = 1400;
                    } else {
                        price = 100;
                    }
                }

                if (flightNumber.equals("#FLGHT9") || flightNumber.equals("#FLGHT10")) {
                    if (ticketClass == 1) {
                        price = 782;
                    } else {
                        price = 555.55;
                    }
                }

                if (flightNumber.equals("#FLGHT11") || flightNumber.equals("#FLGHT12")) {
                    if (ticketClass == 1) {
                        price = 800;
                    } else {
                        price = 572.12;
                    }
                }

                if (flightNumber.equals("#FLGHT13") || flightNumber.equals("#FLGHT14")) {
                    if (ticketClass == 1) {
                        price = 577.15;
                    } else {
                        price = 470;
                    }
                }

                if (flightNumber.equals("#FLGHT15") || flightNumber.equals("#FLGHT16")) {
                    if (ticketClass == 1) {
                        price = 1122;
                    } else {
                        price = 970;
                    }
                }

                if (flightNumber.equals("#FLGHT17") || flightNumber.equals("#FLGHT18")) {
                    if (ticketClass == 1) {
                        price = 577;
                    } else {
                        price = 498.12;
                    }
                }

                if (flightNumber.equals("#FLGHT19") || flightNumber.equals("#FLGHT20")) {
                    double basePrice = 1077;
                    if (ticketClass == 1) {
                        price = 1597;
                    } else {
                        price = 1077;
                    }
                }

                PreparedStatement statementInsTickets = connection.prepareStatement("INSERT INTO TICKETS VALUES(?,?,?,?)");
                statementInsTickets.setInt(1, index);
                statementInsTickets.setInt(2, ticketClass);
                statementInsTickets.setDouble(3, price);
                statementInsTickets.setString(4, flightNumber);
                statementInsTickets.execute();
                index++;
                i++;
            }
        }
        connection.close();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        loadPassengers();
        loadFlights();
        loadPrices();
    }
}
