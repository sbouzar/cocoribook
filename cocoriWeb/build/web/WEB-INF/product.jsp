<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <%@include file="includes/head.jsp" %>
        <title>${product.title} | CocoriBook : librairie en ligne</title>
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
                            <li class="active">${product.title}</li>
                        </ol>                      
                        <h1 class="page-header">${product.title}</h1>
                        <c:if test="${not empty product.subtitle}">
                            <h2>${product.subtitle}</h2>
                        </c:if>
                    </div>
                </div>
            </div>

            <div id="content" class="bg-gray-new">
                <div class="container">
                    <div class="row">
                        <div class="col-md-8">
                            <main class="product" role="main">

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

                                <div class="row">

                                    <div class="product-media col-sm-5">
                                        <c:if test="${not empty product.image}">
                                            <img src="images/covers/${product.image}" alt="" />
                                        </c:if>
                                        <c:if test="${empty product.image}">
                                            <img src="images/covers/default.jpg" alt="" />
                                        </c:if>
                                    </div>

                                    <div class="product-body col-sm-7">

                                        <h1 class="product-header">${product.title}
                                            <c:if test="${not empty product.subtitle}">
                                                <small>${product.subtitle}</small>
                                            </c:if>
                                        </h1>

                                        <c:if test="${not empty product.labels}">
                                            <ul class="list-inline">
                                                <c:forEach items="${product.labels}" var="label">
                                                    <li><a href="controller?section=products&label=${label.id}" class="label label-info">${label.name}</a></li>
                                                    </c:forEach>
                                            </ul>
                                        </c:if>                                        

                                        <c:if test="${not empty product.totalPrice}">
                                            <p class="price txt-info">
                                                <strong>€ ${product.totalPrice} TTC</strong>&nbsp;
                                                <c:if test="${product.discount > 0}">
                                                    <span class="label label-info">Remise comprise ${product.discount}%</span>
                                                </c:if>
                                                <c:if test="${product.tax > 0}">
                                                    <span class="label label-warning">Taxe comprise ${product.tax}%</span>
                                                </c:if>
                                            </p>
                                        </c:if>

                                        <c:if test="${not empty product.authors}">
                                            <ul class="list-inline">
                                                <c:forEach items="${product.authors}" var="author">
                                                    <li><a href="controller?section=products&author=${author.id}">${author.firstName} ${author.lastName}</a></li>
                                                    </c:forEach>
                                            </ul>
                                        </c:if>                                        

                                        <ul class="list-inline">
                                            <c:if test="${not empty product.format}">
                                                <li>${product.format}</li>
                                                </c:if>
                                                <c:if test="${not empty product.type}">
                                                <li><a href="controller?section=products&type=${product.type}">${product.type}</a></li>
                                                </c:if>
                                                <c:if test="${not empty product.language}">
                                                <li>${product.language}</li>
                                                </c:if>
                                                <c:if test="${product.volume > 0}">
                                                <li><span class="text-muted">Tome : </span>${product.volume}</li>
                                                </c:if>
                                                <c:if test="${not empty product.pages}">
                                                <li><span class="text-muted">Pages : </span>${product.pages}</li>
                                                </c:if>
                                        </ul>


                                        <c:if test="${not empty product.summary}">
                                            <p><c:out value="${product.summary}" /></p>
                                        </c:if>

                                        <c:if test="${not empty product.inventory}">
                                            <c:if test="${product.inventory > 0}">
                                                <p><i class="fa fa-cube"></i>&nbsp;En stock : <span class="badge"> ${product.inventory}</span></p>
                                                <a href="controller?mode=cart&id=${product.id}" class="btn btn-success"><i class="fa fa-cart-plus"></i>&nbsp;Ajouter</a>
                                            </c:if>
                                            <c:if test="${product.inventory <= 0}">
                                                <p class="label label-danger"><i class="fa fa-exclamation-triangle"></i>&nbsp;Epuisé</p>
                                            </c:if>
                                        </c:if>

                                        <hr />

                                        <ul class="list-unstyled">
                                            <c:if test="${not empty product.publicationDate}">
                                                <li><i class="fa fa-calendar"></i>&nbsp;<span class="text-muted">Date de parution : </span>${product.publicationDate}</li>
                                                </c:if>
                                                <c:if test="${not empty product.publisher.name}">
                                                <li><i class="fa fa-book"></i>&nbsp;<span class="text-muted">Editeur : </span><a href="controller?section=products&publisher=${product.publisher.id}">${product.publisher.name}</a></li>
                                                </c:if>
                                                <c:if test="${not empty product.isbn10}">
                                                <li><i class="fa fa-barcode"></i>&nbsp;<span class="text-muted">ISBN 10 : </span>${product.isbn10}</li>
                                                </c:if>
                                                <c:if test="${not empty product.isbn13}">
                                                <li><i class="fa fa-barcode"></i>&nbsp;<span class="text-muted">ISBN 13 : </span>${product.isbn13}</li>
                                                </c:if>
                                                <c:if test="${product.weight > 0}">
                                                <li><i class="fa fa-info"></i>&nbsp;<span class="text-muted">Poids : </span>${product.weight}</li>
                                                </c:if>
                                        </ul>
                                    </div>

                                </div>

                                <div class="product-footer">

                                    <c:if test="${not empty product.themes}">
                                        <h2 class="widget-heading"><small>Thèmes</small></h2>
                                        <div class="widget">
                                            <ul class="list-inline list-label">
                                                <c:forEach items="${product.themes}" var="theme">
                                                    <li><a href="controller?section=products&theme=${theme.id}" class="label label-success">${theme.name}</a></li>
                                                    </c:forEach>
                                            </ul>
                                        </div>
                                    </c:if>                                        

                                    <c:if test="${not empty product.tags}">
                                        <h2 class="widget-heading"><small>Mots-clés</small></h2>
                                        <div class="widget">
                                            <ul class="list-inline list-label">
                                                <c:forEach items="${product.tags}" var="tag">
                                                    <li><a href="controller?section=products&tag=${tag.id}" class="label label-success">${tag.name}</a></li>
                                                    </c:forEach>
                                            </ul>
                                        </div>
                                    </c:if>                                        

                                </div>  


                                <c:if test="${not empty product.comments or logged}">
                                    <footer>

                                        <ul class="nav nav-tabs" role="tablist">
                                            <c:if test="${not empty product.comments}">
                                                <li role="presentation" class="active"><a href="#comments" aria-controls="comments" role="tab" data-toggle="tab">Commentaires</a></li>
                                                </c:if>                                        
                                                <c:if test="${logged}">
                                                <li role="presentation"><a href="#form" aria-controls="form" role="tab" data-toggle="tab">Laissez un commentaire</a></li>
                                                </c:if>                                        
                                        </ul>

                                        <div class="tab-content">
                                            <c:if test="${not empty commentsByProd}">
                                                <div role="tabpanel" class="tab-pane active" id="comments">
                                                    <c:forEach items="${commentsByProd}" var="comment">
                                                        <div class="panel panel-default">
                                                            <div class="panel-body">
                                                                <p>
                                                                    <time>${comment.commentDate}</time>
                                                                    <span class="author"><i class="glyphicon glyphicon-user"></i>&nbsp;${comment.customer.pseudo}</span>
                                                                </p>
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
                                                        </div>        

                                                    </c:forEach>
                                                    
                                                    <c:if test="${pagination > 0 && page <= pagination}">
                                                        <nav class="text-center">
                                                            <ul class="pagination">
                                                                <c:forEach begin="1" end="${pagination}" varStatus="loop">
                                                                    <li<c:if test="${(loop.index) == page}"> class="active"</c:if>><a href="${currentURL}&id=${product.id}&page=${loop.index}&next=${next}">${loop.index}</a></li>
                                                                </c:forEach>
                                                            </ul>
                                                        </nav>
                                                    </c:if>
                                                </div>
                                            </c:if>  
                                            <c:if test="${logged}">
                                                <div role="tabpanel" class="tab-pane" id="form">
                                                    <form action="controller?section=product&id=${product.id}" method="post">
                                                        <div class="panel panel-default">
                                                            <div class="panel-body">
                                                                <div class="form-group">
                                                                    <label for="message">Votre message</label>
                                                                    <textarea class="form-control" rows="3" maxlength="500" id="message" name="message"></textarea>
                                                                </div>                                                                
                                                                <div class="form-group">
                                                                    <div class="rating txt-success"> 
                                                                        <label>
                                                                            <input type="radio" name="ratingOptions" value="1" checked>
                                                                            <i class="glyphicon glyphicon-star" data-toggle="tooltip" data-placement="top" title="1 sur 5"></i>
                                                                        </label>
                                                                        <label>
                                                                            <input type="radio" name="ratingOptions" value="2">
                                                                            <i class="glyphicon glyphicon-star-empty" data-toggle="tooltip" data-placement="top" title="2 sur 5"></i>
                                                                        </label>
                                                                        <label>
                                                                            <input type="radio" name="ratingOptions" value="3">
                                                                            <i class="glyphicon glyphicon-star-empty" data-toggle="tooltip" data-placement="top" title="3 sur 5"></i>
                                                                        </label>
                                                                        <label>
                                                                            <input type="radio" name="ratingOptions" value="4">
                                                                            <i class="glyphicon glyphicon-star-empty" data-toggle="tooltip" data-placement="top" title="4 sur 5"></i>
                                                                        </label>
                                                                        <label>
                                                                            <input type="radio" name="ratingOptions" value="5">
                                                                            <i class="glyphicon glyphicon-star-empty" data-toggle="tooltip" data-placement="top" title="5 sur 5"></i>
                                                                        </label>
                                                                    </div>
                                                                </div>                                                                
                                                            </div>
                                                        </div>
                                                        <p class="text-right"><button type="submit" class="btn btn-primary" name="confirm">OK</button></p>
                                                    </form>
                                                </div>
                                            </c:if>  
                                        </div>

                                    </footer>        
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