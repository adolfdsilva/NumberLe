package audi.com.numberle.utils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Audi on 27/03/17.
 */


    /*
    * This should be done at server side
    */

public class SlotCalculator {

    private static DatabaseReference mDatabase;
    private static SlotCallback slotCallback;
    private static List<String> slots = new ArrayList<>();
    private static List<AppointmentUser> appointments = new ArrayList<>();


    static {
        mDatabase = FirebaseDatabase.getInstance().getReference();
//        AppointmentUser user = new AppointmentUser();
//        user.ETA = 90;
//        user.slot = "12:30";
//        AppointmentUser user2 = new AppointmentUser();
//        user2.ETA = 30;
//        user2.slot = "15:30";
//        appointments.add(user);
//        appointments.add(user2);
    }

    public void setSlotCallback(SlotCallback slotCallback) {
        SlotCalculator.slotCallback = slotCallback;
    }

    static void getSlots(String shopName, double eta) {
        Query query = mDatabase.child("Appointments").child(shopName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    AppointmentUser appointmentUser = user.getValue(AppointmentUser.class);
                    appointments.add(appointmentUser);
                }
                if (slotCallback != null)
                    slotCallback.gotSlots(null);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    static void setSlots(String workingHours, int ETA) throws ParseException {
        String hours[] = workingHours.split("-");
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date Date1 = format.parse(hours[0]);
        Date Date2 = format.parse(hours[1]);
        long mills = Date2.getTime() - Date1.getTime();
        long Mins = mills / (1000 * 60);

        long parts = Mins / ETA;
        Calendar cal = Calendar.getInstance();
        cal.setTime(Date1);
        int j = 0;
        boolean flag = true;
        AppointmentUser appointmentUser = null;
        for (int i = 0; i < parts ; i++) {
            //Calculate shop booked hours
            if (flag && appointments.size() > j) {
                appointmentUser = appointments.get(j++);
                flag = false;
            }
            Date appointDate1 = format.parse(appointmentUser.slot);
            Calendar appointDate2 = Calendar.getInstance();
            appointDate2.setTime(appointDate1);
            appointDate2.add(Calendar.MINUTE, appointmentUser.ETA);


            cal.add(Calendar.MINUTE, ETA);
            //check if the slot lies between already booked slots
            while (cal.getTime().getTime() > appointDate1.getTime() && cal.getTime().getTime() <= appointDate2.getTime().getTime()) {
                //if so then goto next slot
                cal.add(Calendar.MINUTE, ETA);
                flag = true;
            }
            String toTime = format.format(cal.getTime());
            Calendar tempCal = Calendar.getInstance();
            tempCal.setTime(cal.getTime());
            tempCal.add(Calendar.MINUTE,-ETA);
            String fromTime = format.format(tempCal.getTime());

            if (cal.getTime().getTime() > Date2.getTime())
                break;

            slots.add(fromTime + "-" + toTime);
        }

        System.out.print(slots.toString());
    }

    public static void main(String args[]) {
        try {
            setSlots("8:00-14:30", 30);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    interface SlotCallback {
        void gotSlots(List<String> slots);
    }

    private static class AppointmentUser {
        int ETA;
        String slot;
    }
}
