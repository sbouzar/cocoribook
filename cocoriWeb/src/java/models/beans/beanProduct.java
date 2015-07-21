package models.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.sql.SQLException;
import java.util.Collection;
import models.dao.ProductDAO;
import models.elements.Author;
import models.elements.Product;

public class beanProduct implements Serializable {

    private ProductDAO dao;
    
    private int id;
    private String title;
    private String subtitle;
    private Collection<Author> authors;
    private String volume;
    private String isbn10;
    private String isbn13;
    private String pages;
    private String publicationDate;
    private String summary;
    private String image;
    private String language;
    private int inventory;
    private String format;
    private String weight;
    private float price;
    private float tax;
    private float discount;
    private float totalPrice;
    private int quantity;
    private float unitPrice;
                    
    public beanProduct() {
        this.dao = new ProductDAO();
        quantity = 0;
    }

    public void delta(int qte){
        this.quantity += qte;
        this.price += price;
        this.tax += tax;
        this.discount += discount;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public float getPrice() {
        return round(price, 2);
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTax() {
        return round(tax,2);
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public float getDiscount() {
        return round(discount,2);
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Collection<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Collection<Author> authors) {
        this.authors = authors;
    }

    
    public float getPriceAtDate(int idProduct, Date date) throws SQLException{
        return dao.getPriceAtDate(idProduct, date);
    }

    public float getTaxAtDate(int idProduct, Date date) throws SQLException{
        return dao.getTaxAtDate(idProduct, date);
    }

    public float getDiscountAtDate(int idProduct, Date date) throws SQLException{
        return dao.getDiscountAtDate(idProduct, date);
    }
    
    public float calcTax(){
        float taxAmount = (unitPrice / 100) * tax;
        taxAmount = round(taxAmount, 2);
        return taxAmount;
    }
    
    public float calcDiscount(){
        float discountAmount = (unitPrice / 100) * discount;
        discountAmount = round(discountAmount, 2);
        return discountAmount;
    }
    
    public float calcUnitPrice() {
        float total = 0;
        float taxAmount = (unitPrice / 100) * tax;
        float discountAmount = (unitPrice / 100) * discount;
        total = unitPrice + taxAmount - discountAmount;
        total = round(total, 2);
        return total;
        
    }
    
    public float calcTotalPrice() {
        return calcTotalPrice(price, tax, discount);
    }
    
    public float calcTotalPrice(float price, float tax, float discount) {
        float total = 0;
        float taxAmount = (price / 100) * tax;
        float discountAmount = (price / 100) * discount;
        total = price + taxAmount - discountAmount;
        total = round(total, 2);
        return total;
    }
    
    public float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
    
    public Collection<Author> initAuthors(int idProduct) throws SQLException {
        return dao.initAuthors(idProduct);
    }

    @Override
    public String toString() {
        return String.valueOf(totalPrice);
    }

    public Product find(int id) throws SQLException {
        Product product = dao.findProduct(id);
        product.setTotalPrice(calcTotalPrice(getPriceAtDate(id, new Date()), getTaxAtDate(id, new Date()), getDiscountAtDate(id, new Date())));
        return product;
    }
    
}
