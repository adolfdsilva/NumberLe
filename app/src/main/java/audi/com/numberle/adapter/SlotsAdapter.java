package audi.com.numberle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import audi.com.numberle.R;

/**
 * Created by Audi on 02/04/17.
 */

public class SlotsAdapter extends RecyclerView.Adapter<SlotsAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<String> mValues;
    private OnSlot callback;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView tvSlot;
        public final ImageButton bBook;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvSlot = (TextView) view.findViewById(R.id.tvSlot);
            bBook = (ImageButton) view.findViewById(R.id.bAdd);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvSlot.getText();
        }
    }

    public String getValueAt(int position) {
        return mValues.get(position);
    }

    public void setmValues(List<String> mValues) {
        this.mValues = mValues;
    }

    public SlotsAdapter(Context context, List<String> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_pick_slot, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String slot = mValues.get(position);
        holder.tvSlot.setText(slot);

        holder.bBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.bookSlot(slot);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setCallback(OnSlot callback) {
        this.callback = callback;
    }

    public interface OnSlot {
        void bookSlot(String slot);
    }
}
