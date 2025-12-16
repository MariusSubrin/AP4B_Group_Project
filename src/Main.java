import cards.Princesse;
import game.CoreGame;
import cards.Card;

import static game.CoreGame.afficherPioche;
import static game.CoreGame.initPioche;

public class Main {
    //Doit appeler les bons fichiers au début

    // A chaque début de manche il faudra utiliser newRound pour chaque joueur

    public static void main(String[] args) {
        // TODO: Initialize the game
        System.out.println("Game starting...");
        CoreGame.lancerPartie();
    }
}

/*
Listes de tests à faire (non compléte) :
- Utilisation de toutes les cartes en lançant le jeu
    - soucis avec prêtre
    - soucis avec le chancelier

Revoir les consignes pour savoir si tout est bien fait
 */