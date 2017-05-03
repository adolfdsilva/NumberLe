package audi.com.numberle.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import audi.com.numberle.R;
import audi.com.numberle.ShopDetailActivity;

/**
 * Created by Audi on 14/03/17.
 */

public class ShopsAdapter extends RecyclerView.Adapter<ShopsAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<String> mValues;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public String mBoundString;

        public final View mView;
        public final ImageView ivShopLogo;
        public final TextView tvShopName;
        public final TextView tvShopDesc;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ivShopLogo = (ImageView) view.findViewById(R.id.ivShopLogo);
            tvShopName = (TextView) view.findViewById(R.id.tvShopName);
            tvShopDesc = (TextView) view.findViewById(R.id.tvShopDesc);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvShopName.getText();
        }
    }

    public String getValueAt(int position) {
        return mValues.get(position);
    }

    public ShopsAdapter(Context context, List<String> items) {
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

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, ShopDetailActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}