<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion et Inscription</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .container {
            background: #ffffff;
            padding: 20px 30px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            width: 100%;
        }

        h2, h3 {
            text-align: center;
            color: #333333;
        }

        .form-group {
            margin-bottom: 15px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #555555;
        }

        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #dddddd;
            border-radius: 4px;
            font-size: 14px;
        }

        button {
            background-color: #007bff;
            color: #ffffff;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            width: 100%;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #0056b3;
        }

        .separator {
            text-align: center;
            margin: 20px 0;
        }

        .separator p {
            margin: 0;
            color: #888888;
            font-size: 14px;
        }

        .error {
            background-color: #ffe6e6;
            color: #cc0000;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 15px;
            text-align: center;
        }
    </style>
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
