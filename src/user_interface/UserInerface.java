package user_interface;

import business_logic.*;
import data_base.DBLoading;
import net.miginfocom.swing.MigLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.util.Calendar;

/**
 * Created by r2-d2 on 24.04.16.
 */
public class UserInerface implements Runnable {
    private static int quantityOfLoadingFlights = 0;
    private static int quantityOfLoadingPassengers = 0;
    private static int quantityOfLoadingTickets = 0;
    private static JButton flightsButton = new JButton("Flights Board");
    private static JButton ticketsButton = new JButton("Price list");
    private static JButton passengersButton = new JButton("Passengers");
    private static JFrame frame;
    private static JPanel panel;
    private static JPanel panelView;
    private static JPanel panelFlightsVew;
    private static JPanel panelTicketsView;
    private static JPanel panelPassengersView;

    public static JPanel getPanel() {
        return panel;
    }
    public static JPanel getPanelView() {
        return panelView;
    }
    public static void setPanelView(JPanel panelView) {
        UserInerface.panelView = panelView;
    }
    public static JPanel getPanelFlightsVew() {
        return panelFlightsVew;
    }
    public static JPanel getPanelPassengersView() {
        return panelPassengersView;
    }
    public static void setPanelPassengersView(JPanel panelPassengersView) {
        UserInerface.panelPassengersView = panelPassengersView;
    }
    public static JPanel getPanelTicketsView() {
        return panelTicketsView;
    }
    public static void setPanelTicketsView(JPanel panelTicketsView) {
        UserInerface.panelTicketsView = panelTicketsView;
    }
    public static void setPanelFlightsVew(JPanel panelFlightsVew) {
        UserInerface.panelFlightsVew = panelFlightsVew;
    }

    @Override
    public void run() {
        frame = new JFrame("Airport --Romchik&Co--");
        frame.setSize(1020,430);
        frame.setLocation(10,10);
        panel = new JPanel(new MigLayout());
        frame.add(panel);
        panel.add(flightsButton);
        panel.add(ticketsButton);
        panel.add(passengersButton,"wrap");
        try {
            panelFlightsVew = createPanelFlightsView();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        panelView = panelFlightsVew;
        panel.add(panelView, "span");

        passengersButton.addActionListener(new PassengersButtonActionListener());
        flightsButton.addActionListener(new FlightsButtonActionListener());
        ticketsButton.addActionListener(new TicketsButtonActionListener());

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static JPanel createPanelFlightsView() throws SQLException, ClassNotFoundException {
        if(quantityOfLoadingFlights < 1){
            DBLoading.loadFlights();
            quantityOfLoadingFlights++;
        }
        GUIObjectsMaker.loadingRowsForFlights();
        JPanel flightsVew = new JPanel(new MigLayout());
        JTable flightsArrivalTable = GUIObjectsMaker.createFlightsArrivalTable();
        JScrollPane scrollPaneForArrivalFlight = new JScrollPane(flightsArrivalTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        flightsVew.add(scrollPaneForArrivalFlight);

        JTable flightsDepartureTable = GUIObjectsMaker.createFlightsDepartureTable();
        JScrollPane scrollPaneForDepartureFlight = new JScrollPane(flightsDepartureTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        flightsVew.add(scrollPaneForDepartureFlight, "wrap");

        JComboBox comboBoxFlights = GUIObjectsMaker.createComboBoxFlights();

        JPanel selectAndDeletePanel = new JPanel();
        flightsVew.add(selectAndDeletePanel,"span, wrap");

        JTable selectedFlight = GUIObjectsMaker.createSelectedFlight(comboBoxFlights);
        JScrollPane scrollPaneForSelectedFlight = new JScrollPane(selectedFlight);
        selectAndDeletePanel.add(comboBoxFlights);
        JButton deleteFlightButton = new JButton("DELETE SELECTED FLIGHT");
        flightsVew.add(scrollPaneForSelectedFlight,"span,wrap");

        deleteFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DBLoading.deleteFlightFromDB(Main.getSelectedFlights().get(0));
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                Main.getFlights().remove(Main.getSelectedFlights().get(0));
                Main.getPassengers().removeAll(Main.getSelectedFlights().get(0).getPassengers());
                panelTicketsView = null;
                panelPassengersView = null;
                panel.remove(panelView);
                try {
                    panelFlightsVew = createPanelFlightsView();

                } catch (SQLException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                panelView = panelFlightsVew;
                panel.add(panelView,"span");
                updateFrame();
            }
        });

        comboBoxFlights.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable selected = GUIObjectsMaker.createSelectedFlight(comboBoxFlights);
                scrollPaneForSelectedFlight.setViewportView(selected);
                updateFrame();
            }
        });
        selectAndDeletePanel.add(deleteFlightButton);

        JPanel panelInputData = new JPanel(new MigLayout());
        TextField flightTypeTextField = new TextField();
        flightTypeTextField.setMinimumSize(new Dimension(80,16));
        panelInputData.add(flightTypeTextField);

        TextField timeTextField = new TextField();
        timeTextField.setMinimumSize(new Dimension(163,16));
        panelInputData.add(timeTextField);

        TextField flightNumberTextField = new TextField();
        flightNumberTextField.setMinimumSize(new Dimension(110,16));
        panelInputData.add(flightNumberTextField);

        TextField cityTextField = new TextField();
        cityTextField.setMinimumSize(new Dimension(77,16));
        panelInputData.add(cityTextField);

        TextField terminalTextField = new TextField();
        terminalTextField.setMinimumSize(new Dimension(70,16));
        panelInputData.add(terminalTextField);

        TextField statusTextField = new TextField();
        statusTextField.setMinimumSize(new Dimension(80,16));
        panelInputData.add(statusTextField);

        TextField gateTextField = new TextField();
        gateTextField.setMinimumSize(new Dimension(40,16));
        panelInputData.add(gateTextField);

        JButton insertFlightButton = new JButton("INSERT FLIGHT");
        insertFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String insertFlightType = flightTypeTextField.getText();
                String insertTime = timeTextField.getText();
                String insertFlightNumber = flightNumberTextField.getText();
                String insertCity = cityTextField.getText();
                String insertTerminal = terminalTextField.getText();
                String insertStatus = statusTextField.getText();
                String insertGate = gateTextField.getText();
                if(CheckingInputInformation.isFlightTypeValid(insertFlightType)
                        && CheckingInputInformation.isTimeValid(insertTime)
                        && CheckingInputInformation.isFlightNumberForInsertValid(insertFlightNumber)
                        && CheckingInputInformation.isCityValid(insertCity)
                        && CheckingInputInformation.isTerminalValid(insertTerminal)
                        && CheckingInputInformation.isStatusValid(insertStatus)
                        && CheckingInputInformation.isGateValid(insertGate)){
                    Flight flight = new Flight();
                    flight.setType(FlightType.valueOf(insertFlightType));
                    try {
                        flight.setTime(DBLoading.PRECISELLY_FORMAT.parse(insertTime));
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    flight.setFlightNumber(insertFlightNumber);
                    flight.setCity(insertCity);
                    flight.setTerminal(insertTerminal);
                    flight.setStatus(FlightStatus.valueOf(insertStatus));
                    flight.setGate(insertGate);

                    GUIObjectsMaker.insertFlightAndTickets(flight);
                } else {
                    GUIObjectsMaker.showWarningDataValidationMessage();
                }
            }
        });
        selectAndDeletePanel.add(insertFlightButton);

        JButton updateFlightButton = new JButton("UPDATE");
        updateFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String insertFlightType = flightTypeTextField.getText();
                String insertTime = timeTextField.getText();
                String insertFlightNumber = flightNumberTextField.getText();
                String insertCity = cityTextField.getText();
                String insertTerminal = terminalTextField.getText();
                String insertStatus = statusTextField.getText();
                String insertGate = gateTextField.getText();
                if(CheckingInputInformation.isFlightTypeValid(insertFlightType)
                        && CheckingInputInformation.isTimeValid(insertTime)
                        && CheckingInputInformation.isFlightNumberForUpdateValid(insertFlightNumber)
                        && CheckingInputInformation.isCityValid(insertCity)
                        && CheckingInputInformation.isTerminalValid(insertTerminal)
                        && CheckingInputInformation.isStatusValid(insertStatus)
                        && CheckingInputInformation.isGateValid(insertGate)){
                    if(quantityOfLoadingTickets < 1){
                        try {
                            DBLoading.loadTickets();
                            quantityOfLoadingTickets++;
                        } catch (ClassNotFoundException e1) {
                            e1.printStackTrace();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                    Flight flight = Main.getSelectedFlights().get(0);
                    try {
                        DBLoading.updateFlight(flight, insertFlightType,insertTime,insertFlightNumber,insertCity,insertTerminal,insertStatus,insertGate);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    flight.setType(FlightType.valueOf(insertFlightType));
                    try {
                        flight.setTime(DBLoading.PRECISELLY_FORMAT.parse(insertTime));
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    flight.setFlightNumber(insertFlightNumber);
                    flight.setCity(insertCity);
                    flight.setTerminal(insertTerminal);
                    flight.setStatus(FlightStatus.valueOf(insertStatus));
                    flight.setGate(insertGate);
                    panel.remove(panelView);
                    try {
                        panelFlightsVew = createPanelFlightsView();
                        panelView = panelFlightsVew;
                        panel.add(panelView, "span");
                        updateFrame();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    panelPassengersView = null;
                    panelTicketsView = null;
                } else {
                    GUIObjectsMaker.showWarningDataValidationMessage();
                }
            }
        });

        selectAndDeletePanel.add(updateFlightButton);

        flightsVew.add(panelInputData,"span");

        return flightsVew;
    }
    public static JPanel createPanelPassengerView() throws SQLException, ClassNotFoundException {
        if(quantityOfLoadingPassengers<1){
            DBLoading.loadPassengers();
            quantityOfLoadingPassengers++;
        }
        JPanel passengerView = new JPanel(new MigLayout());
        JComboBox comboBoxFlight = GUIObjectsMaker.createComboBoxFlights("all");
        JTable tablePassengers = GUIObjectsMaker.createSelectedPassengers(comboBoxFlight,"","","");
        JScrollPane scrollPaneforTablePassengers = new JScrollPane(tablePassengers);

        JPanel searchPassengersViewPanel = new JPanel(new MigLayout());
        searchPassengersViewPanel.add(comboBoxFlight);

        Dimension textFieldDimension = new Dimension(80,15);
        JTextArea firstNameTextArea = new JTextArea("First Name:");
        firstNameTextArea.setEditable(false);
        JTextField firstNameTextField = new JTextField();
        firstNameTextField.setMinimumSize(textFieldDimension);
        JTextArea lastNameTextArea = new JTextArea("Last Name:");
        lastNameTextArea.setEditable(false);
        JTextField lastNameTextField = new JTextField();
        lastNameTextField.setMinimumSize(textFieldDimension);
        JTextArea passportTextArea = new JTextArea("Passport:");
        passportTextArea.setEditable(false);
        JTextField passportTextField = new JTextField();
        passportTextField.setMinimumSize(textFieldDimension);
        JButton searshPassengersButton = new JButton("SEARCH");
        JButton deleteSelectedPassengersButton = new JButton("DELETE SELECTED PASSENGERS");

        searchPassengersViewPanel.add(firstNameTextArea);
        searchPassengersViewPanel.add(firstNameTextField);
        searchPassengersViewPanel.add(lastNameTextArea);
        searchPassengersViewPanel.add(lastNameTextField);
        searchPassengersViewPanel.add(passportTextArea);
        searchPassengersViewPanel.add(passportTextField);
        searchPassengersViewPanel.add(searshPassengersButton);
        searchPassengersViewPanel.add(deleteSelectedPassengersButton);

        passengerView.add(searchPassengersViewPanel,"span, wrap");
        passengerView.add(scrollPaneforTablePassengers,"wrap");
        comboBoxFlight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable selectedPassenger = GUIObjectsMaker.createSelectedPassengers(comboBoxFlight,firstNameTextField.getText(),lastNameTextField.getText(), passportTextField.getText());
                scrollPaneforTablePassengers.setViewportView(selectedPassenger);
                updateFrame();
            }
        });

        searshPassengersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable selectedPassenger = GUIObjectsMaker.createSelectedPassengers(comboBoxFlight, firstNameTextField.getText(),lastNameTextField.getText(), passportTextField.getText());
                scrollPaneforTablePassengers.setViewportView(selectedPassenger);
                updateFrame();
            }
        });

        deleteSelectedPassengersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DBLoading.deletePassengersFromDB(Main.getSelectedPassengers());
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                for(Flight flight:Main.getFlights()){
                        flight.getPassengers().removeAll(Main.getSelectedPassengers());
                    }
                Main.getPassengers().removeAll(Main.getSelectedPassengers());
                Main.getSelectedPassengers().clear();
                JTable selectedPassengers = GUIObjectsMaker.createSelectedPassengers(comboBoxFlight, firstNameTextField.getText(),lastNameTextField.getText(), passportTextField.getText());
                scrollPaneforTablePassengers.setViewportView(selectedPassengers);
                updateFrame();

            }
        });

        JPanel insertAndApdetePanel = new JPanel(new MigLayout());
        Dimension textUpdateDimension = new Dimension(115,15);

        TextField flightNumberUpdateTextField = new TextField();
        flightNumberUpdateTextField.setMinimumSize(textUpdateDimension);
        TextField firstNameUpdateTextField = new TextField();
        firstNameUpdateTextField.setMinimumSize(textUpdateDimension);
        TextField lastNameUpdateTextField = new TextField();
        lastNameUpdateTextField.setMinimumSize(textUpdateDimension);
        TextField nationalityUpdateTextField = new TextField();
        nationalityUpdateTextField.setMinimumSize(textUpdateDimension);
        TextField passportUpdateTextField = new TextField();
        passportUpdateTextField.setMinimumSize(textUpdateDimension);
        TextField birthdayUpdateTextField = new TextField();
        birthdayUpdateTextField.setMinimumSize(textUpdateDimension);
        TextField sexUpdateTextField = new TextField();
        sexUpdateTextField.setMinimumSize(textUpdateDimension);
        TextField ticketClassUpdateTextField = new TextField();
        ticketClassUpdateTextField.setMinimumSize(textUpdateDimension);

        insertAndApdetePanel.add(flightNumberUpdateTextField);
        insertAndApdetePanel.add(firstNameUpdateTextField);
        insertAndApdetePanel.add(lastNameUpdateTextField);
        insertAndApdetePanel.add(nationalityUpdateTextField);
        insertAndApdetePanel.add(passportUpdateTextField);
        insertAndApdetePanel.add(birthdayUpdateTextField);
        insertAndApdetePanel.add(sexUpdateTextField);
        insertAndApdetePanel.add(ticketClassUpdateTextField,"wrap");

        JButton insertButton = new JButton("INSERT");
        JButton updateButton = new JButton("UPDATE SELECTED");

        JPanel insertAndUpdateButtonsPanel = new JPanel(new MigLayout());

        insertAndUpdateButtonsPanel.add(insertButton);
        insertAndUpdateButtonsPanel.add(updateButton);

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String insertFlightNumber = flightNumberUpdateTextField.getText();
                String insertFirstName = firstNameUpdateTextField.getText();
                String insertLastName = lastNameUpdateTextField.getText();
                String insertNationality = nationalityUpdateTextField.getText();
                String insertPassport = passportUpdateTextField.getText();
                String insertBirthday = birthdayUpdateTextField.getText();
                String insertSex = sexUpdateTextField.getText();
                String insertTicketClass = ticketClassUpdateTextField.getText();
                if(CheckingInputInformation.isFlightNumberForPassengersValid(insertFlightNumber)
                        && CheckingInputInformation.isNameValid(insertFirstName)
                        && CheckingInputInformation.isNameValid(insertLastName)
                        && CheckingInputInformation.isNationalityValid(insertNationality)
                        && CheckingInputInformation.isPassportForInsertValid(insertPassport)
                        && CheckingInputInformation.isBirthdayValid(insertBirthday)
                        && CheckingInputInformation.isSexValid(insertSex)
                        && CheckingInputInformation.isTicketClassValid(insertTicketClass)){
                    Passenger passenger = new Passenger();
                    passenger.setFirstName(insertFirstName);
                    passenger.setLastName(insertLastName);
                    passenger.setNationality(insertNationality);
                    passenger.setPassport(insertPassport);
                    Calendar birthday = Calendar.getInstance();
                    try {
                        birthday.setTime(DBLoading.FORMAT.parse(insertBirthday));
                        passenger.setBirthday(birthday);
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    passenger.setSex(Sex.valueOf(insertSex));
                    passenger.setTicketClass(TicketClass.valueOf(insertTicketClass));
                    Main.getPassengers().add(passenger);
                    for(Flight flight:Main.getFlights()){
                        if(flight.getFlightNumber().equals(insertFlightNumber)){
                            flight.getPassengers().add(passenger);
                            break;
                        }
                    }
                    try {
                        DBLoading.insertPassenger(passenger,insertFlightNumber);
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    scrollPaneforTablePassengers.setViewportView(GUIObjectsMaker.createSelectedPassengers(comboBoxFlight,firstNameTextField.getText(),lastNameTextField.getText(), passportTextField.getText()));
                    updateFrame();
                    GUIObjectsMaker.showSuccessInsertMessage();
                } else{
                    GUIObjectsMaker.showWarningDataValidationMessage();
                }

            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String insertFlightNumber = flightNumberUpdateTextField.getText();
                String insertFirstName = firstNameUpdateTextField.getText();
                String insertLastName = lastNameUpdateTextField.getText();
                String insertNationality = nationalityUpdateTextField.getText();
                String insertPassport = passportUpdateTextField.getText();
                String insertBirthday = birthdayUpdateTextField.getText();
                String insertSex = sexUpdateTextField.getText();
                String insertTicketClass = ticketClassUpdateTextField.getText();
                if(CheckingInputInformation.isFlightNumberForPassengersValid(insertFlightNumber)
                        && CheckingInputInformation.isNameValid(insertFirstName)
                        && CheckingInputInformation.isNameValid(insertLastName)
                        && CheckingInputInformation.isNationalityValid(insertNationality)
                        && CheckingInputInformation.isPassportForUpdateValid(insertPassport)
                        && CheckingInputInformation.isBirthdayValid(insertBirthday)
                        && CheckingInputInformation.isSexValid(insertSex)
                        && CheckingInputInformation.isTicketClassValid(insertTicketClass)){
                    if(Main.getSelectedPassengers().size()==1){
                        Passenger passenger = Main.getSelectedPassengers().get(0);
                        try {
                            DBLoading.updatePassenger(passenger,insertFirstName,insertLastName,insertNationality,insertPassport,insertBirthday,insertSex,insertTicketClass,insertFlightNumber);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        } catch (ClassNotFoundException e1) {
                            e1.printStackTrace();
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                        passenger.setFirstName(insertFirstName);
                        passenger.setLastName(insertLastName);
                        passenger.setNationality(insertNationality);
                        passenger.setPassport(insertPassport);
                        Calendar birthday = Calendar.getInstance();
                        try {
                            birthday.setTime(DBLoading.FORMAT.parse(insertBirthday));
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                        passenger.setBirthday(birthday);
                        passenger.setSex(Sex.valueOf(insertSex));
                        passenger.setTicketClass(TicketClass.valueOf(insertTicketClass));
                        for(Flight flight : Main.getFlights()){
                            if(flight.getPassengers().contains(passenger)){
                                flight.getPassengers().remove(passenger);
                            }
                            if(insertFlightNumber.equals(flight.getFlightNumber())){
                                flight.getPassengers().add(passenger);
                            }
                        }
                        scrollPaneforTablePassengers.setViewportView(GUIObjectsMaker.createSelectedPassengers(comboBoxFlight,firstNameTextField.getText(),lastNameTextField.getText(), passportTextField.getText()));
                        updateFrame();
                    } else {
                        GUIObjectsMaker.showWarningSelectionMessage();
                    }
                } else {
                    GUIObjectsMaker.showWarningDataValidationMessage();
                }
            }
        });

        insertAndApdetePanel.add(insertAndUpdateButtonsPanel,"span");

        passengerView.add(insertAndApdetePanel);

        return passengerView;
    }
    public static JPanel createPanelTicketsView() throws SQLException, ClassNotFoundException {
        if(quantityOfLoadingTickets < 1){
            DBLoading.loadTickets();
            quantityOfLoadingTickets++;
        }
        JPanel ticketsPanelView = new JPanel(new MigLayout());
        JComboBox comboBoxFlight = GUIObjectsMaker.createComboBoxFlights("all");
        JComboBox comboBoxTicketClass = GUIObjectsMaker.createComboBoxTicketClass();
        ticketsPanelView.add(comboBoxFlight);
        ticketsPanelView.add(comboBoxTicketClass);
        JTable ticketsTable = GUIObjectsMaker.createSelectedTickets(comboBoxFlight,comboBoxTicketClass);
        JScrollPane tickets = new JScrollPane(ticketsTable);
        ticketsPanelView.add(comboBoxFlight);
        ticketsPanelView.add(comboBoxTicketClass);
        JTextArea priceTextArea = new JTextArea("Price: ");
        priceTextArea.setEditable(false);
        ticketsPanelView.add(priceTextArea);
        JTextField priceTextField = new JTextField();
        priceTextField.setMinimumSize(new Dimension(90,16));
        ticketsPanelView.add(priceTextField);
        JButton updatePriceButton = new JButton("UPDATE PRICE");
        updatePriceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String updatePrice = priceTextField.getText();
                if(Main.getSelectedTickets().size() == 1) {
                    if (CheckingInputInformation.isPriceValid(updatePrice)) {
                        double price = Double.parseDouble(updatePrice);
                        Ticket ticket = Main.getSelectedTickets().get(0);
                        try {
                            DBLoading.updateTicket(ticket,price);
                            ticket.setPrice(price);
                        } catch (ClassNotFoundException e1) {
                            e1.printStackTrace();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        tickets.setViewportView(GUIObjectsMaker.createSelectedTickets(comboBoxFlight,comboBoxTicketClass));
                        updateFrame();
                    } else {
                        GUIObjectsMaker.showWarningDataValidationMessage();
                    }
                } else {
                    GUIObjectsMaker.showWarningSelectionMessage();
                }
            }
        });
        ticketsPanelView.add(updatePriceButton,"wrap");
        ticketsPanelView.add(tickets,"span");
        comboBoxFlight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable selectedTicketsTable = GUIObjectsMaker.createSelectedTickets(comboBoxFlight,comboBoxTicketClass);
                tickets.setViewportView(selectedTicketsTable);
                updateFrame();
            }
        });
        comboBoxTicketClass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable selectedTicketsTable = GUIObjectsMaker.createSelectedTickets(comboBoxFlight,comboBoxTicketClass);
                tickets.setViewportView(selectedTicketsTable);
                updateFrame();
            }
        });

        return  ticketsPanelView;
    }
    public static void updateFrame(){
        frame.setVisible(false);
        frame.setVisible(true);
    }
}
