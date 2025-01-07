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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            request.setAttribute("error", "Action non spécifiée.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        switch (action) {
            case "login":
                handleLogin(request, response);
                break;

            case "register":
                handleRegister(request, response);
                break;

            default:
                request.setAttribute("error", "Action inconnue.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    protected void handleRequest(HttpServletRequest request, HttpServletResponse response) {
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

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String mdp = request.getParameter("mdp");

        if (username == null || username.trim().isEmpty() || mdp == null || mdp.trim().isEmpty()) {
            request.setAttribute("error", "Veuillez remplir tous les champs.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        try {
            Integer idUser = DatabaseDAOImpl.authenticateUser(username, mdp);
            if (idUser != null && idUser >= 0) {
                Map map = Map.getInstance();

                if (map.getCurrentNumberOfPlayer() == 0) {
                    int idGame = DatabaseDAOImpl.createNewGame(idUser);
                    map.setIdGame(idGame);
                }

                String name = DatabaseDAOImpl.getUserName(idUser);
                int nPlayerBefore = map.getCurrentNumberOfPlayer();
                int idPlayer = map.getIdNewPlayer(idUser, name);

                if (idPlayer > 0 && map.getCurrentNumberOfPlayer() > nPlayerBefore) {
                    DatabaseDAOImpl.addPlayer(idUser, idPlayer, map.getIdGame());
                }

                request.getSession().setAttribute("idPlayer", String.valueOf(idPlayer));
                request.getSession().setAttribute("idUser", String.valueOf(idUser));
                request.getSession().setAttribute("map", map);

                RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp");
                dispatcher.forward(request, response);
            } else {
                request.setAttribute("error", "Nom d'utilisateur ou mot de passe incorrect.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur interne. Veuillez réessayer plus tard.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String mdp = request.getParameter("mdp");
        String confirmMdp = request.getParameter("confirm_mdp");

        if (username == null || username.trim().isEmpty() || mdp == null || mdp.trim().isEmpty() || confirmMdp == null || confirmMdp.trim().isEmpty()) {
            request.setAttribute("error", "Veuillez remplir tous les champs.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        if (!mdp.equals(confirmMdp)) {
            request.setAttribute("error", "Les mots de passe ne correspondent pas.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        try {
            boolean userCreated = DatabaseDAOImpl.createUser(username, mdp);
            if (userCreated) {
                response.sendRedirect("login.jsp");
            } else {
                request.setAttribute("error", "Ce nom d'utilisateur est déjà pris.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur interne. Veuillez réessayer plus tard.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
