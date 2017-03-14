package audi.com.numberle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import audi.com.numberle.adapter.ShopsAdapter;

/**
 * Created by Audi on 13/03/17.
 */

public class PastFragment extends BaseFragment {

    private RecyclerView rvShops;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rvShops = (RecyclerView) inflater.inflate(R.layout.recycler_view_shops, container, false);
        setupRecyclerView(rvShops);
        return rvShops;
    }

    private void setupRecyclerView(RecyclerView rvShops) {
        rvShops.setLayoutManager(new LinearLayoutManager(rvShops.getContext()));
        rvShops.setAdapter(new ShopsAdapter(getActivity(),
                Arrays.asList("Jawed Habib", "Pravin's Barber Shop")));
    }
}
