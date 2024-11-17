import javax.swing.ImageIcon;

public abstract class Carte {
    private String nom;
    private TypeCarte type;
    private ImageIcon image;

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
