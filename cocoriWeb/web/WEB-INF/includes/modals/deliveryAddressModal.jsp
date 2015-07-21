<div class="modal fade" id="deliveryAddressModal" tabindex="-1" role="dialog" aria-labelledby="deliveryAddressModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Fermer"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="deliveryAddressModalLabel">Votre adresse de livraison</h4>
            </div>
            <div class="modal-body">

                <form action="controller?section=deliveryAddresses" method="post" role="form">
                    <input type="hidden" name="section" value="deliveryAddresses">
                    <div class="form-group">
                        <label for="delivery-lastName">Nom</label>
                        <input type="text" class="form-control" required id="delivery-lastName" name="lastName" placeholder="Nom">
                    </div>
                    <div class="form-group">
                        <label for="delivery-firstName">Prénom</label>
                        <input type="text" class="form-control" required id="delivery-firstName" name="firstName" placeholder="Prénom">
                    </div>
                    <div class="form-group">
                        <label for="delivery-addressLine1">Adresse 1</label>
                        <input type="text" class="form-control" required id="delivery-addressLine1" name="addressLine1" placeholder="Adresse 1">
                    </div>
                    <div class="form-group">
                        <label for="delivery-addressLine2">Adresse 2</label>
                        <input type="text" class="form-control" id="delivery-addressLine2" name="addressLine2" placeholder="Adresse 2">
                    </div>
                    <div class="form-group">
                        <label for="digicode">Digicode</label>
                        <input type="text" class="form-control" id="delivery-digicode" name="digicode" placeholder="Digicode">
                    </div>
                    <div class="form-group">
                        <label for="delivery-zipcode">Code Postal</label>
                        <input type="number" min="00001" max="99999" class="form-control" required id="delivery-zipcode" name="zipcode" placeholder="Code Postal">
                    </div>
                    <div class="form-group">
                        <label for="delivery-city">Ville</label>
                        <input type="text" class="form-control" required id="delivery-city" name="city" placeholder="Ville">
                    </div>
                    <div class="form-group">
                        <label for="phone">Telephone</label>
                        <input type="text" class="form-control" id="delivery-phone" name="phone" placeholder="Téléphone">
                    </div>
                    <div class="form-group">
                        <label for="byDefault" >Voulez-vous en faire votre adresse par défaut?  </label>
                        <input type="radio" id="delivery-byDefaultYes"  name="byDefault" checked value="Oui">&nbsp;Oui
                        <input type="radio" id="delivery-byDefaultNo"  name="byDefault" value="Non">&nbsp;Non
                    </div>


                    <button type="submit" class="btn btn-success btn-block" name="addAddress">Ajouter</button>
                </form>

            </div>

        </div>
    </div>
</div>