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
                            <li><a href="controller?section=comments">Vos commentaires</a></li>
                            <li class="active">Edition commentaire</li>
                        </ol>                      
                        <h1 class="page-header">Edition commentaire</h1>
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
                                <div role="tabpanel" class="tab-pane" id="form">
                                    <form action="controller?section=commentEdit&id=${editComment.id}" method="post">
                                        <div class="panel panel-default">
                                            <div class="panel-body">
                                                <div class="form-group">
                                                    <label for="message">Votre commentaire sur "${editComment.product.title}"</label>
                                                    <textarea class="form-control" rows="3" maxlength="500" id="message" name="message">${editComment.comment}</textarea>
                                                </div>                                                                
                                                <div class="form-group">
                                                    <div class="rating txt-success">
                                                        <c:forEach begin="1" end="5" varStatus="loop"> 
                                                            <c:if test="${loop.index <= editComment.rating}">
                                                                <label>
                                                                    <input type="radio" name="ratingOptions" value="${loop.index}" <c:if test="${loop.index eq editComment.rating}">checked</c:if>>                                                    
                                                                    <i class="glyphicon glyphicon-star" data-toggle="tooltip" data-placement="top" title="${loop.index} sur 5"></i>                                                           
                                                                </label>    
                                                            </c:if>
                                                            <c:if test="${loop.index > editComment.rating}">
                                                                <label>
                                                                    <input type="radio" name="ratingOptions" value="${loop.index}">                                                    
                                                                    <i class="glyphicon glyphicon-star-empty" data-toggle="tooltip" data-placement="top" title="${loop.index} sur 5"></i>                                                           
                                                                </label>
                                                            </c:if>
                                                        </c:forEach>                                                       
                                                    </div>
                                                </div>                                                                
                                            </div>
                                        </div>
                                        <p class="text-right"><button type="submit" class="btn btn-primary" name="confirm">OK</button></p>
                                    </form>
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