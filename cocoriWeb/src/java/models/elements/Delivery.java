
package models.elements;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Delivery {
    private int id;
    private int receipt;
    private Date deliveryDate;
    private int deliveredQuantity;
    private Product product;
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    private String sDeliveryDate;

    public Delivery() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReceipt() {
        return receipt;
    }

    public void setReceipt(int receipt) {
        this.receipt = receipt;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
        this.setsDeliveryDate(formatter.format(deliveryDate));
    }

    public int getDeliveredQuantity() {
        return deliveredQuantity;
    }

    public void setDeliveredQuantity(int deliveredQuantity) {
        this.deliveredQuantity = deliveredQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }



    public String getsDeliveryDate() {
        return sDeliveryDate;
    }

    public void setsDeliveryDate(String sDeliveryDate) {
        this.sDeliveryDate = sDeliveryDate;
    }
    
}
