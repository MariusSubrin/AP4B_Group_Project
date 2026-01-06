package model.cards;

import model.game.*;
import controller.CoreGame;
import static controller.CoreGame.view;

public class Baron extends Card {
    @Override
    public void appliquerEffet(Player joueurActif) {
        view.afficherMessage("Le Baron a été joué. Il compare les cartes de deux joueurs, le plus faible quitte la manche");

        Player choix = CoreGame.demanderCible(joueurActif, this);

        // Gérer le cas où aucune cible n'est disponible
        if (choix == null) {
            view.afficherMessage("Aucune cible disponible pour le Baron. L'effet est annulé.");
            return;
        }

        if(choix.hand.get(0).getValueCard() < joueurActif.hand.get(0).getValueCard()){
            choix.elimination();
            view.afficherMessage(choix.getNom() + " est éliminé, sa carte était : " + choix.hand.getFirst());
        }
        else if(choix.hand.get(0).getValueCard() == joueurActif.hand.get(0).getValueCard()){
            view.afficherMessage("Égalité, aucun des joueurs ne quitte la partie");
        }
        else{
            joueurActif.elimination();
            view.afficherMessage(joueurActif.getNom() + " est éliminé, sa carte était : " + joueurActif.hand.getFirst());
        }
    }

    public Baron() {
        super("Baron", 3);
    }
}