/**
 * @file ActionsController.java
 * @brief Servlet class for handling game actions.
 *
 * This class processes various game actions such as moving soldiers, foraging, healing,
 * forming soldiers, and ending turns. It interacts with the game map and updates the game state
 * accordingly.
 */
package controller;

import java.io.IOException;
import java.util.List;

import dao.DatabaseDAOImpl;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Combat;
import model.Map;
import model.Soldier;
import utils.Constantes;
import utils.ScoreDTO;

@WebServlet("/ActionController")
public class ActionsController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @brief Handles the incoming request and performs the corresponding game action.
     * @param request The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     */
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        Map map = Map.getInstance();
        String action = request.getParameter("action");
        Integer idPlayer = Integer.parseInt((String) request.getSession().getAttribute("idPlayer"));

        String posSoldier = request.getParameter("selectedSoldier");
        if (posSoldier != null && !posSoldier.isEmpty()) {
            String[] posParts = posSoldier.split(",");
            int posSoldierX = Integer.parseInt(posParts[0]);
            int posSoldierY = Integer.parseInt(posParts[1]);

            System.out.println("idPlayer " + idPlayer);
            System.out.println("ACTION: " + action + " / TOUR: " + map.getTurn());

            if (action.equals("top")) {
                this.actionMove(posSoldierX, posSoldierY, posSoldierX - 1, posSoldierY, idPlayer, map, request, response);
            } else if (action.equals("bottom")) {
                this.actionMove(posSoldierX, posSoldierY, posSoldierX + 1, posSoldierY, idPlayer, map, request, response);
            } else if (action.equals("right")) {
                this.actionMove(posSoldierX, posSoldierY, posSoldierX, posSoldierY - 1, idPlayer, map, request, response);
            } else if (action.equals("left")) {
                this.actionMove(posSoldierX, posSoldierY, posSoldierX, posSoldierY + 1, idPlayer, map, request, response);
            } else if (action.equals("forage")) {
                map.getActuelPlayer().addGold(Constantes.REWARD_FOREST_FORAGE);
                map.getGrid()[posSoldierX][posSoldierY].getSoldier().setLastActionTurn(map.getTurn());
                System.out.println("Forage");
                this.gameRedirection(request, response, idPlayer, map);
            } else if (action.equals("heal")) {
                map.getGrid()[posSoldierX][posSoldierY].getSoldier().receiveHeal(Constantes.SOLDIER_HEAL);
                map.getGrid()[posSoldierX][posSoldierY].getSoldier().setLastActionTurn(map.getTurn());
                System.out.println("Heal");
                this.gameRedirection(request, response, idPlayer, map);
            }
        } else {
            String posCity = request.getParameter("selectedCity");
            int idWinner = -1;

            if (posCity != null && !posCity.isEmpty()) {
                String[] posParts = posCity.split(",");
                int posCityX = Integer.parseInt(posParts[0]);
                int posCityY = Integer.parseInt(posParts[1]);

                if (action.equals("former")) {
                    if (!map.getGrid()[posCityX][posCityY].isSoldier()) {
                        if (map.getPlayer(map.getIdPlayerTurn()).getGold() >= Constantes.COST_SOLDIER) {
                            map.getPlayer(map.getIdPlayerTurn()).retireGold(Constantes.COST_SOLDIER);
                            map.getGrid()[posCityX][posCityY].setSoldier(new Soldier(map.getIdPlayerTurn()));
                        }
                    }
                }
            }

            if (idPlayer == map.getIdPlayerTurn() && action != null) {
                if (action.equals("endTurn")) {
                    idWinner = map.determineWinner();
                    System.out.println("WINNER PLAYER N:" + idWinner);
                    if (idWinner == -1)
                        map.nextTurn();
                }
            }

            RequestDispatcher dispatcher;
            request.getSession().setAttribute("map", map);
            if (idWinner == -1) {
                request.setAttribute("idPlayer", idPlayer);
                dispatcher = request.getRequestDispatcher("game.jsp");
            } else {
                List<ScoreDTO> scores = DatabaseDAOImpl.getScoreGame(map.getIdGame());
                request.setAttribute("scores", scores);
                request.getSession().setAttribute("idPlayer", "-1");
                dispatcher = request.getRequestDispatcher("endGameScore.jsp");
            }

            System.out.println("ACTION: " + action + " / TOUR: " + map.getTurn());

            try {
                dispatcher.forward(request, response);
                System.out.println("rere");
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @brief Handles the move action for a soldier.
     * @param posX The current x-coordinate of the soldier.
     * @param posY The current y-coordinate of the soldier.
     * @param posX2 The target x-coordinate for the move.
     * @param posY2 The target y-coordinate for the move.
     * @param idPlayer The ID of the player.
     * @param map The game map.
     * @param request The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     */
    private void actionMove(int posX, int posY, int posX2, int posY2, int idPlayer, Map map, HttpServletRequest request,
            HttpServletResponse response) {

        request.setAttribute("idPlayer", idPlayer);
        request.getSession().setAttribute("map", map);

        RequestDispatcher dispatcher;

        if (map.getGrid()[posX2][posY2].isSoldier()
                && map.getGrid()[posX2][posY2].getSoldier().getIdPlayerOwner() != map.getIdPlayerTurn()) {

            request.setAttribute("atqX", posX);
            request.setAttribute("atqY", posY);
            request.setAttribute("defX", posX2);
            request.setAttribute("defY", posY2);

            Combat combat = new Combat(map.getGrid()[posX2][posY2].getSoldier().getDefensePoint(),
                    map.getPlayer(map.getGrid()[posX][posY].getSoldier().getIdPlayerOwner()).getName(),
                    map.getPlayer(map.getGrid()[posX2][posY2].getSoldier().getIdPlayerOwner()).getName(),
                    Constantes.COMBAT_TYPE_SOLDIER);

            map.getGrid()[posX][posY].getSoldier().setLastActionTurn(map.getTurn());

            if (combat.isVictory()) {
                if (map.getGrid()[posX2][posY2].getType() != Constantes.TILE_TYPE_CITY) {
                    map.move(posX, posY, posX2, posY2, idPlayer);
                    DatabaseDAOImpl.combatWin(map.getIdGame(), idPlayer);
                } else {
                    map.getGrid()[posX2][posY2].clearSoldier();
                }
            } else {
                map.getGrid()[posX2][posY2].getSoldier().setDefensePoint(combat.getDefLifeAfterCombat());
            }

            request.setAttribute("combat", combat);
            dispatcher = request.getRequestDispatcher("combat.jsp");
        } else if (map.getGrid()[posX2][posY2].getType() == Constantes.TILE_TYPE_CITY
                && map.getGrid()[posX2][posY2].getState() != map.getIdPlayerTurn()) {
            request.setAttribute("atqX", posX);
            request.setAttribute("atqY", posY);
            request.setAttribute("defX", posX2);
            request.setAttribute("defY", posY2);

            int cityState = map.getGrid()[posX2][posY2].getState();
            String cityName = "Cité";
            if (cityState > 0 && cityState <= Constantes.MAP_N_PLAYER) {
                cityName = "Cité de " + map.getPlayer(cityState).getName();
            }

            Combat combat = new Combat(map.getGrid()[posX2][posY2].getDefense(),
                    map.getPlayer(map.getGrid()[posX][posY].getSoldier().getIdPlayerOwner()).getName(), cityName,
                    Constantes.COMBAT_TYPE_CITY);

            map.getGrid()[posX][posY].getSoldier().setLastActionTurn(map.getTurn());

            if (combat.isVictory()) {
                map.move(posX, posY, posX2, posY2, idPlayer);
                DatabaseDAOImpl.cityWin(map.getIdGame(), idPlayer);
                map.getActuelPlayer().cityWin();
            } else {
                map.getGrid()[posX2][posY2].receiveDamage(combat.getDefLifeAfterCombat());
            }

            request.setAttribute("combat", combat);
            dispatcher = request.getRequestDispatcher("combat.jsp");
        } else {
            map.move(posX, posY, posX2, posY2, idPlayer);
            dispatcher = request.getRequestDispatcher("game.jsp");
        }

        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Redirects to the game page.
     * @param request The HttpServletRequest object.
     * @param response The HttpServletResponse object.
     * @param idPlayer The ID of the player.
     * @param map The game map.
     */
    private void gameRedirection(HttpServletRequest request, HttpServletResponse response, int idPlayer, Map map) {
        request.setAttribute("idPlayer", idPlayer);
        request.getSession().setAttribute("map", map);

        RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp");

        try {
            dispatcher.forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
