package resorses;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by r2-d2 on 24.04.16.
 */
public class WritingFlights {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class clsc = Class.forName("org.h2.Driver");
        Connection connection = DriverManager.getConnection("jdbc:h2:~/airportDataBase","sa","");
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
    }
}
