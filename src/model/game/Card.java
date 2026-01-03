package model.game;

import controller.CoreGame;

public abstract class Card {

    // Enum pour représenter l'état de la carte
    public enum State {
        CACHEE,    // carte mise de côté au début
        DANS_PIOCHE,
        DANS_MAIN,
        DEFAUSSEE
    }

    private static int compteurId = 0; 
    //C'est bon en private car le constructeur mère est appelé avant

    // Attributs de la classe
    protected int idCard;
    protected String nameCard;
    protected State stateCard;
    protected int valueCard;

    public Card(String nameCard, int valueCard) {
        this.idCard = compteurId++;
        this.nameCard = nameCard;
        this.valueCard = valueCard;
        this.mettreDansPioche();
    }

    // Getters
    public int getIdCard() {
        return this.idCard;
    }

    public String getNameCard() {
        return this.nameCard;
    }

    public State getStateCard() {
        return this.stateCard;
    }

    public int getValueCard() {
        return this.valueCard;
    }

    // Méthodes pour changer l'état
    public void mettreDansPioche() {
        this.stateCard = State.DANS_PIOCHE;
        CoreGame.pioche.add(0, this); //Place la carte au-dessous de la pioche
    }

    public void mettreDansMain(Player player) {
        this.stateCard = State.DANS_MAIN;
        player.hand.add(this);
        }

    public void defausser(Player player) {
        this.stateCard = State.DEFAUSSEE;
        CoreGame.carteDefausse.add(this);
        player.hand.remove(this);
    }

    public void jouerCarte(Player joueurActif) {
        System.out.println(joueurActif.getNom() + " joue la carte " + this.nameCard + ".");
        this.appliquerEffet(joueurActif);
        this.defausser(joueurActif);
    }

    public void cacher() {
        this.stateCard = State.CACHEE;
    }

    @Override //Pour afficher la carte au joueur (les vérifications se feront dans la méthode affiche de la classe Player)
    public String toString() {
        String symbole = "";
        switch (nameCard) {
            case "Princesse": symbole = "👑"; break;
            case "Comtesse": symbole = "👸"; break;
            case "Roi": symbole = "🤴"; break;
            case "Chancelier": symbole = "💼"; break;
            case "Prince": symbole = "👨‍💼"; break;
            case "Servante": symbole = "👰"; break;
            case "Baron": symbole = "🎩"; break;
            case "Pretre": symbole = "🙏"; break;
            case "Garde": symbole = "🛡️"; break;
            case "Espionne": symbole = "🕵️"; break;
            default: symbole = "🃏";
        }

        return String.format("│ %-12s %-2s │ (valeur: %d, id: %d)",
                nameCard, symbole, valueCard, idCard);
    }

    // Méthode "virtuelle pure" (méthode abstraite)
    // Elle devra être définie dans chaque sous-classe 
    public abstract void appliquerEffet(Player joueurActif); 
    //Paramètres à changer en fonction des cartes
}

