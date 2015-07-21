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
                            <li class="active">Votre espace</li>
                        </ol>                      
                        <h1 class="page-header">Votre espace</h1>
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
                                <ul class="nav nav-pills nav-stacked">
                                    <li><a href="controller?section=subscribe">Vos informations personnelles</a></li>
                                    <li><a href="controller?section=orders">Vos commandes</a></li>
                                    <li><a href="controller?section=deliveryAddresses">Vos adresses de livraison</a></li>
                                    <li><a href="controller?section=comments">Vos commentaires</a></li>
                                </ul>
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