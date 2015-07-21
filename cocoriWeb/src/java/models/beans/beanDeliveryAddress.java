
package models.beans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import models.dao.DeliveryAddressDAO;
import models.elements.DeliveryAddress;

public class beanDeliveryAddress implements Serializable {
    
    private DeliveryAddressDAO dao;
    private ArrayList<DeliveryAddress> daList;
    private int active;
    
    public beanDeliveryAddress (){
        dao = new DeliveryAddressDAO();
        daList = new ArrayList();
    }

    public DeliveryAddressDAO getDao() {
        return dao;
    }

    public void setDao(DeliveryAddressDAO dao) {
        this.dao = dao;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public ArrayList<DeliveryAddress> getDaList(int id) throws SQLException {
        return dao.listAddresses(id);
    }

    public void setDaList(ArrayList<DeliveryAddress> daList) {
        this.daList = daList;
    }
    
    public DeliveryAddress find(int id) throws SQLException {
        return dao.find(id);
    }
    
    public boolean create(DeliveryAddress da, boolean byDefault, int idCustomer) throws SQLException {
        return dao.create(da, byDefault, idCustomer);
    }
    
    public boolean update(DeliveryAddress da, boolean byDefault, int idCustomer) throws SQLException {
        return dao.update(da, byDefault, idCustomer);
    }
    
    public boolean delete(DeliveryAddress da, int idCustomer) throws SQLException {
        return dao.delete(da, idCustomer);
    }
    
    public boolean update(DeliveryAddress oldActive, DeliveryAddress newActive) throws SQLException {
        return dao.update(oldActive, newActive);
    }
    
    
    
}
