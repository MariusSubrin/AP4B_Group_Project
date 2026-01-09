import controller.CoreGame;
import view.LoveLetterView;

public class Main {
    //Doit appeler les bons fichiers au début

    // A chaque début de manche il faudra utiliser newRound pour chaque joueur

    public static void main(String[] args) {
        CoreGame.view = new LoveLetterView();
        CoreGame.lancerPartie();
    }
}

/*
Soucis lorsque qu'on joue notre princesse ?
 */

/*
MVC :
Afficher / demander à l’utilisateur	: View
Règles du jeu / décisions : Controller
Données (Player, Card…) : Model
 */