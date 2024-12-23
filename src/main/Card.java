package main;

import java.io.Serializable;
import javax.swing.ImageIcon;

// This class represents Card
public abstract class Card implements Serializable{
// Private or protected member
    private String name;
// Private or protected member
    private TypeCard type;
// Private or protected member
    private ImageIcon imageCourante, vraieImage;
// Private or protected member
    private int km; //pour comparer les distances entre elles

// This method handles the logic for Card
    public Card(String n, TypeCard t){
        if(t == TypeCard._25KM)
        {
            km = 25;
        }
        else if(t == TypeCard._50KM)
        {
            km = 50;
        }
        else if(t == TypeCard._75KM)
        {
            km = 75;
        }
        else if(t == TypeCard._100KM)
        {
            km = 100;
        }
        else if(t == TypeCard._200KM)
        {
            km = 200;
        }
        else
        {
            km = 0;
        }
        name = n;
        type = t;
        vraieImage = new ImageIcon("Images/" + t.toString() + ".png");
        imageCourante = vraieImage;  //je sauvegarde l'image de la card comme ça si je change l'image je peux la récupérer après
    }

// This method handles the logic for getName
    public String getName(){
        return name;
    }

// This method handles the logic for getType
    public TypeCard getType(){
        return type;
    }

// This method handles the logic for getImage
    public ImageIcon getImage(){
        return imageCourante;
    }

// This method handles the logic for setImageIcon
    public void setImageIcon(ImageIcon i)
    {
        imageCourante = i;
    }

// This method handles the logic for setImageBack
    public void setImageBack()
    {
        imageCourante = vraieImage;
    }
    
// This method handles the logic for getKilometers
    public int getKilometers()
    {
        return km;
    }
}
