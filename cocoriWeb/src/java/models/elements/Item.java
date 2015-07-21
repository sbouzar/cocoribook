
package models.elements;

import java.io.Serializable;
import models.beans.beanProduct;

public class Item implements Serializable{
    
    private int id;
    private String ref;
    private int qty;
    private beanProduct p;

    public Item(String ref, int qty, beanProduct p) {
        this.ref = ref;
        this.qty = qty;
    }

    public Item(int id, String ref, beanProduct p) {
        this.id = id;
        this.ref = ref;
        this.qty = 0;
    }

    public beanProduct getP() {
        return p;
    }

    public void setP(beanProduct p) {
        this.p = p;
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public void setQty(int qty){
        this.qty = qty;
    }
    
    public int getQty() {
        return qty;
    }
    
    public void delta(int qte){
        this.qty += qte;
    }
    
    
    
}
