package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import dao.DatabaseDAOImpl;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Login: ");
       
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        //LOGIN
        if ("login".equals(action)) {
            String username = request.getParameter("username");
            String mdp = request.getParameter("mdp");

            Integer idUser = DatabaseDAOImpl.authenticateUser(username, mdp);
            if (idUser != null) {
                System.out.println("Succes: id -> " + idUser);
                request.getSession().setAttribute("idUser", String.valueOf(idUser));
                request.getRequestDispatcher("Board").forward(request, response);
                
            } else {
                request.setAttribute("error", "Nom d'utilisateur ou mot de passe incorrect");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        }
        //REGISTER
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
