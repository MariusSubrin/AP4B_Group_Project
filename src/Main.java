import controller.CoreGame;

public class Main {
    //Doit appeler les bons fichiers au début

    // A chaque début de manche il faudra utiliser newRound pour chaque joueur

    public static void main(String[] args) {
        CoreGame.lancerPartie();
        //new LoveLetterView();
    }
}

/*
Listes de tests à faire (non compléte) :
- Utilisation de toutes les cartes en lançant le jeu
    - soucis avec prêtre
    - soucis avec le chancelier

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