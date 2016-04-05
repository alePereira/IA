package main;

import gameModel.GrilleDeJeu;


public class Main {


	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		GrilleDeJeu tet = GrilleDeJeu.getInstance();
		//tet.print();
		long timeEnd = System.currentTimeMillis();

		System.out.println("\n\n\n execution time : " + (timeEnd - time));
	}

	
}
