package models.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import models.dao.ProductDAO;

public class beanCart implements Serializable {

    ArrayList<beanProduct> pList;
    float taxes;
    float discounts;
    beanProduct p;
    ProductDAO dao;
    float total;
    float subtotal;

    public beanCart() {
        this.pList = new ArrayList();
        this.dao = new ProductDAO();
    }

    public beanProduct getP(int id) throws SQLException {
        return dao.find(id);
    }

    public ArrayList<beanProduct> getpList() {
        return pList;
    }

    public void setpList(ArrayList<beanProduct> pList) {
        this.pList = pList;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public beanProduct getP() {
        return p;
    }

    public void setP(beanProduct p) {
        this.p = p;
    }

    public float getDiscounts() {
        return discounts;
    }

    public void setDiscounts(float discounts) {
        this.discounts = discounts;
    }

    public float getTaxes() {
        return taxes;
    }

    public void setTaxes(float taxes) {
        this.taxes = taxes;
    }

    public ProductDAO getDao() {
        return dao;
    }

    public void setDao(ProductDAO dao) {
        this.dao = dao;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public void addP(beanProduct p) {
        boolean exists = false;
        for (beanProduct bp : pList) {
            if (bp.getId() == p.getId()) {
                bp.delta(1);
                exists = true;
                break;
            }
        }
        if (!exists) {
            pList.add(p);
        }

    }

    public void remove(beanProduct bp){
        pList.remove(bp);
        bp.delta(bp.getQuantity());
    }

    
    public int nbItem() {
        if(pList.isEmpty()){
            return 0;
        }else{
            return pList.size();
        }
    }
    
    public float subtotal(float total, float tax, float discount){
        subtotal = 0;
        subtotal = total-tax+discount;
        subtotal = round(subtotal, 2);
        return subtotal;
    }

    public float total(float t) {
        total += t;
        total = round(total, 2);
        return total;
    }
    public float decTotal(float t) {
        total -= t;
        total = round(total, 2);
        return total;
    }

    public float taxes(float tax) {
        taxes += tax;
        taxes = round(taxes, 2);
        return taxes;
    }
    
    public float discounts(float discount) {
        discounts += discount;
        discounts = round(discounts, 2);
        return discounts;
    }
    
    
    public float decTaxes(float tax){
        taxes -= tax;
        taxes = round(taxes, 2);
        return taxes;
    }
    
    public float decDiscounts(float discount){
        discounts -= discount;
        discounts = round(discounts, 2);
        return discounts;
    }

    public float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public beanProduct get(int id) {
        for(beanProduct bp : pList){
            if(bp.getId() == id){
                return bp;
                
            }
        }
        return null;
    }


}
