package model.cards;

import model.game.*;
import controller.CoreGame;
//Il semble qu'on puisse s'autoappeler pour comparer mais jsp pourquoi #TODO
public class Baron extends Card{
    @Override
    public void appliquerEffet(Player joueurActif) {
        //Le baron compare les cartes des deux joueurs et la plus faible quitte la manche
        System.out.println("Le Baron a été joué. Il compare les cartes de deux joueurs, le plus faible quitte la manche");
        Player choix = CoreGame.demanderCible(joueurActif, this);
        if(choix.hand.get(0).getValueCard() < joueurActif.hand.get(0).getValueCard()){
            choix.elimination();
            System.out.println(choix.getNom() + " est éliminé, sa carte était : " + choix.hand.getFirst());
        }
        else if(choix.hand.get(0).getValueCard() == joueurActif.hand.get(0).getValueCard()){
            System.out.println("Egalité aucun des joueurs ne quitte la partie");
        }
        else{
            joueurActif.elimination();
            System.out.println(joueurActif.getNom() + " est éliminé, sa carte était : " + joueurActif.hand.getFirst());
        }
    }

    public Baron() {
        super("Baron", 3);
    }
}
