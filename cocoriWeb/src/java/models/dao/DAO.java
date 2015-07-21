package models.dao;

import java.io.Serializable;
import java.sql.SQLException;

public abstract class DAO<T> implements Serializable {

    protected ConnectionDAO connect = null;

    public DAO() {
        try {
            this.connect = ConnectionDAO.getInstance();
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("SQL Error : " + ex.getMessage());
        }
    }

    public abstract boolean create(T obj) throws Exception;

    public abstract boolean delete(T obj) throws Exception;

    public abstract boolean update(T obj) throws Exception;

    public abstract T find(int id) throws Exception;
}
