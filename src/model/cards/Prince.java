package model.cards;

import model.game.*;
import controller.CoreGame;

public class Prince extends Card {

    @Override
    public void appliquerEffet(Player joueurActif) {
        CoreGame.view.afficherMessage("Le Prince a été joué. Un joueur ciblé défausse sa main.");

        Player choix = CoreGame.demanderCible(joueurActif, this);

        // Gérer le cas où aucune cible n'est disponible
        if (choix == null) {
            CoreGame.view.afficherMessage("Aucune cible disponible pour le Prince. L'effet est annulé.");
            return;
        }

        CoreGame.view.afficherMessage(choix.getNom() + " a défaussé : " + choix.hand.getFirst().toString());
        choix.hand.getFirst().defausser(choix); // Le joueur ciblé défausse sa carte
        if(!CoreGame.pioche.isEmpty()) {
            choix.hand.add(CoreGame.pioche.getLast());
            CoreGame.pioche.removeLast();
        } else {
            CoreGame.view.afficherMessage("La pioche est vide.");
        }
    }

    public Prince() {
        super("Prince", 5);
    }
}