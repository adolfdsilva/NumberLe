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
import audi.com.numberle.entity.Shop;

/**
 * Created by Audi on 20/03/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private final TypedValue mTypedValue = new TypedValue();
    private int mBackground;
    private List<Shop.Product> mValues;
    private OnProductChange callback;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView tvProductName;
        public final TextView tvProductETAPrice;
        public final ImageButton bAdd;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvProductName = (TextView) view.findViewById(R.id.tvProductName);
            tvProductETAPrice = (TextView) view.findViewById(R.id.tvProductETAPrice);
            bAdd = (ImageButton) view.findViewById(R.id.bAdd);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvProductName.getText();
        }
    }

    public Shop.Product getValueAt(int position) {
        return mValues.get(position);
    }

    public void setmValues(List<Shop.Product> mValues) {
        this.mValues = mValues;
    }

    public ProductAdapter(Context context, List<Shop.Product> items) {
        context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
        mBackground = mTypedValue.resourceId;
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_product_item, parent, false);
        view.setBackgroundResource(mBackground);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Shop.Product product = mValues.get(position);
        holder.tvProductName.setText(product.getProductName());
        holder.tvProductETAPrice.setText(product.getPrice() + "\t" + product.getETA());

        holder.bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onProductAdded(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setCallback(OnProductChange callback) {
        this.callback = callback;
    }

    public interface OnProductChange {
        void onProductAdded(Shop.Product product);
        void onProductRemoved(Shop.Product product);
    }
}
