<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Victoire</title>
</head>
<body>
    <h1>Félicitations !</h1>
    <p>Vous avez gagné !</p>
    <form action="<%= request.getContextPath() %>/newGame" method="post">
        <button type="submit">Commencer une nouvelle partie</button>
    </form>
</body>
</html>
