
package models.beans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import models.dao.OrderDAO;
import models.elements.Order;
import models.elements.OrderLine;

public class beanOrder implements Serializable {
    
    private OrderDAO dao;
    private ArrayList<OrderLine> olList;

    public beanOrder() {
        dao = new OrderDAO();
        olList = new ArrayList();
    }

    public boolean create(Order or) throws SQLException {
        return dao.create(or);
    }
    

    public OrderDAO getDao() {
        return dao;
    }

    public void setDao(OrderDAO dao) {
        this.dao = dao;
    }

    public ArrayList<OrderLine> getOlList() {
        return olList;
    }

    public void setOlList(ArrayList<OrderLine> olList) {
        this.olList = olList;
    }
    
    
    
    
    
}
