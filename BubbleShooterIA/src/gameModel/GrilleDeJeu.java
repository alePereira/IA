package gameModel;

import Utilitaire.Doublet;

import java.util.ArrayList;
import java.util.Scanner;


public class GrilleDeJeu implements GlobalValues{
	private int totalPoint = 0;
	private int[][] grille;
	private ArrayList<Zone> zoneAccessibles = new ArrayList<>();
	private int bille;
	private static GrilleDeJeu instance = null;
	
	public static GrilleDeJeu getInstance(){
		if(instance == null){
			instance = new GrilleDeJeu();
		}
		return instance;
	}
	
	private GrilleDeJeu(){
		//creation de l'etat initial du jeu
		grille = new int [NB_LIGNES][NB_COLONNES];
		for(int i = 0; i < NB_LIGNES;i++){
			for(int j = 0; j < NB_COLONNES;j++){
				if(i < NB_LIGNE_INIT)
					grille[i][j] = (int) (1 + (Math.random() * (NB_TYPE_BILLE - 1)));
				else
					grille[i][j] = 0; 
			}
		}
		jouer();
		//recuperationZones();
	}


	private boolean grilleTerminer(){
		int i = 0;
		int j = 0;
		while( i < NB_LIGNES && grille[i][j] == VIDE){
			if(j < NB_COLONNES-1){
				j++;
			}
			else{
				j= 0;
				i++;
			}
		}
		return i == NB_LIGNES;
	}
	private boolean echecPartie(){
		for(int i = 0; i < NB_COLONNES;i++){
			if(grille[NB_LIGNES-1][i] != VIDE)
				return true;
		}
		return false;
	}
	public void jouer(){
		while(!grilleTerminer() && !echecPartie()){
			print();
			System.out.println("\nnombre de points : " + totalPoint);
			bille = (int) (1 + (Math.random() * (NB_TYPE_BILLE - 1)));
			System.out.println("\ncouleur de la bille : " + bille);
			Scanner scanner = new Scanner(System.in);
			int pos = scanner.nextInt();
			tirer(pos);



		}
		print();
		System.out.println("nombre de points : " + totalPoint);
		if(grilleTerminer()){
			System.out.println("vous avez gagné !!");
		}
		else{
			System.out.println("Perdu !!");
		}
	}
	/**
	 * recupere les zones de billes de meme couleurs en prenant en compte les eventuels autres bille pouvant chuter en meme temps que la zone car plus relier au plafonf
	 */
	private void recuperationZones(){
		int[][] visitGrid = new int[NB_LIGNES][NB_COLONNES];
		//les cases vides sont considérés comme vue de base
		for(int i = 0; i < NB_LIGNES;i++){
			for(int j =0; j < NB_COLONNES;j++){
				if(grille[i][j] == VIDE){
					visitGrid[i][j] = 1;
				}
			}
		}
		int i = 0;
		int j = 0;
		while(i < NB_LIGNES && !allZoneCreated(visitGrid)) {
			if(visitGrid[i][j] != 1){
				Zone zone = new Zone(new Doublet(j, i), grille);
				for (Doublet doublet : zone.getPosBilles()) {
					visitGrid[doublet.getPosY()][doublet.getPosX()] = 1;
				}
			}
			if (j < NB_COLONNES - 1) {
				j++;
			} else if (i < NB_LIGNES) {
				i++;
				j = 0;
			}
		}
	}

	/**
	 * retourne vrai si toute la grille contient des 1
	 * @param visitGrid
	 * @return true si toute la grille contient des 1
     */
	private boolean allZoneCreated(int[][] visitGrid){
		for(int i = 0; i < NB_LIGNES;i++){
			for(int j = 0; j < NB_COLONNES;j++){
				if(visitGrid[i][j] != 1){
					return false;
				}
			}
		}
		return true;
	}


	public void tirer(int pos){
		int j = NB_LIGNES-1;
		while(j >= 0 && grille[j][pos] == VIDE){
			j--;
		}
		grille[j+1][pos] = bille;
		Zone zone = new Zone(new Doublet(pos,j+1),grille);
		if(zone.getPosBilles().size() >= 3){
			for(Doublet position : zone.getPosBilles()){
				grille[position.getPosY()][position.getPosX()] = VIDE;
			}
			for(Doublet position : zone.getAutresBillesASuppimer()){
				grille[position.getPosY()][position.getPosX()] = VIDE;
			}
			totalPoint = totalPoint+zone.getTotalPoint();
		}

		//a la fin du tour une nouvelle bille est tir� au sort pour etre tir� au prochain tour
		//bille = (int) (1 + (Math.random() * (NB_TYPE_BILLE - 1)));
	}

	public void print(){

		for(int i = 0; i < NB_LIGNES; i++){
			for(int j = 0; j < NB_COLONNES; j++){
				System.out.print("--");
			}
			System.out.print("\n");
			for(int j = 0; j < NB_COLONNES; j++){
				System.out.print("|"+grille[i][j]);
			}
			System.out.print("|");
			System.out.print("\n");
		}
		for(int j = 0; j < NB_COLONNES; j++){
			System.out.print("--");
		}
	}

	public int getTotalPoint(){
		return totalPoint;
	}
}
