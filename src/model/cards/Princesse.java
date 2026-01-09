package model.cards;
import model.game.*;
import controller.CoreGame;

public class Princesse extends Card {
    @Override
    public void appliquerEffet(Player joueurActif) {
        // La princesse n'a pas d'effet direct lorsqu'elle est jouée.
        // Son effet est de faire perdre le joueur si elle est défaussée.
        CoreGame.view.afficherMessage(getNameCard() + " a été jouée. Le joueur qui la possède est éliminé.");
        joueurActif.elimination();
    }

    public Princesse() {
        super("Diplôme", 9);
    }
}
