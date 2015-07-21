<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Fermer"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="loginModalLabel">Connectez-vous à votre compte</h4>
            </div>
            <div class="modal-body">

                <form action="controller" method="post" role="form">
                    <input type="hidden" name="section" value="login">
                    <div class="form-group">
                        <label for="login-pseudo">Pseudo</label>
                        <input type="text" class="form-control" required id="login-pseudo" name="pseudo" placeholder="Pseudo">
                    </div>
                    <div class="form-group">
                        <label for="login-password">Mot de passe</label>
                        <input type="password" class="form-control" required id="login-password" name="password" placeholder="Mot de passe">
                    </div>

                    <ul class="list-unstyled"><li><a class="" href="#">Vous avez oublié votre pseudo ou votre mot de passe ?</a></li></ul>

                    <button type="submit" class="btn btn-info btn-block">Connexion</button>
                </form>

            </div>
            <div class="modal-footer">
                <a href="controller?section=subscribe" class="btn btn-primary btn-block">Ouvir un compte gratuitement</a>
            </div>
        </div>
    </div>
</div>