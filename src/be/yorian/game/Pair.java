package be.yorian.game;

public class Pair {

	private int kolom;
	private int rij;
	
	public Pair(int r, int k){
		this. kolom = k;
		this.rij = r;
	}

	public int getKolom() {
		return kolom;
	}

	public void setKolom(int kWaarde) {
		this.kolom = kWaarde;
	}

	public int getRij() {
		return rij;
	}

	public void setRij(int rWaarde) {
		this.rij = rWaarde;
	}
	
	
}
