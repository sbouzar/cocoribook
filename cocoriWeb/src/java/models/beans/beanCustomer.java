package models.beans;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import models.dao.CustomerDAO;
import models.elements.Customer;
import models.elements.Validator;

public class beanCustomer implements Serializable {

//    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    private CustomerDAO dao;
    private ArrayList<Customer> users;
    private Customer customer;

    public beanCustomer() throws SQLException {
        this.dao = new CustomerDAO();
        customer = new Customer();
        users = dao.listUsers();
    }

    public boolean subscribe(Map form) throws ParseException, Exception {
        String firstName = new String(((String[]) form.get("firstName"))[0].trim().getBytes(), "UTF-8");
        String lastName = new String(((String[]) form.get("lastName"))[0].trim().getBytes(), "UTF-8");
        String city = new String(((String[]) form.get("city"))[0].trim().getBytes(), "UTF-8");

        if (Validator.controlOnlyLettersFirstName(firstName)) {
            customer.setFirstName(firstName);
        }
        if (Validator.controlOnlyLettersLastName(lastName)) {
            customer.setLastName(lastName);
        }
        if (Validator.controlEmailAdress(((String[]) form.get("email"))[0].trim())) {
            customer.setEmail(((String[]) form.get("email"))[0].trim());
        }
        if (Validator.controlOnlyLettersCity(city)) {
            customer.setCity(city);
        }
        if (Validator.controlZipCode(((String[]) form.get("zipcode"))[0].trim())) {
            customer.setZipcode(((String[]) form.get("zipcode"))[0].trim());
        }
        if (Validator.controlPhoneNumber(((String[]) form.get("phone"))[0].trim())) {
            customer.setPhone(((String[]) form.get("phone"))[0].trim());
        }

        String date = ((String[]) form.get("bDate"))[0];
        Date bDate = formatter.parse(date);
        formatter.setLenient(false);
        if (Validator.controlBirthDate(bDate)) {
            customer.setbDate(bDate);

        }

        customer.setAddress(((String[]) form.get("address"))[0].trim());
        customer.setPseudo(((String[]) form.get("pseudo"))[0].trim());
        customer.setPassword(((String[]) form.get("password"))[0].trim());
        String passwordConfirm = ((String[]) form.get("passwordConfirm"))[0].trim();

        return customer.getPassword().equals(passwordConfirm);
    }

    public boolean subscribe(Map form, int idUser, int idCustomer) throws ParseException, Exception {
        customer.setId(idCustomer);
        customer.setIdUser(idUser);

        String firstName = new String(((String[]) form.get("firstName"))[0].trim().getBytes(), "UTF-8");
        String lastName = new String(((String[]) form.get("lastName"))[0].trim().getBytes(), "UTF-8");
        String city = new String(((String[]) form.get("city"))[0].trim().getBytes(), "UTF-8");

        if (Validator.controlOnlyLettersFirstName(firstName)) {
            customer.setFirstName(firstName);
        }
        if (Validator.controlOnlyLettersLastName(lastName)) {
            customer.setLastName(lastName);
        }
        if (Validator.controlEmailAdress(((String[]) form.get("email"))[0].trim())) {
            customer.setEmail(((String[]) form.get("email"))[0].trim());
        }
        if (Validator.controlOnlyLettersCity(city)) {
            customer.setCity(city);
        }
        if (Validator.controlZipCode(((String[]) form.get("zipcode"))[0].trim())) {
            customer.setZipcode(((String[]) form.get("zipcode"))[0].trim());
        }
        if (Validator.controlPhoneNumber(((String[]) form.get("phone"))[0].trim())) {
            customer.setPhone(((String[]) form.get("phone"))[0].trim());
        }

        String date = ((String[]) form.get("bDate"))[0];
        Date bDate = formatter.parse(date);
        formatter.setLenient(false);
        if (Validator.controlBirthDate(bDate)) {
            customer.setbDate(bDate);

        }

        customer.setAddress(((String[]) form.get("address"))[0].trim());
        customer.setPseudo(((String[]) form.get("pseudo"))[0].trim());
        customer.setPassword(((String[]) form.get("password"))[0].trim());
        String passwordConfirm = ((String[]) form.get("passwordConfirm"))[0].trim();

        return customer.getPassword().equals(passwordConfirm);
    }

    public boolean login(String pseudo, String password) throws SQLException, Exception {
        return dao.login(pseudo, password);
    }

    public int getIdCustomer(String pseudo, String password) throws SQLException, Exception {
        return dao.getIdCustomer(pseudo, password);
    }

    public boolean checkCustomer(String pseudo) throws SQLException {
        return dao.checkCustomer(pseudo);
    }

    public Collection<Customer> listUsers() throws SQLException {
        return dao.listUsers();
    }

    public Customer find(int id) throws SQLException, Exception {
        return dao.find(id);
    }

    public Customer find(String pseudo) throws SQLException {
        return dao.find(pseudo);
    }

    public boolean create(Customer obj) throws SQLException {
        return dao.create(obj);
    }

    public boolean update(Customer obj) throws SQLException {
        return dao.update(obj);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
