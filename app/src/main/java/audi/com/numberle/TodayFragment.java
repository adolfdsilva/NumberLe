package audi.com.numberle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import audi.com.numberle.adapter.AppointmentAdapter;
import audi.com.numberle.entity.Appointment;

/**
 * Created by Audi on 13/03/17.
 */

public class TodayFragment extends BaseFragment {

    private RecyclerView rvShops;
    private List<Appointment> today;

    public TodayFragment(List<Appointment> today) {
        this.today = today;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rvShops = (RecyclerView) inflater.inflate(R.layout.recycler_view_shops, container, false);
        setupRecyclerView(rvShops);
        return rvShops;
    }

    private void setupRecyclerView(RecyclerView rvShops) {
        rvShops.setLayoutManager(new LinearLayoutManager(rvShops.getContext()));
        rvShops.setAdapter(new AppointmentAdapter(getActivity(), today, AppointmentAdapter.TODAY));
    }
}
