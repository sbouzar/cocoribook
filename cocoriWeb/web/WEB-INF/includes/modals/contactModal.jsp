<div class="modal fade" id="contactModal" tabindex="-1" role="dialog" aria-labelledby="contactModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form action="${currentURL}" method="post" role="form" >

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Fermer"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="contactModalLabel">Contactez-nous</h4>
                </div>
                <div class="modal-body">
                    
                    <input type="hidden" value="contact" name="task" />

                    <div class="form-group">
                        <label for="contact-name">Nom</label>
                        <input type="text" class="form-control" required id="contact-name" name="contact-name" placeholder="Nom" value="${customer.firstName} ${customer.lastName}">
                    </div>
                    <div class="form-group">
                        <label for="contact-email">Adresse email</label>
                        <input type="email" class="form-control" required id="contact-email" name="contact-email" placeholder="Adresse email" value="${customer.email}">
                    </div>

                    <div class="form-group">
                        <label for="contact-message">Message</label>
                        <textarea class="form-control" rows="3" required id="contact-message" name="contact-message"></textarea>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Annuler</button>
                    <button type="submit" class="btn btn-info">Envoyer</button>
                </div>

            </form>

        </div>
    </div>
</div>