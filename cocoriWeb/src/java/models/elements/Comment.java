package models.elements;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment implements Serializable {

    private int id;
    private String comment;
    private String commentDate;
    private Customer customer;
    private Product product;
    private Byte rating;
    private boolean active;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Byte getRating() {
        return rating;
    }

    public void setRating(Byte rating) {
        this.rating = rating;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = formatter.format(commentDate);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    

}
