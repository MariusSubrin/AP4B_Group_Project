package cards;

import game.CoreGame;
import game.Player;

public class Chancelier extends Card {
    @Override
    public void appliquerEffet(Player joueurActif) {
        // L'effet du Chancelier est de piocher 3 cartes et d'en remettre 2 sous la pioche.
        System.out.println("Le Chancelier a été joué. Pioche 3 cartes et en remet 2 sous la pioche.");

        Card carte1 = CoreGame.pioche.get(CoreGame.pioche.size() - 1); //On récupére les cartes
        Card carte2 = CoreGame.pioche.get(CoreGame.pioche.size() - 2);
        Card carte3 = CoreGame.pioche.get(CoreGame.pioche.size() - 3);

        carte1.mettreDansMain(joueurActif); //On modifie leur statut et on les met dans la main du joueur
        carte2.mettreDansMain(joueurActif);
        carte3.mettreDansMain(joueurActif);
        
        CoreGame.pioche.remove(CoreGame.pioche.size() - 1); //On oublie par de supprimer celles de la pioche pour éviter les doublons
        CoreGame.pioche.remove(CoreGame.pioche.size() - 1);
        CoreGame.pioche.remove(CoreGame.pioche.size() - 1);

        joueurActif.choixCarte(); 
    }

    public Chancelier() {
        super("Chancelier", 6); // Le Chancelier a une valeur de 9
    }

}
