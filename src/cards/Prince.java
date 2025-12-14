package cards;
import game.CoreGame;
import game.Player;

public class Prince extends Card {

    @Override
    public void appliquerEffet(Player joueurActif) 
    {
        // L'effet du Prince est de faire défausser une carte à un joueur ciblé.
        System.out.println("Le Prince a été joué. Un joueur ciblé défausse sa main.");

        Player choix = CoreGame.demanderCible(joueurActif);
        choix.hand.get(0).defausser(joueurActif);
        if(CoreGame.pioche.size() > 0)
        {
            choix.hand.add(CoreGame.pioche.get(CoreGame.pioche.size() - 1));
            CoreGame.pioche.remove(CoreGame.pioche.size() - 1);
            return;
        }
        else
        {
            System.out.println("La pioche est vide.");
            choix.hand.add(CoreGame.carteCachee);
            CoreGame.carteCachee = null;
            return;
        }
    }

    public Prince() 
    {
        super("Prince", 5);
    }

}
