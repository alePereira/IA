package IAPlayer;

import gameModel.GlobalValues;

/**
 * Created by Alexandre on 01/04/2016.
 */
public abstract class Etat {
    protected int[][]grille;
    protected int bille;

    public int[][] getGrille() {
        Etat etat = this.clone();
        return etat.grille;
    }

    public int getBille() {
        return bille;
    }

    public int[][] cloneGrille() {
        int[][] copie = grille.clone();
        for(int i = 0; i <grille.length;i++){
            copie[i] = grille[i].clone();
        }
        return copie;
    }

    @Override
    public abstract Etat clone();
}
