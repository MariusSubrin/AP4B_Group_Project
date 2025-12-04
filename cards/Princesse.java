package cards;

public class Princesse extends Card {
    @Override
    public void appliquerEffet() {
        // La princesse n'a pas d'effet direct lorsqu'elle est jouée.
        // Son effet est de faire perdre le joueur si elle est défaussée.
        System.out.println("La Princesse a été jouée. Le joueur qui la défausse est éliminé.");
    }

    public Princesse() {
        super("Princesse", 8);
    }
}
