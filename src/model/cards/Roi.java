package model.cards;

import model.game.*;
import controller.CoreGame;

import java.util.ArrayList;
import java.util.List;

public class Roi extends Card {
    @Override
    public void appliquerEffet(Player joueurActif) {
        System.out.println("Le Roi a été joué. Échange de deux mains.");

        Player choix = CoreGame.demanderCible(joueurActif, this);

        // Gérer le cas où aucune cible n'est disponible
        if (choix == null) {
            System.out.println("Aucune cible disponible pour le Roi. L'effet est annulé.");
            return;
        }

        List<Card> copie = new ArrayList<>(joueurActif.hand);
        joueurActif.hand = choix.hand;
        choix.hand = copie;
        System.out.println(joueurActif.getNom() + " et " + choix.getNom() + " ont échangé de main");
    }

    public Roi() {
        super("Roi", 7);
    }
}