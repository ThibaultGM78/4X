package controller;

import model.Map;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.SQLException;

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
			
			String action = request.getParameter("action");
		    String playerIdStr = (String) request.getSession().getAttribute("idPlayer");

		   int playerId;
		   if(playerIdStr == null ) {
			   playerId = map.getIdNewPlayer();
			   request.getSession().setAttribute("idPlayer", String.valueOf(playerId));
		   }
		   else {
			   playerId = Integer.parseInt(playerIdStr);
		   }
		   
		   if(playerId == map.getIdPlayerTurn() && action != null) {
			   
			   if(action.equals("endTurn")) {
				   this.map.setNextPlayerTurn();
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
