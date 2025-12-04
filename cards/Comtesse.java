package cards;

public class Comtesse extends Card {
    @Override
    public void appliquerEffet() {
        // La Comtesse n'a pas d'effet direct lorsqu'elle est jouée.
        // Son effet est de forcer le joueur à la défausser s'il a le Roi ou le Prince en main.
        System.out.println("La Comtesse a été jouée.");
    }

    public Comtesse() {
        super("Comtesse", 7);
    }

}
