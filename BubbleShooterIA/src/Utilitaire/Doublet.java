package Utilitaire;

public class Doublet {
	private int posX;
	private int posY;
	
	
	public Doublet(int x,int y){
		posX = x;
		posY = y;
	}
	
	
	public int getPosX(){
		return posX;
	}
	
	public int getPosY(){
		return posY;
	}

	@Override
	public boolean equals(Object o){
		if(o instanceof Doublet){
			Doublet obj = (Doublet)o;
			return (this.posX == obj.posX &&this.posY == obj.posY);
		}
		return false;
	}
}
