package business_logic;

import user_interface.Main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by r2-d2 on 29.04.16.
 */
public class CheckingInputInformation {

    public static boolean isFlightNumberForPassengersValid(String flightNumber){
        StringBuilder stringForPattern = new StringBuilder("(");
        for(Flight flight : Main.getFlights()){
            stringForPattern.append(flight.getFlightNumber()+"|");
        }
        String stringForPatternStr = stringForPattern.substring(0,stringForPattern.length()-1)+")";
        Pattern pattern = Pattern.compile(stringForPatternStr);
        Matcher matcher = pattern.matcher(flightNumber);
        return matcher.matches();
    }
    public static boolean isFlightNumberForInsertValid(String flightNumber){
        Pattern pattern = Pattern.compile("#FLGHT\\d*");
        Matcher matcher = pattern.matcher(flightNumber);
        return matcher.matches() && !isFlightNumberForPassengersValid(flightNumber);
    }
    public static boolean isFlightNumberForUpdateValid(String flightNumber){
        if(flightNumber.equals(Main.getSelectedFlights().get(0).getFlightNumber())){
            return true;
        }
        Pattern pattern = Pattern.compile("#FLGHT\\d*");
        Matcher matcher = pattern.matcher(flightNumber);
        return matcher.matches() && !isFlightNumberForPassengersValid(flightNumber);
    }
    public static boolean isNameValid(String name){
        Pattern pattern = Pattern.compile("[A-Z][a-z]+");
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
    public static boolean isNationalityValid(String nationality){
        Pattern pattern = Pattern.compile("[a-z]+");
        Matcher matcher = pattern.matcher(nationality);
        return matcher.matches();
    }
    public static boolean isPassportForInsertValid(String passpor){
        for(Passenger passenger : Main.getPassengers()){
            if(passpor.equals(passenger.getPassport())){
                return false;
            }
        }
        Pattern pattern = Pattern.compile("[A-Z]{2}\\d+");
        Matcher matcher = pattern.matcher(passpor);
        return matcher.matches();
    }
    public static boolean isPassportForUpdateValid(String passport){
        if(passport.equals(Main.getSelectedPassengers().get(0).getPassport())){
            return true;
        }
        for(Passenger passenger : Main.getPassengers()){
            if(passport.equals(passenger.getPassport())){
                return false;
            }
        }
        Pattern pattern = Pattern.compile("[A-Z]{2}\\d+");
        Matcher matcher = pattern.matcher(passport);
        return matcher.matches();
    }
    public static boolean isBirthdayValid(String birthday){
        Pattern pattern = Pattern.compile("([0-2]\\d|3[0-1])\\.(0[1-9]|1[0-2])\\.(19\\d\\d|2000)");
        Matcher matcher = pattern.matcher(birthday);
        return matcher.matches();
    }
    public static boolean isSexValid(String sex){
        StringBuilder stringBuilderForPattern = new StringBuilder("(");
        for(Sex sexl:Sex.values()){
            stringBuilderForPattern.append(sexl.name()+"|");
        }
        Pattern pattern = Pattern.compile(stringBuilderForPattern.substring(0,stringBuilderForPattern.length()-1)+")");
        Matcher matcher = pattern.matcher(sex);
        return matcher.matches();
    }
    public static boolean isTicketClassValid(String ticketClass){
        StringBuilder stringBuilderForPattern = new StringBuilder("(");
        for(TicketClass ticketClass1:TicketClass.values()){
            stringBuilderForPattern.append(ticketClass1.name()+"|");
        }
        Pattern pattern = Pattern.compile(stringBuilderForPattern.substring(0,stringBuilderForPattern.length()-1)+")");
        Matcher matcher = pattern.matcher(ticketClass);
        return matcher.matches();
    }
    public static boolean isFlightTypeValid(String flightType){
        StringBuilder stringForPattern = new StringBuilder("(");
        for(FlightType ft:FlightType.values()){
            stringForPattern.append(ft.name()+"|");
        }
        String stringForPatternStr = stringForPattern.substring(0,stringForPattern.length()-1)+")";
        Pattern pattern = Pattern.compile(stringForPatternStr);
        Matcher matcher = pattern.matcher(flightType);
        return matcher.matches();
    }
    public static boolean isTimeValid(String time){
        Pattern pattern = Pattern.compile("([0-2]\\d|3[0-1])\\.(0[1-9]|1[0-2])\\.2016 at ([0-1]\\d|2[0-3]):[0-5]\\d");
        Matcher matcher = pattern.matcher(time);
        return matcher.matches();
    }
    public static boolean isCityValid(String city){
        Pattern pattern = Pattern.compile("[A-Z][a-z]+( [A-Z][a-z]+)*");
        Matcher matcher = pattern.matcher(city);
        return matcher.matches();
    }
    public static boolean isTerminalValid(String terminal){
        Pattern pattern = Pattern.compile("A|B|C");
        Matcher matcher = pattern.matcher(terminal);
        return matcher.matches();
    }
    public static boolean isStatusValid(String status){
        StringBuilder stringForPattern = new StringBuilder("(");
        for(FlightStatus fs:FlightStatus.values()){
            stringForPattern.append(fs.name()+"|");
        }
        String stringForPatternStr = stringForPattern.substring(0,stringForPattern.length()-1)+")";
        Pattern pattern = Pattern.compile(stringForPatternStr);
        Matcher matcher = pattern.matcher(status);
        return matcher.matches();
    }
    public static boolean isGateValid(String gate){
        Pattern pattern = Pattern.compile("[1-3]");
        Matcher matcher = pattern.matcher(gate);
        return matcher.matches();
    }
    public static boolean isPriceValid(String price){
        Pattern pattern = Pattern.compile("\\d+\\.\\d{2}");
        Matcher matcher = pattern.matcher(price);
        return matcher.matches();
    }
}
