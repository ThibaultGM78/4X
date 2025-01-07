package controller;


import java.io.IOException;

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
import model.Tile;
import java.util.Set;
import java.util.HashSet;



@WebServlet("/ActionController")
public class ActionsController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map map = Map.getInstance();
            String action = request.getParameter("action");
            Integer idPlayer = Integer.parseInt((String) request.getSession().getAttribute("idPlayer"));

            String posSoldier = request.getParameter("selectedSoldier"); 
            if (posSoldier != null && !posSoldier.isEmpty()) {
                // Traitement des actions de soldat (mouvement, foraging, heal, etc.)
                String[] posParts = posSoldier.split(",");
                int posSoldierX = Integer.parseInt(posParts[0]);
                int posSoldierY = Integer.parseInt(posParts[1]);

                System.out.println("idPlayer " + idPlayer);
                System.out.println("ACTION: " + action + " / TOUR: " + map.getTurn());

                // Mouvement des soldats
                if(action.equals("top")) {
                    this.actionMove(posSoldierX, posSoldierY, posSoldierX - 1, posSoldierY, idPlayer, map, request, response);
                }
                else if(action.equals("bottom")) {
                    this.actionMove(posSoldierX, posSoldierY, posSoldierX + 1, posSoldierY, idPlayer, map, request, response);
                }
                else if(action.equals("right")) {
                    this.actionMove(posSoldierX, posSoldierY, posSoldierX, posSoldierY - 1, idPlayer, map, request, response);
                }
                else if(action.equals("left")) {
                    this.actionMove(posSoldierX, posSoldierY, posSoldierX, posSoldierY + 1, idPlayer, map, request, response);
                }
                else if(action.equals("forage")) {
                    map.getActuelPlayer().addGold(Constantes.REWARD_FOREST_FORAGE);
                    map.getGrid()[posSoldierX][posSoldierY].getSoldier().setLastActionTurn(map.getTurn());
                    System.out.println("Forage");
                    this.gameRedirection(request, response, idPlayer, map);
                }
                else if(action.equals("heal")) {
                    map.getGrid()[posSoldierX][posSoldierY].getSoldier().receiveHeal(Constantes.SOLDIER_HEAL);
                    map.getGrid()[posSoldierX][posSoldierY].getSoldier().setLastActionTurn(map.getTurn());
                    System.out.println("Heal");
                    this.gameRedirection(request, response, idPlayer, map);
                }
            } else {
                // Si aucune action de soldat, on traite les actions de ville
                String posCity = request.getParameter("selectedCity"); 

                if (posCity != null && !posCity.isEmpty()) {
                    String[] posParts = posCity.split(",");
                    int posCityX = Integer.parseInt(posParts[0]);
                    int posCityY = Integer.parseInt(posParts[1]);

                    if(action.equals("former")) {
                        if(!map.getGrid()[posCityX][posCityY].isSoldier()) {
                            if(map.getPlayer(map.getIdPlayerTurn()).getGold() >= Constantes.COST_SOLDIER) {
                                map.getPlayer(map.getIdPlayerTurn()).retireGold(Constantes.COST_SOLDIER);
                                map.getGrid()[posCityX][posCityY].setSoldier(new Soldier(map.getIdPlayerTurn()));
                            }
                        }
                    }
                }

                // Changement de tour
                if(idPlayer == map.getIdPlayerTurn() && action != null) {
                    if(action.equals("endTurn")) {
                        map.nextTurn();
                    }
                }

                // Vérifier la condition de victoire après l'action du joueur
                if (checkVictoryCondition(idPlayer, map)) {
                    // Si la condition de victoire est remplie, afficher un message ou rediriger
                    System.out.println("Le joueur " + map.getPlayer(idPlayer).getName() + " a gagné !");
                    // Rediriger vers la page de victoire
                    response.sendRedirect("victory.jsp"); // Redirige vers la page de victoire
                    return; // Assurez-vous de sortir après la redirection
                } else {
                    // Si aucun joueur n'a gagné, continuer le jeu
                    request.setAttribute("idPlayer", idPlayer);
                    request.getSession().setAttribute("map", map);
                    request.getRequestDispatcher("game.jsp").forward(request, response); // Utilisation de forward sans envoyer de réponse avant
                }
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace();  // Gestion de l'exception IOException et ServletException
        }
    }

    

 // Méthode de vérification de la condition de victoire
    private boolean checkVictoryCondition(int idPlayer, Map map) {
        model.Player currentPlayer = map.getPlayer(idPlayer);
        System.out.println("Vérification de la condition de victoire pour le joueur " + currentPlayer.getName());

        // Vérifiez combien de joueurs sont actifs dans la partie
        int activePlayers = 0;
        for (model.Player player : map.getPlayers()) {
            if (player != null) {
                activePlayers++;
            }
        }

        // Si le joueur est seul dans la partie, seule la condition de l'or s'applique
        if (activePlayers == 1) {
            System.out.println("Le joueur " + currentPlayer.getName() + " est seul dans la partie.");
            if (currentPlayer.getGold() >= 100) {
                System.out.println("Le joueur " + currentPlayer.getName() + " a gagné avec " + currentPlayer.getGold() + " or.");
                return true;
            } else {
                System.out.println("Le joueur " + currentPlayer.getName() + " n'a pas encore atteint 100 or.");
                return false;
            }
        }

        // Condition 1 : Vérification de l'or du joueur
        if (currentPlayer != null && currentPlayer.getGold() >= 100) {
            System.out.println("Le joueur " + currentPlayer.getName() + " a gagné avec " + currentPlayer.getGold() + " or.");
            return true; // Le joueur a gagné avec plus de 100 or
        }

        // Condition 2 : Vérification des soldats restants sur la carte
        System.out.println("Analyse des soldats présents sur la carte :");
        boolean allSoldiersBelongToCurrentPlayer = true;

        // Liste pour suivre les joueurs avec des soldats
        Set<Integer> playersWithSoldiers = new HashSet<>();

        // Parcourir la grille pour identifier les soldats
        for (int x = 0; x < map.getGrid().length; x++) {
            for (int y = 0; y < map.getGrid()[x].length; y++) {
                Tile tile = map.getGrid()[x][y];
                if (tile.isSoldier()) {
                    int soldierOwner = tile.getSoldier().getIdPlayerOwner();
                    playersWithSoldiers.add(soldierOwner); // Ajouter le propriétaire à l'ensemble
                    System.out.println("Soldat trouvé : Position (" + x + ", " + y + "), Propriétaire : Joueur " + soldierOwner);
                }
            }
        }

        // Vérifier si tous les soldats appartiennent au joueur actuel
        for (Integer playerId : playersWithSoldiers) {
            if (playerId != idPlayer) {
                allSoldiersBelongToCurrentPlayer = false; // Si un autre joueur a des soldats, la condition échoue
                System.out.println("Le joueur " + map.getPlayer(playerId).getName() + " a encore des soldats.");
            }
        }

        // Si aucun autre joueur n'a de soldats, le joueur actuel a gagné
        if (allSoldiersBelongToCurrentPlayer) {
            System.out.println("Le joueur " + currentPlayer.getName() + " a gagné, car les autres joueurs n'ont plus de soldats.");
            return true;
        }

        System.out.println("Aucune condition de victoire remplie pour le joueur " + currentPlayer.getName() + ".");
        return false; // Aucune condition de victoire remplie
    }














    
    private void actionMove(int posX, int posY, int posX2, int posY2, int idPlayer, Map map, HttpServletRequest request, HttpServletResponse response) {

    	request.setAttribute("idPlayer",  idPlayer);
        request.getSession().setAttribute("map", map);
        
		RequestDispatcher dispatcher;
		
		if(map.getGrid()[posX2][posY2].isSoldier() && map.getGrid()[posX2][posY2].getSoldier().getIdPlayerOwner() != map.getIdPlayerTurn()) {
			
			request.setAttribute("atqX", posX);
			request.setAttribute("atqY", posY);
			request.setAttribute("defX", posX2);
			request.setAttribute("defX", posY2);
			
			Combat combat = new Combat(
					map.getGrid()[posX2][posY2].getSoldier().getDefensePoint(),
					map.getPlayer(map.getGrid()[posX][posY].getSoldier().getIdPlayerOwner()).getName(),
					map.getPlayer(map.getGrid()[posX2][posY2].getSoldier().getIdPlayerOwner()).getName(),
					Constantes.COMBAT_TYPE_SOLDIER
					);
			
			map.getGrid()[posX][posY].getSoldier().setLastActionTurn(map.getTurn());
			
			if(combat.isVictory()) {
				
				if(map.getGrid()[posX2][posY2].getType() != Constantes.TILE_TYPE_CITY) {
					map.move(posX, posY, posX2, posY2, idPlayer);
					DatabaseDAOImpl.combatWin(map.getIdGame(), idPlayer);
				}
				else {
					map.getGrid()[posX2][posY2].clearSoldier();
				}
				
			}
			else {
				map.getGrid()[posX2][posY2].getSoldier().setDefensePoint(combat.getDefLifeAfterCombat());
			}
			

			request.setAttribute("combat", combat);
			dispatcher = request.getRequestDispatcher("combat.jsp");
		}
		else if(map.getGrid()[posX2][posY2].getType() == Constantes.TILE_TYPE_CITY && map.getGrid()[posX2][posY2].getState() != map.getIdPlayerTurn()){
			request.setAttribute("atqX", posX);
			request.setAttribute("atqY", posY);
			request.setAttribute("defX", posX2);
			request.setAttribute("defX", posY2);
			
			int cityState = map.getGrid()[posX2][posY2].getState();
			String cityName = "Cité";
			if(cityState > 0 && cityState <= Constantes.MAP_N_PLAYER) {
				cityName = "Cité de " + map.getPlayer(cityState).getName();
			}
			
			Combat combat = new Combat(
					map.getGrid()[posX2][posY2].getDefense(),
					map.getPlayer(map.getGrid()[posX][posY].getSoldier().getIdPlayerOwner()).getName(),
					cityName,
					Constantes.COMBAT_TYPE_CITY
					);
			
			map.getGrid()[posX][posY].getSoldier().setLastActionTurn(map.getTurn());
			
			if(combat.isVictory()) {
				
				map.move(posX, posY, posX2, posY2, idPlayer);
				DatabaseDAOImpl.cityWin(map.getIdGame(), idPlayer);
				
			}
			else {
				map.getGrid()[posX2][posY2].receiveDamage(combat.getDefLifeAfterCombat());
			}
			
			request.setAttribute("combat", combat);
			dispatcher = request.getRequestDispatcher("combat.jsp");
		}
		else {
			map.move(posX, posY, posX2, posY2, idPlayer);
			
	        dispatcher = request.getRequestDispatcher("game.jsp");
		}
		
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
    
    private void gameRedirection(HttpServletRequest request, HttpServletResponse response, int idPlayer, Map map) {
    	
    	 request.setAttribute("idPlayer",  idPlayer);
	     request.getSession().setAttribute("map", map);
	     
	        
        RequestDispatcher dispatcher = request.getRequestDispatcher("game.jsp");

      
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
