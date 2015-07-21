<div id="bottom">

    <section class="hidden-xs">
        <div class="container">
            <div class="row">
                <div class="col-sm-4">
                    <c:if test="${not empty lastUsers}">
                        <h1 class="widget-heading">Derniers inscrits</h1>
                        <div class="widget">
                            <ul class="item-list">
                                <c:forEach var="item" items="${lastUsers}" varStatus="loop">
                                    <li><i class="glyphicon glyphicon-user"></i>&nbsp;${item.pseudo}&nbsp;</li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>
                </div>
                <div class="col-sm-4">
                    <c:if test="${not empty publishers}">
                        <h1 class="widget-heading">Les éditeurs</h1>
                        <div class="widget">
                            <ul class="item-list">
                                <c:forEach var="item" items="${publishers}" varStatus="loop">
                                    <li><i class="fa fa-book"></i>&nbsp;<a href="controller?section=products&publisher=${item.id}&title=${item.name}">${item.name}</a></li>
                                </c:forEach>
                            </ul>
                        </div>
                    </c:if>
                </div>
                <div class="col-sm-4">
                    <c:if test="${not empty lastComments}">
                        <h1 class="widget-heading">Derniers commentaires</h1>
                        <div class="widget">
                            <c:forEach var="item" items="${lastComments}" varStatus="loop">
                                <div class="media">
                                    <div class="media-left">
                                        <a href="controller?section=product&id=${item.product.id}">
                                            <c:if test="${not empty item.product.image}">
                                                <img class="media-object" src="images/covers/${item.product.image}" alt="">
                                            </c:if>
                                            <c:if test="${empty item.product.image}">
                                                <img class="media-object" src="images/covers/default.jpg" alt="">
                                            </c:if>
                                        </a>
                                    </div>
                                    <div class="media-body">
                                        <p>${item.comment}</p>
                                        <p><time>${item.commentDate}</time> <span class="author"><i class="glyphicon glyphicon-user"></i>&nbsp;${item.customer.pseudo}</span></p>
                                        <p class="note">
                                            <c:forEach begin="1" end="5" varStatus="loop">
                                                <c:if test="${loop.index <= item.rating}">
                                                    <i class="glyphicon glyphicon-star"></i>
                                                </c:if>
                                                <c:if test="${loop.index > item.rating}">    
                                                    <i class="glyphicon glyphicon-star-empty"></i>
                                                </c:if>
                                            </c:forEach>
                                        </p>
                                    </div>
                                </div>
                            </c:forEach>  
                        </div>
                    </c:if>
                </div>					
            </div>
        </div>
    </section>

    <footer role="contentinfo">
        <div class="container">
            <h1 class="text-center">Cocoribook</h1>
            <div class="row">
                <div class="col-sm-6">
                    <p class="text-right">6 8 rue Georges et Mai Politzer<br/>75012 PARIS 12</p>
                </div>
                <div class="col-sm-6">
                    <p class="text-left">Téléphone&nbsp;: 01 44 50 18 22<br/>Fax&nbsp;: 01 44 50 18 23</p>
                </div>
                <div class="col-xs-12">
                    <ul class="list-inline social-nav text-center">
                        <li><a href="https://fr-fr.facebook.com/" target="_blank"><i class="fa fa-facebook-square"></i></a></li>
                        <li><a href="https://twitter.com" target="_blank"><i class="fa fa-twitter-square"></i></a></li>
                        <li><a href="https://plus.google.com/" target="_blank"><i class="fa fa-google-plus-square"></i></a></li>
                    </ul>
                </div>
            </div>

        </div>
    </footer>

    <div class="copyright">
        <div class="container">
            <p><a href="controller">Cocoribook</a> © 2015 Tous droits résevés. <a href="controller?section=page&title=Conditions d'utilisation">Conditions d'utilisation</a> <a href="controller?section=page&title=Mentions légales">Mentions légales</a> <a href="controller?section=page&title=Confidentialité">Confidentialité</a></p> 
        </div>
    </div>

</div>

<a id="totop" href="#top" title="Retour en haut de page"><i class="glyphicon glyphicon-chevron-up"></i></a>
