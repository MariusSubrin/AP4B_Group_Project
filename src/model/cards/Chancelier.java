package model.cards;

import model.game.*;
import controller.CoreGame;

public class Chancelier extends Card {
    @Override
    public void appliquerEffet(Player joueurActif) {
        // L'effet du Chancelier est de piocher 2 cartes et d'en remettre 2 sous la pioche.
        CoreGame.view.afficherMessage(getNameCard() + " a été joué.");
        if(CoreGame.pioche.isEmpty())
        {
            CoreGame.view.afficherMessage("La pioche est vide l'effet est annulé.");
            return;
        }
        if(CoreGame.pioche.size() == 1)
        {
            CoreGame.view.afficherMessage("Il n'y a qu'une seule carte dans la pioche, vous ne pouvez en piocher qu'une seule.");
            Card carte1 = CoreGame.pioche.get(CoreGame.pioche.size() - 1); //On récupére la carte
            carte1.mettreDansMain(joueurActif); //On modifie son statut et on la met dans la main du joueur
            CoreGame.pioche.remove(CoreGame.pioche.size() - 1); //On n'oublie pas de supprimer celle de la pioche pour éviter les doublons
        }
        else
        {
            CoreGame.view.afficherMessage(joueurActif.getNom() + " pioche 2 cartes et en remet 2 sous la pioche.");
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
        super("Administration universitaire", 6);} // Le Chancelier a une valeur de 6

}
