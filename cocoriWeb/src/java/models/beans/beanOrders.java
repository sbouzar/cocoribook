
package models.beans;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import models.dao.OrdersDAO;
import models.elements.Customer;
import models.elements.Delivery;
import models.elements.DeliveryAddress;
import models.elements.Order;
import models.elements.Product;
import models.elements.Status;

public class beanOrders implements Serializable {
    private OrdersDAO dao;


    public beanOrders() {
        this.dao = new OrdersDAO();
    }
    
    public Order find(int id) throws Exception {
        return dao.find(id);
    }
    
    public ArrayList<Order> findAll(int id) throws Exception {
        return dao.findAll(id);
    }
    
    public ArrayList<Order> orderLine(int id) throws SQLException {
        return dao.orderLine(id);
    }
    
    public ArrayList<Order> getDeliveryDetail(ArrayList<Order> abo) throws SQLException{
        return dao.getDeliveryDetail(abo);
    }
    
}
