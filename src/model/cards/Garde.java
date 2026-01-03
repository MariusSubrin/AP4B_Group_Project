package model.cards;

import model.game.*;
import controller.CoreGame;
import java.util.Arrays;
import java.util.List;

public class Garde extends Card {
    private static final List<String> NOMS_CARTES_VALIDES = Arrays.asList(
            "Princesse", "Comtesse", "Roi", "Chancelier", "Prince",
            "Servante", "Baron", "Pretre", "Garde", "Espionne"
    );

    @Override
    public void appliquerEffet(Player joueurActif) {
        System.out.println("La Garde a été jouée. Vous allez tenter de deviner la carte d'un autre joueur afin de l'éliminer");

        Player cible = CoreGame.demanderCible(joueurActif, this);
        if (cible == null) {
            System.out.println("Aucune cible disponible pour la Garde. L'effet est annulé.");
            return;
        }

        boolean devineValide = false;
        String supposition = "";

        while (!devineValide) {
            System.out.println(joueurActif.getNom() + ", quelle carte pensez-vous que " + cible.getNom() + " possède ?");
            System.out.println("Cartes possibles : " + String.join(", ", NOMS_CARTES_VALIDES));
            System.out.print("> ");

            supposition = CoreGame.sc.nextLine().trim();

            // Vérifier que c'est un nom de carte valide
            boolean carteValide = false;
            for (String nomCarte : NOMS_CARTES_VALIDES) {
                if (supposition.equalsIgnoreCase(nomCarte)) {
                    carteValide = true;
                    supposition = nomCarte; // Normaliser le nom
                    break;
                }
            }

            if (!carteValide) {
                System.out.println("Nom de carte invalide. Essayez encore.");
            } else {
                devineValide = true;
            }
        }

        if (!cible.hand.isEmpty() && cible.hand.get(0).getNameCard().equalsIgnoreCase(supposition)) {
            System.out.println("Vous avez deviné correctement ! " + cible.getNom() + " est éliminé.");
            cible.elimination();
        } else {
            System.out.println("Vous n'avez pas deviné correctement.");
        }
    }

    public Garde() {
        super("Garde", 1);
    }
}