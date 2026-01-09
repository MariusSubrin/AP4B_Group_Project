package model.cards;

import model.game.*;
import controller.CoreGame;

public class Baron extends Card {
    @Override
    public void appliquerEffet(Player joueurActif) {
        CoreGame.view.afficherMessage(getNameCard() + " a été joué. Il compare les cartes de deux joueurs, le plus faible quitte la manche");

        Player choix = CoreGame.demanderCible(joueurActif, this);

        // Gérer le cas où aucune cible n'est disponible
        if (choix == null) {
            CoreGame.view.afficherMessage("Aucune cible disponible pour " + getNameCard() + ". L'effet est annulé.");
            return;
        }

        if(choix.hand.get(0).getValueCard() < joueurActif.hand.get(0).getValueCard()){
            choix.elimination();
            if(choix.hand.size() > 0)
            {
            CoreGame.view.afficherMessage(choix.getNom() + " est éliminé, sa carte était : " + choix.hand.get(0));
            }
            else
            {
                CoreGame.view.afficherMessage(choix.getNom() + " est éliminé, sa main était vide.");
            }
        }
        else if(choix.hand.get(0).getValueCard() == joueurActif.hand.get(0).getValueCard()){
            CoreGame.view.afficherMessage("Égalité, aucun des joueurs ne quitte la partie");
        }
        else{
            joueurActif.elimination();
            CoreGame.view.afficherMessage(joueurActif.getNom() + " est éliminé, sa carte était : " + joueurActif.hand.get(0));
        }
    }

    public Baron() {
        super("Étudiant surveillant", 3);
    }
}