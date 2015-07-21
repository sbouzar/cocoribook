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
                            <li><a href="controller?section=orders">Vos commandes</a></li>
                            <li class="active">Votre commande n°${orderDetail.orderNb}</li>
                        </ol>                      
                        <h1 class="page-header">Votre commande n°${orderDetail.orderNb}</h1>
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
                                    <div class="col-sm-5">
                                        <p class="text-left">
                                        <ul class="list-unstyled">
                                            <li>N°${orderDetail.orderNb}</li>
                                            <li>Commande effectuée le ${orderDetail.sOrderDate}</li>
                                            <li>État : ${orderDetail.status}</li>
                                        </ul>
                                        Adresse de livraison :
                                        <address>
                                            <strong>${orderDetail.deliveryAddress.lastName} ${orderDetail.deliveryAddress.firstName}</strong><br>
                                            ${orderDetail.deliveryAddress.addressLine1} ${orderDetail.deliveryAddress.addressLine2} <c:if test="${not empty orderDetail.deliveryAddress.digicode}">Digicode : ${orderDetail.deliveryAddress.digicode}</c:if><br>
                                            ${orderDetail.deliveryAddress.zipcode} ${orderDetail.deliveryAddress.city}<br>
                                        </address>
                                    </div>

                                    <div class="panel panel-body">
                                        
                                            <table class="table tab-pane">
                                                <tr>

                                                    <th>Article</th>
                                                    <th>Livré / Commandé</th>
                                                    <th>Détail livraison</th>
                                                </tr>
                                                <c:if test="${not empty orderLine}">
                                                    <c:forEach var="item" items="${orderLine}" varStatus="loop">
                                                        <tr>
                                                            <td><a href="controller?section=product&id=${item.product.id}" target="_blank">${item.product.title} ${item.product.subtitle}</a> (${item.product.publisher.name} - ${item.product.type})</td>
                                                            <td>${item.quantityDelivered} / ${item.quantityOrdered} </td>
                                                            <c:if test="${not empty item.delivery}">
                                                                <td>
                                                                <c:forEach var="deliverItem" items="${item.delivery}" varStatus="loop">
                                                                    N°${deliverItem.receipt} (${deliverItem.sDeliveryDate}) - Nombre d'article : ${deliverItem.deliveredQuantity}<br>
                                                                </c:forEach>
                                                                </td>
                                                            </c:if>
                                                            <c:if test="${empty item.delivery}"><td></td></c:if>
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