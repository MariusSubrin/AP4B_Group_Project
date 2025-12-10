package game;

import cards.Card;
import java.util.ArrayList;
import java.util.List;

public class CoreGame {
    public static List<Card> 
    pioche = new ArrayList<Card>(), // Pioche principale
    carteDefausse = new ArrayList<Card>(); // Liste des cartes défaussées (Cartes visibles pour tous les joueurs)
    public static List<Player> joueurs = new ArrayList<Player>();
}

