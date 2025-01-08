<%@ page import="java.util.List" %>
<%@ page import="utils.ScoreDTO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
</head>
<body>
    <h1>Liste des Scores</h1>
    <%
        // Simuler ou récupérer la liste de scores (normalement fournie par un contrôleur ou DAO)
        List<ScoreDTO> scores = (List<ScoreDTO>) request.getAttribute("scores");
        if (scores == null || scores.isEmpty()) {
    %>
        <p>Aucun score trouvé.</p>
    <%
        } else {
    %>
        <table>
            <thead>
                <tr>
                    <th>ID Partie</th>
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
                        <td><%= score.getIdGame() %></td>
                        <td><%= score.getNCombatWin() %></td>
                        <td><%= score.getNCityWin() %></td>
                        <td><%= score.getScore() %></td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    <%
        }
    %>
    <div class="button-container">
        <form action="<%= request.getContextPath() %>/Board" method="post">
            <button type="submit">Retour au Plateau</button>
        </form>
    </div>
</body>
</html>
