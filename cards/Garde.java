package cards;

import game.CoreGame;
import game.Player;

public class Garde extends Card{
    @Override
    public void appliquerEffet(Player joueurActif) {
        // L'effet de la Garde est de tenter de deviner la carte d'un autre joueur.
        System.out.println("La Garde a été jouée.");

        Player cible = CoreGame.demanderCible(joueurActif);
        System.out.println(joueurActif.getNom() + ", quelle carte pensez-vous que " + cible.getNom() + " possède ?");
        String test = CoreGame.sc.nextLine().trim();

        if (cible.hand.get(0).getNameCard().equalsIgnoreCase(test)) {
            System.out.println("Vous avez deviné correctement ! " + cible.getNom() + " est éliminé.");
            cible.elimination();
        } else {
            System.out.println("Vous n'avez pas deviné correctement.");
        }
    }

    public Garde() {
        super("Garde", 1);
    }

}
