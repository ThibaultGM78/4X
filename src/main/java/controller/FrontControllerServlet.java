package controller;

import model.Map;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;

@WebServlet("/Board")
public class FrontControllerServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
	private  Map map = new Map();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		String idUserStr = (String) request.getSession().getAttribute("idUser");
		
		if(idUserStr == null) {
			new LoginController().handleRequest(request, response);
		}
		else {
			String playerIdStr = (String) request.getSession().getAttribute("idPlayer");
			int playerId;
			if(playerIdStr == null ) {
			   playerId = map.getIdNewPlayer();
			   request.getSession().setAttribute("idPlayer", String.valueOf(playerId));
			   map.getPlayer(playerId).setIdUser(Integer.parseInt(idUserStr));
		   }
		   else {
			   playerId = Integer.parseInt(playerIdStr);
		   }
		
			String action = request.getParameter("action");
			if(action != null && !action.isEmpty()) {
				//new ActionsController().handleRequest(request, response, map);
				
				 
				    String posSoldier = request.getParameter("selectedSoldier"); 
				    if(posSoldier != null && posSoldier != "") {
				    	   String[] posParts = posSoldier.split(",");
						    int posSoldierX = Integer.parseInt(posParts[0]);
						    int posSoldierY = Integer.parseInt(posParts[1]);
						    
						    if(action.equals("top")) {
						    	map.move(posSoldierX, posSoldierY, posSoldierX - 1, posSoldierY);
						    }
						    if(action.equals("bottom")) {
						    	map.move(posSoldierX, posSoldierY, posSoldierX + 1, posSoldierY);
						    }
						    if(action.equals("right")) {
						    	map.move(posSoldierX, posSoldierY, posSoldierX, posSoldierY - 1);
						    }
						    if(action.equals("left")) {
						    	map.move(posSoldierX, posSoldierY, posSoldierX, posSoldierY + 1);
						    }
						    
				    }
				 
				   if(playerId == map.getIdPlayerTurn() && action != null) {
					   
					   if(action.equals("endTurn")) {
						   this.map.setNextPlayerTurn();
						   this.map.nextTurn();
					   }
				   }
			}
			
		   
		 
	       request.setAttribute("idPlayer", playerId);
			request.setAttribute("map", map);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp");
	        dispatcher.forward(request, response);
	        
	          
	        System.out.println("Refresh: " + playerId + " / " + action + "/ " + this.map.getIdPlayerTurn());
	}
		
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}
