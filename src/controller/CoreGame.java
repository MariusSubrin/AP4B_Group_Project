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
            auMoinsUneCibleValide = true;
        }

        // Si aucune cible valide et que ce n'est pas le Prince
        if (!auMoinsUneCibleValide) {
            view.afficherMessage("⚠ Aucune cible disponible (tous les joueurs sont protégés ou éliminés).");
            return null;
        }

        // Afficher les joueurs disponibles avec leurs IDs
        StringBuilder message = new StringBuilder();
        message.append("ID | Nom | Statut\n");
        message.append("-----------------\n");

        for (Player p : joueurs) {
            if (!p.isElimine()) {
                message.append(p.getId()).append("  | ")
                        .append(p.getNom());

                if (p.hasProtection()) {
                    message.append(" (🛡️ protégé)");
                }

                if (p == joueurActif && carteActive.getNameCard().equals("Prince")) {
                    message.append(" (vous-même - autorisé pour le Prince)");
                }

                message.append("\n");
            }
        }

        view.afficherMessage(message.toString());

        while (true) {
            // Afficher clairement la demande
            String input = view.lireInput(joueurActif.getNom() + ", qui vises-tu ? (entrez l'ID) :");

            if (input == null || input.trim().isEmpty()) {
                view.afficherMessage("❌ Entrée invalide.");
                continue;
            }

            int choix;
            try {
                choix = Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                view.afficherMessage("❌ Veuillez entrer un nombre valide.");
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
                view.afficherMessage("❌ Aucun joueur ne correspond à cet ID.");
                continue;
            }

            // Auto-ciblage interdit (sauf Prince)
            if (cible == joueurActif && !carteActive.getNameCard().equals("Prince")) {
                view.afficherMessage("❌ Vous ne pouvez pas vous viser vous-même.");
                continue;
            }

            if (cible.isElimine()) {
                view.afficherMessage("❌ Ce joueur est éliminé.");
                continue;
            }

            if (cible.hasProtection()) {
                view.afficherMessage("❌ Ce joueur est protégé.");
                continue;
            }

            // Cible valide
            view.afficherMessage("Cible sélectionnée : " + cible.getNom());
            return cible;
        }
    }

    public static void afficherEtAttendre(String message) {
        if (view != null) {
            view.afficherMessage(message);
            // Petite pause pour la lisibilité
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
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

    public static void resetPioche() {
        // 1. Remettre les cartes des mains des joueurs dans la pioche
        for (Player p : joueurs) {
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
        for (Card c : cartesDefaussees) {
            c.mettreDansPioche();
        }
        carteDefausse.clear();

        // 3. Remettre la carte cachée dans la pioche
        carteCachee.mettreDansPioche();
        carteCachee = null;

        System.out.println("Toutes les cartes ont été remises dans la pioche !");
    }

    private static void randomPioche(){
        Collections.shuffle(pioche);
    }

    //Lancement du jeu global, à la fin c'est la fin du jeu et il y a un grand gagnant
    public static void lancerPartie(){
        if (view == null) {
            System.err.println("ERREUR: La vue n'est pas initialisée!");
            return;
        }

        view.afficherMessage("✨ Début de la partie Love Letter ! ✨");
        view.afficherSeparateur();
        // Initialiser l'interface
        initialiserInterface();

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
            if(i > 1){
                resetPioche();
            }
            lancerManche();
            i ++;
        }

        view.afficherMessage("La partie est terminée !");
        view.afficherMessage("Le gagnant de la partie est " + joueurMaxFaveurs().getNom() + " !");
        //Fermeture du scanner global automatiquement

    }

    // Méthode modifiée pour getWinner() pour mieux gérer l'affichage
    public static Player getWinner() {
        view.afficherMessage("La manche est terminée. Détermination du gagnant...");
        List<Player> winners = new ArrayList<>();

        if (howManyAlive() == 1) {
            view.afficherMessage("Un seul joueur reste en lice.");
            for (Player p : joueurs) {
                if (!p.isElimine()) {
                    winners.add(p);
                    break;
                }
            }
        } else if (pioche.isEmpty()) {
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

            // Ajouter tous les joueurs avec la valeur la plus haute
            for (Player p : joueurs) {
                if (!p.isElimine() && !p.hand.isEmpty() && p.hand.get(0).getValueCard() == highestValue) {
                    winners.add(p);
                }
            }
        }

        if (winners.isEmpty()) {
            // Cas spécial : tous les joueurs sont éliminés sauf un
            for (Player p : joueurs) {
                if (!p.isElimine()) {
                    winners.add(p);
                    break;
                }
            }
        }

        if (winners.isEmpty()) {
            view.afficherMessage("Aucun gagnant n'a été déterminé.");
            throw new IllegalStateException("Aucun gagnant trouvé.");
        } else if (winners.size() == 1) {
            view.afficherMessage("Le gagnant de la manche est " + winners.get(0).getNom() + " !");
        } else {
            view.afficherMessage("Égalité ! " + winners.size() + " joueurs à égalité :");
            for (Player p : winners) {
                view.afficherMessage("  - " + p.getNom() + " (carte: " + p.hand.get(0).getNameCard() + ")");
            }
        }

        attributionPoints(winners);
        return winners.get(0); // Retourne le premier gagnant pour la prochaine manche
    }

    public static void attributionPoints (List<Player> winners){
        for (Player p : winners){
            if (p.isEspionneJouee()){
                p.ajouterFaveur(2);
            }else p.ajouterFaveur(1);
        }
    }

    public static void lancerManche() {
        // Afficher les faveurs actuelles au début de la manche
        view.afficherSeparateur();
        view.afficherMessage("✨ DÉBUT D'UNE NOUVELLE MANCHE ✨");
        view.afficherSeparateur();
        view.afficherMessage("Faveurs actuelles :");
        for (Player joueur : joueurs) {
            view.afficherMessage("  " + joueur.getNom() + " : " + joueur.getNombreFaveur() + " faveur(s)");
        }
        view.afficherSeparateur();

        // Déplacer le gagnant de la manche précédente en première position
        deplacerGagnantEnPremier();

        initialiserInterface(); // Mise à jour de l'interface

        // Initialiser la carte cachée
        if (pioche.isEmpty()) {
            throw new IllegalStateException("Pioche vide au début de la manche.");
        }
        carteCachee = pioche.get(pioche.size() - 1);
        pioche.remove(pioche.size() - 1);
        carteCachee.cacher();

        //On montre la carté cachée ?
        view.afficherMessage("Carte cachée : " + carteCachee.getNameCard() + " (valeur: " + carteCachee.getValueCard() + ")");

        // Logique pour lancer la manche
        for (Player joueur : joueurs) {
            joueur.newRound(); // On reset tout les attributs
        }

        view.afficherMessage("Distribution des cartes initiales...");
        for (Player joueur : joueurs) {
            // Distribuer une carte à chaque joueur
            joueur.piocher();
            view.afficherMessage("  " + joueur.getNom() + " a reçu une carte.");
        }

        initialiserInterface(); // Mettre à jour l'affichage après distribution

        // Boucle principale de la manche
        int i = 0;
        while (!pioche.isEmpty() && howManyAlive() > 1) {
            Player joueurActuel = joueurs.get(i % joueurs.size());
            if (!joueurActuel.isElimine()) {
                lancerTour(joueurActuel);
                initialiserInterface(); // Mettre à jour après chaque tour
            }
            i++;

            // Petite pause pour lisibilité
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Déterminer le gagnant de la manche
        Player gagnantManche = getWinner();
        gagnantManche.gagnant = true;

        view.afficherSeparateur();
        view.afficherMessage("🏆 " + gagnantManche.getNom() + " gagne la manche ! 🏆");

        // Ajouter les faveurs (points) au(x) gagnant(s)
        List<Player> winners = new ArrayList<>();
        for (Player p : joueurs) {
            if (!p.isElimine() || p == gagnantManche) {
                winners.add(p);
            }
        }
        attributionPoints(winners);

        // Afficher les faveurs mises à jour
        view.afficherMessage("Mise à jour des faveurs :");
        for (Player p : winners) {
            int points = p.isEspionneJouee() ? 2 : 1;
            view.afficherMessage("  " + p.getNom() + " : +" + points + " faveur(s)");
        }

        initialiserInterface(); // Dernière mise à jour
    }

    //Début du tour d'un joueur
    public static void lancerTour(Player joueurActif) {
        // Désactiver la protection au début du tour
        joueurActif.protectionOff();

        // Mettre à jour l'interface
        initialiserInterface();

        view.afficherSeparateur();
        view.afficherMessage("🎲 TOUR DE " + joueurActif.getNom().toUpperCase() + " 🎲");

        // Vérifier si le joueur a la Comtesse et un Prince/Roi en main
        boolean doitJouerComtesse = false;
        if (joueurActif.hand.size() == 2) {
            Card carte1 = joueurActif.hand.get(0);
            Card carte2 = joueurActif.hand.get(1);

            // Si le joueur a la Comtesse et un Prince (5) ou Roi (7)
            if ((carte1.getNameCard().equals("Comtesse") &&
                    (carte2.getNameCard().equals("Prince") || carte2.getNameCard().equals("Roi"))) ||
                    (carte2.getNameCard().equals("Comtesse") &&
                            (carte1.getNameCard().equals("Prince") || carte1.getNameCard().equals("Roi")))) {

                doitJouerComtesse = true;
                view.afficherMessage("⚠ " + joueurActif.getNom() + " a la Comtesse avec le Prince/Roi. La Comtesse doit être jouée !");
            }
        }

        // Piocher une carte
        if (!pioche.isEmpty()) {
            view.afficherMessage("📚 " + joueurActif.getNom() + " pioche une carte...");
            joueurActif.piocher();

            // Si le joueur doit jouer la Comtesse, la sélectionner automatiquement
            if (doitJouerComtesse) {
                view.afficherMessage("La Comtesse est jouée automatiquement (règle spéciale).");
                for (Card c : joueurActif.hand) {
                    if (c.getNameCard().equals("Comtesse")) {
                        c.jouerCarte(joueurActif);
                        break;
                    }
                }
            } else {
                // Sinon, demander au joueur de choisir une carte
                view.afficherMessage("C'est à vous de choisir une carte à jouer.");
                joueurActif.choixCarte();
            }
        } else {
            view.afficherMessage("La pioche est vide.");
        }

        // Afficher l'état après le tour
        view.afficherMessage("État après le tour :");
        if (!joueurActif.isElimine()) {
            if (!joueurActif.hand.isEmpty()) {
                view.afficherMessage("  Carte restante : " + joueurActif.hand.get(0).getNameCard());
            } else {
                view.afficherMessage("  Main vide");
            }
        } else {
            view.afficherMessage("  " + joueurActif.getNom() + " a été éliminé !");
        }

        // Mettre à jour l'interface
        initialiserInterface();

        // Petite pause pour lisibilité
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
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

    // Dans CoreGame.java, ajoute ces méthodes :

    public static void mettreAJourInterface() {
        if (view == null) return;

        // Mettre à jour les infos de jeu
        view.mettreAJourInfosJeu(
                pioche.size(),
                carteDefausse.size(),
                carteCachee != null ? "Carte cachée (" + carteCachee.getNameCard() + ")" : "Non définie"
        );

        // Mettre à jour les infos des joueurs
        String[] joueursInfo = new String[joueurs.size()];
        for (int i = 0; i < joueurs.size(); i++) {
            Player p = joueurs.get(i);
            StringBuilder info = new StringBuilder();
            info.append(p.getNom()).append(" | ");
            info.append(p.getNombreFaveur()).append(" faveurs | ");
            info.append(p.isElimine() ? "Éliminé" : "En jeu").append(" | ");
            info.append(p.hasProtection() ? "🛡️ Protégé" : "");

            // Ajouter un indicateur si c'est le gagnant de la manche précédente
            if (p.gagnant) {
                info.append(" 👑");
            }

            joueursInfo[i] = info.toString();
        }

        view.mettreAJourJoueurs(joueursInfo);
    }

    public static void afficherMessageAvecStyle(String message) {
        if (view != null) {
            view.afficherMessage(message);
        }
    }

    public static void afficherSeparateur() {
        if (view != null) {
            view.afficherSeparateur();
        }
    }

    // Méthode pour initialiser et mettre à jour l'interface
    public static void initialiserInterface() {
        if (view == null) return;

        // Mettre à jour les infos de jeu
        view.mettreAJourInfosJeu(
                pioche.size(),
                carteDefausse.size(),
                carteCachee != null ? carteCachee.getNameCard() + " (valeur: " + carteCachee.getValueCard() + ")" : "Non définie"
        );

        // Mettre à jour les infos des joueurs
        String[] joueursInfo = new String[joueurs.size()];
        for (int i = 0; i < joueurs.size(); i++) {
            Player p = joueurs.get(i);
            StringBuilder info = new StringBuilder();
            info.append(p.getNom()).append(" | ");
            info.append(p.getNombreFaveur()).append(" faveurs | ");
            info.append(p.isElimine() ? "Éliminé" : "En jeu").append(" | ");
            if (p.hasProtection()) {
                info.append("🛡️ Protégé");
            }
            if (p.gagnant) {
                info.append(" 👑");
            }

            // Ajouter la carte en main si elle est visible
            if (!p.isElimine() && !p.hand.isEmpty() && p == joueurs.get(0)) {
                // Pour le joueur actif, on peut montrer sa carte
                info.append(" | Main: ").append(p.hand.get(0).getNameCard());
            }

            joueursInfo[i] = info.toString();
        }

        view.mettreAJourJoueurs(joueursInfo);
    }

    //Exemple appel view
    /*view.afficherMessage("Qui veux-tu viser ?");
    String cible = view.lireSaisie();*/
}