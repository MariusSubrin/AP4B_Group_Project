package cards;

import game.*;

public class Baron extends Card{
    @Override
    public void appliquerEffet(Player joueurActif) {
        //Le baron compare les cartes des deux joueurs et la plus faible quitte la manche
        System.out.println("La Baron a été joué");
        Player choix = CoreGame.demanderCible(joueurActif);
        if(choix.hand.get(0).getValueCard() < joueurActif.hand.get(0).getValueCard()){
            choix.elimination();
        }
        else if(choix.hand.get(0).getValueCard() == joueurActif.hand.get(0).getValueCard()){
            System.out.println("Egalité aucun des joueurs ne quitte la partie");
        }
        else{
            joueurActif.elimination();
        }
    }

    public Baron() {
        super("Baron", 3);
    }
}
