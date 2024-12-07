import java.io.Serializable;
import javax.swing.ImageIcon;

public abstract class Carte implements Serializable{
    private String nom;
    private TypeCarte type;
    private ImageIcon image;
    private int km; //pour comparer les distances entre elles

    public Carte(String n, TypeCarte t){
        if(t == TypeCarte._25KM)
        {
            km = 25;
        }
        else if(t == TypeCarte._50KM)
        {
            km = 50;
        }
        else if(t == TypeCarte._75KM)
        {
            km = 75;
        }
        else if(t == TypeCarte._100KM)
        {
            km = 100;
        }
        else if(t == TypeCarte._200KM)
        {
            km = 200;
        }
        else
        {
            km = 0;
        }
        nom = n;
        type = t;
        image = new ImageIcon("Images/" + t.toString() + ".png");
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
    public int getKilometre()
    {
        return km;
    }
}
