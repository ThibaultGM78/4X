<%@ page import="model.Map" %>
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
	<%  
		model.Map map = (model.Map) session.getAttribute("map"); 
		Integer idPlayer = Integer.parseInt((String) session.getAttribute("idPlayer"));
	%>
	<div>
	    <div class="container">
	    	<div>
	    	<h1>4X</h1>
			<p>idPlayer == <%= (String) session.getAttribute("idPlayer") %> / idUser == <%= (String) session.getAttribute("idUser") %></p>
			<p>Argent: <%= map.getPlayer(idPlayer).getGold() %></p>
		    <% if(map.getIdPlayerTurn() == Integer.parseInt((String) session.getAttribute("idPlayer"))){
		    	%>
		    		<p>Ton tour</p>
		    		<div>
					    <h2>Action Soldat</h2>
					    <select id="posSoldat">
					        <option value="">Sélectionnez un soldat</option>
								<% 
									        
								String pos;
					            for (int i = 0; i < Constantes.MAP_SIZE; i++) {
					            	for(int j = 0; j < Constantes.MAP_SIZE; j++)
					            		if(map.getGrid()[i][j].isSoldier() && map.getGrid()[i][j].getSoldier().getIdPlayerOwner() == map.getIdPlayerTurn() 
					            			&& map.getGrid()[i][j].getSoldier().getLastActionTurn() < map.getTurn()
					            				){
					            			pos = i + "," + j;
					            			%> 
					            			<option value="<%= pos%>">Soldat <%= pos %></option>
					            			<%
					            		}
	
					         		}  
						         %>
					    </select>
					    <button type="submit" name="direction" value="top" onclick="setAction('top')">Haut</button>
				        <button type="submit" name="direction" value="bottom" onclick="setAction('bottom')">Bas</button>
				        <button type="submit" name="direction" value="right" onclick="setAction('right')">Gauche</button>
				        <button type="submit" name="direction" value="left" onclick="setAction('left')">Droite</button>
				        
				         <h2>Action Cité</h2>
				         <select id="posCity">
					        <option value="">Sélectionnez une cité</option>
								<%
					            for (int i = 0; i < Constantes.MAP_SIZE; i++) {
					            	for(int j = 0; j < Constantes.MAP_SIZE; j++)
					            		if(map.getGrid()[i][j].getType() == Constantes.TILE_TYPE_CITY && map.getGrid()[i][j].getState() == idPlayer) {
					            			pos = i + "," + j;
					            			%> 
					            			<option value="<%= pos%>">Cité <%= pos %></option>
					            			<%
					            		}
	
					         		}  
						         %>
					    </select>
					    <button type="submit" name="city" value="former" onclick="setAction('former')">Former une troupe</button>
					</div>
		    		
				    <button type="submit" onclick="setAction('endTurn')">Fin de tour</button>
				    <% } %>
		    
		    </div>
		    <div class="grid">
		        <% 
			       
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
					    	if(grid[i][j].isSoldier()){%>
					    		<img src="<%= grid[i][j].getOverlayImagePath() %>" class="overlay-image"/>
					    	<%}%>
					    	   
					    </div>
					</div>
		        <% 
		            	}
		            }
		        %>
		    </div>
	    </div>
    </div>
    <script>
    
        setInterval(function() {
            var form = document.createElement("form");
            form.method = "POST";
            form.action = "Board";  // URL de destination
            
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
            
            var selectedSoldier = document.getElementById("posSoldat").value;
            var soldierInput = document.createElement("input");
            soldierInput.type = "hidden";
            soldierInput.name = "selectedSoldier"; 
            soldierInput.value = selectedSoldier;
            form.appendChild(soldierInput);
            
            var selectedCity = document.getElementById("posCity").value;
            var cityInput = document.createElement("input");
            cityInput.type = "hidden";
            cityInput.name = "selectedCity"; 
            cityInput.value = selectedCity;
            form.appendChild(cityInput);

            document.body.appendChild(form);
            form.submit();
        }
        
    </script>
</body>
</html>
