package audi.com.numberle;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import audi.com.numberle.adapter.ProductAdapter;
import audi.com.numberle.entity.Shop;
import audi.com.numberle.utils.Constants;
import br.com.mauker.materialsearchview.db.HistoryContract;

/**
 * Created by Audi on 14/03/17.
 */

public class ShopDetailActivity extends BaseActivity {

    private Shop shop;
    private RecyclerView rvProducts;
    private ProductAdapter productAdapter;
    private List<Shop.Product> userProducts;
    private MenuItem cartItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);

        init();

        productAdapter.setCallback(new ProductAdapter.OnProductChange() {
            @Override
            public void onProductAdded(Shop.Product product) {
                userProducts.add(product);
                ActionItemBadge.update(ShopDetailActivity.this, cartItem, FontAwesome.Icon.faw_shopping_cart, ActionItemBadge.BadgeStyles.YELLOW, userProducts.size());
            }

            @Override
            public void onProductRemoved(Shop.Product product) {
                userProducts.remove(product);
                ActionItemBadge.update(ShopDetailActivity.this, cartItem, FontAwesome.Icon.faw_shopping_cart, ActionItemBadge.BadgeStyles.YELLOW, userProducts.size());
            }
        });

        findViewById(R.id.bDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("Users").child(user.getUid()).child(shop.getName()).setValue(userProducts);
            }
        });
    }

    private void init() {
        shop = new Gson().fromJson(getIntent().getStringExtra(HistoryContract.HistoryEntry.COLUMN_JSON_OBJECT), Shop.class);
        userProducts = new ArrayList<>();

        rvProducts = (RecyclerView) findViewById(R.id.rvShopProducts);
        rvProducts.setLayoutManager(new LinearLayoutManager(rvProducts.getContext()));
        productAdapter = new ProductAdapter(this, shop.getProducts());
        rvProducts.setAdapter(productAdapter);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(shop.getName());
        loadBackdrop();
        loadProducts();

    }

    private void loadProducts() {
        Query query = mDatabase.getRef().child("ShopProduct").child(shop.getName());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Shop.Product product = productSnapshot.getValue(Shop.Product.class);
                    shop.getProducts().add(product);
                }
                Constants.debug(shop.getProducts().toString());
                productAdapter.setmValues(shop.getProducts());
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Constants.debug(databaseError.getMessage());

            }
        });
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Picasso.with(this).load(shop.getBanner()).into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.layout_menu_cart, menu);
        cartItem = menu.findItem(R.id.mCart);
        return true;
    }
}
