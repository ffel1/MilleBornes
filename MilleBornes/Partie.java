import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Partie {
    private int points;
    private List<Joueur> joueurs;

    public Partie(){
        points = 0;
        joueurs = new ArrayList<Joueur>();
    }

    public static void main(String[] args){
        JFrame fenetre = new JFrame();
        fenetre.setVisible(true);
        JPanel panel = new JPanel();
        fenetre.setContentPane(panel);

        // Fentre de la taille de l'écran
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int hauteur = (int)dimension.getHeight();
        int largeur  = (int)dimension.getWidth();
        fenetre.setSize(largeur, hauteur);
        panel.setLayout(new BorderLayout());

        // Bouton jouer
        JPanel boutonJouerPanel = new JPanel();
        boutonJouerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(boutonJouerPanel, BorderLayout.NORTH);
        JButton boutonJouer = new JButton("Jouer");
        boutonJouer.setVisible(true);
        boutonJouer.setPreferredSize(new Dimension(150, 50));
        //boutonJouer.addActionListener(e -> System.out.println("Bouton jouer"));
        boutonJouerPanel.add(boutonJouer, BorderLayout.CENTER);

        //  Bouton quitter
        JPanel boutonPanel = new JPanel();
        boutonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(boutonPanel, BorderLayout.SOUTH);
        JButton boutonQuitter = new JButton("Quitter");
        boutonQuitter.setVisible(true);
        boutonQuitter.setPreferredSize(new Dimension(150, 50));
        boutonQuitter.addActionListener(e -> System.exit(0));
        boutonPanel.add(boutonQuitter, BorderLayout.CENTER);

        // Image
        ImageIcon image = new ImageIcon("MilleBornes.png");
        JLabel label = new JLabel(image);
        label.setVisible(true);
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel.add(imagePanel, BorderLayout.CENTER);
        imagePanel.add(label);

        boutonPanel.setBackground(Color.GREEN);
        boutonJouerPanel.setBackground(Color.GREEN);
        //panel.add(label, BorderLayout.CENTER);
        panel.setBackground(Color.ORANGE);
    }

    public void nouvellePartie(){};
    public void quitterPartie(){};
    public void sauvegarderPartie(){};
    public boolean jouerCarte(Carte c, Joueur u, Joueur cible){
        return true;
    };
    public void jouer(Carte c, Joueur u, Joueur cible){};
    public void afficherAction(Carte c, Joueur u, Joueur cible){};
    public void ajouterHistorique(Carte c, Joueur u, Joueur cible){};
    public void nouvelleManche(){};
}
