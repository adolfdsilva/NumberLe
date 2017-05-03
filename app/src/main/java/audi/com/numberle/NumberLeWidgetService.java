package audi.com.numberle;

import android.content.Intent;
import android.widget.RemoteViewsService;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import audi.com.numberle.adapter.AppointmentProvider;
import audi.com.numberle.entity.Appointment;
import audi.com.numberle.entity.AppointmentUser;
import audi.com.numberle.utils.Constants;

/**
 * Created by Audi on 20/04/17.
 */

public class NumberLeWidgetService extends RemoteViewsService {
    AppointmentProvider appointmentProvider;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        setUpUserAppointments();
        appointmentProvider = new AppointmentProvider(this, intent);
        return appointmentProvider;

    }

    private void setUpUserAppointments() {
        final List<Appointment> today = new ArrayList<>();
        Constants.debug("setUpUserAppointments");
        final Calendar now = Calendar.getInstance();
        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null)
            return;
        Query userQuery = mDatabase.getRef().child(Constants.USERS).child(user.getUid());
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot shopAppointments : dataSnapshot.getChildren()) {
                    String shopName = shopAppointments.getKey();
                    Appointment appointment = new Appointment();
                    appointment.setShopName(shopName);
                    for (DataSnapshot appointSnapShot : shopAppointments.getChildren()) {
                        AppointmentUser appointmentUser = appointSnapShot.getValue(AppointmentUser.class);
                        appointment.setAppointment(appointmentUser);
                        Calendar date = Calendar.getInstance();
                        date.setTimeInMillis(appointmentUser.getDate());
                        if (date.compareTo(now) == 0) {
                            today.add(appointment);
                        }
                    }
                }
                Constants.debug(today.toString());
                appointmentProvider.setToday(today);
                appointmentProvider.onDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
