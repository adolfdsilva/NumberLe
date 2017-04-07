package audi.com.numberle.entity;

/**
 * Created by Audi on 03/04/17.
 */

public class AppointmentUser {
    private int ETA;
    private String slot;
    private long date;

    public int getETA() {
        return ETA;
    }

    public void setETA(int ETA) {
        this.ETA = ETA;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
