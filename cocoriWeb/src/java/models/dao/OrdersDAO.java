package models.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import models.beans.beanOrders;
import models.elements.Delivery;
import models.elements.DeliveryAddress;
import models.elements.Order;
import models.elements.Product;
import models.elements.Publisher;
import models.elements.Status;

public class OrdersDAO extends DAO<Order> {

    @Override
    public boolean create(Order obj) throws Exception {
        return false;
    }

    @Override
    public boolean delete(Order obj) throws Exception {
        return false;
    }

    @Override
    public boolean update(Order obj) throws Exception {
        return false;
    }

    @Override
    public Order find(int id) throws SQLException {
        String query = "SELECT o.idOrder, o.orderNb, o.status, o.orderDate, "
                + " da.lastname AS dLastname, da.firstname AS dFirstname, da.addressLine1 AS dAddressLine1, da.addressLine2 AS dAddressLine2, "
                + " da.zipcode AS dZipcode, da.city AS dCity, da.digicode AS dDigicode, da.phone AS dPhone"
                + " FROM \"Order\" AS o"
                + " JOIN Customer AS cu ON o.idCustomer = cu.idCustomer"
                + " JOIN \"User\" AS us ON cu.idUser = us.idUser"
                + " JOIN DeliveryAddress AS da ON o.idDeliveryAddress = da.idDeliveryAddress"
                + " WHERE o.idOrder = " + id;

        Order bo = new Order();

        ResultSet rs = connect.state().executeQuery(query);
        if (rs.next()) {
            DeliveryAddress da = new DeliveryAddress();

            int status = rs.getInt("status");

            bo.setId(rs.getInt("idOrder"));
            bo.setOrderNb(rs.getInt("orderNb"));
            bo.setStatus(Status.values()[status - 1]);
            bo.setOrderDate(rs.getDate("orderDate"));

            da.setLastName(rs.getString("dLastname"));
            da.setFirstName(rs.getString("dFirstname"));
            da.setAddressLine1(rs.getString("dAddressLine1"));
            da.setAddressLine2(rs.getString("dAddressLine2"));
            da.setZipcode(rs.getString("dZipcode"));
            da.setCity(rs.getString("dCity"));
            da.setDigicode(rs.getString("dDigicode"));
            da.setPhone(rs.getString("dPhone"));

            bo.setDeliveryAddress(da);
        }

        return bo;
    }

    public ArrayList<Order> findAll(int id) throws SQLException {

        String query = "SELECT o.idOrder, o.orderNb, o.status, o.orderDate, "
                + " da.lastname AS dLastname, da.firstname AS dFirstname, da.addressLine1 AS dAddressLine1, da.addressLine2 AS dAddressLine2, "
                + " da.zipcode AS dZipcode, da.city AS dCity, da.digicode AS dDigicode, da.phone AS dPhone"
                + " FROM \"Order\" AS o"
                + " JOIN Customer AS cu ON o.idCustomer = cu.idCustomer"
                + " JOIN \"User\" AS us ON cu.idUser = us.idUser"
                + " JOIN DeliveryAddress AS da ON o.idDeliveryAddress = da.idDeliveryAddress"
                + " WHERE cu.idCustomer = " + id
                + " ORDER BY o.status, o.orderDate DESC";

        ArrayList<Order> al = new ArrayList();

        ResultSet rs = connect.state().executeQuery(query);

        while (rs.next()) {
            Order bo = new Order();
            DeliveryAddress da = new DeliveryAddress();

            int status = rs.getInt("status");

            bo.setId(rs.getInt("idOrder"));
            bo.setOrderNb(rs.getInt("orderNb"));
            bo.setStatus(Status.values()[status - 1]);
            bo.setOrderDate(rs.getDate("orderDate"));

            da.setLastName(rs.getString("dLastname"));
            da.setFirstName(rs.getString("dFirstname"));
            da.setAddressLine1(rs.getString("dAddressLine1"));
            da.setAddressLine2(rs.getString("dAddressLine2"));
            da.setZipcode(rs.getString("dZipcode"));
            da.setCity(rs.getString("dCity"));
            bo.setDeliveryAddress(da);

            al.add(bo);
        }

        rs.close();
        connect.state().close();

        return al;
    }

    public ArrayList<Order> orderLine(int id) throws SQLException {
        ArrayList<Order> abo = new ArrayList();

        String query = "SELECT o.orderNb, ol.quantity, p.volume, p.format, p.idProduct, w.title, w.subtitle, w.type, pu.name"
                + " FROM OrderLine AS ol"
                + " JOIN \"Order\" AS o ON ol.idOrder = o.idOrder"
                + " JOIN Product AS p ON ol.idProduct = p.idProduct"
                + " JOIN Work AS w ON p.idWork = w.idWork"
                + " JOIN Publisher AS pu ON p.idPublisher = pu.idPublisher"
                + " WHERE ol.idOrder = " + id;

        ResultSet rs = connect.state().executeQuery(query);
        while (rs.next()) {
            Product p = new Product();
            Publisher publisher = new Publisher();
            Order bo = new Order();

            publisher.setName(rs.getString("name"));
            p.setTitle(rs.getString("title"));
            p.setSubtitle(rs.getString("subtitle"));
            p.setId(rs.getInt("idProduct"));
            p.setVolume(rs.getInt("volume"));
            p.setType(rs.getString("type"));
            p.setPublisher(publisher);

            bo.setQuantityOrdered(rs.getInt("quantity"));
            bo.setProduct(p);
            bo.setId(id);
            abo.add(bo);
        }

        rs.close();
        connect.state().close();

        for (Order bo : abo) {
            query = " SELECT d.idOrder, dp.idDeliveredProduct, dp.idDelivery, dp.deliveredQuantity, dp.idProduct"
                    + " FROM DeliveredProduct AS dp"
                    + " JOIN Product AS p"
                    + " ON dp.idProduct=p.idProduct"
                    + " JOIN Delivery AS d"
                    + " ON dp.idDelivery=d.idDelivery"
                    + " JOIN \"Order\" AS o"
                    + " ON d.idOrder=o.idOrder"
                    + " WHERE p.idProduct = " + bo.getProduct().getId()
                    + " AND o.idOrder = " + id;

            rs = connect.state().executeQuery(query);
            int totalQuantity = 0;
            int deliveredQuantity = 0;

            while (rs.next()) {
                deliveredQuantity = rs.getInt("deliveredQuantity");
                totalQuantity = totalQuantity + deliveredQuantity;

            }

            rs.close();
            connect.state().close();

            bo.setQuantityDelivered(totalQuantity);
        }

        return abo;
    }

    public ArrayList<Order> getDeliveryDetail(ArrayList<Order> abo) throws SQLException {

        for (Order bo : abo) {
            ArrayList<Delivery> ald = new ArrayList();

            String query = "SELECT d.receiptNb AS dReceiptNb, d.deliveryDate, d.idOrder, dp.idDeliveredProduct, dp.idDelivery, dp.deliveredQuantity, dp.idProduct"
                    + " FROM DeliveredProduct AS dp"
                    + " JOIN Product AS p"
                    + " ON dp.idProduct=p.idProduct"
                    + " JOIN Delivery AS d"
                    + " ON dp.idDelivery=d.idDelivery"
                    + " JOIN \"Order\" AS o"
                    + " ON d.idOrder=o.idOrder"
                    + " WHERE o.idOrder = " + bo.getId()
                    + " AND p.idProduct = " + bo.getProduct().getId();

            ResultSet rs = connect.state().executeQuery(query);

            while (rs.next()) {
                
                Delivery d = new Delivery();
                System.out.println(rs.getString("dReceiptNb"));
                d.setReceipt(rs.getInt("dReceiptNb"));
                d.setDeliveryDate(rs.getDate("deliveryDate"));
                d.setDeliveredQuantity(rs.getInt("deliveredQuantity"));

                ald.add(d);
            }
            bo.setDelivery(ald);

            rs.close();
            connect.state().close();
        }

        return abo;
    }

}
