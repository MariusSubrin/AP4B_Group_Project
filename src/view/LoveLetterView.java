package view;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class LoveLetterView extends JFrame {

    private JTextArea zoneTexte;
    private JTextField zoneSaisie;
    private JButton boutonValider;
    private JPanel panelJoueurs;
    private JLabel labelPioche;
    private JLabel labelDefausse;
    private JLabel labelCarteCachee;

    // File d'attente pour les inputs (thread-safe)
    private final BlockingQueue<String> inputQueue = new LinkedBlockingQueue<>();

    public LoveLetterView() {
        initUI();
    }

    private void initUI() {
        // Configuration de la fenêtre
        setTitle("💌 Love Letter");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Utiliser le look and feel du système
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Panel principal avec BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(240, 248, 255));

        // ========== PANEL SUPÉRIEUR : Infos du jeu ==========
        JPanel topPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        topPanel.setBackground(new Color(255, 250, 240));
        topPanel.setBorder(BorderFactory.createTitledBorder("📊 État du jeu"));

        // Carte cachée
        JPanel panelCachee = new JPanel(new BorderLayout());
        panelCachee.setBorder(BorderFactory.createTitledBorder("🃏 Carte cachée"));
        labelCarteCachee = new JLabel("Non définie", SwingConstants.CENTER);
        labelCarteCachee.setFont(new Font("Arial", Font.BOLD, 14));
        panelCachee.add(labelCarteCachee, BorderLayout.CENTER);
        panelCachee.setBackground(new Color(255, 250, 240));

        // Pioche
        JPanel panelPioche = new JPanel(new BorderLayout());
        panelPioche.setBorder(BorderFactory.createTitledBorder("📚 Pioche"));
        labelPioche = new JLabel("20 cartes", SwingConstants.CENTER);
        labelPioche.setFont(new Font("Arial", Font.BOLD, 14));
        panelPioche.add(labelPioche, BorderLayout.CENTER);
        panelPioche.setBackground(new Color(255, 250, 240));

        // Défausse
        JPanel panelDefausse = new JPanel(new BorderLayout());
        panelDefausse.setBorder(BorderFactory.createTitledBorder("🗑️ Défausse"));
        labelDefausse = new JLabel("0 cartes", SwingConstants.CENTER);
        labelDefausse.setFont(new Font("Arial", Font.BOLD, 14));
        panelDefausse.add(labelDefausse, BorderLayout.CENTER);
        panelDefausse.setBackground(new Color(255, 250, 240));

        topPanel.add(panelCachee);
        topPanel.add(panelPioche);
        topPanel.add(panelDefausse);

        // ========== PANEL CENTRAL : Log et joueurs ==========
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        // Zone de texte pour le log
        zoneTexte = new JTextArea();
        zoneTexte.setEditable(false);
        zoneTexte.setLineWrap(true);
        zoneTexte.setWrapStyleWord(true);
        zoneTexte.setFont(new Font("Monospaced", Font.PLAIN, 13));
        zoneTexte.setBackground(new Color(255, 253, 245));
        zoneTexte.setBorder(BorderFactory.createTitledBorder("📜 Historique des actions"));

        JScrollPane scrollTexte = new JScrollPane(zoneTexte);
        scrollTexte.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Panel des joueurs
        panelJoueurs = new JPanel();
        panelJoueurs.setLayout(new BoxLayout(panelJoueurs, BoxLayout.Y_AXIS));
        panelJoueurs.setBorder(BorderFactory.createTitledBorder("👥 Joueurs en jeu"));
        panelJoueurs.setBackground(new Color(255, 250, 240));

        JScrollPane scrollJoueurs = new JScrollPane(panelJoueurs);
        scrollJoueurs.setPreferredSize(new Dimension(250, 0));

        centerPanel.add(scrollTexte, BorderLayout.CENTER);
        centerPanel.add(scrollJoueurs, BorderLayout.EAST);

        // ========== PANEL INFÉRIEUR : Saisie ==========
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JLabel labelSaisie = new JLabel("Votre réponse : ");
        labelSaisie.setFont(new Font("Arial", Font.BOLD, 14));

        zoneSaisie = new JTextField();
        zoneSaisie.setFont(new Font("Arial", Font.PLAIN, 14));
        zoneSaisie.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(165, 42, 42), 2),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        boutonValider = new JButton("Valider");
        boutonValider.setFont(new Font("Arial", Font.BOLD, 14));
        boutonValider.setBackground(new Color(165, 42, 42));
        boutonValider.setForeground(Color.WHITE);
        boutonValider.setFocusPainted(false);
        boutonValider.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Ajouter les composants au panel
        bottomPanel.add(labelSaisie, BorderLayout.WEST);
        bottomPanel.add(zoneSaisie, BorderLayout.CENTER);
        bottomPanel.add(boutonValider, BorderLayout.EAST);

        // ========== ASSEMBLAGE FINAL ==========
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // Configuration des listeners
        configurerListeners();

        // Rendre la fenêtre visible
        setVisible(true);

        // Focus sur le champ de saisie
        SwingUtilities.invokeLater(() -> zoneSaisie.requestFocusInWindow());
    }

    private void configurerListeners() {
        // Action pour le bouton et la touche Entrée
        boutonValider.addActionListener(e -> validerSaisie());
        zoneSaisie.addActionListener(e -> validerSaisie());
    }

    private void validerSaisie() {
        String texte = zoneSaisie.getText().trim();
        if (!texte.isEmpty()) {
            // Ajouter à la file d'attente
            inputQueue.offer(texte);
            // Effacer le champ
            zoneSaisie.setText("");
        }
    }

    // ========== MÉTHODES PUBLIQUES ==========

    public void afficherMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            if (message.contains("gagnant") || message.contains("gagne") || message.contains("Gagnant")) {
                zoneTexte.append("🏆 " + message + "\n");
            } else if (message.contains("éliminé") || message.contains("Éliminé")) {
                zoneTexte.append("💀 " + message + "\n");
            } else if (message.contains("tour") || message.contains("Tour")) {
                zoneTexte.append("🔄 " + message + "\n");
            } else if (message.contains("pioche") || message.contains("Pioche")) {
                zoneTexte.append("📚 " + message + "\n");
            } else if (message.contains("joue") || message.contains("Joue")) {
                zoneTexte.append("🎴 " + message + "\n");
            } else {
                zoneTexte.append(message + "\n");
            }

            // Auto-scroll
            zoneTexte.setCaretPosition(zoneTexte.getDocument().getLength());
        });
    }

    public String lireInput(String prompt) {
        // Afficher le prompt
        afficherMessage(prompt);

        // Afficher un indicateur visuel pour la saisie
        SwingUtilities.invokeLater(() -> {
            zoneTexte.append("> "); // Indicateur de saisie
            zoneTexte.setCaretPosition(zoneTexte.getDocument().getLength());
        });

        try {
            SwingUtilities.invokeLater(() -> {
                zoneSaisie.requestFocusInWindow();
                zoneSaisie.setText("");
            });

            // Attendre l'entrée
            String input = inputQueue.poll(300, TimeUnit.SECONDS);

            // Afficher ce que l'utilisateur a saisi
            if (input != null) {
                final String finalInput = input;
                SwingUtilities.invokeLater(() -> {
                    zoneTexte.append(finalInput + "\n");
                    zoneTexte.setCaretPosition(zoneTexte.getDocument().getLength());
                });
            }

            return input;

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public void mettreAJourJoueurs(String[] infosJoueurs) {
        SwingUtilities.invokeLater(() -> {
            panelJoueurs.removeAll();

            if (infosJoueurs == null || infosJoueurs.length == 0) {
                JLabel label = new JLabel("Aucun joueur", SwingConstants.CENTER);
                label.setFont(new Font("Arial", Font.ITALIC, 12));
                panelJoueurs.add(label);
            } else {
                for (String info : infosJoueurs) {
                    JPanel carteJoueur = creerCarteJoueur(info);
                    panelJoueurs.add(carteJoueur);
                    panelJoueurs.add(Box.createRigidArea(new Dimension(0, 10)));
                }
            }

            panelJoueurs.revalidate();
            panelJoueurs.repaint();
        });
    }

    private JPanel creerCarteJoueur(String info) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setMaximumSize(new Dimension(280, 100));
        panel.setBackground(Color.WHITE);

        // Parser les infos (format: "Nom | Faveurs | Statut | Protection")
        String[] parts = info.split("\\|");
        String nom = parts.length > 0 ? parts[0].trim() : "Inconnu";
        String faveurs = parts.length > 1 ? parts[1].trim() : "0 faveurs";
        String statut = parts.length > 2 ? parts[2].trim() : "En jeu";
        String protection = parts.length > 3 ? parts[3].trim() : "";

        // Couleur selon le statut
        if (statut.contains("Éliminé")) {
            panel.setBackground(new Color(255, 230, 230));
        } else if (protection.contains("Protégé")) {
            panel.setBackground(new Color(230, 255, 230));
        }

        // Nom
        JLabel labelNom = new JLabel("👤 " + nom);
        labelNom.setFont(new Font("Arial", Font.BOLD, 14));

        // Détails
        JLabel labelDetails = new JLabel("<html>"
                + "🏆 " + faveurs + "<br/>"
                + "📊 " + statut + "<br/>"
                + (protection.isEmpty() ? "" : "🛡️ " + protection)
                + "</html>");
        labelDetails.setFont(new Font("Arial", Font.PLAIN, 12));

        panel.add(labelNom, BorderLayout.NORTH);
        panel.add(labelDetails, BorderLayout.CENTER);

        return panel;
    }

    public void mettreAJourInfosJeu(int taillePioche, int tailleDefausse, String carteCacheeInfo) {
        SwingUtilities.invokeLater(() -> {
            labelPioche.setText(taillePioche + " cartes");
            labelDefausse.setText(tailleDefausse + " cartes");
            if (carteCacheeInfo != null) {
                labelCarteCachee.setText(carteCacheeInfo);
            }
        });
    }

    public void afficherSeparateur() {
        SwingUtilities.invokeLater(() -> {
            zoneTexte.append("\n════════════════════════════════════════════════════\n");
            zoneTexte.setCaretPosition(zoneTexte.getDocument().getLength());
        });
    }
}