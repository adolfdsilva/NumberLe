package audi.com.numberle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import audi.com.numberle.adapter.SlotsAdapter;
import audi.com.numberle.entity.AppointmentUser;
import audi.com.numberle.entity.Shop;
import audi.com.numberle.utils.Constants;
import audi.com.numberle.utils.SlotCalculator;

/**
 * Created by Audi on 26/03/17.
 */

public class PickSlotActivity extends BaseActivity implements SlotCalculator.SlotCallback {

    private SlotsAdapter adapter;
    private Shop shop;
    private int ETA;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_slot);
        init();

        Bundle bundle = getIntent().getBundleExtra(Constants.SLOT_EXTRAS);

        shop = bundle.getParcelable(Shop.class.getSimpleName());
        ETA = bundle.getInt("ETA");

        SlotCalculator slotCalculator = new SlotCalculator(shop, ETA, PickSlotActivity.this, mDatabase);
        slotCalculator.getSlots();

    }

    private void init() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView rvSlots = (RecyclerView) findViewById(R.id.rvSlots);
        rvSlots.setLayoutManager(new LinearLayoutManager(rvSlots.getContext()));

        adapter = new SlotsAdapter(this, new ArrayList<String>());
        rvSlots.setAdapter(adapter);

        adapter.setCallback(new SlotsAdapter.OnSlot() {
            @Override
            public void bookSlot(String slot) {
                mDatabase.child(Constants.APPOINTMENTS).child(shop.getName());
                AppointmentUser user = new AppointmentUser();
                user.setETA(ETA);
                user.setSlot(slot);
                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                mDatabase.child(Constants.APPOINTMENTS).child(shop.getName()).child(userID);
                mDatabase.child(Constants.APPOINTMENTS).child(shop.getName()).child(userID).setValue(user);
            }
        });
    }

    @Override
    public void gotSlots(List<String> slots) {
        adapter.setmValues(slots);
        adapter.notifyDataSetChanged();
    }
}
