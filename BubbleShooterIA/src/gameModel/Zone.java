package gameModel;

import java.util.ArrayList;
import java.util.Stack;
import Utilitaire.Doublet;

public class Zone implements GlobalValues{
	private int typeZone;
	private ArrayList<Doublet> posBilles = new ArrayList<>();
	private int totalPoint;
	//colonnes contenant les billes accessibles de la zone
	private ArrayList<Doublet> billesAccessibles = new ArrayList<>();
	private ArrayList<Doublet> autresBillesASuppimer = new ArrayList<>();
	private int[][] visitGrid;
	private Stack<Doublet> pile;
	
	public Zone(Doublet posInit,int[][] grille){
		typeZone = grille[posInit.getPosY()][posInit.getPosX()];
		visitGrid = new int[NB_LIGNES][NB_COLONNES];
		pile = new Stack<>();
		posBilles.add(posInit);
		if(isAccessibleBoundary(posInit,grille))
			billesAccessibles.add(posInit);

		pile.push(posInit);
		visitGrid[posInit.getPosY()][posInit.getPosX()] = 1;
		createZone(grille);
		// attention la fonction necessite la reinitialisation de visitGrid et la pile.
		determineAutresBilles(grille);
		calculTotalPoint();
	}
	
	private void createZone(int[][] grille){
		while(!pile.empty()){
			Doublet	posActuel = pile.pop();
			Doublet pos;
			if(posActuel.getPosX() > 0) {
				pos = new Doublet(posActuel.getPosX() - 1, posActuel.getPosY());
				verifPos(grille[posActuel.getPosY()][posActuel.getPosX()], pos, grille);
			}
			if(posActuel.getPosX() < NB_COLONNES-1) {
				pos = new Doublet(posActuel.getPosX() + 1, posActuel.getPosY());
				verifPos(grille[posActuel.getPosY()][posActuel.getPosX()], pos, grille);
			}
			if(posActuel.getPosY() > 0){
				pos = new Doublet(posActuel.getPosX(),posActuel.getPosY()-1);
				verifPos(grille[posActuel.getPosY()][posActuel.getPosX()],pos,grille);
			}

			if(posActuel.getPosY() < NB_LIGNES-1){
				pos = new Doublet(posActuel.getPosX(),posActuel.getPosY()+1);
				verifPos(grille[posActuel.getPosY()][posActuel.getPosX()],pos,grille);
			}


		}
		//determiner si d'autres billes de type differents seront supprimé si la zone l'est


	}
	private void verifPos(int typeBille,Doublet pos,int[][]grille){
		if(visitGrid[pos.getPosY()][pos.getPosX()] == VIDE && grille[pos.getPosY()][pos.getPosX()] == typeBille){
			posBilles.add(pos);
			pile.push(pos);
			if(isAccessibleBoundary(pos,grille)){
				billesAccessibles.add(pos);
			}
		}
		visitGrid[pos.getPosY()][pos.getPosX()] = 1;
	}

	/**
	 * comptabilise les billes en les enlevants de la liste autresBillesASuppimer
	 * @param pos position de la bille
	 * @param grille plateau du jeu
     */
	private void verifPos(Doublet pos,int[][]grille){
		if(visitGrid[pos.getPosY()][pos.getPosX()] == VIDE && grille[pos.getPosY()][pos.getPosX()] != VIDE){
			pile.push(pos);
			autresBillesASuppimer.remove(pos);
		}
		visitGrid[pos.getPosY()][pos.getPosX()] = 1;
	}


	/**
	 * revele si la bille est en bordure de la zone et que cette bordure est une zone vide.
	 * @param pos
	 * @param grille
     * @return vrai si la bille est en bordure du vide
     */
	private boolean isAccessibleBoundary(Doublet pos,int[][] grille){
		boolean res = false;
		if(pos.getPosX() > 0) {
			pos = new Doublet(pos.getPosX() - 1, pos.getPosY());
			if(grille[pos.getPosY()][pos.getPosX()] == 0)
				res = true;
		}
		if(pos.getPosX() < NB_COLONNES-1) {
			pos = new Doublet(pos.getPosX() + 1, pos.getPosY());
			if(grille[pos.getPosY()][pos.getPosX()] == 0)
				res = true;
		}
		if(pos.getPosY() > 0){
			pos = new Doublet(pos.getPosX(),pos.getPosY()-1);
			if(grille[pos.getPosY()][pos.getPosX()] == 0)
				res = true;
		}

		if(pos.getPosY() < NB_LIGNES-1){
			pos = new Doublet(pos.getPosX(),pos.getPosY()+1);
			if(grille[pos.getPosY()][pos.getPosX()] == 0)
				res = true;
		}
		return res;
	}

	private void determineAutresBilles(int[][] grille){

		//reinitialisation
		visitGrid = new int[NB_LIGNES][NB_COLONNES];
		pile = new Stack<>();
		//on imagine la zone supprimée:
		for(Doublet pos : posBilles){
			grille[pos.getPosY()][pos.getPosX()] = VIDE;
		}
		//on determine maintenant en partant des billes du plafond celle entouré de vide
		for(int i = 0; i < NB_COLONNES; i++){
			if(grille[0][i] != VIDE){
				pile.push(new Doublet(i,0));
			}
			visitGrid[0][i] = 1;
			for(int j = 1;j < NB_LIGNES;j++){
				if(grille[j][i] != VIDE){
					autresBillesASuppimer.add(new Doublet(i,j));
				}
			}
		}
		//on part des noeud de la pile et on enleve les noeud de "autresBillesAsupprimer" au fur et a mesure
		while(!pile.empty()){
			Doublet posActuel = pile.pop();
			Doublet pos;
			if(posActuel.getPosX() > 0) {
				pos = new Doublet(posActuel.getPosX() - 1, posActuel.getPosY());
				verifPos( pos, grille);
			}
			if(posActuel.getPosX() < NB_COLONNES-1) {
				pos = new Doublet(posActuel.getPosX() + 1, posActuel.getPosY());
				verifPos(pos, grille);
			}
			if(posActuel.getPosY() > 0){
				pos = new Doublet(posActuel.getPosX(),posActuel.getPosY()-1);
				verifPos(pos,grille);
			}

			if(posActuel.getPosY() < NB_LIGNES-1){
				pos = new Doublet(posActuel.getPosX(),posActuel.getPosY()+1);
				verifPos(pos,grille);
			}
		}
		//on recree la zone
		for(Doublet pos : posBilles){
			grille[pos.getPosY()][pos.getPosX()] = typeZone;
		}

	}

	private void calculTotalPoint(){
		totalPoint = POINT_BILLE*(posBilles.size()+autresBillesASuppimer.size());
	}
	public ArrayList<Doublet> getPosBilles(){
		return posBilles;
	}

	/**
	 * unused because of functional issue
	 * @return the accessible bubbles of this area for a shoot
     */
	public ArrayList<Doublet> getBillesAccessibles(){
		return billesAccessibles;
	}

	public int getTotalPoint(){
		return totalPoint;
	}

	public ArrayList<Doublet> getAutresBillesASuppimer() {
		return autresBillesASuppimer;
	}
}
