
package models.elements;

import models.beans.beanProduct;

public class OrderLine {
    
    private beanProduct product;
    private int quantity;

    public OrderLine() {
    }

    public OrderLine(beanProduct product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public beanProduct getProduct() {
        return product;
    }

    public void setProduct(beanProduct product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
