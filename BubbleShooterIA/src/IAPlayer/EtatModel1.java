package IAPlayer;

/**
 * Created by Alexandre on 01/04/2016.
 */
public class EtatModel1 extends Etat{

    public EtatModel1(int[][]grille, int bille){
       this.grille = grille;
        this.bille = bille;
    }


    @Override
    public Etat clone() {
        EtatModel1 etat = new EtatModel1(this.cloneGrille(),bille);
        return etat;
    }

}
