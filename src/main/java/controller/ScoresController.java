package controller;

import java.io.IOException;
import java.util.ArrayList;
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
