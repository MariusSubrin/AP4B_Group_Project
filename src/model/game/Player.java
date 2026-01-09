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

        if (this.hand.size() == 2) {
            CoreGame.view.afficherMessage("Vos 2 cartes en main :");
            for (Card c : this.hand) {
                CoreGame.view.afficherMessage(c.toString());
            }
            CoreGame.view.afficherMessage("");

            boolean flag = false;
            while (!flag) {
                String input = CoreGame.view.lireInput("Quelle carte voulez-vous jouer ? (entrez l'ID) :");

                if (input == null || input.trim().isEmpty()) {
                    CoreGame.view.afficherMessage("❌ Entrée invalide.");
                    continue;
                }

                try {
                    int choix = Integer.parseInt(input.trim());

                    // Recherche de la carte
                    Card carteAJouer = null;
                    for (Card c : this.hand) {
                        if (choix == c.getIdCard()) {
                            carteAJouer = c;
                            flag = true;
                            break;
                        }
                    }

                    if (carteAJouer != null) {
                        CoreGame.view.afficherMessage("Vous jouez la carte : " + carteAJouer.getNameCard());
                        carteAJouer.jouerCarte(this);
                    } else {
                        CoreGame.view.afficherMessage("❌ L'ID donné ne correspond à aucune carte. Réessayez.");
                    }
                } catch (NumberFormatException e) {
                    CoreGame.view.afficherMessage("❌ Veuillez entrer un nombre valide.");
                }
            }
        }

        if (this.hand.size() == 3) {
            CoreGame.view.afficherMessage("Vous avez 3 cartes (Chancelier). Choisissez 2 cartes à remettre :");
            for (Card c : this.hand) {
                CoreGame.view.afficherMessage(c.toString());
            }

            // Première carte à remettre
            Card premiereCarte = null;
            while (premiereCarte == null) {
                String input1 = CoreGame.view.lireInput("1ère carte à remettre dans la pioche (entrez l'ID) :");

                if (input1 == null || input1.trim().isEmpty()) {
                    CoreGame.view.afficherMessage("❌ Entrée invalide.");
                    continue;
                }

                try {
                    int choix1 = Integer.parseInt(input1.trim());

                    for (Card c : this.hand) {
                        if (choix1 == c.getIdCard()) {
                            premiereCarte = c;
                            break;
                        }
                    }

                    if (premiereCarte != null) {
                        CoreGame.view.afficherMessage("✅ 1ère carte : " + premiereCarte.getNameCard());
                        premiereCarte.mettreDansPioche();
                    } else {
                        CoreGame.view.afficherMessage("❌ ID invalide.");
                    }
                } catch (NumberFormatException e) {
                    CoreGame.view.afficherMessage("❌ Veuillez entrer un nombre valide.");
                }
            }

            // Deuxième carte à remettre
            Card deuxiemeCarte = null;
            while (deuxiemeCarte == null) {
                String input2 = CoreGame.view.lireInput("2ème carte à remettre dans la pioche (entrez l'ID) :");

                if (input2 == null || input2.trim().isEmpty()) {
                    CoreGame.view.afficherMessage("❌ Entrée invalide.");
                    continue;
                }

                try {
                    int choix2 = Integer.parseInt(input2.trim());

                    for (Card c : this.hand) {
                        if (choix2 == c.getIdCard() && c != premiereCarte) {
                            deuxiemeCarte = c;
                            break;
                        }
                    }

                    if (deuxiemeCarte != null) {
                        CoreGame.view.afficherMessage("✅ 2ème carte : " + deuxiemeCarte.getNameCard());
                        deuxiemeCarte.mettreDansPioche();
                    } else {
                        CoreGame.view.afficherMessage("❌ ID invalide ou même carte.");
                    }
                } catch (NumberFormatException e) {
                    CoreGame.view.afficherMessage("❌ Veuillez entrer un nombre valide.");
                }
            }

            // Maintenant choisir quelle carte jouer
            Card carteRestante = null;
            for (Card c : this.hand) {
                if (c != premiereCarte && c != deuxiemeCarte) {
                    carteRestante = c;
                    break;
                }
            }

            if (carteRestante != null) {
                CoreGame.view.afficherMessage("✅ Vous jouez la carte restante : " + carteRestante.getNameCard());
                carteRestante.jouerCarte(this);
            }
        }
    }
}
