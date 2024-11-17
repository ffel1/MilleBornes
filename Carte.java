import javax.swing.ImageIcon;

public abstract class Carte {
    private String nom;
    private TypeCarte type;
    private ImageIcon image;

    //Constructeur avec nom, type et ImageIcon image pour les cartes
    public Carte(String n, TypeCarte t){
        nom = n;
        type = t;
        image = new ImageIcon("images/"+t.toString()+".png");
    }

    public String getNom(){
        return nom;
    }

    public TypeCarte getType(){
        return type;
    }

    public ImageIcon getImage(){
        return image;
    }
}
