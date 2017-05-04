package audi.com.numberle.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Audi on 25/03/17.
 */

public class Shop implements Parcelable{
    private String name;
    private String desc;
    private String location;
    private String operation_hours;
    private List<Product> products ;
    private String banner;
    private String logo;

    {
        products = new ArrayList<>();
    }

    public Shop(){

    }

    protected Shop(Parcel in) {
        name = in.readString();
        desc = in.readString();
        location = in.readString();
        operation_hours = in.readString();
        banner = in.readString();
        logo = in.readString();
    }

    public static final Creator<Shop> CREATOR = new Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel in) {
            return new Shop(in);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOperation_hours() {
        return operation_hours;
    }

    public void setOperation_hours(String operation_hours) {
        this.operation_hours = operation_hours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    @Override
    public String toString() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(desc);
        dest.writeString(location);
        dest.writeString(operation_hours);
        dest.writeString(banner);
        dest.writeString(logo);
    }

    public static class Product {

        private String productName;
        private double price;
        private int ETA;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getETA() {
            return ETA;
        }

        public void setETA(int ETA) {
            this.ETA = ETA;
        }

        @Override
        public String toString() {
            return productName;
        }
    }
}
