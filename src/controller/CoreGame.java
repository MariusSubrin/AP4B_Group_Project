package controller;

import java.util.*;

import model.cards.*;
import model.game.*;
import view.LoveLetterView;

public class CoreGame {

    public static List<Card> pioche = new ArrayList<>(); // Pioche principale
    public static List<Card> carteDefausse = new ArrayList<>(); // Liste des cartes défaussées (Cartes visibles pour tous les joueurs)
    public static List<Player> joueurs = new ArrayList<Player>();
    public static Card carteCachee; // Carte cachée
    public static LoveLetterView view; // Vue GUI
    // public static int faveurs = 13; // Nombre total de faveurs disponibles dans le jeu, utile ?

    public static void afficherPioche(){
        for (Card c : pioche){
            view.afficherMessage(c.toString());
        }
    }

    public static Player demanderCible(Player joueurActif, Card carteActive) {

        view.afficherMessage("Joueurs disponibles :");

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
            view.afficherMessage("Aucune cible disponible (tous les joueurs sont protégés ou éliminés).");
            return null;
        }

        // Afficher les joueurs disponibles
        for (Player p : joueurs) {
            if (!p.isElimine()) {
                view.afficherMessage(p.getId() + " - " + p.getNom() +
                        (p.hasProtection() ? " (protégé)" : "") +
                        (p == joueurActif && carteActive.getNameCard().equals("Prince") ? " (vous-même - autorisé pour le Prince)" : ""));
            }
        }

        while (true) {
            String input = view.lireInput(joueurActif.getNom() + ", qui vises-tu ? (donner l'id) :");

            if (input == null) {
                view.afficherMessage("Entrée invalide.");
                continue;
            }

            int choix;
            try {
                choix = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                view.afficherMessage("Veuillez entrer un nombre valide.");
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
                view.afficherMessage("Aucun joueur ne correspond à cet id.");
                continue;
            }

            // Auto-ciblage interdit (sauf Prince)
            if (cible == joueurActif &&
                    !carteActive.getNameCard().equals("Prince")) {
                view.afficherMessage("Vous ne pouvez pas vous viser vous-même.");
                continue;
            }

            if (cible.isElimine()) {
                view.afficherMessage("Ce joueur est éliminé.");
                continue;
            }

            if (cible.hasProtection()) {
                view.afficherMessage("Ce joueur est protégé.");
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

    public static void resetPioche(){
        pioche.clear();
        initPioche();
        randomPioche();
    }

    public static void resetHands(){
        for (Player p : joueurs) {
            while (!p.hand.isEmpty()) 
            {
                p.hand.remove(p.hand.get(0));
            }
        }
    }

    private static void randomPioche(){
        Collections.shuffle(pioche);
    }

    //Lancement du jeu global, à la fin c'est la fin du jeu et il y a un grand gagnant
    public static void lancerPartie(){
        // Logique pour lancer la partie
        view.afficherMessage("Début de la partie !");

        int nbjoueurs = 0;
        do {
            String inputNb = view.lireInput("Veuillez choisir le nombre de joueurs (2-6) : ");
            if (inputNb == null) {
                view.afficherMessage("Entrée invalide.");
                continue;
            }
            try {
                nbjoueurs = Integer.parseInt(inputNb.trim());
            } catch (NumberFormatException e) {
                view.afficherMessage("Veuillez entrer un nombre valide.");
                continue;
            }
        }while(nbjoueurs < 2 || nbjoueurs > 6);

        for (int i = 1; i <= nbjoueurs; i++)
        {
            String nomJoueur = view.lireInput("Entrez le nom du joueur " + i + " : ");
            if (nomJoueur == null) {
                view.afficherMessage("Entrée invalide.");
                i--; // retry
                continue;
            }
            joueurs.add(new Player(nomJoueur.trim()));
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
            view.afficherMessage("Début de la manche " + i);
            lancerManche();
            i ++;
        }

        view.afficherMessage("La partie est terminée !");
        view.afficherMessage("Le gagnant de la partie est " + joueurMaxFaveurs().getNom() + " !");
        //Fermeture du scanner global automatiquement

    }

    //Lancement d'une manche, à la fin il y a un gagnant qui gagne une ou deux faveurs
    public static void lancerManche()
    {
        // Afficher les faveurs actuelles au début de la manche
        view.afficherMessage("\n═══════════════════════════════════════");
        view.afficherMessage("     DÉBUT D'UNE NOUVELLE MANCHE");
        view.afficherMessage("═══════════════════════════════════════");
        view.afficherMessage("Faveurs actuelles :");
        for (Player joueur : joueurs) {
            view.afficherMessage("  " + joueur.getNom() + " : " + joueur.getNombreFaveur() + " faveur(s)");
        }
        view.afficherMessage("═══════════════════════════════════════\n");

        deplacerGagnantEnPremier();

        //Random la pioche
        resetPioche();
        try
        {
            resetHands();
        }
        catch (Exception e)
        {
            view.afficherMessage("Erreur lors de la réinitialisation des mains des joueurs : " + e.getMessage());
        }
        //Initialiser la carte cachée
        if (pioche.isEmpty()) {
            throw new IllegalStateException("Pioche vide au début de la manche.");
        }
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
        while (!(pioche.isEmpty()) && (howManyAlive() > 1)){
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
        view.afficherMessage("La manche est terminée. Détermination du gagnant...");
        List<Player> winners = new ArrayList<>();

        if (howManyAlive() == 1) 
        {
            view.afficherMessage("Un seul joueur reste en lice.");
            for (Player p : joueurs) {
                if (!(p.isElimine())) {
                    winners.add(p);
                }
            }
            attributionPoints(winners);
        }

        if (pioche.isEmpty()) 
        {
            view.afficherMessage("La pioche est vide. Comparaison des cartes restantes...");
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
            for (Player p : joueurs) 
            {
                view.afficherMessage("On verifie l'égalité pour ");
                if (!p.isElimine() && !p.hand.isEmpty() && p.hand.get(0).getValueCard() == highestValue) {
                    winners.add(p);
                }
            }

            attributionPoints(winners);

            if (winners.isEmpty()) {
                view.afficherMessage("Aucun gagnant n'a été déterminé.");
                throw new IllegalStateException("Aucun gagnant trouvé.");
            } else if (winners.size() == 1) 
            {
                view.afficherMessage("Le gagnant de la manche est " + winners.get(0).getNom() + " !");
            } else {
                // En cas d'égalité, tout les joueurs gagnants ont un point
                view.afficherMessage("Égalité ! Tout les joueurs à égalité gagnent, le premier d'entre eux commencera la prochaine manche");
            }
        }
        return winners.get(0);
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
        if (joueurs.isEmpty()) {
            throw new IllegalStateException("Aucun joueur dans la liste.");
        }
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