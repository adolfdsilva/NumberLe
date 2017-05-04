package audi.com.numberle;

import android.content.Intent;
import android.text.format.DateUtils;
import android.widget.RemoteViewsService;

import com.google.android.gms.tasks.Tasks;
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
import java.util.concurrent.Semaphore;

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
        appointmentProvider = new AppointmentProvider(this, intent);
        return appointmentProvider;

    }
}
