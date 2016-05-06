package test;

import business_logic.*;
import data_base.DBLoading;
import org.junit.*;
import user_interface.Main;
import user_interface.UserInerface;
import java.sql.SQLException;

/**
 * Created by r2-d2 on 05.05.16.
 */
public class TestCheckingInputInformation {
    @BeforeClass
    public static void loading() throws SQLException, ClassNotFoundException {
        DBLoading.loadFlights();
        DBLoading.loadPassengers();
    }
    @Test
    public void testIsFlightNumberForPassengersValid(){
        for(Flight flight : Main.getFlights()){
            Assert.assertTrue("testing of CheckingInputInformation.isFlightNumberForPassengersValid failed "+flight.getFlightNumber(),
                    CheckingInputInformation.isFlightNumberForPassengersValid(flight.getFlightNumber()));
        }
    }
    @Test
    public void testIsNameValid(){
        Assert.assertTrue("testing of CheckingInputInformation.isNameValid failed 1",CheckingInputInformation.isNameValid("Bob"));
        Assert.assertFalse("testing of CheckingInputInformation.isNameValid failed 2",CheckingInputInformation.isNameValid("Bob3"));
    }
    @Test
    public void testIsNationalityValid(){
        Assert.assertTrue("testing of CheckingInputInformation.isNationalityValid failed 1",CheckingInputInformation.isNationalityValid("ukrainian"));
        Assert.assertFalse("testing of CheckingInputInformation.isNationalityValid failed 2",CheckingInputInformation.isNationalityValid("Ukrainian"));
    }
    @Test
    public void testIsPassportForInsertValid() throws SQLException, ClassNotFoundException {
        UserInerface.createPanelPassengerView();
        Assert.assertTrue("testing of CheckingInputInformation.isPassportForInsertValid failed 1",CheckingInputInformation.isPassportForInsertValid("AA8773812345678"));
        Assert.assertFalse("testing of CheckingInputInformation.isNationalityValid failed 2",CheckingInputInformation.isPassportForInsertValid("AAS98"));
    }
    @Test
    public void testIsPassportForUpdateValid() throws SQLException, ClassNotFoundException {
        UserInerface.createPanelPassengerView();
        Assert.assertTrue("testing of CheckingInputInformation.isPassportForUpdateValid failed 1",CheckingInputInformation.isPassportForUpdateValid("AA8773812345678"));
        Assert.assertFalse("testing of CheckingInputInformation.isPassportForUpdateValid failed 2",CheckingInputInformation.isPassportForUpdateValid("AAS98"));
    }
    @Test
    public void testIsBirthdayValid(){
        Assert.assertTrue("testing of CheckingInputInformation.isBirthdayValid failed 1",CheckingInputInformation.isBirthdayValid("21.12.1948"));
        Assert.assertFalse("testing of CheckingInputInformation.isBirthdayValid failed 2",CheckingInputInformation.isBirthdayValid("AAS98"));
        Assert.assertFalse("testing of CheckingInputInformation.isBirthdayValid failed 3",CheckingInputInformation.isBirthdayValid("21.12.1748"));
        Assert.assertFalse("testing of CheckingInputInformation.isBirthdayValid failed 4",CheckingInputInformation.isBirthdayValid("41.12.1948"));
        Assert.assertFalse("testing of CheckingInputInformation.isBirthdayValid failed 5",CheckingInputInformation.isBirthdayValid("11.22.1948")) ;
    }
    @Test
    public void testIsSexValid(){
        Assert.assertTrue("testing of CheckingInputInformation.isSexValid failed 1",CheckingInputInformation.isSexValid("MAN"));
        Assert.assertTrue("testing of CheckingInputInformation.isSexValid failed 2",CheckingInputInformation.isSexValid("WOMAN"));
        Assert.assertFalse("testing of CheckingInputInformation.isSexValid failed 3",CheckingInputInformation.isSexValid("MALE"));
        Assert.assertFalse("testing of CheckingInputInformation.isSexValid failed 4",CheckingInputInformation.isSexValid("gay"));
    }
    @Test
    public void testIsTicketClassValid(){
        int i = 0;
        for(TicketClass ticketClass : TicketClass.values()){
            Assert.assertTrue("testing of CheckingInputInformation.isTicketClassValid failed "+ticketClass.name(),
                    CheckingInputInformation.isTicketClassValid(ticketClass.name()));
            Assert.assertFalse("testing of CheckingInputInformation.isTicketClassValid failed ",
                    CheckingInputInformation.isTicketClassValid(ticketClass.name()+i++));
        }
    }
    @Test
    public void testIsFlightTypeValid(){
        int i = 0;
        for(FlightType flightType : FlightType.values()){
            Assert.assertTrue("testing of CheckingInputInformation.isFlightTypeValid failed "+flightType.name(),
                    CheckingInputInformation.isFlightTypeValid(flightType.name()));
            Assert.assertFalse("testing of CheckingInputInformation.IsFlightTypeValid failed ",
                    CheckingInputInformation.isFlightTypeValid(flightType.name()+i++));
        }
    }
    @Test
    public void testIsTimeValid(){
        Assert.assertTrue("testing of CheckingInputInformation.isTimeValid failed 1",CheckingInputInformation.isTimeValid("21.12.2016 at 07:00"));
        Assert.assertFalse("testing of CheckingInputInformation.isTimeValid failed 2",CheckingInputInformation.isTimeValid("AAS98"));
        Assert.assertFalse("testing of CheckingInputInformation.isTimeValid failed 3",CheckingInputInformation.isTimeValid("21.12.1748 at 12:12"));
        Assert.assertFalse("testing of CheckingInputInformation.isTimeValid failed 4",CheckingInputInformation.isTimeValid("41.12.2016 at 20:00"));
        Assert.assertFalse("testing of CheckingInputInformation.isTimeValid failed 5",CheckingInputInformation.isTimeValid("11.11.2016 at 28:88")) ;
    }
    @Test
    public void testIsCityValid(){
        Assert.assertTrue("testing of CheckingInputInformation.isCityValid failed 1",CheckingInputInformation.isCityValid("Bucharest"));
        Assert.assertFalse("testing of CheckingInputInformation.isCityValid failed 2",CheckingInputInformation.isCityValid("ukrainian"));
    }
    @Test
    public void testIsTerminalValid(){
        Assert.assertTrue("testing of CheckingInputInformation.isTerminalValid failed 1",CheckingInputInformation.isTerminalValid("A"));
        Assert.assertFalse("testing of CheckingInputInformation.isTerminalValid failed 2",CheckingInputInformation.isTerminalValid("Z"));
    }
    @Test
    public void testIsStatusValid(){
        char i = 0;
        for(FlightStatus status : FlightStatus.values()){
            Assert.assertTrue("testing of CheckingInputInformation.isStatusValid failed "+status.name(),
                    CheckingInputInformation.isStatusValid(status.name()));
            Assert.assertFalse("testing of CheckingInputInformation.isStatusValid failed ",
                    CheckingInputInformation.isStatusValid(status.name()+i++));
        }
    }
    @Test
    public void testIsGateValid(){
        Assert.assertTrue("testing of CheckingInputInformation.isGateValid failed 1",CheckingInputInformation.isGateValid("1"));
        Assert.assertFalse("testing of CheckingInputInformation.isGateValid failed 2",CheckingInputInformation.isGateValid("Z"));
        Assert.assertFalse("testing of CheckingInputInformation.isGateValid failed 2",CheckingInputInformation.isGateValid("18"));
    }
    @Test
    public void testIsPriceValid(){
        Assert.assertTrue("testing of CheckingInputInformation.isPriceValid failed 1",CheckingInputInformation.isPriceValid("1.00"));
        Assert.assertFalse("testing of CheckingInputInformation.isPriceValid failed 2",CheckingInputInformation.isPriceValid("Z"));
        Assert.assertFalse("testing of CheckingInputInformation.isPriceValid failed 2",CheckingInputInformation.isPriceValid("18,12"));
    }
}
