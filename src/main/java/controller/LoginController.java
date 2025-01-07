/**
 * @file LoginController.java
 * @brief Servlet class for handling user login and registration.
 *
 * This class processes login and registration requests, authenticates users,
 * and manages user sessions. It also handles the creation of new games and players.
 */
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

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @brief Handles the request to display the login page.
     * @param request The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     */
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Login: ");

        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");

        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Handles POST requests to the servlet.
     * @param request The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException If an I/O error occurs.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // LOGIN
        if ("login".equals(action)) {
            String username = request.getParameter("username");
            String mdp = request.getParameter("mdp");

            Integer idUser = DatabaseDAOImpl.authenticateUser(username, mdp);
            if (idUser >= 0) {
                Map map = Map.getInstance();

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
            } else {
                request.setAttribute("error", "Nom d'utilisateur ou mot de passe incorrect");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }
        // REGISTER
        else if ("register".equals(action)) {
            String username = request.getParameter("username");
            String mdp = request.getParameter("mdp");
            String confirmMdp = request.getParameter("confirm_mdp");

            if (mdp.equals(confirmMdp)) {
                if (DatabaseDAOImpl.createUser(username, mdp)) {
                    response.sendRedirect("login.jsp");
                } else {
                    request.setAttribute("error", "Ce nom d'utilisateur est déjà pris");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("error", "Les mots de passe ne correspondent pas");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }
    }
}
