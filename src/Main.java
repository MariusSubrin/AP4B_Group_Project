import controller.CoreGame;
import view.LoveLetterView;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            CoreGame.view = new LoveLetterView();
            new Thread(() -> {
                CoreGame.lancerPartie();
            }).start();
        });
    }
}

/*
Listes de tests à faire (non compléte) :
- Utilisation de toutes les cartes en lançant le jeu

Soucis lorsque qu'on joue notre dame

Revoir les consignes pour savoir si tout est bien fait
- Gestion des exceptions

Diagramme de Gantt
 */

/*
MVC :
Afficher / demander à l’utilisateur	: View
Règles du jeu / décisions : Controller
Données (Player, Card…) : Model
 */