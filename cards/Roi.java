package cards;

public class Roi extends Card{
    @Override
    public void appliquerEffet() {
        // L'effet du Roi est d'échanger sa main avec un autre joueur.
        System.out.println("Le Roi a été joué. Échange de deux mains.");
    }

    public Roi() {
        super("Roi", 6);
    }
}
