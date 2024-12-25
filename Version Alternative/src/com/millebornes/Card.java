package com.millebornes;

import java.io.Serializable;
import javax.swing.ImageIcon;

/**
 * The Card class represents a generic card in the game.
 * 
 * This abstract class serves as the base class for all types of cards in the game. 
 * A card has a name, a type, an image, and a value associated with kilometers, which are used to compare distances.
 * The card's image can be manipulated and reset, and it can be assigned different values based on its type.
 * 
 * Responsibilities:
 * - Store and manage the name, type, image, and kilometer value for each card.
 * - Provide methods to retrieve and modify these attributes.
 * - Serve as a base class for specific card types such as Attack or Boot cards.
 * 
 * @see TypeCard
 * @see ImageIcon
 * @see Attack
 * @see Boot
 */
public abstract class Card implements Serializable {
    
    // Private member to store the card's name
    private String name;
    
    // Private member to store the card's type
    private TypeCard type;
    
    // Private member to store the current image of the card
    private ImageIcon imageCourante, vraieImage;
    
    // Private member to store the card's kilometers value for distance comparison
    private int km;

    /**
     * Constructor to initialize the card with its name, type, and corresponding image.
     * The constructor assigns a kilometers value based on the card's type, 
     * and sets up the card's image using the name of the type.
     * 
     * @param n The name of the card.
     * @param t The type of the card (from the TypeCard enum).
     */
    public Card(String n, TypeCard t) {
        // Assign kilometers based on the card type
        if(t == TypeCard._25KM) {
            km = 25;
        } else if(t == TypeCard._50KM) {
            km = 50;
        } else if(t == TypeCard._75KM) {
            km = 75;
        } else if(t == TypeCard._100KM) {
            km = 100;
        } else if(t == TypeCard._200KM) {
            km = 200;
        } else {
            km = 0;
        }

        // Set the card's name and type
        name = n;
        type = t;
        
        // Set the card's image based on the type
        vraieImage = new ImageIcon(getClass().getResource("/Images/" + t.toString() + ".png"));
        imageCourante = vraieImage;  // Save the original image to revert back to it if needed
    }

    /**
     * Returns the name of the card.
     * 
     * @return The name of the card.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the type of the card.
     * 
     * @return The type of the card (from the TypeCard enum).
     */
    public TypeCard getType() {
        return type;
    }

    /**
     * Returns the current image of the card.
     * 
     * @return The current image of the card as an ImageIcon.
     */
    public ImageIcon getImage() {
        return imageCourante;
    }

    /**
     * Sets the image of the card to a new image.
     * 
     * @param i The new image to set as the card's current image.
     */
    public void setImageIcon(ImageIcon i) {
        imageCourante = i;
    }

    /**
     * Resets the card's image to its original image.
     * 
     * This method restores the card's image to the original one stored at initialization.
     */
    public void setImageBack() {
        imageCourante = vraieImage;
    }

    /**
     * Returns the kilometers value of the card.
     * 
     * @return The kilometers value associated with the card, used for distance comparison.
     */
    public int getKilometers() {
        return km;
    }
}