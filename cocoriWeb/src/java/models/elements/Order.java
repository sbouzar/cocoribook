package models.elements;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Order implements Serializable {

    private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    private int id;
    private int orderNb;
    private Date orderDate;
    private String sOrderDate;
    private Status status;
    private Customer customer;
    private DeliveryAddress deliveryAddress;
    private Product product;
    private int quantityOrdered;
    private int quantityDelivered;
    private ArrayList<Delivery> delivery;
    private ArrayList<Order> orders;
    private ArrayList<OrderLine> orderLines;

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderNb() {
        return orderNb;
    }

    public void setOrderNb(int orderNb) {
        this.orderNb = orderNb;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
        this.setsOrderDate(formatter.format(orderDate));
    }

    public String getStatus() {
        return status.getName();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantityOrdered() {
        return quantityOrdered;
    }

    public void setQuantityOrdered(int quantityOrdered) {
        this.quantityOrdered = quantityOrdered;
    }

    public int getQuantityDelivered() {
        return quantityDelivered;
    }

    public void setQuantityDelivered(int quantityDelivered) {
        this.quantityDelivered = quantityDelivered;
    }

    public ArrayList<Delivery> getDelivery() {
        return delivery;
    }

    public void setDelivery(ArrayList<Delivery> delivery) {
        this.delivery = delivery;
    }

    public String getsOrderDate() {
        return sOrderDate;
    }

    public void setsOrderDate(String sOrderDate) {
        this.sOrderDate = sOrderDate;
    }

    public ArrayList<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(ArrayList<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }
    
    
}
