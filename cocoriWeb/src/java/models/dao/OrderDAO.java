package models.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import models.elements.Order;
import models.elements.OrderLine;

public class OrderDAO extends DAO<Order> {

    @Override
    public boolean create(Order or) throws SQLException {
        String query = "INSERT INTO \"Order\" (idCustomer, idDeliveryAddress, status, orderDate, orderNb)"
                + " VALUES(?,?,?,GETDATE(),?)";
        PreparedStatement pstmt = connect.preparedState(query);
        pstmt.setInt(1, or.getCustomer().getId());
        pstmt.setInt(2, or.getDeliveryAddress().getId());
        pstmt.setInt(3, 1);
        pstmt.setInt(4, 1);
        pstmt.executeUpdate();
        pstmt.close();
        int lastIdOrder = getLastIdOrder();
        ArrayList<OrderLine> olList = new ArrayList();
        olList = or.getOrderLines();
        for (OrderLine ol : olList) {
            query = "INSERT INTO OrderLine (idOrder, idProduct, quantity)"
                    + " VALUES(?,?,?)";
            pstmt = connect.preparedState(query);
            pstmt.setInt(1, lastIdOrder);
            pstmt.setInt(2, ol.getProduct().getId());
            pstmt.setInt(3, ol.getQuantity());
            pstmt.executeUpdate();
            pstmt.close();
        }

        return true;
    }

    private int getLastIdOrder() throws SQLException {
        String query = "SELECT MAX(idOrder) AS idOrder FROM \"Order\"";
        int lastIdOrder = 0;
        ResultSet rs = connect.state().executeQuery(query);
        if (rs.next()) {
            lastIdOrder = Integer.valueOf(rs.getString("idOrder"));
        }
        rs.close();
        return lastIdOrder;
    }

    @Override
    public boolean delete(Order obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(Order obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order find(int id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
