package model.cards;

import model.game.*;
import controller.CoreGame;

public class Comtesse extends Card {
    @Override
    public void appliquerEffet(Player joueurActif) {
        // La Comtesse n'a pas d'effet direct lorsqu'elle est jouée.
        // Son effet est de forcer le joueur à la défausser s'il a le Roi ou le Prince en main.
        CoreGame.view.afficherMessage(getNameCard() + " a été jouée.");
    }

    public Comtesse() {
        super("c", 8);
    }

}
