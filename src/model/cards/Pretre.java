package model.cards;

import model.game.*;
import controller.CoreGame;

public class Pretre extends Card {
    @Override
    public void appliquerEffet(Player joueurActif) {
        System.out.println("Le Prêtre a été joué.");

        Player cible = CoreGame.demanderCible(joueurActif, this);

        // Gérer le cas où aucune cible n'est disponible
        if (cible == null) {
            System.out.println("Aucune cible disponible pour le Prêtre. L'effet est annulé.");
            return;
        }

        System.out.println(joueurActif.getNom() + " regarde la main de " + cible.getNom() + " : " + cible.hand.get(0).getNameCard());
    }

    public Pretre() {
        super("Pretre", 2);
    }
}