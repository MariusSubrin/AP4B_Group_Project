package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cards.Card;

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

    public Player(String nom) {
        this.id = compteurId++;   // ID unique auto-incrémenté
        this.nom = nom;
        this.nombre_faveur = 0;
        this.elimine = false;
        this.protection = false;
        this.espionne_jouee = false;
        this.hand = new ArrayList<Card>();
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

    public void newRound() {
        this.resurrection();
        this.protectionOff();
        this.resetEspionneJouee();
    }

    public void choixCarte(){
        if(this.hand.size() == 2){
            System.out.println("Voici vos cartes : \n");
            for (Card c : this.hand) {
                System.out.println(c.toString()); //on affiche toute ses cartes
            }

            try (Scanner sc = new Scanner(System.in)) {
                boolean flag = false;
                while (!flag){
                    System.out.println("Laquelle voulez-vous jouer ? (donnez l'id) : \n");
                    int choix = sc.nextInt();
                    for (Card c : this.hand) {
                        if (choix == c.getIdCard()){
                            c.jouerCarte(this);
                            flag = true;
                        }
                    }
                    if (!flag){
                        System.out.println("L'id donné ne correspond à aucune carte. Réessayez. \n");
                    }
                }
            }
        }

        if(this.hand.size() == 4){
            System.out.println("Voici vos cartes : \n");
            for (Card c : this.hand) {
                System.out.println(c.toString()); //on affiche toute ses cartes
            }

            try (Scanner sc = new Scanner(System.in)) {
                boolean flag_1 = false, flag_2 = false, flag_3 = false;

                while (!flag_1){
                    System.out.println("Laquelle voulez-vous jouer ? (donnez l'id) : \n");
                    int choix_1 = sc.nextInt();
                    for (Card c : this.hand) {
                        if (choix_1 == c.getIdCard()){
                            c.jouerCarte(this);
                            flag_1 = true;
                        }
                    }
                    if (!flag_1){
                        System.out.println("L'id donné ne correspond à aucune carte. Réessayez. \n");
                    }
                }

                while (!flag_2){
                    System.out.println("Quelle 1ière carte voulez-vous remettre dans la pioche? (donnez l'id) : \n");
                    int choix_2 = sc.nextInt();
                    for (Card c : this.hand) {
                        if (choix_2 == c.getIdCard()){
                            c.mettreDansPioche();
                            flag_2 = true;
                        }
                    }
                    if (!flag_2){
                        System.out.println("L'id donné ne correspond à aucune carte. Réessayez. \n");
                    }
                }

                while (!flag_3){
                    System.out.println("Quelle 2sd carte voulez-vous remettre dans la pioche? (donnez l'id) : \n");
                    int choix_3 = sc.nextInt();
                    for (Card c : this.hand) {
                        if (choix_3 == c.getIdCard()){
                            c.mettreDansPioche();
                            flag_3 = true;
                        }
                    }
                    if (!flag_3){
                        System.out.println("L'id donné ne correspond à aucune carte. Réessayez. \n");
                    }
                }
            }
        }
    }
}
