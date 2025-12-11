package cards;
import game.CoreGame;
import game.Player;

import game.Player;

public class Comtesse extends Card {
    @Override
    public void appliquerEffet(Player joueurActif) {
        // La Comtesse n'a pas d'effet direct lorsqu'elle est jouée.
        // Son effet est de forcer le joueur à la défausser s'il a le Roi ou le Prince en main.
        System.out.println("La Comtesse a été jouée.");
    }

    public Comtesse() {
        super("Comtesse", 8);
    }

}
