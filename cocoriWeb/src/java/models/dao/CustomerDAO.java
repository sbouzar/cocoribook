package models.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import models.elements.Encryption;
import models.beans.beanCustomer;
import models.elements.Customer;

public class CustomerDAO extends DAO<Customer> {

    @Override
    public boolean create(Customer obj) throws SQLException {
        java.sql.Date bDate = new java.sql.Date(obj.getbDate().getTime());
        String encryptPass = null;
        try {
            encryptPass = Encryption.encrypt(obj.getPassword());
        } catch (Exception ex) {
            // Exception, propager dès la classe DAO ou traiter ici?
        }

        String crUser = "INSERT INTO \"User\"(pseudo, email, password, isAdmin, active)"
                + " VALUES(?,?,?,?,?) ";

        PreparedStatement pstmt = connect.preparedState(crUser);
        pstmt.setString(1, obj.getPseudo());
        pstmt.setString(2, obj.getEmail());
        pstmt.setString(3, encryptPass);
        pstmt.setBoolean(4, false);
        pstmt.setBoolean(5, true);
        pstmt.executeUpdate();
        pstmt.close();

        int idUser = 0;
        String user = "SELECT TOP 1 idUser FROM \"User\""
                + " ORDER BY idUser DESC";
        ResultSet rs = connect.state().executeQuery(user);
        if (rs.next()) {
            idUser = rs.getInt("idUser");
        }
        rs.close();

        String crCustomer = "INSERT INTO Customer(idUser, lastName, firstName, address, zipcode, city, phone, bDate, active)"
                + " VALUES(?,?,?,?,?,?,?,?,?) ";
        pstmt = connect.preparedState(crCustomer);
        pstmt.setInt(1, idUser);
        pstmt.setString(2, obj.getLastName());
        pstmt.setString(3, obj.getFirstName());
        pstmt.setString(4, obj.getAddress());
        pstmt.setString(5, obj.getZipcode());
        pstmt.setString(6, obj.getCity());
        pstmt.setString(7, obj.getPhone());
        pstmt.setDate(8, bDate);
        pstmt.setBoolean(9, true);
        pstmt.executeUpdate();
        pstmt.close();
        
        return true;
    }

    @Override
    public boolean delete(Customer obj) {
        return false;
    }

    @Override
    public boolean update(Customer obj) throws SQLException {
        String encryptPass = null;
        try {
            encryptPass = Encryption.encrypt(obj.getPassword());
        } catch (Exception ex) {
            // Exception, propager dès la classe DAO ou traiter ici?
        }

        String upCustomer = "UPDATE Customer SET lastName = ?, firstName = ?, \"address\" = ?, zipcode = ?,"
                + " city = ?, phone = ?, bDate = ?"
                + " WHERE idCustomer=" + obj.getId();

        java.sql.Date bDate = new java.sql.Date(obj.getbDate().getTime());

        PreparedStatement pstmt = connect.preparedState(upCustomer);
        pstmt.setString(1, obj.getLastName());
        pstmt.setString(2, obj.getFirstName());
        pstmt.setString(3, obj.getAddress());
        pstmt.setString(4, obj.getZipcode());
        pstmt.setString(5, obj.getCity());
        pstmt.setString(6, obj.getPhone());
        pstmt.setDate(7, bDate);
        pstmt.executeUpdate();
        pstmt.close();

        String upUser = "UPDATE \"User\" SET pseudo = ?, email = ?, password = ? "
                + "WHERE idUser=" + obj.getIdUser();

        pstmt = connect.preparedState(upUser);
        pstmt.setString(1, obj.getPseudo());
        pstmt.setString(2, obj.getEmail());
        pstmt.setString(3, encryptPass);
        pstmt.executeUpdate();
        pstmt.close();

        return true;
    }

    @Override
    public Customer find(int id) throws Exception, SQLException {
        Customer customer = new Customer();

        String query = "SELECT u.pseudo, u.email, u.\"password\", c.idCustomer, c.idUser, "
                + " c.lastName, c.firstName, c.\"address\", c.zipcode,"
                + " c.city, c.phone, c.bDate, c.active"
                + " FROM Customer AS c"
                + " JOIN \"User\" AS u"
                + " ON c.idUser=u.idUser"
                + " WHERE c.idCustomer=" + id;

        ResultSet rs = connect.state().executeQuery(query);
        if (rs.next()) {
            customer.setPseudo(rs.getString("pseudo"));
            customer.setEmail(rs.getString("email"));

            String decryptPass = null;
            try {
                decryptPass = Encryption.decrypt(rs.getString("password"));
            } catch (Exception ex) {
                // Exception, propager dès la classe DAO ou traiter ici?
            }
            customer.setPassword(decryptPass);
            customer.setId(id);
            customer.setIdUser(rs.getInt("idUser"));
            customer.setLastName(rs.getString("lastName"));
            customer.setFirstName(rs.getString("firstName"));
            customer.setAddress(rs.getString("address"));
            customer.setZipcode(rs.getString("zipcode"));
            customer.setCity(rs.getString("city"));
            customer.setPhone(rs.getString("phone"));
            customer.setbDate(rs.getDate("bDate"));
        }
        rs.close();
        connect.state().close();

        return customer;
    }
    
    
    public Customer find(String pseudo) throws SQLException {
        Customer customer = new Customer();

        String query = "SELECT c.idCustomer, c.lastName, c.firstName, c.\"address\", c.zipcode,"
                + " c.city, u.idUser"
                + " FROM Customer AS c"
                + " JOIN \"User\" AS u"
                + " ON c.idUser=u.idUser"
                + " WHERE u.pseudo='" + pseudo + "'";

        ResultSet rs = connect.state().executeQuery(query);
        if (rs.next()) {
            customer.setId(Integer.valueOf(rs.getString("idCustomer")));
            customer.setIdUser(rs.getInt("idUser"));
            customer.setLastName(rs.getString("lastName"));
            customer.setFirstName(rs.getString("firstName"));
            customer.setAddress(rs.getString("address"));
            customer.setZipcode(rs.getString("zipcode"));
            customer.setCity(rs.getString("city"));
        }
        rs.close();
        connect.state().close();

        return customer;
    }

    public int getIdCustomer(String pseudo, String password) throws SQLException {
        int idCustomer = 0;
        String encryptPass = null;
        try {
            encryptPass = Encryption.encrypt(password);
        } catch (Exception ex) {
            // Exception, propager dès la classe DAO ou traiter ici?
        }
        String query = "SELECT c.idCustomer FROM Customer AS c"
                + " JOIN \"User\" AS u"
                + " ON c.idUser=u.idUser"
                + " WHERE u.pseudo='" + pseudo + "'"
                + " AND u.password='" + encryptPass + "'";

        ResultSet rs = connect.state().executeQuery(query);
        if (rs.next()) {
            idCustomer = rs.getInt("idCustomer");
        }

        rs.close();
        connect.state().close();

        return idCustomer;
    }

    public boolean login(String pseudo, String password) throws SQLException, Exception {
        String encryptPass = null;
        try {
            encryptPass = Encryption.encrypt(password);
        } catch (Exception ex) {
            // Exception, propager dès la classe DAO ou traiter ici?
        }
        String query = "SELECT u.password"
                + " FROM \"User\" AS u"
                + " WHERE u.active = 1"
                + " AND u.pseudo = '" + pseudo + "'";

        ResultSet rs = connect.state().executeQuery(query);

        if (rs.next()) {

            if (encryptPass.equals(rs.getString("password"))) {
                return true;
            }
        }
        rs.close();
        connect.state().close();
        return false;
    }

    public ArrayList<Customer> listUsers() throws SQLException {
        ArrayList<Customer> users = new ArrayList();

        String query = "SELECT TOP 7 idUser, pseudo"
                + " FROM \"User\""
                + " WHERE active=1"
                + " ORDER BY idUser DESC";
        ResultSet rs = connect.state().executeQuery(query);
        
        while(rs.next()){
            Customer user = new Customer();
            user.setIdUser(rs.getInt("idUser"));
            user.setPseudo(rs.getString("pseudo"));
            users.add(user);
        }
        rs.close();
        connect.state().close();
                
        return users;
    }

    public boolean checkCustomer(String pseudo) throws SQLException {
       
        String query = "SELECT pseudo FROM \"User\" WHERE pseudo='" + pseudo + "'";

        ResultSet rs = connect.state().executeQuery(query);

        if (rs.next()) {
            if (pseudo.equals(rs.getString("pseudo"))) {
                return false;
            }
        }
        rs.close();
        connect.state().close();
        return true;

    }

        
}
