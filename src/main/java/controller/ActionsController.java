package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Combat;
import model.Map;
import model.Soldier;
import utils.Constantes;

@WebServlet("/ActionController")
public class ActionsController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
    	
    	Map map = Map.getInstance();
    	String action = request.getParameter("action");
    	Integer idPlayer = Integer.parseInt((String) request.getSession().getAttribute("idPlayer"));
    	
    	String posSoldier = request.getParameter("selectedSoldier"); 
	    if(posSoldier != null && posSoldier != "") {
	    	   	String[] posParts = posSoldier.split(",");
			    int posSoldierX = Integer.parseInt(posParts[0]);
			    int posSoldierY = Integer.parseInt(posParts[1]);

			    System.out.println("idPlayer " + idPlayer);
			    
		        System.out.println("ACTION: " + action + " / TOUR: " + map.getTurn());
			    if(action.equals("top") ) {
			    	//map.move(posSoldierX, posSoldierY, posSoldierX - 1, posSoldierY, idPlayer);
			    	this.actionMove(posSoldierX, posSoldierY, posSoldierX - 1, posSoldierY, idPlayer, map, request, response);
			    }
			    if(action.equals("bottom")) {
			    	//map.move(posSoldierX, posSoldierY, posSoldierX + 1, posSoldierY,  idPlayer);
			    	this.actionMove(posSoldierX, posSoldierY, posSoldierX + 1, posSoldierY, idPlayer, map, request, response);
			    }
			    if(action.equals("right")) {
			    	//map.move(posSoldierX, posSoldierY, posSoldierX, posSoldierY - 1, idPlayer);
			    	this.actionMove(posSoldierX, posSoldierY, posSoldierX, posSoldierY - 1, idPlayer, map, request, response);
			    }
			    if(action.equals("left")) {
			    	//map.move(posSoldierX, posSoldierY, posSoldierX, posSoldierY + 1, idPlayer);
			    	this.actionMove(posSoldierX, posSoldierY, posSoldierX, posSoldierY + 1, idPlayer, map, request, response);
			    }
	    }
	    else {
	    	 String posCity = request.getParameter("selectedCity"); 
	 	    if(posCity != null && posCity != "") {
	 	    	   	String[] posParts = posCity.split(",");
	 			    int posCityX = Integer.parseInt(posParts[0]);
	 			    int posCityY = Integer.parseInt(posParts[1]);
	 			    
	 			    if(action.equals("former")) {
	 			    	if(!map.getGrid()[posCityX][posCityY].isSoldier()) {
	 			    		
	 			    		if(map.getPlayer(map.getIdPlayerTurn()).getGold() >= Constantes.COST_SOLDIER) {
	 			    			map.getPlayer(map.getIdPlayerTurn()).retireGold(Constantes.COST_SOLDIER);
	 			    			map.getGrid()[posCityX][posCityY].setSoldier(new Soldier(map.getIdPlayerTurn()));
	 			    		}
	 			    		
	 			    		
	 			    	}
	 			    }
	 	    }
	 	 
	 	   if(idPlayer == map.getIdPlayerTurn() && action != null) {
	 		   
	 		   if(action.equals("endTurn")) {
	 			  
	 			   map.nextTurn();
	 			  
	 			   
	 		   }
	 	   }
	 	 
	        request.setAttribute("idPlayer",  idPlayer);
	        request.getSession().setAttribute("map", map);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp");
	       
	        
	        System.out.println("ACTION: " + action + " / TOUR: " + map.getTurn());
	        
	        try {
	 		dispatcher.forward(request, response);
	 		} catch (ServletException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		} catch (IOException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
	    }
	   
    }
    
    private void actionMove(int posX, int posY, int posX2, int posY2, int idPlayer, Map map, HttpServletRequest request, HttpServletResponse response) {

    	request.setAttribute("idPlayer",  idPlayer);
        request.getSession().setAttribute("map", map);
        
    	if(map.canMove(posX2, posY2)) {
    		
    		RequestDispatcher dispatcher;
    		
    		if(map.getGrid()[posX2][posY2].isSoldier() && map.getGrid()[posX2][posY2].getSoldier().getIdPlayerOwner() != map.getIdPlayerTurn()) {
    			
    			request.setAttribute("atqX", posX);
    			request.setAttribute("atqY", posY);
    			request.setAttribute("defX", posX2);
    			request.setAttribute("defX", posY2);
    			
    			Combat combat = new Combat(
    					map.getGrid()[posX2][posY2].getSoldier().getDefensePoint(),
    					map.getPlayer(map.getGrid()[posX][posY].getSoldier().getIdPlayerOwner()),
    					map.getPlayer(map.getGrid()[posX2][posY2].getSoldier().getIdPlayerOwner()),
    					Constantes.COMBAT_TYPE_SOLDIER
    					);
    			
    			map.getGrid()[posX][posY].getSoldier().setLastActionTurn(map.getTurn());
    			
				if(combat.isVictory()) {
					
					if(map.getGrid()[posX2][posY2].getType() != Constantes.TILE_TYPE_CITY) {
						map.move(posX, posY, posX2, posY2, idPlayer);
					}
					else {
						map.getGrid()[posX2][posY2].clearSoldier();
					}
					
				}
				else {
					map.getGrid()[posX2][posY2].getSoldier().setDefensePoint(combat.getDefLifeAfterCombat());
				}
    			
    
    			request.setAttribute("combat", combat);
    			dispatcher = request.getRequestDispatcher("combat.jsp");
    		}
    		else if(map.getGrid()[posX2][posY2].getType() == Constantes.TILE_TYPE_CITY && map.getGrid()[posX2][posY2].getState() != map.getIdPlayerTurn()){
    			request.setAttribute("atqX", posX);
    			request.setAttribute("atqY", posY);
    			request.setAttribute("defX", posX2);
    			request.setAttribute("defX", posY2);
    			
    			Combat combat = new Combat(
    					map.getGrid()[posX2][posY2].getDefense(),
    					map.getPlayer(map.getGrid()[posX][posY].getSoldier().getIdPlayerOwner()),
    					map.getPlayer(map.getGrid()[posX2][posY2].getState()),
    					Constantes.COMBAT_TYPE_CITY
    					);
    			
    			map.getGrid()[posX][posY].getSoldier().setLastActionTurn(map.getTurn());
    			
				if(combat.isVictory()) {
					
					map.move(posX, posY, posX2, posY2, idPlayer);
					
				}
				else {
					map.getGrid()[posX2][posY2].receiveDamage(combat.getDefLifeAfterCombat());
				}
    			
    			request.setAttribute("combat", combat);
    			dispatcher = request.getRequestDispatcher("combat.jsp");
    		}
    		else {
    			map.move(posX, posY, posX2, posY2, idPlayer);
    			
		        dispatcher = request.getRequestDispatcher("game.jsp");
    		}
    		
		      try {
		 		dispatcher.forward(request, response);
		 		} catch (ServletException e) {
		 			// TODO Auto-generated catch block
		 			e.printStackTrace();
		 		} catch (IOException e) {
		 			// TODO Auto-generated catch block
		 			e.printStackTrace();
		 		}
    	}
    	else {
    		request.setAttribute("idPlayer",  idPlayer);
	        request.getSession().setAttribute("map", map);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp");
	       
	        System.out.println("Cant move");
	        
	        try {
	 		dispatcher.forward(request, response);
	 		} catch (ServletException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		} catch (IOException e) {
	 			// TODO Auto-generated catch block
	 			e.printStackTrace();
	 		}
    	}
    }
}
