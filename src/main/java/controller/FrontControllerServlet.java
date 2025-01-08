/**
 * @file FrontControllerServlet.java
 * @brief Servlet class for handling front-end requests and managing game sessions.
 *
 * This class processes incoming requests, manages user sessions, and directs requests to the appropriate controllers
 * based on the action specified. It also handles game initialization and player management.
 */
package controller;

import model.Map;
import utils.ScoreDTO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.List;

import dao.DatabaseDAOImpl;

@WebServlet("/Board")
public class FrontControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @brief Handles POST requests to the servlet.
     * @param request The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // SESSION
        String idUserStr = (String) request.getSession().getAttribute("idUser");
        Map map = Map.getInstance();
        
        String newGame = (String) request.getParameter("newGame");

        if (idUserStr == null) {
            new LoginController().handleRequest(request, response);
        } 
        if(newGame != null && newGame.equals("true")) {
        	
        	System.out.println("FINN");
        	
        	if(map.isGameClose()) {
        		Map.resetInstance();
        		map = Map.getInstance();
        	}
        	
        	int idUser = Integer.parseInt(idUserStr);
        	
        	if (map.getCurrentNumberOfPlayer() == 0) {
                int idGame = DatabaseDAOImpl.createNewGame(idUser);
                map.setIdGame(idGame);
                System.out.println("idGame :" + idGame);
            }

            String name = DatabaseDAOImpl.getUserName(idUser);

            int nPlayerBefore = map.getCurrentNumberOfPlayer();
            int idPlayer = map.getIdNewPlayer(idUser, name);

            if (idPlayer > 0 && map.getCurrentNumberOfPlayer() > nPlayerBefore) {
                DatabaseDAOImpl.addPlayer(idUser, idPlayer, map.getIdGame());
            }

            System.out.println("New session player : " + idPlayer + "/ name :" + name);

            request.getSession().setAttribute("idPlayer", String.valueOf(idPlayer));
            request.getSession().setAttribute("idUser", String.valueOf(idUser));
            request.getSession().setAttribute("map", map);

            RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp");
            dispatcher.forward(request, response);
        	
        }
        else {
            Integer playerId = Integer.parseInt((String) request.getSession().getAttribute("idPlayer"));
            Integer idUser = Integer.parseInt(idUserStr);

            String action = request.getParameter("action");
            if (action != null && !action.isEmpty()) {
                if (action.equals("deconnexion")) {
                    request.getSession().invalidate();
                    request.getSession(true);

                    RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                    dispatcher.forward(request, response);
                } else if (action.equals("scoreGame") || action.equals("scoreHistory")) {
                    new ScoresController().handleRequest(request, response);
                } else {
                    new ActionsController().handleRequest(request, response);
                }
            } else {
                RequestDispatcher dispatcher;
                request.getSession().setAttribute("map", map);
                if (!map.isGameClose()) {
                    request.setAttribute("idPlayer", playerId);
                    dispatcher = request.getRequestDispatcher("game.jsp");
                } else {
                    List<ScoreDTO> scores = DatabaseDAOImpl.getScoreGame(map.getIdGame());
                    request.setAttribute("scores", scores);
                    request.getSession().setAttribute("idPlayer", "-1");
                    dispatcher = request.getRequestDispatcher("endGameScore.jsp");
                }

                System.out.println("ACTION: " + action + " / TOUR: " + map.getTurn());

                dispatcher.forward(request, response);
            }
        }
    }

    /**
     * @brief Handles GET requests to the servlet.
     * @param request The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
