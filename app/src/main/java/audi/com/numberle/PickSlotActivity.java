package audi.com.numberle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import audi.com.numberle.adapter.SlotsAdapter;

/**
 * Created by Audi on 26/03/17.
 */

public class PickSlotActivity extends BaseActivity {

    private RecyclerView rvSlots;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_slot);
        init();


    }

    private void init() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvSlots = (RecyclerView) findViewById(R.id.rvSlots);
        rvSlots.setLayoutManager(new LinearLayoutManager(rvSlots.getContext()));

        SlotsAdapter adapter = new SlotsAdapter(this, null);
        rvSlots.setAdapter(adapter);
    }
}
