package models.beans;

import models.elements.Tag;
import models.elements.Label;
import models.elements.Theme;
import models.elements.Publisher;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import models.dao.ProductsDAO;
import models.elements.Product;

public class beanProducts implements Serializable {
    
    private ProductsDAO dao;
    private HashMap<String, Product> promoteProducts;
    private HashMap<String, Product> newsProducts;
    private HashMap<String, Product> bestSellersProducts;
    private ArrayList<Theme> themes;
    private ArrayList<Tag> tags;
    private ArrayList<Label> labels;
    private ArrayList<Publisher> publishers;
    
    public beanProducts() throws SQLException {
        this.dao = new ProductsDAO();
        promoteProducts = dao.listPromote();
        newsProducts = dao.listNews();
        bestSellersProducts = dao.listBestSellers();
        themes = dao.listThemes();
        tags = dao.listTags();
        labels = dao.listLabels();
        publishers = dao.listPublishers();
    }
    
    public Collection<Product> listPromote(){
        return promoteProducts.values();
    }
    
    public Collection<Product> listNews(){
        return newsProducts.values();
    }
    
    public Collection<Product> listBestSellers(){
        return bestSellersProducts.values();
    }
    
    public Collection<Product> listSearch(Map parameters, int next) throws SQLException, UnsupportedEncodingException, NumberFormatException {
        return dao.listSearch(parameters, next);
    }
    
    public int getSearchPagination(Map parameters, int next) throws SQLException, UnsupportedEncodingException{
        return dao.getSearchPagination(parameters, next);
    }
    
    public Collection<Theme> listThemes(){
        return themes;
    }
    
    public Collection<Tag> listTags(){
        return tags;
    }
    
    public Collection<Label> listLabels(){
        return labels;
    }
    
    public Collection<Publisher> listPublishers(){
        return publishers;
    }

}
