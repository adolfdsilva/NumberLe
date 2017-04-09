package audi.com.numberle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import audi.com.numberle.entity.Appointment;
import audi.com.numberle.entity.AppointmentUser;
import audi.com.numberle.entity.Shop;
import audi.com.numberle.utils.Constants;
import br.com.mauker.materialsearchview.MaterialSearchView;
import br.com.mauker.materialsearchview.db.HistoryContract;

public class HomeActivity extends BaseActivity {


    private DrawerLayout mDrawerLayout;
    private MaterialSearchView searchView;
    private List<Object> shops = new ArrayList<>();
    private Calendar now, tomorrow;
    private List<Appointment> today = new ArrayList<>();
    private List<Appointment> past = new ArrayList<>();
    private List<Appointment> upcomming = new ArrayList<>();
    private Adapter fragmentAdapter;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        setUpUserAppointments();


        Query query = mDatabase.getRef().child(Constants.SHOP);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot shopSnapshot : dataSnapshot.getChildren()) {
                    Shop shop = shopSnapshot.getValue(Shop.class);
                    shops.add(shop);
                }
                searchView.clearAll();
                searchView.addSuggestions(shops);
                Constants.debug(shops.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Constants.debug(databaseError.getMessage());

            }
        });

        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String query = (String) parent.getAdapter().getItem(position);
                Constants.debug("Submitted Query: " + query);
                Intent intent = new Intent(getApplicationContext(), ShopDetailActivity.class);
                intent.putExtra(HistoryContract.HistoryEntry.COLUMN_JSON_OBJECT, query);
                startActivity(intent);
                searchView.closeSearch();
            }
        });
    }

    private void setUpUserAppointments() {
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
                        if (date.compareTo(now) < 0) {
                            past.add(appointment);
                        } else if (date.compareTo(tomorrow) > 0) {
                            upcomming.add(appointment);
                        } else {
                            today.add(appointment);
                        }
                    }
                }

                setupViewPager();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        now = Calendar.getInstance();
        tomorrow = Calendar.getInstance();
        tomorrow.set(Calendar.HOUR_OF_DAY, 23);
        tomorrow.set(Calendar.MINUTE, 59);
        tomorrow.set(Calendar.SECOND, 59);

        searchView = (MaterialSearchView) findViewById(R.id.searchView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.openSearch();
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupViewPager() {

        fragmentAdapter = new Adapter(getSupportFragmentManager());

        fragmentAdapter.addFragment(new TodayFragment(today), "Today");
        fragmentAdapter.addFragment(new UpcomingFragment(upcomming), "Upcoming");
        fragmentAdapter.addFragment(new PastFragment(past), "Past");

        viewPager.setAdapter(fragmentAdapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (searchView.isOpen()) {
            // Close the search on the back button press.
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    private static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
