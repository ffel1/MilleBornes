package main;

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
                return "AS_DU_VOLANT";
            case CAMION_CITERNE:
                return "CAMION_CITERNE";
            case INCREVABLE:
                return "INCREVABLE";
            case VEHICULE_PRIORITAIRE:
                return "VEHICULE_PRIORITAIRE";
            case CREVAISON:
                return "CREVAISON";
            case ACCIDENT:
                return "ACCIDENT";
            case PANNE_D_ESSENCE:
                return "PANNE_D_ESSENCE";
            case LIMITATION_DE_VITESSE:
                return "LIMITE_DE_VITESSE";
            case FEU_ROUGE:
                return "FEU_ROUGE";
            case ROUE_DE_SECOURS:
                return "ROUE_DE_SECOURS";
            case REPARATION:
                return "REPARATION";
            case ESSENCE:
                return "ESSENCE";
            case FIN_LIMITATION_VITESSE:
                return "FIN_LIMITATION_VITESSE";
            case FEU_VERT:
                return "FEU_VERT";
            case _25KM:
                return "25_KM";
            case _50KM:
                return "50_KM";
            case _75KM:
                return "75_KM";
            case _100KM:
                return "100_KM";
            case _200KM:
                return "200_KM";
            default:
                return ""; 
        }
    }
}