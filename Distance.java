public class Distance extends Carte{
    private int kilometre;

    public Distance(TypeCarte t, int k){
        super("Distance"+t.toString(), t);
        kilometre = k;
    }

    public int getKilometre(){
        return kilometre;
    }
}