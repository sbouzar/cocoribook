package models.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import models.elements.DeliveryAddress;

public class DeliveryAddressDAO extends DAO<DeliveryAddress> {

    public boolean create(DeliveryAddress da, boolean byDefault, int idCustomer) throws SQLException {
        String query = "INSERT INTO DeliveryAddress (lastName, firstName, addressLine1,"
                + " addressLine2, zipcode, city, digicode, phone, active)"
                + " VALUES (?,?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = connect.preparedState(query);
        pstmt.setString(1, da.getLastName());
        pstmt.setString(2, da.getFirstName());
        pstmt.setString(3, da.getAddressLine1());
        pstmt.setString(4, da.getAddressLine2());
        pstmt.setString(5, da.getZipcode());
        pstmt.setString(6, da.getCity());
        pstmt.setString(7, da.getDigicode());
        pstmt.setString(8, da.getPhone());

        pstmt.setInt(9, 1);

        pstmt.executeUpdate();
        pstmt.close();
        int idDeliveryAddress = getLastIdDeliveryAddress();

        query = "INSERT INTO CustomerAddress (idDeliveryAddress, idCustomer, active)"
                + " VALUES (?,?,?)";
        pstmt = connect.preparedState(query);
        pstmt.setInt(1, idDeliveryAddress);
        pstmt.setInt(2, idCustomer);
        if (byDefault == true) {
            pstmt.setInt(3, 1);
        } else {
            pstmt.setInt(3, 0);
        }
        pstmt.executeUpdate();
        pstmt.close();

        if (byDefault == true) {
            query = "UPDATE CustomerAddress"
                    + " SET active=0 WHERE idCustomer=? AND idDeliveryAddress!=?";
            pstmt = connect.preparedState(query);
            pstmt.setInt(1, idCustomer);
            pstmt.setInt(2, idDeliveryAddress);
            pstmt.executeUpdate();
            pstmt.close();
            query = "UPDATE CustomerAddress"
                    + " SET active=1 WHERE idCustomer=? AND idDeliveryAddress=?";
            pstmt = connect.preparedState(query);
            pstmt.setInt(1, idCustomer);
            pstmt.setInt(2, idDeliveryAddress);
            pstmt.executeUpdate();
            pstmt.close();
        }

        return true;
    }

    public int getLastIdDeliveryAddress() throws SQLException {
        String query = "SELECT MAX(idDeliveryAddress) AS idDeliveryAddress FROM DeliveryAddress";
        int lastIdDeliveryAddress = 0;
        ResultSet rs = connect.state().executeQuery(query);
        if (rs.next()) {
            lastIdDeliveryAddress = Integer.valueOf(rs.getString("idDeliveryAddress"));
        }
        rs.close();
        return lastIdDeliveryAddress;
    }

    public boolean delete(DeliveryAddress da, int idCustomer) throws SQLException {
        String query = "UPDATE DeliveryAddress SET active=0"
                + " WHERE idDeliveryAddress = ?";
        PreparedStatement pstmt = connect.preparedState(query);
        pstmt.setInt(1, da.getId());
        pstmt.executeUpdate();
        pstmt.close();
        query = "UPDATE CustomerAddress"
                + " SET active=0 WHERE idCustomer=? AND idDeliveryAddress=?";
        pstmt = connect.preparedState(query);
        pstmt.setInt(1, idCustomer);
        pstmt.setInt(2, da.getId());
        pstmt.executeUpdate();
        pstmt.close();
        if (da.getActive() == 1) {
            int getNewIdDa = 0;
            query = "SELECT TOP 1(idDeliveryAddress)"
                    + " FROM CustomerAddress"
                    + " WHERE idCustomer=" + idCustomer
                    + " ORDER BY idDeliveryAddress ASC ";
            ResultSet rs = connect.state().executeQuery(query);
            if (rs.next()) {
                getNewIdDa = rs.getInt("idDeliveryAddress");
            }
            rs.close();
            connect.state().close();
            // update set active 1 a l id recupéré
            query = "UPDATE CustomerAddress"
                    + " SET active=1 WHERE idCustomer=? AND idDeliveryAddress=?";
            pstmt = connect.preparedState(query);
            pstmt.setInt(1, idCustomer);
            pstmt.setInt(2, getNewIdDa);
            pstmt.executeUpdate();
            pstmt.close();
        }

        return true;
    }

    public boolean update(DeliveryAddress oldActive, DeliveryAddress newActive) throws SQLException {
        String query = "UPDATE CustomerAddress SET active = ?"
                + " WHERE idDeliveryAddress=?";
        PreparedStatement pstmt = connect.preparedState(query);
        pstmt.setInt(1, 1);
        pstmt.setInt(2, newActive.getId());
        pstmt.executeUpdate();
        pstmt.close();

        query = "UPDATE CustomerAddress SET active=?"
                + " WHERE idDeliveryAddress=?";
        pstmt = connect.preparedState(query);
        pstmt.setInt(1, 0);
        pstmt.setInt(2, oldActive.getId());
        pstmt.executeUpdate();
        pstmt.close();

        return true;
    }

    @Override
    public DeliveryAddress find(int id) throws SQLException {
        DeliveryAddress da = new DeliveryAddress();
        String query = "SELECT da.idDeliveryAddress, da.lastName, da.firstName,"
                + " da.addressLine1, da.addressLine2, da.digicode, da.phone,"
                + " da.zipcode, da.city"
                + " FROM DeliveryAddress AS da"
                + " JOIN CustomerAddress as ca"
                + " ON da.idDeliveryAddress=ca.idDeliveryAddress"
                + " WHERE ca.idCustomer=" + id
                + " AND ca.active=1"
                + " AND da.active=1";

        ResultSet rs = connect.state().executeQuery(query);
        if (rs.next()) {
            da.setId(rs.getInt("idDeliveryAddress"));
            da.setAddressLine1(rs.getString("addressLine1"));
            da.setAddressLine2(rs.getString("addressLine2"));
            da.setLastName(rs.getString("lastName"));
            da.setFirstName(rs.getString("firstName"));
            da.setZipcode(rs.getString("zipcode"));
            da.setCity(rs.getString("city"));
            da.setDigicode(rs.getString("digicode"));
            da.setPhone(rs.getString("phone"));
            da.setActive(1);
        }
        rs.close();
        connect.state().close();

        return da;
    }

    public ArrayList<DeliveryAddress> listAddresses(int id) throws SQLException {
        ArrayList<DeliveryAddress> addresses = new ArrayList();
        DeliveryAddress da = new DeliveryAddress();
        String query = "SELECT da.idDeliveryAddress, da.lastName, da.firstName,"
                + " da.addressLine1, da.addressLine2, da.digicode, da.phone,"
                + " da.zipcode, da.city, ca.active"
                + " FROM DeliveryAddress AS da"
                + " JOIN CustomerAddress as ca"
                + " ON da.idDeliveryAddress=ca.idDeliveryAddress"
                + " WHERE ca.idCustomer=" + id
                + " AND da.active=1";

        ResultSet rs = connect.state().executeQuery(query);
        while (rs.next()) {
            da.setId(rs.getInt("idDeliveryAddress"));
            da.setAddressLine1(rs.getString("addressLine1"));
            da.setAddressLine2(rs.getString("addressLine2"));
            da.setLastName(rs.getString("lastName"));
            da.setFirstName(rs.getString("firstName"));
            da.setZipcode(rs.getString("zipcode"));
            da.setCity(rs.getString("city"));
            da.setDigicode(rs.getString("digicode"));
            da.setPhone(rs.getString("phone"));
            da.setActive(rs.getInt("active"));
            addresses.add(da);
            da = new DeliveryAddress();
        }
        rs.close();
        connect.state().close();

        return addresses;
    }

    @Override
    public boolean create(DeliveryAddress obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean update(DeliveryAddress da, boolean byDefault, int idCustomer) throws SQLException {
        String query = "UPDATE DeliveryAddress SET"
                + " lastName=?,"
                + " firstName=?,"
                + " addressLine1=?,"
                + " addressLine2=?,"
                + " zipcode=?,"
                + " city=?,"
                + " digicode=?,"
                + " phone=?,"
                + " active=?"
                + " WHERE idDeliveryAddress=?";

        PreparedStatement pstmt = connect.preparedState(query);
        pstmt.setString(1, da.getLastName());
        pstmt.setString(2, da.getFirstName());
        pstmt.setString(3, da.getAddressLine1());
        pstmt.setString(4, da.getAddressLine2());
        pstmt.setString(5, da.getZipcode());
        pstmt.setString(6, da.getCity());
        pstmt.setString(7, da.getDigicode());
        pstmt.setString(8, da.getPhone());
        pstmt.setInt(9, 1);
        pstmt.setInt(10, da.getId());

        pstmt.executeUpdate();
        pstmt.close();

        if (byDefault == true) {
            query = "UPDATE CustomerAddress"
                    + " SET active=0 WHERE idCustomer=? AND idDeliveryAddress!=?";
            pstmt = connect.preparedState(query);
            pstmt.setInt(1, idCustomer);
            pstmt.setInt(2, da.getId());
            pstmt.executeUpdate();
            pstmt.close();
            query = "UPDATE CustomerAddress"
                    + " SET active=1 WHERE idCustomer=? AND idDeliveryAddress=?";
            pstmt = connect.preparedState(query);
            pstmt.setInt(1, idCustomer);
            pstmt.setInt(2, da.getId());
            pstmt.executeUpdate();
            pstmt.close();
        } else {
            query = "UPDATE CustomerAddress"
                    + " SET active=0 WHERE idCustomer=? AND idDeliveryAddress=?";
            pstmt = connect.preparedState(query);
            pstmt.setInt(1, idCustomer);
            pstmt.setInt(2, da.getId());
            pstmt.executeUpdate();
            pstmt.close();
            int getNewIdDa = 0;
            query = "SELECT TOP 1(idDeliveryAddress)"
                    + " FROM CustomerAddress"
                    + " WHERE idCustomer=" + idCustomer
                    + " ORDER BY idDeliveryAddress ASC ";
            ResultSet rs = connect.state().executeQuery(query);
            if (rs.next()) {
                getNewIdDa = rs.getInt("idDeliveryAddress");
            }
            rs.close();
            connect.state().close();
            // update set active 1 a l id recupéré
            query = "UPDATE CustomerAddress"
                    + " SET active=1 WHERE idCustomer=? AND idDeliveryAddress=?";
            pstmt = connect.preparedState(query);
            pstmt.setInt(1, idCustomer);
            pstmt.setInt(2, getNewIdDa);
            pstmt.executeUpdate();
            pstmt.close();
        }

        return true;
    }

    @Override
    public boolean update(DeliveryAddress obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(DeliveryAddress obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
