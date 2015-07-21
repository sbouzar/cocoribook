<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header data-spy="affix" data-offset-top="160">

    <form id="search-form" action="controller" class="search-form closed" method="get" role="search" autocomplete="off">
        <div class="container">
            <input type="hidden" name="section" value="products">
            <div class="form-group">
                <label class="sr-only" for="search">Rechercher un livre</label>
                <input type="text" class="form-control" name="search" id="search" placeholder="Rechercher un ou plusieurs livres par leurs titres ou leurs auteurs">
                <button type="submit"><i class="glyphicon glyphicon-search"></i></button>
            </div>
            <div class="close-search"><i class="glyphicon glyphicon-remove"></i></div>
        </div>
    </form>

    <nav role="navigation">
        <div class="container">

            <h1 class="pull-left hidden-xs"><a class="brand" href="controller">Cocori<strong>book</strong></a></h1>

            <div class="pull-right">

                <ul class="nav nav-pills pull-left">
                    <li><a href="controller"><i class="glyphicon glyphicon-home"></i><span class="sr-only">Accueil</span></a></li>
                    <li><a href="#" data-toggle="modal" data-target="#contactModal"><i class="glyphicon glyphicon-envelope"></i><span class="sr-only">Nous contacter</span></a></li>
                    
                    <c:if test="${!logged}">
                        <li><a href="#" data-toggle="modal" data-target="#loginModal"><i class="glyphicon glyphicon-user"></i><span class="sr-only">Connexion</span></a></li>
                    </c:if>

                    <c:if test="${logged}">
                        <li><a href="controller?section=customerTasks"><i class="glyphicon glyphicon-user"></i><span class="sr-only">Espace personnel</span></a></li>
                        <li><a href="controller?disconnect=disconnect"><i class="fa fa-user-times"></i><span class="sr-only">Déconnexion</span></a></li>
                    </c:if>

                </ul>

                <ul id="cart" class="nav nav-pills pull-left">
                    <li class="active">
                        <a href="#" data-toggle="modal" data-target="#cartModal">
                            <i class="fa fa-shopping-cart"></i>&nbsp;${total} &euro;<span class="badge">${nbItem}</span>
                        </a>
                    </li>
                </ul>

                <ul class="nav nav-pills pull-left">
                    <li>
                        <a class="search-btn" href="">
                            <i class="glyphicon glyphicon-search"></i><span class="sr-only">Rechercher</span>
                        </a>
                    <li>
                </ul>

            </div>
        </div>

        <div class="navbar navbar-inverse straight">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand visible-xs" href="controller">Cocori<strong>book</strong></a>
                </div>

                <div class="collapse navbar-collapse" id="navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="controller?section=products&type=Litt&eacute;rature&title=Littérature">Littérature</a></li>
                        <li><a href="controller?section=products&type=Bande dessin&eacute;e&title=Bande dessinée">Bande dessinée</a></li>
                        <li><a href="controller?section=products&type=Culture&title=Culture">Culture</a></li>
                        <li><a href="controller?section=products&type=Livre Pratique&title=Livre Pratique">Livre Pratique</a></li>
                    </ul>
                </div>
            </div>
        </div>

    </nav>

</header>