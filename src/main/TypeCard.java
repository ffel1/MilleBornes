package main;

public enum TypeCard {
    EXPERT_DRIVER,
    TANK_TRUCK,
    PUNCTURE_PROOF,
    PRIOTIRY_VEHICLE,
    FLAT_TIRE,
    ACCIDENT,
    OUT_OF_FUEL,
    SPEED_LIMITATION,
    RED_LIGHT,
    SPARE_WHEEL,
    REPAIR,
    FUEL,
    END_SPEED_LIMITATION,
    GREEN_LIGHT,
    _25KM,
    _50KM,
    _75KM,
    _100KM,
    _200KM;

// This method handles the logic for toString
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