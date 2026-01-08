import controller.CoreGame;
import view.LoveLetterView;

public class Main {
    //Doit appeler les bons fichiers au début

    // A chaque début de manche il faudra utiliser newRound pour chaque joueur

    public static void main(String[] args) {
        CoreGame.lancerPartie();
        new LoveLetterView();
    }
}

/*
Listes de tests à faire (non compléte) :
- Utilisation de toutes les cartes en lançant le jeu

Soucis lorsque qu'on joue notre dame

Revoir les consignes pour savoir si tout est bien fait
- Gestion des exceptions

Diagramme de Gantt

On pourrait attaquer automatiquement l'autre joueur quand on est que 2 et qu'il n'y a donc pas de choix à faire

Faire un read me pour annoncer la version java notamment
 */

/*
MVC :
Afficher / demander à l’utilisateur	: View
Règles du jeu / décisions : Controller
Données (Player, Card…) : Model
 */