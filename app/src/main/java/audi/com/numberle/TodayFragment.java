package audi.com.numberle;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import audi.com.numberle.adapter.AppointmentAdapter;
import audi.com.numberle.entity.Appointment;
import audi.com.numberle.utils.Constants;

/**
 * Created by Audi on 13/03/17.
 */

public class TodayFragment extends BaseFragment {

    private RecyclerView rvShops;
    private List<Appointment> today;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        today = getArguments().getParcelableArrayList(Constants.APPOINTMENTS);
        rvShops = (RecyclerView) inflater.inflate(R.layout.recycler_view_shops, container, false);
        setupRecyclerView(rvShops);
        return rvShops;
    }

    private void setupRecyclerView(RecyclerView rvShops) {
        final AppointmentAdapter adapter = new AppointmentAdapter(getActivity(), today, AppointmentAdapter.TODAY);
        rvShops.setLayoutManager(new LinearLayoutManager(rvShops.getContext()));
        rvShops.setAdapter(adapter);
        adapter.setButtonCallback(new AppointmentAdapter.ListButton() {
            @Override
            public void onCancel(Appointment appointment) {
                mDatabase.child(Constants.APPOINTMENTS).child(appointment.getShopName())
                        .child(appointment.getAppointment().getKey()).getRef().removeValue();
                mDatabase.child(Constants.USERS).child(user.getUid()).child(appointment.getShopName())
                        .child(appointment.getAppointment().getKey()).getRef().removeValue();
                today.remove(appointment);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
