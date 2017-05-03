package audi.com.numberle.entity;

/**
 * Created by Audi on 07/04/17.
 */

public class Appointment {
    private String shopName;
    private AppointmentUser appointment;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }


    public AppointmentUser getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentUser appointment) {
        this.appointment = appointment;
    }
}
