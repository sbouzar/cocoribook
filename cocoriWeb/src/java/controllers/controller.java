package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.elements.Label;
import models.elements.Publisher;
import models.elements.Tag;
import models.elements.Theme;
import models.beans.beanCart;
import models.beans.beanComment;
import models.beans.beanCustomer;
import models.beans.beanDeliveryAddress;
import models.beans.beanOrder;
import models.beans.beanOrders;
import models.beans.beanProduct;
import models.beans.beanProducts;
import models.elements.Comment;
import models.elements.Customer;
import models.elements.DeliveryAddress;
import models.elements.Order;
import models.elements.OrderLine;
import models.elements.Product;

@WebServlet(name = "controller", urlPatterns = {"/controller"})
public class controller extends HttpServlet {

    private Cookie getCookie(Cookie[] cookies, String name) {
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) {
                    return c;
                }
            }
        }
        return null;
    }

    private void keepForm(HttpServletRequest request, HttpSession session) {
        Customer c = (Customer) session.getAttribute("customer");
        String lastName = request.getParameter("lastName");
        String firstName = request.getParameter("firstName");
        String email = request.getParameter("email");
        String bDate = request.getParameter("bDate");
        String address = request.getParameter("address");
        String pseudo = request.getParameter("pseudo");
        String zipcode = request.getParameter("zipcode");
        String city = request.getParameter("city");
        String phone = request.getParameter("phone");

        c.setLastName(lastName);
        c.setFirstName(firstName);
        c.setEmail(email);
        c.setBirthDate(bDate);
        c.setAddress(address);
        c.setPseudo(pseudo);
        c.setPhone(phone);
        c.setZipcode(zipcode);
        c.setCity(city);

        session.setAttribute("customer", c);

    }

    private void feedHome(beanProducts products, HttpServletRequest request) {
        //listPromote : feed carousel
        Collection<Product> promoteProducts = products.listPromote();
        request.setAttribute("carousel", promoteProducts);

        //listNews : feed news
        Collection<Product> newsProducts = products.listNews();
        request.setAttribute("news", newsProducts);

        //listNews : feed bestsellers
        Collection<Product> bestSellersProducts = products.listBestSellers();
        request.setAttribute("bestSellers", bestSellersProducts);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // encoding
        response.setContentType("text/html; charset=UTF-8");

        // default page : home
        String url = "/WEB-INF/home.jsp";
        HttpSession session = request.getSession();
        boolean logged = false;
        session.setAttribute("logged", logged);
        int size = 0;
        float total = 0;
        float taxes = 0;
        float discounts = 0;
        float subtotal = 0;

        Cookie ckLogged = getCookie(request.getCookies(), "logged");
        if (ckLogged != null) {
            logged = true;
            session.setAttribute("logged", logged);
        }

        // default : for all pages
        beanProducts products = (beanProducts) request.getAttribute("products");
        if (products == null) {
            try {
                products = new beanProducts();
                request.setAttribute("products", products);
            } catch (SQLException ex) {
                request.setAttribute("error", "<h4>Erreur</h4><p>Impossible de se connecter à la base de données</p>");
            }
        }

        try {
            Collection<Publisher> publishers = products.listPublishers();
            request.setAttribute("publishers", publishers);
        } catch (Exception ex) {
            request.setAttribute("error", "<h4>Erreur</h4><p>Impossible de se connecter à la base de données</p>");
        }

        beanCart cart = (beanCart) session.getAttribute("cart");
        if (cart == null) {
            cart = new beanCart();
            session.setAttribute("cart", cart);
        }
        size = cart.nbItem();
        session.setAttribute("nbItem", size);

        beanProduct bProduct = (beanProduct) session.getAttribute("product");
        try {
            if (bProduct == null) {
                if (request.getParameter("id") != null) {
                    bProduct = cart.getP(Integer.valueOf(request.getParameter("id")));

                } else {
                    bProduct = new beanProduct();
                }
                session.setAttribute("product", bProduct);
            }
        } catch (SQLException ex) {
            request.setAttribute("error", "<h4>Erreur</h4><p>Impossible de se connecter à la base de données</p>");
        }

        // get current url with parameters
        String currentURL = request.getRequestURL().append('?').toString();
        if (request.getQueryString() != null) {
            currentURL = request.getRequestURL().append('?').append(request.getQueryString()).toString();
        }
        // remove page param
        currentURL = currentURL.replaceAll("&page=[0-9]+", "");
        currentURL = currentURL.replaceAll("&next=[0-9]+", "");
        currentURL = currentURL.replaceAll("&mode=modifyModCart", "");
        currentURL = currentURL.replaceAll("&mode=cart", "");
//        currentURL = currentURL.replaceAll("&id=[0-9]+", "");
        request.setAttribute("currentURL", currentURL);

        // contact task
        if ("contact".equals(request.getParameter("task"))) {

            String email = "";
            String name = "";
            String message = "";

            if (request.getParameter("contact-email") != null) {
                email = request.getParameter("contact-email");
            }
            if (request.getParameter("contact-name") != null) {
                name = request.getParameter("contact-name");
            }
            if (request.getParameter("contact-message") != null) {
                message = request.getParameter("contact-message");
            }

            if (!email.isEmpty() && !name.isEmpty() && !message.isEmpty()) {

                if (SendMail.run("clement.guillory@gmail.com", name, message, email)) {
                    request.setAttribute("success", "<p>Votre message a bien été envoyé</p>");
                } else {
                    request.setAttribute("error", "<p>Echec de l'envoi du mail</p>");
                }
            } else {
                request.setAttribute("error", "<p>Votre message n'a pas pu être envoyé</p>");
            }
        }

        // home
        if (request.getParameter("section") == null) {
            feedHome(products, request);
        }

        // page
        if ("page".equals(request.getParameter("section"))) {
            url = "/WEB-INF/page.jsp";
            // get page title
            if (request.getParameter("title") != null) {
                String title = request.getParameter("title");
                title = new String(title.getBytes(), "UTF-8");
                request.setAttribute("title", title);
            }
        }

        // products search
        if ("products".equals(request.getParameter("section"))) {
            try {
                url = "/WEB-INF/products.jsp";

                // get current page
                if (request.getParameter("page") != null) {
                    try {
                        int page = Integer.valueOf(request.getParameter("page"));
                        request.setAttribute("page", page);
                    } catch (NumberFormatException ex) {
                        request.setAttribute("error", "<h4>Erreur</h4><p>Page inexistante</p>");
                    }
                } else {
                    request.setAttribute("page", 1);
                }

                // get page title
                if (request.getParameter("title") != null) {
                    String title = request.getParameter("title");
                    title = new String(title.getBytes(), "UTF-8");
                    request.setAttribute("title", title);
                } else {
                    request.setAttribute("title", "Ouvrages");
                }

                // get listSearch pagination
                int next = 9;
                try {
                    if (request.getParameter("next") != null) {
                        next = Integer.valueOf(request.getParameter("next"));
                    }
                    request.setAttribute("next", next);
                    Integer pagination = products.getSearchPagination(request.getParameterMap(), next);
                    request.setAttribute("pagination", pagination);
                } catch (NumberFormatException ex) {
                    request.setAttribute("error", "<h4>Erreur</h4><p>Page inexistante</p>");
                }

                //listSearch : feed search list
                try {
                    Collection<Product> searchedProducts = products.listSearch(request.getParameterMap(), next);
                    request.setAttribute("searchedProducts", searchedProducts);
                } catch (NumberFormatException ex) {
                    request.setAttribute("error", "<h4>Erreur</h4><p>Page inexistante</p>");
                }

                // themes list
                Collection<Theme> themes = products.listThemes();
                request.setAttribute("themes", themes);

                // tags list
                Collection<Tag> tags = products.listTags();
                request.setAttribute("tags", tags);

                // labels list
                Collection<Label> labels = products.listLabels();
                request.setAttribute("labels", labels);
            } catch (SQLException ex) {
                request.setAttribute("error", "<h4>Erreur</h4><p>Impossible de se connecter à la base de données</p>");
            }
        }

        // product
        if ("product".equals(request.getParameter("section"))) {
            url = "/WEB-INF/product.jsp";
            beanComment bc = null;
            beanProduct bean = null;
            int next = 5;
            int page = 0;

            Product product = (Product) request.getAttribute("product");
            if (product == null) {
                try {
                    bean = new beanProduct();
                    bc = new beanComment();
                    int id = Integer.valueOf(request.getParameter("id"));
                    request.setAttribute("product", bean.find(id));

                    if (request.getParameter("page") != null) {
                        page = Integer.valueOf(request.getParameter("page"));
                        request.setAttribute("page", page);
                    } else {
                        request.setAttribute("page", 1);
                    }
                    request.setAttribute("next", next);

                    Integer pagination = bc.getCommentPagination(id, 0, next);
                    request.setAttribute("pagination", pagination);
                    request.setAttribute("commentsByProd", bc.commentByPages(page, id, 0, next));
                } catch (SQLException ex) {
                    request.setAttribute("error", "<h4>Erreur</h4><p>Impossible de se connecter à la base de données</p>");
                }
            }

            if (request.getParameter("confirm") != null) {
                try {
                    beanCustomer bcu = new beanCustomer();
                    Comment c = new Comment();
                    c.setComment(new String(request.getParameter("message").getBytes(), "UTF-8"));

                    if (c.getComment().trim().isEmpty()) {
                        request.setAttribute("error", "<p>Le commentaire ne peut être vide</p>");
                    } else {
                        c.setProduct((Product) request.getAttribute("product"));
                        c.setRating(Byte.valueOf(request.getParameter("ratingOptions")));
                        c.setCustomer(bcu.find(ckLogged.getValue()));
                        bc.create(c);
                        request.setAttribute("product", bean.find(c.getProduct().getId()));
                        request.setAttribute("commentsByProd", bc.commentByPages(page, c.getProduct().getId(), 0, next));
                        request.setAttribute("success", "<p>Merci pour votre commentaire</p>");
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            // themes list
            Collection<Theme> themes = products.listThemes();
            request.setAttribute("themes", themes);

            // tags list
            Collection<Tag> tags = products.listTags();
            request.setAttribute("tags", tags);

            // labels list
            Collection<Label> labels = products.listLabels();
            request.setAttribute("labels", labels);

        }

        // login
        if ("login".equals(request.getParameter("section"))) {
            Customer customer = (Customer) session.getAttribute("login");
            beanCustomer bc = null;
            try {
                bc = new beanCustomer();
            } catch (SQLException ex) {
                request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontrée</p>");
            }
            if (customer == null) {
                customer = new Customer();
                session.setAttribute("login", customer);
            }

            String pseudo = request.getParameter("pseudo").trim();
            String password = request.getParameter("password").trim();

            if (pseudo != null && password != null) {
                if (!pseudo.isEmpty() && !password.isEmpty()) {
                    boolean logMatch = false;
                    try {
                        logMatch = bc.login(pseudo, password);
                    } catch (SQLException ex) {
                        request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontrée</p>");
                    } catch (Exception ex) {
                        request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontrée</p>");
                    }
                    if (logMatch) {
                        Cookie ck = new Cookie("logged", pseudo);
                        response.addCookie(ck);
                        session.setAttribute("logged", true);
                        url = "/WEB-INF/customerTasks.jsp";
                        try {
                            customer.setId(bc.getIdCustomer(pseudo, password));
                            customer = bc.find(customer.getId()); 
                        } catch (Exception ex) {
                            request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontrée</p>");
                        }
                        session.setAttribute("customer", customer);
                    } else {
                        feedHome(products, request);
                        request.setAttribute("error", "<h4>Erreur</h4><p>La connexion a échouée</p>");
                    }
                }
            }
        }

        if ("subscribe".equals(request.getParameter("section"))) {
            url = "/WEB-INF/subscribe.jsp";
            ckLogged = getCookie(request.getCookies(), "logged");
            if (ckLogged != null && (boolean) session.getAttribute("logged") == true) {
                // edit personal information
                Customer customer = (Customer) session.getAttribute("customer");
                beanCustomer bc = null;
                try {
                    bc = new beanCustomer();
                } catch (SQLException ex) {
                    request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontré sur la base de donnée</p>");
                }
                try {
                    customer = bc.find(customer.getId());
                } catch (SQLException ex) {
                    request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontré sur la base de donnée</p>");
                } catch (Exception ex) {
                    request.setAttribute("error", ex.getMessage());
                }

                session.setAttribute("customer", customer);
                if (request.getParameter("confirm") != null) {
                    Map<String, String[]> subscribe = request.getParameterMap();

                    try {
                        if (bc.subscribe(subscribe, customer.getIdUser(), customer.getId())) {
                            customer = bc.getCustomer();
                            if (!ckLogged.getValue().equals(customer.getPseudo())) {
                                if (bc.checkCustomer(customer.getPseudo())) {
                                    bc.update(customer);
                                    session.setAttribute("customer", customer);
                                    url = "/WEB-INF/customerTasks.jsp";
                                    ckLogged.setValue(customer.getPseudo());
                                } else {
                                    request.setAttribute("error", "<h4>Erreur</h4><p>Ce pseudo est déjà  utilisé</p>");
                                }
                            } else {
                                bc.update(customer);
                                session.setAttribute("customer", customer);
                                url = "/WEB-INF/customerTasks.jsp";
                                ckLogged.setValue(customer.getPseudo());
                                request.setAttribute("success", "<p>Vos informations personnelles ont été mises à jour</p>");
                            }

                        } else {
                            request.setAttribute("error", "<h4>Erreur</h4><p>La confirmation du mot de passe a échouée</p>");
                        }
                    } catch (ParseException ex) {
                        request.setAttribute("error", "<h4>Erreur</h4><p>La date saisie n'est pas valide</p>");
                    } catch (SQLException ex) {
                        request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontré sur la base de donnée</p>");
                    } catch (Exception ex) {
                        request.setAttribute("error", ex.getMessage());
                    }
                }
            } else { // subscription
                if (request.getParameter("subscribe") != null) {

                    try {
                        Customer customer = new Customer();
                        session.setAttribute("customer", customer);
                        beanCustomer bc = new beanCustomer();
                        Map subscribe = request.getParameterMap();

                        if (bc.subscribe(subscribe)) {
                            request.setAttribute("customer", bc.getCustomer());
                            customer = bc.getCustomer();
                            if (bc.checkCustomer(customer.getPseudo())) {
                                if (bc.create(customer)) {
                                    logged = bc.login(customer.getPseudo(), customer.getPassword());
                                    session.setAttribute("customer", customer);
                                    if (logged) {
                                        Cookie ck = new Cookie("logged", customer.getPseudo());
                                        response.addCookie(ck);
                                        session.setAttribute("logged", logged);
                                        customer.setId(bc.getIdCustomer(customer.getPseudo(), customer.getPassword()));
                                        session.setAttribute("customer", customer);
                                        url = "/WEB-INF/customerTasks.jsp";
                                    }
                                }
                            } else {
                                keepForm(request, session);
                                request.setAttribute("error", "<h4>Erreur</h4><p>Ce pseudo est déjÃ  utilisé</p>");
                            }
                        } else {
                            keepForm(request, session);
                            request.setAttribute("error", "<h4>Erreur</h4><p>La confirmation du mot de passe a échouée</p>");
                        }
                    } catch (ParseException ex) {
                        keepForm(request, session);
                        request.setAttribute("error", "<h4>Erreur</h4><p>La date saisie n'est pas valide</p>");
                    } catch (SQLException ex) {
                        request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontré sur la base de donnée</p>");
                    } catch (Exception ex) {
                        keepForm(request, session);
                        request.setAttribute("error", ex.getMessage());
                    }
                }
            }
        }

        if ("commentEdit".equals(request.getParameter("section"))) {
            int id = Integer.valueOf(request.getParameter("id"));
            Comment c = new Comment();
            beanComment com = null;
            try {
                com = new beanComment();
                c = com.find(id);
            } catch (SQLException ex) {
                request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontrée sur la base de données</p>");
            }

            request.setAttribute("editComment", c);
            url = "/WEB-INF/commentEdit.jsp";
            if (request.getParameter("confirm") != null) {
                c.setComment(new String(request.getParameter("message").getBytes(), "UTF-8"));
                if (c.getComment().trim().isEmpty()) {
                    request.setAttribute("error", "<p>Le commentaire ne peut être vide</p>");
                } else {
                    c.setRating(Byte.valueOf(request.getParameter("ratingOptions")));
                    try {
                        com.update(c);
                        request.setAttribute("editComment", c);
                        request.setAttribute("success", "<p>Votre commentaire a été modifié</p>");
                    } catch (SQLException ex) {
                        request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontrée sur la base de données</p>");
                        ex.printStackTrace();
                    }
                }
            }
        }

        if ("comments".equals(request.getParameter("section"))) {
            int next = 5;
            int page = 0;
            ckLogged = getCookie(request.getCookies(), "logged");
            if (ckLogged != null && (boolean) session.getAttribute("logged") == true && (Customer) session.getAttribute("customer") != null) {
                beanComment bcm = (beanComment) session.getAttribute("userComments");
                Customer c = (Customer) session.getAttribute("customer");
                beanCustomer bc = null;
                try {
                    bc = new beanCustomer();
                } catch (SQLException ex) {
                    request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontrée sur la base de données</p>");
                }

                Collection<Comment> cbc = null;
                if (bcm == null) {
                    try {
                        bcm = new beanComment();
                        c = bc.find(c.getId());

                        if (request.getParameter("page") != null) {
                            page = Integer.valueOf(request.getParameter("page"));
                            request.setAttribute("page", page);
                        } else {
                            request.setAttribute("page", 1);
                        }
                        request.setAttribute("next", next);
                        
                        Integer pagination = bcm.getCommentPagination(0, c.getIdUser(), next);
                        request.setAttribute("pagination", pagination);

                        cbc = bcm.commentByPages(page, 0, c.getIdUser(), next);
                        request.setAttribute("listUserComments", cbc);
                        url = "WEB-INF/comments.jsp";
                        if (cbc.isEmpty()) {
                            request.setAttribute("warning", "<p>Vous n'avez posté aucun message.</p>");
                        }
                    } catch (Exception ex) {
                        request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontrée sur la base de données</p>");
                    }
                }
                if ("remove".equals(request.getParameter("action"))) {

                    try {
                        Comment com = new Comment();
                        int id = Integer.valueOf(request.getParameter("id"));
                        com.setId(id);
                        bcm.delete(com);
                        if (request.getParameter("page") != null) {
                            page = Integer.valueOf(request.getParameter("page"));
                            request.setAttribute("page", page);
                        } else {
                            request.setAttribute("page", 1);
                        }
                        request.setAttribute("next", next);
                        
                        Integer pagination = bcm.getCommentPagination(0, c.getIdUser(), next);
                        request.setAttribute("pagination", pagination);

                        cbc = bcm.commentByPages(page, 0, c.getIdUser(), next);
                        request.setAttribute("listUserComments", cbc);
                        if (cbc.isEmpty()) {
                            request.setAttribute("warning", "<p>Vous n'avez posté aucun message.</p>");
                        }
                    } catch (Exception ex) {
                        request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontrée sur la base de données</p>");
                        ex.printStackTrace();
                    }
                    url = "WEB-INF/comments.jsp";
                }
            } else {
                response.sendRedirect("controller");
            }
        }

        if ("orders".equals(request.getParameter("section"))) {
            ckLogged = getCookie(request.getCookies(), "logged");
            if (ckLogged != null && (boolean) session.getAttribute("logged") == true && (Customer) session.getAttribute("customer") != null) {
                Order orders = (Order) session.getAttribute("orders");
                Customer customer = (Customer) session.getAttribute("customer");

                if (orders == null) {
                    Collection<Order> co = null;
                    try {
                        beanOrders bo = new beanOrders();
                        co = bo.findAll(customer.getId());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    session.setAttribute("listOrders", co);
                }
                url = "/WEB-INF/orders.jsp";
            } else {
                // TODO return message on front view : connection required                
                response.sendRedirect("controller");
            }
        }

        if ("orderdetail".equals(request.getParameter("section"))) {
            ckLogged = getCookie(request.getCookies(), "logged");
            if (ckLogged != null && (boolean) session.getAttribute("logged") == true) {
                ArrayList<Order> orderLine = new ArrayList();
                beanOrders bo = new beanOrders();
                int id = Integer.valueOf(request.getParameter("id"));
                try {
                    orderLine = bo.orderLine(id);
                    orderLine = bo.getDeliveryDetail(orderLine);
                } catch (SQLException ex) {
                    request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontrée sur la base de données</p>");
                }
                session.setAttribute("orderLine", orderLine);

                try {
                    session.setAttribute("orderDetail", bo.find(id));
                } catch (Exception ex) {
                    request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontrée sur la base de données</p>");
                }
                url = "/WEB-INF/orderdetail.jsp";
            } else {
                // TODO return message on front view : connection required                
                response.sendRedirect("controller");
            }
        }

        // accès à l'espace perso
        if ("customerTasks".equals(request.getParameter("section"))) {
            ckLogged = getCookie(request.getCookies(), "logged");
            if (ckLogged != null && (boolean) session.getAttribute("logged") == true) {
                url = "/WEB-INF/customerTasks.jsp";
            } else {
                // TODO return message on front view : connection required
                response.sendRedirect("controller");
            }
        }

        // default : for all pages => users list
        beanCustomer users = (beanCustomer) request.getAttribute("users");
        if (users == null) {
            try {
                users = new beanCustomer();
            } catch (SQLException ex) {
                request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontrée sur la base de données</p>");
            }
            request.setAttribute("users", users);
        }

        try {
            Collection<Customer> lastUsers = users.listUsers();
            request.setAttribute("lastUsers", lastUsers);
        } catch (SQLException ex) {
            request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontrée sur la base de données</p>");
        }

        // default : for all pages => last comments
        beanComment comments = (beanComment) request.getAttribute("comments");
        if (comments == null) {
            try {
                comments = new beanComment();
                request.setAttribute("comments", comments);
            } catch (SQLException ex) {
                request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontrée sur la base de données</p>");
            }
        }

        try {
            Collection<Comment> lastComments = comments.listComments();
            request.setAttribute("lastComments", lastComments);
        } catch (SQLException ex) {
            request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontrée sur la base de données</p>");
        }

        // disconnect
        if ("disconnect".equals(request.getParameter("disconnect"))) {
            logged = false;
            session.setAttribute("logged", logged);
            session.setAttribute("customer", null);
            ckLogged = getCookie(request.getCookies(), "logged");
            if (ckLogged != null) {
                ckLogged.setValue("");
                ckLogged.setMaxAge(0);
                response.addCookie(ckLogged);
            }
            request.setAttribute("error", "<h4>Déconnexion</h4><p>Vous êtes déconnecté</p>");
        }

        // Cart
        if ("cart".equals(request.getParameter("mode"))) {

            cart = (beanCart) session.getAttribute("cart");
            if (cart == null) {
                cart = new beanCart();
                session.setAttribute("cart", cart);
            }

            bProduct = (beanProduct) session.getAttribute("product");
            try {
                bProduct = cart.getP(Integer.valueOf(request.getParameter("id")));
                bProduct.setId(Integer.valueOf(request.getParameter("id")));

            } catch (SQLException ex) {
                request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontrée sur la base de données</p>");
            }

            bProduct.setQuantity(bProduct.getQuantity() + 1);
            bProduct.setUnitPrice(bProduct.calcUnitPrice());
            bProduct.setPrice(bProduct.calcTotalPrice());
            cart.addP(bProduct);
            size = cart.nbItem();
            total = cart.total(bProduct.getPrice());

            taxes = cart.taxes(bProduct.calcTax());
            discounts = cart.discounts(bProduct.calcDiscount());
            subtotal = cart.subtotal(total, taxes, discounts);

            session.setAttribute("product", bProduct);
            session.setAttribute("taxes", taxes);
            session.setAttribute("discounts", discounts);
            session.setAttribute("subtotal", subtotal);
            session.setAttribute("total", total);
            session.setAttribute("nbItem", size);
            session.setAttribute("cart", cart);
            session.setAttribute("pList", cart.getpList());

            request.setAttribute("success", "<p>Votre panier a été mis à jour</p>");
        
        }

        if ("modifyModCart".equals(request.getParameter("mode"))) {
            int newQty = 0;

            cart = (beanCart) session.getAttribute("cart");
            if (cart == null) {
                cart = new beanCart();
                session.setAttribute("cart", cart);
            }
            bProduct = (beanProduct) session.getAttribute("product");
            try {
                if (bProduct == null) {
                    if (request.getParameter("id") != null) {
                        bProduct = cart.getP(Integer.valueOf(request.getParameter("id")));

                    } else {
                        bProduct = new beanProduct();
                    }
                    session.setAttribute("product", bProduct);
                }
            } catch (SQLException ex) {
                request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontrée sur la base de données</p>");
            }

            if (request.getParameter("nbModal" + bProduct.getId()) != null) {

                while (newQty == 0) {
                    for (beanProduct bp : cart.getpList()) {
                        newQty = Integer.valueOf(request.getParameter("nbModal" + bp.getId()));
                        if (newQty == 0) {
                            total = (float) session.getAttribute("total");
                            total = cart.round(total - bp.getPrice(), 2);
                            cart.setTotal(total);
                            if (total == 0) {
                                taxes = 0;
                                discounts = 0;
                                subtotal = 0;
                                size = 0;
                                cart.setTaxes(0);
                                cart.setDiscounts(0);
                                cart.setSubtotal(0);
                                cart.setTotal(0);
                                newQty = -1;
                            } else {
                                taxes = (float) session.getAttribute("taxes");
                                taxes = cart.round(taxes - bp.calcTax(), 2);
                                discounts = (float) session.getAttribute("discounts");
                                discounts = cart.round(discounts - bp.calcDiscount(), 2);
                                cart.setTaxes(taxes);
                                cart.setDiscounts(discounts);
                                subtotal = (float) session.getAttribute("subtotal");
                                subtotal = cart.subtotal(total, taxes, discounts);
                                cart.setSubtotal(subtotal);
                            }
                            cart.remove(bp);
                            size = cart.nbItem();

                            session.setAttribute("product", bProduct);
                            session.setAttribute("taxes", taxes);
                            session.setAttribute("discounts", discounts);
                            session.setAttribute("subtotal", subtotal);
                            session.setAttribute("total", total);
                            session.setAttribute("nbItem", size);
                            session.setAttribute("cart", cart);

                            break;
                        }
                    }
                }

                for (beanProduct bp : cart.getpList()) {
                    if (bp.getQuantity() != Integer.valueOf(request.getParameter("nbModal" + bp.getId()))) {
                        newQty = Integer.valueOf(request.getParameter("nbModal" + bp.getId()));

                        if (bp.getQuantity() < newQty) {
                            total = cart.total(bp.getUnitPrice() * (newQty - bp.getQuantity()));
                            taxes = cart.taxes(bp.calcTax() * (newQty - bp.getQuantity()));
                            discounts = cart.discounts(bp.calcDiscount() * (newQty - bp.getQuantity()));
                            subtotal = cart.subtotal(total, taxes, discounts);
                            session.setAttribute("taxes", taxes);
                            session.setAttribute("discounts", discounts);
                            session.setAttribute("subtotal", subtotal);
                            session.setAttribute("total", total);
                            session.setAttribute("cart", cart);
                        }
                        if (bp.getQuantity() > newQty && newQty != 0) {
                            total = cart.decTotal(bp.getUnitPrice() * (bp.getQuantity() - newQty));
                            taxes = cart.decTaxes(bp.calcTax() * (bp.getQuantity() - newQty));
                            discounts = cart.decDiscounts(bp.calcDiscount() * (bp.getQuantity() - newQty));
                            subtotal = cart.subtotal(total, taxes, discounts);
                            session.setAttribute("taxes", taxes);
                            session.setAttribute("discounts", discounts);
                            session.setAttribute("subtotal", subtotal);
                            session.setAttribute("total", total);
                            session.setAttribute("cart", cart);
                        }
                        if (newQty != 0) {
                            bp.setQuantity(newQty);
                            bp.setPrice(bp.getUnitPrice() * bp.getQuantity());
                            session.setAttribute("product", bp);
                        }
                    }
                }

            }
            
            request.setAttribute("success", "<p>Votre panier a été mis à jour</p>");
        }

        if ("displayCart".equals(request.getParameter("section"))) {
            url = "/WEB-INF/cart.jsp";
            cart = (beanCart) session.getAttribute("cart");
            if (cart == null) {
                cart = new beanCart();
                session.setAttribute("cart", cart);
            }
            bProduct = (beanProduct) session.getAttribute("product");
            try {
                if (bProduct == null) {
                    if (request.getParameter("id") != null) {
                        bProduct = cart.getP(Integer.valueOf(request.getParameter("id")));

                    } else {
                        bProduct = new beanProduct();
                    }
                    session.setAttribute("product", bProduct);
                }
            } catch (SQLException ex) {
                request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontrée sur la base de données</p>");
            }
            int newQty = 0;

            if (request.getParameter("emptyCart") != null) {
                total = 0;
                taxes = 0;
                discounts = 0;
                subtotal = 0;
                size = 0;
                cart.setTaxes(0);
                cart.setDiscounts(0);
                cart.setSubtotal(0);
                cart.setTotal(0);
                newQty = -1;

                cart.setpList(new ArrayList());
                size = 0;

                session.setAttribute("product", bProduct);
                session.setAttribute("taxes", taxes);
                session.setAttribute("discounts", discounts);
                session.setAttribute("subtotal", subtotal);
                session.setAttribute("total", total);
                session.setAttribute("nbItem", size);
                session.setAttribute("cart", cart);
            }

            if (request.getParameter("nb" + bProduct.getId()) != null || request.getParameter("refresh") != null) {
                while (newQty == 0) {
                    for (beanProduct bp : cart.getpList()) {
                        newQty = Integer.valueOf(request.getParameter("nb" + bp.getId()));
                        if (newQty == 0) {
                            total = (float) session.getAttribute("total");
                            total = cart.round(total - bp.getPrice(), 2);
                            cart.setTotal(total);
                            if (total == 0) {
                                taxes = 0;
                                discounts = 0;
                                subtotal = 0;
                                size = 0;
                                cart.setTaxes(0);
                                cart.setDiscounts(0);
                                cart.setSubtotal(0);
                                cart.setTotal(0);
                                newQty = -1;
                            } else {
                                taxes = (float) session.getAttribute("taxes");
                                taxes = cart.round(taxes - bp.calcTax(), 2);
                                discounts = (float) session.getAttribute("discounts");
                                discounts = cart.round(discounts - bp.calcDiscount(), 2);
                                cart.setTaxes(taxes);
                                cart.setDiscounts(discounts);
                                subtotal = (float) session.getAttribute("subtotal");
                                subtotal = cart.subtotal(total, taxes, discounts);
                                cart.setSubtotal(subtotal);
                            }
                            cart.remove(bp);
                            size = cart.nbItem();

                            session.setAttribute("product", bProduct);
                            session.setAttribute("taxes", taxes);
                            session.setAttribute("discounts", discounts);
                            session.setAttribute("subtotal", subtotal);
                            session.setAttribute("total", total);
                            session.setAttribute("nbItem", size);
                            session.setAttribute("cart", cart);

                            break;
                        }
                    }
                }

                for (beanProduct bp : cart.getpList()) {
                    if (bp.getQuantity() != Integer.valueOf(request.getParameter("nb" + bp.getId()))) {
                        newQty = Integer.valueOf(request.getParameter("nb" + bp.getId()));
                        if (bp.getQuantity() < newQty) {
                            total = cart.total(bp.getUnitPrice() * (newQty - bp.getQuantity()));
                            taxes = cart.taxes(bp.calcTax() * (newQty - bp.getQuantity()));
                            discounts = cart.discounts(bp.calcDiscount() * (newQty - bp.getQuantity()));
                            subtotal = cart.subtotal(total, taxes, discounts);
                            session.setAttribute("taxes", taxes);
                            session.setAttribute("discounts", discounts);
                            session.setAttribute("subtotal", subtotal);
                            session.setAttribute("total", total);
                            session.setAttribute("cart", cart);
                        }
                        if (bp.getQuantity() > newQty && newQty != 0) {
                            total = cart.decTotal(bp.getUnitPrice() * (bp.getQuantity() - newQty));
                            taxes = cart.decTaxes(bp.calcTax() * (bp.getQuantity() - newQty));
                            discounts = cart.decDiscounts(bp.calcDiscount() * (bp.getQuantity() - newQty));
                            subtotal = cart.subtotal(total, taxes, discounts);
                            session.setAttribute("taxes", taxes);
                            session.setAttribute("discounts", discounts);
                            session.setAttribute("subtotal", subtotal);
                            session.setAttribute("total", total);
                            session.setAttribute("cart", cart);
                        }
                        if (newQty != 0) {
                            bp.setQuantity(newQty);
                            bp.setPrice(bp.getUnitPrice() * bp.getQuantity());
                            session.setAttribute("product", bp);
                        }
                    }
                }

            }

            if ("remove".equals(request.getParameter("action"))) {

                bProduct = cart.get(Integer.valueOf(request.getParameter("id")));
                total = (float) session.getAttribute("total");
                total = cart.round(total - bProduct.getPrice(), 2);
                cart.setTotal(total);
                if (total == 0) {
                    taxes = 0;
                    discounts = 0;
                    subtotal = 0;
                    size = 0;
                    cart.setTaxes(0);
                    cart.setDiscounts(0);
                    cart.setSubtotal(0);
                    cart.setTotal(0);
                } else {
                    taxes = (float) session.getAttribute("taxes");
                    taxes = cart.round(taxes - bProduct.calcTax(), 2);
                    discounts = (float) session.getAttribute("discounts");
                    discounts = cart.round(discounts - bProduct.calcDiscount(), 2);
                    cart.setTaxes(taxes);
                    cart.setDiscounts(discounts);
                    subtotal = (float) session.getAttribute("subtotal");
                    subtotal = cart.subtotal(total, taxes, discounts);
                    cart.setSubtotal(subtotal);
                }
                cart.remove(bProduct);
                size = cart.nbItem();

                session.setAttribute("product", bProduct);
                session.setAttribute("taxes", taxes);
                session.setAttribute("discounts", discounts);
                session.setAttribute("subtotal", subtotal);
                session.setAttribute("total", total);
                session.setAttribute("nbItem", size);
                session.setAttribute("cart", cart);
            }

            ckLogged = getCookie(request.getCookies(), "logged");

            session.setAttribute("pList", cart.getpList());

        }

        if ("order".equals(request.getParameter("section"))) {
            url = "/WEB-INF/order.jsp";
            ckLogged = getCookie(request.getCookies(), "logged");
            if (ckLogged != null && (boolean) session.getAttribute("logged") == true) {
                url = "/WEB-INF/order.jsp";
                session.removeAttribute("errorOrder");
                beanCustomer bc = null;
                try {
                    bc = new beanCustomer();
                    Customer customer = bc.find(ckLogged.getValue());
                    customer.setPseudo(ckLogged.getValue());
                    session.setAttribute("lastName", customer.getLastName());
                    session.setAttribute("firstName", customer.getFirstName());
                    session.setAttribute("address", customer.getAddress());
                    session.setAttribute("zipcode", customer.getZipcode());
                    session.setAttribute("city", customer.getCity());

                    beanDeliveryAddress bda = new beanDeliveryAddress();
                    DeliveryAddress da = bda.find(customer.getId());
                    session.setAttribute("da", da);
                    session.setAttribute("daLastName", da.getLastName());
                    session.setAttribute("daFirstName", da.getFirstName());
                    session.setAttribute("daAddressLine1", da.getAddressLine1());
                    session.setAttribute("daAddressLine2", da.getAddressLine2());
                    session.setAttribute("daZipcode", da.getZipcode());
                    session.setAttribute("daCity", da.getCity());

                } catch (SQLException ex) {
                    request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontrée sur la base de données</p>");
                }

            } else {
                url = "/WEB-INF/cart.jsp";
                session.setAttribute("errorOrder", "<p class='alert alert-danger'>Vous devez être connecté</p>");
                response.sendRedirect(currentURL.replaceAll("order", "displayCart"));
            }

        }

        if ("deliveryAddresses".equals(request.getParameter("section"))) {
            url = "/WEB-INF/deliveryAddresses.jsp";
            ckLogged = getCookie(request.getCookies(), "logged");
            if (ckLogged != null && (boolean) session.getAttribute("logged") == true) {
                url = "/WEB-INF/deliveryAddresses.jsp";
                beanCustomer bc = null;
                DeliveryAddress da = new DeliveryAddress();
                beanDeliveryAddress bda = new beanDeliveryAddress();

                ArrayList<DeliveryAddress> daList;
                try {
                    bc = new beanCustomer();
                    Customer customer = bc.find(ckLogged.getValue());
                    customer.setPseudo(ckLogged.getValue());
                    session.setAttribute("lastName", customer.getLastName());
                    session.setAttribute("firstName", customer.getFirstName());
                    session.setAttribute("address", customer.getAddress());
                    session.setAttribute("zipcode", customer.getZipcode());
                    session.setAttribute("city", customer.getCity());

                    bda = new beanDeliveryAddress();
                    daList = bda.getDaList(customer.getId());
                    for (DeliveryAddress dd : daList) {
                        if (dd.getActive() == 1) {
                            da = dd;
                            break;
                        }
                    }
                    session.setAttribute("da", da);
                    session.setAttribute("daList", daList);

                    if (request.getParameter("addAddress") != null) {
                        boolean byDefault = true;
                        da = new DeliveryAddress();
                        da.setLastName(request.getParameter("lastName"));
                        da.setFirstName(request.getParameter("firstName"));
                        da.setAddressLine1(request.getParameter("addressLine1"));
                        da.setAddressLine2(request.getParameter("addressLine2"));
                        da.setDigicode(request.getParameter("digicode"));
                        da.setZipcode(request.getParameter("zipcode"));
                        da.setCity(request.getParameter("city"));
                        da.setPhone(request.getParameter("phone"));
                        if (request.getParameter("byDefault").equals("Oui")) {
                            byDefault = true;
                            da.setActive(1);
                        } else {
                            byDefault = false;
                            da.setActive(0);
                        }
                        bda = new beanDeliveryAddress();
                        bda.create(da, byDefault, customer.getId());
                        daList = bda.getDaList(customer.getId());
                        session.setAttribute("da", da);
                        session.setAttribute("daList", daList);
                    }
                    if (request.getParameter("editAddress") != null) {
                        boolean byDefault = true;
                        da = new DeliveryAddress();

                        da.setId(Integer.valueOf(request.getParameter("mid")));
                        da.setLastName(request.getParameter("mlastName"));
                        da.setFirstName(request.getParameter("mfirstName"));
                        da.setAddressLine1(request.getParameter("maddressLine1"));
                        da.setAddressLine2(request.getParameter("maddressLine2"));
                        da.setDigicode(request.getParameter("mdigicode"));
                        da.setZipcode(request.getParameter("mzipcode"));
                        da.setCity(request.getParameter("mcity"));
                        da.setPhone(request.getParameter("mphone"));
                        if (request.getParameter("mbyDefault").equals("Oui")) {
                            byDefault = true;
                            da.setActive(1);
                        } else {
                            byDefault = false;
                            da.setActive(0);
                        }

                        bda = new beanDeliveryAddress();
                        bda.update(da, byDefault, customer.getId());
                        daList = bda.getDaList(customer.getId());
                        session.setAttribute("da", da);
                        session.setAttribute("daList", daList);
                    }
                    if ("removeAddress".equals(request.getParameter("action"))) {
                        da = new DeliveryAddress();
                        bda = new beanDeliveryAddress();
                        daList = (ArrayList<DeliveryAddress>) session.getAttribute("daList");
                        for (DeliveryAddress addr : daList) {
                            if (addr.getId() == Integer.valueOf(request.getParameter("id"))) {
                                da = addr;
                                break;
                            }
                        }

                        bda.delete(da, customer.getId());
                        daList = bda.getDaList(customer.getId());
                        if (da.getActive() == 1) {
                            daList.get(0).setActive(1);
                            da = daList.get(0);
                        }
                        session.setAttribute("da", da);
                        session.setAttribute("daList", daList);
                    }
                    if (request.getParameter("refreshAddress") != null) {
                        da = (DeliveryAddress) session.getAttribute("da");
                        for (DeliveryAddress deliv : daList) {
                            if (request.getParameter("modifyRadio").equals("ra" + deliv.getId())) {
                                da.setActive(0);
                                bda.update(da, deliv);
                                da = deliv;
                                da.setActive(1);
                                break;
                            }
                        }
                        session.setAttribute("da", da);
                        session.setAttribute("daList", daList);
                    }

                } catch (SQLException ex) {
                    request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontrée sur la base de données</p>");
                }
            } else {
                response.sendRedirect(request.getRequestURI());
            }
        }
        
        if("pay".equals(request.getParameter("section"))){
            url = "/WEB-INF/pay.jsp";
            Order order = new Order();
            OrderLine ol = new OrderLine();
            beanOrder bo = new beanOrder();
            
            try {
                beanCustomer bc;
                bc = new beanCustomer();
                cart = (beanCart) session.getAttribute("cart");
                ArrayList<OrderLine> olList = new ArrayList();
                ArrayList<beanProduct> pList = cart.getpList();
                for(beanProduct bp : pList){
                    ol = new OrderLine(bp, bp.getQuantity());
                    olList.add(ol);
                }
                Customer customer = bc.find(ckLogged.getValue());
                DeliveryAddress da = (DeliveryAddress)session.getAttribute("da");
                order.setCustomer(customer);
                order.setDeliveryAddress(da);
                order.setOrderLines(olList);
                bo.create(order);
                
            } catch (SQLException ex) {
                request.setAttribute("error", "<h4>Erreur</h4><p>Une erreur a été rencontrée sur la base de données</p>");
            }

        }


        // redirect to url
        request.getRequestDispatcher(url).include(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Main controller";
    }// </editor-fold>

}
