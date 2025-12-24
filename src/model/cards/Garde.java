package model.cards;

import model.game.*;
import controller.CoreGame;

public class Garde extends Card{
    @Override
    public void appliquerEffet(Player joueurActif) {
        // L'effet de la Garde est de tenter de deviner la carte d'un autre joueur.
        System.out.println("La Garde a été jouée. Vous allez tenter de deviner la carte d'un autre joueur afin de l'éliminer");

        Player cible = CoreGame.demanderCible(joueurActif, this);
        System.out.println(joueurActif.getNom() + ", quelle carte pensez-vous que " + cible.getNom() + " possède ?");
        String test = CoreGame.sc.nextLine().trim();
        //Faut vérifier que ce type de carte existe, sinon bug #TODO

        if (cible.hand.getFirst().getNameCard().equalsIgnoreCase(test)) {
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
