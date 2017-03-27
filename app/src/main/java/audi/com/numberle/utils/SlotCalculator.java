package audi.com.numberle.utils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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

    static {
        mDatabase = FirebaseDatabase.getInstance().getReference();
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
                }
                if (slotCallback != null)
                    slotCallback.gotSlots(null);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    interface SlotCallback {
        void gotSlots(List<String> slots);
    }

    private static class AppointmentUser {
        double ETA;
        String slot;
    }
}
