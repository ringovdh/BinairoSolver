package be.yorian.game;

import java.util.HashSet;

public class Game {

	private String[][] values;
	private int rows;
	private int columns;
	boolean isChanged;
	boolean isValid = true;
	boolean isSolved = false;

	public Game() {
		rows = 10;
		columns = 10;
		values = new String[rows][columns];
		isChanged = true;
	}

	public void initSimpleGame() {

		// rij 1
		vulRij(0, "1", ".", "1", ".", ".", "1", ".", ".", "0", ".");
		// rij 2
		vulRij(1, ".", ".", ".", ".", ".", ".", ".", ".", ".", ".");
		// rij 3
		vulRij(2, ".", ".", ".", ".", ".", ".", "0", ".", "0", ".");
		// rij 4
		vulRij(3, ".", ".", "0", ".", ".", ".", ".", ".", ".", ".");
		// rij 5
		vulRij(4, ".", "1", ".", "1", ".", ".", ".", ".", ".", "1");
		// rij 6
		vulRij(5, ".", ".", ".", ".", ".", ".", "1", ".", ".", "1");
		// rij 7
		vulRij(6, ".", ".", ".", ".", ".", "0", ".", ".", ".", ".");
		// rij 8
		vulRij(7, ".", ".", "0", "0", ".", ".", "1", ".", ".", ".");
		// rij 9
		vulRij(8, ".", ".", ".", ".", ".", ".", ".", ".", "1", "1");
		// rij 10
		vulRij(9, ".", ".", "0", "0", ".", ".", ".", ".", "0", ".");

	}

	public void initExpertGame() {

		// rij 1
		vulRij(0, ".", ".", ".", ".", ".", "0", "0", ".", "1", ".");
		// rij 2
		vulRij(1, ".", ".", ".", ".", ".", ".", "1", "0", ".", ".");
		// rij 3
		vulRij(2, ".", ".", ".", ".", ".", "1", ".", "0", ".", ".");
		// rij 4
		vulRij(3, ".", "1", ".", ".", "0", ".", ".", ".", ".", "0");
		// rij 5
		vulRij(4, ".", "0", ".", ".", ".", ".", "0", ".", ".", ".");
		// rij 6
		vulRij(5, "0", ".", ".", "0", "0", ".", ".", ".", ".", "0");
		// rij 7
		vulRij(6, ".", ".", ".", ".", ".", ".", ".", ".", ".", ".");
		// rij 8
		vulRij(7, ".", "0", ".", ".", "0", ".", ".", ".", ".", ".");
		// rij 9
		vulRij(8, "0", ".", "0", ".", "0", ".", ".", ".", ".", ".");
		// rij 10
		vulRij(9, "0", "0", "1", ".", "1", ".", ".", ".", "0", ".");
		
		// initial game afdrukken
		print();
	}
	
	public void initShortGame() {
		// rij 1
		vulRij4(0, ".", ".", "1", ".");
		// rij 2
		vulRij4(1, ".", ".", ".", ".");
		// rij 3
		vulRij4(2, ".", ".", ".", "0");
		// rij 4
		vulRij4(3, "1", ".", ".", ".");
	}
	
	private void vulRij(int r, String string, String string2, String string3, String string4, String string5,
			String string6, String string7, String string8, String string9, String string10) {
		values[r][0] = string;
		values[r][1] = string2;
		values[r][2] = string3;
		values[r][3] = string4;
		values[r][4] = string5;
		values[r][5] = string6;
		values[r][6] = string7;
		values[r][7] = string8;
		values[r][8] = string9;
		values[r][9] = string10;

	}
	
	private void vulRij4(int r, String string, String string2, String string3, String string4) {
		values[r][0] = string;
		values[r][1] = string2;
		values[r][2] = string3;
		values[r][3] = string4;
	}

	public void print() {
		for (String[] r : values) {
			for (String cel : r) {
				System.out.print(cel + " ");
			}
			System.out.println();
		}
		System.out.println("--------------------");
	}

	public Pair isSolved() {
		return searchForEmptyPlace();
	}
	
	public void makeGuess(String value) {
		setChanged(true);
		setSolved(false);
		Pair pair = searchForEmptyPlace();
		values[pair.getRij()][pair.getKolom()] = value;
	}
	
	public void cloneArray(String[][] src) {
	    int length = src.length;
	    String[][] target = new String[length][src[0].length];
	    for (int i = 0; i < length; i++) {
	        System.arraycopy(src[i], 0, target[i], 0, src[i].length);
	    }
	    this.values = target;
	}

	private Pair searchForEmptyPlace() {
		Pair pair = null;
		for (int r = 0; r < rows; r++) {
		    if (pair == null){
		    	for (int k = 0; k < columns; k++) {
					if (values[r][k].equals(".")) {
						pair = new Pair(r, k);
						break;
					}
		    	}
			}
		    else{
		    	break;
		    }
		}
		return pair;
	}


	public boolean isChanged() {
		return isChanged;
	}

	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged;
	}

	public String[][] getValues() {
		return values;
	}

	public int getRows() {
		return this.rows;
	}

	public int getColumns() {
		return columns;
	}

	public void setSolved(boolean isSolved) {
		this.isSolved = isSolved;
	}

	public void validate() {
		
		int countOnes;
		int countZeros;
		isValid = true;
		
		// check aantallen per rij
		for (int r = 0; r < rows; r++) {
			countOnes = 0;
			countZeros = 0;
			
			for (int k = 0; k < columns; k++) {
				if (values[r][k].equals("1")) {
					countOnes++;
				}
				if (values[r][k].equals("0")) {
					countZeros++;
				}
			}
			if (countOnes > columns / 2 || countZeros > columns / 2) {
				isValid = false;
			}
		}
		
		// check aantallen per kolom
		for (int k = 0; k < columns; k++) {
			countOnes = 0;
			countZeros = 0;
					
			for (int r = 0; r < rows; r++) {
				if (values[r][k].equals("1")) {
					countOnes++;
				}
				if (values[r][k].equals("0")) {
					countZeros++;
				}
			}
			if (countOnes > rows / 2 || countZeros > rows / 2) {
				isValid = false;
			}
		}
		
		// check triples per rij
		for (int r = 0; r < rows; r++) {
			for (int k = 0; k < columns-2; k++) {
				if (values[r][k].equals(values[r][k+1])&&(!values[r][k].equals("."))) {
					if (values[r][k+1].equals(values[r][k+2])) {
						isValid = false;
					}
				}
			}
		}
		// check triples per kolom
		for (int k = 0; k < columns; k++) {
			for (int r = 0; r < rows-2; r++) {
				if (values[r][k].equals(values[r+1][k])&&(!values[r][k].equals("."))){
					if (values[r+1][k].equals(values[r+2][k])){
						isValid = false;
					}
				}
			}
		}
		
		// check of er identieke rijen zijn
		HashSet<String> rijen = new HashSet<>();
		for (int r = 0; r < rows; r++) { 
			String rij = "";		
			for (int k = 0; k < columns; k++) {
				rij = rij + values[r][k];
			}
			if (!rij.contains(".")) {
				if(!rijen.add(rij)){
					isValid = false;
				}
			}
		}
		
		// check of er identieke kolommen zijn
		HashSet<String> kolommen = new HashSet<>();
		for (int k = 0; k < columns; k++) {			 
			String kolom = "";		
			for (int r = 0; r < rows; r++) {
				kolom = kolom + values[r][k];
			}
			if (!kolom.contains(".")) {
				if(!kolommen.add(kolom)){
					isValid = false;
				}
			}
		}
		
		// check of er nog lege cellen zijn
		if(searchForEmptyPlace() == null){
			isSolved = true;
		}
	}

	public Game copyGame() {
		Game copy = new Game();
		copy.values = values;
		return copy;
	}

	public void setValues(String[][] values2) {
		this.values = values2;
	}

}
