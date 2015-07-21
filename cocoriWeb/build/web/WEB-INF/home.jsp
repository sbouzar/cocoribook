<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <%@include file="includes/head.jsp" %>
        <title>CocoriBook : librairie en ligne</title>
        <meta name="description" content="cocoribook.fr : votre librairie en ligne. Retrouvez un choix de plus 10 000 livres à commander.">
        <meta name="keywords" content="librairie, librairie en ligne, livres, livres neufs, vente de livres en ligne, vente de livres, achats de livres">
    </head>
    <body id="top" class="home">
        <%@include file="includes/modals/cartModal.jsp" %>
        <%@include file="includes/modals/contactModal.jsp" %>
        <%@include file="includes/modals/loginModal.jsp" %>
        <div id="wrapper">
            <%@include file="includes/header.jsp" %>
            
            <main class="bg-gray-new" role="main">
                <div>
                    <div class="container">
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
                        <c:if test="${not empty carousel}">

                            <div id="carousel-promote" class="carousel slide" data-ride="carousel">
                                <ol class="carousel-indicators">
                                    <c:forEach var="item" items="${carousel}" varStatus="loop">
                                        <li data-target="#carousel-promote" data-slide-to="${loop.index}"<c:if test="${loop.index == 0}"> class="active"</c:if>></li>
                                    </c:forEach>
                                </ol>
                                <div class="carousel-inner" role="listbox">
                                    
                                    <c:forEach var="item" items="${carousel}" varStatus="loop">
                                    
                                        <article class="item promote<c:if test="${loop.index == 0}"> active</c:if>">
                                            <div class="promote-media pull-left">
                                                <c:if test="${not empty item.image}">
                                                    <img src="images/covers/${item.image}" alt="${item.title}">
                                                </c:if>
                                                <c:if test="${empty item.image}">
                                                    <img src="images/covers/default.jpg" alt="${item.title}">
                                                </c:if>
                                            </div>
                                            <div class="promote-body imaged">
                                                <h1 class="promote-heading"><a href="controller?section=product&id=${item.id}">${item.title}</a></h1>
                                                <c:if test="${not empty item.subtitle}">
                                                    <h2 class="promote-subheading"><a href="controller?section=product&id=${item.id}">${item.subtitle}</a></h2>
                                                </c:if>
                                                    
                                                <c:if test="${not empty item.authors}">
                                                    <p class="promote-author">de 
                                                        <c:forEach var="author" items="${item.authors}" varStatus="loop">
                                                            <a href="controller?section=products&author=${author.id}&title=${author.firstName} ${author.lastName}">${author.firstName} ${author.lastName}</a>
                                                        </c:forEach>
                                                    </p>
                                                </c:if>
                                                
                                                <c:if test="${not empty item.summary}">
                                                    <p class="promote-summary">${item.summary}</p>
                                                </c:if>
                                                <p class="promote-price label label-info">€ ${item.totalPrice}</p>
                                                <p><a href="controller?section=product&id=${item.id}" class="btn btn-primary"><i class="fa fa-eye fa-lg"></i>&nbsp;&nbsp;Détail</a>&nbsp;<a href="controller?mode=cart&id=${item.id}" class="btn btn-default"><i class="fa fa-cart-plus fa-lg"></i>&nbsp;&nbsp;Ajouter</a></p>
                                            </div>
                                        </article>

                                    </c:forEach>
                                </div>
                                <a class="left carousel-control" href="#carousel-promote" role="button" data-slide="prev">
                                    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                                    <span class="sr-only">Previous</span>
                                </a>
                                <a class="right carousel-control" href="#carousel-promote" role="button" data-slide="next">
                                    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                                    <span class="sr-only">Next</span>
                                </a>
                            </div>

                        </c:if>

                    </div>
                </div>
            </main>
            
            <section class="section separator hidden-xs">
                <div>
                    <div class="container">
                        <div class="row text-center">
                            <div class="col-sm-3"><div class="catchline"><i class="fa fa-cart-arrow-down"></i>Commandez</div></div>
                            <div class="col-sm-3"><div class="catchline"><i class="fa fa-archive"></i>Faîtes-vous livrer</div></div>
                            <div class="col-sm-3"><div class="catchline"><i class="fa fa-book"></i>Lisez</div></div>
                            <div class="col-sm-3"><div class="catchline"><i class="fa fa-comment"></i>Revenez et commentez</div></div>
                        </div>
                    </div>
                </div>
            </section>

            <c:if test="${not empty news}">
            
                <section class="section bg-gray-new">
                    <div>
                        <div class="container">
                            <h1 class="section-heading text-center">Les nouveautés</h1>
                            <ul class="clearfix list-unstyled list-featured">
                                <c:forEach var="item" items="${news}" varStatus="loop">
                                    <li class="pull-left">
                                        <article class="featured">
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
                                                    <li><a href="controller?mode=cart&id=${item.id}" title="Ajouter au panier"><i class="fa fa-cart-plus"></i></a></li>
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
                                                        <c:forEach var="author" items="${item.authors}" varStatus="loop">
                                                            <a href="controller?section=products&author=${author.id}&title=${author.firstName} ${author.lastName}">${author.firstName} ${author.lastName}</a>
                                                        </c:forEach>
                                                    </p>
                                                </c:if>
                                                <p class="featured-price">€ ${item.totalPrice}</p>
                                            </div>
                                        </article>
                                    </li>
                                </c:forEach>
                            </ul>
                            <p class="clearfix text-center">
                                <a href="controller?section=products" class="btn btn-default">Voir tous les ouvrages</a>
                            </p>
                        </div>
                    </div>
                </section>

            </c:if>
                            
            <c:if test="${not empty bestSellers}">
            
                <section class="section  best-sellers">
                    <div>
                        <div class="container">
                            <h1 class="section-heading text-center">Les meilleures ventes</h1>
                            <ul class="clearfix list-unstyled list-featured">
                                <c:forEach var="item" items="${bestSellers}" varStatus="loop">
                                    <li class="pull-left">
                                        <article class="featured">
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
                                                    <li><a href="controller?mode=cart&id=${item.id}" title="Ajouter au panier"><i class="fa fa-cart-plus"></i></a></li>
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
                                                        <c:forEach var="author" items="${item.authors}" varStatus="loop">
                                                            <a href="controller?section=products&author=${author.id}&title=${author.firstName} ${author.lastName}">${author.firstName} ${author.lastName}</a>
                                                        </c:forEach>
                                                    </p>
                                                </c:if>
                                                <p class="featured-price">€ ${item.totalPrice}</p>
                                            </div>
                                        </article>
                                    </li>
                                </c:forEach>
                            </ul>
                            <p class="clearfix text-center">
                                <a href="controller?section=products" class="btn btn-default">Voir tous les ouvrages</a>
                            </p>
                        </div>
                    </div>
                </section>

            </c:if>
                       
            <%@include file="includes/footer.jsp" %>
        </div>
        <%@include file="includes/scripts.jsp" %>
    </body>
</html>
