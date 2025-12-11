package cards; //Fait parti du package cards

import game.CoreGame;
import game.Player;
import java.util.ArrayList;
import java.util.List;

public class Roi extends Card{
    @Override
    public void appliquerEffet(Player joueurActif) {
        // L'effet du Roi est d'échanger sa main avec un autre joueur.
        System.out.println("Le Roi a été joué. Échange de deux mains.");

        Player choix = CoreGame.demanderCible(joueurActif);
        List<Card> copie = new ArrayList<>(joueurActif.hand);
        joueurActif.hand = choix.hand;
        choix.hand = copie;
        //inversion des deux listes de mains
    }

    public Roi() {
        super("Roi", 7);
    }
}
