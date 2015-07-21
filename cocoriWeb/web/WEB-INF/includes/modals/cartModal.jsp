<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="cartModal" tabindex="-1" role="dialog" aria-labelledby="cartModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Fermer"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="cartModalLabel">Votre panier</h4>
            </div>
            <form action="${currentURL}&mode=modifyModCart" method="post" role="form">
                <div class="modal-body">

                    <table class="table table-responsive">
                        <caption class="sr-only">Panier</caption>
                        <thead class="bg-info">
                            <tr>
                                <th scope="col">Ouvrage</th>
                                <th class="quantity text-center" scope="col">Quantité</th>
                                <th class="text-right" scope="col">Prix</th>
                            </tr>
                        </thead>
                        <tfoot>
                            <tr>
                                <td colspan="2" class="text-right"><strong>Total :</strong></td>
                                <td class="text-right"><strong>${total} &euro;</strong></td>
                            </tr>
                        </tfoot>
                        <tbody>
                            <c:forEach var="product" items="${pList}" varStatus="loop">
                                <tr<c:if test="${loop.index % 2 != 0}"> class="active"</c:if>>
                                    <td><span>${product.title}</span></td>
                                    <td><input type="number" min="0" name="nbModal${product.id}" class="form-control input-sm" value="${product.quantity}"></td>
                                    <td class="text-right"><span>${product.price} &euro;</span></td>
                                </tr>
                            </c:forEach>

                        </tbody>
                    </table>

                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-default"><i class="fa fa-refresh"></i>&nbsp;Modifier</button>
                    <a href="controller?section=order" class="btn btn-primary">Acheter</a>
                    <a href="controller?section=displayCart" class="btn btn-info">Voir le panier</a>
                </div>

            </form>
        </div>
    </div>
</div>