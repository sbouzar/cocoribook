<div class="modal fade" id="daModifyModal" tabindex="-1" role="dialog" aria-labelledby="daModifyModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Fermer"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="daModifyModalLabel">Modifiez votre adresse de livraison</h4>
            </div>
            <div class="modal-body">

                <form action="controller?section=deliveryAddresses" method="post" role="form">
                    <input type="hidden" name="section" value="da">
                    <input  id="da-id" type="hidden" name="mid" />
                    <div class="form-group">
                        <label for="da-lastName">Nom</label>
                        <input type="text" class="form-control" required id="da-lastName" name="mlastName" >
                    </div>
                    <div class="form-group">
                        <label for="da-firstName">Prénom</label>
                        <input type="text" class="form-control" required id="da-firstName" name="mfirstName" >
                    </div>
                    <div class="form-group">
                        <label for="da-addressLine1">Adresse 1</label>
                        <input type="text" class="form-control" required id="da-addressLine1" name="maddressLine1" >
                    </div>
                    <div class="form-group">
                        <label for="da-addressLine2">Adresse 2</label>
                        <input type="text" class="form-control" id="da-addressLine2" name="maddressLine2" >
                    </div>
                    <div class="form-group">
                        <label for="da-digicode">Digicode</label>
                        <input type="text" class="form-control" id="da-digicode" name="mdigicode" >
                    </div>
                    <div class="form-group">
                        <label for="da-zipcode">Code Postal</label>
                        <input type="text" class="form-control" required id="da-zipcode" name="mzipcode" >
                    </div>
                    <div class="form-group">
                        <label for="da-city">Ville</label>
                        <input type="text" class="form-control" required id="da-city" name="mcity" >
                    </div>
                    <div class="form-group">
                        <label for="da-phone">Telephone</label>
                        <input type="text" class="form-control" id="da-phone" name="mphone" >
                    </div>
                    <div class="form-group">
                        <label for="byDefault" >Voulez-vous en faire votre adresse par défaut?  </label>
                        <input type="radio" id="da-byDefaultYes"  name="mbyDefault" checked value="Oui">&nbsp;Oui
                        <input type="radio" id="da-byDefaultNo"  name="mbyDefault" value="Non">&nbsp;Non
                    </div>


                    <button type="submit" class="btn btn-success btn-block" name="editAddress">Modifier</button>
                </form>

            </div>

        </div>
    </div>
</div>