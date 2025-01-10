<%@ page import="java.util.List"%>
<%@ page import="utils.ScoreDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Scores</title>
<style>
table {
	border-collapse: collapse;
	width: 100%;
}

th, td {
	border: 1px solid black;
	padding: 8px;
	text-align: center;
}

.nextGameBtn {
    display: none;
}

.p1{
	background-color: #FF474D;
}
.p2{
	background-color: #ADDFFF;
}
.p3{
	background-color: #96F97A;
}
.p4{
	background-color: #FCA746;
}
</style>
<script>
    // JavaScript pour afficher le bouton après 20 secondes
    window.onload = function() {
        setTimeout(function() {
            document.getElementById("nextGameBtn").style.display = "block";
            console.log("lezgongue")
        }, 10000);
    };
</script>
</head>
<body>
	<%
	// Simuler ou récupérer la liste de scores (normalement fournie par un contrôleur ou DAO)
	List<ScoreDTO> scores = (List<ScoreDTO>) request.getAttribute("scores");
	model.Map map = (model.Map) session.getAttribute("map");
	%>
	<h1>Score de fin de partie</h1>
	<p>
		Victoire de :
		<%=map.getPlayer(scores.get(0).getIdPlayer()).getName()%>
	<table>
		<thead>
			<tr>
				<th>Joueur</th>
				<th>Combats Gagnés</th>
				<th>Cités Gagnées</th>
				<th>Score Total</th>
			</tr>
		</thead>
		<tbody>
			<%
			for (ScoreDTO score : scores) {
			%>
			<tr class="<%= "p" + score.getIdPlayer()%>">
				<td><%=map.getPlayer(score.getIdPlayer()).getName()%></td>
				<td><%=score.getNCombatWin()%></td>
				<td><%=score.getNCityWin()%></td>
				<td><%=score.getScore()%></td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
	
	<div>

		<button type="submit" onclick="setGame()" class="nextGameBtn" id="nextGameBtn">Refaire une partie</button> 
    </div>
</body>
<script>
	
	function setGame() {

		var form = document.createElement("form");
		form.method = "POST";
		form.action = "Board";

		var actionInput = document.createElement("input");
		actionInput.type = "hidden";
		actionInput.name = "newGame";
		actionInput.value = "true";
		form.appendChild(actionInput);

		console.log("click");
		
		document.body.appendChild(form);
		form.submit();
	}

</script>
</html>
