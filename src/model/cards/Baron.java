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

        if(choix.hand.getFirst().getValueCard() < joueurActif.hand.getFirst().getValueCard()){
            choix.elimination();
            if(choix.hand.size() > 0)
            {
            CoreGame.view.afficherMessage(choix.getNom() + " est éliminé, sa carte était : " + choix.hand.getFirst());
            }
            else
            {
                CoreGame.view.afficherMessage(choix.getNom() + " est éliminé, sa main était vide.");
            }
        }
        else if(choix.hand.getFirst().getValueCard() == joueurActif.hand.getFirst().getValueCard()){
            CoreGame.view.afficherMessage("Égalité, aucun des joueurs ne quitte la partie");
        }
        else{
            joueurActif.elimination();
            CoreGame.view.afficherMessage(joueurActif.getNom() + " est éliminé, sa carte était : Étudiant");
        }
    }

    public Baron() {
        super("Étudiant", 3);
    }
}