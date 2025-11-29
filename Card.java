public abstract class Card {

    // Enum pour représenter l'état de la carte
    public enum State {
        CACHEE,    // carte mise de côté au début
        DANS_PIOCHE,
        DANS_MAIN,
        DEFAUSSEE
    }

    private static int compteurId = 0;

    protected int idCard;
    protected String nameCard;
    protected State stateCard;


    public Card(String nameCard) {
        this.idCard = compteurId++;
        this.nameCard = nameCard;
        this.stateCard = State.DANS_PIOCHE; // par défaut : dans la pioche
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

    // Méthodes pour changer l'état
    public void mettreDansPioche() {
        this.stateCard = State.DANS_PIOCHE;
    }

    public void mettreDansMain() {
        this.stateCard = State.DANS_MAIN;
    }

    public void defausser() {
        this.stateCard = State.DEFAUSSEE;
    }

    public void cacher() {
        this.stateCard = State.CACHEE;
    }

    @Override //Pour afficher la carte au joueur
    public String toString() {
        return "Card{id=" + idCard +
               ", name='" + nameCard + '\'' +
               ", state=" + stateCard +
               '}';
    }

    // Méthode "virtuelle pure" (méthode abstraite)
    // Elle devra être définie dans chaque sous-classe 
    public abstract void appliquerEffet(); 
    //Paramètres à changer en fonction des cartes
}
