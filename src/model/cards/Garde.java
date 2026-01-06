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
        CoreGame.view.afficherMessage("La Garde a été jouée. Vous allez tenter de deviner la carte d'un autre joueur afin de l'éliminer");

        Player cible = CoreGame.demanderCible(joueurActif, this);
        if (cible == null) {
            CoreGame.view.afficherMessage("Aucune cible disponible pour la Garde. L'effet est annulé.");
            return;
        }

        boolean devineValide = false;
        String supposition = "";

        while (!devineValide) {
            supposition = CoreGame.view.lireInput(joueurActif.getNom() + ", quelle carte pensez-vous que " + cible.getNom() + " possède ?\nCartes possibles : " + String.join(", ", NOMS_CARTES_VALIDES));

            if (supposition == null) {
                CoreGame.view.afficherMessage("Entrée invalide.");
                continue;
            }

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
                CoreGame.view.afficherMessage("Nom de carte invalide. Essayez encore.");
            } else {
                devineValide = true;
            }
        }

        if (!cible.hand.isEmpty() && cible.hand.get(0).getNameCard().equalsIgnoreCase(supposition)) {
            CoreGame.view.afficherMessage("Vous avez deviné correctement ! " + cible.getNom() + " est éliminé.");
            cible.elimination();
        } else {
            CoreGame.view.afficherMessage("Vous n'avez pas deviné correctement.");
        }
    }

    public Garde() {
        super("Garde", 1);
    }
}