package resorses;

import business_logic.CheckingInputInformation;

import java.sql.SQLException;

/**
 * Created by r2-d2 on 23.04.16.
 */
public class M {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println(CheckingInputInformation.isFlightTypeValid("ARRIVAL"));
        System.out.println(CheckingInputInformation.isTimeValid("19.05.2016 at 9:00"));
        System.out.println(CheckingInputInformation.isCityValid("Kiev"));
        System.out.println(CheckingInputInformation.isTerminalValid("A"));
        System.out.println(CheckingInputInformation.isStatusValid("DEPARTED_AT"));
        System.out.println(CheckingInputInformation.isGateValid("2"));

        //System.out.println(CheckingInputInformation.isPriceValid("1288.15"));
    }
}

