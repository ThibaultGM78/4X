package controller;

import beans.Map;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;

@WebServlet("/Board")
public class BoardServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
	private  Map map = new Map();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		//Map map = new Map();
		
	   String playerIdParam = request.getParameter("idPlayer");
	   String action = request.getParameter("action");
	   
	   int playerId = (playerIdParam != null) ? Integer.parseInt(playerIdParam) : map.getIdNewPlayer();
	   
	   if(playerId == map.getIdPlayerTurn() && action != null) {
		   
		   if(action.equals("endTurn")) {
			   System.out.println("hello");
			   this.map.setNextPlayerTurn();
		   }
	   }
	 
       request.setAttribute("idPlayer", playerId);
		
		request.setAttribute("map", map);
        RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp");
        dispatcher.forward(request, response);
        System.out.println("Refresh: " + playerId + " / " + action + "/ " + this.map.getIdPlayerTurn());
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}
