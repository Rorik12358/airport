package resorses;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Created by r2-d2 on 23.04.16.
 */
public class ReadingWritingPassenger {
    public static void main(String[] args) {
        try(BufferedReader reader = new BufferedReader(new FileReader("src/resorses/names"))) {
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
                Class cls = Class.forName("org.h2.Driver");
                Connection connection = DriverManager.getConnection("jdbc:h2:~/airportDataBase","sa","");
                PreparedStatement statement = connection.prepareStatement("INSERT INTO PASSENGERS VALUES(?,?,?,?,?,?,?,?)");
                statement.setString(1,firstName);
                statement.setString(2,secondName);
                statement.setString(3,nationalities[nt]);
                if(nt < nationalities.length-1){
                    nt++;
                } else {
                    nt = 0;
                }
                char ch1 = (char) (random.nextInt(15)+100);
                char ch2 = (char) (random.nextInt(15)+100);
                StringBuilder pasp = new StringBuilder();
                pasp.append(ch1).append(ch2).append(index);
                statement.setString(4,pasp.toString().toUpperCase());
                java.sql.Date birthday = new java.sql.Date(random.nextLong()/10000000);
                statement.setDate(5,birthday);
                statement.setInt(6,random.nextInt(2)+1);
                statement.setInt(7,random.nextInt(2)+1);
                StringBuilder flight = new StringBuilder("#FLGHT");
                flight.append(numberOfLight);
                if(numberOfLight == 20){
                    numberOfLight = 1;
                } else{
                    numberOfLight++;
                }
                statement.setString(8,flight.toString());
                index++;
                statement.execute();




            }
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
}
