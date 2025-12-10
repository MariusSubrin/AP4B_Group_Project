package cards;

import game.Player;

public class Prince extends Card {

    @Override
    public void appliquerEffet() {
        // L'effet du Prince est de faire défausser une carte à un joueur ciblé.
        System.out.println("Le Prince a été joué. Un joueur ciblé défausse sa main.");

        Player cible = this.demanderCible();
        if (!cible.hand.isEmpty()) {
            cible.hand.get(0).defausser();
            cible.hand.remove(0);
        }
    }

    public Prince() {
        super("Prince", 5);
    }

}
