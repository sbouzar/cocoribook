package models.dao;

import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import models.elements.Label;
import models.elements.Publisher;
import models.elements.Tag;
import models.elements.Theme;
import models.beans.beanProduct;
import models.beans.beanProducts;
import models.elements.Product;

public class ProductsDAO extends DAO<beanProducts> {

    public HashMap<String, Product> listPromote() throws SQLException {

        HashMap<String, Product> products = new HashMap();

        String query = "SELECT TOP 8 p.idProduct, p.summary, p.image, w.title, w.subtitle"
                + " FROM Product AS p"
                + " JOIN Work AS w ON p.idWork = w.idWork"
                + " WHERE p.active=1"
                + " AND p.promote=1"
                + " AND p.inventory > 0";

        ResultSet rs = connect.state().executeQuery(query);

        while (rs.next()) {
            // create product
            beanProduct bp = new beanProduct();
            Product product = new Product();

            // set product
            product.setId(rs.getInt("idProduct"));
            product.setSummary(rs.getString("summary"));
            product.setImage(rs.getString("image"));
            product.setTitle(rs.getString("title"));
            product.setSubtitle(rs.getString("subtitle"));
            product.setPrice(bp.getPriceAtDate(rs.getInt("idProduct"), new Date()));
            product.setTax(bp.getTaxAtDate(rs.getInt("idProduct"), new Date()));
            product.setDiscount(bp.getDiscountAtDate(rs.getInt("idProduct"), new Date()));
            product.setTotalPrice(bp.calcTotalPrice(product.getPrice(), product.getTax(), product.getDiscount()));
            product.setAuthors(bp.initAuthors(rs.getInt("idProduct")));
            // add product to collection
            products.put(String.valueOf(product.getId()), product);
        }

        rs.close();
        connect.state().close();

        return products;
    }

    public HashMap<String, Product> listNews() throws SQLException {

        HashMap<String, Product> products = new HashMap();

        String query = "SELECT TOP 5 p.idProduct, p.summary, p.image, w.title, w.subtitle"
                + " FROM Product AS p"
                + " JOIN Work AS w ON p.idWork = w.idWork"
                + " WHERE p.active=1"
                + " AND p.inventory > 0"
                + " ORDER BY p.idProduct DESC";

        ResultSet rs = connect.state().executeQuery(query);

        while (rs.next()) {
            // create product
            beanProduct bp = new beanProduct();
            Product product = new Product();
            
            // set product
            product.setId(rs.getInt("idProduct"));
            product.setSummary(rs.getString("summary"));
            product.setImage(rs.getString("image"));
            product.setTitle(rs.getString("title"));
            product.setSubtitle(rs.getString("subtitle"));
            product.setPrice(bp.getPriceAtDate(rs.getInt("idProduct"), new Date()));
            product.setTax(bp.getTaxAtDate(rs.getInt("idProduct"), new Date()));
            product.setDiscount(bp.getDiscountAtDate(rs.getInt("idProduct"), new Date()));
            product.setTotalPrice(bp.calcTotalPrice(product.getPrice(), product.getTax(), product.getDiscount()));
            product.setAuthors(bp.initAuthors(rs.getInt("idProduct")));
            // add product to collection
            products.put(String.valueOf(product.getId()), product);
        }

        rs.close();
        connect.state().close();

        return products;
    }

    public HashMap<String, Product> listBestSellers() throws SQLException {

        HashMap<String, Product> products = new HashMap();

        String query = "SELECT TOP 5 p.idProduct, p.summary, p.image, w.title, w.subtitle"
                + " FROM Product AS p"
                + " JOIN Work AS w ON p.idWork = w.idWork"
                + " WHERE p.active=1"
                + " AND p.inventory > 0"
                + " ORDER BY p.counter DESC";

        ResultSet rs = connect.state().executeQuery(query);

        while (rs.next()) {
            // create product
            beanProduct bp = new beanProduct();
            Product product = new Product();
            
            // set product
            product.setId(rs.getInt("idProduct"));
            product.setSummary(rs.getString("summary"));
            product.setImage(rs.getString("image"));
            product.setTitle(rs.getString("title"));
            product.setSubtitle(rs.getString("subtitle"));
            product.setPrice(bp.getPriceAtDate(rs.getInt("idProduct"), new Date()));
            product.setTax(bp.getTaxAtDate(rs.getInt("idProduct"), new Date()));
            product.setDiscount(bp.getDiscountAtDate(rs.getInt("idProduct"), new Date()));
            product.setTotalPrice(bp.calcTotalPrice(product.getPrice(), product.getTax(), product.getDiscount()));
            product.setAuthors(bp.initAuthors(rs.getInt("idProduct")));
            // add product to collection
            products.put(String.valueOf(product.getId()), product);
        }

        rs.close();
        connect.state().close();

        return products;
    }

    public int getSearchPagination(Map parameters, int nextp) throws SQLException, UnsupportedEncodingException {

        float products = 0;
        float next = nextp;

        String query = "SELECT DISTINCT COUNT(p.idProduct) AS counter"
                + " FROM Product AS p"
                + " JOIN Work AS w"
                + " ON p.idWork = w.idWork"
                + " JOIN Publisher AS pu"
                + " ON p.idPublisher = pu.idPublisher";

        String andQuery = "";

        // publisher criteria
        String[] publisher = (String[]) parameters.get("publisher");
        if (publisher != null) {
            andQuery += " AND pu.idPublisher = '" + publisher[0] + "'";
        }

        // author criteria
        String[] author = (String[]) parameters.get("author");
        if (author != null) {
            query += " JOIN WorkAuthor AS wa"
                    + " ON w.idWork = wa.idWork";
            andQuery += " AND wa.idAuthor = '" + author[0] + "'";
        }

        // type criteria
        String[] type = (String[]) parameters.get("type");
        if (type != null) {
            String t = new String (type[0].getBytes(),"UTF-8");
            andQuery += " AND w.type = '" + t + "'";
        }

        // tag criteria
        String[] tag = (String[]) parameters.get("tag");
        if (tag != null) {
            query += " JOIN ProductTag AS pt"
                    + " ON p.idProduct = pt.idProduct";
            andQuery += " AND pt.idTag = '" + tag[0] + "'";
        }

        // label criteria
        String[] label = (String[]) parameters.get("label");
        if (label != null) {
            query += " JOIN ProductLabel AS pl"
                    + " ON p.idProduct = pl.idProduct";
            andQuery += " AND pl.idLabel = '" + label[0] + "'"
                    + " AND pl.startDate < GETDATE()"
                    + " AND (pl.endDate IS NULL OR pl.endDate > GETDATE())";
        }

        // theme criteria
        String[] theme = (String[]) parameters.get("theme");
        if (theme != null) {
            query += " JOIN WorkTheme AS wt"
                    + " ON w.idWork = wt.idWork";
            andQuery += " AND wt.idTheme = '" + theme[0] + "'";
        }

        // fulltext criteria
        String[] search = (String[]) parameters.get("search");
        if (search != null) {
            query += " JOIN WorkAuthor AS wa"
                    + " ON w.idWork = wa.idWork"
                    + " JOIN Author AS a"
                    + " ON wa.idAuthor = a.idAuthor";
            andQuery += " AND (";
            String[] criterias = search[0].split(",");
            for (int i = 0; i < criterias.length; i++) {
                String criteria =  criterias[i].trim().replaceAll("\\<.*?>","");
                criteria =  criteria.replaceAll("'","''");
                criteria = new String (criteria.getBytes(),"UTF-8");
                andQuery += " (w.title LIKE '%" + criteria + "%' OR w.subtitle LIKE '%" + criteria + "%')";
                andQuery += " OR (CONCAT( a.firstName, ' ', a.lastName ) LIKE '%" + criteria + "%')";
                if (i < criterias.length - 1) {
                    andQuery += " OR ";
                }
            }
            andQuery += ")";
        }

        query += " WHERE p.active=1" + andQuery;

        ResultSet rs = connect.state().executeQuery(query);

        if (rs.next()) {
            products = rs.getInt("counter");
        }

        rs.close();
        connect.state().close();

        int pages = (int) Math.ceil(products / next);
        return pages;

    }

    public ArrayList<Product> listSearch(Map parameters, int next) throws SQLException, UnsupportedEncodingException, NumberFormatException {

        ArrayList<Product> products = new ArrayList();

        String query = "SELECT DISTINCT p.idProduct, p.summary, p.image, p.inventory, w.title, w.subtitle"
                + " FROM Product AS p"
                + " JOIN Work AS w"
                + " ON p.idWork = w.idWork";

        String andQuery = "";

        // publisher criteria
        String[] publisher = (String[]) parameters.get("publisher");
        if (publisher != null) {
            query += " JOIN Publisher AS pu"
                    + " ON p.idPublisher = pu.idPublisher";
            andQuery += " AND pu.idPublisher = '" + publisher[0] + "'";
        }

        // author criteria
        String[] author = (String[]) parameters.get("author");
        if (author != null) {
            query += " JOIN WorkAuthor AS wa"
                    + " ON w.idWork = wa.idWork";
            andQuery += " AND wa.idAuthor = '" + author[0] + "'";
        }

        // type criteria
        String[] type = (String[]) parameters.get("type");
        if (type != null) {
            String t = new String (type[0].getBytes(),"UTF-8");
            andQuery += " AND w.type = '" + t + "'";
        }

        // tag criteria
        String[] tag = (String[]) parameters.get("tag");
        if (tag != null) {
            query += " JOIN ProductTag AS pt"
                    + " ON p.idProduct = pt.idProduct";
            andQuery += " AND pt.idTag = '" + tag[0] + "'";
        }

        // label criteria
        String[] label = (String[]) parameters.get("label");
        if (label != null) {
            query += " JOIN ProductLabel AS pl"
                    + " ON p.idProduct = pl.idProduct";
            andQuery += " AND pl.idLabel = '" + label[0] + "'"
                    + " AND pl.startDate < GETDATE()"
                    + " AND (pl.endDate IS NULL OR pl.endDate > GETDATE())";
        }

        // theme criteria
        String[] theme = (String[]) parameters.get("theme");
        if (theme != null) {
            query += " JOIN WorkTheme AS wt"
                    + " ON w.idWork = wt.idWork";
            andQuery += " AND wt.idTheme = '" + theme[0] + "'";
        }
        
        // fulltext criteria
        String[] search = (String[]) parameters.get("search");
        if (search != null) {
            query += " JOIN WorkAuthor AS wa"
                    + " ON w.idWork = wa.idWork"
                    + " JOIN Author AS a"
                    + " ON wa.idAuthor = a.idAuthor";
            andQuery += " AND (";
            String[] criterias = search[0].split(",");
            for (int i = 0; i < criterias.length; i++) {
                String criteria =  criterias[i].trim().replaceAll("\\<.*?>","");
                criteria =  criteria.replaceAll("'","''");
                criteria = new String (criteria.getBytes(),"UTF-8");
                andQuery += " (w.title LIKE '%" + criteria + "%' OR w.subtitle LIKE '%" + criteria + "%')";
                andQuery += " OR (CONCAT( a.firstName, ' ', a.lastName ) LIKE '%" + criteria + "%')";
                if (i < criterias.length - 1) {
                    andQuery += " OR ";
                }
            }
            andQuery += ")";
        }

        // pagination scope
        int offset = 0;
        //int next = 9;
        String[] page = (String[]) parameters.get("page");
        if (page != null) {
            for (int i = 0; i < page.length; i++) {
                int p = Integer.valueOf(page[i]) - 1;
                offset = next * p;
            }
        }

        query += " WHERE p.active=1"
                + andQuery
                + " ORDER BY w.title ASC"
                + " OFFSET " + offset + " ROWS"
                + " FETCH NEXT " + next + " ROWS ONLY";
        
        ResultSet rs = connect.state().executeQuery(query);

        while (rs.next()) {
            // create product
            beanProduct bp = new beanProduct();
            Product product = new Product();
            
            // set product
            product.setId(rs.getInt("idProduct"));
            product.setSummary(rs.getString("summary"));
            product.setImage(rs.getString("image"));
            product.setTitle(rs.getString("title"));
            product.setSubtitle(rs.getString("subtitle"));
            product.setInventory(rs.getInt("inventory"));
            product.setPrice(bp.getPriceAtDate(rs.getInt("idProduct"), new Date()));
            product.setTax(bp.getTaxAtDate(rs.getInt("idProduct"), new Date()));
            product.setDiscount(bp.getDiscountAtDate(rs.getInt("idProduct"), new Date()));
            product.setTotalPrice(bp.calcTotalPrice(product.getPrice(), product.getTax(), product.getDiscount()));
            product.setAuthors(bp.initAuthors(rs.getInt("idProduct")));
            // add product to collection
            products.add(product);
        }

        rs.close();
        connect.state().close();

        return products;
    }

    public ArrayList<Theme> listThemes() throws SQLException {

        ArrayList<Theme> themes = new ArrayList();

        String query = "SELECT idTheme, name, \"level\""
                + " FROM Theme"
                + " WHERE active=1"
                + " AND \"level\" > 0"
                + " ORDER BY \"left\", name";

        ResultSet rs = connect.state().executeQuery(query);

        while (rs.next()) {
            // create theme
            Theme theme = new Theme();
            // set theme
            theme.setId(rs.getInt("idTheme"));
            theme.setName(rs.getString("name"));
            theme.setLevel(rs.getInt("level"));
            // add theme to collection
            themes.add(theme);
        }

        rs.close();
        connect.state().close();

        return themes;
    }

    public ArrayList<Tag> listTags() throws SQLException {

        ArrayList<Tag> tags = new ArrayList();

        String query = "SELECT idTag, name"
                + " FROM Tag"
                + " WHERE active=1"
                + " ORDER BY name";

        ResultSet rs = connect.state().executeQuery(query);

        while (rs.next()) {
            // create tag
            Tag tag = new Tag();
            // set tag
            tag.setId(rs.getInt("idTag"));
            tag.setName(rs.getString("name"));
            // add tag to collection
            tags.add(tag);
        }

        rs.close();
        connect.state().close();

        return tags;
    }

    public ArrayList<Label> listLabels() throws SQLException {

        ArrayList<Label> labels = new ArrayList();

        String query = "SELECT idLabel, name"
                + " FROM Label"
                + " WHERE active=1"
                + " ORDER BY name";

        ResultSet rs = connect.state().executeQuery(query);

        while (rs.next()) {
            // create label
            Label label = new Label();
            // set label
            label.setId(rs.getInt("idLabel"));
            label.setName(rs.getString("name"));
            // add label to collection
            labels.add(label);
        }

        rs.close();
        connect.state().close();

        return labels;
    }

    public ArrayList<Publisher> listPublishers() throws SQLException {

        ArrayList<Publisher> publishers = new ArrayList();

        String query = "SELECT TOP 7 idPublisher, name"
                + " FROM Publisher"
                + " WHERE active=1"
                + " ORDER BY NEWID()";

        ResultSet rs = connect.state().executeQuery(query);

        while (rs.next()) {
            // create publisher
            Publisher publisher = new Publisher();
            // set publisher
            publisher.setId(rs.getInt("idPublisher"));
            publisher.setName(rs.getString("name"));
            // add publisher to collection
            publishers.add(publisher);
        }

        rs.close();
        connect.state().close();

        return publishers;
    }

    @Override
    public boolean create(beanProducts obj) {
        return false;
    }

    @Override
    public boolean delete(beanProducts obj) {
        return false;
    }

    @Override
    public boolean update(beanProducts obj) {
        return false;
    }

    @Override
    public beanProducts find(int id) {
        return null;
    }

}
