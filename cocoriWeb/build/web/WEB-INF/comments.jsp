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
                            <li class="active">Vos commentaires</li>
                        </ol>                      
                        <h1 class="page-header">Vos commentaires</h1>
                    </div>
                </div>
            </div>

            <div id="content" class="bg-gray-new">
                <div class="container">
                    <div class="row">
                        <div class="col-xs-12">
                            <main role="main">     
                                <c:if test="${not empty error}">
                                    <div class="alert alert-danger" role="alert">${error}</div>
                                </c:if>
                                <c:if test="${not empty success}">
                                    <div class="alert alert-success" role="alert">${success}</div>
                                </c:if>
                                <c:if test="${empty listUserComments}">
                                    <div class="alert alert-warning" role="alert">
                                        ${warning}
                                    </div>
                                </c:if>
                                <c:forEach items="${listUserComments}" var="comment">
                                    <div class="panel panel-default comment">
                                        <div class="panel-heading">
                                            <h4>
                                                <i class="glyphicon glyphicon-book"></i>&nbsp;<a href="controller?section=product&id=${comment.product.id}" target="_blank">${comment.product.title} <small>${comment.product.subtitle}</small></a>
                                                <time class="pull-right">${comment.commentDate}</time>                                                
                                            </h4>                                                
                                        </div>
                                        <div class="panel-body">
                                            <p>${comment.comment}</p>
                                            <p class="note">
                                                <c:forEach begin="1" end="5" varStatus="loop">
                                                    <c:if test="${loop.index <= comment.rating}">
                                                        <i class="glyphicon glyphicon-star"></i>
                                                    </c:if>
                                                    <c:if test="${loop.index > comment.rating}">    
                                                        <i class="glyphicon glyphicon-star-empty"></i>
                                                    </c:if>
                                                </c:forEach>
                                            </p>
                                        </div>
                                        <div class="panel-footer text-right">
                                            <ul class="list-inline">
                                                <li><i class="glyphicon glyphicon-pencil"></i><a href="controller?section=commentEdit&id=${comment.id}">&nbsp;Modifier</a></li>
                                                <li><i class="glyphicon glyphicon-trash"></i><a href="controller?section=comments&action=remove&id=${comment.id}">&nbsp;Supprimer</a></li>
                                            </ul>
                                        </div>
                                    </div> 
                                </c:forEach>
                                    <c:if test="${pagination > 0 && page <= pagination}">

                                        <nav class="text-center">
                                            <ul class="pagination">
                                                <c:forEach begin="1" end="${pagination}" varStatus="loop">
                                                    <li<c:if test="${(loop.index) == page}"> class="active"</c:if>><a href="${currentURL}&page=${loop.index}&next=${next}">${loop.index}</a></li>
                                                </c:forEach>
                                                </ul>
                                            </nav>

                                    </c:if>                            </main>
                        </div>
                    </div>
                </div>
            </div>
            <%@include file="includes/footer.jsp" %>
        </div>

        <%@include file="includes/scripts.jsp" %>
    </body>
</html>