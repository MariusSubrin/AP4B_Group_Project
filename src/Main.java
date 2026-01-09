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