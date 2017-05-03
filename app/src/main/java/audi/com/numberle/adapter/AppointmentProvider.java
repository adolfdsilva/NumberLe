package audi.com.numberle.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RemoteViews;
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

import audi.com.numberle.R;
import audi.com.numberle.entity.Appointment;
import audi.com.numberle.entity.AppointmentUser;
import audi.com.numberle.utils.Constants;

/**
 * Created by Audi on 20/04/17.
 */

public class AppointmentProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private Intent intent;
    private List<Appointment> today = new ArrayList<>();


    //For obtaining the activity's context and intent
    public AppointmentProvider(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    public void setToday(List<Appointment> today) {
        this.today = today;
    }

    @Override
    public void onCreate() {
//        setUpUserAppointments();
    }

    @Override
    public void onDataSetChanged() {
//        setUpUserAppointments();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return today.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Appointment appointment = today.get(i);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.layout_appointment_item);
        remoteViews.setViewVisibility(R.id.bCancel, View.GONE);
        remoteViews.setTextViewText(R.id.tvShopName, appointment.getShopName());
        remoteViews.setTextViewText(R.id.tvAppointTime, appointment.getAppointment().getSlot());
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }



}
