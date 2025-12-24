package model.cards;

import model.game.*;
import controller.CoreGame;

public class Chancelier extends Card {
    @Override
    public void appliquerEffet(Player joueurActif) {
        // L'effet du Chancelier est de piocher 2 cartes et d'en remettre 2 sous la pioche.
        System.out.println("Le Chancelier a été joué.");
        if(CoreGame.pioche.isEmpty())
        {
            System.out.println("La pioche est vide l'effet est annulé.");
            return;
        }
        if(CoreGame.pioche.size() < 2)
        {
            System.out.println("Il n'y a qu'une seule carte dans la pioche, vous ne pouvez en piocher qu'une seule.");
            Card carte1 = CoreGame.pioche.getLast(); //On récupére les cartes
            carte1.mettreDansMain(joueurActif); //On modifie leur statut et on les met dans la main du joueur
            CoreGame.pioche.removeLast(); //On oublie pas de supprimer celle de la pioche pour éviter les doublons
        }
        else
        {
            System.out.println("Piochez 2 cartes et en remettez 2 sous la pioche.");
            Card carte1 = CoreGame.pioche.get(CoreGame.pioche.size() - 1); //On récupére les cartes
            Card carte2 = CoreGame.pioche.get(CoreGame.pioche.size() - 2);

            carte1.mettreDansMain(joueurActif); //On modifie leur statut et on les met dans la main du joueur
            carte2.mettreDansMain(joueurActif);
        
            CoreGame.pioche.remove(CoreGame.pioche.size() - 1); //On oublie par de supprimer celles de la pioche pour éviter les doublons
            CoreGame.pioche.remove(CoreGame.pioche.size() - 1);
        }
        joueurActif.choixCarte(); 
    }

    public Chancelier() {
        super("Chancelier", 6);} // Le Chancelier a une valeur de 6

}
