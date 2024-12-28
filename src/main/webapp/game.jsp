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
	<p id="idPlayer" data-idPlayer="<%= request.getAttribute("idPlayer") %>"></p>
	<div>
		<h1>4X</h1>
		<p><%= request.getAttribute("idPlayer")  %><p>
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
	    <div>
	    <% if(map.getIdPlayerTurn() == (int) request.getAttribute("idPlayer")){
	    	%>
	    		<p>Ton tour</p>
			    <button type="submit" onclick="setAction('endTurn')">Fin de tour</button>
	    	<%
	    }
	    %>
	    
	    </div>
    </div>
    <script>
   	 	var idPlayer = document.getElementById("idPlayer").getAttribute("data-idPlayer");
   	 	
        setInterval(function() {
            //window.location.href = 'Board?idPlayer=' + idPlayer;

            //Crée un formulaire
            var form = document.createElement("form");
            form.method = "POST";
            form.action = "Board";  // URL de destination

            //Ajoute l'attribut idPlayer au formulaire
            var input = document.createElement("input");
            input.type = "hidden";
            input.name = "idPlayer";
            input.value = idPlayer;  
            form.appendChild(input);

            //Soumet le formulaire
            document.body.appendChild(form);
            form.submit();
        }, 5000);
        
        function setAction(action) {
    
        	var form = document.createElement("form");
            form.method = "POST";
            form.action = "Board";
            
            var actionInput = document.createElement("input");
            actionInput.type = "hidden";
            actionInput.name = "action"; 
            actionInput.value = action; 
            form.appendChild(actionInput);
            
            var input = document.createElement("input");
            input.type = "hidden";
            input.name = "idPlayer";
            input.value = idPlayer;  
            form.appendChild(input);
            
            console.log("coucou")
            
            document.body.appendChild(form);
            form.submit();
        }
        
    </script>
</body>
</html>
