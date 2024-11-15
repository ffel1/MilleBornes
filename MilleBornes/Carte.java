public abstract class Carte {
    private String nom;
    private TypeCarte type;

    public Carte(String n, TypeCarte t){
        nom = n;
        type = t;
    }

    public String getNom(){
        return nom;
    }
    public TypeCarte getType(){
        return type;
    }
}
