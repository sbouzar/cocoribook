<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="d" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="e" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
    <head>
        <%@include file="includes/head.jsp" %>
        <d:if test="${!logged}">
            <title>Inscription | CocoriBook : librairie en ligne</title>
        </d:if>
        <d:if test="${logged}">
             <title>Votre Espace | CocoriBook : librairie en ligne</title>           
        </d:if>
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
                                <c:if test="${!logged}">
                                <li class="active">Inscription</li>
                            </ol>                      
                            <h1 class="page-header">Inscription</h1>
                        </c:if>
                        <c:if test="${logged}">
                            <li><a href="controller?section=customerTasks">Votre espace</a></li>
                            <li class="active">Modification</li>
                            </ol>                      
                            <h1 class="page-header">Modification</h1> 
                        </c:if>
                    </div>
                </div>
            </div>

            <div id="content" class="bg-gray-new">
                <div class="container">
                    <div class="row">
                        <div class="col-md-8">
                            <main role="main">
                                <c:if test="${not empty error}">
                                    <div class="alert alert-danger" role="alert">${error}</div>
                                </c:if>
                                <c:if test="${not empty success}">
                                    <div class="alert alert-success" role="alert">${success}</div>
                                </c:if>
                                <form class="form-horizontal" method="post">
                                    <fieldset>
                                        <legend>Informations personnelles</legend>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label" for="firstName">Prénom</label>
                                            <div class="col-sm-9">
                                                <input type="text" class="form-control" name="firstName" id="firstName" placeholder="Prénom" required value="${customer.firstName}">
                                            </div>                            
                                        </div>    
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label" for="lastName">Nom</label>
                                            <div class="col-sm-9">
                                                <input type="text" class="form-control" name="lastName" id="lastName" placeholder="Nom" required value="${customer.lastName}">
                                            </div>                            
                                        </div>    
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label" for="email">Email</label>
                                            <div class="col-sm-9">
                                                <input type="email" class="form-control" name="email" id="email" placeholder="Email" required value="${customer.email}">
                                            </div>                            
                                        </div>    
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label" for="bDate">Date de naissance</label>
                                            <div class="col-sm-4">
                                                <input type="text" class="form-control" name="bDate" id="bDate" required value="${customer.birthDate}" placeholder="jj-mm-aaaa">
                                            </div>                            
                                        </div>   
                                    </fieldset>
                                    <fieldset>
                                        <legend>Informations de connexion</legend>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label" for="pseudo">Pseudo</label>
                                            <div class="col-sm-9">
                                                <input type="text" class="form-control" name="pseudo" id="pseudo" required placeholder="Pseudo" value="${customer.pseudo}">
                                            </div>                            
                                        </div>    
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label" for="password">Mot de passe</label>
                                            <div class="col-sm-9">
                                                <input type="password" class="form-control" name="password" id="password" required placeholder="Mot de passe" value="${customer.password}">
                                            </div>                            
                                        </div>    
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label" for="passwordConfirm">Confirmation</label>
                                            <div class="col-sm-9">
                                                <input type="password" class="form-control" name="passwordConfirm" id="passwordConfirm" required placeholder="Confirmez le mot de passe" value="${customer.password}">
                                            </div>                            
                                        </div>    
                                    </fieldset>
                                    <fieldset>
                                        <legend>Adresse</legend>
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label" for="address">Adresse</label>
                                            <div class="col-sm-9">
                                                <input type="text" class="form-control" name="address" id="address" required placeholder="Adresse" value="${customer.address}">
                                            </div>                            
                                        </div>    
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label" for="city">Ville</label>
                                            <div class="col-sm-9">
                                                <input type="text" class="form-control" name="city" id="city" required placeholder="Ville" value="${customer.city}">
                                            </div>                            
                                        </div>    
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label" for="zipcode">Code postal</label>
                                            <div class="col-sm-4">
                                                <input type="text" class="form-control" maxlength="5" name="zipcode" id="zipcode" required placeholder="Code postal" value="${customer.zipcode}">
                                            </div>                            
                                        </div>    
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label" for="phone">Téléphone</label>
                                            <div class="col-sm-4">
                                                <input type="tel" class="form-control" maxlength="10" name="phone" id="phone" required placeholder="Téléphone" value="${customer.phone}">
                                            </div>                            
                                        </div>    
                                    </fieldset>
                                    <p class="text-right"><a href="controller?section=customerTasks" class="btn btn-default">Annuler</a>&nbsp;<e:choose><e:when test="${logged}"><button type="submit" class="btn btn-primary" name="confirm">Confirmer</button></e:when><e:otherwise><button type="submit" class="btn btn-primary" name="subscribe">S'inscrire</button></e:otherwise></e:choose></p>
                                </form>
                            </main>
                        </div>
                        <div class="col-md-4">
                            <aside class="text-center" role="complementary">
                                <div class="catchline"><i class="fa fa-cart-arrow-down"></i>Commandez</div>
                                <div class="catchline"><i class="fa fa-archive"></i>Faîtes-vous livrer</div>
                                <div class="catchline"><i class="fa fa-book"></i>Lisez</div>
                                <div class="catchline"><i class="fa fa-comment"></i>Revenez et commentez</div>
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