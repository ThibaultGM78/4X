package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Map;

@WebServlet("/newGame")
public class NewGameController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Constructeur sans arguments, nécessaire pour l'instanciation du servlet
    public NewGameController() {
        super();  // Appel du constructeur parent (HttpServlet)
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Réinitialiser l'état du jeu
        Map map = Map.getInstance();
        map.reset();  // Appel de la méthode reset pour réinitialiser le jeu
        
        // Rediriger vers la page de jeu
        request.getSession().setAttribute("map", map);  // Assurez-vous que l'état du jeu est enregistré dans la session
        response.sendRedirect("game.jsp");  // Redirigez vers la page du jeu
    }
}
