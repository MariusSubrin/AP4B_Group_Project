package model.game;

import java.util.ArrayList;
import java.util.List;

import model.game.Card;
import controller.CoreGame;

public class Player {

    // Compteur global pour attribuer des IDs uniques
    private static int compteurId = 0;

    // Attributs de la classe
    private int id;
    private String nom;
    private int nombre_faveur;
    private boolean elimine;
    private boolean protection;
    private boolean espionne_jouee;
    public List<Card> hand;
    public boolean gagnant; //Si jamais c'est le gagnant de la manche précédente

    public Player(String nom) {
        this.id = compteurId++;   // ID unique auto-incrémenté
        this.nom = nom;
        this.nombre_faveur = 0;
        this.elimine = false;
        this.protection = false;
        this.espionne_jouee = false;
        this.hand = new ArrayList<Card>();
        this.gagnant = false;
    }

    // Getters (accesseurs)
    public int getId() {
        return this.id;
    }

    public String getNom() {
        return this.nom;
    }

    public int getNombreFaveur() {
        return this.nombre_faveur;
    }

    public boolean isElimine() {
        return this.elimine;
    }

    public boolean hasProtection() {
        return this.protection;
    }

    public boolean isEspionneJouee() {
        return this.espionne_jouee;
    }

    // Setters
    // Pas besoin de setNom car initialisé au départ et non changeable par la suite

    public void elimination() {
        this.elimine = true;
        this.hand.getFirst().defausser(this);
    }

    public void resurrection() {
        this.elimine = false;
    }

    public void espionneJouee() {
        this.espionne_jouee = true;
    }

    public void resetEspionneJouee() {
        this.espionne_jouee = false;
    }

    public void ajouterFaveur(int n) {
        this.nombre_faveur += n;
    }

    public void protectionOn() {
        this.protection = true;
    }

    public void protectionOff() {
        this.protection = false;
    }

    public void piocher(){
        CoreGame.view.afficherMessage("Le joueur " + this.getNom() + " pioche une carte.");
        Card p = CoreGame.pioche.getLast();
        p.mettreDansMain(this);
        CoreGame.pioche.remove(CoreGame.pioche.size() - 1);
    }

    public void newRound() {
        this.resurrection();
        this.protectionOff();
        this.resetEspionneJouee();
        this.gagnant = false;
    }

    public void choixCarte() {
        CoreGame.view.afficherMessage("----------------------------------------------------------------------");
        CoreGame.view.afficherMessage("C'est le tour de " + this.getNom() + " !");

        if (this.hand.size() == 2) 
        {
            CoreGame.view.afficherMessage("Voici vos cartes : \n");
            for (Card c : this.hand) 
            {
                CoreGame.view.afficherMessage(c.toString());
            }

            boolean flag = false;
            while (!flag) {
                String input = CoreGame.view.lireInput("Laquelle voulez-vous jouer ? (donnez l'id) : ");
                if (input == null) {
                    CoreGame.view.afficherMessage("Entrée invalide.");
                    continue;
                }
                try 
                {
                    int choix = Integer.parseInt(input.trim());

                    // Recherche de la carte sans modifier la liste
                    Card carteAJouer = null;
                    for (Card c : this.hand) {
                        if (choix == c.getIdCard()) {
                            carteAJouer = c;
                            flag = true;
                            break;
                        }
                    }

                    if (carteAJouer != null) {
                        carteAJouer.jouerCarte(this);
                    } else {
                        CoreGame.view.afficherMessage("L'id donné ne correspond à aucune carte. Réessayez. \n");
                    }
                } catch (NumberFormatException e) {
                    CoreGame.view.afficherMessage("Veuillez entrer un nombre valide. \n");
                }
            }
        }

        if (this.hand.size() == 3) {
            CoreGame.view.afficherMessage("Voici vos cartes : \n");
            for (Card c : this.hand) {
                CoreGame.view.afficherMessage(c.toString());
            }

            // Première carte à remettre
            boolean premiereCarteTrouvee = false;
            while (!premiereCarteTrouvee) {
                String input1 = CoreGame.view.lireInput("Quelle 1ère carte voulez-vous remettre dans la pioche? (donnez l'id) : ");
                if (input1 == null) {
                    CoreGame.view.afficherMessage("Entrée invalide.");
                    continue;
                }
                try {
                    int choix1 = Integer.parseInt(input1.trim());

                    Card premiereCarte = null;
                    for (Card c : this.hand) {
                        if (choix1 == c.getIdCard()) {
                            premiereCarte = c;
                            break;
                        }
                    }

                    if (premiereCarte != null) {
                        premiereCarte.mettreDansPioche();
                        hand.remove(premiereCarte);
                        premiereCarteTrouvee = true;
                    } else {
                        CoreGame.view.afficherMessage("L'id donné ne correspond à aucune carte. Réessayez. \n");
                    }
                } catch (NumberFormatException e) {
                    CoreGame.view.afficherMessage("Veuillez entrer un nombre valide. \n");
                }
            }

            // Deuxième carte à remettre
            boolean deuxiemeCarteTrouvee = false;
            while (!deuxiemeCarteTrouvee) {
                String input2 = CoreGame.view.lireInput("Quelle 2ème carte voulez-vous remettre dans la pioche? (donnez l'id) : ");
                if (input2 == null) {
                    CoreGame.view.afficherMessage("Entrée invalide.");
                    continue;
                }
                try {
                    int choix2 = Integer.parseInt(input2.trim());

                    Card deuxiemeCarte = null;
                    for (Card c : this.hand) {
                        if (choix2 == c.getIdCard()) {
                            deuxiemeCarte = c;
                            break;
                        }
                    }

                    if (deuxiemeCarte != null) {
                        deuxiemeCarte.mettreDansPioche();
                        hand.remove(deuxiemeCarte);
                        deuxiemeCarteTrouvee = true;
                    } else {
                        CoreGame.view.afficherMessage("L'id donné ne correspond à aucune carte. Réessayez. \n");
                    }
                } catch (NumberFormatException e) {
                    CoreGame.view.afficherMessage("Veuillez entrer un nombre valide. \n");
                }
            }
        }

        if(this.hand.size() < 1)
        {
            // Ne devrait jamais arriver
            CoreGame.view.afficherMessage("Erreur : Vous une seule carte en main.");
        }
        if(this.hand.size() >= 4)
        {
            // Ne devrait jamais arriver
            CoreGame.view.afficherMessage("Erreur : Vous avez trop de cartes en main.");
        }
    }
}


