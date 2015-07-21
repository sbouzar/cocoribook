package models.beans;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;
import models.dao.CommentDAO;
import models.elements.Comment;

public class beanComment implements Serializable {

    private CommentDAO dao;

    public beanComment() throws SQLException {
        this.dao = new CommentDAO();
    }

    public boolean create(Comment bc) throws SQLException {
        return dao.create(bc);
    }

    public boolean delete(Comment bc) throws SQLException {
        return dao.delete(bc);
    }
    
    public boolean update(Comment bc) throws SQLException{
        return dao.update(bc);
    }

    public Collection<Comment> listComments() throws SQLException {
        return dao.listComments();
    }

    public Comment find(int id) throws SQLException{
        return dao.find(id);
    }
    
    public Collection<Comment> findAll(int id) throws SQLException {
        return dao.findAll(id);
    }
    
    public Collection<Comment> commentByPages(int page, int idProduct, int idUser, int next) throws SQLException {
        return dao.commentByPages(page, idProduct, idUser, next);
    }
    
    public int getCommentPagination(int idProduct, int idUser, int nextp) throws SQLException {
        return dao.getCommentPagination(idProduct, idUser, nextp);
    }

    public CommentDAO getDao() {
        return dao;
    }

    public void setDao(CommentDAO dao) {
        this.dao = dao;
    }

 
}
