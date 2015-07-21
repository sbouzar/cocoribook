package models.dao;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDAO implements Serializable {
    
    private Connection con;
    private static ConnectionDAO connection;
    
    private ConnectionDAO() throws ClassNotFoundException, SQLException{

        
        String url = "jdbc:sqlserver://localhost:1433;databaseName=CocoriBook;";
        String user = "sa";
        String pwd = "22juillet2013";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        con = DriverManager.getConnection(url, user, pwd);
        
    }
    
    public static synchronized ConnectionDAO getInstance() throws ClassNotFoundException, SQLException{
        if(connection == null){
            ConnectionDAO.connection = new ConnectionDAO();
        }
        
        return ConnectionDAO.connection;
    }
    
    public Statement state() throws SQLException{
        return con.createStatement();
    }
    
    public CallableStatement callState(String query) throws SQLException{
        return con.prepareCall(query);
    }
    
    public PreparedStatement preparedState(String query) throws SQLException{
        return con.prepareStatement(query);
    }
    
    public void closeConnection() throws SQLException{
        ConnectionDAO.connection = null;
        if(con != null){
            con.close();
        }
    }
        
}
