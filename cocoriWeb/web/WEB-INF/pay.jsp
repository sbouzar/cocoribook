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
                            <li><a href="controller?section=displayCart">Panier</a></li>
                            <li><a href="controller?section=order">Commande</a></li>
                            <li class="active">Paiement</li>
                        </ol>                      
                        <h1 class="page-header">Paiement</h1>
                    </div>
                </div>
            </div>

            <div id="content" class="bg-gray-new">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <main role="main">
                                <h2 class="text-left">Votre commande a bien été enregistrée.</h2>
                                <p class="pull-right"><a href="controller?section=products" class="btn btn-default"><i class="fa fa-chevron-left"></i>&nbsp;Continuer vos achats</a>
                                <p>Veuillez procéder au paiement</p>
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
