<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <%@include file="includes/head.jsp" %>
        <title>Votre Espace | CocoriBook : librairie en ligne</title>
        <meta name="description" content="cocoribook.fr : votre librairie en ligne. Retrouvez un choix de plus 10 000 livres à commander.">
        <meta name="keywords" content="librairie, librairie en ligne, livres, livres neufs, vente de livres en ligne, vente de livres, achats de livres">
    </head>
    <body id="top">
        <%@include file="includes/modals/cartModal.jsp" %>
        <%@include file="includes/modals/contactModal.jsp" %>
        <%@include file="includes/modals/loginModal.jsp" %>
        <div id="wrapper">
            <%@include file="includes/header.jsp" %>
            <div class="page-heading">
                <div> 
                    <div class="container">
                        <ol class="breadcrumb">
                            <li><a href="controller">Accueil</a></li>
                            <li><a href="controller?section=customerTasks">Votre espace</a></li>
                            <li class="active">Vos commandes</li>
                        </ol>                      
                        <h1 class="page-header">Vos commandes</h1>
                    </div>
                </div>
            </div>

            <div id="content" class="bg-gray-new">
                <div class="container">
                    <div class="row">
                        <div class="col-md-24">
                            <main role="main">
                                <c:if test="${not empty error}">
                                    <div class="alert alert-danger" role="alert">${error}</div>
                                </c:if>
                                <c:if test="${not empty success}">
                                    <div class="alert alert-success" role="alert">${success}</div>
                                </c:if>
                                <div class="panel panel-default">  
                                    <div class="table-responsive">
                                    <table class="table">
                                        <tr>
                                            <th>N°</th>
                                            <th>Effectuée le</th>                                            
                                            <th>État</th>
                                            <th>Destinataire</th>
                                            <th>Adresse de destination</th>
                                            <th></th>
                                        </tr>
                                        <c:if test="${not empty listOrders}">
                                            <c:forEach var="item" items="${listOrders}" varStatus="loop">
                                                <tr>
                                                    <td><a href="controller?section=orderdetail&id=${item.id}">${item.orderNb}</a></td>
                                                    <td>${item.sOrderDate}</td>                                                    
                                                    <td>${item.status}</td>
                                                    <td>${item.deliveryAddress.firstName} ${item.deliveryAddress.lastName}</td>
                                                    <td>${item.deliveryAddress.addressLine1} ${item.deliveryAddress.addressLine2}</td> 
                                                    <td>${item.deliveryAddress.zipcode} ${item.deliveryAddress.city}</td>
                                                </tr>
                                            </c:forEach>
                                        </c:if>
                                    </table>
                                    </div>
                                </div>
                            </main>
                        </div>
                    </div>
                </div>
            </div>

            <%@include file="includes/footer.jsp" %>
        </div>
        <%@include file="includes/scripts.jsp" %>
    </body>
</html>