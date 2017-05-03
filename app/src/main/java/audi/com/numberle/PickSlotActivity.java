package audi.com.numberle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import audi.com.numberle.adapter.SlotsAdapter;
import audi.com.numberle.entity.AppointmentUser;
import audi.com.numberle.entity.Shop;
import audi.com.numberle.utils.Constants;
import audi.com.numberle.utils.SlotCalculator;

/**
 * Created by Audi on 26/03/17.
 */

public class PickSlotActivity extends BaseActivity implements SlotCalculator.SlotCallback, DatePickerDialog.OnDateSetListener {

    private SlotsAdapter adapter;
    private Shop shop;
    private int ETA;
    private Calendar date;
    private MenuItem mDate;
    private SlotCalculator slotCalculator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_slot);
        init();

        Bundle bundle = getIntent().getBundleExtra(Constants.SLOT_EXTRAS);

        shop = bundle.getParcelable(Shop.class.getSimpleName());
        ETA = bundle.getInt("ETA");

        slotCalculator = new SlotCalculator(shop, ETA, date, PickSlotActivity.this, mDatabase);
        slotCalculator.getSlots();
    }

    private void init() {
        date = Calendar.getInstance();
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
                DateFormat format = new SimpleDateFormat("HH:mm");
                Date date1 = null;
                try {
                    date1 = format.parse(slot.split("-")[0]);
                } catch (ParseException e) {
                    Constants.error("Error parsing slots");
                    return;
                }

                int mins = (date1.getHours() * 60 - date1.getMinutes()) - (date.get(Calendar.HOUR_OF_DAY) * 60 + date.get(Calendar.MINUTE));
                date.add(Calendar.MINUTE, mins);

                AppointmentUser user = new AppointmentUser();
                user.setETA(ETA);
                user.setSlot(slot);
                user.setDate(date.getTimeInMillis());
                user.setShopLogo(shop.getLogo());
                mDatabase.child(Constants.APPOINTMENTS).child(shop.getName()).push().setValue(user);
                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                Constants.debug(userID);
                mDatabase.child(Constants.USERS).child(userID).child(shop.getName()).push().setValue(user);

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    @Override
    public void gotSlots(List<String> slots, boolean isClosed) {
        if (!isClosed) {
            adapter.setmValues(slots);
            adapter.notifyDataSetChanged();
        } else {
            //shop closed change date or book next day date
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.layout_pick_slot, menu);
        mDate = menu.findItem(R.id.mDate);
        mDate.setTitle(date.get(Calendar.DAY_OF_MONTH) + "");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mDate) {
            Calendar now = Calendar.getInstance();
            now.setTime(date.getTime());
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    PickSlotActivity.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.setThemeDark(true);
            dpd.show(getFragmentManager(), "DatePickerDialog");

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        date.set(year, monthOfYear, dayOfMonth);
        mDate.setTitle(dayOfMonth + "");
        slotCalculator.setDate(date);
        slotCalculator.getSlots();
    }
}
