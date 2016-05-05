package business_logic;

import java.util.Calendar;

/**
 * Created by r2-d2 on 23.04.16.
 */
public class Passenger {
    private String firstName;
    private String lastName;
    private String nationality;
    private String passport;
    private Calendar birthday;
    private Sex sex;
    private TicketClass ticketClass;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passenger)) return false;

        Passenger passenger = (Passenger) o;

        if (firstName != null ? !firstName.equals(passenger.firstName) : passenger.firstName != null) return false;
        if (lastName != null ? !lastName.equals(passenger.lastName) : passenger.lastName != null) return false;
        if (nationality != null ? !nationality.equals(passenger.nationality) : passenger.nationality != null)
            return false;
        if (passport != null ? !passport.equals(passenger.passport) : passenger.passport != null) return false;
        if (birthday != null ? !birthday.equals(passenger.birthday) : passenger.birthday != null) return false;
        if (sex != passenger.sex) return false;
        return ticketClass == passenger.ticketClass;

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (nationality != null ? nationality.hashCode() : 0);
        result = 31 * result + (passport != null ? passport.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (ticketClass != null ? ticketClass.hashCode() : 0);
        return result;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(Calendar birthday) {
        this.birthday = birthday;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public TicketClass getTicketClass() {
        return ticketClass;
    }

    public void setTicketClass(TicketClass ticketClass) {
        this.ticketClass = ticketClass;
    }
}
