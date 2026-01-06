package controller;

import java.util.*;

import model.cards.*;
import model.game.*;

public class CoreGame {

    public static List<Card> pioche = new ArrayList<>(); // Pioche principale
    public static List<Card> carteDefausse = new ArrayList<>(); // Liste des cartes défaussées (Cartes visibles pour tous les joueurs)
    public static List<Player> joueurs = new ArrayList<Player>();
    public static Card carteCachee; // Carte cachée
    // public static int faveurs = 13; // Nombre total de faveurs disponibles dans le jeu, utile ?

    public static void afficherPioche(){
        System.out.println("Test");
        for (Card c : pioche){
            System.out.println(c.toString());
        }
    }

    public static final Scanner sc = new Scanner(System.in); //Scanner global, se ferme à la fin du programme

    public static Player demanderCible(Player joueurActif, Card carteActive) {

        System.out.println("Joueurs disponibles :");

        // Vérifier s'il y a au moins une cible valide
        boolean auMoinsUneCibleValide = false;
        for (Player p : joueurs) {
            if (!p.isElimine() && !p.hasProtection() && p != joueurActif) {
                auMoinsUneCibleValide = true;
            }
        }

        // Si c'est le Prince, on peut se cibler soi-même (c'est autorisé)
        if (!auMoinsUneCibleValide && carteActive.getNameCard().equals("Prince")) {
            auMoinsUneCibleValide = true; // On peut toujours se cibler soi-même avec le Prince
        }

        // Si aucune cible valide et que ce n'est pas le Prince
        if (!auMoinsUneCibleValide) {
            System.out.println("Aucune cible disponible (tous les joueurs sont protégés ou éliminés).");
            return null;
        }

        // Afficher les joueurs disponibles
        for (Player p : joueurs) {
            if (!p.isElimine()) {
                System.out.println(p.getId() + " - " + p.getNom() +
                        (p.hasProtection() ? " (protégé)" : "") +
                        (p == joueurActif && carteActive.getNameCard().equals("Prince") ? " (vous-même - autorisé pour le Prince)" : ""));
            }
        }

        while (true) {
            System.out.println("\n" + joueurActif.getNom() + ", qui vises-tu ? (donner l'id) :");
            System.out.print("> ");

            int choix;
            try {
                choix = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
                continue;
            }

            Player cible = null;

            // Recherche du joueur
            for (Player p : joueurs) {
                if (choix == p.getId()) {
                    cible = p;
                    break;
                }
            }

            // Aucun joueur trouvé
            if (cible == null) {
                System.out.println("Aucun joueur ne correspond à cet id.");
                continue;
            }

            // Auto-ciblage interdit (sauf Prince)
            if (cible == joueurActif &&
                    !carteActive.getNameCard().equals("Prince")) {
                System.out.println("Vous ne pouvez pas vous viser vous-même.");
                continue;
            }

            if (cible.isElimine()) {
                System.out.println("Ce joueur est éliminé.");
                continue;
            }

            if (cible.hasProtection()) {
                System.out.println("Ce joueur est protégé.");
                continue;
            }

            // Cible valide
            return cible;
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

        int winFaveurs = switch (nbjoueurs) {
            case 2 -> 6;
            case 3 -> 5;
            case 4 -> 4;
            case 5, 6 -> 3;
            default -> 0; // sécurité
        };

        //Initialisation de la pioche
        initPioche();

        int i = 1;

        while(joueurMaxFaveurs().getNombreFaveur() < winFaveurs){
            System.out.println("Début de la manche " + i);
            if(i > 1){
                remiseDansPioche();
            }
            lancerManche();
            i ++;
        }

        System.out.println("La partie est terminée !");
        System.out.println("Le gagnant de la partie est " + joueurMaxFaveurs().getNom() + " !");
        //Fermeture du scanner global automatiquement

    }

    public static void remiseDansPioche() {
        // 1. Remettre les cartes des mains des joueurs dans la pioche
        for(Player p : joueurs) {
            // Vérifier si le joueur a des cartes en main
            if (!p.hand.isEmpty()) {
                // on utilise une copie de la liste pour éviter ConcurrentModificationException
                List<Card> cartesEnMain = new ArrayList<>(p.hand);
                for (Card c : cartesEnMain) {
                    c.mettreDansPioche();
                }
                p.hand.clear();
            }
        }

        // 2. Remettre les cartes défaussées dans la pioche
        // On utilise une copie pour éviter ConcurrentModificationException
        List<Card> cartesDefaussees = new ArrayList<>(carteDefausse);
        for(Card c : cartesDefaussees) {
            c.mettreDansPioche();
        }
        carteDefausse.clear();

        // 3. Remettre la carte cachée dans la pioche
        carteCachee.mettreDansPioche();
        carteCachee = null;

        System.out.println("Toutes les cartes ont été remises dans la pioche !");
    }

    //Lancement d'une manche, à la fin il y a un gagnant qui gagne une ou deux faveurs
    public static void lancerManche()
    {
        // Afficher les faveurs actuelles au début de la manche
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("     DÉBUT D'UNE NOUVELLE MANCHE");
        System.out.println("═══════════════════════════════════════");
        System.out.println("Faveurs actuelles :");
        for (Player joueur : joueurs) {
            System.out.println("  " + joueur.getNom() + " : " + joueur.getNombreFaveur() + " faveur(s)");
        }
        System.out.println("═══════════════════════════════════════\n");

        deplacerGagnantEnPremier();

        //Random la pioche
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
        while (!pioche.isEmpty() && howManyAlive() > 1){
            Player joueurActuel = joueurs.get(i % joueurs.size());
            if(!joueurActuel.isElimine()){
                lancerTour(joueurActuel);
            }
            i++;
        }

        Player p = getWinner();
        p.gagnant = true;
        //Mettre le joueur ayant gagné pour débuter la prochaine manche (surement avec une vérification))
    }

    public static Player getWinner() {
        //On crée une liste de gagnant
        List<Player> winners = new ArrayList<>();

        if (howManyAlive() == 1) {
            for (Player p : joueurs) {
                if (!p.isElimine()) {
                    winners.add(p);
                }
            }
            attributionPoints(winners);
            return winners.get(0); //Pour ne surtout pas aller plus loin
        }

        if (pioche.isEmpty()) {
            int highestValue = -1;

            // Trouver la valeur la plus haute parmi les joueurs encore en jeu
            for (Player p : joueurs) {
                if (!p.isElimine() && !p.hand.isEmpty()) {
                    int cardValue = p.hand.get(0).getValueCard();
                    if (cardValue > highestValue) {
                        highestValue = cardValue;
                    }
                }
            }

            // Vérifier s'il y a égalité
            for (Player p : joueurs) {
                if (!p.isElimine() && !p.hand.isEmpty() && p.hand.get(0).getValueCard() == highestValue) {
                    winners.add(p);
                }
            }

            attributionPoints(winners);

            if (winners.size() == 1) {
                return winners.get(0);
            } else {
                // En cas d'égalité, tout les joueurs gagnants ont un point
                System.out.println("Égalité ! Tout les joueurs à égalité gagnent, le premier d'entre eux commencera la prochaine manche");
                return winners.get(0);
            }
        }

        throw new IllegalStateException("Aucun gagnant trouvé alors que la manche devrait être terminée.");
    }

    public static void attributionPoints (List<Player> winners){
        for (Player p : winners){
            if (p.isEspionneJouee()){
                p.ajouterFaveur(2);
            }else p.ajouterFaveur(1);
        }
    }

    //Lancement d'un tour, à la fin c'est à un autre joueur de jouer
    public static void lancerTour(Player joueurActif)
    {
        // Désactiver la protection au début du tour
        joueurActif.protectionOff();

        // Logique pour lancer le tour d'un joueur
        joueurActif.piocher();
        joueurActif.choixCarte();
    }

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
        Player p = getPastGagnant(); //référence vers objet dans pas de soucis mémoire
        joueurs.remove(p);  // Supprime le gagnant de sa position actuelle
        joueurs.add(0, p);   // L'ajoute à l'indice 0
    }

    public static Player getPastGagnant(){
        for (Player p : joueurs){
            if (p.gagnant == true){
                return p;
            }
        }
        return joueurs.get(0); //Lors de la première manche aucun joueur n'est gagnant de la précédente
    }

    public static int howManyAlive(){
        int x = 0;
        for (Player p : joueurs){
            if(!p.isElimine()){
                x ++;
            }
        }
        return x;
    }

    //Exemple appel view
    /*view.afficherMessage("Qui veux-tu viser ?");
    String cible = view.lireSaisie();*/
}