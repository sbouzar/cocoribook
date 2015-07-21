<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <%@include file="includes/head.jsp" %>
        <title>Votre compte | CocoriBook : librairie en ligne</title>
        <meta name="description" content="cocoribook.fr : votre librairie en ligne. Retrouvez un choix de plus 10 000 livres à commander.">
        <meta name="keywords" content="librairie, librairie en ligne, livres, livres neufs, vente de livres en ligne, vente de livres, achats de livres">
    </head>
    <body id="top">
        <%@include file="includes/modals/cartModal.jsp" %>
        <%@include file="includes/modals/contactModal.jsp" %>
        <%@include file="includes/modals/loginModal.jsp" %>
        <%@include file="includes/modals/deliveryAddressModal.jsp" %>
        <%@include file="includes/modals/daModifyModal.jsp" %>
        <div id="wrapper">
            <%@include file="includes/header.jsp" %>
            <div class="page-heading">
                <div>
                    <div class="container">
                        <ol class="breadcrumb">
                            <li><a href="controller">Accueil</a></li>
                            <li><a href="controller?section=customerTasks">Votre espace</a></li>
                            <li class="active">Vos adresses de livraison</li>
                        </ol>                      
                        <h1 class="page-header">Vos adresses de livraison</h1>
                    </div>
                </div>
            </div>

            <div id="content" class="bg-gray-new">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <main role="main">
                                <c:if test="${not empty error}">
                                    <div class="alert alert-danger  alert-dismissible" role="alert">
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                        ${error}
                                    </div>
                                </c:if>
                                <c:if test="${not empty success}">
                                    <div class="alert alert-success  alert-dismissible" role="alert">
                                        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                                        ${success}
                                    </div>
                                </c:if>
                                <p class="text-right"><a href="controller" class="btn btn-default"><i class="fa fa-chevron-left"></i>&nbsp;Continuer vos achats</a>
                                <form id="daForm" action="controller?section=deliveryAddresses" method="post">
                                    
                                    <table class="table table-striped">
                                        <caption>Vos adresses</caption>
                                        <thead class="bg-info">
                                            <tr class="cart-table">
                                                <th class="hidden">Id</th>
                                                <th scope="col">Nom</th>
                                                <th scope="col">Prénom</th>
                                                <th scope="col">Adresse</th>
                                                <th scope="col">Digicode</th>
                                                <th scope="col">CP</th>
                                                <th scope="col">Ville</th>
                                                <th scope="col">Téléphone</th>
                                                <th scope="col">Adresse par défaut</th>
                                                <th scope="col">Modifier</th>
                                                <th scope="col">Supprimer</th>
                                            </tr>
                                        </thead>
                                        <tfoot>

                                        </tfoot>
                                        <tbody id="daBody">
                                            <c:forEach var="deliveryAddress" items="${daList}" varStatus="loop">
                                            <!--<input type='hidden' name="daId" value='${deliveryAddress.id}'>-->
                                                <tr class="cart-table">
                                                    <td class="hidden">${deliveryAddress.id}</td>
                                                    <td>${deliveryAddress.lastName}</td>
                                                    <td>${deliveryAddress.firstName}</td>
                                                    <td>${deliveryAddress.addressLine1} <span>${deliveryAddress.addressLine2}</span></td>
                                                    <td>${deliveryAddress.digicode}</td>
                                                    <td>${deliveryAddress.zipcode}</td>
                                                    <td>${deliveryAddress.city}</td>
                                                    <td>${deliveryAddress.phone}</td>
                                                    <td><input type="radio" name="modifyRadio" value="ra${deliveryAddress.id}" <c:if test="${deliveryAddress.active==1}">checked id="checkedAddress"</c:if>/></td>
                                                    <td><a href="#" name="editDa" value="${deliveryAddress.id}" class="modifyDelivAddress" data-toggle="modal" data-target="#daModifyModal" ><i class="fa fa-pencil"></i></a></td>
                                                    <td><a href="controller?section=deliveryAddresses&id=${deliveryAddress.id}&action=removeAddress"><i class="glyphicon glyphicon-remove "></i></a></td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>

                                    </table> 
                                    <div class="text-right">

                                        <a href="#" data-toggle="modal" data-target="#deliveryAddressModal" class="btn btn-success">
                                            <i class="fa fa-plus"></i>&nbsp;Ajouter une adresse
                                        </a>
                                        <button type="submit" class="btn btn-success" name="refreshAddress"><i class="fa fa-refresh"></i>&nbsp;Actualiser vos adresses</button>
                                        <c:if test="${nbItem>0}"><a href="controller?section=displayCart" name="cartReturn" class="btn btn-success"><i class="fa fa-shopping-cart"></i>&nbsp;Retourner au panier</a></c:if>
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