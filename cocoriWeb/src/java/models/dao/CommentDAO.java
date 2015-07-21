package models.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import models.elements.Comment;
import models.elements.Customer;
import models.elements.Product;

public class CommentDAO extends DAO<Comment> {

    @Override
    public boolean create(Comment obj) throws SQLException {

        String query = "INSERT INTO Comment(idProduct, idUser, comment, rating, commentDate, active) VALUES(?,?,?,?,?,?)";
        PreparedStatement pstmt = connect.preparedState(query);
        pstmt.setInt(1, obj.getProduct().getId());
        pstmt.setInt(2, obj.getCustomer().getIdUser());
        pstmt.setString(3, obj.getComment());
        pstmt.setByte(4, obj.getRating());
        pstmt.setDate(5, new java.sql.Date(System.currentTimeMillis()));
        pstmt.setBoolean(6, true);

        pstmt.executeUpdate();
        pstmt.close();

        return true;
    }

    @Override
    public boolean delete(Comment obj) throws SQLException {
        String query = "DELETE Comment WHERE idComment = ?";
        PreparedStatement pstmt = connect.preparedState(query);
        pstmt.setInt(1, obj.getId());
        pstmt.executeUpdate();
        pstmt.close();

        return true;
    }

    @Override
    public boolean update(Comment obj) throws SQLException {
        String query = "UPDATE Comment SET comment = ?, rating = ?"
                + " WHERE idComment = ?";

        PreparedStatement pstmt = connect.preparedState(query);
        pstmt.setString(1, obj.getComment());
        pstmt.setByte(2, obj.getRating());
        pstmt.setInt(3, obj.getId());

        pstmt.executeUpdate();
        pstmt.close();

        return true;
    }

    @Override
    public Comment find(int id) throws SQLException {

        Customer u = new Customer();
        Product p = new Product();
        Comment c = new Comment();

        String query = "SELECT u.pseudo, w.title, w.subtitle, c.comment, c.commentDate, c.rating, p.idProduct, p.\"image\""
                + " FROM Comment AS c"
                + " JOIN \"User\" AS u"
                + " ON c.idUser=u.idUser"
                + " JOIN Product AS p"
                + " ON c.idProduct=p.idProduct"
                + " JOIN Work AS w"
                + " ON p.idWork=w.idWork"
                + " WHERE c.active=1"
                + " AND idComment = '" + id + "'";

        ResultSet rs = connect.state().executeQuery(query);
        if (rs.next()) {

            u.setPseudo(rs.getString("pseudo"));
            c.setCustomer(u);

            p.setTitle(rs.getString("title"));
            p.setSubtitle(rs.getString("subtitle"));
            p.setId(rs.getInt("idProduct"));
            p.setImage(rs.getString("image"));
            c.setProduct(p);

            c.setComment(rs.getString("comment"));
            c.setCommentDate(rs.getDate("commentDate"));
            c.setRating(rs.getByte("rating"));
            c.setId(id);
        }

        rs.close();
        connect.state().close();

        return c;
    }

    public ArrayList<Comment> findAll(int id) throws SQLException {

        ArrayList<Comment> comments = new ArrayList();

        String query = "SELECT u.pseudo, c.idComment, c.comment, c.commentDate, c.rating, p.idProduct, w.title, w.subtitle"
                + " FROM Comment AS c"
                + " JOIN \"User\" AS u"
                + " ON c.idUser=u.idUser"
                + " JOIN Product AS p"
                + " ON c.idProduct=p.idProduct"
                + " JOIN Work AS w"
                + " ON p.idWork=w.idWork"
                + " WHERE c.active=1"
                + " AND u.idUser = " + id
                + " ORDER BY c.commentDate DESC";

        ResultSet rs = connect.state().executeQuery(query);

        while (rs.next()) {
            Customer customer = new Customer();
            Product product = new Product();
            Comment comment = new Comment();

            customer.setPseudo(rs.getString("pseudo"));
            comment.setCustomer(customer);

            product.setTitle(rs.getString("title"));
            product.setSubtitle(rs.getString("subtitle"));
            product.setId(rs.getInt("idProduct"));
            comment.setProduct(product);

            comment.setId(rs.getInt("idComment"));
            comment.setComment(rs.getString("comment"));
            comment.setCommentDate(rs.getDate("commentDate"));
            comment.setRating(rs.getByte("rating"));

            comments.add(comment);
        }

        rs.close();
        connect.state().close();

        return comments;
    }

    public ArrayList<Comment> listComments() throws SQLException {
        ArrayList<Comment> comments = new ArrayList();

        String query = "SELECT TOP 3 u.pseudo, w.title, w.subtitle, c.comment, c.commentDate, c.rating, p.idProduct, p.\"image\""
                + " FROM Comment AS c"
                + " JOIN \"User\" AS u"
                + " ON c.idUser=u.idUser"
                + " JOIN Product AS p"
                + " ON c.idProduct=p.idProduct"
                + " JOIN Work AS w"
                + " ON p.idWork=w.idWork"
                + " WHERE c.active=1"
                + " ORDER BY commentDate DESC";

        ResultSet rs = connect.state().executeQuery(query);

        while (rs.next()) {
            Customer user = new Customer();
            Product product = new Product();
            Comment comment = new Comment();

            user.setPseudo(rs.getString("pseudo"));
            comment.setCustomer(user);

            product.setId(rs.getInt("idProduct"));
            product.setTitle(rs.getString("title"));
            product.setSubtitle(rs.getString("subtitle"));
            product.setImage(rs.getString("image"));
            comment.setProduct(product);

            comment.setComment(rs.getString("comment"));
            comment.setCommentDate(rs.getDate("commentDate"));
            comment.setRating(rs.getByte("rating"));

            comments.add(comment);
        }

        rs.close();
        connect.state().close();

        return comments;
    }

    public int getCommentPagination(int idProduct, int idUser, int nextp) throws SQLException {
        float comments = 0;
        float next = nextp;

        if (idProduct != 0) {
            String query = "SELECT DISTINCT COUNT(co.idProduct) AS counter"
                    + " FROM Comment AS co"
                    + " WHERE co.idProduct = " + idProduct;

            ResultSet rs = connect.state().executeQuery(query);
            if (rs.next()) {
                comments = rs.getInt("counter");
            }
            rs.close();
            connect.state().close();
        }

        if (idUser != 0) {
            String query = "SELECT DISTINCT COUNT(co.idComment) AS counter"
                    + " FROM Comment AS co"
                    + " WHERE co.idUser = " + idUser;

            ResultSet rs = connect.state().executeQuery(query);
            if (rs.next()) {
                comments = rs.getInt("counter");
            }
            rs.close();
            connect.state().close();
        }

        int pages = (int) Math.ceil(comments / next);

        return pages;

    }

    public ArrayList<Comment> commentByPages(int page, int idProduct, int idUser, int next) throws SQLException {
        ArrayList<Comment> ac = new ArrayList();

        int offset = 1;
        if (page > 1) {
            for (int i = 1; i < page; i++) {
                offset = next * page - 5;
            }
            offset += 1;
        }

        if (page > 1) {
            next = next * page;
        }

        if (idProduct != 0) {
            String query = "SELECT DISTINCT t.idComment, t.comment, t.commentDate, t.rating, t.pseudo, t.idUser"
                    + " FROM "
                    + "(SELECT ROW_NUMBER() OVER(Order BY c.commentDate DESC) AS rownum, c.idComment, c.comment, c.commentDate, c.rating, u.pseudo, u.idUser"
                    + "	FROM Comment AS c "
                    + "	JOIN \"User\" AS u ON c.idUser=u.idUser "
                    + "	JOIN Product AS p ON c.idProduct=p.idProduct "
                    + "	JOIN Work AS w ON p.idWork=w.idWork "
                    + "	WHERE c.active= 1 AND p.idProduct = ?) AS t"
                    + "	WHERE rownum >= " + offset + " AND rownum <= " + next;
            PreparedStatement pstmt = connect.preparedState(query);
            pstmt.setInt(1, idProduct);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                Comment comment = new Comment();

                customer.setPseudo(rs.getString("pseudo"));
                customer.setId(rs.getInt("idUser"));
                comment.setCustomer(customer);

                comment.setId(rs.getInt("idComment"));
                comment.setComment(rs.getString("comment"));
                comment.setCommentDate(rs.getDate("commentDate"));
                comment.setRating(rs.getByte("rating"));

                ac.add(comment);
            }

            rs.close();
            pstmt.close();
        }

        if (idUser != 0) {
            String query = "SELECT DISTINCT t.idComment, t.comment, t.commentDate, t.rating, t.pseudo, t.idUser, "
                    + " t.title, t.subtitle, t.idProduct"
                    + " FROM "
                    + "(SELECT ROW_NUMBER() OVER(Order BY c.commentDate DESC) AS rownum, c.idComment, c.comment, c.commentDate, "
                    + " c.rating, u.pseudo, u.idUser, w.title, w.subtitle, p.idProduct"
                    + "	FROM Comment AS c "
                    + "	JOIN \"User\" AS u ON c.idUser=u.idUser "
                    + "	JOIN Product AS p ON c.idProduct=p.idProduct "
                    + "	JOIN Work AS w ON p.idWork=w.idWork "
                    + "	WHERE c.active= 1 AND u.idUser = ?) AS t"
                    + "	WHERE rownum >= " + offset + " AND rownum <= " + next;
            PreparedStatement pstmt = connect.preparedState(query);
            pstmt.setInt(1, idUser);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                Comment comment = new Comment();
                Product product = new Product();
                
                customer.setPseudo(rs.getString("pseudo"));
                customer.setId(rs.getInt("idUser"));
                comment.setCustomer(customer);

                product.setTitle(rs.getString("title"));
                product.setSubtitle(rs.getString("subtitle"));
                product.setId(rs.getInt("idProduct"));
                comment.setProduct(product);
                
                comment.setId(rs.getInt("idComment"));
                comment.setComment(rs.getString("comment"));
                comment.setCommentDate(rs.getDate("commentDate"));
                comment.setRating(rs.getByte("rating"));

                ac.add(comment);
            }

            rs.close();
            pstmt.close();
        }
        return ac;
    }

}
