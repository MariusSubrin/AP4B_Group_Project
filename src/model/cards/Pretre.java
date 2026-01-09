package model.cards;

import model.game.*;
import controller.CoreGame;

public class Pretre extends Card {
    @Override
    public void appliquerEffet(Player joueurActif) {
        CoreGame.view.afficherMessage("Le Prêtre a été joué.");

        Player cible = CoreGame.demanderCible(joueurActif, this);

        // Gérer le cas où aucune cible n'est disponible
        if (cible == null) {
            CoreGame.view.afficherMessage("Aucune cible disponible pour le Prêtre. L'effet est annulé.");
            return;
        }

        CoreGame.view.afficherMessage(joueurActif.getNom() + " regarde la main de " + cible.getNom() + " : " + cible.hand.get(0).getNameCard());
    }

    public Pretre() {
        super("Pretre", 2);
    }
}