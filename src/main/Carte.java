package main;

import java.io.Serializable;
import javax.swing.ImageIcon;

public abstract class Carte implements Serializable{
    private String nom;
    private TypeCarte type;
    private ImageIcon imageCourante, vraieImage;
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
        vraieImage = new ImageIcon("Images/" + t.toString() + ".png");
        imageCourante = vraieImage;  //je sauvegarde l'image de la carte comme ça si je change l'image je peux la récupérer après
    }

    public String getNom(){
        return nom;
    }

    public TypeCarte getType(){
        return type;
    }

    public ImageIcon getImage(){
        return imageCourante;
    }

    public void setImageIcon(ImageIcon i)
    {
        imageCourante = i;
    }

    public void setImageBack()
    {
        imageCourante = vraieImage;
    }
    
    public int getKilometre()
    {
        return km;
    }
}
