<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <%@include file="includes/head.jsp" %>
        <title>Ouvrages | CocoriBook : librairie en ligne</title>
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
                            <li class="active"> <c:if test="${not empty title}">${title}</c:if> <c:if test="${empty title}">Ouvrages</c:if></li>
                        </ol>                      
                        <h1 class="page-header">${title}</h1>
                    </div>
                </div>
            </div>

            <div id="content" class="bg-gray-new">
                <div class="container">
                    <div class="row">
                        <div class="col-md-8">
                            <main class="products" role="main">
                                
                                <c:if test="${empty searchedProducts && empty error}">
                                    <div class="alert alert-warning" role="alert">
                                        <h4>Désolé !</h4>
                                        <p>Il n'existe aucun produit correspondant à votre recherche.</p>
                                    </div>
                                </c:if>
                                
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
                                
                                <c:if test="${not empty searchedProducts}">
                                    <form class="form-inline panel panel-default" action="${currentURL}" method="post">
                                        <div class="panel-body">
                                            <div class="form-group">
                                                <label for="next">Résultats par page</label>
                                                <select class="form-control" id="next" name="next">
                                                    <option value="6"<c:if test="${next == 6}"> selected</c:if>>6</option>
                                                    <option value="9"<c:if test="${next == 9}"> selected</c:if>>9</option>
                                                    <option value="12"<c:if test="${next == 12}"> selected</c:if>>12</option>
                                                    <option value="15"<c:if test="${next == 15}"> selected</c:if>>15</option>
                                                </select>
                                            </div>
                                            <button class="btn btn-info" type="submit">Afficher</button>
                                        </div>
                                    </form>
                                </c:if>
                                    
                                <c:forEach var="item" items="${searchedProducts}" varStatus="loop">

                                    <c:if test="${loop.index % 3 == 0}">
                                        <div class="row">
                                        </c:if>

                                        <div class="col-sm-4">
                                            <article class="featured<c:if test="${item.inventory < 1}"> expired</c:if>">
                                                <div class="featured-media">
                                                    <a href="controller?section=product&id=${item.id}">
                                                        <c:if test="${not empty item.image}">
                                                            <img src="images/covers/${item.image}" alt="${item.title}">
                                                        </c:if>
                                                        <c:if test="${empty item.image}">
                                                            <img src="images/covers/default.jpg" alt="${item.title}">
                                                        </c:if>
                                                    </a>
                                                    <ul class="featured-actions text-center list-inline">
                                                        <li><a href="controller?section=product&id=${item.id}" title="Voir le détail"><i class="fa fa-eye"></i></a></li>
                                                        <c:if test="${item.inventory > 0}">
                                                            <li><a href="controller?mode=cart&id=${item.id}" title="Ajouter au panier"><i class="fa fa-cart-plus"></i></a></li>
                                                        </c:if>
                                                    </ul>
                                                </div>
                                                <div class="featured-body">
                                                    <h2 class="featured-heading">
                                                        <a href="controller?section=product&id=${item.id}">
                                                            ${item.title}
                                                            <c:if test="${not empty item.subtitle}">
                                                                <br/><small>${item.subtitle}</small>
                                                            </c:if>                                                        
                                                        </a>
                                                    </h2>
                                                    <c:if test="${not empty item.authors}">
                                                        <p class="featured-author">de 
                                                            <c:forEach var="author" items="${item.authors}">
                                                                <a href="controller?section=products&author=${author.id}&title=${author.firstName} ${author.lastName}">${author.firstName} ${author.lastName}</a>
                                                            </c:forEach>
                                                        </p>
                                                    </c:if>
                                                    <c:if test="${item.inventory < 1}">
                                                        <p class="label label-danger"><i class="fa fa-exclamation-triangle"></i>&nbsp;Epuisé</p>
                                                    </c:if>
                                                    <c:if test="${item.inventory > 0}">
                                                        <p class="featured-price">€ ${item.totalPrice}</p>
                                                    </c:if>
                                                </div>
                                            </article>
                                        </div>

                                        <c:if test="${(loop.index+1) % 3 == 0 or loop.last}">
                                        </div>
                                    </c:if>

                                </c:forEach>

                                <c:if test="${pagination > 0 && page <= pagination}">
                                    <nav class="text-center">
                                        <ul class="pagination">
                                            <c:forEach begin="1" end="${pagination}" varStatus="loop">
                                                <li<c:if test="${(loop.index) == page}"> class="active"</c:if>><a href="${currentURL}&page=${loop.index}&next=${next}">${loop.index}</a></li>
                                            </c:forEach>
                                        </ul>
                                    </nav>
                                </c:if>
                            </main>
                        </div>
                        <div class="col-md-4">
                            <aside role="complementary">
                                <c:if test="${not empty themes}">
                                    <h1 class="widget-heading">Themes</h1>
                                    <div class="widget">
                                        <ul class="list-unstyled list-nav">
                                            <c:forEach var="theme" items="${themes}" varStatus="loop">
                                                <li class="level${theme.level}">
                                                    <c:if test="${theme.level > 1}">
                                                        <i class="fa fa-arrow-circle-right"></i>
                                                    </c:if>
                                                    <a href="controller?section=products&theme=${theme.id}&title=${theme.name}">${theme.name}</a>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </c:if>

                                <c:if test="${not empty tags}">
                                    <h1 class="widget-heading">Mots-clés</h1>
                                    <div class="widget">
                                        <ul class="list-inline list-label">
                                            <c:forEach var="tag" items="${tags}" varStatus="loop">
                                                <li><a href="controller?section=products&tag=${tag.id}&title=${tag.name}" class="label label-success">${tag.name}</a></li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </c:if>

                                <c:if test="${not empty labels}">
                                    <h1 class="widget-heading">Rubriques</h1>
                                    <div class="widget">
                                        <ul class="list-inline list-label">
                                            <c:forEach var="label" items="${labels}" varStatus="loop">
                                                <li><a href="controller?section=products&label=${label.id}&title=${label.name}" class="label label-info">${label.name}</a></li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </c:if>

                            </aside>
                        </div>
                    </div>
                </div>
            </div>
            <%@include file="includes/footer.jsp" %>
        </div>
        <%@include file="includes/scripts.jsp" %>
    </body>
</html>