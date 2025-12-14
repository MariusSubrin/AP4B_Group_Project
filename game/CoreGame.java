package game;

import cards.Card;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CoreGame {

    public static List<Card> pioche = new ArrayList<>(); // Pioche principale
    public static List<Card> carteDefausse = new ArrayList<>(); // Liste des cartes défaussées (Cartes visibles pour tous les joueurs)
    public static List<Player> joueurs = new ArrayList<Player>();

    public static final Scanner sc = new Scanner(System.in); //Scanner global, il faudra le fermer à la fin du programme TODO

    public static Player demanderCible(Player joueurActif) {

        System.out.println("Joueurs disponibles :");
        for (Player p : joueurs) {
            System.out.println(p.getId() + " - " + p.getNom());
        }//On affiche la liste de joueurs

        while(true)
            {
                System.out.println("\n" + joueurActif.getNom() + ", qui vises tu ? :");
                System.out.print("> ");

                String choix = sc.nextLine().trim();

                // Cherche un joueur avec ce nom
                for (Player p : joueurs)
                {
                    if(!p.getNom().equalsIgnoreCase(choix) || choix.equals(String.valueOf(p.getId()))){
                        continue;
                        //Si ce n'est pas le joueur que l'on cherche on passe directement au suivant
                    }

                    if (p.equals(joueurActif)) {
                        System.out.println("Vous ne pouvez pas vous viser vous-même.");
                        break;
                    }

                    if (p.isElimine()) {
                        System.out.println("Ce joueur est éliminé.");
                        break;
                    }

                    if (p.hasProtection()) {
                        System.out.println("Ce joueur est protégé.");
                        break;
                    }

                    return p; //joueur valide
                }

                System.out.println("Aucun joueur valide ne correspond à ce choix.");
            }
    }

    public void lancerPartie() {
        
    }
}

