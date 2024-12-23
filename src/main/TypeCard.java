package main;

/**
 * The TypeCard enum defines the different types of cards in the game.
 * 
 * Each card type corresponds to a specific event, condition, or action that can be performed in the game. 
 * The enum is used to categorize the cards, and each constant represents a specific type of card, such as "Expert Driver" or "Flat Tire".
 * 
 * Responsibilities:
 * - Represent all possible types of cards in the game.
 * - Provide a method to return a localized string for each card type to be displayed in the user interface.
 * 
 * This enum helps in organizing the card types, making it easier to manage their behavior and display in the game.
 * 
 * The cards in the game are categorized based on their effect or action, such as obstacles (e.g., "Flat Tire"), 
 * special abilities (e.g., "Expert Driver"), and distance modifiers (e.g., "25KM").
 */
public enum TypeCard {
    EXPERT_DRIVER,       // A card that represents an expert driver.
    TANK_TRUCK,          // A card that represents a tank truck.
    PUNCTURE_PROOF,      // A card that represents a puncture-proof ability.
    PRIOTIRY_VEHICLE,    // A card that represents a priority vehicle.
    FLAT_TIRE,           // A card that represents a flat tire (obstacle).
    ACCIDENT,            // A card that represents an accident (obstacle).
    OUT_OF_FUEL,         // A card that represents running out of fuel (obstacle).
    SPEED_LIMITATION,    // A card that represents a speed limitation.
    RED_LIGHT,           // A card that represents a red light (obstacle).
    SPARE_WHEEL,         // A card that represents a spare wheel.
    REPAIR,              // A card that represents a repair.
    FUEL,                // A card that represents fuel.
    END_SPEED_LIMITATION,// A card that represents the end of a speed limitation.
    GREEN_LIGHT,         // A card that represents a green light (actionable).
    _25KM,               // A card that represents a 25 km distance increase.
    _50KM,               // A card that represents a 50 km distance increase.
    _75KM,               // A card that represents a 75 km distance increase.
    _100KM,              // A card that represents a 100 km distance increase.
    _200KM;              // A card that represents a 200 km distance increase.

    /**
     * Returns the localized string representation of the card type.
     * 
     * This method provides the string name of the card type in French for user display.
     * Each card type corresponds to a specific action or condition, which is displayed 
     * to the player in the user interface.
     * 
     * @return A string representing the card type in French.
     */
    public String toString() {
        switch (this) {
            case EXPERT_DRIVER:
                return "As du volant";
            case TANK_TRUCK:
                return "Camion citerne";
            case PUNCTURE_PROOF:
                return "Increvable";
            case PRIOTIRY_VEHICLE:
                return "Véhicule prioritaire";
            case FLAT_TIRE:
                return "Crevaison";
            case ACCIDENT:
                return "Accident";
            case OUT_OF_FUEL:
                return "Panne d'essence";
            case SPEED_LIMITATION:
                return "Limite de vitesse"; 
            case RED_LIGHT:
                return "Feu rouge";      
            case SPARE_WHEEL:
                return "Roue de secours"; 
            case REPAIR:
                return "Réparation"; 
            case FUEL:
                return "Essence";        
            case END_SPEED_LIMITATION:
                return "Fin de limitation de vitesse";
            case GREEN_LIGHT:
                return "Feu vert";
            case _25KM:
                return "25 km";
            case _50KM:
                return "50 km";
            case _75KM:
                return "75 km";
            case _100KM:
                return "100 km"; 
            case _200KM:
                return "200 km";
            default:
                return ""; 
        }
    }
}