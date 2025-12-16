package game;

import java.util.Collections;
import java.util.List;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import cards.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CoreGame {

    public static List<Card> pioche = new ArrayList<>(); // Pioche principale
    public static List<Card> carteDefausse = new ArrayList<>(); // Liste des cartes défaussées (Cartes visibles pour tous les joueurs)
    public static List<Player> joueurs = new ArrayList<Player>();
    public static Card carteCachee; // Carte cachée
    public static Player gagnant; // Joueur gagnant de la dernière manche
    // public static int faveurs = 13; // Nombre total de faveurs disponibles dans le jeu, utile ?

    public static void afficherPioche(){
        System.out.println("Test");
        for (Card c : pioche){
            System.out.println(c.toString());
        }
    }

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

    public static void initPioche(){
        //Initialisation de la pioche
        new Princesse();
        new Comtesse();
        new Roi();
        new Chancelier();
        new Chancelier();
        new Prince();
        new Prince();
        new Servante();
        new Servante();
        new Baron();
        new Baron();
        new Pretre();
        new Pretre();
        new Garde();
        new Garde();
        new Garde();
        new Garde();
        new Garde();
        new Garde();
        new Espionne();
        new Espionne();
    }

    private static void randomPioche(){
        Collections.shuffle(pioche);
    }

    /*public void lancerPartie()
    {
        faveurs = 13; //Réinitialiser le nombre de faveurs
        // Logique pour lancer la partie
        System.out.println("Début de la partie !");
        System.out.println("Veuillez choisir le nombre de joueurs (2-4) : ");
        int nbjoueurs = Integer.parseInt(CoreGame.sc.nextLine().trim()); //A voir si ca marche
        for (int i = 1; i <= nbjoueurs; i++) 
        {
            System.out.println("Entrez le nom du joueur " + i + " : ");
            String nomJoueur = CoreGame.sc.nextLine().trim();
            joueurs.add(new Player(nomJoueur));
        }
        //Initialiser la pioche écrire chaques cartes une à une ?
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
    */ //version 13jetons

    //Lancement du jeu global, à la fin c'est la fin du jeu et il y a un grand gagnant
    public static void lancerPartie(){
        // Logique pour lancer la partie
        System.out.println("Début de la partie !");

        int nbjoueurs;
        do {
            System.out.println("Veuillez choisir le nombre de joueurs (2-6) : ");
            nbjoueurs = Integer.parseInt(CoreGame.sc.nextLine().trim()); //A voir si ca marche
        }while(nbjoueurs < 2 || nbjoueurs > 6);

        for (int i = 1; i <= nbjoueurs; i++)
        {
            System.out.println("Entrez le nom du joueur " + i + " : ");
            String nomJoueur = CoreGame.sc.nextLine().trim();
            joueurs.add(new Player(nomJoueur));
        }

        int winFaveurs = 0;
        if(nbjoueurs == 2){ winFaveurs = 6; }
        if(nbjoueurs == 3){ winFaveurs = 5; }
        if(nbjoueurs == 4){ winFaveurs = 4; }
        if(nbjoueurs == 5){ winFaveurs = 3; }
        if(nbjoueurs == 6){ winFaveurs = 3; }

        //Initialisation de la pioche
        initPioche();

        while(joueurMaxFaveurs().getNombreFaveur() < winFaveurs){
            lancerManche();
        }

        System.out.println("La partie est terminée !");
        System.out.println("Le gagnant de la partie est " + joueurMaxFaveurs().getNom() + " !");
    }

    //Lancement d'une manche, à la fin il y a un gagnant que gagne une ou deux faveurs
    public static void lancerManche()
    {
        deplacerGagnantEnPremier();

        //

        //Random la pioche TODO
        randomPioche();

        //Initialiser la carte cachée
        carteCachee = pioche.get(pioche.size() - 1);
        pioche.remove(pioche.size() - 1);

        // Logique pour lancer la manche
        for (Player joueur : joueurs) 
        {
            joueur.newRound(); //On reset tout les attributs
        }
        for(Player joueur : joueurs)
        {
            //Distribuer une carte à chaque joueur
            joueur.piocher();
        }

        int i = 0;
        while (!pioche.isEmpty() || joueurs.size() > 1){ //Revoir condition TODO
            if(!joueurs.get(i).isElimine()){
                lancerTour(joueurs.get(i));
                i ++;
            }else{ i ++; }
        }

        /*for (Player joueurActif : joueurs)
        {
            lancerTour(joueurActif); //Lancer le tour du joueur actif
        }*/

        CoreGame.gagnant = null; //Mettre le joueur ayant gagné pour débuter la prochaine manche (surement avec une vérification))
    }

    //Lancement d'un tour, à la fin c'est à un autre joueur de jouer
    public static void lancerTour(Player joueurActif)
    {
        // Logique pour lancer le tour d'un joueur
        joueurActif.piocher();
        joueurActif.choixCarte();
    }

    /*public static Player joueurMaxFaveurs()
    {
    return joueurs.stream()
                  .max(Comparator.comparingInt(Player::getNombreFaveur))
                  .orElse(null);
    }
    */ //Version de fou incompréhensible mdr

    public static Player joueurMaxFaveurs(){
        Player max = joueurs.get(0);

        for (Player p : joueurs) {
            if (p.getNombreFaveur() > max.getNombreFaveur()) {
                max = p;
            }
        }

        return max;
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