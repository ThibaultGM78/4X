package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Map;

import java.io.IOException;
import dao.DatabaseDAOImpl;

@WebServlet("/ActionController")
public class ActionsController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        /*System.out.println("Action: ");
       
        String idUserStr = (String) request.getSession().getAttribute("idUser");
        String playerIdStr = (String) request.getSession().getAttribute("idPlayer");
	    String posSoldier = request.getParameter("selectedSoldier");
	    String action = request.getParameter("action");
	    
	    if(posSoldier != null && posSoldier != "") {
	    	   String[] posParts = posSoldier.split(",");
			    int posSoldierX = Integer.parseInt(posParts[0]);
			    int posSoldierY = Integer.parseInt(posParts[1]);
			    System.out.println( "Soldat " + posSoldierX + " et " + posSoldierY );
	    }
	 

	   int playerId;
	   if(playerIdStr == null ) {
		   playerId = map.getIdNewPlayer();
		   request.getSession().setAttribute("idPlayer", String.valueOf(playerId));
		   map.getPlayer(playerId).setIdUser(Integer.parseInt(idUserStr));
	   }
	   else {
		   playerId = Integer.parseInt(playerIdStr);
	   }
	   
	   if(playerId == map.getIdPlayerTurn() && action != null) {
		   
		   if(action.equals("endTurn")) {
			  map.setNextPlayerTurn();
			  map.nextTurn();
		   }
	   }
	 
       request.setAttribute("idPlayer", playerId);*/
      
    }
}
