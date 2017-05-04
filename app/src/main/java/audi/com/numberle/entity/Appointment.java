package audi.com.numberle.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Audi on 07/04/17.
 */

public class Appointment implements Parcelable {
    private String shopName;
    private AppointmentUser appointment;

    public Appointment() {

    }

    protected Appointment(Parcel in) {
        shopName = in.readString();
    }

    public static final Creator<Appointment> CREATOR = new Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel in) {
            return new Appointment(in);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shopName);
    }
}
