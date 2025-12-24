package model.cards;

import model.game.*;
import controller.CoreGame;

public class Prince extends Card {

    @Override
    public void appliquerEffet(Player joueurActif) 
    {
        // L'effet du Prince est de faire défausser une carte à un joueur ciblé.
        System.out.println("Le Prince a été joué. Un joueur ciblé défausse sa main.");

        Player choix = CoreGame.demanderCible(joueurActif, this);
        choix.hand.getFirst().defausser(joueurActif); //Car le joueur n'a qu'une seule carte de toute façon
        if(!CoreGame.pioche.isEmpty())
        {
            choix.hand.add(CoreGame.pioche.getLast());
            CoreGame.pioche.removeLast();
        }
        else
        {
            System.out.println("La pioche est vide.");
        }
    }

    public Prince() 
    {
        super("Prince", 5);
    }

}
