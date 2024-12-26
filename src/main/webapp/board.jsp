<%@ page import="beans.Board" %>
<%@ page import="utils.Constantes" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Plateau 10x10</title>
    <style>
        .grid {
            display: grid;
            grid-template-columns: repeat(10, 60px);
            grid-template-rows: repeat(10, 60px);
            gap: 2px; /* Espace entre les carrés */
        }
        .cell {
            width: 60px;
            height: 60px;
            background-color: lightgray;
            border: 1px solid black;
        }
    </style>
</head>
<body>
    <div class="grid">
        <% 
	        beans.Board board = (beans.Board) request.getAttribute("board");
	        int[][] grid = board.getGrid();
            for (int i = 0; i < Constantes.BOARD_SIZE; i++) {
            	for(int j = 0; j < Constantes.BOARD_SIZE; j++){
        %>
            <div class="cell"><%= grid[i][j] %></div>
        <% 
            	}
            }
        %>
    </div>
</body>
</html>
