public enum TypeCarte {
    AS_DU_VOLANT,
    CAMION_CITERNE,
    INCREVABLE,
    VEHICULE_PRIORITAIRE,
    CREVAISON,
    ACCIDENT,
    PANNE_D_ESSENCE,
    LIMITATION_DE_VITESSE,
    FEU_ROUGE,
    ROUE_DE_SECOURS,
    REPARATION,
    ESSENCE,
    FIN_LIMITATION_VITESSE,
    FEU_VERT,
    _25KM,
    _50KM,
    _75KM,
    _100KM,
    _200KM;

    public String toString() {
        switch (this) {
            case AS_DU_VOLANT:
                return "As du Volant";
            case CAMION_CITERNE:
                return "Camion Citerne";
            case INCREVABLE:
                return "Increvable";
            case VEHICULE_PRIORITAIRE:
                return "Véhicule Prioritaire";
            case CREVAISON:
                return "Crevaison";
            case ACCIDENT:
                return "Accident";
            case PANNE_D_ESSENCE:
                return "Panne d'Essence";
            case LIMITATION_DE_VITESSE:
                return "Limite de Vitesse";
            case FEU_ROUGE:
                return "Feu Rouge";
            case ROUE_DE_SECOURS:
                return "Roue de Secours";
            case REPARATION:
                return "Réparation";
            case ESSENCE:
                return "Essence";
            case FIN_LIMITATION_VITESSE:
                return "Fin Limitation Vitesse";
            case FEU_VERT:
                return "Feu Vert";
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