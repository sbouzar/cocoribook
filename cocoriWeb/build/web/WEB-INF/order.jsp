
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <%@include file="includes/head.jsp" %>
        <title>Commande | CocoriBook : librairie en ligne</title>
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
                            <li><a href="controller?section=displayCart">Panier</a></li>
                            <li class="active">Commande</li>
                        </ol>                      
                        <h1 class="page-header">Votre commande</h1>
                    </div>
                </div>
            </div>

            <div id="content" class="bg-gray-new">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <main role="main">
                                <p class="text-right"><a href="controller?section=displayCart" class="btn btn-default"><i class="fa fa-chevron-left"></i>&nbsp;Modifier votre panier</a>
                                <form action="controller?section=pay" method="post">
                                    <table class="table table-striped">
                                        <caption>Contenu de votre commande</caption>
                                        <thead class="bg-info">
                                            <tr class="cart-table">
                                                <th scope="col"><span>Ouvrage</span></th>
                                                <th scope="col">Prix</th>
                                                <th scope="col" class="qty">Quantité</th>
                                                <th scope="col">Total</th>
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
                                                    <td>${product.quantity}</td>
                                                    <td>${product.price} &euro;</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>

                                    <div>
                                        <div class="row">
                                            <div class="col-sm-6">
                                                <strong><span class="txt-success">Votre adresse de facturation</span></strong>
                                                <address>
                                                    <strong>${firstName} ${lastName}</strong><br>
                                                    ${address}<br>
                                                    ${zipcode} ${city}<br>
                                                </address>
                                            </div>
                                            <div class="text-right col-sm-6">
                                                <strong><span class="txt-success">Votre adresse de livraison</span></strong>
                                                <address>
                                                    <strong>${daFirstName} ${daLastName}</strong><br>
                                                    ${daAddressLine1}<br>
                                                    ${daAddressLine2}<br>
                                                    ${daZipcode} ${daCity}<br>
                                                </address>
                                            </div>
                                        </div>

                                    </div>

                                    <div class="text-right">
                                        <a href="controller?section=deliveryAddresses" class="btn btn-default"><i class="fa fa-envelope-o"></i>&nbsp;Modifier votre adresse de livraison</a>
                                        <button type="submit" class="btn btn-success"><i class="fa fa-credit-card"></i>&nbsp;Procéder au paiement</button>
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
