package game;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import cards.Card;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CoreGame {

    public static List<Card> pioche = new ArrayList<>(); // Pioche principale
    public static List<Card> carteDefausse = new ArrayList<>(); // Liste des cartes défaussées (Cartes visibles pour tous les joueurs)
    public static List<Player> joueurs = new ArrayList<Player>();
    public static Card carteCachee; // Carte cachée
    public static Player gagnant; // Joueur gagnant de la dernière manche
    public static int faveurs = 13; // Nombre total de faveurs disponibles dans le jeu

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

    public void lancerPartie() 
    {
        faveurs = 13; //Réinitialiser le nombre de faveurs
        // Logique pour lancer la partie
        System.out.println("Début de la partie !");
        System.out.println("Veuillez choisir le nombre de joueurs (2-4) :");
        int nbjoueurs = Integer.parseInt(CoreGame.sc.nextLine().trim()); //A voir si ca marche
        for (int i = 1; i <= nbjoueurs; i++) 
        {
            System.out.println("Entrez le nom du joueur " + i + " :");
            String nomJoueur = CoreGame.sc.nextLine().trim();
            joueurs.add(new Player(nomJoueur));
        }
        //Initialiser la pioche écrire chaque cartes une à une ?
        //Initialiser la carte cachée
        carteCachee = pioche.get(pioche.size() - 1); //Faire une méthode pour piocher une carte?
        pioche.remove(pioche.size() - 1);
        //Distribuer les cartes aux joueurs
        for (Player joueur : joueurs) 
        {
            joueur.hand.add(pioche.get(pioche.size() - 1));
            pioche.remove(pioche.size() - 1);
        }
        gagnant = joueurs.get(0); //A voir comment on détermine le gagnant de la première manche
        while(faveurs > 0){
            lancerManche();
        }
        System.out.println("La partie est terminée !");
        System.out.println("Le gagnant de la partie est " + obtenirJoueurMaxFaveurs().getNom() + " !");
    }

    public void lancerManche() 
    {
        deplacerGagnantEnPremier();
        // Logique pour lancer la manche
        for (Player joueur : joueurs) 
        {
            joueur.newRound();
        }
        for(Player joueur : joueurs)
        {
            //Distribuer une carte à chaque joueur
            joueur.hand.add(pioche.get(pioche.size() - 1));
            pioche.remove(pioche.size() - 1);
        }
        for (Player joueurActif : joueurs) 
        {
            lancerTour(joueurActif); //Lancer le tour du joueur actif
        }

        CoreGame.gagnant = null; //Mettre le joueur ayant gagné pour débuter la prochaine manche (surement avec une vérification))
    }

    public void lancerTour(Player joueurActif) 
    {
        // Logique pour lancer le tour d'un joueur
    }

    public static Player obtenirJoueurMaxFaveurs() 
    {
    return joueurs.stream()
                  .max(Comparator.comparingInt(Player::getNombreFaveur))
                  .orElse(null);
    }

    public static void deplacerGagnantEnPremier() 
    {
        if (gagnant != null && joueurs.contains(gagnant)) 
        {
            joueurs.remove(gagnant);  // Supprime le gagnant de sa position actuelle
            joueurs.add(0, gagnant);   // L'ajoute à l'indice 0
        }
    }   
}