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
 * Created by Audi on 20/03/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<String> mValues;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public String mBoundString;

        public final View mView;
        public final TextView tvShopName;
        public final TextView tvShopDesc;
        public final ImageButton bAdd;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvShopName = (TextView) view.findViewById(R.id.tvShopName);
            tvShopDesc = (TextView) view.findViewById(R.id.tvShopDesc);
            bAdd = (ImageButton) view.findViewById(R.id.bAdd);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvShopName.getText();
        }
    }

    public String getValueAt(int position) {
        return mValues.get(position);
    }

    public ProductAdapter(Context context, List<String> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_shops_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mBoundString = mValues.get(position);
        holder.tvShopName.setText(mValues.get(position));

        holder.bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
