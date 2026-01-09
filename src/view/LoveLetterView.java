package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoveLetterView extends JFrame {

    private JTextArea zoneTexte;
    private JTextField zoneSaisie;
    private JButton boutonValider;
    private String currentInput;
    private boolean inputAvailable = false;

    public LoveLetterView() {
        setTitle("Love Letter");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // centre la fenêtre
        setResizable(true);

        zoneTexte = new JTextArea();
        zoneTexte.setEditable(false);
        zoneTexte.setFont(new Font("Monospaced", Font.PLAIN, 14));
        zoneTexte.setBackground(new Color(250, 250, 250));
        zoneTexte.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        zoneSaisie = new JTextField();
        zoneSaisie.setFont(new Font("SansSerif", Font.PLAIN, 14));
        zoneSaisie.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        boutonValider = new JButton("Valider");
        boutonValider.setFont(new Font("SansSerif", Font.BOLD, 14));
        boutonValider.setBackground(new Color(70, 130, 180));
        boutonValider.setForeground(Color.WHITE);
        boutonValider.setFocusPainted(false);

        JScrollPane scroll = new JScrollPane(zoneTexte);
        scroll.setBorder(BorderFactory.createTitledBorder("Messages du jeu"));

        JPanel bas = new JPanel();
        bas.setLayout(new BoxLayout(bas, BoxLayout.X_AXIS));
        bas.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bas.add(zoneSaisie);
        bas.add(Box.createHorizontalStrut(10));
        bas.add(boutonValider);

        // Ajouter un titre en haut
        JLabel titre = new JLabel("Love Letter", SwingConstants.CENTER);
        titre.setFont(new Font("Arial", Font.BOLD, 28));
        titre.setForeground(new Color(70, 130, 180));
        JPanel haut = new JPanel();
        haut.add(titre);

        boutonValider.addActionListener(e -> {
            currentInput = zoneSaisie.getText();
            zoneTexte.append("Saisie utilisateur : " + currentInput + "\n");
            zoneSaisie.setText("");
            inputAvailable = true;
        });

        // Ajouter support pour la touche Entrée
        zoneSaisie.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    currentInput = zoneSaisie.getText();
                    zoneTexte.append("Saisie utilisateur : " + currentInput + "\n");
                    zoneSaisie.setText("");
                    inputAvailable = true;
                }
            }
        });

        add(haut, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(bas, BorderLayout.SOUTH);

        setVisible(true); // 🔥 la fenêtre apparaît
    }

    public void afficherMessage(String msg) {
        zoneTexte.append(msg + "\n");
    }

    public String lireSaisie() {
        String texte = zoneSaisie.getText();
        zoneTexte.append("Saisie utilisateur : " + texte + "\n");
        zoneSaisie.setText("");
        return texte;
    }

    public String lireInput(String prompt) {
        afficherMessage(prompt);
        inputAvailable = false;
        while (!inputAvailable) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return currentInput;
    }
}