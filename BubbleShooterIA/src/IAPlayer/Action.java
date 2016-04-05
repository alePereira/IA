package IAPlayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Alexandre on 01/04/2016.
 */
public abstract class Action {
    /**
     * la colonne ou on envoie la bille
     */
    protected int decision;
    protected List<int[][]> etatsSuivants = new ArrayList<>();
    protected List<Integer> probabiliteTransition = new ArrayList<>();
    protected List<Integer> gainsTransition = new ArrayList<>();

    abstract protected void createTransition(EtatModel1 etatModel1Actuel);

    public int getDecision() {
        return decision;
    }

    public Iterator<int[][]> getEtatsSuivants() {
        return etatsSuivants.iterator();
    }

    public Iterator<Integer> getProbabiliteTransition() {
        return probabiliteTransition.iterator();
    }
    public Iterator<Integer> getGainsTransition() {
        return gainsTransition.iterator();
    }
}
