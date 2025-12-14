package cards;

import game.CoreGame;
import game.Player;

public class Pretre extends Card{
    @Override
    public void appliquerEffet(Player joueurActif) {
        // L'effet du Prêtre est de regarder la main d'un autre joueur.
        System.out.println("Le Prêtre a été joué.");

        Player cible = CoreGame.demanderCible(joueurActif);
        System.out.println(joueurActif.getNom() + " regarde la main de " + cible.getNom() + " : " + cible.hand.get(0).getNameCard());
    }

    public Pretre() {
        super("Pretre", 2);
    }

}
