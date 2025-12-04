package game;
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

    public Player(String nom) {
        this.id = compteurId++;   // ID unique auto-incrémenté
        this.nom = nom;
        this.nombre_faveur = 0;
        this.elimine = false;
        this.protection = false;
        this.espionne_jouee = false;
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
    // A chaque début de manche il faudra utiliser newRound pour chaque joueur
}
