package model.cards;

import model.game.*;
import controller.CoreGame;

public class Prince extends Card {

    @Override
    public void appliquerEffet(Player joueurActif) {
        CoreGame.view.afficherMessage(getNameCard() + " a été joué. Un joueur ciblé défausse sa main.");

        Player choix = CoreGame.demanderCible(joueurActif, this);

        // Gérer le cas où aucune cible n'est disponible
        if (choix == null) {
            CoreGame.view.afficherMessage("Aucune cible disponible pour" + getNameCard() + ". L'effet est annulé.");
            return;
        }

        CoreGame.view.afficherMessage(choix.getNom() + " a défaussé : " + choix.hand.get(0).toString());
        choix.hand.get(0).defausser(choix); // Le joueur ciblé défausse sa carte
        if(!CoreGame.pioche.isEmpty()) {
            choix.hand.add(CoreGame.pioche.get(CoreGame.pioche.size() - 1));
            CoreGame.pioche.remove(CoreGame.pioche.size() - 1);
        } else {
            CoreGame.view.afficherMessage("La pioche est vide.");
        }
    }

    public Prince() {
        super("Responsable de module", 5);
    }
}