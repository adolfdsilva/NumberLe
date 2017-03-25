package audi.com.numberle.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Audi on 25/03/17.
 */

public class Shop {
    private String name;
    private String desc;
    private String location;
    private String operation_hours;
    private String banner;
    private List<Product> products ;

    {
        products = new ArrayList<>();
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

    public static class Product {

        private String productName;
        private double price;
        private double ETA;

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

        public double getETA() {
            return ETA;
        }

        public void setETA(double ETA) {
            this.ETA = ETA;
        }

        @Override
        public String toString() {
            return productName;
        }
    }
}
