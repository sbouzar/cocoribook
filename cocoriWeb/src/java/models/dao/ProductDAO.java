package models.dao;

import java.sql.PreparedStatement;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import models.elements.Author;
import models.beans.beanProduct;
import models.elements.Comment;
import models.elements.Customer;
import models.elements.Label;
import models.elements.Product;
import models.elements.Publisher;
import models.elements.Tag;
import models.elements.Theme;

public class ProductDAO extends DAO<beanProduct> {

    @Override
    public boolean create(beanProduct obj) {
        return false;
    }

    @Override
    public boolean delete(beanProduct obj) {
        return false;
    }

    @Override
    public boolean update(beanProduct obj) {
        return false;
    }

    @Override
    public beanProduct find(int id) throws SQLException {

        String query = "SELECT wo.title FROM Work AS wo"
                + " JOIN Product AS p"
                + " ON wo.idWork = p.idWork"
                + " WHERE p.idProduct = ?";
        
        beanProduct p = new beanProduct();
        
        PreparedStatement pstmt = connect.preparedState(query);
        pstmt.setInt(1, id);
        
        ResultSet rs = pstmt.executeQuery();

        if(rs.next()){
            p.setId(id);
            p.setTitle(rs.getString("title"));
        }
        p.setPrice(getPriceAtDate(id, new Date()));
        p.setUnitPrice(getPriceAtDate(id, new Date()));
        p.setTax(getTaxAtDate(id, new Date()));
        p.setDiscount(getDiscountAtDate(id, new Date()));
        
        rs.close();
        pstmt.close();
                
        return p;
    }
    
    public Product findProduct(int id) throws SQLException {

        Product product = new Product();

        String query = "SELECT pu.idPublisher, pu.name AS puName, w.title, w.subtitle, w.type, p.volume, p.isbn10, p.isbn13,"
                + " p.pages, p.publicationDate, p.summary, p.image, p.language, p.inventory, p.format, p.weight"
                + " FROM Product AS p"
                + " JOIN Publisher AS pu"
                + " ON p.idPublisher = pu.idPublisher"
                + " JOIN Work AS w"
                + " ON p.idWork = w.idWork"
                + " WHERE p.idProduct = ?";

        PreparedStatement pstmt = connect.preparedState(query);
        pstmt.setInt(1, id);
        
        ResultSet rs = pstmt.executeQuery();
        
        if(rs.next()){
            product.setId(id);
                // publisher
                Publisher publisher = new Publisher();
                publisher.setId(rs.getInt("idPublisher"));
                publisher.setName(rs.getString("puName"));
            product.setPublisher(publisher);
            product.setComments(initComments(id));
            product.setAuthors(initAuthors(id));
            product.setThemes(initThemes(id));
            product.setLabels(initLabels(id));
            product.setTags(initTags(id));
            product.setPrice(getPriceAtDate(id, new Date()));
            product.setDiscount(getDiscountAtDate(id, new Date()));
            product.setTax(getTaxAtDate(id, new Date()));
            product.setTitle(rs.getString("title"));
            product.setSubtitle(rs.getString("subtitle"));
            product.setType(rs.getString("type"));
            product.setVolume(rs.getInt("volume"));
            product.setIsbn10(rs.getString("isbn10"));
            product.setIsbn13(rs.getString("isbn13"));
            product.setPages(rs.getInt("pages"));
            product.setPublicationDate(rs.getDate("publicationDate"));
            product.setSummary(rs.getString("summary"));
            product.setImage(rs.getString("image"));
            product.setLanguage(rs.getString("language"));
            product.setInventory(rs.getInt("inventory"));
            product.setFormat(rs.getString("format"));
            product.setWeight(rs.getFloat("weight"));
        }
        
        rs.close();
        pstmt.close();
        
        return product;
    }

    public float getPriceAtDate(int idProduct, Date date) throws SQLException {

        //convert java date to sql datetime
        java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());

        float price = 0;
        String query = "SELECT TOP 1 p.price"
                + " FROM Price AS p"
                + " JOIN ProductPrice AS pp ON p.idPrice = pp.idPrice"
                + " WHERE pp.idProduct = ?"
                + " AND (pp.endDate > ? "
                + " OR pp.endDate IS NULL)"
                + " ORDER BY pp.startDate DESC";
        
        PreparedStatement pstmt = connect.preparedState(query);
        pstmt.setInt(1, idProduct);
        pstmt.setTimestamp(2, sqlDate);
        
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            price = rs.getFloat("price");
        }
        
        rs.close();
        pstmt.close();
        
        return price;
    }

    public float getTaxAtDate(int idProduct, Date date) throws SQLException {

        //convert java date to sql datetime
        java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());

        float tax = 0;
        String query = "SELECT TOP 1 t.rate"
                + " FROM Tax AS t"
                + " JOIN ProductTax AS pt ON t.idTax = pt.idTax"
                + " WHERE pt.idProduct = ?"
                + " AND pt.startDate < ?"
                + " AND (pt.endDate > ?"
                + " OR pt.endDate IS NULL)"
                + " ORDER BY pt.startDate DESC";

        PreparedStatement pstmt = connect.preparedState(query);
        pstmt.setInt(1, idProduct);
        pstmt.setTimestamp(2, sqlDate);
        pstmt.setTimestamp(3, sqlDate);
        
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            tax = rs.getFloat("rate");
        }
        
        rs.close();
        pstmt.close();
        
        return tax;
    }

    public float getDiscountAtDate(int idProduct, Date date) throws SQLException {

        float discount = 0;

        //convert java date to sql datetime
        java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());

        String query = "SELECT TOP 1 d.rate"
                + " FROM Discount AS d"
                + " JOIN ProductDiscount AS pd ON d.idDiscount = pd.idDiscount"
                + " WHERE pd.idProduct = ?"
                + " AND pd.startDate < ?"
                + " AND (pd.endDate > ?"
                + " OR pd.endDate IS NULL)"
                + " ORDER BY pd.startDate DESC";

        PreparedStatement pstmt = connect.preparedState(query);
        pstmt.setInt(1, idProduct);
        pstmt.setTimestamp(2, sqlDate);
        pstmt.setTimestamp(3, sqlDate);
        
        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            discount = rs.getFloat("rate");
        }
        
        rs.close();
        pstmt.close();
        
        return discount;
    }

    public Collection<Comment> initComments(int idProduct) throws SQLException{
        
        ArrayList<Comment> comments = new ArrayList();
        
        String query = "SELECT c.idComment, c.comment, c.rating, c.commentDate,"
                + " u.idUser, u.pseudo"
                + " FROM Product AS p"
                + " JOIN Comment AS c"
                + " ON p.idproduct = c.idProduct"
                + " JOIN \"User\" AS u"
                + " ON c.idUser = u.idUser"
                + " WHERE p.idProduct = ?"
                + " ORDER BY c.commentDate DESC";
        
        PreparedStatement pstmt = connect.preparedState(query);
        pstmt.setInt(1, idProduct);
        
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Comment comment = new Comment();
            comment.setId(rs.getInt("idComment"));
            comment.setComment(rs.getString("comment"));
            comment.setRating(rs.getByte("rating"));
            comment.setCommentDate(rs.getDate("commentDate"));
            Customer user = new Customer();
            user.setId(rs.getInt("idUser"));
            user.setPseudo(rs.getString("pseudo"));
            comment.setCustomer(user);
            comments.add(comment);
        }
        
        rs.close();
        pstmt.close();
        
        return comments;
    }
    
    public Collection<Author> initAuthors(int idProduct) throws SQLException{
        
        ArrayList<Author> authors = new ArrayList();
        
        String query = "SELECT a.idAuthor, a.lastName, a.firstName, a.bio"
                + " FROM Product AS p"
                + " JOIN Work AS w"
                + " ON p.idWork = w.idWork"
                + " JOIN WorkAuthor AS wa"
                + " ON w.idWork = wa.idWork"
                + " JOIN Author AS a"
                + " ON wa.idAuthor = a.idAuthor"
                + " WHERE p.idProduct = ?";
        
        PreparedStatement pstmt = connect.preparedState(query);
        pstmt.setInt(1, idProduct);
        
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Author author = new Author();
            author.setId(rs.getInt("idAuthor"));
            author.setLastName(rs.getString("lastName"));
            author.setFirstName(rs.getString("firstName"));
            author.setBio(rs.getString("bio"));
            authors.add(author);
        }
        rs.close();
        pstmt.close();
        
        return authors;
    }
    
    public Collection<Theme> initThemes(int idProduct) throws SQLException{
        
        ArrayList<Theme> themes = new ArrayList();
        
        String query = "SELECT t.idTheme, t.name, t.level"
                + " FROM Product AS p"
                + " JOIN Work AS w"
                + " ON p.idWork = w.idWork"
                + " JOIN WorkTheme AS wt"
                + " ON w.idWork = wt.idWork"
                + " JOIN Theme AS t"
                + " ON wt.idTheme = t.idTheme"
                + " WHERE p.idProduct = ?"
                + " AND t.active=1";
        
        PreparedStatement pstmt = connect.preparedState(query);
        pstmt.setInt(1, idProduct);
        
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Theme theme = new Theme();
            theme.setId(rs.getInt("idTheme"));
            theme.setName(rs.getString("name"));
            theme.setLevel(rs.getInt("level"));
            themes.add(theme);
        }
        rs.close();
        pstmt.close();
        
        return themes;
    }
    
    public Collection<Tag> initTags(int idProduct) throws SQLException{
        
        ArrayList<Tag> tags = new ArrayList();
        
        String query = "SELECT t.idTag, t.name"
                + " FROM Product AS p"
                + " JOIN ProductTag AS pt"
                + " ON p.idProduct = pt.idProduct"
                + " JOIN Tag AS t"
                + " ON pt.idTag = t.idTag"
                + " WHERE p.idProduct = ?"
                + " AND t.active=1";
        
        PreparedStatement pstmt = connect.preparedState(query);
        pstmt.setInt(1, idProduct);
        
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Tag tag = new Tag();
            tag.setId(rs.getInt("idTag"));
            tag.setName(rs.getString("name"));
            tags.add(tag);
        }
        rs.close();
        pstmt.close();
        
        return tags;
    }
    
    public Collection<Label> initLabels(int idProduct) throws SQLException{
        
        ArrayList<Label> labels = new ArrayList();
        
        String query = "SELECT l.idLabel, l.name"
                + " FROM Product AS p"
                + " JOIN ProductLabel AS pl"
                + " ON p.idProduct = pl.idProduct"
                + " JOIN Label AS l"
                + " ON pl.idLabel = l.idLabel"
                + " WHERE p.idProduct = ?"
                + " AND l.active=1"
                + " AND pl.startDate < GETDATE()"
                + " AND (endDate > GETDATE() OR endDate IS NULL)";
        
        PreparedStatement pstmt = connect.preparedState(query);
        pstmt.setInt(1, idProduct);
        
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            Label label = new Label();
            label.setId(rs.getInt("idLabel"));
            label.setName(rs.getString("name"));
            labels.add(label);
        }
        rs.close();
        pstmt.close();
        
        return labels;
    }
    
}
