package model.cards;

import model.game.*;
import controller.CoreGame;

public class Espionne extends Card{
    @Override
    public void appliquerEffet(Player joueurActif) {
        // L'effet est de faire gagner un point de faveur en + au joueur si personne d'autre n'a joué d'Espionne.
        //Faut que le joueur soit toujours en lice à la fin.
        //Il ne peux gagner que 2 points max, même s'il a joué les 2 espionnes et qu'il gagne.
        CoreGame.view.afficherMessage("L'Espionne a été jouée.");

        joueurActif.espionneJouee();
    }

    public Espionne() {
        super("Espionne", 0);
    }
}
