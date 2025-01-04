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
	    	<div class="info">
	    	<h1>4X</h1>
			<p>idPlayer == <%= (String) session.getAttribute("idPlayer") %> / idUser == <%= (String) session.getAttribute("idUser") %></p>
			<button type="submit" onclick="setAction('scoreHistory')">Afficher l'historiuqe de score</button>
			<% if(idPlayer > 0) {
				%>
				<p>Joueur: <%=(String) map.getPlayer(idPlayer).getName()%></p>
				<p>Argent: <%= (Integer) map.getPlayer(idPlayer).getGold()%></p>
				<%
			}
			%>
			
			<p>Tour de <%= map.getActuelPlayer().getName() %></p>
		    <% if(map.getIdPlayerTurn() == idPlayer){
		    	%>
		    		<div>
					    <h4>Action Soldat</h4>
						<div id="soldier-actions">
						    <%
						        String pos;
						        for (int i = 0; i < Constantes.MAP_SIZE; i++) {
						            for (int j = 0; j < Constantes.MAP_SIZE; j++) {
						                if (map.getGrid()[i][j].isSoldier() &&
						                    map.getGrid()[i][j].getSoldier().getIdPlayerOwner() == map.getIdPlayerTurn() 
						                    ) {
						
						                    pos = i + "," + j;
						    %>
						        <div class="soldier-row">
						            <span>Soldat [<%= pos %>]; defense = <%= map.getGrid()[i][j].getSoldier().getDefensePoint() %>/<%= map.getGrid()[i][j].getSoldier().getMaxDefensePoint() %> :</span>
						            
						            <% if( map.getGrid()[i][j].getSoldier().getLastActionTurn() < map.getTurn()) {
						             %>
						            <% 
						            if(map.canMove(i - 1, j)){
						            	%><button type="button" onclick="setActionSoldier('top', '<%= pos %>')">Haut</button><% 
						            }
						            %>
						            <% 
						            if(map.canMove(i + 1, j)){
						            	%><button type="button" onclick="setActionSoldier('bottom', '<%= pos %>')">Bas</button><% 
						            }
						            %>
						            <% 
						            if(map.canMove(i, j - 1)){
						            	%><button type="button" onclick="setActionSoldier('right', '<%= pos %>')">Gauche</button><% 
						            }
						            %>
						            <% 
						            if(map.canMove(i, j + 1)){
						            	%><button type="button" onclick="setActionSoldier('left', '<%= pos %>')">Droite</button><% 
						            }
						            %>   
						            <% 
						            if(map.getGrid()[i][j].getType() == Constantes.TILE_TYPE_FOREST){
						            	%><button type="button" onclick="setActionSoldier('forage', '<%= pos %>')">Forage</button><% 
						            }
						            %>
						           <% 
						            if(map.getGrid()[i][j].getSoldier().isBlessed()){
						            	%><button type="button" onclick="setActionSoldier('heal', '<%= pos %>')">Soin</button><% 
						            }
						            }
						            %>
						        </div>
						    <%
						                }
						            }
						        }
						    %>
						</div>
					    
				        <div>

					        <h4>Action Cité</h4>
							<div id="city-actions">
							    <%
							        for (int i = 0; i < Constantes.MAP_SIZE; i++) {
							            for (int j = 0; j < Constantes.MAP_SIZE; j++) {
							                if (map.getGrid()[i][j].getType() == Constantes.TILE_TYPE_CITY &&
							                    map.getGrid()[i][j].getState() == idPlayer) {
							
							                    pos = i + "," + j;
							    %>
							        <div class="city-row">
							            <span>Cité [<%= pos %>]:</span>
							            
							            <% if(map.getActuelPlayer().getGold() >= Constantes.COST_SOLDIER && !map.getGrid()[i][j].isSoldier()){
							            	%><button type="button" onclick="setActionCity('former', '<%= pos %>')">Former une troupe</button><%
							            }%>
							        </div>
							    <%
							                }
							            }
							        }
							    %>
							</div>   
					    </div>
					</div>
		    		
				    <button type="submit" onclick="setAction('endTurn')">Fin de tour</button>
				    <% } %>
		    		<button type="submit" onclick="setAction('scoreGame')">Afficher le score</button>
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
            
            document.body.appendChild(form);
            form.submit();
        }
        
        function setActionSoldier(action,pos){
        	var form = document.createElement("form");
            form.method = "POST";
            form.action = "Board";
            
            var actionInput = document.createElement("input");
            actionInput.type = "hidden";
            actionInput.name = "action"; 
            actionInput.value = action; 
            form.appendChild(actionInput);
            
            var soldierInput = document.createElement("input");
            soldierInput.type = "hidden";
            soldierInput.name = "selectedSoldier"; 
            soldierInput.value = pos;
            form.appendChild(soldierInput);
            
            document.body.appendChild(form);
            form.submit();
        }
        
        function setActionCity(action,pos){
        	var form = document.createElement("form");
            form.method = "POST";
            form.action = "Board";
            
            var actionInput = document.createElement("input");
            actionInput.type = "hidden";
            actionInput.name = "action"; 
            actionInput.value = action; 
            form.appendChild(actionInput);
            
            var cityInput = document.createElement("input");
            cityInput.type = "hidden";
            cityInput.name = "selectedCity"; 
            cityInput.value = pos;
            form.appendChild(cityInput);
            
            document.body.appendChild(form);
            form.submit();
        }
    </script>
</body>
</html>
