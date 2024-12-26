<%@ page import="beans.Map" %>
<%@ page import="utils.Constantes" %>
<%@ page import="model.Tile" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Plateau 10x10</title>
    <link rel="stylesheet" type="text/css" href="css/game.css"> 
</head>
<body>
	<h1>Test</h1>
    <div class="grid">
        <% 
	        beans.Map map = (beans.Map) request.getAttribute("map");
	        Tile[][] grid = map.getGrid();
	        String color;
            for (int i = 0; i < Constantes.MAP_SIZE; i++) {
            	for(int j = 0; j < Constantes.MAP_SIZE; j++){   
   		
        %>
            <div class="<%= grid[i][j].getCellCSSClass() %>">
			    <div class="image-container ">
			    	<%if(grid[i][j].getType() != 0){%>
			    		
			    		<img src="<%= grid[i][j].getImagePath() %>" class="background-image"/>
			    	<%
			    	}
			    	if(grid[i][j].getSoldier() != null){%>
			    		<img src="<%= grid[i][j].getOverlayImagePath() %>" class="overlay-image"/>
			    	<%}%>
			    	   
			    </div>
			</div>
        <% 
            	}
            }
        %>
    </div>
</body>
</html>
