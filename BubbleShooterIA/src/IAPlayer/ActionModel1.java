package IAPlayer;

import gameModel.GlobalValues;

/**
 * Created by Alexandre on 01/04/2016.
 */
public class ActionModel1 extends Action implements GlobalValues{

    public ActionModel1(int position, EtatModel1 etatModel1Actuel){
        this.decision = position;
        createTransition(etatModel1Actuel);
    }
    @Override
    protected void createTransition(EtatModel1 etatModel1Actuel){

    }

}
