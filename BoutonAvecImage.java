import javax.swing.*;
public class BoutonAvecImage {
    public static void main(String[] args) {
        // Création de la fenêtre principale
        JFrame frame = new JFrame("Bouton avec Image");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Chargement de l'image
        ImageIcon icon = new ImageIcon("Images/25_KM.png"); // Remplacez par le chemin de votre image

        // Création d'un bouton avec l'image
        JButton bouton = new JButton("", icon);
        bouton.setBounds(100, 100, 106, 150);
        bouton.setHorizontalTextPosition(SwingConstants.CENTER); // Position du texte
        bouton.setVerticalTextPosition(SwingConstants.BOTTOM);   // Position du texte par rapport à l'image
        bouton.setFocusPainted(false); // Supprime le focus visuel sur le bouton
        //bouton.setBorderPainted(false); // Supprime les bordures
        bouton.setContentAreaFilled(false); // Rend le bouton plat (sans remplissage)

        // Ajout du bouton à la fenêtre
        frame.add(bouton);

        // Affichage de la fenêtre
        frame.setVisible(true);
    }
}
