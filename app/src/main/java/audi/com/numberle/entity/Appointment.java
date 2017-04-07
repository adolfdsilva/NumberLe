package audi.com.numberle.entity;

/**
 * Created by Audi on 07/04/17.
 */

public class Appointment {
    private String shopName;
    private Shop.Product product;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Shop.Product getProduct() {
        return product;
    }

    public void setProduct(Shop.Product product) {
        this.product = product;
    }
}
