package audi.com.numberle.utils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import audi.com.numberle.entity.AppointmentUser;
import audi.com.numberle.entity.Shop;

/**
 * Created by Audi on 27/03/17.
 */


    /*
    * This should be done at server side
    */

public class SlotCalculator {


    private DatabaseReference mDatabase;
    private SlotCallback slotCallback;
    private Shop shop;
    private List<String> slots = new ArrayList<>();
    private HashMap<Date, List<AppointmentUser>> appointments = new HashMap<>();
    private int ETA;
    private Calendar date;

    public SlotCalculator(Shop shop, int ETA, Calendar date, SlotCallback slotCallback, DatabaseReference database) {
        this.shop = shop;
        this.ETA = ETA;
        this.date = date;
        this.mDatabase = database;
        this.slotCallback = slotCallback;
    }


    public void getSlots() {
        Query query = mDatabase.child(Constants.APPOINTMENTS).child(shop.getName());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    AppointmentUser appointmentUser = user.getValue(AppointmentUser.class);
                    Date date = new Date(appointmentUser.getDate());
                    List<AppointmentUser> appointmentUsersList = appointments.get(date);
                    if (appointmentUsersList == null)
                        appointmentUsersList = new ArrayList<>();
                    appointmentUsersList.add(appointmentUser);
                    appointments.put(date, appointmentUsersList);
                }
                try {
                    setSlots();
                } catch (ParseException e) {
                    Constants.exception("Error Parsing Date", e);
                }
                if (slotCallback != null)
                    slotCallback.gotSlots(slots, false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    private void setSlots() throws ParseException {
        List<AppointmentUser> appointmentUsers = appointments.get(date.getTimeInMillis());
        if (appointmentUsers == null)
            appointmentUsers = new ArrayList<>();

        String hours[] = shop.getOperation_hours().split("-");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date Date1 = format.parse(hours[0]);
        Date Date2 = format.parse(hours[1]);
        long mills = Date2.getTime() - Date1.getTime();
        long Mins = mills / (1000 * 60);

        long parts = Mins / ETA;
        Calendar cal = Calendar.getInstance();

        //check if shop is closed
        long nowMins = cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE);
        long closingMins = Date2.getHours() * 60 + Date2.getMinutes();
        if (nowMins > closingMins) {
            slotCallback.gotSlots(slots, true);
            return;
        }

        if (cal.get(Calendar.DAY_OF_MONTH) <= date.get(Calendar.DAY_OF_MONTH) && cal.get(Calendar.HOUR_OF_DAY) + 2 < Date1.getHours())
            cal.setTime(Date1);
        else
            cal.add(Calendar.HOUR_OF_DAY, 2);

        int j = 0;
        boolean flag = true;
        AppointmentUser appointmentUser = null;
        for (int i = 0; i < parts; i++) {
            //Calculate shop booked hours
            if (flag && appointmentUsers.size() > j) {
                appointmentUser = appointmentUsers.get(j++);
                flag = false;
            }
            Date appointDate1 = null;
            Calendar appointDate2 = null;
            if (appointmentUser != null) {

                appointDate1 = format.parse(appointmentUser.getSlot());
                appointDate2 = Calendar.getInstance();
                appointDate2.setTime(appointDate1);
                appointDate2.add(Calendar.MINUTE, appointmentUser.getETA());
            }

            cal.add(Calendar.MINUTE, ETA);

            //check if the slot lies between already booked slots
            while ((appointDate1 != null) && cal.getTime().getTime() > appointDate1.getTime() && cal.getTime().getTime() <= appointDate2.getTime().getTime()) {
                //if so then goto next slot
                cal.add(Calendar.MINUTE, ETA);
                flag = true;
            }
            String toTime = format.format(cal.getTime());
            Calendar tempCal = Calendar.getInstance();
            tempCal.setTime(cal.getTime());
            tempCal.add(Calendar.MINUTE, -ETA);
            String fromTime = format.format(tempCal.getTime());

            long currentTime = cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE);
            long closingTime = Date2.getHours() * 60 + Date2.getMinutes();
            if (currentTime > closingTime)
                break;

            slots.add(fromTime + "-" + toTime);
        }

        System.out.print(slots.toString());
    }
//
//    public static void main(String args[]) throws ParseException {
//        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
//        Date Date1 = format.parse("8:00");
//
//        Calendar cal = Calendar.getInstance();
//        System.out.println(cal.getTimeInMillis());
//        cal.setTime(Date1);
//
//        Calendar now = Calendar.getInstance();
//        now.set(Calendar.YEAR, cal.get(Calendar.YEAR));
//        now.set(Calendar.MONTH, cal.get(Calendar.MONTH));
//        now.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH));
//
//        System.out.println(cal.get(Calendar.HOUR_OF_DAY) + " " + now.get(Calendar.HOUR_OF_DAY));
//
//    }

    public interface SlotCallback {
        void gotSlots(List<String> slots, boolean isClosed);
    }

}
