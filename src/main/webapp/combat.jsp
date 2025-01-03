<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="utils.Constantes" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Combat</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            margin: 20px;
        }
        .container {
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-top: 30px;
        }
        .column {
            text-align: center;
            flex: 1;
        }
        .column img {
            max-width: 150px;
        }
        .column.atq img {
            transform: scaleX(-1); /* Inversion horizontale */
        }
    </style>
</head>
<body>
    <h1>Combat</h1>
    <%
    	model.Combat combat = (model.Combat) request.getAttribute("combat");
        int dice1 = combat.getDice1();
        int dice2 = combat.getDice2();
        int total = dice1 + dice2;
        
        
        
    %>
    <p class="dice">Dé 1 : <%= dice1 %></p>
    <p class="dice">Dé 2 : <%= dice2 %></p>

    <div class="container">
        <div class="column atq">
            <h2>Attaquant</h2>
            <p>Attaque: <%= total %></p>
            <img src="./images/icons/Large/soldier.png" alt="Image Attaquant">
        </div>
        <div class="column ">
            <h2>Défenseur</h2>
            <p>Defense Initial: <%= combat.getInitDefLife() %></p>
            <p>Defense Apres Combat: <%= combat.getDefLifeAfterCombat() %></p>
            <img src="./images/icons/Large/soldier.png" alt="Image Défenseur">
        </div>
    </div>
    <div>
    	<% if(combat.isVictory()){
    		
    		if(combat.getType() == Constantes.COMBAT_TYPE_SOLDIER){
    			%><p>Le soldat adverse est vaincu, vous prenez sa place</p><%
    		}
    		else if(combat.getType() == Constantes.COMBAT_TYPE_CITY){
    			%><p>Le cité adverse est tombé, vous là controlez desormais</p><%
    		}
    	}
    	%>
    
    </div>
    <div class="button-container">
        <form action="<%= request.getContextPath() %>/Board" method="post">
            <button type="submit">Retour au Plateau</button>
        </form>
    </div>

</body>
</html>
