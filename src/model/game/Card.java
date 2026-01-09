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
        CoreGame.view.afficherMessage(joueurActif.getNom() + " joue la carte " + this.nameCard);
        this.appliquerEffet(joueurActif);
        this.defausser(joueurActif);
        CoreGame.view.afficherMessage("✅ Carte " + this.nameCard + " a été défaussée.");
    }

    public void cacher() {
        this.stateCard = State.CACHEE;
    }

    @Override //Pour afficher la carte au joueur (les vérifications se feront dans la méthode affiche de la classe Player)
    public String toString() {
        String symbole = "";
        String couleur = "";

        switch (nameCard) {
            case "Princesse":
                symbole = "👑";
                couleur = "ROSE";
                break;
            case "Comtesse":
                symbole = "👸";
                couleur = "VIOLET";
                break;
            case "Roi":
                symbole = "🤴";
                couleur = "OR";
                break;
            case "Chancelier":
                symbole = "💼";
                couleur = "GRIS";
                break;
            case "Prince":
                symbole = "👨‍💼";
                couleur = "BLEU";
                break;
            case "Servante":
                symbole = "👰";
                couleur = "BEIGE";
                break;
            case "Baron":
                symbole = "🎩";
                couleur = "MARRON";
                break;
            case "Pretre":
                symbole = "🙏";
                couleur = "BLANC";
                break;
            case "Garde":
                symbole = "🛡️";
                couleur = "ARGENT";
                break;
            case "Espionne":
                symbole = "🕵️";
                couleur = "INDIGO";
                break;
            default:
                symbole = "🃏";
                couleur = "NOIR";
        }

        return String.format("" +
                        "┌─────────────────┐\n" +
                        "│ %-15s \n" +
                        "├─────────────────┤\n" +
                        "│ %-2s Valeur: %-2d   \n" +
                        "│ %-15s \n" +
                        "│ ID: %-11d \n" +
                        "└─────────────────┘",
                nameCard,
                symbole, valueCard,
                couleur,
                idCard);
    }

    // Méthode "virtuelle pure" (méthode abstraite)
    // Elle devra être définie dans chaque sous-classe 
    public abstract void appliquerEffet(Player joueurActif); 
    //Paramètres à changer en fonction des cartes
}

