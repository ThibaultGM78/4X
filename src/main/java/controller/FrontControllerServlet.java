package controller;

import model.Map;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;

@WebServlet("/Board")
public class FrontControllerServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
		//SESSION
		String idUserStr = (String) request.getSession().getAttribute("idUser");
		
		Map map = Map.getInstance();
	
		if(idUserStr == null) {
			new LoginController().handleRequest(request, response);
		}
		else {
			
		   Integer playerId = Integer.parseInt((String) request.getSession().getAttribute("idPlayer"));
		   
		   String action = request.getParameter("action");
		   if(action != null && !action.isEmpty()) {
			   
			   	if(action.equals("scoreGame") || action.equals("scoreHistory")) {
			   		new ScoresController().handleRequest(request, response);
			   	}
			   	else {
					new ActionsController().handleRequest(request, response);
			   	}
			}  
		   else {
				request.setAttribute("idPlayer", playerId);
				request.getSession().setAttribute("map", map);
		        RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp");
		        dispatcher.forward(request, response);
		        
		        System.out.println("Refresh: " + playerId + " / " + action + "/ " + map.getIdPlayerTurn());
		   }

		}
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doPost(request, response);
    }
}
