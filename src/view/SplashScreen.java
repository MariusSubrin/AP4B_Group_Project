package view;

import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {

    public SplashScreen() {
        // Création du contenu du splash screen
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(new Color(255, 240, 245));

        // Panel principal avec padding
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20));
        mainPanel.setBackground(new Color(255, 240, 245));

        // Titre
        JLabel title = new JLabel("💌 LOVE LETTER 💌", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 36));
        title.setForeground(new Color(165, 42, 42));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Sous-titre
        JLabel subtitle = new JLabel("Jeu de cartes et de déduction", SwingConstants.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.ITALIC, 18));
        subtitle.setForeground(new Color(128, 0, 0));
        subtitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Cœur dessiné
        JPanel heartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();

                // Dessiner un cœur
                g2d.setColor(new Color(255, 105, 180));
                int[] xPoints = {
                        width/2,
                        (int)(width * 0.7),
                        width/2,
                        (int)(width * 0.3)
                };
                int[] yPoints = {
                        (int)(height * 0.3),
                        height/2,
                        (int)(height * 0.7),
                        height/2
                };

                // Dessiner le cœur avec une courbe
                g2d.fill(new java.awt.geom.Path2D.Double() {{
                    moveTo(width/2, height * 0.7);
                    curveTo(width * 0.8, height * 0.9, width, height * 0.5, width/2, height * 0.2);
                    curveTo(0, height * 0.5, width * 0.2, height * 0.9, width/2, height * 0.7);
                }});
            }
        };
        heartPanel.setPreferredSize(new Dimension(150, 150));

        // Message de chargement
        JLabel loading = new JLabel("Chargement...", SwingConstants.CENTER);
        loading.setFont(new Font("SansSerif", Font.PLAIN, 14));
        loading.setForeground(Color.DARK_GRAY);
        loading.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        mainPanel.add(title, BorderLayout.NORTH);
        mainPanel.add(subtitle, BorderLayout.CENTER);
        mainPanel.add(heartPanel, BorderLayout.CENTER);
        mainPanel.add(loading, BorderLayout.SOUTH);

        content.add(mainPanel, BorderLayout.CENTER);
        setContentPane(content);
        pack();

        // Centrer à l'écran
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowSize = getSize();
        int x = (screenSize.width - windowSize.width) / 2;
        int y = (screenSize.height - windowSize.height) / 2;
        setLocation(x, y);

        setVisible(true);
    }

    public void fermer() {
        setVisible(false);
        dispose();
    }
}