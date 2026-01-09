package model.cards;

import model.game.*;
import controller.CoreGame;

import java.util.ArrayList;
import java.util.List;

public class Roi extends Card {
    @Override
    public void appliquerEffet(Player joueurActif) {
        CoreGame.view.afficherMessage(getNameCard() + " a été joué. Échange de deux mains.");

        Player choix = CoreGame.demanderCible(joueurActif, this);

        // Gérer le cas où aucune cible n'est disponible
        if (choix == null) {
            CoreGame.view.afficherMessage("Aucune cible disponible pour" + getNameCard() + ". L'effet est annulé.");
            return;
        }

        List<Card> copie = new ArrayList<>(joueurActif.hand);
        joueurActif.hand = choix.hand;
        choix.hand = copie;
        CoreGame.view.afficherMessage(joueurActif.getNom() + " et " + choix.getNom() + " ont échangé de main");
    }

    public Roi() {
        super("Responsable de filière", 7);
    }
}