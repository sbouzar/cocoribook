<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <%@include file="includes/head.jsp" %>
        <title>Page | CocoriBook : librairie en ligne</title>
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
                            <li class="active">${title}</li>
                        </ol>                      
                        <h1 class="page-header">${title}</h1>
                    </div>
                </div>
            </div>

            <div id="content" class="bg-gray-new">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <main role="main">
                                <c:if test="${not empty error}">
                                    <div class="alert alert-danger" role="alert">
                                        ${error}
                                    </div>
                                </c:if>
                                <c:if test="${not empty success}">
                                    <div class="alert alert-success" role="alert">
                                        ${success}
                                    </div>
                                </c:if>
                                <p>Quisque rutrum. Vivamus elementum semper nisi. Quisque libero metus, condimentum nec, tempor a, commodo mollis, magna. Curabitur nisi. Nulla neque dolor, sagittis eget, iaculis quis, molestie non, velit.</p>
                                <p>In hac habitasse platea dictumst. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos hymenaeos. Nunc nonummy metus. Quisque id mi. Phasellus accumsan cursus velit.</p>
                                <p>Sed aliquam ultrices mauris. Vivamus aliquet elit ac nisl. Maecenas ullamcorper, dui et placerat feugiat, eros pede varius nisi, condimentum viverra felis nunc et lorem. Suspendisse pulvinar, augue ac venenatis condimentum, sem libero volutpat nibh, nec pellentesque velit pede quis nunc. Morbi vestibulum volutpat enim.</p>
                                <p>Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Fusce id purus. Praesent turpis. Curabitur vestibulum aliquam leo. Nulla consequat massa quis enim. Pellentesque posuere.</p>
                                <p>Pellentesque ut neque. Suspendisse enim turpis, dictum sed, iaculis a, condimentum nec, nisi. Suspendisse potenti. Nullam quis ante. Maecenas vestibulum mollis diam.</p>
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