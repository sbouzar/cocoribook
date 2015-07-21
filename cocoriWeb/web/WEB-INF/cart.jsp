<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <%@include file="includes/head.jsp" %>
        <title>Panier | CocoriBook : librairie en ligne</title>
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
                            <li><a href="controller?section=products">Ouvrages</a></li>
                            <li class="active">Panier</li>
                        </ol>                      
                        <h1 class="page-header">Panier</h1>
                    </div>
                </div>
            </div>

            <div id="content" class="bg-gray-new">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <main role="main">
                                <c:if test="${not empty error}">
                                    <div class="alert alert-danger" role="alert">${error}</div>
                                </c:if>
                                <c:if test="${not empty success}">
                                    <div class="alert alert-success" role="alert">${success}</div>
                                </c:if>
                                <p class="text-right"><a href="controller?section=products" class="btn btn-default"><i class="fa fa-chevron-left"></i>&nbsp;Continuer vos achats</a>
                                <form action="controller?section=displayCart" method="post">
                                    <div>
                                        <c:if test="${!logged}">
                                            <c:if test="${not empty errorOrder}">${errorOrder}</c:if>
                                        </c:if>
                                    </div>
                                    <table class="table table-striped">
                                        <caption>Contenu de votre panier</caption>
                                        <thead class="bg-info">
                                            <tr class="cart-table">
                                                <th scope="col"><span>Ouvrage</span></th>
                                                <th scope="col">Prix</th>
                                                <th scope="col" class="qty">Quantité</th>
                                                <th scope="col">Total</th>
                                                <th scope="col">Supprimer</th>
                                            </tr>
                                        </thead>
                                        <tfoot>
                                            <tr>
                                                <td colspan="5" class="text-right">
                                                    <strong>Total HT: <span class="txt-success">${subtotal} &euro;</span></strong>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="5" class="text-right">
                                                    <strong>Taxes : <span class="txt-success">${taxes} &euro;</span></strong>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="5" class="text-right">
                                                    <strong>Total TTC: <span class="txt-success">${total} &euro;</span></strong>
                                                </td>
                                            </tr>
                                        </tfoot>
                                        <tbody>
                                            <c:forEach var="product" items="${pList}" varStatus="loop">
                                                <tr class="cart-table">
                                                    <td>${product.title}</td>
                                                    <td>${product.unitPrice} &euro;</td>
                                                    <td><input type="number" name="nb${product.id}" min="0" value="${product.quantity}"></td>
                                                    <td>${product.price} &euro;</td>
                                                    <td><a href="controller?section=displayCart&id=${product.id}&action=remove"><i class="glyphicon glyphicon-remove "></i></a></td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>

                                    </table> 
                                    <div class="text-right">
                                        <button type="submit" class="btn btn-default" name="refresh"><i class="fa fa-refresh"></i>&nbsp;Actualiser le panier</button>
                                        <button type="submit" class="btn btn-default" name="emptyCart"><i class="fa fa-credit-card"></i>&nbsp;Vider le panier</button>
                                        <a href="controller?section=order" class="btn btn-success"><i class="fa fa-credit-card"></i>&nbsp;Passer la commande</a>
                                    </div>
                                </form>
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