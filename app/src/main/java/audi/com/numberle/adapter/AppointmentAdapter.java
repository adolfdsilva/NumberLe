package audi.com.numberle.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import audi.com.numberle.R;
import audi.com.numberle.entity.Appointment;

/**
 * Created by Audi on 08/04/17.
 */

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<Appointment> mValues;
    private int day;

    // Define the list of accepted constants and declare the NavigationMode annotation
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TODAY, UPCOMING, PAST})
    public @interface DAYS {
    }

    // Declare the constants
    public static final int TODAY = 0;
    public static final int UPCOMING = 1;
    public static final int PAST = 2;


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final ImageView ivShopLogo;
        public final TextView tvShopName;
        public final TextView tvAppTime;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ivShopLogo = (ImageView) view.findViewById(R.id.ivShopLogo);
            tvShopName = (TextView) view.findViewById(R.id.tvShopName);
            tvAppTime = (TextView) view.findViewById(R.id.tvAppointTime);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvShopName.getText();
        }
    }

    public Appointment getValueAt(int position) {
        return mValues.get(position);
    }

    public AppointmentAdapter(Context context, List<Appointment> items, @DAYS int day) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;
        this.day = day;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_appointment_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Appointment appointment = mValues.get(position);
        holder.tvShopName.setText(appointment.getShopName());

        if (day == TODAY)
            holder.tvAppTime.setText(appointment.getAppointment().getSlot());
        else {
            DateFormat format = new SimpleDateFormat("dd MMM");
            String date = format.format(new Date(appointment.getAppointment().getDate()));
            holder.tvAppTime.setText(date + "   " + appointment.getAppointment().getSlot());
        }

//        Glide.with(holder.ivShopLogo.getContext())
//                .load(Cheeses.getRandomCheeseDrawable())
//                .fitCenter()
//                .into(holder.ivShopLogo);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}