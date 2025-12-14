package cards;

import game.Player;

public class Servante extends Card{
    @Override
    public void appliquerEffet(Player joueurActif) {
        // L'effet de la Servante est de protéger le joueur pendant un tour.
        System.out.println("La Servante a été jouée. " + joueurActif.getNom() + " est protégé(e) jusqu'à son prochain tour.");
        joueurActif.protectionOn();

        //La protection repasse en Off à chaque début du tour d'un joueur
        //Peut etre gérer le cas particulier de la servante car ceci changerait beaucoup de choses.
    }

    public Servante() {
        super("Servante", 4);
    }
}
