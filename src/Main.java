import controller.CoreGame;
import view.LoveLetterView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Démarrer l'interface Swing dans l'EDT
        SwingUtilities.invokeLater(() -> {
            try {
                // 1. Créer et afficher la fenêtre
                LoveLetterView fenetre = new LoveLetterView();
                CoreGame.view = fenetre;

                // 2. Attendre un peu que la fenêtre s'initialise
                Thread.sleep(500);

                // 3. Démarrer le jeu dans un thread séparé
                new Thread(() -> {
                    try {
                        Thread.sleep(300); // Petit délai supplémentaire
                        CoreGame.lancerPartie();
                    } catch (Exception e) {
                        e.printStackTrace();
                        fenetre.afficherMessage("❌ Erreur lors du démarrage du jeu: " + e.getMessage());
                    }
                }).start();

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Erreur critique: " + e.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        });
    }
}

/*
Listes de tests à faire (non compléte) :
- Utilisation de toutes les cartes en lançant le jeu

Soucis lorsque qu'on joue notre dame

Revoir les consignes pour savoir si tout est bien fait
- Gestion des exceptions

Diagramme de Gantt
 */

/*
MVC :
Afficher / demander à l’utilisateur	: View
Règles du jeu / décisions : Controller
Données (Player, Card…) : Model
 */