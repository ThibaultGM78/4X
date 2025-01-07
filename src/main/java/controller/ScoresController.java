/**
 * @file ScoresController.java
 * @brief Servlet class for handling score-related requests.
 *
 * This class processes requests to view the scores of the current game and the score history of a user.
 * It retrieves the scores from the database and forwards the request to the appropriate JSP page for display.
 */
package controller;

import java.io.IOException;
import java.util.List;

import dao.DatabaseDAOImpl;
import utils.ScoreDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Map;

@WebServlet("/ScoresController")
public class ScoresController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @brief Handles the request to display the scores.
     * @param request The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     */
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");

        if (!action.isEmpty() && action.equals("scoreGame")) {
            Map map = Map.getInstance();
            List<ScoreDTO> scores = DatabaseDAOImpl.getScoreGame(map.getIdGame());

            request.setAttribute("scores", scores);
            request.getSession().setAttribute("map", map);
            RequestDispatcher dispatcher = request.getRequestDispatcher("scoreGame.jsp");

            try {
                dispatcher.forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (!action.isEmpty() && action.equals("scoreHistory")) {
            int idUser = Integer.parseInt((String) request.getSession().getAttribute("idUser"));
            List<ScoreDTO> scores = DatabaseDAOImpl.getScoreHistory(idUser);

            request.setAttribute("scores", scores);
            RequestDispatcher dispatcher = request.getRequestDispatcher("scoreHistory.jsp");

            try {
                dispatcher.forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
