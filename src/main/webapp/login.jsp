<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
	  <div class="container">
        <h2>Connexion</h2>

        <!-- Affichage des erreurs -->
        <c:if test="${not empty error}">
            <div class="error">
                <p>${error}</p>
            </div>
        </c:if>

        <!-- Formulaire de connexion -->
        <form action="LoginController" method="POST">
            <input type="hidden" name="action" value="login">
            <div class="form-group">
                <label for="username">Nom d'utilisateur</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="mdp">Mot de passe</label>
                <input type="password" id="mdp" name="mdp" required>
            </div>
            <button type="submit">Se connecter</button>
        </form>

        <div class="separator">
            <p>Ou</p>
        </div>

        <!-- Formulaire d'inscription -->
        <h3>Créer un compte</h3>
        <form action="LoginController" method="POST">
            <input type="hidden" name="action" value="register">
            <div class="form-group">
                <label for="username">Nom d'utilisateur</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="mdp">Mot de passe</label>
                <input type="password" id="mdp" name="mdp" required>
            </div>
            <div class="form-group">
                <label for="confirm_mdp">Confirmer le mot de passe</label>
                <input type="password" id="confirm_mdp" name="confirm_mdp" required>
            </div>
            <button type="submit">Créer un compte</button>
        </form>
    </div>
</body>
</html>
